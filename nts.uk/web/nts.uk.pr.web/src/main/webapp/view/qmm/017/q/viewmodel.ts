module nts.uk.pr.view.qmm017.q {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;
            formulaContent: string;
            calculator: Calculator;

            constructor(data) {
                var self = this;
                self.items = ko.observableArray([]);
                self.currentCodeList = ko.observableArray([]);
                self.formulaContent = data.formulaContent;
                self.buildListItemModel(data.itemsBag);
                self.bindGridListItem();
                self.calculator = new Calculator();
            }

            isDuplicated(itemName) {
                var self = this;
                let foundItem = _.find(self.items(), function(item) {
                    return item.name == itemName;
                });
                return foundItem !== undefined;
            }

            buildListItemModel(itemsBag) {
                var self = this;
                _.forEach(itemsBag, function(item) {
                    if (item.name.indexOf('関数') === -1 && self.formulaContent.indexOf(item.name) !== -1 && !self.isDuplicated(item.name)) {
                        self.items.push(new ItemModel(item.name, 0));
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
                            editCellEnding: function(evt, ui) {
                                var foundItem = _.find(self.items(), function(item) { return item.code == ui.rowID });
                                (foundItem.code !== ui.value) ? foundItem.value = ui.value : foundItem.code = ui.value;
                            }
                        }]
                });
                $("[aria-describedby='lstItemValue_code']").css({ "backgroundColor": "#CFF1A5" });
            }

            calculationTrial() {
                var self = this;
                let replacedValue = self.formulaContent + '+ 0';
                _.forEach(self.items(), function(item) {
                    replacedValue = replacedValue.replace(new RegExp(item.code, 'g'), item.value);
                });
                let contentPieces = replacedValue.split(/[\+|\-|\×|\÷|\＾]/);
                let listTreeObject = [];
                for (let i = 0; i < contentPieces.length - 1; i++) {
                    listTreeObject.push(nts.uk.util.createTreeFromString(contentPieces[i], "（", "）", ",", [])[0]);
                };
                let listOperator = [];
                let toCharContent = replacedValue.split('');
                _.forEach(toCharContent, function(char) {
                    if (char === '+' || char === '-' || char === '×' || char === '÷' || char === '＾') {
                        listOperator.push(char);
                    }
                });
                debugger;
            }

            calculateTreeObject(treeObject) {
                var self = this;
                if (treeObject.value === '関数＠条件式') {
                    _.forEach(treeObject.children, function(child) {
                        if (child.children.length > 0) {
                            child.value = self.calculateTreeObject(child);
                        }
                    });
                    return self.calculator.calculateConditionExpression(
                        self.compareValues(
                            treeObject.children[0],
                            treeObject.children[2],
                            treeObject.children[1]),
                        treeObject.children[3],
                        treeObject.children[4]
                    );
                } else if (treeObject.value === '関数＠かつ') {
                    _.forEach(treeObject.children, function(child) {
                        if (child.children.length > 0) {
                            child.value = self.calculateTreeObject(child);
                        }
                    });
                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                } else if (treeObject.value === '関数＠条件式') {

                }
            }

            compareValues(firstValue, secondValue, comparator) {
                if (comparator === '＜') {
                    return firstValue < secondValue;
                } else if (comparator === '＞') {
                    return firstValue > secondValue;
                } else if (comparator === '≦') {
                    return firstValue <= secondValue;
                } else if (comparator === '≧') {
                    return firstValue >= secondValue;
                } else if (comparator === '＝') {
                    return firstValue === secondValue;
                } else if (comparator === '≠') {
                    return firstValue !== secondValue;
                }
            }

            close() {
                nts.uk.ui.windows.close();
            }

        }
    }

    export class Calculator {
        constructor() {

        }

        calculateConditionExpression(condition, result1, result2) {
            if (condition) {
                return result1;
            } else {
                return result2;
            }
        }

        calculateAndExpression(lstCondition) {
            _.forEach(lstCondition, function(condition) {
                if (!condition) {
                    return false;
                }
            });
            return true;
        }

        calculateOrExpression(lstCondition) {
            _.forEach(lstCondition, function(condition) {
                if (condition) {
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
            return moment("2012/01", "YYYY/MM").year();
        }

        calculateExportMonth(yearMonth) {
            return moment("2012/01", "YYYY/MM").month() + 1;
        }
    }


    class ItemModel {
        code: string;
        value: number;
        constructor(code: string, value: number) {
            this.code = code;
            this.value = value;
        }
    }
}