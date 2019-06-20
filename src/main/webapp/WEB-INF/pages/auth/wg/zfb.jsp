<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>支付宝网购</title>
</head>
<script type="text/javascript">
    $(function () {
        $("#zfb_pp").change(function () {
            var pp = $("#zfb_pp").val();
            if (pp == 1) {
                $("#zfb_dj").val("3999");
            } else if (pp == 2) {
                $("#zfb_dj").val("9999");
            }
        });

        $("#zfb_tj").click(function () {
            var money = $("#zfb_dj").val() * $("#zfb_sl").val();
            alert("总价："+money);
            if (money > 0 && money < 1000000) {
                $.ajax({
                    url: "/wg/pay/zfb_pay",
                    type: "POST",
                    data: {
                        money: money,
                        zfb_pp: $("#zfb_pp").val()
                    },
                    /*dataType: "json",*/
                    success: function (data) {
                        alert("购买成功！总金额为：" + money);
                        $("#back_form").append(data);
                    },
                    error:function () {
                        alert("error");
                    }
                });
            } else {
                alert("金额异常！总金额为：" + money);
            }
        })
    })
</script>
<div class="openAppGrid">
    <div id="back_form"></div>
    <div id="openAppGrid">
        <form id="pay_zfb">
            <table border="1" bgcolor="#deb887">
                <tr>
                    <td>商品</td>
                    <td>手机</td>
                </tr>
                <tr>
                    <td>品牌</td>
                    <td>
                        <select id="zfb_pp" name="zfb_pp">
                            <option value="1" selected>华为</option>
                            <option value="2">苹果</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>单价</td>
                    <td><input type="text" id="zfb_dj" value="3999" oninput="value=value.replace(/[^\d]/g,'')"></td>
                </tr>
                <tr>
                    <td>数量</td>
                    <td><input id="zfb_sl" name="zfb_sl" value="1" oninput="value=value.replace(/[^\d]/g,'')"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="button" value="购买" id="zfb_tj">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%--<div>
    <form name="punchout_form" method="post" action="https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=PHTkVqqbLsRVzZojW83E1bTyXgOh7RoG75Oa8BYfAsTYMy0MnKlxn0khPne3nA8dIlwlzCIwuRnZFC0coxRqtvDISNd3LeKK5wXiKzqnBkcOTyYQKtLRVzOuLwQTS27Ox7N1tnLHq5giLJIwonYsH1bQjVfI58PpDJ4hy2s%2BRYq6myznujmdQKC1H0zKA8IP98B2ZTMXWGOeb6YOkquWP24T8OhEnTGYbgdDZxIdqrjdGOwX1kitDOYmdiYA5e6LCm54HE2O%2FMFN83JzwnZQAGyRSREgJCJXPiVPqKtnfcont%2Bg%2BWp06n62nbM%2BHTq9WEEKnzBoxHQmTiqFs%2Bds3tw%3D%3D&return_url=http%3A%2F%2Fwww.baidu.com&notify_url=http%3A%2F%2Fwww.baidu.com&version=1.0&app_id=2016092200571453&sign_type=RSA2&timestamp=2019-02-21+17%3A53%3A07&alipay_sdk=alipay-sdk-java-3.4.49.ALL&format=json">
        <input type="text" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;20190221175227&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;,&quot;total_amount&quot;:&quot;3999&quot;,&quot;subject&quot;:&quot;1&quot;,&quot;body&quot;:&quot;HuaWei&quot;}">
        <input type="submit" value="立即支付" >
    </form>
    &lt;%&ndash;<script>document.forms[0].submit();</script>&ndash;%&gt;
</div>--%>
