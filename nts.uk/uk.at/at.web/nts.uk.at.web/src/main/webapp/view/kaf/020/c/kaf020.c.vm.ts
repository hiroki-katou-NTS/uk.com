module nts.uk.at.view.kaf020.c.viewmodel {
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import OptionalItemApplicationContent = nts.uk.at.view.kaf020.shr.viewmodel.Content;

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

    class Kaf020CViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.OPTIONAL_ITEM_APPLICATION);
        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable({
            applicationContents: ko.observableArray([])
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
            debugger
            vm.application = params.application;
            vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.createParamKAF020();
            vm.approvalReason = params.approvalReason;
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
            vm.isSendMail = ko.observable(true);
        }

        // 起動する
        createParamKAF020() {

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

            let command = {
                businessTrip: dataFetch.businessTripContent,
                businessTripInfoOutput: dataFetch.businessTripOutput,
                application: ko.toJS(vm.application())
            };

            vm.$blockui("show");

            return vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then((valid: boolean) => {
                    if (valid) {
                        return vm.$ajax(API.updateOptionalItem, command)
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
        applicationContents: KnockoutObservableArray<OptionalItemApplicationContent>
    }

    const API = {
        updateOptionalItem: ''
    }
}