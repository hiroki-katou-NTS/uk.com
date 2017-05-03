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
                            class ScreenModel {
                                constructor(data) {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.personalUPItems = ko.observableArray([]);
                                    self.currentCodeList = ko.observableArray([]);
                                    self.formulaContent = data.formulaContent;
                                    self.buildListItemModel(data.itemsBag);
                                    self.bindGridListItem();
                                    self.calculator = new Calculator();
                                }
                                isDuplicated(itemName) {
                                    var self = this;
                                    let foundItem = _.find(self.items(), function (item) {
                                        return item.name == itemName;
                                    });
                                    return foundItem !== undefined;
                                }
                                buildListItemModel(itemsBag) {
                                    var self = this;
                                    _.forEach(itemsBag, function (item) {
                                        if (item.name.indexOf('関数') === -1 && self.formulaContent.indexOf(item.name) !== -1 && !self.isDuplicated(item.name)) {
                                            if (item.name.indexOf('個人単価＠') !== -1) {
                                                self.personalUPItems.push(new ItemModel(item.name, 0));
                                            }
                                            else {
                                                self.items.push(new ItemModel(item.name, 0));
                                            }
                                        }
                                    });
                                }
                                bindGridListItem() {
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
                                }
                                calculationTrial() {
                                    var self = this;
                                    let replacedValue = self.formulaContent + '+ 0';
                                    _.forEach(self.items(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    _.forEach(self.personalUPItems(), function (item) {
                                        replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                                    });
                                    let contentPieces = replacedValue.split(/[\+|\-|\×|\÷|\＾]/);
                                    let listTreeObject = [];
                                    for (let i = 0; i < contentPieces.length - 1; i++) {
                                        listTreeObject.push(nts.uk.util.createTreeFromString(contentPieces[i], "（", "）", ",", [])[0]);
                                    }
                                    ;
                                    let listOperator = [];
                                    let toCharContent = replacedValue.split('');
                                    _.forEach(toCharContent, function (char) {
                                        if (char === '+' || char === '-' || char === '×' || char === '÷' || char === '＾') {
                                            listOperator.push(char);
                                        }
                                    });
                                }
                                calculateTreeObject(treeObject) {
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
                                }
                                compareValues(firstValue, secondValue, comparator) {
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
                                }
                                operatorHandler(firstValue, secondValue, operator) {
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
                                }
                                close() {
                                    nts.uk.ui.windows.close();
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = q.viewmodel || (q.viewmodel = {}));
                        class Calculator {
                            constructor() {
                            }
                            calculateConditionExpression(condition, result1, result2) {
                                if (condition) {
                                    return result1;
                                }
                                else {
                                    return result2;
                                }
                            }
                            calculateAndExpression(lstCondition) {
                                _.forEach(lstCondition, function (condition) {
                                    if (!condition || condition !== 'true') {
                                        return false;
                                    }
                                });
                                return true;
                            }
                            calculateOrExpression(lstCondition) {
                                _.forEach(lstCondition, function (condition) {
                                    if (condition || condition === 'true') {
                                        return true;
                                    }
                                });
                                return false;
                            }
                            calculateCeil(value) {
                                return _.ceil(value);
                            }
                            calculateMax(lstValue) {
                                return _.max(lstValue);
                            }
                            calculateMin(lstValue) {
                                return _.min(lstValue);
                            }
                            calculateNumberOfFamily(minAge, maxAge) {
                                return 0;
                            }
                            calculateAddMonth(yearMonth, month) {
                                return moment(yearMonth, "YYYY/MM").add(month, 'months').format("YYYY/MM");
                            }
                            calculateExportYear(yearMonth) {
                                return moment(yearMonth, "YYYY/MM").year();
                            }
                            calculateExportMonth(yearMonth) {
                                return moment(yearMonth, "YYYY/MM").month() + 1;
                            }
                        }
                        q.Calculator = Calculator;
                        class ItemModel {
                            constructor(code, value) {
                                this.code = code;
                                this.value = value;
                            }
                        }
                    })(q = qmm017.q || (qmm017.q = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
