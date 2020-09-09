module nts.uk.at.view.kaf008_ref.b.viewmodel {
    //import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;
    import BusinessTripOutput = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripOutput;
    import BusinessTripContent = nts.uk.at.view.kaf008_ref.shr.viewmodel.BusinessTripContent;
    import Mode = nts.uk.at.view.kaf008_ref.shr.viewmodel.Mode;

    @component({
        name: 'kaf008-b',
        template: '/nts.uk.at.web/view/kaf_ref/008/b/index.html'
    })
    class Kaf008BViewModel extends ko.ViewModel {

        appType: KnockoutObservable<number> = ko.observable(AppType.BUSINESS_TRIP_APPLICATION);
        approvalReason: KnockoutObservable<string>;
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        model: Model;
        mode: number = Mode.Edit;
        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable({
            businessTripContent: {
                departureTime: ko.observable(null),
                returnTime: ko.observable(null),
                tripInfos: []
            },
            businessTripOutput: null
        });
        printContent: any;

        created(params: {
            application: any,
            printContentOfEachAppDto: PrintContentOfEachAppDto,
            approvalReason: any,
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void) => void
        }) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
            if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                vm.mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? Mode.Edit : Mode.View;
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
            vm.appDispInfoStartupOutput.subscribe(value => {
                if (value) {
                    vm.dataFetch().businessTripOutput.appDispInfoStartup = value;
                }
                ;
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
                    let businessTripContent = res.businessTripDto;
                    let eachDetail: Array<any> = _.map(businessTripContent.tripInfos, function (detail) {
                        const workInfo = res.businessTripInfoOutputDto.infoBeforeChange;
                        const timeInfo = res.businessTripInfoOutputDto.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
                        let workName = "";
                        let timeName = "";

                        if (!workName) {
                            let wkDayInfo = _.filter(ko.toJS(workInfo), function (item) {
                                return item.date == detail.date;
                            });
                            if (wkDayInfo.length != 0) {
                                workName = wkDayInfo[0].workTypeDto.name;
                            }
                        }

                        if (!timeName) {
                            let wkTimeInfo = _.filter(ko.toJS(timeInfo), function (item) {
                                return item.worktimeCode == detail.wkTimeCd;
                            });
                            if (wkTimeInfo.length != 0) {
                                timeName = wkTimeInfo[0].workTimeDisplayName.workTimeName;
                            }
                        }

                        return {
                            date: detail.date,
                            wkTimeCd: detail.wkTimeCd == null ? "" : detail.wkTimeCd,
                            wkTimeName: timeName,
                            wkTypeCd: detail.wkTypeCd == null ? "" : detail.wkTypeCd,
                            wkTypeName: workName,
                            startWorkTime: detail.startWorkTime,
                            endWorkTime: detail.endWorkTime
                        };
                    });

                    let cloneData = _.clone(vm.dataFetch());

                    cloneData.businessTripContent.departureTime(businessTripContent.departureTime);
                    cloneData.businessTripContent.returnTime(businessTripContent.returnTime);
                    cloneData.businessTripContent.tripInfos = eachDetail;

                    vm.printContent.opBusinessTripInfoOutput = cloneData.businessTripContent;

                    cloneData.businessTripOutput = res.businessTripInfoOutputDto;

                    vm.dataFetch(cloneData);
                }
            }).fail(err => {
                vm.$dialog.error({messageId: err.msgId});
            }).always(() => vm.$blockui('hide'));
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;

            let dataFetch = ko.toJS(vm.dataFetch);

            let command = {
                businessTrip: dataFetch.businessTripContent,
                businessTripInfoOutput: dataFetch.businessTripOutput,
                application: ko.toJS(vm.application())
            };

            vm.$blockui("show");

            return vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then((valid: boolean) => {
                    if (valid) {
                        return vm.$ajax(API.updateBusinessTrip, command)
                    }
                }).done(res => {
                    if (res) {
                        if (res) {
                            vm.printContent.opBusinessTripInfoOutput = dataFetch.businessTripContent;
                            vm.$dialog.info({messageId: "Msg_15"}).then(() => $(vm.$el).find('#A5_3').focus());
                        }
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
                }).always(() => vm.$blockui("hide"));
        }

        dispose() {
            const vm = this;

        }

    }

    interface DetailSreenInfo {
        businessTripContent: BusinessTripContent;
        businessTripOutput: BusinessTripOutput;
    }

    export interface BusinessTripInfo {
        departureTime: KnockoutObservable<number>;
        returnTime: KnockoutObservable<number>;
        tripInfos: Array<TripInfoDetail>;
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