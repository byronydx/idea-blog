/**
 * 时间处理工具类
 *
 * @author suihonghua
 *
 * @returns {DateUtil}
 */
function DateUtil() {
}
DateUtil.prototype = new Object();

// DateUtil.prototype.length = 1;

/**
 * 格式化日期时间
 *
 * @param d
 *            Date实例
 * @returns string 格式如：2001-01-01 01:01:01
 *
 * @author suihonghua
 */
DateUtil.formatDateTime = function(date) {
	return this.format(date, "yyyy-MM-dd HH:mm:ss");
};

/**
 * 格式化日期
 *
 * @param d
 *            Date实例
 * @returns string 格式如：2001-01-01
 *
 * @author suihonghua
 */
DateUtil.formatDate = function(date) {
	return this.format(date, "yyyy-MM-dd");
};

/**
 * 格式化时间
 *
 * @param d
 *            Date实例
 * @returns string 格式如：01:01:01
 *
 * @author suihonghua
 */
DateUtil.formatTime = function(date) {
	return this.format(date, "HH:mm:ss");
};

/**
 * 时间格式化 e.g. var testDate = new Date( 1320336000000 );//这里必须是整数，毫秒
 *
 * var testStr1 = DateUtil.format(testDate, "yyyy年MM月dd日HH小时mm分ss秒"); var
 * testStr2 = DateUtil.format(testDate, "yyyy-MM-dd HH:mm:ss.S"); var testStr3 =
 * DateUtil.format(testDate, "yyyy-MM-dd HH:mm:ss"); alert(testStr1);
 * alert(testStr2); alert(testStr3);
 *
 * @param date
 * @param format
 * @returns
 *
 * @author suihonghua
 */
DateUtil.format = function(date, format) {
	try {
		// yyyy年MM月dd日 HH小时mm分ss秒
		var o = {
			"M+" : date.getMonth() + 1, // month
			"d+" : date.getDate(), // day
			"H+" : date.getHours(), // hour
			"m+" : date.getMinutes(), // minute
			"s+" : date.getSeconds(), // second
			"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
			"S" : date.getMilliseconds()
		// millisecond
		};

		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
        return format;
	} catch (e) {
		return "";
	}
};

/**
 * 解析为Data类型(目前string只支持标准格式)
 *
 * @param val
 *            参数(int or string)
 * @returns Date
 *
 * @author suihonghua
 */
DateUtil.parse = function(val) {
	if (typeof (val) == "number") {
		return new Date(val);
	} else if (typeof (val) == "string") {
		return new Date(Date.parse(val.replace(/-/g, "/")));
	}
	return null;
};

DateUtil.addDays = function(date,value) {
	date.setDate(date.getDate() + value);
	return date;
};

/**
 * 时间的大小比较
 *
 * @format yyyy-MM-dd HH:mm:ss
 *
 * @returns {boolean}
 *
 */
DateUtil.dateCompare=function(startdate,enddate){
  var beginTimes = startdate.substring(0, 10).split('-');
  var endTimes = enddate.substring(0, 10).split('-');
  var  beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + startdate.substring(10, 19);
  var endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + enddate.substring(10, 19);
  var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
  if (a < 0) {
    return false;
  } else if (a >= 0) {
    return true;
  }
}

/**
 * 字符串工具类
 *
 * @author suihonghua
 *
 * @returns {StringUtil}
 *
 */
function StringUtil() {
}
StringUtil.prototype = new Object();

// StringUtil.prototype.length = 1;

StringUtil.nullToEmpty = function(str) {
	return str == null ? "" : str;
};

StringUtil.isEmpty = function(str) {
	if (null == str || '' == str) {
		return true;
	}
	return false;
};

StringUtil.trim = function(str) {
	return str.replace(/(^[\s]*)|([\s]*$)/g, "");
};

StringUtil.trimLeft = function(str) {
	return str.replace(/(^[\s]*)/g, "");
};

StringUtil.trimRight = function(str) {
	return str.replace(/([\s]*$)/g, "");
};

StringUtil.replaceAll = function(str, s1, s2) {
	return str.replace(new RegExp(s1, "gm"), s2);
};

