module nts.uk.at.view.kaf011.c.viewmodel {
	import windows = nts.uk.ui.windows;
   	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	import dialog = nts.uk.ui.dialog;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	
	export class KAF011C {
		displayInforWhenStarting: any;
		appDate: KnockoutObservable<string>;
		reasonTypeItemLst: KnockoutObservableArray<any> = ko.observableArray([]);
		appStandardReasonCD: KnockoutObservable<number> = ko.observable();
		appReason: KnockoutObservable<string> = ko.observable("");
		appDispInfoStartupOutput: any = ko.observable();
		appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		indexApprover: number = 0;
        approvalRootState: KnockoutObservableArray<any>;
		constructor(){
			let self = this;
			let dataTransfer = windows.getShared('KAF011C');
			self.displayInforWhenStarting = dataTransfer;
			let reasonTypeItemLst: any = dataTransfer.appDispInfoStartup.appDispInfoNoDateOutput.reasonTypeItemLst;
			self.appDispInfoStartupOutput(dataTransfer.appDispInfoStartup);
			self.appDate = ko.observable(dataTransfer.abs.application.appDate);
			self.approvalRootState = ko.observableArray([]);
			if(reasonTypeItemLst){
				self.reasonTypeItemLst(reasonTypeItemLst);	
			}
			self.appDate.subscribe((value:any) => {
				block.invisible();
				ajax('at/request/application/holidayshipment/changeDateScreenC',{appDateNew: new Date(value), displayInforWhenStarting: self.displayInforWhenStarting}).then((data: any) =>{
					self.indexApprover = 0;
					self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput = data.appDispInfoStartup.appDispInfoWithDateOutput;
					self.appDispInfoStartupOutput(data.appDispInfoStartup);
				}).fail((fail: any) => {
					dialog.error({ messageId: fail.messageId});
				}).always(() => {
                    block.clear();
                });
			});
			
			self.appDispInfoStartupOutput.subscribe(value => {
                if(!_.isEmpty(value.appDispInfoWithDateOutput.opListApprovalPhaseState)) {
                    self.approvalRootState(ko.mapping.fromJS(value.appDispInfoWithDateOutput.opListApprovalPhaseState)());
                } else {
                    self.approvalRootState([]);
                }
            });
		}
		
		
		triggerValidate(): boolean{
			$('.nts-input').trigger("validate");
			$('input').trigger("validate");
			return !nts.uk.ui.errors.hasError();
		}
		public handleMailResult(result: any, vm: any): any {
			let dfd = $.Deferred();
			if(_.isEmpty(result.autoFailServer)) {
				if(_.isEmpty(result.autoSuccessMail)) {
					if(_.isEmpty(result.autoFailMail)) {
						dfd.resolve(true);
					} else {
						dialog.error({ messageId: 'Msg_768', messageParams: [_.join(result.autoFailMail, ',')] }).then(() => {
				        	dfd.resolve(true);
				        });	
					}
				} else {
					dialog.info({ messageId: 'Msg_392', messageParams: [_.join(result.autoSuccessMail, ',')] }).then(() => {
						if(_.isEmpty(result.autoFailMail)) {
							dfd.resolve(true);	
						} else {
							dialog.error({ messageId: 'Msg_768', messageParams: [_.join(result.autoFailMail, ',')] }).then(() => {
					        	dfd.resolve(true);
					        });	
						}
			        });	
				}	
			} else {
				dialog.error({ messageId: 'Msg_1057' }).then(() => {
		        	dfd.resolve(true);
		        });
			}
			return dfd.promise();
		}
		
