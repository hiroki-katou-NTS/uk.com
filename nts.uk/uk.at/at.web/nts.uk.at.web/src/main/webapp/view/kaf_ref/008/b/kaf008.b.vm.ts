module nts.uk.at.view.kaf008_ref.b.viewmodel {
    //import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;

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

        businessTripOutput: KnockoutObservable<any> = ko.observable();
        businessTrip: KnockoutObservable<any> = ko.observable();
        printContent: any;

        created(params: {
            application: any,
            printContentOfEachAppDto: PrintContentOfEachAppDto,
            approvalReason: any,
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void ) => void
        }) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
            if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                vm.mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
            }
            vm.createParamKAF008();
            vm.printContent = params.printContentOfEachAppDto;

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
        }

        mounted() {
            const vm = this;
            vm.businessTrip.subscribe(value => {
                vm.printContent.opBusinessTripInfoOutput = value;
            });
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
                    // vm.printContent.opBusinessTripInfoOutput = tripInfo;
                    vm.businessTripOutput(res.businessTripInfoOutputDto);


                }
            }).fail(err => {
                vm.$dialog.error({messageId: err.msgId});
            }).always(() => vm.$blockui('hide'));
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;

            let command = {
                businessTripDto : ko.toJS(vm.businessTrip),
                businessTripInfoOutputDto : ko.toJS(vm.businessTripOutput),
                applicationDto : ko.toJS(vm.application())
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
            }).always(() => vm.$blockui("hide"));;
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