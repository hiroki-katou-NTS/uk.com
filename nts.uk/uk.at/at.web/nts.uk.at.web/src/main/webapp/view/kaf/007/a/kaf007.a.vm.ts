/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf007_ref.a.viewmodel {
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange;
	import ModelDto = nts.uk.at.view.kaf007_ref.shr.viewmodel.ModelDto;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
	import ReflectWorkChangeApp = nts.uk.at.view.kaf007_ref.shr.viewmodel.ReflectWorkChangeApp;

	@bean()
	export class Kaf007AViewModel extends Kaf000AViewModel {
		
		appType: KnockoutObservable<number> = ko.observable(AppType.WORK_CHANGE_APPLICATION);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		model: KnockoutObservable<ModelDto> = ko.observable(null);
		isSendMail: KnockoutObservable<Boolean>;
		reflectWorkChange: ReflectWorkChangeApp;
		appWorkChange: AppWorkChange;
		setupType: number;
		workTypeLst: any[];
		comment: KnockoutObservable<string> = ko.observable("");

		created(params: any) {
			const vm = this;
			vm.isSendMail = ko.observable(false);
			vm.reflectWorkChange = new ReflectWorkChangeApp("", 1);
			vm.setupType = null;
			vm.appWorkChange = new AppWorkChange("", "", "", "", 0, 0, 0, 0);

            vm.$blockui("show");
            vm.loadData([], [], vm.appType())
            .then((loadDataFlag: any) => {
                if(loadDataFlag) {
                    let empLst = [],
                        dateLst = [],
                        appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                        command = { empLst, dateLst, appDispInfoStartupOutput };
                    return vm.$ajax(API.startNew, command);
                }
            }).then((successData: any) => {
                if(successData) {
					console.log(successData);
					vm.fetchData(successData);
                }
            }).fail((failData: any) => {
				console.log(failData);         
            }).always(() => vm.$blockui("hide"));
		}

		mounted() {
			const vm = this;

            vm.application.subscribe(app => {
               if (app) {
                   let startDate = app.opAppStartDate();
                   let endDate = app.opAppEndDate();
                   let checkFormat = vm.validateAppDate(startDate, endDate);

                   if (checkFormat) {
                       vm.changeAppDate();
                   }
               }
            });
		}

		validateAppDate(start:string , end: string) {
            let startDate = moment(start);
            let endDate = moment(end);
            if (startDate.isValid() && endDate.isValid()) {
                return true;
            }
            return false;
		}
		
		changeAppDate() {
            const vm = this;

            vm.$errors("clear");

            // vm.$validate([
            //     '#kaf000-a-component4 .nts-input'
            // ]).then((valid: boolean) => {
            //     if (valid) {
            //         return vm.$blockui("show").then(() => vm.$ajax(API.changeAppDate, command));
            //     }
            // }).done((res: any) => {
            //     if (res.result) {
            //         let output = res.businessTripInfoOutputDto;

            //         vm.dataFetch().businessTripOutput = output;
            //         vm.dataFetch.valueHasMutated();
            //     }
            // }).fail(err => {
            //     vm.dataFetch().businessTripOutput.businessTripActualContent = [];
            //     vm.dataFetch.valueHasMutated();
            //     vm.handleError(err);
            // }).always(() => vm.$blockui("hide"));
        }
		
		fetchData(params: any) {
			const vm = this;
			vm.model({
				workTypeCode: ko.observable(params.workTypeCD),
				workTimeCode: ko.observable(params.workTimeCD),
				appDispInfoStartupOutput: ko.observable(params.appDispInfoStartupOutput),
				reflectWorkChange: ko.observable(params.reflectWorkChangeAppDto),
				workTypeLst: params.workTypeLst,
				setupType: ko.observable(params.setupType),
				predetemineTimeSetting: ko.observable(params.predetemineTimeSetting),
				appWorkChangeSet: params.appWorkChangeSet
			});
			vm.getWorkDispName(params.workTypeLst, 
				params.workTypeCD, 
				params.workTimeCD, 
				params.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst);
			if(params.appWorkChangeSet.initDisplayWorktimeAtr === 1) {
				vm.appWorkChange.startTime1(null);
				vm.appWorkChange.endTime1(null);
				vm.appWorkChange.startTime2(null);
				vm.appWorkChange.endTime2(null);
			} else {
				var lstTimezone = params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone;
				var time1 = _.filter(lstTimezone, ['workNo', 1]);
				var time2 = _.filter(lstTimezone, ['workNo', 2]);

				vm.appWorkChange.startTime1(time1.length > 0 ? time1[0].start : null);
				vm.appWorkChange.endTime1(time1.length > 0 ? time1[0].end : null);
				vm.appWorkChange.startTime2(time2.length > 0 ? time2[0].start : null);
				vm.appWorkChange.endTime2(time2.length > 0 ? time2[0].end : null);
			}
			vm.comment(vm.model().appWorkChangeSet.comment1.comment);
			$("#comment1")
				.css("color", vm.model().appWorkChangeSet.comment1.colorCode)
				.css("fontWeight", vm.model().appWorkChangeSet.comment1.bold == true ? "bold" : "");
		}

		getWorkDispName(workTypeLst: any, workTypeCode: string, workTimeCode: string, workTimeLst: any) {
			const vm = this;

			vm.appWorkChange.workTimeCode(workTimeCode);
			vm.appWorkChange.workTypeCode(workTypeCode);
			var dataWorkType = _.filter(workTypeLst, (x) => { return workTypeCode === x.workTypeCode});
			vm.appWorkChange.workTypeName(dataWorkType.length > 0 ? dataWorkType[0].name : vm.$i18n('KAF007_79'));
			var dataWorktTime = _.filter(workTimeLst, (x) => { return workTimeCode === x.worktimeCode});
			vm.appWorkChange.workTimeName(dataWorktTime.length > 0 ? dataWorktTime[0].workTimeDisplayName.workTimeName : vm.$i18n('KAF007_79'));
		}

		register() {

		}
		// register() {
        //     const vm = this;
        //     let workChange = ko.toJS(vm.appWorkChange),
        //         application = ko.toJS(vm.application),
        //         appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
        //         command = { workChange, application, appDispInfoStartupOutput };
		// 	vm.$validate([
		// 		'.ntsControl',
		// 		'.nts-input'
		// 	]).then((valid: boolean) => {
		// 		if(valid) {
		// 			return vm.$blockui("show").then(() => vm.$ajax(API.register, command));
		// 		}
		// 	}).done((data: any) => {
		// 		if(data) {
		// 			vm.$dialog.info({ messageId: "Msg_15" });	
		// 		}
		// 	})
        // 	.always(() => vm.$blockui("hide"));
		// }

		conditionA14() {
			const vm = this;

			return ko.computed(() => {
				if(vm.model().setupType() !== null && vm.model().setupType() === 0 && vm.model().reflectWorkChange().whetherReflectAttendance === 1) {
					return true;
				};
				return false;
			}, vm);
		}
	}

	const API = {
		startNew: "at/request/application/workchange/startNew",
		register: "at/request/application/workchange/addworkchange_PC"
	}
}