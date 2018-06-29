var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var PS = window.parent;
                var NtsDialogBindingHandler = (function () {
                    function NtsDialogBindingHandler() {
                    }
                    NtsDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var message = ko.unwrap(data.message);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsDialog'></div>");
                        if (show == true) {
                            $('body').append($dialog);
                            var dialogbuttons = [];
                            var _loop_1 = function(button) {
                                dialogbuttons.push({
                                    text: ko.unwrap(button.text),
                                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                    click: function () { button.click(bindingContext.$data, $dialog); }
                                });
                            };
                            for (var _i = 0, buttons_1 = buttons; _i < buttons_1.length; _i++) {
                                var button = buttons_1[_i];
                                _loop_1(button);
                            }
                            $dialog.dialog({
                                title: title,
                                modal: modal,
                                closeOnEscape: false,
                                buttons: dialogbuttons,
                                dialogClass: "no-close",
                                open: function () {
                                    $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                    $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                    $('.ui-widget-overlay').last().css('z-index', 120000);
                                },
                                close: function (event) {
                                    bindingContext.$data.option.show(false);
                                }
                            }).text(message);
                        }
                        else {
                            if ($('#ntsDialog').dialog("instance") != null)
                                $('#ntsDialog').dialog("destroy");
                            $('#ntsDialog').remove();
                        }
                    };
                    return NtsDialogBindingHandler;
                }());
                function getCurrentWindow() {
                    var self = nts.uk.ui.windows.getSelf();
                    var dfd = $.Deferred();
                    if (!nts.uk.util.isNullOrUndefined(self)) {
                        dfd.resolve(self);
                    }
                    else {
                        if (nts.uk.util.isInFrame()) {
                            dfd.resolve({ isFrame: true });
                        }
                        nts.uk.deferred.repeat(function (conf) { return conf
                            .task(function () {
                            var def = $.Deferred();
                            self = nts.uk.ui.windows.getSelf();
                            if (!nts.uk.util.isNullOrUndefined(self)) {
                                dfd.resolve(self);
                            }
                            def.resolve(self);
                            return def.promise();
                        })
                            .while(function (c) { return nts.uk.util.isNullOrUndefined(c); })
                            .pause(300); });
                    }
                    return dfd.promise();
                }
                var NtsErrorDialogBindingHandler = (function () {
                    function NtsErrorDialogBindingHandler() {
                    }
                    NtsErrorDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var headers = ko.unwrap(option.headers);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var displayrows = ko.unwrap(option.displayrows);
                        getCurrentWindow().done(function (self) {
                            var idX = "";
                            if (self.isFrame) {
                                idX = nts.uk.util.randomId();
                                self = PS.window.parent.nts.uk.ui.windows.getSelf();
                                PS = PS.window.parent;
                            }
                            else {
                                idX = self.id;
                            }
                            var id = 'ntsErrorDialog_' + idX;
                            var $dialog = $("<div>", { "id": id, "class": "ntsErrorDialog" });
                            if (self.isRoot) {
                                PS.$('body').append($dialog);
                            }
                            else {
                                var temp = self;
                                while (!nts.uk.util.isNullOrUndefined(temp)) {
                                    if (temp.isRoot) {
                                        $(temp.globalContext.document.getElementsByTagName("body")).append($dialog);
                                        temp = null;
                                    }
                                    else {
                                        temp = temp.parent;
                                    }
                                }
                            }
                            var dialogbuttons = [];
                            var _loop_2 = function(button) {
                                dialogbuttons.push({
                                    text: ko.unwrap(button.text),
                                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                    click: function () { button.click(bindingContext.$data, $dialog); }
                                });
                            };
                            for (var _i = 0, buttons_2 = buttons; _i < buttons_2.length; _i++) {
                                var button = buttons_2[_i];
                                _loop_2(button);
                            }
                            $dialog.data("winid", idX);
                            var dialogWidth = 40 + 35 + 17;
                            headers.forEach(function (header, index) {
                                if (ko.unwrap(header.visible)) {
                                    if (typeof ko.unwrap(header.width) === "number") {
                                        dialogWidth += ko.unwrap(header.width);
                                    }
                                    else {
                                        dialogWidth += 200;
                                    }
                                }
                            });
                            $dialog.dialog({
                                title: title,
                                modal: modal,
                                autoOpen: false,
                                closeOnEscape: false,
                                width: dialogWidth,
                                maxHeight: 500,
                                buttons: dialogbuttons,
                                dialogClass: "no-close",
                                open: function () {
                                    $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                    $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                    $('.ui-widget-overlay').last().css('z-index', nts.uk.ui.dialog.getMaxZIndex());
                                    var offsetDraged = $dialog.data("stopdrop");
                                    if (nts.uk.util.isNullOrUndefined(offsetDraged)) {
                                        $dialog.ntsDialogEx("centerUp", self);
                                    }
                                    else {
                                        $dialog.closest(".ui-dialog").offset(offsetDraged);
                                    }
                                },
                                close: function (event) {
                                    bindingContext.$data.option().show(false);
                                }
                            }).dialogPositionControl();
                            $dialog.on("dialogopen", function () {
                                var maxrowsHeight = 0;
                                var index = 0;
                                $(this).find("table tbody tr").each(function () {
                                    if (index < displayrows) {
                                        index++;
                                        maxrowsHeight += $(this).height();
                                    }
                                });
                                maxrowsHeight = maxrowsHeight + 33 + 20 + 20 + 55 + 4 + $(this).find("table thead").height();
                                if (maxrowsHeight > $dialog.dialog("option", "maxHeight")) {
                                    maxrowsHeight = $dialog.dialog("option", "maxHeight");
                                }
                                $dialog.dialog("option", "height", maxrowsHeight);
                            });
                            PS.$("body").data(self.id, $dialog);
                            $(element).data("dialogX", $dialog);
                            if (self.isRoot) {
                                $("body").bind("dialogclosed", function (evt, eData) {
                                    var $cDialog = $("#ntsErrorDialog_" + eData.dialogId);
                                    if (!nts.uk.util.isNullOrEmpty($cDialog)) {
                                        $("body").data(eData.dialogId).dialog("destroy");
                                        $cDialog.remove();
                                    }
                                });
                            }
                        });
                    };
                    NtsErrorDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var errors = ko.unwrap(data.errors);
                        var headers = ko.unwrap(option.headers);
                        var displayrows = ko.unwrap(option.displayrows);
                        var autoclose = ko.unwrap(option.autoclose);
                        var show = ko.unwrap(option.show);
                        var isNotFunctionArea = _.isEmpty($('#functions-area')) && _.isEmpty($('#functions-area-bottom'));
                        var isFrame = nts.uk.util.isInFrame();
                        if (isNotFunctionArea && isFrame) {
                            if (!_.isEmpty(errors)) {
                                var mesArr_1 = [], mesCodeArr_1 = _.map(errors, function (error) { return error.errorCode; });
                                _.forEach(errors, function (error) {
                                    mesArr_1.push(error.message);
                                    mesCodeArr_1.push(error.errorCode);
                                });
                                var totalMes = _.join(_.uniq(mesArr_1), '\n');
                                var totalMesCode = _.join(_.uniq(mesCodeArr_1), ', ');
                                var mainD = PS.window.parent.nts.uk.ui.windows.getSelf();
                                while (!mainD.isRoot) {
                                    mainD = mainD.parent;
                                }
                                nts.uk.ui.errors.clearAll();
                                mainD.globalContext.nts.uk.ui.dialog.error({ message: totalMes, messageId: totalMesCode }).then(function () {
                                });
                            }
                            return;
                        }
                        getCurrentWindow().done(function (self) {
                            var $dialog = $(element).data("dialogX");
                            if (show == true) {
                                var $errorboard = $("<div id='error-board'></div>");
                                var $errortable = $("<table></table>");
                                var $header = $("<thead></thead>");
                                var $headerRow_1 = $("<tr></tr>");
                                $headerRow_1.append("<th style='display:none;'></th>");
                                headers.forEach(function (header, index) {
                                    if (ko.unwrap(header.visible)) {
                                        var $headerElement = $("<th>" + ko.unwrap(header.text) + "</th>").width(ko.unwrap(header.width));
                                        $headerRow_1.append($headerElement);
                                    }
                                });
                                $header.append($headerRow_1);
                                $errortable.append($header);
                                var $body = $("<tbody></tbody>");
                                errors.forEach(function (error, index) {
                                    var $row = $("<tr></tr>");
                                    $row.click(function () {
                                        error.$control.eq(0).exposeOnTabPanel().focus();
                                        var $dialogContainer = $dialog.closest("[role='dialog']");
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
                                        var currentControlOffset = error.$control.offset();
                                        var doc = document.documentElement;
                                        var scrollX = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
                                        var scrollY = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0);
                                        var top = additonalTop + currentControlOffset.top + error.$control.outerHeight() - scrollY;
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
                                    $row.append("<td style='display:none;'>" + (index + 1) + "</td>");
                                    headers.forEach(function (header) {
                                        if (ko.unwrap(header.visible))
                                            if (error.hasOwnProperty(ko.unwrap(header.name))) {
                                                var $column = $("<td>" + error[ko.unwrap(header.name)] + "</td>");
                                                $row.append($column);
                                            }
                                    });
                                    $body.append($row);
                                });
                                $errortable.append($body);
                                $errorboard.append($errortable);
                                var $message = $("<div></div>");
                                $dialog.html("");
                                $dialog.append($errorboard).append($message);
                                $dialog.dialog("open");
                            }
                            else {
                                $dialog.dialog("close");
                            }
                        });
                    };
                    return NtsErrorDialogBindingHandler;
                }());
                ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
                ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=dialog-ko-ext.js.map