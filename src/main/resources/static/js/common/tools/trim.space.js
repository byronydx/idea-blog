/**
 * 去掉input框前后空格
 */
$("input[type=text],input[type=textarea]").bind("blur",function(){
    var result=$(this).val().replace(/(^\s*)|(\s*$)/g, "");
    $(this).val(result);
});