<#include "/front/commons/_head.ftl" />
	<div class='container w'>
			<div class='breadcrumb'>
				<strong class='business-strong'>
					<a href='${(domainUrlUtil.EJS_URL_RESOURCES)!}/index.html'>首页</a>
				</strong>
				<span>
					&nbsp;>&nbsp;
					<a href='javascript:void(0)'>我的ejavashop</a>
				</span>
				<span>
					&nbsp;>&nbsp;
					<a href='javascript:void(0)'>我的优惠劵</a>
				</span>
			</div>
		</div>
		<div class='container w'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>我的优惠劵</h3>
				
				<div class='mc'>
					<div class="coupon-toolbar">
						<ul>
							<li class="active"><a href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/member/coupon-use.html">未使用</a></li>
							<li><a href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/member/coupon-use-yes.html">已使用</a></li>
						</ul>
					</div>

					<div class="voucher">
						<!-- 未使用 -->
						<div class="unused-box-use main-use selected">
						<#if couponUsers??>
							<#list couponUsers as couponUser>
								<#if !couponUser.timeout>
									<div class="unused-box">
										<div class="unused-left">
												<strong class="unused-price">￥${(couponUser.couponValue)!}</strong>
												<p class="unused-limit">【消费满 ${(couponUser.minAmount)!}元 可用】</p>
												<div class="unused-time"><#if couponUser.useStartTime??>${couponUser.useStartTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
													--<#if couponUser.useEndTime??>${couponUser.useEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
												<div class="unused-icon"></div>
										</div>
										<div class="unused-right">
												<div class="unused-range-item">
													<span class="">券&nbsp;&nbsp;编&nbsp;&nbsp;号：</span>
													<span class="txt">${(couponUser.couponSn)!}</span>
												</div>
												<div class="unused-range-item">
													<span class="">优惠价名称：</span>
													<span class="txt">${(couponUser.couponName)!}</span>
												</div>
												<div class="unused-range-item">
													<span class="">适用商家：</span>
													<span class="txt">${(couponUser.sellerName)!}</span>
												</div>
												<div class="unused-btns">
													<a href="${(domainUrlUtil.EJS_URL_RESOURCES)!}/store/${(couponUser.sellerId)!0}.html" target='_blank' class="a-btn" target="_blank">立即使用</a>
												</div>
										</div>
									</div>
								<#else>
									<div class="unused-box">
										<div class="unused-left">
												<div class="bg-color-overdue"></div>
												<strong class="unused-price">￥${(couponUser.couponValue)!}</strong>
												<p class="unused-limit">【消费满 ${(couponUser.minAmount)!}元 可用】</p>
												<div class="unused-time"><#if couponUser.useStartTime??>${couponUser.useStartTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
													--<#if couponUser.useEndTime??>${couponUser.useEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
												<div class="unused-icon"></div>
												<div class="overdue-icon"></div>
										</div>
										<div class="unused-right">
												<div class="unused-range-item">
													<span class="">券&nbsp;&nbsp;编&nbsp;&nbsp;号：</span>
													<span class="txt">${(couponUser.couponSn)!}</span>
												</div>
												<div class="unused-range-item">
													<span class="">优惠价名称：</span>
													<span class="txt">${(couponUser.couponName)!}</span>
												</div>
												<div class="unused-range-item">
													<span class="">适用商家：</span>
													<span class="txt">${(couponUser.sellerName)!}</span>
												</div>
										</div>
									</div>
								</#if>	
								
							</#list>
						</#if>	
							
							
						</div>
					</div>

					<div class='mt10'>
						<!-- 分页 -->
						<#include "/front/commons/_pagination.ftl" />
					</div>
			  </div>
				
			</div>
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#myecouponuser").addClass("currnet_page");
		});
	</script>				
<#include "/front/commons/_end.ftl" />