/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmr001.b {

    @bean()
    export class ViewModel extends ko.ViewModel {
		isRegister: boolean = true;
        reservationFrameName1: KnockoutObservable<string> = ko.observable("");
        reservationStartTime1: KnockoutObservable<number> = ko.observable();
        reservationEndTime1: KnockoutObservable<number> = ko.observable();
        useTime2: KnockoutObservable<boolean> = ko.observable(false);
		reservationFrameName2: KnockoutObservable<string> = ko.observable("");
        reservationStartTime2: KnockoutObservable<number> = ko.observable();
        reservationEndTime2: KnockoutObservable<number> = ko.observable();
        reserveContentChangeList: any[] = [
            { 'id': ContentChangeDeadline.ALLWAY_FIXABLE, 'name': this.$i18n("KMR001_97") },
            { 'id': ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR, 'name': this.$i18n("KMR001_98") },
            { 'id': ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE, 'name': this.$i18n("KMR001_99") },
        ];
        selectedContent: KnockoutObservable<number> = ko.observable(0);
		contentChangeDeadlineDayLst: KnockoutObservableArray<any> = ko.observableArray([]);
		contentChangeDeadlineDayCurrent: KnockoutObservable<number> = ko.observable(0);
		orderMngAtr: KnockoutObservable<number> = ko.observable(0);
		monthlyResults: KnockoutObservable<boolean> = ko.observable(true);
		listRole: KnockoutObservableArray<any> = ko.observableArray([]);
		roleName: KnockoutObservable<string> = ko.pureComputed(() => {
			return _.chain(this.listRole()).sortBy('roleCode').map(o => o.name).join(", ").value();
		});

        constructor() {
            super();
            const vm = this;
			vm.$blockui("show");
			vm.start();
			
			vm.useTime2.subscribe((value) => {
				if(!value) {
					$('.required-field-2').ntsError('clear');
				}
			});
        }

		start() {
			const vm = this;
			vm.$blockui("show");
			vm.$ajax(API.GET_BENTO_RESERVATION)
			.then((data) => {
				if(data) {
					let contentChangeDeadlineDayLst = [{ value: 0, localizedName: "" }];
					contentChangeDeadlineDayLst = _.concat(contentChangeDeadlineDayLst, data.contentChangeDeadlineDayEnum);
					vm.contentChangeDeadlineDayLst(contentChangeDeadlineDayLst);
					vm.contentChangeDeadlineDayCurrent(_.head(vm.contentChangeDeadlineDayLst()).value);
					if(data.setting) {
						let frame1 = _.find(data.setting.reservationRecTimeZoneLst, (o: any) => o.frameNo==1);
						if(frame1) {
							vm.reservationFrameName1(frame1.receptionHours.receptionName);
							vm.reservationStartTime1(frame1.receptionHours.startTime);
							vm.reservationEndTime1(frame1.receptionHours.endTime);
						}
						vm.useTime2(data.setting.receptionTimeZone2Use);
						if(vm.useTime2()) {
							let frame2 = _.find(data.setting.reservationRecTimeZoneLst, (o: any) => o.frameNo==2);
							if(frame2) {
								vm.reservationFrameName2(frame2.receptionHours.receptionName);
								vm.reservationStartTime2(frame2.receptionHours.startTime);
								vm.reservationEndTime2(frame2.receptionHours.endTime);
							}
						}
						vm.selectedContent(data.setting.correctionContent.contentChangeDeadline);
						vm.contentChangeDeadlineDayCurrent(data.setting.correctionContent.contentChangeDeadlineDay);
						vm.orderMngAtr(data.setting.correctionContent.orderMngAtr);
						vm.monthlyResults(data.setting.monthlyResultsMethod);
						vm.isRegister = false;
					} else {
						vm.isRegister = true;
					}
					return vm.$ajax('com', API.GET_ROLE_INFO, data.setting ? data.setting.correctionContent.canModifiLst : []);
				}
				return false;
			}).then((dataRole) => {
				if(dataRole) {
					vm.listRole(dataRole);
				}
			}).fail(function(res) {
				vm.$dialog.error({ messageId: res.messageId });
			}).always(() => {
				vm.$blockui("hide");
			});
		}

        registerBentoReserveSetting() {
			const vm = this;
			vm.$blockui("show");
			vm.$validate('.required-field', '.required-field-2')
			.then((valid) => {
				if(valid) {
					return vm.validate();
				}	
			}).then((valid) => {
				if(valid) {
					let command = vm.createCommand();
					vm.$ajax(API.ADD_BENTO_RESERVATION, command).then(() => {
						vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
							vm.start();
						});
					});
				}
			}).fail(function(res) {
				vm.$dialog.error({ messageId: res.messageId });	
			}).always(() => {
				vm.$blockui("hide");
			});
        }

		createCommand() {
			const vm = this;
			let reservationRecTimeZoneLst: Array<any> = [];
			reservationRecTimeZoneLst.push({
				receptionHours: {
					receptionName: vm.reservationFrameName1(),
					startTime: vm.reservationStartTime1(),
					endTime: vm.reservationEndTime1()
				},
				frameNo: 1
			});
			if(vm.useTime2()) {
				reservationRecTimeZoneLst.push({
					receptionHours: {
						receptionName: vm.reservationFrameName2(),
						startTime: vm.reservationStartTime2(),
						endTime: vm.reservationEndTime2()
					},
					frameNo: 2
				});
			}
			let canModifiLst: Array<any> = _.map(vm.listRole(), o => o.roleId),
				correctionContent = {
					contentChangeDeadline: vm.selectedContent(),
					contentChangeDeadlineDay: vm.contentChangeDeadlineDayCurrent(),
					orderMngAtr: vm.orderMngAtr(),
					canModifiLst
				},
				command = {
					isRegister: vm.isRegister,
					operationDistinction: 0,
					correctionContent,
					monthlyResults: vm.monthlyResults() ? 1 : 0,
					reservationRecTimeZoneLst,
					receptionTimeZone2Use: vm.useTime2()
				};
			return command;
		}

		validate() {
			const vm = this;
			if(vm.reservationStartTime1() >= vm.reservationEndTime1()) {
				vm.$dialog.error({ messageId: 'Msg_849' });
				return false;	
			}
			if(vm.useTime2()) {
				if(vm.reservationStartTime2() >= vm.reservationEndTime2()) {
					vm.$dialog.error({ messageId: 'Msg_849' });
					return false;	
				}	
			}
			if(vm.selectedContent()==ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE) {
				if(vm.contentChangeDeadlineDayCurrent()==0) {
					nts.uk.ui.dialog.caution({ messageId: 'Msg_2263' });
					return false;
				}
			}
			return true;	
		}

		openCDL025() {
			const vm = this
            , data = {
            	roleType: 3,
                multiple: true,
                currentCode: _.map(vm.listRole(), o => o.roleId)
            };
			nts.uk.ui.windows.setShared("paramCdl025", data);
        	vm.$window.modal('com', '/view/cdl/025/index.xhtml')
            .then(() => {
				let dataReturn = nts.uk.ui.windows.getShared("dataCdl025");
				if (!nts.uk.util.isNullOrUndefined(dataReturn)) {
					vm.$blockui("show");
					vm.$ajax('com', API.GET_ROLE_INFO, nts.uk.util.isNullOrEmpty(dataReturn) ? [] : dataReturn).done((dataRole) => {
						if(dataRole) {
							vm.listRole(dataRole);
						}
					}).fail(function(res) {
						vm.$dialog.error({ messageId: res.messageId });	
					}).always(() => {
						vm.$blockui("hide");
					});	
				}	
            });	
		}
    }

    const API = {
		GET_BENTO_RESERVATION: 'at/record/reservation/bento-menu/start',
        ADD_BENTO_RESERVATION: 'bento/bentomenusetting/save',
		GET_ROLE_INFO: 'ctx/sys/auth/role/get/rolename/by/roleids'
    }

    export enum ContentChangeDeadline {
        // 常に修正可能
        ALLWAY_FIXABLE = 0,
        // 受付時間内は修正可能
        MODIFIED_DURING_RECEPTION_HOUR = 1, 
        // 注文日からの○日間修正可能
        MODIFIED_FROM_ORDER_DATE = 2
    }

	
}