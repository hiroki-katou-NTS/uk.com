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
            lstCompany: KnockoutObservableArray<vmbase.CompanyAppRootDto>;
            columns: KnockoutObservableArray<any>;
            listHistory: KnockoutObservableArray<vmbase.ListHistory>;
            currentCode : KnockoutObservable<string>;
            lstItems: KnockoutObservableArray<vmbase.ListApproval>;
            lstDelete: KnockoutObservableArray<vmbase.ListApproval>
            historyStr: KnockoutObservable<string>;
            lstNameAppType: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                self.lstCompany = ko.observableArray([]);
                self.nameCompany  = ko.observable('Kakashi');
                self.historyStr  = ko.observable('');
                self.dataSource = ko.observable(null);
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
                self.currentCode = ko.observable(null);
                self.columns = ko.observableArray([
                    { headerText: 'Id', key: 'approvalId', hidden: true },
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' }
                ]);
                self.listHistory = ko.observableArray([]);
                self.lstItems = ko.observableArray([new vmbase.ListApproval('000','Kakashi0'),
                                                    new vmbase.ListApproval('111','Kakashi1'),
                                                    new vmbase.ListApproval('222','Kakashi2'),
                                                    new vmbase.ListApproval('333','Kakashi3'),
                                                    new vmbase.ListApproval('444','Kakashi4')
                    ]);
                self.lstDelete = ko.observableArray([]);
                self.startPage();
                //subcribe
                self.currentCode.subscribe(function(codeChanged) {
                    let itemCurrent = self.findApproval(codeChanged);
                    let history = self.findHistory(codeChanged);
                    self.historyStr(history.dateRange);
                });
                self.lstNameAppType = ko.observableArray([]);
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
                    self.lstCompany(self.dataSource().lstCompanyRoot);
                    self.convertHist(self.lstCompany());
                    if(self.listHistory().length > 0){
                        self.currentCode(self.listHistory()[0].approvalId);
                    }
                    self.nameCompany(data.companyName);
                    console.log(data);
                    servicebase.getNameAppType().done(function(lstName){
                        console.log(lstName);
                        self.lstNameAppType(lstName);
                    })
                })
            }
            /*
                open Dialog C
            */
            openDialogC() {
                var self = this;
            }
            
            /*
                open Dialog D
            */
            openDialogD(value) {
//                nts.uk.ui.block.invisible()
                var self = this;
            }
            
            openDialogI(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/i/index.xhtml");
            }
            openDialogJ(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/j/index.xhtml");
                
            }
            deleteRow(index){
                var self = this;
                let objSelected: vmbase.ListApproval = self.findApproval(index);
                self.lstDelete.push(objSelected);
                let lstNew = self.lstItems();
                self.lstItems([]);
                _.each(lstNew, function(item: vmbase.ListApproval){
                    if(item.approvalId != index){
                        self.lstItems.push(item);
                    }
                });
                  console.log(self.lstItems());
           }
            /**
             * find approval is selected
             */
            findApproval(value: string): any {
                let self = this;
                return _.find(self.lstItems(), function(obj: vmbase.ListApproval) {
                    return obj.approvalId == value;
                })
            }
            /**
             * find appRootHist is selected
             */
            findHistory(value: string): vmbase.ListHistory {
                let self = this;
                return _.find(self.listHistory(), function(obj: vmbase.ListHistory) {
                    return obj.approvalId == value;
                })
            }
            /**
             * register
             */
            register(){
                var self = this;
                console.log(self.lstDelete());
            }
            /**
             * convert to lst history
             */
            convertHist(lstCom: Array<vmbase.CompanyAppRootDto>): void{
                var self = this;
                self.listHistory([]);
                _.each(lstCom, function(itemHist: vmbase.CompanyAppRootDto){
                    let str = itemHist.company.startDate + ' ~ ' + itemHist.company.endDate;
                    self.listHistory.push(new vmbase.ListHistory(itemHist.company.approvalId,str));
                });
            }
        }
    }
}