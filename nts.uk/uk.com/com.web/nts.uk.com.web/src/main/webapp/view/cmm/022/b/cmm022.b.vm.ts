module nts.uk.com.view.cmm022.b.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;

    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
        export class ScreenModel {
            
            columns: KnockoutObservableArray<any>
            columnsItem: KnockoutObservableArray<any>
            
            listMaster: KnockoutObservableArray<any> = ko.observableArray([]);
            masterSelected: KnockoutObservable<any> = ko.observable();
            
            items: KnockoutObservableArray<any> = ko.observableArray([]);
            selected: KnockoutObservable<CommonMasterItem> = ko.observable();
            listItems: KnockoutObservableArray<any> = ko.observableArray([]);
            title: KnockoutObservable<CommonMasterItem> = ko.observable();
            constructor() {
                let self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'commonMasterId', width: 80, hidden: true },
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'commonMasterCode', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("CMM022_B1_7"), key: 'commonMasterName', width: 160, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("CMM022_B1_8"), key: 'commonMasterMemo', width: 160, formatter: _.escape}
                ]);
                self.columnsItem = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM022_B1_5"), key: 'commonMasterItemId', width: 80, hidden: true },
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'commonMasterItemCode', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("CMM022_B1_7"), key: 'commonMasterItemName', width: 160, formatter: _.escape}
                ]);
                self.masterSelected.subscribe(function(code){
                    let objectSelected = _.find(self.listMaster(), function(o) { return o.commonMasterId == code; })
                    self.title(objectSelected.commonMasterCode + " " +  objectSelected.commonMasterName);
                    let param = {
                        commonMasterId: code,
                    }
                    service.getListMasterItem(param).done((data: any) => {
                        self.listItems(data.listCommonMasterItem);                           
                    });
                });
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                let getshareMaster = getShared('listMasterToB');
                _.forEach(getshareMaster, (obj) => {
                    let parameter = {
                        commonMasterId: obj.commonMasterId,
                        commonMasterCode: obj.commonMasterCode,
                        commonMasterName: obj.commonMasterName,
                        commonMasterMemo: obj.commonMasterMemo,
                    }
                    self.listMaster().push(new CommonMasterItem(parameter));               
                });
                _.sortBy(self.listMaster(), ['commonMasterCode']);
                self.masterSelected(self.listMaster()[0].commonMasterId);

                let item = {
                    code: 1,
                    name: "b",
                    displayNum: 1
                }
                let array = [];
                array.push(item);

                dfd.resolve();

                return dfd.promise();
            }
            
            register(){
                let self = this;
                let param = {
                    commonMasterId: self.masterSelected(),
                    listMasterItem: self.listItems(),
                }
                service.add(param).done(function(data: any) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(err) {
                    error({ messageId: err.messageId });
                }).always(function() {
                    block.clear();
                });
                
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                });
            }
            
            // close dialog
            closeDialog() {
                nts.uk.ui.windows.close();
            }

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
        
    class MasterItem{
        commonMasterItemId: string;
        commonMasterItemCode: string;
        commonMasterItemName: string;
        displayNumber: number;
        usageStartDate: string;
        usageEndDate: string;
        useSetting: Array<string>;
        constructor(param: IMasterItem){
            let self = this;
            self.commonMasterItemId = param.commonMasterItemId;
            self.commonMasterItemCode = param.commonMasterItemCode;
            self.commonMasterItemName = param.commonMasterItemName;
            self.displayNumber = param.displayNumber;
            self.usageStartDate = param.usageStartDate;
            self.usageEndDate = param.usageEndDate;
            self.useSetting = param.useSetting;
        }
    }
        
    export interface ICommonMaster {
        commonMasterId: string;
        commonMasterCode: string;
        commonMasterName: string;
        commonMasterMemo: string;
    }
    
    class CommonMasterItem {
        commonMasterId: string;
        commonMasterCode: string;
        commonMasterName: string;
        commonMasterMemo: string;

        constructor(param: ICommonMaster) {
            let self = this;
            self.commonMasterId = param.commonMasterId;
            self.commonMasterCode = param.commonMasterCode;
            self.commonMasterName = param.commonMasterName;
            self.commonMasterMemo = param.commonMasterMemo;
        }
    }
    
}