StringUtil.startWith = function(str, s) {
	if (str == null || s == '' || str.length == 0 || s.length == 0)
		return false;
	if (str.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
};

/**
 * Map实现类(详细使用请阅读代码，非Java原版Map)
 *
 * @author suihonghua
 *
 * @returns {Map}
 *
 */
function Map() {
	this.container = new Object();
}

Map.prototype.putAll = function(map) {
	var keyset = map.keySet();
	for ( var i = 0; i < keyset.length; i++) {
		var key = keyset[i];
		this.put(key, map.get(key));
	}
};

Map.prototype.put = function(key, value) {
	this.container[key] = value;
};

Map.prototype.get = function(key) {
	return this.container[key];
};

Map.prototype.keySet = function() {
	var keyset = [];
	for ( var key in this.container) {
		keyset.push(key);
	}
	return keyset;
};

Map.prototype.size = function() {
	var keyset = this.keySet();
	return keyset.length;
};

Map.prototype.containsKey = function(key) {
	for ( var _key in this.container) {
		if (_key == key) {
			return true;
		}
	}
	return false;
};

//返回map中的key值数组
Map.prototype.keyArray=function(){

    var keys=new Array();
    for(var p in this.container)
    {
        keys.push(p);
    }

    return keys;
}

Map.prototype.toString = function() {
	var str = "";
	for ( var key in this.container) {
		str += key + ":" + this.container[key] + ",";
	}
	if (str.length > 0) {
		str = str.substring(0, str.length - 1);
	}
	str = "{" + str + "}";
	return str;
};

Map.valueOf = function(obj) {
	var map = new Map();
	for ( var key in obj) {
		map.put(key, obj[key]);
	}
	return map;
};

//删除map中指定的key
Map.prototype.remove = function(key){
    try{

        delete this.container[key];

    }catch(e){
        return e;
    }
};

//清空map
Map.prototype.clear = function(){
    try{
        delete this.container;
        this.container = {};

    }catch(e){
        return e;
    }
};

/**
 * 正则验证工具类
 *
 * @author ligang
 *
 * @returns {RegexUtil}
 */
function RegexUtil() {
}
RegexUtil.prototype = new Object();

/**
 * 验证参数是否是有效URL
 *
 * @param url
 * @return
 */
RegexUtil.isUrl = function(url) {
	var strRegex = "^((https|http|ftp|rtsp|mms)://)?([a-z0-9A-Z]{0,10}\.)?[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
	var re = new RegExp(strRegex);
	if (re.test(url)) {
		return true;
	} else {
		return false;
	}
};
/**
 * 根据名称判断是否是直辖市
 * @param name
 * @returns {boolean}
 */
RegexUtil.isMunicipality = function(name){
    var strRegex = /^(北京|上海|天津|重庆)/;
    if(strRegex.test(name)) {
        return true;
    } else {
        return false;
    }
};
/**
 * 校验是否是数字或带小数点数字
 * @param name
 * @returns {boolean}
 */
RegexUtil.isNumber = function(name){
    var strRegex = /^(\d{1,8})(\.\d{1,4})?$/;
    if(strRegex.test(name)) {
        return true;
    } else {
        return false;
    }
};
/**
 * 校验是否正整数
 * @param name
 * @returns {boolean}
 */
RegexUtil.isInteger = function(name){
	var strRegex = /^\d+$/;
	if(strRegex.test(name)) {
		return true;
	} else {
		return false;
	}
};
/**
 * 校验是否为SKU
 * @param name
 * @returns {boolean}
 */
RegexUtil.isSku = function(name){
	var strRegex = /^CU\d{5}$/;
	if(strRegex.test(name)) {
		return true;
	} else {
		return false;
	}
};
/**
 * 校验是否为合法货位
 * @param name
 * @returns {boolean}
 */
RegexUtil.isLocation = function(name){
	/*var strRegex = /^[A-C]{1}\d{2}-\d{3}-\d{4}$/;
	if(strRegex.test(name)) {
		return true;
	} else {
		return false;
	}*/
	return true;
};

/**序列化表单数据
 * @param	formId  form表单的Id值
 */
function serializeForm(formId) {
    var jsonData = {};
    var serializeArray = $("#" + formId).serializeArray();
    $.each(serializeArray, function(index, field){
        var name = field.name;
        var value = StringUtil.trim(field.value);
        if(value){
            jsonData[name] = value;
        }
    });
    return jsonData;
}

// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2) {
	var  r1 = 0,  r2 = 0, t1, t2, n, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
        r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
        r2 = 0;
	}
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
    //m = (r1 >= r2) ? r1-r2 : r2-r1;
	with (Math) {
		t1 = Number(arg1.toString().replace(".", ""));
		t2 = Number(arg2.toString().replace(".", ""));
		return ((t1 / t2) * pow(10, r2-r1)).toFixed(n<2?2:n);
	}
}

// 给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
};


//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
    var r1, r2, m, n;
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{
        r1=s1.split(".")[1].length;
    }catch(e){
        r1 = 0;
    }
    try{
        r2=s2.split(".")[1].length;
    }catch(e){
        r2 = 0;
    }
    // 动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    m = r1 + r2;
    return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10, m)).toFixed(n);

}
//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
};

