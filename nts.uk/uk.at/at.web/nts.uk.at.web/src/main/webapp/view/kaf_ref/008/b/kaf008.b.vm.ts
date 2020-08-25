module nts.uk.at.view.kaf008_ref.b.viewmodel {
    //import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;


    @component({
        name: 'kaf008-b',
        template: '/nts.uk.at.web/view/kaf_ref/008/b/index.html'
    })
    class Kaf008BViewModel extends ko.ViewModel {

        approvalReason: KnockoutObservable<string>;
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        model: Model;
        dataFetch: KnockoutObservable<any> = ko.observable(null);
        mode: string = 'edit';
        applicationTest: any = {
            version: 1,
            // appID: '939a963d-2923-4387-a067-4ca9ee8808zz',
            prePostAtr: 1,
            employeeID: this.$user.employeeId,
            appType: 3,
            appDate: moment(new Date()).format('YYYY/MM/DD'),
            enteredPerson: '1',
            inputDate: moment(new Date()).format('YYYY/MM/DD HH:mm:ss'),
            reflectionStatus: {
                listReflectionStatusOfDay: [{
                    actualReflectStatus: 1,
                    scheReflectStatus: 1,
                    targetDate: '2020/01/07',
                    opUpdateStatusAppReflect: {
                        opActualReflectDateTime: '2020/01/07 20:11:11',
                        opScheReflectDateTime: '2020/01/07 20:11:11',
                        opReasonActualCantReflect: 1,
                        opReasonScheCantReflect: 0

                    },
                    opUpdateStatusAppCancel: {
                        opActualReflectDateTime: '2020/01/07 20:11:11',
                        opScheReflectDateTime: '2020/01/07 20:11:11',
                        opReasonActualCantReflect: 1,
                        opReasonScheCantReflect: 0
                    }
                }]
            },
            opStampRequestMode: 1,
            opReversionReason: '1',
            opAppStartDate: '2020/08/07',
            opAppEndDate: '2020/08/08',
            opAppReason: 'jdjadja',
            opAppStandardReasonCD: 1


        };

        created(
            params: {
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void ) => void
            }
        ) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = ko.observable(new Application(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appID, 3, [], 2, "", "", 0));
            if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                vm.mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
            }
            vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;
            vm.createParamKAF008();

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
        }

        mounted() {
            const vm = this;
        }

        createParamKAF008() {
            let vm = this;
            vm.$blockui('show');
            vm.$ajax(API.getDetail, {
                companyId: vm.$user.companyId,
                applicationId: ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.application.appID
            }).done(res => {
                console.log(res);
                // if (res) {
                //     vm.dataFetch({
                //         workType: ko.observable(res.workType),
                //         workTime: ko.observable(res.workTime),
                //         appDispInfoStartup: ko.observable(res.appDispInfoStartup),
                //         goBackReflect: ko.observable(res.goBackReflect),
                //         lstWorkType: ko.observable(res.lstWorkType),
                //         goBackApplication: ko.observable(res.goBackApplication)
                //     });
                // }
            }).fail(err => {
                vm.$dialog.error({messageId: err.msgId});
            }).always(() => vm.$blockui('hide'));
        }

        // event update cần gọi lại ở button của view cha
        update() {

        }

        dispose() {
            const vm = this;

        }

        // changeWorkTypeCode(data: BusinessTripInfoOutputDto, date: string, wkCode: string, index: number) {
        //     const vm = this;
        //     let businessTripInfoOutputDto = ko.toJS(data);
        //     let typeCode = wkCode;
        //     let command = {
        //         date, businessTripInfoOutputDto, typeCode
        //     }
        //     let cloneOutput = _.clone(vm.businessTripOutput());
        //     vm.$ajax(API.changeWorkTypeCode, command).done(data => {
        //         let contentAfterChange = data;
        //         vm.businessTripOutput(data);
        //         let workTypeAfterChange = data.infoAfterChange;
        //         let InfoChanged = _.findIndex(workTypeAfterChange, {date: date});
        //         let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
        //         let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;
        //         cloneOutput.businessTripActualContent[index].opAchievementDetail.workTypeCD = workCodeChanged;
        //         cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTypeName = workNameChanged;
        //         vm.businessTripOutput(cloneOutput);
        //     }).fail(err => {
        //         cloneOutput.businessTripActualContent[index].opAchievementDetail.workTypeCD = "";
        //         cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTypeName = "";
        //         vm.businessTripOutput(cloneOutput);
        //         let param;
        //         if (err.message && err.messageId) {
        //             param = {messageId: err.messageId};
        //         } else {
        //             if (err.message) {
        //                 param = {message: err.message};
        //             } else {
        //                 param = {messageId: err.messageId};
        //             }
        //         }
        //         vm.$dialog.error(param);
        //     }).always(() => vm.$blockui("hide"));;
        // }
        //
        // changeWorkTimeCode(data: BusinessTripInfoOutputDto, date: string, wkCode: string, timeCode: string, index: number) {
        //     const vm = this;
        //     let inputDate = date;
        //     let businessTripInfoOutputDto = ko.toJS(null);
        //     let typeCode = wkCode;
        //     let command = {
        //         date, businessTripInfoOutputDto, typeCode, timeCode
        //     }
        //     vm.$ajax(API.changWorkTimeCode, command).done(data => {
        //         let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: inputDate});
        //         let currentDetail = vm.businessTripOutput().businessTripActualContent[selectedIndex].opAchievementDetail;
        //         vm.items()[selectedIndex].wkTimeName(data);
        //         currentDetail.opWorkTimeName = data;
        //     }).fail(err => {
        //         let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: inputDate});
        //         let currentDetail = vm.businessTripOutput().businessTripActualContent[selectedIndex].opAchievementDetail;
        //         vm.items()[selectedIndex].wkTimeCd("");
        //         vm.items()[selectedIndex].wkTimeName("");
        //         currentDetail.workTimeCD = "";
        //         currentDetail.opWorkTimeName = "";
        //     });
        // }

    }

    const API = {
        getDetail: "at/request/application/businesstrip/getDetailPC"
    }
}