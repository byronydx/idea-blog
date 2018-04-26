var xescm = {
    common: {

        queryListWithPage: function (nav, param) {
            CommonClient.post(CommonClient.parseUrl(nav), param, function (result) {

                if (result == undefined || result == null) {
                    alert("HTTP请求无数据返回！");
                } else if (result.code == 200) {// 1:normal
                    reloadGrid(result);// 刷新页面数据
                    laypage({
                        cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
                        pages: result.result.pages, // 总页数
                        skip: true, // 是否开启跳页
                        skin: "molv",
                        groups: 3, // 连续显示分页数
                        curr: result.result.pageNum, // 当前页
                        jump: function (obj, first) { // 触发分页后的回调
                            if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                                queryData(obj.curr);
                            }
                        }
                    });
                } else if (result.code == 403) {
                    alert("没有权限")
                } else {
                    $("#dataTbody").html("");
                }
            });
        },

        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPage: function (nav) {
            nav = replaceSpecialChar(nav);
            //加载页面
            $(".page-content-area").load(CommonClient.parseUrl(nav), function (response, status) {
                if (status == "error") {
                    $(".page-content-area").load("/error/404");
                }

                if (status == "timeout") {
                    $(".page-content-area").load("/error/timeout");
                }

            });
        },
        /**
         * 新增
         * @param {Object} nav  提交url
         */
        addModel: function (nav) {
            //加载新增页面
            xescm.common.loadPage(CommonClient.parseUrl(nav));
        },
        /**
         * 编辑
         * @param {Object} nav  提交url
         */
        editModel: function (nav, id) {
            if (!id) {
                layer.msg('请选择一条记录', {
                    skin: 'layui-layer-molv',
                    time: 1000,
                    icon: 0
                });
            }
            var url = nav + "/" + id;
            xescm.common.loadPage(CommonClient.parseUrl(url));
        },
        /**
         * 删除
         *
         * @param {Object}
         *            nav 提交url
         * @param callback
         *            主函数执行完毕后调用的回调函数名称
         */
        delModel: function (nav, param, messages, successFunction) {
            if (!messages) {
                messages = "确认删除吗？";
            }
            layer.confirm(messages, {
                skin: 'layui-layer-molv',
                icon: 3,
                title: '删除提示',
                scrollbar: false
            }, function (index, layero) {
                layer.close(index);
                CommonClient.post(CommonClient.parseUrl(nav), param, function (result) {
                    if (checkResult(result)) {
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            time: 1000,
                            icon: 1
                        });
                        if (successFunction) {
                            successFunction();
                        }
                    }
                });

            });
        },
        /**
         * 通用提示框
         *
         * @param title 提示语
         * @param {Object}
         *            nav 提交url
         * @param callback
         *            主函数执行完毕后调用的回调函数名称
         */
        remindModel: function (nav, param, title, messages, successFunction) {
            if (!messages) {
                messages = "确认删除吗？";
            }
            if (!title) {
                messages = "提示";
            }
            layer.confirm(messages, {
                skin: 'layui-layer-molv',
                icon: 3,
                title: title,
                scrollbar: false
            }, function (index, layero) {
                layer.close(index);
                CommonClient.post(CommonClient.parseUrl(nav), param, function (result) {
                    if (checkResult(result)) {
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            time: 1000,
                            icon: 1
                        });
                        if (successFunction) {
                            successFunction();
                        }
                    }
                });

            });
        },
        submit: function (submitUrl, param, messages, successFunction) {
            if (!messages) {
                messages = "确认提交吗？";
            }
            layer.confirm(messages, {
                skin: 'layui-layer-molv',
                icon: 3,
                title: '确认操作',
                scrollbar: false
            }, function (index) {
                layer.close(index);
                CommonClient.post(CommonClient.parseUrl(submitUrl), param, function (result) {
                    if (checkResult(result)) {
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            time: 1000,
                            icon: 1
                        });
                        if (successFunction) {
                            successFunction();
                        }
                    }
                });
            }, function (index) {
                layer.close(index);
            });
            return;
        },
        /**
         * 返回按钮
         *
         * @param {Object}
         *            nav 提交url
         */
        goBack: function (nav) {
            xescm.common.loadPage(CommonClient.parseUrl(nav));
        },
        /**
         * 提交表单 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         *
         * @param {Object}
         *            commitUrl 表单提交地址
         * @param {Object}
         *            listUrl 表单提交成功后转向的列表页地址
         */
        commit: function (formId, commitUrl, successFunction) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            $.ajax({
                type: "post",
                url: CommonClient.parseUrl(commitUrl),
                data: data,
                dataType: "json",
                beforeSend: function () {
                    index = layer.load();
                },
                success: function (result) {
                    layer.close(index);
                    if (checkResult(result)) {
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            time: 1000,
                            icon: 1
                        });
                        if (successFunction) {
                            successFunction(result);
                        }
                    }
                },
                error: function (data, errorMsg) {
                    layer.close(index);
                    layer.msg(data.responseText, {
                        time: 1000,
                        icon: 2
                    });
                }
            });
        },
        /**
         * 提交表单,没有layer提醒。 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         *
         * @param {Object}
         *            commitUrl 表单提交地址
         * @param {Object}
         *            listUrl 表单提交成功后转向的列表页地址
         */
        commitNoRemind: function (formId, commitUrl, successFunction) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            $.ajax({
                type: "post",
                url: CommonClient.parseUrl(commitUrl),
                data: data,
                dataType: "json",
                beforeSend: function () {
                    index = layer.load();
                },
                success: function (result) {
                    layer.close(index);
                    if (checkResult(result)) {
                        /* layer.msg(result.message, {
                         skin : 'layui-layer-molv',
                         time:1000,
                         icon : 1
                         });*/
                        if (successFunction) {
                            successFunction(result);
                        }
                    }
                },
                error: function (data, errorMsg) {
                    layer.close(index);
                    layer.msg(data.responseText, {
                        time: 1000,
                        icon: 2
                    });
                }
            });
        }
    }
};

function submitAndQuery(url) {
    CommonClient.post(CommonClient.parseUrl(url), null, function (result) {

        if (checkResult(result)) {
            layer.msg(result.message, {
                skin: 'layui-layer-molv',
                time: 1000,
                icon: 1
            });
            queryData();
        }
    });
}