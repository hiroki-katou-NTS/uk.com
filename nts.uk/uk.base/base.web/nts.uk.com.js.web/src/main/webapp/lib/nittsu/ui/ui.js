var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var toBeResource;
            (function (toBeResource) {
                toBeResource.yes = "はい";
                toBeResource.no = "いいえ";
                toBeResource.cancel = "キャンセル";
                toBeResource.close = "閉じる";
                toBeResource.info = "情報";
                toBeResource.warn = "警告";
                toBeResource.error = "エラー";
                toBeResource.confirm = "確認";
                toBeResource.unset = "未設定";
                toBeResource.errorContent = "エラー内容";
                toBeResource.errorCode = "エラーコード";
                toBeResource.errorList = "エラー一覧";
                toBeResource.plzWait = "お待ちください";
                toBeResource.targetNotFound = "対象データがありません";
                toBeResource.clear = "解除";
                toBeResource.searchBox = "検索テキストボックス";
            })(toBeResource = ui.toBeResource || (ui.toBeResource = {}));
            function localize(textId) {
                return textId;
            }
            ui.localize = localize;
            ui.confirmSave = function (dirtyChecker) {
                var frame = ui.windows.getSelf();
                if (frame.$dialog === undefined || frame.$dialog === null) {
                    confirmSaveWindow(dirtyChecker);
                }
                else {
                    confirmSaveDialog(dirtyChecker, frame.$dialog);
                }
            };
            function confirmSaveWindow(dirtyChecker) {
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        return "ban co muon save hok?";
                    }
                };
                confirmSaveEnable(beforeunloadHandler);
            }
            function confirmSaveDialog(dirtyChecker, dialog) {
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        e.preventDefault();
                        nts.uk.ui.dialog.confirm("Are you sure you want to leave the page?")
                            .ifYes(function () {
                            dirtyChecker.reset();
                            dialog.dialog("close");
                        }).ifNo(function () {
                        });
                    }
                };
                confirmSaveEnableDialog(beforeunloadHandler, dialog);
            }
            function confirmSaveEnableDialog(beforeunloadHandler, dialog) {
                dialog.on("dialogbeforeclose", beforeunloadHandler);
            }
            ui.confirmSaveEnableDialog = confirmSaveEnableDialog;
            ;
            function confirmSaveDisableDialog(dialog) {
                dialog.on("dialogbeforeclose", function () { });
            }
            ui.confirmSaveDisableDialog = confirmSaveDisableDialog;
            ;
            function confirmSaveEnable(beforeunloadHandler) {
                $(window).bind('beforeunload', beforeunloadHandler);
            }
            ui.confirmSaveEnable = confirmSaveEnable;
            ;
            function confirmSaveDisable() {
                $(window).unbind('beforeunload');
            }
            ui.confirmSaveDisable = confirmSaveDisable;
            ;
            var block;
            (function (block) {
                function invisible() {
                    var rect = calcRect();
                    $.blockUI({
                        message: null,
                        overlayCSS: { opacity: 0 },
                        css: {
                            width: rect.width,
                            left: rect.left
                        }
                    });
                }
                block.invisible = invisible;
                function grayout() {
                    var rect = calcRect();
                    $.blockUI({
                        message: '<div class="block-ui-message">' + toBeResource.plzWait + '</div>',
                        fadeIn: 200,
                        css: {
                            width: rect.width,
                            left: rect.left
                        }
                    });
                }
                block.grayout = grayout;
                function clear() {
                    $.unblockUI({
                        fadeOut: 200
                    });
                }
                block.clear = clear;
                function calcRect() {
                    var width = 220;
                    var left = ($(window).width() - width) / 2;
                    return {
                        width: width,
                        left: left
                    };
                }
            })(block = ui.block || (ui.block = {}));
            var DirtyChecker = (function () {
                function DirtyChecker(targetViewModelObservable) {
                    this.targetViewModel = targetViewModelObservable;
                    this.initialState = this.getCurrentState();
                }
                DirtyChecker.prototype.getCurrentState = function () {
                    return ko.toJSON(this.targetViewModel());
                };
                DirtyChecker.prototype.reset = function () {
                    this.initialState = this.getCurrentState();
                };
                DirtyChecker.prototype.isDirty = function () {
                    return this.initialState !== this.getCurrentState();
                };
                return DirtyChecker;
            }());
            ui.DirtyChecker = DirtyChecker;
            var smallExtensions;
            (function (smallExtensions) {
                $(function () {
                    $(document).on('mouseenter', '.limited-label', function (e) {
                        var $label = $(e.target);
                        if (isOverflow($label)) {
                            var $view_1 = $('<div />').addClass('limited-label-view')
                                .text($label.text() || $label.val())
                                .appendTo('body')
                                .position({
                                my: 'left top',
                                at: 'left bottom',
                                of: $label,
                                collision: 'flip'
                            });
                            $label.bind('mouseleave.limitedlabel', function () {
                                $label.unbind('mouseleave.limitedlabel');
                                $view_1.remove();
                            });
                        }
                    });
                });
                function isOverflow($label) {
                    if ($label[0].nodeName === "INPUT"
                        && (window.navigator.userAgent.indexOf("MSIE") > -1
                            || !!window.navigator.userAgent.match(/trident/i))) {
                        var $div = $("<div/>").appendTo($(document.body));
                        var style = $label[0].currentStyle;
                        if (style) {
                            for (var p in style) {
                                $div[0].style[p] = style[p];
                            }
                        }
                        $div.html($label.val());
                        var width = $div.outerWidth();
                        var scrollWidth = $div[0].scrollWidth;
                        $div.remove();
                        return width < scrollWidth;
                    }
                    return $label.outerWidth() < $label[0].scrollWidth;
                }
            })(smallExtensions || (smallExtensions = {}));
            var keyboardStream;
            (function (keyboardStream) {
                var _lastKey = {
                    code: undefined,
                    time: undefined
                };
                function lastKey() {
                    return {
                        code: _lastKey.code,
                        time: _lastKey.time
                    };
                }
                keyboardStream.lastKey = lastKey;
                function wasKeyDown(keyCode, millisToExpire) {
                    return _lastKey.code === keyCode
                        && (+new Date() - +_lastKey.time <= millisToExpire);
                }
                keyboardStream.wasKeyDown = wasKeyDown;
                $(function () {
                    $(window).on("keydown", function (e) {
                        _lastKey.code = e.keyCode;
                        _lastKey.time = new Date();
                    });
                });
            })(keyboardStream = ui.keyboardStream || (ui.keyboardStream = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ui.js.map