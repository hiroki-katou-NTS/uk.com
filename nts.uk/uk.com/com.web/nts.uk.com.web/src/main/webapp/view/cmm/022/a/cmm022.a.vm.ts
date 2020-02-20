module nts.uk.com.view.cmm022.a {

    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import alert = nts.uk.ui.dialog.alert;

    export module viewmodel {
        export class ScreenModel {
            commonMasters: KnockoutObservableArray<ICommonMaster> = ko.observableArray([]);
            commonMasterId: KnockoutObservable<String> = ko.observable();
            selectedCommonMaster: KnockoutObservable<ICommonMaster> = ko.observable();
            commonMasterItems: KnockoutObservableArray<ICommonMaster> = ko.observableArray([]);
            commonMasterItemId: KnockoutObservable<String> = ko.observable();
            selectedCommonMasterItem: KnockoutObservable<MasterItem> = ko.observable(new MasterItem());
            constructor() {
                let self = this;

                self.commonMasterId.subscribe((id) => {

                    self.selectedCommonMaster(_.filter(self.commonMasters(), ['commonMasterId', id])[0]);

                    if (!id) {
                        return;
                    }

                    block.grayout();

                    service.getItems(id).done((data: Array<IMasterItem>) => {

                        self.commonMasterItems(data);
                        if (data.length) {
                            self.commonMasterItemId(data[0].commonMasterItemId);
                        }

                    }).fail(function(res) {

                        block.clear();
                        alert(res);

                    }).always(() => {

                        nts.uk.ui.errors.clearAll();
                        block.clear();
                    });

                });
                
                
                self.commonMasterItemId.subscribe((id) => {
                    self.selectedCommonMasterItem(_.filter(self.commonMasterItems(), ['commonMasterItemId', id])[0]);
                    if (!id) {
                        return;
                    }
                });
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {

                let self = this, dfd = $.Deferred(),

                    param = { confirmed: false };

                block.grayout();

                service.startPage(param).done((data: Array<ICommonMaster>) => {

                    self.commonMasters(data);
                    if (data.length) {
                        self.commonMasterId(data[0].commonMasterId);
                    }
                }).fail(function(res) {

                    block.clear();
                    alert(res);

                }).always(() => {
                    nts.uk.ui.errors.clearAll();                    block.clear();                    dfd.resolve();
                });

                return dfd.promise();
            }

            newItem() {
                let self = this;
                setShared('listMasterToB', self.commonMasters());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {

                });
            }

            dialogC() {
                let self = this;
                setShared('listMasterToC', self.commonMasters());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/c/index.xhtml').onClosed(function(): any {
                });
            }

        }


    }

    export interface ICommonMaster {
        commonMasterId: string;
        commonMasterCode: string;
        commonMasterName: string;
        commonMasterMemo: string;
    }

    export interface IMasterItem {
        commonMasterItemId: string;
        commonMasterItemCode: string;
        commonMasterItemName: string;
        displayNumber: number;
        usageStartDate: string;
        usageEndDate: string;
        useSetting: Array<string>;
    }
    
    export class MasterItem {
        commonMasterItemId: KnockoutObservable<String> = ko.observable();
        commonMasterItemCode: KnockoutObservable<String> = ko.observable();
        commonMasterItemName: KnockoutObservable<String> = ko.observable();
        displayNumber: KnockoutObservable<number> = ko.observable();
        usageStartDate: KnockoutObservable<String> = ko.observable();
        usageEndDate: KnockoutObservable<String> = ko.observable();
        useSetting: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor(data?: IMasterItem) {
            let self = this;
            if (data) {
                self.commonMasterItemId(data.commonMasterItemId);
                self.commonMasterItemCode(data.commonMasterItemCode);
                self.commonMasterItemName(data.commonMasterItemName);
                self.displayNumber(data.displayNumber);
                self.usageStartDate(data.usageStartDate);
                self.usageEndDate(data.usageEndDate);
                self.useSetting(data.useSetting);
            }
        }
    }
}