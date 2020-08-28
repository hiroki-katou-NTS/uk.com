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

        businessTripOutput: KnockoutObservable<any> = ko.observable();
        businessTrip: KnockoutObservable<BusinessTripInfo> = ko.observable();

        created(params: {
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void) => void
        }) {
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
                if (res) {
                    let businessTrip = res.businessTripDto;
                    let eachDetail: TripInfoDetail = _.map(businessTrip.tripInfos, function (detail) {
                        return {
                            date: detail.date,
                            wkTimeCd: detail.wkTimeCd,
                            wkTimeName: null,
                            wkTypeCd: detail.wkTypeCd,
                            wkTypeName: null,
                            startWorkTime: detail.startWorkTime,
                            endWorkTime: detail.endWorkTime
                        };
                    });
                    let tripInfo: BusinessTripInfo = {
                        departureTime: businessTrip.departureTime,
                        returnTime: businessTrip.returnTime,
                        tripInfos: eachDetail
                    }
                    vm.businessTrip(tripInfo);
                    vm.businessTripOutput(res.businessTripInfoOutputDto);
                }
            }).fail(err => {
                vm.$dialog.error({messageId: err.msgId});
            }).always(() => vm.$blockui('hide'));
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            let application = ko.toJS(vm.application);
            vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;
            vm.applicationTest.prePostAtr = application.prePostAtr;
            vm.applicationTest.opAppReason = application.opAppReason;
            vm.applicationTest.opAppStandardReasonCD = application.opAppStandardReasonCD;
            vm.applicationTest.opReversionReason = application.opReversionReason;

            let command = {
                businessTripDto : ko.toJS(vm.businessTrip),
                businessTripInfoOutputDto : ko.toJS(vm.businessTripOutput),
                applicationDto : vm.applicationTest
            }
            vm.$ajax(API.updateBusinessTrip, command).done(res => {
                if (res) {
                    vm.$dialog.info({ messageId: "Msg_15" });
                }
            }).fail(err => {
                let param;
                if (err.message && err.messageId) {
                    param = { messageId: err.messageId };
                } else {
                    if (err.message) {
                        param = { message: err.message };
                    } else {
                        param = { messageId: err.messageId };
                    }
                }
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("show"));;
        }

        dispose() {
            const vm = this;

        }

    }

    export interface BusinessTripInfo {
        departureTime: number;
        returnTime: number;
        tripInfos: TripInfoDetail;
    }

    export interface TripInfoDetail {
        date: string;
        wkTimeCd: string;
        wkTimeName: string;
        wkTypeCd: string;
        wkTypeName: string;
        startWorkTime: number;
        endWorkTime: number;
    }

    interface TripInfo {
        date: string;
        wkTypeCd: string;
        wkTimeCd: string;
        startWorkTime: number;
        endWorkTime: number;
    }

    const API = {
        getDetail: "at/request/application/businesstrip/getDetailPC",
        updateBusinessTrip: "at/request/application/businesstrip/updateBusinessTrip"
    }
}