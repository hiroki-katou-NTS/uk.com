module qpp008.g.viewmodel {
    export class ScreenModel {
        detailDifferentList: Array<DetailsEmployeer>;
        selectedDetailDiff: KnockoutObservable<number>;
        firstLoad: KnockoutObservable<boolean>;
        /*checkBox*/
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        itemCategoryChecked: KnockoutObservableArray<ItemModel>;
        selectedItemCategoryCode: KnockoutObservableArray<number>;

        confirmedSqueezingSwitch: KnockoutObservableArray<ItemModel>;
        selectedconfirmedCode: KnockoutObservable<number>;

        detailsEmployerList: KnockoutObservableArray<ItemModel>;

        constructor() {
            let self = this;
            self.detailDifferentList = new Array();
            self.selectedDetailDiff = ko.observable(0);
            self.initIggrid(self.detailDifferentList);
            self.itemCategoryChecked = ko.observableArray([
                new ItemModel(0, '支給'),
                new ItemModel(1, '控除'),
                new ItemModel(2, '記事'),
            ]);
            self.selectedItemCategoryCode = ko.observableArray([0, 1, 2])

            self.confirmedSqueezingSwitch = ko.observableArray([
                new ItemModel(0, 'すべて'),
                new ItemModel(1, '未確認'),
                new ItemModel(2, '確認済み'),
            ]);
            self.selectedconfirmedCode = ko.observable(0);

            /*checkBox*/
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);



        }

        initIggrid(data: Array<DetailsEmployeer>) {
            let self = this;
            /*iggrid*/
            self.firstLoad = ko.observable(true);
            let _isDataBound = false;
            let _checkAll = false;
            let _checkAllValue = false;
            // event
            $("#grid-comparing-different").on("iggriddatarendered", function(evt: JQueryEventObject, ui: any) {
                _.forEach(ui.owner.dataSource.dataView(), function(item, index) {
                    if (item["difference"] < 0) {
                        let cell1 = $("#grid-comparing-different").igGrid("cellById", item["id"], "difference");
                        $(cell1).addClass('red-color-diff').attr('data-class', 'red-color-diff');
                        $(window).on('resize', () => { $(cell1).addClass('red-color-diff'); });
                    } else if (item["difference"] > 0) {
                        let cell1 = $("#grid-comparing-different").igGrid("cellById", item["id"], "difference");
                        $(cell1).addClass('blue-color-diff').attr('data-class', 'blue-color-diff');
                        $(window).on('resize', () => { $(cell1).addClass('blue-color-diff'); });
                    }
                });
            });

            let processingYMEarlier = "2017/02";
            let processingYMLater = "2017/03";
            $("#grid-comparing-different").igGrid({
                primaryKey: "id",
                dataSource: data,
                width: "100%",
                autoGenerateColumns: false,
                responseDataKey: "id",
                columns: [
                    { headerText: "", key: "id", dataType: "number", hidden: true },
                    { headerText: "社員コード", key: "employeeCode", dataType: "string", width: "10%" },
                    { headerText: "氏名", key: "employeeName", dataType: "string", width: "10%" },
                    { headerText: "区分", key: "categoryAtr", dataType: "string", width: "4%" },
                    { headerText: "項目名", key: "itemName", dataType: "string", width: "10%" },
                    { headerText: processingYMEarlier, key: "comparisonDate1", dataType: "number", format: "currency", width: "8%" },
                    { headerText: processingYMLater, key: "comparisonDate2", dataType: "number", format: "currency", width: "8%" },
                    { headerText: "差額", key: "difference", dataType: "number", format: "currency", width: "8%" },
                    {
                        headerText: "差異理由",
                        key: "reasonDifference",
                        dataType: "string",
                        width: "12%",
                        template: "{{if ${difference} !== 0}}<input type='text' data-value = ${id} value='${reasonDifference}' class='reasonDifference-text' />{{/if}}"
                    },
                    { headerText: "登録状況（" + processingYMEarlier + "）", key: "registrationStatus1", dataType: "string", width: "12%" },
                    { headerText: "登録状況（" + processingYMLater + "）", key: "registrationStatus2", dataType: "string", width: "12%" },
                    {
                        headerText: "確認済",
                        key: "confirmedStatus",
                        dataType: "bool",
                        width: "3%",
                        template: "{{if ${difference} !== 0}}<input type='checkbox' {{if ${confirmedStatus} === true}} checked='checked'{{/if}} class='confirmedStatus-checkBox'/>{{/if}}"
                    }
                ],
                features:
                [
                    {
                        name: "Paging",
                        type: "local",
                        pageIndexChanged: function(evt: any, ui: any) {

                        },
                        pageSize: 10
                    },
                    {
                        name: "Updating",
                        editMode: "none",
                        enableAddRow: false,
                        enableDeleteRow: false,
                        columnSettings: [
                            {
                                columnKey: "id",
                                readOnly: true,
                            },
                            {
                                columnKey: "employeeCode",
                                readOnly: true
                            },
                            {
                                columnKey: "employeeName",
                                readOnly: true
                            },
                            {
                                columnKey: "categoryAtr",
                                readOnly: true
                            },
                            {
                                columnKey: "itemName",
                                readOnly: true
                            },
                            {
                                columnKey: "comparisonDate1",
                                readOnly: true
                            },
                            {
                                columnKey: "comparisonDate2",
                                readOnly: true
                            },
                            {
                                columnKey: "difference",
                                readOnly: true
                            },
                            {
                                columnKey: "reasonDifference"
                            },
                            {
                                columnKey: "registrationStatus1",
                                readOnly: true
                            },
                            {
                                columnKey: "registrationStatus2",
                                readOnly: true
                            },
                            {
                                columnKey: "confirmedStatus",
                                readOnly: true
                            },
                        ]
                    },
                ]
            });
            $("#grid-comparing-different").on("change", ".reasonDifference-text", function(evt: any) {
                let $element = $(this);
                let selectedValue = $element.val();
                let selectId = $element.attr('data-value');
                self.updateValueResionDiffChange(Number(selectId), selectedValue);
                return;
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let processingYMEarlier = 201702;
            let processingYMLater = 201703;
            let dfd = $.Deferred();
            self.detailDifferentList = new Array();
            service.getDetailDifferentials(processingYMEarlier, processingYMLater).done(function(data: any) {
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

        updateValueResionDiffChange(id: number, newValue: string) {
            let self = this;
            _.forEach(self.detailDifferentList, function(item) {
                if (item.id === id) {
                    item.reasonDifference = newValue
                    return true;
                }
                return false;
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
        id: number;
        employeeCode: string;
        employeeName: string;
        categoryAtr: string;
        itemName: string;
        itemCode: string;
        comparisonDate1: number;
        comparisonDate2: number;
        difference: number;
        reasonDifference: string;
        registrationStatus1: string;
        registrationStatus2: string;
        confirmedStatus: boolean;
        constructor(data: any, id: number) {
            let self = this;
            if (data) {
                self.id = id;
                self.employeeCode = data.employeeCode;
                self.employeeName = data.employeeName;
                self.categoryAtr = data.categoryAtr;
                self.itemName = data.itemName;
                self.itemCode = data.itemCode;
                self.comparisonDate1 = data.comparisonValue1;
                self.comparisonDate2 = data.comparisonValue2;
                self.difference = data.valueDifference;
                self.reasonDifference = data.reasonDifference;
                self.registrationStatus1 = data.registrationStatus1;
                self.registrationStatus2 = data.registrationStatus2;
                self.confirmedStatus = data.confirmedStatus;

            }
        }
    }
}