package com.ejavashop.web.controller.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejavashop.core.ConstantsEJS;
import com.ejavashop.core.ConvertUtil;
import com.ejavashop.core.HttpJsonResult;
import com.ejavashop.core.ServiceResult;
import com.ejavashop.entity.cart.Cart;
import com.ejavashop.entity.member.Member;
import com.ejavashop.entity.product.ProductGoods;
import com.ejavashop.service.cart.ICartService;
import com.ejavashop.service.product.IProductGoodsService;
import com.ejavashop.vo.cart.CartInfoVO;
import com.ejavashop.web.controller.BaseController;
import com.ejavashop.web.util.WebFrontSession;

/**
 * 购物流程-购物车<br>
 * 本controller中得请求都需要登录才能访问
 * 
 * @Filename: CartController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "cart")
public class CartController extends BaseController {

    @Resource
    private ICartService         cartService;
    @Resource
    private IProductGoodsService productGoodsService;

    /**
     * 添加物品到购物车
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/addtocart.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> addCart(HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         Map<String, Object> dataMap, Cart cart) {

        Member member = WebFrontSession.getLoginedUser(request);
        cart.setMemberId(member.getId());

        ProductGoods goods = null;
        if (cart.getProductGoodsId() == null || cart.getProductGoodsId().intValue() == 0) {
            // 如果是货品是空，则可能是从列表页等不能选择货品的页面请求过来，随机取一个货品放入购物车
            ServiceResult<List<ProductGoods>> goodsResult = productGoodsService
                .getGoodSByProductId(cart.getProductId());
            if (goodsResult.getSuccess() && goodsResult.getResult() != null
                && goodsResult.getResult().size() > 0) {
                goods = goodsResult.getResult().get(0);
            }
        } else {
            ServiceResult<ProductGoods> goodResult = productGoodsService
                .getProductGoodsById(cart.getProductGoodsId());
            goods = goodResult.getResult();
        }
        if (goods == null) {
            return new HttpJsonResult<Boolean>("添加购物车失败，获取货品信息失败！");
        }

        cart.setProductGoodsId(goods.getId());
        cart.setSpecId(goods.getNormAttrId());
        cart.setSpecInfo(goods.getNormName());

        ServiceResult<Boolean> serviceResult = cartService.addToCart(cart);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到 我的购物车 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/detail.html", method = { RequestMethod.GET })
    public String toMyCart(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap) {

        Member member = WebFrontSession.getLoginedUser(request);

        //取购物车信息  产品价格 按照商家来区分
        //查询购物车
        ServiceResult<CartInfoVO> serviceResult = cartService.getCartInfoByMId(member.getId(), null,
            ConstantsEJS.SOURCE_1_PC, 1);
        dataMap.put("cartInfoVO", serviceResult.getResult());

        return "front/cart/cart";
    }

    /**
     * 购物车页面ajax更新购物车列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/getcartinfo.html", method = { RequestMethod.POST })
    public String getCartInfo(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        //取购物车信息  产品价格 按照商家来区分
        //查询购物车
        ServiceResult<CartInfoVO> serviceResult = cartService.getCartInfoByMId(member.getId(), null,
            ConstantsEJS.SOURCE_1_PC, 1);
        dataMap.put("cartInfoVO", serviceResult.getResult());

        return "front/cart/cart_list";
    }

    /**
     * 跳转到添加购物车成功页面
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/add.html", method = { RequestMethod.GET })
    public String toCartSuccess(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap) {
        return "front/cart/cartsuccess";
    }

    /**
     * 更新单条购物车购买数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateCartById.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updateCartById(HttpServletRequest request,
                                                                HttpServletResponse response) {

        String cartIdStr = request.getParameter("id");
        Integer cartId = ConvertUtil.toInt(cartIdStr, 0);
        String countStr = request.getParameter("count");
        Integer count = ConvertUtil.toInt(countStr, 0);

        ServiceResult<Boolean> serviceResult = cartService.updateCartNumber(cartId, count);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 删除购物车数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteCartById.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> deleteCartById(HttpServletRequest request,
                                                                HttpServletResponse response) {
        String[] idsStr = request.getParameterValues("id");
        if (idsStr == null || idsStr.length == 0) {
            return new HttpJsonResult<Boolean>("请选择商品后再点击删除！");
        }
        List<Integer> ids = new ArrayList<Integer>();
        for (String idStr : idsStr) {
            ids.add(ConvertUtil.toInt(idStr, 0));
        }
        ServiceResult<Boolean> serviceResult = cartService.deleteCartByIds(ids);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 单条购物车数据选中或不选中
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cartchecked.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> cartChecked(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestParam(value = "id", required = true) Integer id,
                                                             @RequestParam(value = "checked", required = true) Integer checked) {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Boolean> serviceResult = cartService.updateChecked(member.getId(), id,
            checked);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 购物车数据全选或不全选
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cartcheckedall.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> cartCheckedAll(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @RequestParam(value = "checked", required = true) Integer checked) {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Boolean> serviceResult = cartService.updateChecked(member.getId(), 0,
            checked);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }
}
