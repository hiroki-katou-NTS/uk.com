module nts.uk.com.view.cmm018.j {
    export module viewmodel {
        import servicebase = cmm018.shr.servicebase;
        import close = nts.uk.ui.windows.close;
//        import vmbase = cmm018.shr.vmbase;
        export class ScreenModel {
            isUpdate: KnockoutObservable<boolean>;
            beforeIndex: number;
            size: number;
            personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
            currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
            isLast: KnockoutObservable<boolean>;
            newStartDate: KnockoutObservable<string>;
            item: KnockoutObservable<string> = ko.observable('項目移送');
            dataSource: IDataJ;
            itemList:  KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number> = ko.observable(0);
            constructor() {
                var self = this; 
                self.itemList =  ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_54") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_55") }
                                ]);
                let lstItem = [];
                lstItem.push(new UpdateHistoryDto('1','1'));
                lstItem.push(new UpdateHistoryDto('0','2'));
                let dataFix: IDataJ = new IDataJ('2021-11-02','2001-12-31','123','abc',1,0,'2020-11-02',lstItem);
                var data: IDataJ = nts.uk.ui.windows.getShared('CMM018J_PARAM')|| dataFix;
                self.dataSource = data;
                let c = nts.uk.ui.windows.getShared('personCostList')|| { startDate: '2017-01-02', endDate: '2017-04-05'}
                self.personCostList = ko.observableArray();
                let b = nts.uk.ui.windows.getShared('currentPersonCost')|| {startDate:'2020-11-02',endDate:'9999-12-31',premiumSets: []}
                self.currentPersonCost = ko.observable(b);
                self.size = _.size(self.personCostList());
                self.isLast = ko.observable((_.findIndex(self.personCostList(), function(o){return self.currentPersonCost().startDate() == o.startDate(); })==(self.size-1))?true:false);
                self.beforeIndex = _.findIndex(self.personCostList(), function(o) { return o.startDate() == self.currentPersonCost().startDate(); })-1;
                self.newStartDate = ko.observable(self.currentPersonCost().startDate);//startDate
                self.isUpdate = ko.observable(true);
                self.selectedId.subscribe(function(codeChange){
                    if(codeChange == 1){//delete
                        self.isUpdate(false);
                    }else{//update
                        self.isUpdate(true);
                    }
                });
            }
            
            /**
             * update/delete data when no error and close dialog
             */
            registration(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                //data
                let dataFix: IDataJ = new IDataJ(self.newStartDate(),'9999-12-31',self.dataSource.workplaceId,self.dataSource.employeeId,self.dataSource.check,self.selectedId(),self.dataSource.startDate,self.dataSource.lstUpdate);
                if(self.isUpdate()) {//TH: edit
                    //履歴編集を実行する(Update history)
                    servicebase.updateHistory(self.dataSource).done(function(){
                        //情報メッセージ（Msg_15）(Show message Msg_15)
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            //close dialog
                            close();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(function(){
                            nts.uk.ui.block.clear();
                        });       
                    });
                } else {//TH: delete
                    //削除する期間が最新なのかチェックする
                    if(self.dataSource.endDate != '9999-12-31'){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_154" }).then(function(res){
                            nts.uk.ui.block.clear();
                        });
                        return;
                     }
                        
                    //確認メッセージ（Msg_18）を表示する(Show Confirm Message Msg_18)
                    nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function(){
                        //履歴の削除を実行する(Delete history) 
                        servicebase.updateHistory(self.dataSource).done(function(){
                            //情報メッセージ（Msg_16）(Show message Msg_16)
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                                close();
                            });
                        }).fail(function(res){
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){
                                nts.uk.ui.block.clear();
                            });
                        });
                    }).ifNo(function(){
                        nts.uk.ui.block.clear();        
                    });
                }
            }
            submitAndCloseDialog(): void {
                var self = this;
                let dataFix: IDataJ = new IDataJ(self.newStartDate(),'9999-12-31',self.dataSource.workplaceId,self.dataSource.employeeId,self.dataSource.check,self.selectedId(),self.dataSource.startDate,self.dataSource.lstUpdate);
                servicebase.updateHistory(self.dataSource).done(function(){
                    alert("done");
                }).fail(function(res){
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                });
            }
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.close();   
            }
        }
        class IDataJ{
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**履歴ID*/
            workplaceId: string;
            /**社員ID*/
            employeeId: string;
            /**check 申請承認の種類区分*/
            check: number;
            /**「履歴を削除する」を選択する か、「履歴を修正する」を選択する か。*/
            editOrDelete: number;
            /**開始日 previous*/
            startDatePrevious: string;
            /** list history and approvalId */
            lstUpdate: Array<UpdateHistoryDto>;
            constructor(startDate: string,
            endDate: string,
            workplaceId: string,
            employeeId: string,
            check: number,
            editOrDelete: number,
            startDatePrevious: string,
            lstUpdate: Array<UpdateHistoryDto>){
                this.startDate = startDate;
                this.endDate = endDate;
                this.workplaceId = workplaceId;
                this.employeeId = employeeId;
                this.check = check;
                this.editOrDelete = editOrDelete;
                this.startDatePrevious = startDatePrevious;
                this.lstUpdate = lstUpdate;
            }
        }
        class UpdateHistoryDto{
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
            constructor(approvalId: string, historyId: string){
                this.approvalId = approvalId;
                this.historyId = historyId; 
            }
        }
    }
}