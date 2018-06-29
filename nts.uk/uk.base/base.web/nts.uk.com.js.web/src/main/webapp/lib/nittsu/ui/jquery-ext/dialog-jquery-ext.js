var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsDialogEx;
                (function (ntsDialogEx) {
                    $.fn.ntsDialogEx = function (action, winContainer) {
                        var $dialog = $(this);
                        switch (action) {
                            case 'centerUp':
                                centerUp($dialog, winContainer);
                            default:
                                break;
                        }
                    };
                    function centerUp($dialog, winContainer) {
                        var top = 0, left = 0;
                        var dialog = $dialog.closest("div[role='dialog']");
                        dialog.addClass("disappear");
                        if (!winContainer.isRoot) {
                            var offset = winContainer.$dialog.closest("div[role='dialog']").offset();
                            console.log(dialog.offset());
                            top += offset.top;
                            left += offset.left;
                        }
                        setTimeout(function () {
                            var isFrame = nts.uk.util.isInFrame();
                            var dialogM = winContainer.isRoot ? isFrame ? window.parent.window.parent.$("body") : $("body")
                                : winContainer.$dialog.closest("div[role='dialog']");
                            var topDiff = (dialogM.innerHeight() - dialog.innerHeight()) / 2;
                            var leftDiff = (dialogM.innerWidth() - dialog.innerWidth()) / 2;
                            if (topDiff > 0) {
                                top += topDiff;
                            }
                            if (leftDiff > 0) {
                                left += leftDiff;
                            }
                            dialog.css({ top: top, left: left });
                            dialog.removeClass("disappear");
                        }, 33);
                    }
                })(ntsDialogEx || (ntsDialogEx = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=dialog-jquery-ext.js.map