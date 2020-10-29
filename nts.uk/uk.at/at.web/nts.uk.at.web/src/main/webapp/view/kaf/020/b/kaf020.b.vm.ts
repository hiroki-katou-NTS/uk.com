module nts.uk.at.view.kaf020.b {
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;

    @bean()
    export class Kaf020BViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.BUSINESS_TRIP_APPLICATION);
        isSendMail: KnockoutObservable<boolean> = ko.observable(false);
        application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
        b4_2value: KnockoutObservable<string> = ko.observable('wait');
        applicationContents: KnockoutObservableArray<Content> = ko.observableArray([]);
        code: string;

        constructor(props: any) {
            super();
        }

        created(params: any) {
            const vm = this;
            if (params != undefined)
                vm.code =
                    params.code;
            vm.$blockui("show");
            vm.loadData([], [], vm.appType()).then((loadFlag) => {
                if (loadFlag) {
                    return vm.initScreen(params);
                }
            }).then((response: any) => {
                let contents: Array<Content> = [];
                response.forEach((item: ResponseContent) => {
                    contents.push({
                        optionalItemName: item.optionalItemDto.optionalItemName,
                        optionalItemNo: item.optionalItemDto.optionalItemNo,
                        optionalItemAtr: item.optionalItemDto.optionalItemAtr,
                        unit: item.optionalItemDto.unit,
                        inputUnitOfTimeItem: item.controlOfAttendanceItemsDto ? item.controlOfAttendanceItemsDto.inputUnitOfTimeItem : null,
                        description: item.optionalItemDto.description,
                        timeUpper: item.optionalItemDto.calcResultRange.timeUpper ? nts.uk.time.format.byId("Clock_Short_HM", item.optionalItemDto.calcResultRange.timeUpper) : null,
                        timeLower: item.optionalItemDto.calcResultRange.timeLower ? nts.uk.time.format.byId("Clock_Short_HM", item.optionalItemDto.calcResultRange.timeLower) : null,
                        amountLower: item.optionalItemDto.calcResultRange.amountLower,
                        amountUpper: item.optionalItemDto.calcResultRange.amountUpper,
                        numberLower: item.optionalItemDto.calcResultRange.numberLower,
                        numberUpper: item.optionalItemDto.calcResultRange.numberUpper,
                        upperCheck: item.optionalItemDto.calcResultRange.upperCheck,
                        lowerCheck: item.optionalItemDto.calcResultRange.lowerCheck,
                        time: ko.observable(''),
                        number: ko.observable(),
                        amount: ko.observable(),
                        detail: ''
                    });
                })
                vm.applicationContents(contents)
            }).then(() => {
                vm.focusDate();
            }).always(() => {
                vm.$blockui("hide");
            })
            $('#fixed-table').ntsFixedTable({width: 740});
        }

        mounted() {
            const vm = this;
        }

        initScreen(params: any) {
            const vm = this;
            if (params == undefined) vm.$jump("../a/index.xhtml");
            return vm.$ajax('screen/at/kaf020/b/get',
                {settingItemNoList: params.settingItems.map((item: any) => item.no)}
            );
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
            vm.applicationContents().forEach((item: Content) => {
                optionalItems.push({
                    itemNo: item.optionalItemNo,
                    times: item.number() || null,
                    amount: item.amount() || null,
                    time: item.time() || null
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
            vm.$ajax('screen/at/kaf020/b/register', command).done(result => {
                if (result != undefined) {
                    self.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                        location.reload();
                    });
                }
            }).fail(err => {
                vm.$dialog.error(err);
                // vm.handleError(err);
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


    interface ResponseContent {
        optionalItemDto: {
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
        },
        controlOfAttendanceItemsDto: {
            inputUnitOfTimeItem: string
        }
    }

    interface Content {
        optionalItemName: string
        optionalItemNo: number
        optionalItemAtr: number
        unit: string
        inputUnitOfTimeItem: string
        timeUpper: number
        timeLower: number
        numberUpper: number
        lowerCheck: boolean
        upperCheck: boolean
        numberLower: number
        amountLower: number
        amountUpper: number
        description: string,
        time: KnockoutObservable<string>,
        number: KnockoutObservable<number>,
        amount: KnockoutObservable<number>,
        detail: string,
    }
}