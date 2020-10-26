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
            vm.loadData([], [], vm.appType()).then((...flag) => {
                console.log(flag)
            }).then(
                result => {
                    console.log(result);
                });
            $('#fixed-table').ntsFixedTable({width: 640});
            vm.initScreen(params);
        }

        mounted() {
            const vm = this;
        }

        initScreen(params: any) {
            const vm = this;
            if (params == undefined) vm.$jump("../a/index.xhtml");
            vm.$ajax('screen/at/kaf020/b/get',
                {settingItemNoList: params.settingItems.map((item: any) => item.no)}
            ).then(response => {
                let contents: Array<Content> = [];
                response.forEach((item: any) => {
                    item.optionalItemDto.calcResultRange.upperCheck
                    item.optionalItemDto.calcResultRange.lowerCheck
                    item.optionalItemDto.lowerCheck
                    contents.push({
                        optionalItemName: item.optionalItemDto.optionalItemName,
                        optionalItemNo: item.optionalItemDto.optionalItemNo,
                        optionalItemAtr: item.optionalItemDto.optionalItemAtr,
                        unit: item.optionalItemDto.unit,
                        description: item.optionalItemDto.description,
                        time: ko.observable('13:00'),
                        number: ko.observable(3),
                        amount: ko.observable(4),
                        detail: '',
                    })
                })
                vm.applicationContents(contents)
            });
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
                    itemNo: item.optionalItemNo + 640,
                    times: item.number(),
                    amount: item.amount(),
                    time: 32
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
            vm.$ajax('screen/at/kaf020/b/register', command);
        }
    }

    interface Content {
        optionalItemName: string
        optionalItemNo: number
        optionalItemAtr: number
        unit: string
        description: string,
        time: KnockoutObservable<string>,
        number: KnockoutObservable<number>,
        amount: KnockoutObservable<number>,
        detail: string,
    }
}