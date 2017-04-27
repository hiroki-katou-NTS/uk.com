module qpp008.g.viewmodel {
    export class ScreenModel {
        detailDifferentList: Array<DetailsEmployeer>;
        comparativeDateEarlier: string;
        comparativeDateLater: string;
        numberOfPeople: number;
        employeerIDSelection: Array<string>;
        itemCategoryChecked: KnockoutObservableArray<ItemModel>;
        selectedItemCategoryCode: KnockoutObservableArray<number>;

        confirmedSqueezingSwitch: KnockoutObservableArray<ItemModel>;
        selectedconfirmedCode: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self.detailDifferentList = new Array();
            self.comparativeDateEarlier = "2017/02";
            self.comparativeDateLater = "2017/03";
            self.numberOfPeople = 2;
            self.employeerIDSelection = new Array("999000000000000000000000000000000001", "999000000000000000000000000000000002");

            self.itemCategoryChecked = ko.observableArray([
                new ItemModel(0, '支給'),
                new ItemModel(1, '控除'),
                new ItemModel(3, '記事'),
            ]);
            self.selectedItemCategoryCode = ko.observableArray([0, 1, 3])

            self.confirmedSqueezingSwitch = ko.observableArray([
                new ItemModel(0, 'すべて'),
                new ItemModel(1, '未確認'),
                new ItemModel(2, '確認済み'),
            ]);
            self.selectedconfirmedCode = ko.observable(0);
            self.initIggrid(self.detailDifferentList);
        }

        initIggrid(data: Array<DetailsEmployeer>) {
            let self = this;
            /*iggrid*/
            let _isDataBound = false;
            let _checkAll = false;
            let _checkAllValue = false;
            // event
            $("#grid-comparing-different").on("iggriddatarendered", function(evt: JQueryEventObject, ui: any) {
                _.forEach(ui.owner.dataSource.dataView(), function(item, index) {
                    if (item["difference"] < 0) {
                        let cell1 = $("#grid-comparing-different").igGrid("cellById", item["selectId"], "difference");
                        $(cell1).addClass('red-color-diff').attr('data-class', 'red-color-diff');
                        $(window).on('resize', () => { $(cell1).addClass('red-color-diff'); });
                    } else if (item["difference"] > 0) {
                        let cell1 = $("#grid-comparing-different").igGrid("cellById", item["selectId"], "difference");
                        $(cell1).addClass('blue-color-diff').attr('data-class', 'blue-color-diff');
                        $(window).on('resize', () => { $(cell1).addClass('blue-color-diff'); });
                    }
                });
            });

            $("#grid-comparing-different").igGrid({
                primaryKey: "selectId",
                dataSource: data,
                width: "100%",
                autoGenerateColumns: false,
                responseDataKey: "selectId",
                columns: [
                    { headerText: "", key: "selectId", dataType: "number", hidden: true },
                    { headerText: "社員コード", key: "employeeCode", dataType: "string", width: "10%" },
                    { headerText: "氏名", key: "employeeName", dataType: "string", width: "10%" },
                    { headerText: "区分", key: "categoryAtrName", dataType: "string", width: "4%" },
                    { headerText: "項目名", key: "itemName", dataType: "string", width: "10%" },
                    {
                        headerText: self.comparativeDateEarlier,
                        key: "comparisonDate1",
                        dataType: "number",
                        format: "{0:d2}",
                        width: "8%",
                        template: "{{if ${comparisonDate1} != -1}}<span>${comparisonDate1}</span>{{/if}}"
                    },
                    {
                        headerText: self.comparativeDateLater,
                        key: "comparisonDate2",
                        dataType: "number",
                        format: "{0:d2}",
                        width: "8%",
                        template: "{{if ${comparisonDate2} != -1}}<span>${comparisonDate2}</span>{{/if}}"
                    },
                    { headerText: "差額", key: "difference", dataType: "number", format: "{0:d2}", width: "8%" },
                    {
                        headerText: "差異理由",
                        key: "reasonDifference",
                        dataType: "string",
                        width: "12%",
                        template: "{{if ${difference} !== 0}}<input type='text' data-value = ${selectId} value='${reasonDifference}' class='reasonDifference-text' />{{/if}}"
                    },
                    { headerText: "登録状況（" + self.comparativeDateEarlier + "）", key: "registrationStatus1", dataType: "string", width: "12%" },
                    { headerText: "登録状況（" + self.comparativeDateLater + "）", key: "registrationStatus2", dataType: "string", width: "12%" },
                    {
                        headerText: "確認済",
                        key: "confirmedStatus",
                        dataType: "bool",
                        width: "3%",
                        template: "{{if ${difference} !== 0}}<input type='checkbox' {{if ${confirmedStatus} === true}} checked='checked'{{/if}} data-value = ${selectId} class='confirmedStatus-checkBox'/>{{/if}}"
                    }
                ],
                features:
                [
                    { name: "Paging", type: "local", pageSize: 10 },
                ]
            });
            $("#grid-comparing-different").on("change", ".reasonDifference-text", function(evt: any) {
                let $element = $(this);
                let selectedValue = $element.val();
                let selectId = $element.attr('data-value');
                self.resionDiffChange(Number(selectId), selectedValue);
            });

            $("#grid-comparing-different").on("change", ".confirmedStatus-checkBox", function(evt: any) {
                let $element = $(this);
                let selectId = $element.attr('data-value');
                let isChecked = $element.is(":checked");
                self.comfirmStatusChange(Number(selectId), isChecked);
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let processingYMEarlier = Number(self.comparativeDateEarlier.trim().replace("/", ""));
            let processingYMLater = Number(self.comparativeDateLater.trim().replace("/", ""));
            let dfd = $.Deferred();
            self.detailDifferentList = new Array();
            service.getDetailDifferentials(processingYMEarlier, processingYMLater, self.employeerIDSelection).done(function(data: any) {
                let mapDetailDiff = _.map(data, function(item, i) {
                    return new DetailsEmployeer(item, i);
                });
                self.detailDifferentList = mapDetailDiff;
                self.initIggrid(self.detailDifferentList);
                dfd.resolve();
            }).fail(function(error: any) {

            });
            return dfd.promise();
        }

        resionDiffChange(selectId: number, newValue: string) {
            let self = this;
            _.forEach(self.detailDifferentList, function(item) {
                if (item.selectId === selectId) {
                    item.reasonDifference = newValue
                    return false;
                }
            });
        }

        comfirmStatusChange(selectId: number, isChecked: boolean) {
            let self = this;
            _.forEach(self.detailDifferentList, function(item) {
                if (item.selectId === selectId) {
                    item.confirmedStatus = isChecked;
                    return false;
                }
            });
        }

        checkedAllComfirmStatus() {
            let self = this;
            _.forEach(self.detailDifferentList, function(item) {
                item.confirmedStatus = true;
            });
            $("#grid-comparing-different").igGrid({ dataSource: self.detailDifferentList });
            $("#grid-comparing-different").igGrid("dataBind");
        }

        unCheckedAllComfirmStatus() {
            let self = this;
            _.forEach(self.detailDifferentList, function(item) {
                item.confirmedStatus = false;
            });
            $("#grid-comparing-different").igGrid({ dataSource: self.detailDifferentList });
            $("#grid-comparing-different").igGrid("dataBind");
        }

        refineComparingDifferrent() {
            let self = this;
            let dataRefine = new Array();
            dataRefine = _.filter(self.detailDifferentList, function(item) {
                if (_.indexOf(self.selectedItemCategoryCode(), item.categoryAtr) != -1) {
                    if (self.selectedconfirmedCode() == 1 && item.confirmedStatus == false && item.difference != 0) {
                        return true;
                    } else if (self.selectedconfirmedCode() == 2 && item.confirmedStatus == true && item.difference != 0) {
                        return true;
                    } else if (self.selectedconfirmedCode() == 0) {
                        return true;
                    }
                    return false
                }
                return false;
            });

            $("#grid-comparing-different").igGrid({ dataSource: dataRefine });
            $("#grid-comparing-different").igGrid("dataBind");
        }

        registerData() {
            let insertUpdate = 0;
            service.insertUpdatePaycompConfirm(insertUpdate).fail(function(error: any){
                
            });
        }

    }

    class ItemModel {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            let self = this;
            self.code = code;
            self.name = name;
        }
    }

    class DetailsEmployeer {
        selectId: number;
        employeeCode: string;
        employeeName: string;
        categoryAtr: number;
        categoryAtrName: string;
        itemName: string;
        itemCode: string;
        comparisonDate1: number;
        comparisonDate2: number;
        difference: number;
        reasonDifference: string;
        registrationStatus1: string;
        registrationStatus2: string;
        confirmedStatus: boolean;
        constructor(data: any, selectId: number) {
            let self = this;
            if (data) {
                self.selectId = selectId;
                self.employeeCode = data.employeeCode;
                self.employeeName = data.employeeName;
                self.categoryAtr = data.categoryAtr;
                self.categoryAtrName = data.categoryAtrName
                self.itemName = data.itemName;
                self.itemCode = data.itemCode;
                self.comparisonDate1 = data.comparisonValue1;
                self.comparisonDate2 = data.comparisonValue2;
                self.difference = data.valueDifference;
                self.reasonDifference = data.reasonDifference;
                self.registrationStatus1 = data.registrationStatus1;
                self.registrationStatus2 = data.registrationStatus2;
                self.confirmedStatus = data.confirmedStatus === 1 ? true : false;
            }
        }
    }
}