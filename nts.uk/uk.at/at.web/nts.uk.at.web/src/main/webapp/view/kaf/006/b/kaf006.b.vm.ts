module nts.uk.at.view.kaf006_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import WorkType = nts.uk.at.view.kaf006.shr.viewmodel.WorkType;
    import Kaf006ShrViewModel = nts.uk.at.view.kaf006.shr.viewmodel.Kaf006ShrViewModel;

    @component({
        name: 'kaf006-b',
        template: `/nts.uk.at.web/view/kaf/006/b/index.html`
    })
    export class Kaf006BViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.ABSENCE_APPLICATION);
        application: KnockoutObservable<Application>;
        appDispInfoStartupOutput: any;
        data: any = null;
		hdAppSet: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedType: KnockoutObservable<any> = ko.observable();
		workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedWorkTypeCD: KnockoutObservable<string> = ko.observable(null);
		selectedWorkType: KnockoutObservable<WorkType> = ko.observable(new WorkType({workTypeCode: '', name: ''}));
		selectedWorkTimeCD: KnockoutObservable<string> = ko.observable();
		selectedWorkTimeName: KnockoutObservable<string> = ko.observable();
		selectedWorkTimeDisp: KnockoutComputed<string>;
		dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedDateSpec: KnockoutObservable<any> = ko.observable();
		relationshipReason: KnockoutObservable<string> = ko.observable();
		maxNumberOfDay: KnockoutComputed<any>;
		specAbsenceDispInfo: KnockoutObservable<any> = ko.observable();
		isDispMourn: any = ko.observable(false);
		isCheckMourn: any = ko.observable(false);
		requiredVacationTime: KnockoutObservable<number> = ko.observable(0);
		timeRequired: KnockoutObservable<string> = ko.observable();
		leaveComDayOffManas: KnockoutObservableArray<any> = ko.observableArray([]);
		payoutSubofHDManagements: KnockoutObservable<any> = ko.observableArray([]);
		workTypeBefore: KnockoutObservable<any> = ko.observable();
		workTypeAfter: KnockoutObservable<any> = ko.observable();

		yearRemain: KnockoutObservable<number> = ko.observable();
		subHdRemain: KnockoutObservable<number> = ko.observable();
		subVacaRemain: KnockoutObservable<number> = ko.observable();
		remainingHours: KnockoutObservable<number> = ko.observable();

		over60HHourRemain: KnockoutObservable<string> = ko.observable();
		subVacaHourRemain: KnockoutObservable<string> = ko.observable();
		timeYearLeave: KnockoutObservable<string> = ko.observable();
		childNursingRemain: KnockoutObservable<string> = ko.observable();
		nursingRemain: KnockoutObservable<string> = ko.observable();
		isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);
		startTime1: KnockoutObservable<number> = ko.observable();
        endTime1: KnockoutObservable<number> = ko.observable();
        startTime2: KnockoutObservable<number> = ko.observable();
		endTime2: KnockoutObservable<number> = ko.observable();
		
        // 60H超休
        over60H: KnockoutObservable<number> = ko.observable();
        // 時間代休
        timeOff: KnockoutObservable<number> = ko.observable();
        // 時間年休
        annualTime: KnockoutObservable<number> = ko.observable();
        // 子の看護
        childNursing: KnockoutObservable<number> = ko.observable();
        // 介護時間
        nursing: KnockoutObservable<number> = ko.observable();

        isSendMail: KnockoutObservable<Boolean>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;

        // Condition
		condition10: KnockoutObservable<boolean> = ko.observable(true);
		condition11: KnockoutObservable<boolean> = ko.observable(true);
		condition30: KnockoutObservable<boolean> = ko.observable(true);
		condition12: KnockoutObservable<boolean> = ko.observable(true);
		condition19Over60: KnockoutObservable<boolean> = ko.observable(true);
		condition19Substitute: KnockoutObservable<boolean> = ko.observable(true);
		condition19Annual: KnockoutObservable<boolean> = ko.observable(true);
		condition19ChildNursing: KnockoutObservable<boolean> = ko.observable(true);
		condition19Nursing: KnockoutObservable<boolean> = ko.observable(true);
		condition14: KnockoutObservable<boolean> = ko.observable(true);
		condition15: KnockoutObservable<boolean> = ko.observable(true);
		condition21: KnockoutObservable<boolean> = ko.observable(true);
		condition22: KnockoutObservable<boolean> = ko.observable(true);
		condition23: KnockoutObservable<boolean> = ko.observable(true);
		condition24: KnockoutObservable<boolean> = ko.observable(true);
		condition1: KnockoutObservable<boolean> = ko.observable(true);
		condition6: KnockoutObservable<boolean> = ko.observable(true);
		condition7: KnockoutObservable<boolean> = ko.observable(true);
		condition8: KnockoutObservable<boolean> = ko.observable(true);
		condition9: KnockoutObservable<boolean> = ko.observable(true);
        
        created(params: {
            appType: any,
            application: any,
            printContentOfEachAppDto: PrintContentOfEachAppDto,
            approvalReason: any,
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void) => void,
            eventReload: (evt: () => void) => void
        }) {
            const vm = this;

            vm.isSendMail = ko.observable(true);
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.approvalReason = params.approvalReason;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));
        }

        mounted() {
            const vm = this;

            vm.maxNumberOfDay = ko.computed(() => {
				let data = vm.$i18n("KAF006_44").concat("\n");
				if (vm.specAbsenceDispInfo()) {
					vm.dateSpecHdRelationLst(vm.specAbsenceDispInfo().dateSpecHdRelationLst);
					
					if (vm.dateSpecHdRelationLst() && vm.dateSpecHdRelationLst().length > 0) {
						vm.selectedDateSpec(vm.dateSpecHdRelationLst()[0].relationCD);
					}
	
	
					if (vm.isDispMourn() && vm.isCheckMourn()) {
						let param = vm.specAbsenceDispInfo().maxDay + vm.specAbsenceDispInfo().dayOfRela;
						data = data + vm.$i18n("KAF006_46", param.toString());
					} else {
						let param = vm.specAbsenceDispInfo().maxDay;
						data = data + vm.$i18n("KAF006_46", param.toString());
					}

				}
				return data;
            });
            
            vm.selectedWorkTimeDisp = ko.computed(() => {
				const vm = this;

				if (vm.selectedWorkTimeCD()) {
					return vm.selectedWorkTimeCD() + " " + vm.selectedWorkTimeName();
				}

				return vm.$i18n("KAF006_21");
            });
            
            vm.selectedDateSpec.subscribe(() => {
				if (vm.selectedType() !== 3 || vm.dateSpecHdRelationLst().length === 0) {
					return;
				}
				let command = {
					frameNo: vm.specAbsenceDispInfo() ? vm.specAbsenceDispInfo().frameNo : null,
					specHdEvent: vm.specAbsenceDispInfo() ? vm.specAbsenceDispInfo().specHdEvent : null,
					relationCD: vm.selectedDateSpec()
				};

				vm.$blockui("show");
                vm.$ajax(API.changeRela, command).done((success) => {
					if (success) {
						if (vm.specAbsenceDispInfo()) {
							vm.specAbsenceDispInfo().maxDay = success.maxDayObj.maxDay;
							vm.specAbsenceDispInfo().dayOfRela = success.maxDayObj.dayOfRela;
						}
					}
                }).fail((error) => {
					if (error) {
						vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
					}
                }).always(() => {
                    vm.$blockui("hide");
                })
			});
        }

        reload() {
			const vm = this;
			if(vm.appType() === AppType.ABSENCE_APPLICATION) {
				vm.createParamKAF006();
			}
        }

        update() {
            const vm = this;
        }
        
        private createParamKAF006() {
            const vm = this;

            let command = {

            };

            vm.$blockui("show");
            vm.$ajax(API.initPageB, command)
                .done((success) => {
                    vm.fetchData(success);
                }).fail((error) => {
                    vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
                }).always(() => vm.$blockui('hide'));
        }

        private fetchData(data: any) {

        }
    };

    const API = {
        initPageB: 'at/request/application/appforleave/getAppForLeaveStartB',
        changeRela: 'at/request/application/appforleave/changeRela'
    };
}