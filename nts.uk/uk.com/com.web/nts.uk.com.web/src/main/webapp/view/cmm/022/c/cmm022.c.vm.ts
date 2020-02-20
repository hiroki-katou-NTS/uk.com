module nts.uk.com.view.cmm022.c.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    
    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
        export class ScreenModel {
            
            settings: KnockoutObservableArray<any> = ko.observableArray([]);
            columns: KnockoutObservableArray<any>
            columnsItem: KnockoutObservableArray<any>
            itemSelected: KnockoutObservable<CommonMasterItem> = ko.observable();
            
            listMaster: KnockoutObservableArray<any> = ko.observableArray([]);
            
            masterSelected: KnockoutObservable<CommonMasterItem> = ko.observable();
            
            commonMasterCode: KnockoutObservable<any> = ko.observable();
            commonMasterName: KnockoutObservable<any> = ko.observable();
            
            selectedItem: KnockoutObservable<any> = ko.observable();
            changeList: KnockoutObservableArray<any> = ko.observableArray([]);
            constructor() {
                let self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'commonMasterId', width: 80, hidden: true },
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'commonMasterCode', width: 80, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("CMM022_B1_7"), key: 'commonMasterName', width: 160, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("CMM022_B1_8"), key: 'commonMasterMemo', width: 160, formatter: _.escape}
                ]);
            
                self.masterSelected.subscribe(function(code){
                    if(!!code){
                        self.selectedItem(_.find(self.listMaster(), function(o) { return o.commonMasterId == code; }));
                        self.commonMasterCode(self.selectedItem().commonMasterCode);
                        self.commonMasterName(self.selectedItem().commonMasterName);                        
                    }

                });
                
                self.commonMasterCode.subscribe(function(item){
                    if(item != self.selectedItem().commonMasterCode){
                        let param = {
                            commonMasterId: self.selectedItem().commonMasterId,
                            commonMasterCode: item,
                            commonMasterName: self.selectedItem().commonMasterName,
                            commonMasterMemo: self.selectedItem().commonMasterMemo
                        }
                        self.changeList().push(param);
                    }
                });
                
                self.commonMasterName.subscribe(function(temp) {
                    if (temp != self.selectedItem().commonMasterName) {
                        let parameter = {
                            commonMasterId: self.selectedItem().commonMasterId,
                            commonMasterCode: self.selectedItem().commonMasterCode,
                            commonMasterName: temp,
                            commonMasterMemo: self.selectedItem().commonMasterMemo
                        }
                        self.changeList().push(parameter);
                    }
                });
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                let getshareMaster = getShared('listMasterToC');
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
                
                if(_.size(self.listMaster()) > 0){
                    self.masterSelected(self.listMaster()[0].commonMasterId);    
                }else{
                    self.masterSelected(null);
                    self.commonMasterCode(null);
                    self.commonMasterName(null);
                }
                dfd.resolve();

                return dfd.promise();
            }
            
            register(){
                let self = this;
                let param = {
                    listCommonMaster: self.changeList()
                }
                service.update(param).done(function(data: any) {
                    service.getListMaster().done(function(data: any){
                        self.listMaster(data);
                        console.log(data);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });
                }).fail(function(err) {
                    error({ messageId: err.messageId });
                }).always(function() {
                    blockUI.clear();
                });
            }
            
            // close dialog
            closeDialog() {
                nts.uk.ui.windows.close();
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