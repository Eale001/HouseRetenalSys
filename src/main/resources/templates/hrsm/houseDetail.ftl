<#include "/common/commoncss.ftl">
<link rel="stylesheet" type="text/css" href="css/common/checkbox.css"/>
<style type="text/css">
    a {
        color: black;
    }

    a:hover {
        text-decoration: none;
    }

    .bgc-w {
        background-color: #fff;
    }

</style>

<div class="row" style="padding-top: 10px;">
    <div class="col-md-2">
        <h1 style="font-size: 24px; margin: 0;" class="">房源管理</h1>
    </div>
    <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
            disabled="disabled">房源管理</a>
    </div>
</div>
<div class="row" style="padding-top: 15px;">
    <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box">
            <!--盒子头-->
            <div class="box-header">
                <h3 class="box-title">
                    <a href="javascript:history.back();" class="label label-default"
                       style="padding: 5px;"> <i
                            class="glyphicon glyphicon-chevron-left"></i> <span>返回</span>
                    </a>
                </h3>
            </div>
            <!--盒子身体-->
            <form action="/orderEdit" method="post" onsubmit="return check();">
                <div class="box-body no-padding">
                    <div class="box-body">
                        <!--錯誤信息提示  -->
                        <div class="alert alert-danger alert-dismissable" role="alert" style="display: none;">
                            错误信息:
                            <button class="thisclose close" type="button">&times;</button>
                            <span class="error-mess"></span>
                        </div>
                        <div class="row">

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">标题</span>
                                </label>
                                <input name="title" type="text" value="${(house.title)!''}" class="form-control" readonly/>
                                <input name="houseId" type="text" value="${(house.houseId)!''}" hidden="hidden"/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">住房面积</span>
                                </label>
                                <input name="area" type="text" value="${(house.area)!''}" class="form-control" readonly/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">租金</span>
                                </label>
                                <input name="price" type="text" value="${(house.price)!''}" class="form-control" readonly/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">联系电话</span>
                                </label>
                                <input name="phone" type="text" value="${(house.phone)!''}" class="form-control" readonly/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">联系人</span>
                                </label>
                                <input name="contacts" type="text" value="${(house.contacts)!''}" class="form-control" readonly/>
                            </div>


                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">所属地区</span>
                                </label>

                                <select class="form-control" name="regionId" readonly="">
							        <#list districtList as district>
                                        <option value="${(district.districtId)!''}">${(district.region.regionName)!''} ${(district.disName)!''}</option>
                                    </#list>
                                </select>

                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">房屋类型</span>
                                </label>

                                <select class="form-control" name="regionId" readonly="">
							        <#list typeList as type>
                                        <option value="${(type.typeId)!''}">${(type.typeName)!''}</option>
                                    </#list>
                                </select>

                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label1">描述</span>
                                </label>
                                <input name="discription" type="text" value="${(house.discription)!''}" class="form-control" readonly/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label>
                                    <span id="ctl00_cphMain_Label2">录入人员</span>
                                </label>
                                <input name="userName" type="text" value="${(user.userName)!''}"
                                       style="background-color:#fff;" class="form-control" readonly/>
                            </div>

                        </div>
                    </div>
                </div>
                <!--盒子尾-->
                <div class="box-footer">
                    <input class="btn btn-primary" id="save" type="submit" value="预约"/>
                    <input class="btn btn-default" id="cancel" type="button" value="取消"
                           onclick="window.history.back();"/>
                </div>
            </form>
        </div>
    </div>
</div>
<#include "/common/modalTip.ftl"> 
<script>
    //表单提交前执行的onsubmit()方法；返回false时，执行相应的提示信息；返回true就提交表单到后台校验与执行
    function check() {
        console.log("开始进入了");
        //提示框可能在提交之前是block状态，所以在这之前要设置成none
        $('.alert-danger').css('display', 'none');
        var isRight = 1;
        $('.form-control').each(function (index) {
            // 如果在这些input框中，判断是否能够为空
            if ($(this).val() == "") {
                // 排除哪些字段是可以为空的，在这里排除
                if (index == 1) {
                    return true;
                }

                // 获取到input框的兄弟的文本信息，并对应提醒；
                var brother = $(this).siblings('.control-label').text();
                var errorMess = "[" + brother + "输入框信息不能为空]";
                // 对齐设置错误信息提醒；红色边框
                $(this).parent().addClass("has-error has-feedback");
                $('.alert-danger').css('display', 'block');
                // 提示框的错误信息显示
                $('.error-mess').text(errorMess);
                // 模态框的错误信息显示
                $('.modal-error-mess').text(errorMess);
                isRight = 0;
                return false;
            } else {
                // 在这个里面进行其他的判断；不为空的错误信息提醒
                return true;
            }
        });
        if (isRight == 0) {
            //modalShow(0);
            return false;
        } else if (isRight == 1) {
            //modalShow(1);
            return true;
        }
//	return false;
    }
</script>