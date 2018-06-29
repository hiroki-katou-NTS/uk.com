var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var PS = window.parent;
            var dialog;
            (function (dialog) {
                function getMaxZIndex() {
                    var overlayElements = PS.$(".ui-widget-overlay");
                    var max = 12000;
                    if (overlayElements.length > 0) {
                        var zIndexs = _.map(overlayElements, function (element) { return parseInt($(element).css("z-index")); });
                        var temp = _.max(zIndexs);
                        max = temp > max ? temp : max;
                    }
                    return max;
                }
                dialog.getMaxZIndex = getMaxZIndex;
                function createNoticeDialog(message, buttons, header) {
                    var $control = $('<div/>').addClass('control').addClass("pre");
                    var text;
                    if (typeof message === "object") {
                        if (message.message) {
                            text = message.message;
                            if (message.messageId) {
                                $control.append(message.messageId);
                            }
                        }
                        else {
                            text = nts.uk.resource.getMessage(message.messageId, message.messageParams);
                            $control.append(message.messageId);
                        }
                    }
                    else {
                        text = message;
                    }
                    text = text.replace(/\n/g, '<br />');
                    var $this = PS.$('<div/>').addClass('notice-dialog')
                        .append($('<div/>').addClass('text').append(text))
                        .append($control)
                        .appendTo('body')
                        .dialog({
                        dialogClass: "no-close-btn",
                        width: 'auto',
                        modal: true,
                        minWidth: 300,
                        maxWidth: 800,
                        maxHeight: 400,
                        closeOnEscape: false,
                        buttons: buttons,
                        open: function () {
                            $(this).closest('.ui-dialog').css('z-index', getMaxZIndex() + 2);
                            $('.ui-widget-overlay').last().css('z-index', getMaxZIndex() + 1);
                            var $buttons = $(this).parent().find('.ui-dialog-buttonset > button')
                                .removeClass('ui-button ui-corner-all ui-widget');
                            if ($buttons.filter(".proceed").length === 1) {
                                $buttons.filter(".proceed").focus();
                            }
                            else if ($buttons.filter(".danger").length === 1) {
                                $buttons.not(".danger").focus();
                            }
                            else {
                                $buttons.eq(0).focus();
                            }
                            if (header && header.icon) {
                                var $headerContainer = $("<div'></div>").addClass("ui-dialog-titlebar-container");
                                $headerContainer.append($("<img>").attr("src", header.icon).addClass("ui-dialog-titlebar-icon"));
                                $headerContainer.append($(this).parent().find(".ui-dialog-title"));
                                $(this).parent().children(".ui-dialog-titlebar").prepend($headerContainer);
                            }
                        },
                        close: function (event) {
                            PS.$(this).dialog('destroy');
                            PS.$(event.target).remove();
                        }
                    });
                    $this.dialogPositionControl();
                    if (header && header.text) {
                        $this.dialog("option", "title", header.text);
                    }
                    return $this;
                }
                function version() {
                    var versinText = "AP version: ...";
                    var $this = PS.$('<div/>').addClass('version-dialog')
                        .append($('<div/>').addClass('text').append(versinText))
                        .appendTo('body')
                        .dialog({});
                }
                dialog.version = version;
                function simpleDialog(message, option) {
                    var then = $.noop;
                    var $dialog = PS.$('<div/>').hide();
                    $(function () {
                        $dialog.appendTo('body').dialog({
                            autoOpen: false
                        });
                    });
                    setTimeout(function () {
                        var $this = createNoticeDialog(message, [{
                                text: ui_1.toBeResource.close,
                                "class": "large",
                                click: function () {
                                    $this.dialog('close');
                                    then();
                                }
                            }], { text: option.title });
                    }, 0);
                    return {
                        then: function (callback) {
                            then = callback;
                        }
                    };
                }
                function info(message) {
                    return simpleDialog(message, { title: ui_1.toBeResource.info });
                }
                dialog.info = info;
                function caution(message) {
                    return simpleDialog(message, { title: ui_1.toBeResource.warn });
                }
                dialog.caution = caution;
                function error(message) {
                    return simpleDialog(message, { title: ui_1.toBeResource.error });
                }
                dialog.error = error;
                function alertError(message) {
                    return error(message);
                }
                dialog.alertError = alertError;
                function alert(message) {
                    return error(message);
                }
                dialog.alert = alert;
                ;
                function confirm(message, option) {
                    var handleYes = $.noop;
                    var handleNo = $.noop;
                    var handleCancel = $.noop;
                    var handleThen = $.noop;
                    var hasNoButton = true;
                    var hasCancelButton = false;
                    var option = option || {
                        buttonStyles: { yes: "danger" }
                    };
                    var handlers = {
                        ifYes: function (handler) {
                            handleYes = handler;
                            return handlers;
                        },
                        ifCancel: function (handler) {
                            hasNoButton = false;
                            hasCancelButton = true;
                            handleCancel = handler;
                            return handlers;
                        },
                        ifNo: function (handler) {
                            hasNoButton = true;
                            handleNo = handler;
                            return handlers;
                        },
                        then: function (handler) {
                            handleThen = handler;
                            return handlers;
                        }
                    };
                    setTimeout(function () {
                        var buttons = [];
                        buttons.push({
                            text: ui_1.toBeResource.yes,
                            "class": "yes large " + (option.buttonStyles.yes || ""),
                            click: function () {
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                            }
                        });
                        if (hasNoButton) {
                            buttons.push({
                                text: ui_1.toBeResource.no,
                                "class": "no large " + (option.buttonStyles.no || ""),
                                click: function () {
                                    $this.dialog('close');
                                    handleNo();
                                    handleThen();
                                }
                            });
                        }
                        if (hasCancelButton) {
                            buttons.push({
                                text: ui_1.toBeResource.cancel,
                                "class": "cancel large",
                                click: function () {
                                    $this.dialog('close');
                                    handleCancel();
                                    handleThen();
                                }
                            });
                        }
                        var $this = createNoticeDialog(message, buttons, { text: ui_1.toBeResource.confirm });
                    });
                    return handlers;
                }
                dialog.confirm = confirm;
                ;
                function confirmDanger(message) {
                    return confirm(message);
                }
                dialog.confirmDanger = confirmDanger;
                function confirmProceed(message) {
                    return confirm(message, { buttonStyles: { yes: "proceed" } });
                }
                dialog.confirmProceed = confirmProceed;
                function addError(errorBody, error, idx) {
                    var row = $("<tr/>");
                    row.append("<td style='display: none;'>" + idx + "/td><td>" + error["message"] + "</td><td>" + error["messageId"] + "</td>");
                    var nameId = error["supplements"]["NameID"];
                    if (!uk.util.isNullOrUndefined(nameId)) {
                        row.click(function (evt, ui) {
                            var element = $("body").find('[NameID="' + nameId + '"]');
                            var tab = element.closest("[role='tabpanel']");
                            while (!uk.util.isNullOrEmpty(tab)) {
                                var tabId = tab.attr("id");
                                tab.siblings(":first").children("li[aria-controls='" + tabId + "']").children("a").click();
                                tab = tab.parent().closest("[role='tabpanel']");
                            }
                            element.focus();
                            var $dialogContainer = errorBody.closest(".bundled-errors-alert").closest("[role='dialog']");
                            var $self = nts.uk.ui.windows.getSelf();
                            var additonalTop = 0;
                            var additonalLeft = 0;
                            if (!$self.isRoot) {
                                var $currentDialog = $self.$dialog.closest("[role='dialog']");
                                var $currentHeadBar = $currentDialog.find(".ui-dialog-titlebar");
                                var currentDialogOffset = $currentDialog.offset();
                                additonalTop = currentDialogOffset.top + $currentHeadBar.height();
                                additonalLeft = currentDialogOffset.left;
                            }
                            var currentControlOffset = element.offset();
                            var doc = document.documentElement;
                            var scrollX = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
                            var scrollY = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0);
                            var top = additonalTop + currentControlOffset.top + element.outerHeight() - scrollY;
                            var left = additonalLeft + currentControlOffset.left - scrollX;
                            var $errorDialogOffset = $dialogContainer.offset();
                            var maxLeft = $errorDialogOffset.left + $dialogContainer.width();
                            var maxTop = $errorDialogOffset.top + $dialogContainer.height();
                            if ($errorDialogOffset.top < top && top < maxTop) {
                                $dialogContainer.css("top", top + 15);
                            }
                            if (($errorDialogOffset.left < left && left < maxLeft)) {
                                $dialogContainer.css("left", left);
                            }
                        });
                    }
                    row.appendTo(errorBody);
                }
                function getRoot() {
                    var self = nts.uk.ui.windows.getSelf();
                    while (!self.isRoot) {
                        self = self.parent;
                    }
                    return $(self.globalContext.document).find("body");
                }
                function bundledErrors(errors) {
                    var then = $.noop;
                    var id = uk.util.randomId();
                    var container = $("<div id='" + id + "' class='bundled-errors-alert'/>"), functionArea = $("<div id='functions-area-bottom'/>"), errorBoard = $("<div id='error-board'>    <table> <thead> <tr>    <th style='width: auto;'>"
                        + ui_1.toBeResource.errorContent + "</th><th style='display: none;'/>    <th style='width: 150px;'>"
                        + ui_1.toBeResource.errorCode + "</th>   </tr>   </thead>    <tbody/>    </table> </div>"), closeButton = $("<button class='ntsButton ntsClose large'/>");
                    var errorBody = errorBoard.find("tbody");
                    if ($.isArray(errors["errors"])) {
                        _.forEach(errors["errors"], function (error, idx) {
                            addError(errorBody, error, idx + 1);
                        });
                    }
                    else {
                        return alertError(errors);
                    }
                    var dialogInfo = nts.uk.ui.windows.getSelf();
                    closeButton.appendTo(functionArea);
                    functionArea.appendTo(container);
                    errorBoard.appendTo(container);
                    container.appendTo(getRoot());
                    setTimeout(function () {
                        container.dialog({
                            title: ui_1.toBeResource.errorList,
                            dialogClass: "no-close-btn",
                            modal: false,
                            resizable: false,
                            width: 450,
                            maxHeight: 500,
                            closeOnEscape: false,
                            open: function () {
                                errorBoard.css({ "overflow": "auto", "max-height": "300px", "margin-bottom": "65px" });
                                functionArea.css({ "left": "0px" });
                                closeButton.text(ui_1.toBeResource.close).click(function (evt) {
                                    container.dialog("destroy");
                                    container.remove();
                                    then();
                                });
                                container.ntsDialogEx("centerUp", dialogInfo);
                            },
                            close: function (event) {
                            }
                        }).dialogPositionControl();
                    }, 0);
                    if (!dialogInfo.isRoot) {
                        var normalClose = dialogInfo.onClosedHandler;
                        var onCloseAuto = function () {
                            normalClose();
                            if (container.dialog("isOpen")) {
                                container.dialog("close");
                            }
                        };
                        dialogInfo.onClosedHandler = onCloseAuto;
                    }
                    return {
                        then: function (callback) {
                            then = callback;
                        }
                    };
                }
                dialog.bundledErrors = bundledErrors;
                ;
            })(dialog = ui_1.dialog || (ui_1.dialog = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=dialog.js.map