module nts.uk.at.view.kaf008_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    import BusinessTripInfoDetail = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripInfoDetail;
    import BusinessTripOutput = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripOutput;
    import BusinessTripContent = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripContent;



    @bean()
    class Kaf008AViewModel extends Kaf000AViewModel {

        appType: KnockoutObservable<number> = ko.observable(AppType.BUSINESS_TRIP_APPLICATION);
        application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));

        businessTripContent: KnockoutObservable<BusinessTripContent> = ko.observable({
            departureTime: null,
            returnTime: null,
            tripInfos: []
        });
        businessTripOutput: KnockoutObservable<BusinessTripOutput> = ko.observable();
        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable(null);

        created(params: any) {
            const vm = this;

            vm.loadData([], [], vm.appType())
                .then((loadDataFlag: boolean) => {
                    if (loadDataFlag) {
                        const applicantList = [];
                        const dateLst = [];
                        const appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput);
                        const command = {applicantList, dateLst, appDispInfoStartupOutput};

                        return vm.$ajax(API.startNew, command);
                    }

                    return null;
                }).then((successData: Model | null) => {
                if (successData) {
                    const {result, businessTripInfoOutputDto} = successData;

                    if (result) {
                        if (businessTripInfoOutputDto) {
                            vm.businessTripOutput(businessTripInfoOutputDto);
                            vm.dataFetch({
                                businessTripContent: ko.toJS(vm.businessTripContent),
                                businessTripOutput: ko.toJS(vm.businessTripOutput)
                            });
                        }
                    } else {
                        successData.confirmMsgOutputs.forEach(i => {
                            vm.$dialog.error({messageId: i.msgID})
                        });
                    }
                }
            }).fail((failData: any) => {
                console.log(failData);
            }).always(() => vm.$blockui("hide"));
        }

        mounted() {
            const vm = this;
            vm.application().opAppEndDate.subscribe(value => {
                console.log(value);
                if (value) {
                    vm.changeAppDate();
                }
            });
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
                    let output = res.businessTripInfoOutputDto;
                    vm.businessTripOutput(output);

                    let dataFetch = _.clone(vm.dataFetch());
                    dataFetch.businessTripOutput = output;
                    vm.dataFetch(dataFetch);
                } else {
                    console.log(res.confirmMsgOutputs);
                }
            }).fail(res => {
                console.log(res);
            }).always(() => vm.$blockui("hide"));
        }

        register() {
            const vm = this;
            const dataFetch = ko.toJS(vm.dataFetch);
            const tripOutput = dataFetch.businessTripOutput;
            const tripContent = dataFetch.businessTripContent;

            let lstContent: Array<BusinessTripInfoDetail> = _.map(tripOutput.businessTripActualContent, function (i) {
                return {
                    date: i.date,
                    wkTypeCd: i.opAchievementDetail.workTypeCD,
                    wkTimeCd: i.opAchievementDetail.workTimeCD,
                    startWorkTime: i.opAchievementDetail.opWorkTime,
                    endWorkTime: i.opAchievementDetail.opLeaveTime
                }
            });
            let businessTripDto: any = {
                departureTime: tripContent.departureTime,
                returnTime: tripContent.returnTime,
                tripInfos: lstContent
            };

            let applicationDto = ko.toJS(vm.application);
            let command = {
                businessTripDto: businessTripDto,
                businessTripInfoOutputDto: tripOutput,
                applicationDto: applicationDto
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
                    vm.$dialog.info({messageId: "Msg_15"});
                }
            }).fail(res => {
                console.log(res);
                let param;
                if (res.message && res.messageId) {
                    param = {messageId: res.messageId};
                } else {

                    if (res.message) {
                        param = {message: res.message};
                    } else {
                        param = {messageId: res.messageId};
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

    interface DetailSreenInfo {
        businessTripContent: BusinessTripContent;
        businessTripOutput: BusinessTripOutput;
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