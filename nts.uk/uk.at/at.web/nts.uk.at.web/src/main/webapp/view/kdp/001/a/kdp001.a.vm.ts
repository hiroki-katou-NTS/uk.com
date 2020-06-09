module nts.uk.com.view.kdp001.a {

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
            fistLoad = true;
            
            newMode: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                let self = this;

              
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {

                let self = this, dfd = $.Deferred();

                dfd.resolve();
                return dfd.promise();
            }

            public openDialogB() {
                let self = this;
                setShared('listMasterToB',
                {
                    commonMasters: self.commonMasters(),
                    commonMasterId: self.selectedCommonMaster().commonMasterId(),
                    commonMasterItems: self.commonMasterItems(),
                    commonMasterItemId: self.selectedCommonMasterItem().commonMasterItemId()
                });
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                    let data: IDialogToMaster = getShared('DialogBToMaster');

                    self.selectedCommonMaster().commonMasterId.valueHasMutated();
                });
            }

            public openDialogC() {
                let self = this;
                setShared('listMasterToC', {
                    commonMasters: self.commonMasters(),
                    commonMasterId: self.selectedCommonMaster().commonMasterId()
                });
                
                nts.uk.ui.windows.sub.modal('/view/cmm/022/c/index.xhtml').onClosed(function(): any {
                    let data: IDialogToMaster = getShared('DialogCToMaster');
                    self.commonMasters(data.masterList);
                    self.selectedCommonMaster().commonMasterId.valueHasMutated();
                });
            }

        }


    }
    
    
    export interface IDialogToMaster {
        commonMasterId: string;
        masterList: Array<ICommonMaster>;
        itemList: Array<IMasterItem>;
        commonMasterItemId: string;
        
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