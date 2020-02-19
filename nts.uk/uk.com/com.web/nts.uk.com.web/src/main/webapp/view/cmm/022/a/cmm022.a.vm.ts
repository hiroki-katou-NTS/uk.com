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
            selectedCommonMasterItemId: KnockoutObservable<String> = ko.observable();
            constructor() {
                let self = this;

                self.commonMasterId.subscribe((id) => {

                    self.selectedCommonMaster(_.filter(self.listMaster(), ['commonMasterId', id])[0]);

                    if (!id) {
                        return;
                    }

                    block.grayout();

                    service.getItems(id).done((data: Array<IMasterItem>) => {

                        self.commonMasterItems(data);
                        if (data.length) {
                            self.selectedCommonMasterItemId(data[0].commonMasterItemId);
                        }

                    }).fail(function(res) {

                        block.clear();
                        alert(res);

                    }).always(() => {

                        nts.uk.ui.errors.clearAll();
                        block.clear();
                        dfd.resolve();

                    });

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

                    self.listMaster(data);
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
                setShared('listMasterToB', self.listMaster());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {

                });
            }

            dialogC() {
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
}