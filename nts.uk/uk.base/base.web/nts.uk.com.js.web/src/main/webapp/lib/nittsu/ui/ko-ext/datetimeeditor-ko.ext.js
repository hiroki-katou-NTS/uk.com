var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsDateTimePairEditorBindingHandler = (function () {
                    function NtsDateTimePairEditorBindingHandler() {
                    }
                    NtsDateTimePairEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor(), $element = $(element);
                        var editable = nts.uk.util.isNullOrUndefined(data.editable) ? false : ko.unwrap(data.editable);
                        var construct = new EditorConstructSite($element);
                        construct.build(data, allBindingsAccessor, viewModel, bindingContext);
                        $element.data("construct", construct);
                        return { 'controlsDescendantBindings': true };
                    };
                    NtsDateTimePairEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor(), $element = $(element), timeData = $element.data('timeData'), dateData = $element.data('dateData'), construct = $element.data("construct");
                        ko.bindingHandlers["ntsDatePicker"].update(construct.$date[0], function () {
                            return construct.createDateData(data);
                        }, allBindingsAccessor, viewModel, bindingContext);
                        ko.bindingHandlers["ntsTimeEditor"].update(construct.$time[0], function () {
                            return construct.createTimeData(data);
                        }, allBindingsAccessor, viewModel, bindingContext);
                    };
                    return NtsDateTimePairEditorBindingHandler;
                }());
                var EditorConstructSite = (function () {
                    function EditorConstructSite($root) {
                        this.dateFormat = "YYYY/MM/DD";
                        this.timeFormat = "H:mm:ss";
                        this.timeMode = "time";
                        this.$root = $root;
                    }
                    EditorConstructSite.prototype.build = function (allBindData, allBindingsAccessor, viewModel, bindingContext) {
                        var self = this;
                        self.initVal(allBindData);
                        var $container = $("<div>", { "class": "datetime-editor datetimepair-container" }), $dateContainer = $("<div>", { "class": "date-container component-container" }), $timeContainer = $("<div>", { "class": "time-container component-container" });
                        self.$date = $("<div>", { "class": "date-picker datetimepair-component" });
                        self.$time = $("<input>", { "class": "time-editor datetimepair-component" });
                        $dateContainer.append(self.$date);
                        $timeContainer.append(self.$time);
                        $container.append($dateContainer);
                        $container.append($timeContainer);
                        self.$root.append($container);
                        ko.bindingHandlers["ntsDatePicker"].init(self.$date[0], function () {
                            return self.createDateData(allBindData);
                        }, allBindingsAccessor, viewModel, bindingContext);
                        ko.bindingHandlers["ntsTimeEditor"].init(self.$time[0], function () {
                            return self.createTimeData(allBindData);
                        }, allBindingsAccessor, viewModel, bindingContext);
                    };
                    EditorConstructSite.prototype.initVal = function (allBindData) {
                        var self = this;
                        self.timeValueBind = ko.observable();
                        self.dateValueBind = ko.observable();
                        self.timeValue = ko.computed({
                            read: function () {
                                var value = allBindData.value();
                                if (nts.uk.util.isNullOrEmpty(value)) {
                                    self.timeValueBind("");
                                    return "";
                                }
                                var format = self.dateFormat + " " + self.timeFormat;
                                var timeVal = nts.uk.time.secondsBased.duration.parseString(moment(value, format).format(self.timeFormat)).toValue();
                                self.timeValueBind(timeVal);
                                return timeVal;
                            }, write: function (val) {
                                var dateVal = self.dateValueBind();
                                var timeVal = nts.uk.time.secondsBased.duration.create(val)
                                    .formatById(nts.uk.time.secondsBased.duration.DurationFormatId);
                                allBindData.value(dateVal + " " + timeVal);
                            },
                            owner: this
                        });
                        self.dateValue = ko.computed({
                            read: function () {
                                var value = allBindData.value();
                                if (nts.uk.util.isNullOrEmpty(value)) {
                                    self.dateValueBind("");
                                    return "";
                                }
                                var format = self.dateFormat + " " + self.timeFormat;
                                var val = moment(value, format).format(self.dateFormat);
                                self.dateValueBind(val);
                                return val;
                            }, write: function (val) {
                                var tv = self.timeValueBind();
                                var timeVal = nts.uk.time.secondsBased.duration.create(tv)
                                    .formatById(nts.uk.time.secondsBased.duration.DurationFormatId);
                                allBindData.value(val + " " + timeVal);
                            },
                            owner: this
                        });
                        self.timeValueBind.subscribe(function (v) {
                            self.timeValue(v);
                        });
                        self.dateValueBind.subscribe(function (v) {
                            self.dateValue(v);
                        });
                    };
                    EditorConstructSite.prototype.createDateData = function (allBindData) {
                        var self = this;
                        return { required: allBindData.required,
                            name: allBindData.name,
                            value: self.dateValueBind,
                            dateFormat: self.dateFormat,
                            valueFormat: self.dateFormat,
                            enable: allBindData.enable,
                            disabled: allBindData.disabled,
                            startDate: allBindData.startDate,
                            endDate: allBindData.endDate };
                    };
                    EditorConstructSite.prototype.createTimeData = function (allBindData) {
                        var self = this;
                        return { required: allBindData.required,
                            name: allBindData.name,
                            value: self.timeValueBind,
                            inputFormat: self.timeFormat,
                            mode: self.timeMode,
                            enable: allBindData.enable,
                            disabled: allBindData.disabled,
                            option: { width: "60" } };
                    };
                    return EditorConstructSite;
                }());
                ko.bindingHandlers['ntsDateTimePairEditor'] = new NtsDateTimePairEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=datetimeeditor-ko.ext.js.map