var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var q;
                    (function (q) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(data) {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.personalUPItems = ko.observableArray([]);
                                    self.systemVariableItems = ko.observableArray([]);
                                    self.currentCodeList = ko.observableArray([]);
                                    self.formulaContent = ko.observable(data.formulaContent);
                                    if (self.formulaContent().indexOf('計算式＠') !== -1) {
                                        self.extractOtherFormulaContents(data.itemsBag);
                                    }
                                    else {
                                        self.replaceCodesToNames(data.itemsBag);
                                        self.buildListItemModel(data.itemsBag);
                                        self.bindGridListItem();
                                        self.replaceSystemVariableToValue();
                                    }
                                    self.itemsBag = data.itemsBag;
                                    self.result = ko.observable('');
                                }
                                ScreenModel.prototype.isDuplicated = function (itemName) {
                                    var self = this;
                                    var foundItem = _.find(self.items(), function (item) {
                                        return item.name == itemName;
                                    });
                                    return foundItem !== undefined;
                                };
                                ScreenModel.prototype.replaceCodesToNames = function (itemsBag) {
                                    var self = this;
                                    var replacedContent = self.formulaContent();
                                    _.forEach(itemsBag, function (item) {
                                        if (replacedContent.indexOf(item.code) !== -1) {
                                            replacedContent = replacedContent.replace(item.code, item.name);
                                        }
                                    });
                                    self.formulaContent(replacedContent);
                                };
                                ScreenModel.prototype.replaceSystemVariableToValue = function () {
                                    var self = this;
                                    var replacedContent = self.formulaContent();
                                    _.forEach(self.systemVariableItems(), function (item) {
                                        if (replacedContent.indexOf(item.code) !== -1) {
                                            replacedContent = replacedContent.replace(item.code, item.value);
                                        }
                                    });
                                    self.formulaContent(replacedContent);
                                };
                                ScreenModel.prototype.extractOtherFormulaContents = function (itemsBag) {
                                    var self = this;
                                    var replacedValue = self.formulaContent();
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('計算式＠') !== -1 && self.formulaContent().indexOf(item.name) !== -1) {
                                            var itemCodeSplited = item.code.split('＠');
                                            q.service.findLastestFormulaManual(itemCodeSplited[1])
                                                .done(function (formulaManual) {
                                                replacedValue = replacedValue.replace(new RegExp(item.name, 'g'), "（" + formulaManual.formulaContent + "）");
                                                self.formulaContent(replacedValue);
                                                self.replaceCodesToNames(itemsBag);
                                                self.buildListItemModel(itemsBag);
                                                self.bindGridListItem();
                                                self.replaceSystemVariableToValue();
                                            })
                                                .fail(function () {
                                                replacedValue = replacedValue.replace(new RegExp(item.name, 'g'), "（ ）");
                                                self.formulaContent(replacedValue);
                                                self.replaceCodesToNames(itemsBag);
                                                self.buildListItemModel(itemsBag);
                                                self.bindGridListItem();
                                                self.replaceSystemVariableToValue();
                                            });
                                        }
                                    });
                                };
                                ScreenModel.prototype.buildListItemModel = function (itemsBag) {
                                    var self = this;
                                    self.personalUPItems([]);
                                    self.systemVariableItems([]);
                                    self.items([]);
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('関数') === -1 && self.formulaContent().indexOf(item.name) !== -1 && !self.isDuplicated(item.name)) {
                                            if (item.name.indexOf('個人単価＠') !== -1) {
                                                self.personalUPItems.push(new ItemModel(item.name, 0));
                                            }
                                            else if (item.name.indexOf('変数＠') !== -1) {
                                                self.systemVariableItems.push(new ItemModel(item.name, item.value));
                                            }
                                            else {
                                                self.items.push(new ItemModel(item.name, 0));
                                            }
                                        }
                                    });
                                };
                                ScreenModel.prototype.bindGridListItem = function () {
                                    var self = this;
                                    $("#lstItemValue").igGrid({
                                        primaryKey: "code",
                                        columns: [
                                            { headerText: "計算式の項目名", key: "code", dataType: "string", width: '150px' },
                                            { headerText: "値", key: "value", dataType: "number", width: '150px', format: '0.00' },
                                        ],
                                        dataSource: self.items(),
                                        height: "200px",
                                        width: "330px",
                                        features: [
                                            {
                                                name: "Updating",
                                                enableAddRow: false,
                                                editMode: "row",
                                                enableDeleteRow: false,
                                                columnSettings: [
                                                    { columnKey: "code", editorOptions: { type: "string", disabled: true } }
                                                ],
                                                editCellEnding: function (evt, ui) {
                                                    var foundItem = _.find(self.items(), function (item) { return item.code == ui.rowID; });
                                                    (foundItem.code !== ui.value) ? foundItem.value = ui.value : foundItem.code = ui.value;
                                                }
                                            }]
                                    });
                                    $("[aria-describedby='lstItemValue_code']").css({ "backgroundColor": "#CFF1A5" });
                                };
                                ScreenModel.prototype.replaceFunctionNameToCode = function (targetContent) {
                                    var self = this;
                                    var replaceValue = targetContent;
                                    _.forEach(self.itemsBag, function (item) {
                                        if (replaceValue.indexOf(item.name) !== -1) {
                                            replaceValue = replaceValue.replace(new RegExp(item.name, 'g'), item.code);
                                        }
                                    });
                                    return replaceValue;
                                };
                                ScreenModel.prototype.replaceJPCharToEN = function (targetContent) {
                                    var lstSpecialChar = [
                                        { jp: '＠', en: '@' },
                                        { jp: '×', en: '*' },
                                        { jp: '÷', en: '/' },
                                        { jp: '＾', en: '^' },
                                        { jp: '（', en: '(' },
                                        { jp: '）', en: ')' },
                                        { jp: '＜', en: '<' },
                                        { jp: '＞', en: '>' },
                                        { jp: '≦', en: '<=' },
                                        { jp: '≧', en: '>=' },
                                        { jp: '≠', en: '!=' }
                                    ];
                                    var replaceValue = targetContent;
                                    _.forEach(lstSpecialChar, function (char) {
                                        if (replaceValue.indexOf(char.jp) !== -1) {
                                            replaceValue = replaceValue.replace(new RegExp(char.jp, 'g'), char.en);
                                        }
                                    });
                                    return replaceValue;
                                };
                                ScreenModel.prototype.calculationTrial = function () {
                                    var self = this;
                                    var replacedValue = self.formulaContent();
                                    _.forEach(self.items(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    _.forEach(self.personalUPItems(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    replacedValue = replacedValue.replace(new RegExp(' ', 'g'), '');
                                    replacedValue = self.replaceFunctionNameToCode(replacedValue);
                                    replacedValue = self.replaceJPCharToEN(replacedValue);
                                    self.result('...');
                                    q.service.trialCalculate(replacedValue).done(function (calculatorDto) {
                                        self.result(calculatorDto.result);
                                    });
                                };
                                ScreenModel.prototype.close = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = q.viewmodel || (q.viewmodel = {}));
                        var ItemModel = (function () {
                            function ItemModel(code, value) {
                                this.code = code;
                                this.value = value;
                            }
                            return ItemModel;
                        }());
                    })(q = qmm017.q || (qmm017.q = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm017.q.vm.js.map