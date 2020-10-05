module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API = {
        //TODO
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> =
            ko.observable(new ScheduleAlarmCheckCond('シンプルリスト',
                '就業時間帯上限コード',
                '勤務予定のアラームチェック条件'));
        lScreenscheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> =
            ko.observable(new ScheduleAlarmCheckCond('シンプルリスト',
                '就業時間帯上限コード',
                '勤務予定のアラームチェック条件'));
        workPlace: CodeName = new CodeName('S0000000', 'test data');
        lScreenworkPlace: CodeName = new CodeName('S0000000', 'test data');
        kScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('0000', '数字3文字', '数字3文字 mock', '100');
        lScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('0000', '数字3文字', '数字3文字 mock', '200');


        kScreenGridListData: KnockoutObservableArray<ItemModel>;
        LScreenGridListData: KnockoutObservableArray<ItemModel>;

        backButon: string = "/view/ksm/008/a/index.xhtml";
        currentCode: KnockoutObservable<any>;
        lScreencurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            super();
            const vm = this;
            vm.kScreenGridListData = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.kScreenGridListData.push(new ItemModel('00K' + i, '基本給 K' + i, ""+i));
            }

            vm.LScreenGridListData = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.LScreenGridListData.push(new ItemModel('00L' + i, '基本給 L' + i, ""+i));
            }
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.lScreencurrentCode=ko.observable();

            // TODO

        }

        created() {
            const vm = this;
            // TODO
            _.extend(window, {vm});

            if (vm.kScreenGridListData().length === 0) {
                $('#K6_2').focus();
            }
            if (vm.kScreenGridListData().length !== 0) {
                $('#K6_3').focus();
            }

            vm.kScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#K7_2");
            })
            vm.currentCode.subscribe((newValue: any) => {
                var match = ko.utils.arrayFirst(vm.kScreenGridListData(), function (item) {
                    return item.code === newValue;
                });
                if (match) {
                    $("#K6_2").css("background-color","#f9efef");
                    vm.$errors("clear");
                }
                vm.kScreenWorkingHour.code(match.code);
                vm.kScreenWorkingHour.name(match.name);
                vm.kScreenWorkingHour.numberOfConDays(match.maxNumberOfDay);
            })

            vm.lScreencurrentCode.subscribe((newValue: any) => {
                var match = ko.utils.arrayFirst(vm.LScreenGridListData(), function (item) {
                    return item.code === newValue;
                });
                if (match) {
                    //$("#K6_2").css("background-color","#f9efef");
                  //  vm.$errors("clear");
                }
                vm.lScreenWorkingHour.code(match.code);
                vm.lScreenWorkingHour.name(match.name);
                vm.lScreenWorkingHour.numberOfConDays(match.maxNumberOfDay);
            })
        }

        mounted() {
            const vm = this;
        }

        openModal() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", [vm.currentCode]);
            setShared("kml001isSelection", false);
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
            });
        }

        registerKScreen() {
            const vm = this;
            if (vm.kScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#K7_2", "Msg_1844").then((valid: boolean) => {
                    $("#K7_2").focus();
                });
            }
        }

        registerLScreen() {
            const vm = this;
            if (vm.lScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#L4_2", "Msg_1844").then((valid: boolean) => {
                    $("#L4_2").focus();
                });
            }
        }

        kScreenClickNewButton(str) {
            const vm = this;
            vm.$errors("clear");
            $("#K6_2").focus();
            $("#K6_2").css("background-color","white");
            vm.kScreenWorkingHour.code("");
            vm.kScreenWorkingHour.name("");
            vm.kScreenWorkingHour.numberOfConDays("");
            vm.kScreenWorkingHour.workHour("");
        }

        lScreenClickNewButton() {
            const vm = this;
            vm.$errors("clear");
            $("#L3_2").focus();
            $("#L3_2").css("background-color","white");
            vm.lScreenWorkingHour.code("");
            vm.lScreenWorkingHour.name("");
            vm.lScreenWorkingHour.numberOfConDays("");
            vm.lScreenWorkingHour.workHour("");

        }

        kScreenClickDeleteButton() {
            alert("K Screnn Delete button has been clicked")
        }

        lScreenClickDeleteButton() {
            alert("L Screen Delete button has been clicked")
        }

        lScreenChooseButton(){
            alert("L Screen choose button has been clicked")
        }
    }

    class CodeName {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class ScheduleAlarmCheckCond extends CodeName {
        descriptions: KnockoutObservable<string>;
        codeAndDescription: KnockoutObservable<string>;

        constructor(code: string, name: string, descriptions: string) {
            super(code, name)
            this.descriptions = ko.observable(descriptions);
            this.codeAndDescription = ko.observable(code + " " + descriptions);
        };
    }

    class KscreenWorkHour extends CodeName {
        workHour: KnockoutObservable<string>;
        numberOfConDays: KnockoutObservable<string>;

        constructor(code: string, name: string, workHour: string, numberOfConDays: string) {
            super(code, name)
            this.workHour = ko.observable(workHour);
            this.numberOfConDays = ko.observable(numberOfConDays);
        };
    }

    class ItemModel {
        maxNumberOfDay: string;
        code: string;
        name: string;

        constructor(code: string, name: string, maxNumberOfDay: string) {
            this.code = code;
            this.name = name;
            this.maxNumberOfDay = maxNumberOfDay;
        }
    }
}
