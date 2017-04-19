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
                                    self.currentCodeList = ko.observableArray([]);
                                    self.formulaContent = ko.observable(data.formulaContent);
                                    self.extractOtherFormulaContents(data.itemsBag)
                                        .done(function () {
                                        self.replaceCodesToNames(data.itemsBag);
                                        self.buildListItemModel(data.itemsBag);
                                        self.bindGridListItem();
                                        self.calculator = new Calculator();
                                    });
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
                                ScreenModel.prototype.extractOtherFormulaContents = function (itemsBag) {
                                    var self = this;
                                    var dfdExtraction = $.Deferred();
                                    var replacedValue = self.formulaContent();
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('計算式＠') !== -1 && self.formulaContent().indexOf(item.name) !== -1) {
                                            var itemCodeSplited = item.code.split('＠');
                                            q.service.findLastestFormulaManual(itemCodeSplited[1])
                                                .done(function (formulaManual) {
                                                replacedValue = replacedValue.replace(new RegExp(item.name, 'g'), "（" + formulaManual.formulaContent + "）");
                                                self.formulaContent(replacedValue);
                                                dfdExtraction.resolve();
                                            })
                                                .fail(function () {
                                                dfdExtraction.reject();
                                            });
                                        }
                                    });
                                    return dfdExtraction.promise();
                                };
                                ScreenModel.prototype.buildListItemModel = function (itemsBag) {
                                    var self = this;
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('関数') === -1 && self.formulaContent().indexOf(item.name) !== -1 && !self.isDuplicated(item.name)) {
                                            if (item.name.indexOf('個人単価＠') !== -1) {
                                                self.personalUPItems.push(new ItemModel(item.name, 0));
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
                                            { headerText: "値", key: "value", dataType: "number", width: '150px' },
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
                                                    { columnKey: "code", editorOptions: { type: "string", disabled: true } },
                                                ],
                                                editCellEnding: function (evt, ui) {
                                                    var foundItem = _.find(self.items(), function (item) { return item.code == ui.rowID; });
                                                    (foundItem.code !== ui.value) ? foundItem.value = ui.value : foundItem.code = ui.value;
                                                }
                                            }]
                                    });
                                    $("[aria-describedby='lstItemValue_code']").css({ "backgroundColor": "#CFF1A5" });
                                };
                                ScreenModel.prototype.calculationTrial = function () {
                                    var self = this;
                                    var replacedValue = self.formulaContent() + '+ 0';
                                    _.forEach(self.items(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    _.forEach(self.personalUPItems(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    var contentPieces = replacedValue.split(/[\+|\-|\×|\÷|\＾]/);
                                    var listTreeObject = [];
                                    for (var i = 0; i < contentPieces.length - 1; i++) {
                                        listTreeObject.push(nts.uk.util.createTreeFromString(contentPieces[i], "（", "）", ",", [])[0]);
                                    }
                                    ;
                                    var listOperator = [];
                                    var toCharContent = replacedValue.split('');
                                    _.forEach(toCharContent, function (char) {
                                        if (char === '+' || char === '-' || char === '×' || char === '÷' || char === '＾') {
                                            listOperator.push(char);
                                        }
                                    });
                                };
                                ScreenModel.prototype.calculateTreeObject = function (treeObject) {
                                    var self = this;
                                    if (treeObject.value === '関数＠条件式') {
                                    }
                                    else if (treeObject.value === '関数＠かつ') {
                                    }
                                    else if (treeObject.value === '関数＠または') {
                                    }
                                    else if (treeObject.value === '関数＠四捨五入') {
                                    }
                                    else if (treeObject.value === '関数＠切り捨て') {
                                    }
                                    else if (treeObject.value === '関数＠切り上げ') {
                                    }
                                    else if (treeObject.value === '関数＠最大値') {
                                    }
                                    else if (treeObject.value === '関数＠最小値') {
                                    }
                                    else if (treeObject.value === '関数＠家族人数') {
                                    }
                                    else if (treeObject.value === '関数＠年月加算') {
                                    }
                                    else if (treeObject.value === '関数＠年抽出') {
                                    }
                                    else if (treeObject.value === '関数＠月抽出') {
                                    }
                                };
                                ScreenModel.prototype.compareValues = function (firstValue, secondValue, comparator) {
                                    if (comparator === '＜') {
                                        return firstValue < secondValue;
                                    }
                                    else if (comparator === '＞') {
                                        return firstValue > secondValue;
                                    }
                                    else if (comparator === '≦') {
                                        return firstValue <= secondValue;
                                    }
                                    else if (comparator === '≧') {
                                        return firstValue >= secondValue;
                                    }
                                    else if (comparator === '＝') {
                                        return firstValue === secondValue;
                                    }
                                    else if (comparator === '≠') {
                                        return firstValue !== secondValue;
                                    }
                                };
                                ScreenModel.prototype.operatorHandler = function (firstValue, secondValue, operator) {
                                    if (operator === '+') {
                                        return firstValue + secondValue;
                                    }
                                    else if (operator === '-') {
                                        return firstValue - secondValue;
                                    }
                                    else if (operator === '×') {
                                        return firstValue * secondValue;
                                    }
                                    else if (operator === '÷') {
                                        return firstValue / secondValue;
                                    }
                                    else if (operator === '＾') {
                                        return Math.pow(firstValue, secondValue);
                                    }
                                };
                                ScreenModel.prototype.close = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = q.viewmodel || (q.viewmodel = {}));
                        var Calculator = (function () {
                            function Calculator() {
                            }
                            Calculator.prototype.calculateConditionExpression = function (condition, result1, result2) {
                                if (condition) {
                                    return result1;
                                }
                                else {
                                    return result2;
                                }
                            };
                            Calculator.prototype.calculateAndExpression = function (lstCondition) {
                                _.forEach(lstCondition, function (condition) {
                                    if (!condition || condition !== 'true') {
                                        return false;
                                    }
                                });
                                return true;
                            };
                            Calculator.prototype.calculateOrExpression = function (lstCondition) {
                                _.forEach(lstCondition, function (condition) {
                                    if (condition || condition === 'true') {
                                        return true;
                                    }
                                });
                                return false;
                            };
                            Calculator.prototype.calculateCeil = function (value) {
                                return _.ceil(value);
                            };
                            Calculator.prototype.calculateMax = function (lstValue) {
                                return _.max(lstValue);
                            };
                            Calculator.prototype.calculateMin = function (lstValue) {
                                return _.min(lstValue);
                            };
                            Calculator.prototype.calculateNumberOfFamily = function (minAge, maxAge) {
                                return 1;
                            };
                            Calculator.prototype.calculateAddMonth = function (yearMonth, month) {
                                return moment(yearMonth, "YYYY/MM").add(month, 'months').format("YYYY/MM");
                            };
                            Calculator.prototype.calculateExportYear = function (yearMonth) {
                                return moment(yearMonth, "YYYY/MM").year();
                            };
                            Calculator.prototype.calculateExportMonth = function (yearMonth) {
                                return moment(yearMonth, "YYYY/MM").month() + 1;
                            };
                            return Calculator;
                        }());
                        q.Calculator = Calculator;
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
