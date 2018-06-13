module nts.uk.at.view.kwr006.c {

    import service = nts.uk.at.view.kwr001.c.service;
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            currentCodeList: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;


            allMainDom: KnockoutObservable<any>;
            currentCodeListSwap: KnockoutObservableArray<ItemModel>;
            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;

            enableBtnDel: KnockoutObservable<boolean>;
            enableCodeC3_2: KnockoutObservable<boolean>;
            selectedRuleCode: any;
            //a8-2
            itemListConditionSet: KnockoutObservableArray<any>;
            selectedCodeA8_2: KnockoutObservable<number>;
            enableConfigErrCode: KnockoutObservable<boolean>;


            constructor() {
                var self = this;
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");

                self.allMainDom = ko.observable();
                self.currentCodeListSwap = ko.observableArray([]);
                self.currentCodeListSwap.subscribe(function(value) {
                    console.log(value);
                })
                self.outputItemList = ko.observableArray([]);
                self.currentCodeList = ko.observable();

                self.enableBtnDel = ko.observable(false);
                self.enableCodeC3_2 = ko.observable(false);
                self.selectedRuleCode = ko.observable(0);
                self.currentCodeList.subscribe(function(value) {
                    let codeChoose = _.find(self.allMainDom(), function(o: any) {
                        return value == o.itemCode;
                    });
                    if (!_.isUndefined(codeChoose) && !_.isNull(codeChoose)) {
                        nts.uk.ui.errors.clearAll();
                        self.C3_2_value(codeChoose.itemCode);
                        self.C3_3_value(codeChoose.itemName);
                        self.getOutputItemDailyWorkSchedule(_.find(self.allMainDom(), function(o: any) {
                            return codeChoose.itemCode == o.itemCode;
                        }));
                        self.selectedRuleCode(codeChoose.workTypeNameDisplay);
                        self.enableBtnDel(true);
                        self.enableCodeC3_2(false);
                    } else {
                        self.C3_3_value('');
                        self.C3_2_value('');
                        self.getOutputItemDailyWorkSchedule([]);
                        self.enableBtnDel(false);
                        self.enableCodeC3_2(true);
                    }
                });
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR006_40"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR006_41"), prop: 'name', width: 180 }
                ]);
                self.itemListConditionSet = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KWR006_56")),
                    new BoxModel(1, nts.uk.resource.getText("KWR006_57"))
                ]);
                self.items = ko.observableArray([]);
                self.selectedCodeA8_2 = ko.observable(0);
                self.enableConfigErrCode = ko.observable(true);
                self.selectedCodeA8_2.subscribe(function(value) {
                    if (value == 0) {
                        self.enableConfigErrCode(true);
                    } else {
                        self.enableConfigErrCode(false);
                    }
                })
                self.selectedCodeA8_2.valueHasMutated();
            }
            //chua dinh nghia
            openScreenD(): void {
                nts.uk.ui.windows.sub.modal('/view/kwr/006/d/index.xhtml');
            }
            //chua dinh nghia
            private getOutputItemDailyWorkSchedule(data: any): void {
            }
            //chua dinh nghia
            private newMode(): void {

            }
            //chua dinh nghia
            private saveData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                return dfd.promise();
            }
            //chua dinh nghia
            private removeData(): void {

            }
            //chua dinh nghia
            public closeScreenC(): void {
                nts.uk.ui.windows.close();
            }


            //chua dinh nghia
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                dfd.resolve();

                return dfd.promise();
            }

        }


        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }

    }
}