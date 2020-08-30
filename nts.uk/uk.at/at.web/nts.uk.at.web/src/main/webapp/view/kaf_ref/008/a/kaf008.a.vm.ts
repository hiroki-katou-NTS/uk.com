/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf008_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    import BusinessTripInfoDetail = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripInfoDetail;

    @bean()
    export class Kaf008AViewModel extends Kaf000AViewModel {
        application: KnockoutObservable<Application> = ko.observable(new Application(AppType.BUSINESS_TRIP_APPLICATION));
        businessTripContent: KnockoutObservable<TripContent> = ko.observable({
            departureTime: ko.observable(null),
            returnTime: ko.observable(null),
            contentDisp: ko.observableArray([])
        });
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutput> = ko.observable();

        created(params: any) {
            const vm = this;

            vm.loadData([], [], AppType.BUSINESS_TRIP_APPLICATION)
                .then((loadDataFlag: boolean) => {
                    if (loadDataFlag) {
                        const applicantList = [];
                        const dateLst = [];
                        const appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput);
                        const command = { applicantList, dateLst, appDispInfoStartupOutput };

                        return vm.$ajax(API.startNew, command);
                    }

                    return null;
                }).then((successData: Model | null) => {
                    if (successData) {
                        const { result, businessTripInfoOutputDto } = successData;

                        if (result) {
                            if (businessTripInfoOutputDto) {
                                vm.businessTripContent({
                                    departureTime: ko.observable(null),
                                    returnTime: ko.observable(null),
                                    contentDisp: ko.observableArray([])
                                });

                                vm.businessTripOutput(businessTripInfoOutputDto);
                            }
                        } else {
                            successData.confirmMsgOutputs
                                .forEach(i => {
                                    vm.$dialog.error({ messageId: i.msgID })
                                });
                        }
                    }
                }).fail((failData: any) => {
                    console.log(failData);
                }).always(() => vm.$blockui("hide"));


            // Dummy Grid data

        }

        mounted() {
            const vm = this;

            vm.application().opAppEndDate
            .subscribe(value => {
                console.log(value);
                if (value) {
                    vm.changeAppDate();
                }
            })
        }

        changeAppDate() {
            const vm = this;

            let applicationDto = ko.toJS(vm.application);
            let businessTripInfoOutputDto = ko.toJS(vm.businessTripOutput());
            let command = {
                businessTripInfoOutputDto, applicationDto
            };

            vm.$validate([
                // '.ntsControl',
                // '.nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$blockui("show").then(() => vm.$ajax(API.changeAppDate, command));
                }
            }).done((res: any) => {
                if (res.result) {
                    let content = res.businessTripInfoOutputDto;

                    vm.businessTripOutput(content);
                } else {
                    console.log(res.confirmMsgOutputs);
                }
            }).fail(res => {
                console.log(res);
            }).always(() => vm.$blockui("hide"));
        }

        register() {
            const vm = this;
            const businessTripInfoOutputDto= ko.toJS(vm.businessTripOutput);

            let lstContent: Array<BusinessTripInfoDetail> = _.map(businessTripInfoOutputDto.businessTripActualContent, function (i) {
                return {
                    date: i.date,
                    wkTypeCd: i.opAchievementDetail.workTypeCD,
                    wkTimeCd: i.opAchievementDetail.workTimeCD,
                    startWorkTime: i.opAchievementDetail.opWorkTime,
                    endWorkTime: i.opAchievementDetail.opLeaveTime
                }
            });
            let businessTripDto: any = {
                departureTime: vm.businessTripContent().departureTime(),
                returnTime: vm.businessTripContent().returnTime(),
                tripInfos: lstContent
            };

            let applicationDto = ko.toJS(vm.application);
            let command = {
                businessTripDto,
                businessTripInfoOutputDto,
                applicationDto
            };

            vm.$validate([
                '.ntsControl',
                '.nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$blockui("show").then(() => vm.$ajax(API.register, command));
                }
            }).done((res: any) => {
                if (res) {
                    vm.$dialog.info({ messageId: "Msg_15" });
                }
            }).fail(res => {
                console.log(res);
                let param;
                if (res.message && res.messageId) {
                    param = { messageId: res.messageId };
                } else {

                    if (res.message) {
                        param = { message: res.message };
                    } else {
                        param = { messageId: res.messageId };
                    }
                }
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }
    }

    interface Model {
        result: boolean;
        confirmMsgOutputs: Array<MessageOutput>;
        businessTripInfoOutputDto: BusinessTripInfoOutput;
    }

    interface Setting {
        cid: string;
        appComment: any;
        contractCheck: number;
    }

    export interface BusinessTripInfoOutput {
        setting: Setting;
        appDispInfoStartup: any;
        holidays: any;
        workdays: any;
        businessTripActualContent: any;
        infoBeforeChange: any;
        infoAfterChange: any;
    }

    interface MessageOutput {
        msgID: string;
        paramLst: Array<string>
    }

    export interface TripContent {
        departureTime: KnockoutObservable<number>;
        returnTime: KnockoutObservable<number>;
        contentDisp: KnockoutObservableArray<any>;
    }

    const API = {
        startNew: "at/request/application/businesstrip/start",
        checkBeforeRegister: "at/request/application/businesstrip/checkBeforeRegister",
        register: "at/request/application/businesstrip/register",
        changeAppDate: "at/request/application/businesstrip/changeAppDate"
    }

}