		save(){
			let self = this;
			if(self.triggerValidate()){
				if(self.displayInforWhenStarting.rec){
					self.displayInforWhenStarting.rec.applicationInsert = self.displayInforWhenStarting.rec.applicationUpdate = self.displayInforWhenStarting.rec.application;	
					// self.displayInforWhenStarting.rec.payoutSubofHDManagements.forEach(element => {
					// 	element.dateOfUse = new Date(self.appDate());
					// });
					// self.displayInforWhenStarting.rec.leaveComDayOffMana.forEach(element => {
					// 	element.dateOfUse = new Date(self.appDate());
					// });
				}
				if(self.displayInforWhenStarting.abs){
					self.displayInforWhenStarting.abs.applicationInsert = self.displayInforWhenStarting.abs.applicationUpdate = self.displayInforWhenStarting.abs.application;
					// self.displayInforWhenStarting.abs.payoutSubofHDManagements.forEach(element => {
					// 	element.dateOfUse = new Date(self.appDate());
					// });
					// self.displayInforWhenStarting.abs.leaveComDayOffMana.forEach(element => {
					// 	element.dateOfUse = new Date(self.appDate());
					// });
				}
				block.invisible();
				ajax('at/request/application/holidayshipment/saveChangeDateScreenC',{appDateNew: new Date(self.appDate()), displayInforWhenStarting: self.displayInforWhenStarting, appReason: self.appReason(), appStandardReasonCD: self.appStandardReasonCD()}).then((data: any) =>{
					dialog.info({ messageId: "Msg_15"}).then(()=>{
						nts.uk.request.ajax("at", "at/request/application/reflect-app", data.reflectAppIdLst);
						return self.handleMailResult(data, self).then(() => {
							nts.uk.ui.windows.setShared('KAF011C_RESLUT', { appID: data.appIDLst[0] });
							self.closeDialog();
                            return true;
						});
					});
				}).fail((fail: any) => {
					dialog.error({ messageId: fail.messageId, messageParams: fail.parameterIds});
				}).always(() => {
	                block.clear();
	            });
				
			}
		}
		closeDialog(){
			windows.close();
		}
		
		/**
         * Component 6
         */
         isFirstIndexFrame(loopPhase: any, loopFrame: any, loopApprover: any) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover) == 0;
            }
            let firstIndex = _.chain(loopPhase.listApprovalFrame()).filter(x => _.size(x.listApprover()) > 0).orderBy(x => x.frameOrder()).first().value().frameOrder();
            let approver: any = _.find(loopPhase.listApprovalFrame(), o => o == loopFrame);
            if(approver) {
                return approver.frameOrder() == firstIndex;
            }
            return false;
        }

        getFrameIndex(loopPhase: any, loopFrame: any, loopApprover: any) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover);
            }
            return loopFrame.frameOrder();
        }

        frameCount(listFrame: any) {
            const self = this;
            let listExist = _.filter(listFrame, x => _.size(x.listApprover()) > 0);
            if(_.size(listExist) > 1) {
                return _.size(listExist);
            }
            return _.chain(listExist).map(o => self.approverCount(o.listApprover())).value()[0];
        }

        approverCount(listApprover: any) {
            return _.chain(listApprover).countBy().values().value()[0];
        }

        getApproverAtr(approver: any) {
            if(approver.approvalAtrName() !='未承認'){
                if(approver.agentName().length > 0){
                    if(approver.agentMail().length > 0){
                        return approver.agentName() + '(@)';
                    } else {
                        return approver.agentName();
                    }
                } else {
                    if(approver.approverMail().length > 0){
                        return approver.approverName() + '(@)';
                    } else {
                        return approver.approverName();
                    }
                }
            } else {
                var s = '';
                s = s + approver.approverName();
                if(approver.approverMail().length > 0){
                    s = s + '(@)';
                }
                if(approver.representerName().length > 0){
                    if(approver.representerMail().length > 0){
                        s = s + '(' + approver.representerName() + '(@))';
                    } else {
                        s = s + '(' + approver.representerName() + ')';
                    }
                }
                return s;
            }
        }

        getPhaseLabel(phaseOrder: any) {
            const self = this;
            switch(phaseOrder) {
                case 1: return nts.uk.resource.getText("KAF000_4");
                case 2: return nts.uk.resource.getText("KAF000_5");
                case 3: return nts.uk.resource.getText("KAF000_6");
                case 4: return nts.uk.resource.getText("KAF000_7");
                case 5: return nts.uk.resource.getText("KAF000_8");
                default: return "";
            }
        }

        getApproverLabelByIndex() {
			const self = this;
			self.indexApprover++;
			return nts.uk.resource.getText("KAF000_9",[self.indexApprover+'']);
		}
	}
	
    __viewContext.ready(function() {
		const vm = new KAF011C()
    	__viewContext.bind(vm);
		
		
		$("#recAppDate").focus();
    });
}