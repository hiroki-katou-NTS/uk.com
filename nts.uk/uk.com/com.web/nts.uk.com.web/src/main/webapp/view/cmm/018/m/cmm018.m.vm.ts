module nts.uk.com.view.cmm018.m {
	import block = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            isCompany: KnockoutObservable<Boolean> = ko.observable(false);
            isWorkplace: KnockoutObservable<Boolean> = ko.observable(false);
            isPerson: KnockoutObservable<Boolean> = ko.observable(false);            
            date: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
            sysAtr: KnockoutObservable<number> = ko.observable(0);
            lstAppName: Array<any>;
			sv: SettingVisible = new SettingVisible();
            constructor() {
                let self = this; 
                let param = nts.uk.ui.windows.getShared('CMM018M_PARAM');
                self.sysAtr(param.sysAtr || 0);
                self.lstAppName = param.lstName || [];
				self.start();
            }
			start() {
				const self = this;
				nts.uk.ui.block.invisible();
				let companyId = __viewContext.user.companyId;
				let command = {
					systemAtr: ko.toJS(self.sysAtr),
					companyId
				}
				service.getSetting(command)
					   .done((res: ApproverRegisterSetDto) => {
							self.sv.changeValue(res.companyUnit == 1, res.workplaceUnit == 1, res.employeeUnit == 1);
							self.setUnit(res);
							block.clear();
					   })
					   .fail((res: any) => {
							nts.uk.ui.dialog.alert({ messageId: res.messageId || res.message});
							block.clear();
						});
			}
			setUnit(param: ApproverRegisterSetDto) {
				const self = this;
				self.isCompany(param.companyUnit == 1);
				self.isWorkplace(param.workplaceUnit == 1);
				self.isPerson(param.employeeUnit == 1);
			}
            //閉じるボタン
            closeDialog(){
                nts.uk.ui.windows.close();
            }
            
            printExcel(){
                if (nts.uk.ui.errors.hasError()) { return; }
                let self = this;
                //会社別、職場別、個人別のチェック状態をチェックする(kiểm tra trạng thái check của 会社別、職場別、個人別)
                //１件もチェック入れていない場合(không check cái nào)
                if(!self.isCompany() && !self.isWorkplace() && !self.isPerson()){
                    nts.uk.ui.dialog.alert({ messageId: "Msg_199"});
                    return;    
                }
                let master = new service.MasterApproverRootQuery(self.date(), ko.toJS(self.isCompany), 
                        ko.toJS(self.isWorkplace), ko.toJS(self.isPerson), self.sysAtr(), self.lstAppName);
                nts.uk.ui.block.grayout();
                service.saveAsExcel(master).done(function(data: service.MasterApproverRootQuery){
                    nts.uk.ui.block.clear();
                }).fail(function(res: any){
                    nts.uk.ui.dialog.alert({ messageId: res.messageId});  
                    nts.uk.ui.block.clear();                      
                });
            }
        }
		class SettingVisible {
			isCompany: KnockoutObservable<Boolean> = ko.observable(false);
			isWorkplace: KnockoutObservable<Boolean> = ko.observable(false);
			isPerson: KnockoutObservable<Boolean> = ko.observable(false);
			changeValue(isCompany: Boolean, isWorkplace: Boolean, isPerson: Boolean) {
				this.isCompany(isCompany);
				this.isWorkplace(isWorkplace);
				this.isPerson(isPerson);
			}
		}
        interface ApproverRegisterSetDto {
			companyUnit: number;
			workplaceUnit: number;
			employeeUnit: number;
		}
        
    }
}
