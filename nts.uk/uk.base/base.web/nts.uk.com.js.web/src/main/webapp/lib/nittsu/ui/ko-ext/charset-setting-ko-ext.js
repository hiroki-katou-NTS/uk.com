var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var enums;
        (function (enums) {
            enums.NtsCharset = [
                {
                    value: 1,
                    fieldName: "UTF8",
                    localizedName: "UTF-8"
                },
                {
                    value: 2,
                    fieldName: "UTF8_WITH_BOM",
                    localizedName: "UTF-8 BOM"
                },
                {
                    value: 3,
                    fieldName: "SHIFT_JIS",
                    localizedName: "Shift-JIS"
                },
            ];
        })(enums = uk.enums || (uk.enums = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsCharsetSettingBindingHandler = (function () {
                    function NtsCharsetSettingBindingHandler() {
                    }
                    NtsCharsetSettingBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = (data.value !== undefined) ? ko.unwrap(data.value) : null;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.addClass("ntsControl nts-charset-setting");
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex"))) {
                            container.attr("tabindex", "0");
                        }
                        container.keypress(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                container.igCombo("openDropDown");
                                evt.preventDefault();
                            }
                        });
                        container.igCombo({
                            dataSource: nts.uk.enums.NtsCharset,
                            valueKey: "value",
                            textKey: 'localizedName',
                            mode: "dropdown",
                            visibleItemsCount: 5,
                            disabled: !enable,
                            placeHolder: '',
                            tabIndex: -1,
                            enableClearButton: false,
                            initialSelectedItems: [
                                { value: value }
                            ],
                            selectionChanged: function (evt, ui) {
                                if (ui.items.length > 0) {
                                    data.value(ui.items[0].data["value"]);
                                }
                            }
                        });
                    };
                    NtsCharsetSettingBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = (data.value !== undefined) ? ko.unwrap(data.value) : null;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.igCombo("option", "disabled", !enable);
                        if (!nts.uk.util.isNullOrUndefined(value) && container.igCombo("value") != value) {
                            container.igCombo("value", value);
                        }
                    };
                    return NtsCharsetSettingBindingHandler;
                }());
                ko.bindingHandlers['ntsCharsetSetting'] = new NtsCharsetSettingBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=charset-setting-ko-ext.js.map