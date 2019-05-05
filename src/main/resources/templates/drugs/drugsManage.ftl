<#include "/common/commoncss.ftl">
<script type="text/javascript" src="js/task/taskmanage.js"></script>
<style type="text/css">
    a {
        color: black;
    }

    a:hover {
        text-decoration: none;
    }

    .label-back {
        background-color: #6C7B8B;
        color: white;
    }

    .label-back:hover {
        color: white !important;
        background-color: #5c666b !important;
    }

    .block {
        display: inline-block;
        width: 20px;
    }

    .co {
        color: blue;
    }

    .bl {
        color: black;
    }

    .commen {
        cursor: pointer;
    }

</style>
<div class="row" style="padding-top: 10px;">
    <div class="col-md-2">
        <h1 style="font-size: 24px; margin: 0;" class="">药品管理</h1>
    </div>
    <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
            disabled="disabled">药品管理</a>
    </div>
</div>
<div class="row" style="padding-top: 15px;">
    <div class="col-md-12 thistable">
        <!--id="container"-->
        <div class="bgc-w box box-primary">
            <!--盒子头-->
            <div class="box-header">
                <h3 class="box-title">
                    <a href="/findDrugsById" class="label label-success" style="padding: 5px;">
                        <span class="glyphicon glyphicon-plus"></span> 新增
                    </a>
                </h3>
                <div class="box-tools">
                    <div class="input-group" style="width: 150px;">
                        <input type="text" class="form-control input-sm cha"
                               placeholder="查找..."/>
                        <div class="input-group-btn chazhao">
                            <a class="btn btn-sm btn-default"><span
                                    class="glyphicon glyphicon-search"></span></a>
                        </div>
                    </div>
                </div>
            </div>
            <!--盒子身体-->
            <div class="box-body no-padding">
                <div class="table-responsive">
                    <table class="table table-hover ">
                        <tr>

                            <th scope="col">药品名称</th>
                            <th scope="col">药品类别</th>
                            <th scope="col">价格</th>
                            <th scope="col">说明</th>
                            <th scope="col">图片</th>
                            <th scope="col">销售人员</th>
                            <th scope="col">操作</th>
                        </tr>
						<#list drgusVOSList as item>
						<tr>
                            <td><span>${(item.drugs.drugsName)!''}</span></td>
                            <td><span>${(item.drugCate.cateName)!''}</span></td>
                            <td><span>${(item.drugs.drugsPrice)!''}</span></td>
                            <td><span>${(item.drugs.drugsRemark)!''}</span></td>
                            <td><span>${(item.drugs.drugsPicture)!''}</span></td>
                            <td><span>${(item.userName)!''}</span></td>
							<#--<#if item.brandYeaes??>-->
								<#--<td><span>${item.brandYeaes}</span></td>-->
                            <#--<#else>-->
								 <#--<td><span>0</span></td>-->
                            <#--</#if>-->
                            <#--<td><span>${item.brandComment}</span></td>-->
                            <td>
                                <a href="/goDrugsDetail?drugsId=${item.drugs.drugsId}" class="label xiugai">
                                    <span class="glyphicon glyphicon-edit"></span> 查看</a>
                                <a href="/findDrugsById?drugsId=${item.drugs.drugsId}" class="label xiugai">
                                    <span class="glyphicon glyphicon-edit"></span> 修改</a>
                                <a href="/deleteDrugsById?drugsId=${item.drugs.drugsId}" onclick="{return confirm('删除该记录将不能恢复，确定删除吗？');};"
                                   class="label shanchu"><span
                                        class="glyphicon glyphicon-remove"></span> 删除</a></td>
                        </tr>
                        </#list>

                    </table>
                </div>
            </div>
            <!--盒子尾-->
			<#--<#include "/common/paging.ftl">-->
        </div>
    </div>
</div>
<script>
    <#--$(function () {-->

        <#--$('.baseKetsubmit').on('click', function () {-->
            <#--var baseKey = $('.baseKey').val();-->
            <#--$('.thistable').load('${url}?baseKey=baseKey');-->
        <#--});-->

        <#--$(".chazhao").click(function () {-->
            <#--var con = $(".cha").val();-->
            <#--$(".thistable").load("roleser", {val: con});-->
        <#--});-->

    <#--});-->
</script>


