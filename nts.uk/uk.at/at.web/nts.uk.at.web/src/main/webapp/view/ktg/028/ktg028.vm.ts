module nts.uk.at.view.ktg028.viewmodel {
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    export class ScreenModel {
        texteditorA3_2: any;
        texteditorA4_2: any;
        texteditorA5_4: any;
        items_A2: KnockoutObservableArray<any>;
        columns_A2: KnockoutObservableArray<any>;
        currentCode_A2: KnockoutObservable<any>;
        items_A7: KnockoutObservableArray<any>;
        columns_A7: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList_A7: KnockoutObservableArray<any>;
        isEnable: KnockoutObservable<boolean>;
        isCreated: KnockoutObservable<boolean>;
        index: KnockoutObservable<number>;
        allData: Array<any>;

        constructor() {
            let self = this;
            self.allData = [];
            self.isEnable = ko.observable(true);
            self.isCreated = ko.observable(false);
            self.index = ko.observable(0);
            self.items_A2 = ko.observableArray([]);
            self.currentCode_A2 = ko.observable('');
            self.columns_A2 = ko.observableArray([
                { headerText: '', key: 'serialNumber', width: 40 },
                { headerText: nts.uk.resource.getText('KTG028_6'), key: 'topPageCode', width: 80 },
                { headerText: nts.uk.resource.getText('KTG028_7'), key: 'topPageName', width: 150 }
            ]);
            self.items_A7 = ko.observableArray([]);
            self.currentCodeList_A7 = ko.observableArray([]);
            self.columns_A7 = ko.observableArray([
                { headerText: '', key: 'value', width: 100, hidden: true },
                { headerText: '', key: 'name', width: 300 }
            ]);
            self.texteditorA3_2 = {
                value: ko.observable(''),
                constraint: 'TopPagePartCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "43px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditorA4_2 = {
                value: ko.observable(''),
                constraint: 'TopPagePartName',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "210px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditorA5_4 = {
                value: ko.observable(''),
                constraint: 'Height',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "20px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });
            self.currentCode_A2.subscribe(function(newValue) {
                nts.uk.ui.errors.clearAll();
                let currentItem = _.find(self.items_A2(), { 'topPageCode': newValue });
                if (newValue) {
                    self.texteditorA3_2.enable(false);
                    self.isCreated(false);
                    self.index(_.findIndex(self.items_A2(), ['topPageCode', newValue]));
                    self.texteditorA3_2.value(currentItem.topPageCode);
                    self.texteditorA4_2.value(currentItem.topPageName);
                    self.texteditorA5_4.value(currentItem.height());
                    self.currentCodeList_A7([]);
                    self.currentCodeList_A7(currentItem.listType());
                    $("#name").focus();
                } else {
                    self.isCreated(true);
                    self.texteditorA3_2.enable(true);
                    $("#code").focus();
                }
            });

        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.initData();
            dfd.resolve();
            return dfd.promise();
        }
        initData(): void {
            let self = this;
            let listWidgets = __viewContext.enums.WidgetDisplayItemType;
            self.items_A7(listWidgets);
            self.findAll().done(()=>{
                if (self.items_A2().length > 0) {
                    self.currentCode_A2(self.items_A2()[0].topPageCode);
                }
            });
        }
        findAll(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.findAll().done((data: any) => {
                self.allData = data;
                self.items_A2([]);
                _.forEach(data, (element, index) => {
                    self.items_A2.push(new ItemA2(index, element.topPagePartID, element.topPageCode, element.topPageName
                        , element.width, element.height, _.map(_.filter(element.displayItemTypes, ['notUseAtr', 1]), 'displayItemType')));
                });
                dfd.resolve();
            });

            return dfd.promise();
        }
        /**
    * clean form
    */
        cleanForm(): void {
            var self = this;
            nts.uk.ui.errors.clearAll();
            self.texteditorA3_2.enable(true);
            self.texteditorA3_2.value("");
            self.texteditorA4_2.value("");
            self.currentCode_A2("");
            self.texteditorA5_4.value("");
            self.currentCodeList_A7([]);
            $("#code").focus();
        }
        saveData(): void {
            let self = this;
            $("#code").trigger("validate");
            $("#name").trigger("validate");
            $("#height").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.invisible();
                let optionalWidget = _.find(self.allData, ['topPageCode', self.currentCode_A2()]);
                let displayItemTypes: Array<number> = [];
                let values = _.map(self.items_A7(), 'value');
                _.forEach(values, (x => {
                    let selectedList = _.map(self.currentCodeList_A7(), x => parseInt(x));
                    if (_.includes(selectedList, x)) {
                        displayItemTypes.push({
                            'displayItemType': x,
                            'notUseAtr': 1
                        });
                    } else {
                        displayItemTypes.push({
                            'displayItemType': x,
                            'notUseAtr': 0
                        });
                    }
                }));
                if (optionalWidget) {
                    let data: any = {};
                    data.topPagePartID = optionalWidget.topPagePartID;
                    data.topPageCode = optionalWidget.topPageCode;
                    data.topPageName = self.texteditorA4_2.value();
                    data.width = 6;
                    data.height = parseInt(self.texteditorA5_4.value());
                    data.displayItemTypes = displayItemTypes;
                    service.update(data).done(function() {
                        self.isCreated(false);
                        self.findAll().done(function() {
                            self.currentCode_A2(data.topPageCode);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();
                        $("#name").focus();
                    });
                } else {
                    let data: any = {};
                    data.topPageCode = self.texteditorA3_2.value();
                    data.topPageName = self.texteditorA4_2.value();
                    data.width = 6;
                    data.height = parseInt(self.texteditorA5_4.value());
                    data.displayItemTypes = displayItemTypes;
                    service.add(data).done(function() {
                        self.isCreated(false);
                        self.findAll().done(function() {
                            self.currentCode_A2(data.topPageCode);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15')).then(() => {
                            $("#name").focus();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => {
                            $("#code").focus();
                        });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            }
        }
        removeData(): any {
            let self = this;
            let optionalWidget = _.find(self.allData, ['topPageCode', self.currentCode_A2()]);
            let data = {
                topPagePartID: optionalWidget.topPagePartID,
                displayItemTypes: optionalWidget.displayItemTypes
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                nts.uk.ui.block.grayout();
                service.remove(data).done(function() {
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_16')).then(function() {
                        self.findAll().done(function() {
                            if (self.items_A2().length == 0) {
                                self.cleanForm();
                            } else if (self.index() == self.items_A2().length) {
                                self.currentCode_A2(self.items_A2()[self.index() - 1].topPageCode);
                            } else {
                                self.currentCode_A2(self.items_A2()[self.index()].topPageCode);
                            }
                        });
                    });
                }).fail(function(error) {
                    self.isCreated(false);
                    nts.uk.ui.dialog.alertError(error.messageId);
                });
            }).then(function() {
                nts.uk.ui.block.clear();
            });
        }
    class ItemA2 {
        serialNumber: string;
        topPagePartID: string;
        topPageCode: string;
        topPageName: string;
        width: KnockoutObservable<number>;
        height: KnockoutObservable<number>;
        listType: KnockoutObservableArray<any>;
        constructor(index: string, topPagePartID: string, topPageCode: string, topPageName: string, width: number, height: number, listType: Array<any>) {
            this.serialNumber = index;
            this.topPagePartID = topPagePartID;
            this.topPageCode = topPageCode;
            this.topPageName = topPageName;
            this.width = ko.observable(width);
            this.height = ko.observable(height);
            this.listType = ko.observableArray(listType);
        }
    }
}