<title>timeout Error Page</title>
<!-- ajax layout which only needs content area -->
<!-- PAGE CONTENT BEGINS -->
<!-- #section:pages/error -->
<style>
    h1 {
        font-family: "微软雅黑";
        font-weight: 700;
        margin-bottom: 20px;
        margin-top: -25px;
    }

    p {
        font-size: 14px;
        background: #fff;
        margin: 0;
    }

    .btn {
        height: 34px;
        padding: 0px 12px;
        line-height: 25px;
    }
</style>
<div class="error-container">
    <div class="error-image" style="text-align: center;width:100%;background:#fff;">
        <img src="../img/error-img/timeout.gif" style="width:350px;transform: scale(1.5);">
        <div style="position:relative;z-index: 1;">
            <h1>请求超时...</h1>
            <p>重新 <a href="">请求</a> 一下吧！</p>
        </div>
    </div>
</div>
<!-- page specific plugin scripts -->
<script type="text/javascript">
    var scripts = [null, null]
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
    });
</script>
