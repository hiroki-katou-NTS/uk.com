module nts.uk.com.view.cmm018.j {
    export module viewmodel {
        import servicebase = cmm018.shr.servicebase;
        import close = nts.uk.ui.windows.close;
        import block =  nts.uk.ui.block;
        import vmbase = cmm018.shr.vmbase;
        export class ScreenModel {
            isUpdate: KnockoutObservable<boolean>;
            size: number;
            newStartDate: KnockoutObservable<string>;
            item: KnockoutObservable<string> = ko.observable('');
            dataSource: vmbase.JData_Param;
            itemList:  KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number> = ko.observable(1);
            beginStartDate: KnockoutObservable<string>;
            constructor() {
                let self = this; 
                self.itemList =  ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_54") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_55") }
                                ]);
                let data: vmbase.JData_Param = nts.uk.ui.windows.getShared('CMM018J_PARAM');
                self.dataSource = data;
                self.beginStartDate = ko.observable(moment(self.dataSource.startDate).add(1, 'days').format("YYYY/MM/DD"));
                self.item(self.dataSource.name);
                self.newStartDate = ko.observable(self.dataSource.startDate);//startDate
                self.isUpdate = ko.observable(true);
                self.selectedId.subscribe(function(codeChange){
                    if(codeChange == 0){//delete
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
                block.invisible();
                var self = this;
                //data
                let dataFix: vmbase.JData = new vmbase.JData(self.newStartDate(),'9999-12-31', self.dataSource.workplaceId,
                                self.dataSource.employeeId, self.dataSource.check, self.selectedId(),
                                self.dataSource.startDate, self.dataSource.lstUpdate, self.dataSource.mode, self.dataSource.sysAtr);
                if(self.isUpdate()) {//TH: edit
//                //???????????????????????????????????? > ???????????????????????????????????? ??? false?????????
//                    if(self.newStartDate() < self.beginStartDate()){
//                        //????????????????????????(Msg_156)(error mesage (Msg_156))
//                        nts.uk.ui.dialog.alertError({ messageId: "Msg_156", messageParams: nts.uk.resource.getText("CMM018_48")  }).then(function(res){
//                            block.clear();
//                        });
//                        return;
//                    }
                    //???????????????????????????(Update history)
                    servicebase.updateHistory(dataFix).done(function(){
                        //????????????????????????Msg_15???(Show message Msg_15)
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            //close dialog
                            close();
                        });
                        block.clear();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId:res.messageId, messageParams: res.parameterIds}).then(function(res){
                            $("#startDateInput").focus();
                                block.clear();
                        });      
                    });
                } else {//TH: delete
                    //?????????????????????????????????????????????????????? (Check history the last)
                    if(self.dataSource.endDate != '9999/12/31'){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_154" }).then(function(res){
                            block.clear();
                        });
                        return;
                     }
                    //TH: ???????????????????????????
                    if(self.dataSource.mode == 0){
                        //??????????????????????????????????????????????????????????????????(Check ls co b??? ch???ng ch??o k?)
                        if(self.dataSource.overlapFlag){
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_319" }).then(function(res){
                               block.clear();
                            });
                            return;
                        }
                    }
                    //????????????????????????(x??c nh???n tr?????c khi x??a)
                    //????????????????????????Msg_18??????????????????(Show Confirm Message Msg_18)
                    nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function(){
                        //??????????????????????????????(Delete history) 
                        servicebase.updateHistory(dataFix).done(function(){
                            //????????????????????????Msg_16???(Show message Msg_16)
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                                close();
                            });
                        }).fail(function(res){
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds}).then(function(res){
                                 $("#startDateInput").focus();
                                block.clear();
                            });
                        });
                    }).ifNo(function(){
                        block.clear();        
                    });
                }
            }
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.setShared('CMM018J_OUTPUT', {cancel: true});
                close();   
            }
        }
    }
}