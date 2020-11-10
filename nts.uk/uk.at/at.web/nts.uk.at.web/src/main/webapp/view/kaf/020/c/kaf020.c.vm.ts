module nts.uk.at.view.kaf020.c.viewmodel {
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import OptionalItemApplicationContent = nts.uk.at.view.kaf020.shr.viewmodel.Content;
    import Optional = nts.uk.util.optional.Optional;

    @component({
        name: 'kaf020-c',
        template: `<div>
    <div data-bind="component: { name: 'kaf000-b-component1',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component2',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component3',
                                params: {
                                    appType: appType,
                                    approvalReason: approvalReason,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div class="table">
        <div class="cell" style="width: 825px;" data-bind="component: { name: 'kaf000-b-component4',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: appDispInfoStartupOutput
                            } }"></div>
        <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
                            } }"></div>
    </div>
    <div data-bind="component: { name: 'kaf000-b-component5',
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component6',
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf020-share', params: {dataFetch: dataFetch }}"></div>
    <div data-bind="component: { name: 'kaf000-b-component7',
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component8',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
</div>`,
    })

    export class Kaf020CViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.OPTIONAL_ITEM_APPLICATION);
        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable({
            applicationContents: ko.observableArray([]),
            code: ko.observable('')
        });
        isSendMail: KnockoutObservable<Boolean>;
        application: KnockoutObservable<Application>;
        printContent: any;
        approvalReason: KnockoutObservable<string>;
        appDispInfoStartupOutput: any;

        created(params: {
            appType: any,
            application: any,
            printContentOfEachAppDto: PrintContentOfEachAppDto,
            approvalReason: any,
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void) => void,
            eventReload: (evt: () => void) => void
        }) {
            const vm = this;
            vm.application = params.application;
            vm.appType = params.appType;
            vm.printContent = params.printContentOfEachAppDto;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.createParamKAF020();
            vm.approvalReason = params.approvalReason;
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
            vm.isSendMail = ko.observable(true);
        }

        // 起動する
        createParamKAF020() {
            let vm = this;
            vm.$blockui('show');
            vm.$ajax(PATH_API.getDetail, {
                companyId: vm.$user.companyId,
                applicationId: vm.application().appID()
            }).done((applicationDto: any) => {
                if (applicationDto) {
                    let code = applicationDto.application.code;
                    let contents: Array<OptionalItemApplicationContent> = [];
                    applicationDto.application.optionalItems.forEach((item: any) => {
                        let optionalItem: any = _.find(applicationDto.optionalItems, {optionalItemNo: item.itemNo - 640});
                        let controlOfAttendanceItem: any = _.find(applicationDto.controlOfAttendanceItems, {itemDailyID: item.itemNo});
                        if (optionalItem != null) {
                            contents.push({
                                optionalItemName: optionalItem.optionalItemName,
                                optionalItemNo: optionalItem.optionalItemNo,
                                optionalItemAtr: optionalItem.optionalItemAtr,
                                unit: optionalItem.unit,
                                inputUnitOfTimeItem: controlOfAttendanceItem ? controlOfAttendanceItem.inputUnitOfTimeItem : null,
                                description: optionalItem.description,
                                timeUpper: optionalItem.calcResultRange.timeUpper ? nts.uk.time.format.byId("Clock_Short_HM", optionalItem.calcResultRange.timeUpper) : null,
                                timeLower: optionalItem.calcResultRange.timeLower ? nts.uk.time.format.byId("Clock_Short_HM", optionalItem.calcResultRange.timeLower) : null,
                                amountLower: optionalItem.calcResultRange.amountLower,
                                amountUpper: optionalItem.calcResultRange.amountUpper,
                                numberLower: optionalItem.calcResultRange.numberLower,
                                numberUpper: optionalItem.calcResultRange.numberUpper,
                                upperCheck: optionalItem.calcResultRange.upperCheck,
                                lowerCheck: optionalItem.calcResultRange.lowerCheck,
                                time: ko.observable(item.time),
                                number: ko.observable(item.times),
                                amount: ko.observable(item.amount),
                                detail: ''
                            });
                        }
                    });
                    vm.dataFetch({
                        applicationContents: ko.observableArray(contents),
                        code: code
                    });
                    vm.printContent.opOptionalItemOutput = ko.toJS(vm.dataFetch());
                }
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        reload() {
            const vm = this;
            if (vm.appType() === AppType.OPTIONAL_ITEM_APPLICATION) {
                vm.createParamKAF020();
            }
        }

        update() {
            const vm = this;
            let dataFetch = ko.toJS(vm.dataFetch);
            let optionalItems = new Array();
            dataFetch.applicationContents.forEach((item: OptionalItemApplicationContent) => {
                optionalItems.push({
                    itemNo: item.optionalItemNo,
                    times: item.number || null,
                    amount: item.amount || null,
                    time: item.time || null
                });
            })
            let command = {
                application: ko.toJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.application),
                appDispInfoStartup: ko.toJS(vm.appDispInfoStartupOutput()),
                optItemAppCommand: {
                    code: dataFetch.code,
                    optionalItems
                },
            };
            vm.$blockui("show");
            return vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then((valid: boolean) => {
                    if (valid) {
                        return vm.$ajax(PATH_API.update, command);
                    }
                }).done(res => {
                    if (res) {
                        if (res) {
                            vm.printContent.opBusinessTripInfoOutput = dataFetch.businessTripContent;
                            vm.$dialog.info({messageId: "Msg_15"}).then(() => $(vm.$el).find('#A5_3').focus());
                        }
                    }
                }).fail(err => {
                    vm.handleError(err);
                }).always(() => {
                    vm.$errors("clear");
                    vm.$blockui("hide");
                });
        }

        handleError(err: any) {
            const vm = this;
            let param;

            if (err.message && err.messageId) {

                if (err.messageId == "Msg_23" || err.messageId == "Msg_24" || err.messageId == "Msg_1912" || err.messageId == "Msg_1913") {
                    err.message = err.parameterIds[0] + err.message;
                    param = err;
                } else {
                    param = {messageId: err.messageId, messageParams: err.parameterIds};
                }

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

    interface DetailSreenInfo {
        applicationContents: KnockoutObservableArray<OptionalItemApplicationContent>,
        code: KnockoutObservable<string>,
    }

    const PATH_API = {
        getDetail: 'ctx/at/request/application/optionalitem/getDetail',
        updateOptionalItem: 'ctx/at/request/application/optionalitem/checkBeforeRegister',
        update: 'ctx/at/request/application/optionalitem/update',
    }
}