function accMulThree(arg1,arg2,arg3)
{
  var r1, r2, r3, m;
  var m=0,s1=arg1.toString(),s2=arg2.toString(),s3=arg3.toString();
  try{
      r1=s1.split(".")[1].length;
  }catch(e){
      r1 = 0;
  }
  try{
      r2=s2.split(".")[1].length;
  }catch(e){
      r2 = 0;
  }
  try{
      r3=s3.split(".")[1].length;
  }catch(e){
      r3 = 0;
  }
  // 精度固定2位
  m = r1 + r2 + r3;
  return (Number(s1.replace(".",""))*Number(s2.replace(".",""))*Number(s3.replace(".",""))/Math.pow(10, m)).toFixed(2);

}

// 加法函数，用来得到精确的加法结果
// 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
// 调用：accAdd(arg1,arg2)
// 返回值：arg1加上arg2的精确结果
function accAdd(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m + arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
};
// 减法函数
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	// last modify by deeka
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg2 * m - arg1 * m) / m).toFixed(n);
}
// /给number类增加一个sub方法，调用起来更加方便
Number.prototype.sub = function(arg) {
	return accSub(arg, this);
};


var g_require={};
/**
 * load javascript file
 * @param u:javascript url
 * @param c :callback function
 * example:  loadScript("a.js",function(){})
 * 非阻塞模式加载js文件
 */
function loadScript(u, c) {
	if (typeof g_require[u] != "undefined") {
		if (c) {
			c.call();
		}
		return;
	}
	g_require[u] = true;
	var s = document.createElement("script");
	s.ansyc = 'async';
	s.type = "text/javascript";
	s.src = getRootPath() + u + "?v=g_version";

	var f = document.getElementsByTagName("script")[0];
	f ? f.parentNode.insertBefore(s, f) : document.body.appendChild(s);
	if (!!window.ActiveXObject) {
		s.onreadystatechange = function() {
			if (this.readyState === "loaded" || this.readyState === "complete") {
				// Handle memory leak in IE
				s.onreadystatechange = null;
				if (c) {
					c.call();
				}
			}
		};
	} else {
		s.onload = function() {
			s.onload = null;
			if (c) {
				c.call();
			}
		};

	}
	return undefined;

};

/**
 * 获取根路径
 * @returns {String}
 */
function getRootPath(){
    //return window.location.protocol + '//' + window.location.host + '/'+ webName;
	var pathName = location.pathname.split("/");
	var webName="";
	if(pathName[1]){
		webName = "/"+pathName[1];
	}
	webName = webName == "/wms" ? '': webName;
    return location.protocol + '//' + location.host + webName;
}

/**
 * @param 返回固定格式的日期：yyyy-MM-dd HH:mm:ss
 * @returns
 */
function formatDateToClass(timeStr){
	var returnTime = (timeStr != null && ""!= timeStr) ? DateUtil.format(new Date(timeStr), "yyyy-MM-dd HH:mm:ss") : "";
	return returnTime;
}

/**
 * 序列化form并去掉空字符串
 * @param formid
 * @returns {Array}
 */
function serializeArrayExcludeBlank(formid) {
	var form = $("#"+formid);
	if (form.length==0){
		return [];
	}
	var paramArray = form.serializeArray();
	var result = [];
	var l = paramArray.length;
	for (var i = 0; i<l; i++){
		if (paramArray[i].value != "" && paramArray[i].value=="all"){
			result.push(paramArray[i]);
		}
	}
	return result;
}

/**
 * 序列化form并转成json
 * @param formid
 * @returns {___anonymous11968_11969}
 */
function serializeExcludeBlank(formid){
	var paramArray = serializeArrayExcludeBlank(formid);
	var serializeObj={}; 
	$.each(paramArray, function(){  
        serializeObj[this.name]=this.value;  
    }); 
	return serializeObj;
}
 
/**
 * @param value：原值
 * @param num:保留位数
 */
function disposeNumber(value,num){
	num = num == null ? 4 : num;
	var returnValue = (value != null && typeof (value) == "number") ? parseFloat(value).toFixed(num) : "";
	return returnValue;
}

/**
 * 替换RestFul中特殊字符为浏览器可识别的字符
 * 解决URL参数中有+, 空格, =, %, &, #等特殊符号问题的解决
 */
function replaceSpecialChar(param) {
	var replace = param.replace("%","%25").replace("+","%2B").replace(" ","%20")
		.replace("&","%3D");
	return replace;
}
function UrlUtil() {
}
/**
 *解决浏览器缓存
 */

UrlUtil.addTimestamp = function(url) {
    var getTimestamp=new Date().getTime();
    if(url.indexOf("?") >- 1){
        url = url + "&timestamp=" + getTimestamp
    }else{
        url = url + "?timestamp=" + getTimestamp
    }
    return url;
};


