module nts.uk.at.view.kaf020.b {
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import OptionalItemApplicationContent = nts.uk.at.view.kaf020.shr.viewmodel.Content;

    const PATH_API = {
        register: 'ctx/at/request/application/optionalitem/register',
        getControlAttendance: 'ctx/at/request/application/optionalitem/getControlAttendance',
        listOptionalItem: 'ctx/at/record/optionalitem/findByListItemNo',
    }

    @bean()
    export class Kaf020BViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.OPTIONAL_ITEM_APPLICATION);
        isSendMail: KnockoutObservable<boolean> = ko.observable(false);
        application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
        isAgentMode: KnockoutObservable<boolean> = ko.observable(false);
        code: string;
        dataFetch: KnockoutObservable<DetailSreenInfo> = ko.observable({
            applicationContents: ko.observableArray([])
        });
        allOptional: any = [];

        constructor(props: any) {
            super();
        }

        created(params: any) {
            const vm = this;
            if (params != undefined) {
                nts.uk.characteristics.save("KAF020InitParams", params)
                vm.code = params.code;

            } else {
                nts.uk.characteristics.restore("KAF020InitParams").then((cacheParams: any) => {
                    if (cacheParams != undefined) {
                        vm.code = cacheParams.code;
                    }
                })
            }
            vm.$blockui("show");
            vm.loadData([], [], vm.appType()).then((loadFlag) => {
                if (loadFlag) {
                    if (params != undefined) {
                        return vm.fetchData(params);
                    } else {
                        nts.uk.characteristics.restore("KAF020InitParams").then((cacheParams: any) => {
                            return vm.fetchData(cacheParams);
                        });
                    }
                }
            }).then((response: any) => {

            })
        }

        mounted() {
            const vm = this;
        }

        fetchData(params: any) {
            const vm = this;
            let itemNoList = params.settingItems.map((item: any) => item.no);
            $.when(vm.$ajax(PATH_API.getControlAttendance, {optionalItemNos: itemNoList}), vm.$ajax(PATH_API.listOptionalItem, {optionalItemNos: itemNoList})).done((controlAttendance: any, optionalItems: any) => {
                let contents: Array<OptionalItemApplicationContent> = [];
                itemNoList.forEach((optionalItemNo: number) => {
                    let optionalItem: OptionalItem = _.find(optionalItems, {optionalItemNo: optionalItemNo});
                    let controlOfAttendanceItem: any = _.find(controlAttendance, {itemDailyID: optionalItemNo + 640});
                    contents.push({
                        optionalItemName: optionalItem.optionalItemName,
                        optionalItemNo: optionalItem.optionalItemNo,
                        optionalItemAtr: optionalItem.optionalItemAtr,
                        unit: optionalItem.unit,
                        inputUnitOfTimeItem: controlOfAttendanceItem ? controlOfAttendanceItem.inputUnitOfTimeItem : null,
                        description: optionalItem.description,
                        timeUpper: optionalItem.calcResultRange.timeUpper != null ? nts.uk.time.format.byId("Clock_Short_HM", optionalItem.calcResultRange.timeUpper) : null,
                        timeLower: optionalItem.calcResultRange.timeLower != null ? nts.uk.time.format.byId("Clock_Short_HM", optionalItem.calcResultRange.timeLower) : null,
                        amountLower: optionalItem.calcResultRange.amountLower,
                        amountUpper: optionalItem.calcResultRange.amountUpper,
                        numberLower: optionalItem.calcResultRange.numberLower,
                        numberUpper: optionalItem.calcResultRange.numberUpper,
                        upperCheck: optionalItem.calcResultRange.upperCheck,
                        lowerCheck: optionalItem.calcResultRange.lowerCheck,
                        time: ko.observable(''),
                        times: ko.observable(),
                        amount: ko.observable(),
                        detail: ''
                    });
                })
                vm.dataFetch({applicationContents: ko.observableArray(contents), name: params.name});
            }).then(() => {
                vm.focusDate();
            }).always(() => {
                vm.$blockui("hide");
            });
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

        goBack() {
            const vm = this;
            vm.$jump('../a/index.xhtml');
        }

        register() {
            const vm = this;
            let optionalItems = new Array();
            vm.dataFetch().applicationContents().forEach((item: OptionalItemApplicationContent) => {
                optionalItems.push({
                    itemNo: item.optionalItemNo,
                    times: item.times(),
                    amount: item.amount(),
                    time: item.time()
                });
            })
            let command = {
                application: ko.toJS(vm.application()),
                appDispInfoStartup: vm.appDispInfoStartupOutput(),
                optItemAppCommand: {
                    code: vm.code,
                    optionalItems
                }
            }
            vm.$validate([
                '.ntsControl',
                '.nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    vm.$ajax(PATH_API.register, command).done(result => {
                        if (result != undefined) {
                            vm.$dialog.info({messageId: "Msg_15"}).then(() => window.location.reload());
                            // let contents: Array<OptionalItemApplicationContent> = [];
                            // vm.dataFetch().applicationContents().forEach(item => {
                            //     contents.push({
                            //         ...item,
                            //         time: ko.observable(''),
                            //         times: ko.observable(),
                            //         amount: ko.observable(),
                            //     })
                            // })
                            // vm.dataFetch({applicationContents: ko.observableArray(contents), name: vm.dataFetch().name});
                        }
                    }).fail(err => {
                        vm.$dialog.error(err);
                        // vm.handleError(err);
                    });
                }
            });
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
            vm.$dialog.error(param);
        }
    }

    interface OptionalItemData {
        optionalItem: OptionalItem,
        controlOfAttendanceItems: {
            inputUnitOfTimeItem: number,
        },
    }

    interface OptionalItem {
        itemNo: number,
        calcResultRange: {
            timeUpper: number
            timeLower: number,
            amountLower: number,
            amountUpper: number,
            numberLower: number,
            numberUpper: number,
            upperCheck: boolean,
            lowerCheck: boolean,
        },
        optionalItemName: string,
        optionalItemNo: number,
        optionalItemAtr: number
        unit: string
        description: string,
    }

    interface DetailSreenInfo {
        applicationContents: KnockoutObservableArray<OptionalItemApplicationContent>,
        name: string
    }
}