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
        mode: number = 1;
        isSendMail: KnockoutObservable<boolean>;
        businessTripOutput: KnockoutObservable<BusinessTripOutput> = ko.observable(null);

        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable({
            businessTripContent: {
                departureTime: ko.observable(null),
                returnTime: ko.observable(null),
                tripInfos: []
            },
            businessTripOutput: null
        });

        appDate: KnockoutObservable<any> = ko.observable(null);

        created(params: any) {
            const vm = this;

            vm.isSendMail = ko.observable(false);

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
                            let cloneData = _.clone(vm.dataFetch());
                            cloneData.businessTripOutput = businessTripInfoOutputDto;
                            vm.dataFetch(cloneData);
                            vm.businessTripOutput(businessTripInfoOutputDto)
                        }
                    }
                }
            }).fail((err: any) => {
                vm.handleError(err)
            }).then(() =>{
                vm.focusDate();
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

            let applicationDto = ko.toJS(vm.application);
            let businessTripInfoOutputDto = ko.toJS(vm.dataFetch().businessTripOutput);

            businessTripInfoOutputDto.appDispInfoStartup = ko.toJS(vm.appDispInfoStartupOutput);

            let command = {
                businessTripInfoOutputDto, applicationDto
            };

            vm.$validate([
                '#kaf000-a-component4 .nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$blockui("show").then(() => vm.$ajax(API.changeAppDate, command));
                }
            }).done((res: any) => {
                if (res.result) {
                    let output = res.businessTripInfoOutputDto;

                    vm.dataFetch().businessTripOutput = output;
                    vm.dataFetch.valueHasMutated();
                }
            }).fail(err => {
                vm.dataFetch().businessTripOutput.businessTripActualContent = [];
                vm.dataFetch.valueHasMutated();
                vm.handleError(err);
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
                businessTrip: businessTripDto,
                businessTripInfoOutput: tripOutput,
                application: applicationDto
            };

            vm.$blockui( "show" );
            vm.$validate([
                '.ntsControl',
                '.nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    vm.$ajax(API.checkBeforeRegister, command).done((res: any) => {
                        if ( _.isEmpty( res ) ) {
                            vm.registerData(command);
                        } else {
                            vm.handleConfirmMessage(res, command);
                        }
                    }).fail(err => {
                        let param;
                        switch (err.messageId) {
                            case "Msg_24" :
                                param = err.parameterIds[0] + err.message;
                                break;
                            case "Msg_23" :
                                param = err.parameterIds[0] + err.message;
                                break;
                            default: {
                                if (err.message) {
                                    param = {message: err.message, messageParams: err.parameterIds};
                                } else {
                                    param = {messageId: err.messageId, messageParams: err.parameterIds}
                                }
                                break;
                            }
                        }
                        vm.$dialog.error(param);
                    });
                }
            }).always(() => vm.$blockui("hide"));
        }

        registerData(command: any) {
            const vm = this;
            vm.$blockui("show").then(() => vm.$ajax(API.register, command).done( data => {
                if (data) {
                    vm.$dialog.info({messageId: "Msg_15"})
                        .then(() => {
                            location.reload();
                        });
                }
            }).fail(res => {
                vm.handleError(res);
            }));
        }

        focusDate() {
            const vm = this;
            let dateItem = $(vm.$el).find('#kaf000-a-component4-rangeDate');
            if (dateItem.length) {
                dateItem.focus();
            } else {
                $(vm.$el).find('#kaf000-a-component4-singleDate').focus();
            }
        }

        public handleConfirmMessage(listMes: any, res: any) {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                            vm.registerData(res);
                        } else {
                            vm.handleConfirmMessage(listMes, res);
                        }

                    }
                });
            }
        }

        handleError(err: any) {
            const vm = this;
            let param;
            if (err.message && err.messageId) {
                param = {messageId: err.messageId, messageParams: err.parameterIds};
            } else {

                if (err.message) {
                    param = {message: err.message, messageParams: err.parameterIds};
                } else {
                    param = {messageId: err.messageId, messageParams: err.parameterIds};
                }
            }
            vm.$dialog.error(param).then(() => {
                if (err.messageId == 'Msg_197') {
                    location.reload();
                }
            });
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