function checkResult(data, msgId) {
    var res = true;
    //无返回
    if(data==null || data=='' || data == undefined){
        if(msgId){
            layer.tips('错误-HTTP请求无数据返回!', '#'+msgId);
        }else{
            alert('错误-HTTP请求无数据返回!');
        }
        res = false;
    }
    //返回json
    if(data && data.code != "200"){
        if(data.code === 'ES000006'){// 会话超时
            gotoLogon();
        } else if(data.code === 'ES000007'){// 无权限访问
            alert('您无权操作，请联系系统管理员！');
        } else if(data.code == "EB00008"){// 登录页面过期
            alert(data.message, function () {
                gotoLogon();
            });
        } else {// 登录页面过期
            alert(data.message);
        }
        res = false;
    }
    return res;
}

function gotoLogon(){
    if (window != top){
        top.location.href = "/";
    } else {
        location.href = "/";
    }
}

function alert(msg, callback) {
    layer.alert(msg, {
        title: '提示',
        skin : 'layui-layer-molv',
        icon : 5,
        time : 0,
				scrollbar:  false
    }, function (index) {
        if(callback){
            callback();
        }
        layer.close(index);
    });
    return;
}

function AutocompleteUtil() {
}

/**
 * autocomplete 下拉组件
 *
 * @param nameDom 名称的dom节点 eg: #customerName
 * @param url 请求地址
 * @param notice 匹配不到数据显示的文字
 * @param codeDom 需要回传编码的dom节点 eg: #customerCode
 */
AutocompleteUtil.init = function(nameDom, url, notice, codeDom) {

    if(!nameDom || !url || !notice){
        return;
    }

    $(nameDom).autocomplete({
        mustMatch : true,
        minChars  : 0,
        paramName : 'key',
        dataType  : 'json',
        deferRequestBy : 300,
        serviceUrl: url,
        autoSelectFirst: true,
        showNoSuggestionNotice: true,
        noSuggestionNotice: notice,
        max: 10,
        onInvalidateSelection: function(){
        },
        formatResult: function (suggestion, currentValue){
            return suggestion.display;
        },
        transformResult: function(response) {
            return {
                suggestions: $.map(response.result.result, function(dataItem) {
                    return { value: dataItem.value, code:dataItem.key, data: null, display: dataItem.display };
                })
            };
        },
        onSelect: function (suggestion) {
            $(nameDom).val(suggestion.display);
            $(codeDom).val(suggestion.code);
        }
    });

};

function Select2Util() {
}
/**
 *
 * @param dom dom节点
 * @param url 请求url
 * @param placeholder placeholder
 */
Select2Util.singleSelectInit = function (dom, url, placeholder, codeDom) {

    if (!dom || !url) {
        return;
    }

    if(placeholder){
        placeholder = '';
    }

    $(dom).select2({
        ajax: {
            type: 'GET',
            url: url,
            dataType: 'json',
            delay: 250,
            data: function (term, page) {
                return {
                    name: term,
                    pageNum: page,
                    pageSize: 10
                };
            },
            // processResults: function (data, params) {
            //     params.page = params.page || 1;
            //     console.info("params: " + params.page);
            //     return {
            //         results: data.result.list,
            //         pagination: {
            //             more: (params.page * 10) < data.result.total
            //         }
            //     };
            // },
            results: function (data, page) {
                var more = (page * 10) < data.result.total;
                return {results: data.result.list, more: more};
            },
            cache: true
        },
        multiple: false,
		allowClear: true,
        placeholder: placeholder,//默认文字提示
        language: "zh-CN",
        escapeMarkup: function (markup) {
            return markup;
        }, // 自定义格式化防止xss注入
        minimumInputLength: 0,//最少输入多少个字符后开始查询
        formatResult: function formatRepo(repo) {
            // console.info("formatResult: " + JSON.stringify(repo));
            return repo.name;
        }, // 函数用来渲染结果
        formatSelection: function formatRepoSelection(repo) {
            // console.info("formatSelection: " + JSON.stringify(repo));
			$(dom).val(repo.name);
			$(codeDom).val(repo.code);
			var s2Id = "#s2id_"+$(dom).attr("id");
			var value = $(dom).val();
			if (value == null || value == ""){
				$("#s2id_"+$(dom).attr("id")).find(".select2-search-choice-close").css({"display":"none"});
				$(s2Id).removeClass("select2-allowclear");
			}else{
				$("#s2id_"+$(dom).attr("id")).find(".select2-search-choice-close").css({"display":"block"});
				$(s2Id).addClass("select2-allowclear");
			}
            return repo.name;
        }// 函数用于呈现当前的选择
    });

	$("#s2id_"+$(dom).attr("id")).find(".select2-search-choice-close").on("click",function(){
        $(codeDom).val("");
		$(this).css({"display":"none"})
	});
};