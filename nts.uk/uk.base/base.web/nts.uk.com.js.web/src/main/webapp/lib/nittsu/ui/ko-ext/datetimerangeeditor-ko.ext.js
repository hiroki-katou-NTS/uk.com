var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsDateTimePairRangeEditorBindingHandler = (function () {
                    function NtsDateTimePairRangeEditorBindingHandler() {
                    }
                    NtsDateTimePairRangeEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor(), $element = $(element);
                        var editable = nts.uk.util.isNullOrUndefined(data.editable) ? false : ko.unwrap(data.editable);
                        var construct = new EditorConstructSite($element);
                        construct.build(data, allBindingsAccessor, viewModel, bindingContext);
                        $element.data("construct", construct);
                        return { 'controlsDescendantBindings': true };
                    };
                    NtsDateTimePairRangeEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor(), $element = $(element), construct = $element.data("construct");
                        ko.bindingHandlers["ntsDateTimePairEditor"].update(construct.$start[0], function () {
                            return construct.createStartData(data);
                        }, allBindingsAccessor, viewModel, bindingContext);
                        ko.bindingHandlers["ntsDateTimePairEditor"].update(construct.$end[0], function () {
                            return construct.createEndData(data);
                        }, allBindingsAccessor, viewModel, bindingContext);
                    };
                    return NtsDateTimePairRangeEditorBindingHandler;
                }());
                var EditorConstructSite = (function () {
                    function EditorConstructSite($root) {
                        this.format = "YYYY/MM/DD H:mm:ss";
                        this.$root = $root;
                    }
                    EditorConstructSite.prototype.validate = function (start, end) {
                        var self = this;
                        self.$root.find(".datetimepairrange-container").ntsError('clearKibanError');
                        var mStart = moment(start, self.format);
                        var mEnd = moment(end, self.format);
                        if (mEnd.isBefore(mStart)) {
                            self.$root.find(".datetimepairrange-container")
                                .ntsError('set', "end is smaller than start value", 'Not defined code', false);
                            return false;
                        }
                        return true;
                    };
                    EditorConstructSite.prototype.initVal = function (allBindData) {
                        var self = this;
                        self.startValueBind = ko.observable();
                        self.endValueBind = ko.observable();
                        self.startValue = ko.computed({
                            read: function () {
                                var value = allBindData.value().start();
                                self.startValueBind(value);
                                return value;
                            }, write: function (val) {
                                var endVal = self.endValueBind();
                                if (self.validate(val, endVal)) {
                                    allBindData.value().start(val);
                                    allBindData.value.valueHasMutated();
                                }
                            },
                            owner: this
                        });
                        self.endValue = ko.computed({
                            read: function () {
                                var value = allBindData.value().end();
                                self.endValueBind(value);
                                return value;
                            }, write: function (val) {
                                var startVal = self.startValueBind();
                                if (self.validate(startVal, val)) {
                                    allBindData.value().end(val);
                                    allBindData.value.valueHasMutated();
                                }
                            },
                            owner: this
                        });
                        self.startValueBind.subscribe(function (v) {
                            self.startValue(v);
                        });
                        self.endValueBind.subscribe(function (v) {
                            self.endValue(v);
                        });
                    };
                    EditorConstructSite.prototype.build = function (allBindData, allBindingsAccessor, viewModel, bindingContext) {
                        var self = this;
                        self.initVal(allBindData);
                        var $container = $("<div>", { "class": "datetimerange-editor datetimepairrange-container" });
                        this.$start = $("<div>", { "class": "start-datetime-editor datetimepairrange-component" }),
                            $seperator = $("<div>", { "class": "seperator datetimepairrange-component" }),
                            this.$end = $("<div>", { "class": "end-datetime-editor datetimepairrange-component" });
                        $container.append(this.$start);
                        $container.append($seperator);
                        $container.append(this.$end);
                        self.$root.addClass("ntsControl");
                        self.$root.append($container);
                        $seperator.append($("<span>", { "class": "seperator-span", text: "~" }));
                        ko.bindingHandlers["ntsDateTimePairEditor"].init(this.$start[0], function () {
                            return self.createStartData(allBindData);
                        }, allBindingsAccessor, viewModel, bindingContext);
                        ko.bindingHandlers["ntsDateTimePairEditor"].init(this.$end[0], function () {
                            return self.createEndData(allBindData);
                        }, allBindingsAccessor, viewModel, bindingContext);
                    };
                    EditorConstructSite.prototype.createStartData = function (allBindData) {
                        var self = this;
                        return { required: allBindData.required,
                            name: allBindData.startName,
                            value: self.startValueBind,
                            enable: allBindData.enable,
                            disabled: allBindData.disabled,
                            startDate: allBindData.startDate,
                            endDate: allBindData.endDate };
                    };
                    EditorConstructSite.prototype.createEndData = function (allBindData) {
                        var self = this;
                        return { required: allBindData.required,
                            name: allBindData.endName,
                            value: self.endValueBind,
                            enable: allBindData.enable,
                            disabled: allBindData.disabled,
                            startDate: allBindData.startDate,
                            endDate: allBindData.endDate };
                    };
                    return EditorConstructSite;
                }());
                ko.bindingHandlers['ntsDateTimePairRangeEditor'] = new NtsDateTimePairRangeEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=datetimerangeeditor-ko.ext.js.map