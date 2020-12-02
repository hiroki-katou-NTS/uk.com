/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
import WorkType = nts.uk.at.view.kaf006.shr.viewmodel.WorkType;

module nts.uk.at.view.kaf006_ref.a.viewmodel {

    @bean()
    export class Kaf006AViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.ABSENCE_APPLICATION);
        isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
        isSendMail: KnockoutObservable<Boolean> = ko.observable(false);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		data: any = null;
		hdAppSet: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedType: KnockoutObservable<any> = ko.observable();
		workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedWorkTypeCD: KnockoutObservable<string> = ko.observable(null);
		selectedWorkType: KnockoutObservable<WorkType> = ko.observable(new WorkType({workTypeCode: '', name: ''}));
		dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedDateSpec: KnockoutObservable<any> = ko.observable();
		maxNumberOfDay: KnockoutObservable<any> = ko.observable();
		specAbsenceDispInfo: KnockoutObservable<any> = ko.observable();


        created(params: AppInitParam) {
            const vm = this;

			let empLst: Array<string> = [],
				dateLst: Array<string> = [];
			if (!_.isEmpty(params)) {
				if (!_.isEmpty(params.employeeIds)) {
					empLst = params.employeeIds;
				}
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateLst = [paramDate];
					vm.application().appDate(paramDate);
					vm.application().opAppStartDate(paramDate);
                    vm.application().opAppEndDate(paramDate);
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
            }
            
            vm.$blockui("show");
			vm.loadData(empLst, dateLst, vm.appType())
				.then((loadDataFlag: any) => {
					if (loadDataFlag) {
						let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput);
						return vm.$ajax(API.startNew, appDispInfoStartupOutput);
					}
				}).then((successData: any) => {
					if (successData) {
						console.log(successData);

						vm.data = successData;
						let hdAppSetInput: any[] = vm.data.hdAppSet.dispNames;
						if (hdAppSetInput && hdAppSetInput.length > 0) {
							vm.hdAppSet(hdAppSetInput);
						}
					}
				}).fail((failData: any) => {
					console.log(failData);
				}).always(() => {
                    vm.$blockui("hide");
                });
        }

        mounted() {
			const vm = this;
			
			vm.selectedWorkTypeCD.subscribe(() => {
				if (_.isNil(vm.selectedWorkTypeCD()) || _.isEmpty(vm.workTypeLst())) {
					return;
				}

				// vm.selectedWorkType(new WorkType({workTypeCode: vm.selectedWorkTypeCD(), name: _.filter(vm.workTypeLst(), ['workTypeCode', vm.selectedWorkTypeCD()])[0].name}));
			});
			
			// check selected item
            vm.selectedType.subscribe(() => {
				console.log(this.selectedType())
				let appDates = [];
				if (_.isNil(vm.application().opAppStartDate())) {
					appDates.push(vm.application().opAppStartDate());
				}
				if (_.isNil(vm.application().opAppEndDate()) && vm.application().opAppStartDate() !== vm.application().opAppEndDate()) {
					appDates.push(vm.application().opAppEndDate());
				}

                let command = {
					companyID: __viewContext.user.companyId,
					appDates: appDates,
					startInfo: vm.data,
					holidayAppType: vm.selectedType()
				};

                vm.$blockui("show");
                vm.$ajax(API.getAllAppForLeave, command).done((result) => {
					vm.fetchData(result);
                }).fail((fail) => {

                }).always(() => {
                    vm.$blockui("hide");
                })
            });
		}
		
		fetchData(data: any) {
			const vm = this;

			vm.data = data;
			vm.selectedWorkTypeCD(data.selectedWorkTypeCD);
			vm.workTypeLst(_.forEach(data.workTypeLst, item => item.name = item.workTypeCode + ' ' + item.name));
			vm.appDispInfoStartupOutput(data.appDispInfoStartupOutput);
			vm.specAbsenceDispInfo(data.specAbsenceDispInfo);

			if (vm.specAbsenceDispInfo()) {
				vm.dateSpecHdRelationLst(vm.specAbsenceDispInfo().dateSpecHdRelationLst);
				
				if (vm.dateSpecHdRelationLst() && vm.dateSpecHdRelationLst().length > 0) {
					vm.selectedDateSpec(vm.dateSpecHdRelationLst()[0].relationCD);
				}

				vm.maxNumberOfDay(vm.$i18n("KAF006_44").concat("\n"));
			}


		}

        register() {

        }

        annualHolidayRefer() {

        }

        openHolidays() {

		}
		
		public isDispMourn() {
			const vm = this;

			ko.computed(() => {
				if (vm.specAbsenceDispInfo()) {
					if (vm.specAbsenceDispInfo().specHdForEventFlag && vm.specAbsenceDispInfo().specHdEvent.maxNumberDay === 2 && vm.specAbsenceDispInfo().specHdEvent.makeInvitation === 1) {
						return true;
					}
				}

				return false;
			}, vm);
		}
    }

    const API = {
		startNew: 'at/request/application/appforleave/getAppForLeaveStart',
		getAllAppForLeave: 'at/request/application/appforleave/getAllAppForLeave'
    }
}