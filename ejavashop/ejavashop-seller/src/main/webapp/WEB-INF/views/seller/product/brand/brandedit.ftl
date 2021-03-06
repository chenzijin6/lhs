<#include "/seller/commons/_detailheader.ftl" />
<#assign currentBaseUrl="/seller/product/brand/"/>
<script language="javascript">
    $(function () {
        var backUrl = "${domainUrlUtil.EJS_URL_RESOURCES}${currentBaseUrl}";
        var options = {
            url: '${domainUrlUtil.EJS_URL_RESOURCES}${currentBaseUrl}update',
            type: 'post',
            success: function (data) {
                if (data && null != data.success && data.success == true) {
                    window.location.href=backUrl;
                }else if(data.backUrl != null){
                    alert(data.message);
                    window.location.href=data.backUrl;
                }else{
                    refrushCSRFToken(data.csrfToken);
                    alert(data.message);
                }
            }
        };
        $("#back").click(function () {
            window.location.href = backUrl;
        });
        $("#add").click(function () {
            if($('#addForm').form('validate')){
                $('#addForm').ajaxSubmit(options);
            }
        });
        <#if message??>$.messager.progress('close');
        alert('${message}');</#if>
    })
</script>

<div class="wrapper">
    <div class="formbox-a">
        <h2 class="h2-title">品牌编辑
            <span class="s-poar">
                <a class="a-back" href="${domainUrlUtil.EJS_URL_RESOURCES}${currentBaseUrl}">返回</a>
            </span>
        </h2>

        <#--1.addForm----------------->
        <div class="form-contbox">
        <@form.form method="post" class="validForm" id="addForm" name="addForm" enctype="multipart/form-data">
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
                <input type="hidden" name="id" value="${(brand.id)!''}"/>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>品牌名称: </label>
                            <input type="text" id="name" name="name" value="${(brand.name)!''}"  class="txt w200 easyui-validatebox" missingMessage="品牌名称必填，2-20个字符" data-options="required:true,validType:'length[2,20]'"/>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>首字母: </label>
                            <input type="text" id="nameFirst" name="nameFirst" value="${(brand.nameFirst)!''}" class="txt w200 easyui-validatebox" missingMessage="首字母必填，1个字符" data-options="required:true,validType:'length[1,1]'"/>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>品牌图片: </label>
                            <input type="file" id="imageFile" name="imageFile" class="txt w200"/>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>品牌说明: </label>
                            <textarea id="explainInfo" name="explainInfo" class="txt easyui-validatebox" missingMessage="品牌说明必填，2-200个字符" data-options="required:true,validType:'length[2,200]'" style="margin: 2px; width: 353px; height: 133px;"/>${(brand.explainInfo)!''}</textarea>
                        </p>
                    </div>
                </dd>
            </dl>

            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>帮助</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <label class="lab-item">帮助信息。</label>
                    </div>
                </dd>
            </dl>

            <#--2.batch button-------------->
            <p class="p-item p-btn">
                <input type="button" id="add" class="btn" value="编辑"/>
                <input type="button" id="back" class="btn" value="返回"/>
            </p>
        </@form.form>
        </div>
    </div>
</div>

<#include "/seller/commons/_detailfooter.ftl" />