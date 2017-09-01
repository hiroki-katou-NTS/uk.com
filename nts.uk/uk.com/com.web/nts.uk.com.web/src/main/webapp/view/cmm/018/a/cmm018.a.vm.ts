module nts.uk.com.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
    export module viewmodel {
        export class ScreenModel {
            nameCompany: KnockoutObservable<string>;
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>;
            selectedModeCode: KnockoutObservable<number> = ko.observable(0);
            dataSource: KnockoutObservable<vmbase.CommonApprovalRootDto>;
            columns: KnockoutObservableArray<any>;
            listHistory: KnockoutObservableArray<vmbase.ListHistory>;
            currentCode : KnockoutObservable<string>;
            lstItems: KnockoutObservableArray<vmbase.ListHistory>;
            constructor() {
                var self = this;
                self.nameCompany  = ko.observable('Kakashi');
                self.dataSource = ko.observable(null);
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
                self.currentCode = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' }]);
                let a:vmbase.ListHistory = new vmbase.ListHistory('2017/01/01 ~ 2017/08/31');
                let b:vmbase.ListHistory = new vmbase.ListHistory('2017/01/01 ~ 2017/08/31');
                self.listHistory = ko.observableArray([]);
                self.lstItems = ko.observableArray([]);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.listHistory.push(a);
                self.lstItems.push(a);
                self.lstItems.push(b);
                self.lstItems.push(b);
                self.lstItems.push(b);
                self.lstItems.push(b);
                self.lstItems.push(b);
                self.startPage();
            }
            /**
             * startPage
             * get all data
             */
            startPage(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    self.dataSource(data);
                    console.log(data);
                })
            }
            /*
                open Dialog D, set param = {yearMonth} 
            */
            openDialogC() {
                var self = this;
            }
            
            /*
                open Dialog D, set param = {classification,yearMonth,workPlaceId,classCD}
            */
            openDialogD(value) {
                nts.uk.ui.block.invisible()
                var self = this;
            }
            
            openDialogI(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/i/index.xhtml");
            }
            openDialogJ(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/j/index.xhtml");
                
            }
        }
    }
}