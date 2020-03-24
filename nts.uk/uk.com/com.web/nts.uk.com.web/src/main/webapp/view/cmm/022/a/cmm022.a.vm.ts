module nts.uk.com.view.cmm022.a {

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alert = nts.uk.ui.dialog.alert;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import dialog = nts.uk.ui.dialog;

    export module viewmodel {
        export class ScreenModel {
            commonMasters: KnockoutObservableArray<ICommonMaster> = ko.observableArray([]);
            selectedCommonMaster: KnockoutObservable<ICommonMaster> = ko.observable(new CommonMaster());
            commonMasterItems: KnockoutObservableArray<ICommonMaster> = ko.observableArray([]);
            selectedCommonMasterItem: KnockoutObservable<MasterItem> = ko.observable(new MasterItem(this.defaultItem));
            defaultItem = {
                commonMasterItemId: null,
                commonMasterItemCode: "",
                commonMasterItemName: "",
                displayNumber: null,
                usageStartDate: moment(new Date()).format("YYYY/MM/DD"),
                usageEndDate: "9999/12/31"
            }
            
            newMode: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                let self = this;

                self.selectedCommonMaster().commonMasterId.subscribe((id) => {
                    
                    let cMaster = _.filter(self.commonMasters(), ['commonMasterId', id])[0]

                    self.selectedCommonMaster().updateData(_.filter(self.commonMasters(), ['commonMasterId', id])[0]);

                    if (!id) {
                        self.commonMasterItems([]);
                        self.selectedCommonMasterItem().commonMasterItemId(null);
                        return;
                    }

                    block.grayout();

                    service.getItems(id).done((data: Array<IMasterItem>) => {

                        self.commonMasterItems(data);
                        self.selectedCommonMasterItem().commonMasterItemId(data[0].commonMasterItemId);
                    }).fail(function(res) {
                        self.commonMasterItems([]);
                        self.selectedCommonMasterItem().commonMasterItemId(null);
                        alert(res);

                    }).always(() => {

                        nts.uk.ui.errors.clearAll();
                        block.clear();
                    });

                });


                self.selectedCommonMasterItem().commonMasterItemId.subscribe((id) => {
                    nts.uk.ui.errors.clearAll();
                    $("#A223_2").focus();
                    let selectedItem = _.filter(self.commonMasterItems(), ['commonMasterItemId', id])[0];
                    self.selectedCommonMasterItem().updateData(selectedItem ? selectedItem : self.defaultItem);
                    if (id) {
                        self.newMode(false);
                    } else {
                        self.newMode(true);
                    }
                });
                
                setTimeout(() => {
                    
                    $(window).resize(function() {
                        
                        $("#master-item-list").igGrid("option", "height", (window.innerHeight - 283) + "px");
                        $("#master-list").igGrid("option", "height", (window.innerHeight - 306) + "px");
                        $("#common-master_arena").height((window.innerHeight - 240) + "px");
                    });
                    
                }, 100); 
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {

                let self = this, dfd = $.Deferred(),

                    param = { confirmed: null };

                block.grayout();

                service.startPage(param)
                .done((data: Array<ICommonMaster>) => {

                    self.commonMasters(data);
                    self.selectedCommonMaster().commonMasterId(data.length ? data[0].commonMasterId : null);

                }).fail(function(res) {

                    if (res.messageId == "Msg_1589") {

                        confirm({ messageId: res.messageId }).ifYes(() => {
                            
                            param.confirmed = true;
                            block.grayout();
                            
                            service.startPage(param).done((data: Array<ICommonMaster>) => {

                                self.commonMasters(data);

                            }).always(() => {
                                
                                block.clear();

                            });
                        }).ifNo(() => {
                            
                            alert("Msg_1590");
                            
                        });
                    } else {
                        alert(res.messageId);
                    }

                    block.clear();


                }).always(() => {
                    
                    setTimeout(() => {
                        
                        $("#master-item-list").igGrid("option", "height", (window.innerHeight - 283) + "px");
                        $("#master-list").igGrid("option", "height", (window.innerHeight - 306) + "px");
                        $("#common-master_arena").height((window.innerHeight - 240) + "px");
                        
                    }, 100); 
                    nts.uk.ui.errors.clearAll();
                    block.clear();
                    dfd.resolve();

                });

                return dfd.promise();
            }

            public saveData() {
                
                $('#A223_2').ntsError('check');
                $('#A223_3').ntsError('check');
                
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }

                let self = this
                    , param = ko.mapping.toJS(self);
                param.selectedCommonMasterItem.usageStartDate =
                    moment(param.selectedCommonMasterItem.usageStartDate).format("YYYY/MM/DD");
                
                param.selectedCommonMasterItem.usageEndDate =
                    moment(param.selectedCommonMasterItem.usageEndDate).format("YYYY/MM/DD");

                
                if (!param.newMode && !param.selectedCommonMasterItem.commonMasterItemId) {
                    return;
                }
                block.grayout();

                service.saveItems(param).done(() => {

                    service.getItems(self.selectedCommonMaster().commonMasterId()).done((data: Array<IMasterItem>) => {

                        self.commonMasterItems(data);
                        
                        if (self.newMode()) {
                            self.selectedCommonMasterItem().commonMasterItemId(_.maxBy(data, 'displayNumber').commonMasterItemId);
                        }

                    }).always(() => {

                        block.clear();
                    });


                    info({ messageId: "Msg_15" });


                }).fail(function(res) {
                    dialog.bundledErrors(res);

                }).always(() => {

                    block.clear();
                });
            }
            
            public newItem() {

                let self = this;
                self.selectedCommonMasterItem().commonMasterItemId(null);
                $("#A223_2").focus();
            }
            
            public exportExcel() {
                let self = this;

                block.grayout();

                service.outPutFileExcel().done(() => {
                    block.clear();
                })
            }

            public openDialogB() {
                let self = this;
                setShared('listMasterToB', self.commonMasters());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                    let data: IDialogToMaster = getShared('DialogBToMaster');

                    if (self.selectedCommonMaster().commonMasterId() == data.commonMasterId) {
                        self.selectedCommonMaster().commonMasterId.valueHasMutated();
                        self.commonMasterItems(data.itemList);
                    } else {
                        self.selectedCommonMaster().commonMasterId(data.commonMasterId);
                    }
                });
            }

            public openDialogC() {
                let self = this;
                setShared('listMasterToC', self.commonMasters());
                nts.uk.ui.windows.sub.modal('/view/cmm/022/c/index.xhtml').onClosed(function(): any {
                    let data: IDialogToMaster = getShared('DialogCToMaster');
                    self.commonMasters(data.masterList);
                    if (self.selectedCommonMaster().commonMasterId() == data.commonMasterId) {
                        self.selectedCommonMaster().commonMasterId.valueHasMutated();
                    } else {
                        self.selectedCommonMaster().commonMasterId(data.commonMasterId);
                    }
                });
            }

        }


    }
    
    
    export interface IDialogToMaster {
        commonMasterId: string;
        masterList: Array<ICommonMaster>;
        itemList: Array<IMasterItem>;
    }

    export interface ICommonMaster {
        //共通マスタID
        commonMasterId: string;
        // 共通マスタコード
        commonMasterCode: string;
        // 共通マスタ名
        commonMasterName: string;
        // 備考
        commonMasterMemo: string;
    }

    export class CommonMaster {
        commonMasterId: KnockoutObservable<String> = ko.observable();
        commonMasterCode: KnockoutObservable<String> = ko.observable();
        commonMasterName: KnockoutObservable<String> = ko.observable();
        commonMasterMemo: KnockoutObservable<String> = ko.observable();
        constructor(data?: ICommonMaster) {
            let self = this;
            if (data) {
                self.commonMasterId(data.commonMasterId);
                self.commonMasterCode(data.commonMasterCode);
                self.commonMasterName(data.commonMasterName);
                self.commonMasterMemo(data.commonMasterMemo);
            }
        }
        
        updateData(data?) {
            let self = this;
            self.commonMasterCode(data ? data.commonMasterCode : "");
            self.commonMasterName(data ? data.commonMasterName : "");
            self.commonMasterMemo(data ? data.commonMasterMemo : "");

        }
    }

    export interface IMasterItem {
        // 共通項目ID
        commonMasterItemId: string;
        // 共通項目コード
        commonMasterItemCode: string;
        // 共通項目名
        commonMasterItemName: string;
        // 表示順
        displayNumber: number;
        // 使用開始日
        usageStartDate: string;
        // 使用終了日
        usageEndDate: string;
    }

    export class MasterItem {
       
        commonMasterItemId: KnockoutObservable<String> = ko.observable();
       
        commonMasterItemCode: KnockoutObservable<String> = ko.observable();
      
        commonMasterItemName: KnockoutObservable<String> = ko.observable();
        displayNumber: KnockoutObservable<number> = ko.observable();
        usageStartDate: KnockoutObservable<String> = ko.observable(moment(new Date()).format("YYYY/MM/DD"));
        usageEndDate: KnockoutObservable<String> = ko.observable("9999/12/31");

        constructor(data: IMasterItem) {
            let self = this;
            if (data) {
                self.commonMasterItemId(data.commonMasterItemId);
                self.commonMasterItemCode(data.commonMasterItemCode);
                self.commonMasterItemName(data.commonMasterItemName);
                self.displayNumber(data.displayNumber);
                self.usageStartDate(data.usageStartDate);
                self.usageEndDate(data.usageEndDate);
            }
        }
        
        updateData(data: IMasterItem) {
            let self = this;
            self.commonMasterItemCode(data.commonMasterItemCode);
            self.commonMasterItemName(data.commonMasterItemName);
            self.displayNumber(data.displayNumber);
            self.usageStartDate(data.usageStartDate);
            self.usageEndDate(data.usageEndDate);
            nts.uk.ui.errors.clearAll();
        }
    }
}