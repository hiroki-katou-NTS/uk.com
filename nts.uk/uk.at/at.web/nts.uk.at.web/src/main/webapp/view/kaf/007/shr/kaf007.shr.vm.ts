module nts.uk.at.view.kaf007_ref.shr.viewmodel {

    @component({
        name: 'kaf007-share',
        template: '/nts.uk.at.web/view/kaf/007/shr/index.html'
    })
    class Kaf007ShareViewModel extends ko.ViewModel {
        model: KnockoutObservable<ModelDto>;
        mode: number;
        appWorkChange: AppWorkChange;
        created(params: any) {
            const vm = this;

            vm.model = params.model;
            vm.mode = params.mode;
            vm.appWorkChange = params.appWorkChange;
        }

        mounted() {

        }

        openKDL003Click() {
            const vm = this;

            vm.$window.storage('parentCodes', {
                workTypeCodes: _.map(_.uniqBy(vm.model().workTypeLst, e => e.workTypeCode), item => item.workTypeCode),
                selectedWorkTypeCode: vm.appWorkChange.workTypeCode(),
                workTimeCodes: _.map(vm.model().appDispInfoStartupOutput().appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode),
                selectedWorkTimeCode: vm.appWorkChange.workTimeCode()
            });

            vm.$window.modal('/view/kdl/003/a/index.xhtml').then((result: any) => {
                vm.$window.storage('childData').then(rs => {
                    console.log(rs);
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        vm.appWorkChange.workTypeCode(childData.selectedWorkTypeCode);
                        vm.appWorkChange.workTypeName(childData.selectedWorkTypeName);
                        vm.appWorkChange.workTimeCode(childData.selectedWorkTimeCode);
                        vm.appWorkChange.workTimeName(childData.selectedWorkTimeName);
                    }
                });
            });
        }
    }

    export class AppWorkChange {
        workTypeCode: KnockoutObservable<string>;
        workTypeName: KnockoutObservable<String>;
        workTimeCode: KnockoutObservable<string>;
        workTimeName: KnockoutObservable<String>;
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;
        startTime2: KnockoutObservable<number>;
        endTime2: KnockoutObservable<number>;
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime1: number, endTime1: number, startTime2: number, endTime2: number) {
            this.workTypeCode = ko.observable(workTypeCode);
            this.workTypeName = ko.observable(workTypeName);
            this.workTimeCode = ko.observable(workTimeCode);
            this.workTimeName = ko.observable(workTimeName);
            this.startTime1 = ko.observable(startTime1);
            this.endTime1 = ko.observable(endTime1);
            this.startTime2 = ko.observable(startTime2);
            this.endTime2 = ko.observable(endTime2);
        }
    }

    export class ReflectWorkChangeApp {
        companyId: string;
        whetherReflectAttendance: number;

        constructor(companyID: string, whetherReflectAttendance: number) {
            this.companyId = companyID;
            this.whetherReflectAttendance = whetherReflectAttendance;
        }
    }

    export class ModelDto {

        workTypeCode: KnockoutObservable<string>;

        workTimeCode: KnockoutObservable<string>;

        appDispInfoStartupOutput: KnockoutObservable<any>;

        reflectWorkChange: KnockoutObservable<ReflectWorkChangeApp> = ko.observable(null);

        workTypeLst: any;

        setupType: KnockoutObservable<number>;

        predetemineTimeSetting: KnockoutObservable<any>;

        appWorkChangeSet: any;
    }
}