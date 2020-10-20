module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API = {
        create: 'screen/at/ksm008/k/createWorkHourSetting',
        update: 'screen/at/ksm008/k/updateWorkHourSetting',
        delete: 'screen/at/ksm008/k/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/k/getStartupInfo',
        geDetails: 'screen/at/ksm008/k/getWorkHoursDetails',
        getAllWorkingHours: 'at/shared/worktimesetting/findAll',
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> =
            ko.observable(new ScheduleAlarmCheckCond('',
                '',
                ''));
        lScreenscheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> =
            ko.observable(new ScheduleAlarmCheckCond('シンプルリスト',
                '就業時間帯上限コード',
                '勤務予定のアラームチェック条件'));
        workPlace: CodeName = new CodeName('S0000000', 'test data');
        lScreenworkPlace: CodeName = new CodeName('S0000000', 'test data');
        kScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('', '', '', '');
        lScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('0000', '数字3文字', '数字3文字 mock', '200');


        kScreenGridListData: KnockoutObservableArray<ItemModel>;
        LScreenGridListData: KnockoutObservableArray<ItemModel>;

        backButon: string = "/view/ksm/008/a/index.xhtml";
        currentCode: KnockoutObservable<any>;
        lScreencurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        kScreenSeletedCodeList: KnockoutObservableArray<any>;
        isKScreenUpdateMode: boolean = false;
        initialFocus: KnockoutObservable<any>;
        initialcodeList: KnockoutObservableArray<string>;
        workingHours: KnockoutObservableArray<WorkingHour>;


        kScreenFoucs: FocusItem = new FocusItem(true, false, false);
        lScreenFoucs: FocusItem = new FocusItem(true, false, false);

        constructor() {
            super();
            const vm = this;
            vm.kScreenGridListData = ko.observableArray([]);

            vm.LScreenGridListData = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.LScreenGridListData.push(new ItemModel('00L' + i, '基本給 L' + i, "" + i));
            }
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.lScreencurrentCode = ko.observable();
            vm.kScreenSeletedCodeList = ko.observableArray([]);
            vm.isKScreenUpdateMode = false;
            vm.initialFocus = ko.observable(true);
            vm.initialcodeList = ko.observableArray([]);
            vm.workingHours = ko.observableArray([]);
            $(document).ready(
                function () {
                    setTimeout(function () {
                        vm.initialFocus(true);
                    }, 0);
                });

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
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getDetailsKScreen(newValue);
                    this.isKScreenUpdateMode = true;
                    $('#I6_3').focus();
                }
            })

            vm.lScreencurrentCode.subscribe((newValue: any) => {

            });
            vm.loadKScreenListData();

            vm.$ajax(API.getAllWorkingHours).then(data => {
                vm.initialcodeList(data.map(function (item: any) {
                    return item.code;
                }));
                vm.workingHours(_.map(data, function (item: any) {
                    return new WorkingHour(item.code, item.name)
                }));
            });
        }

        mounted() {
            const vm = this;
        }

        openModal() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.kScreenSeletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.initialcodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.kScreenSeletedCodeList(shareWorkCocde);
                vm.kScreenWorkingHour.workHour(vm.prepareWorkHoursName(shareWorkCocde));
            });
        }

        /**
         * prepare work hours name
         * @param shareWorkCocde
         * @return string
         * @author rafiqul.islam
         * */
        prepareWorkHoursName(shareWorkCocde: any): string {
            const vm = this;
            var workHour: string = "";
            for (var i = 0; i < shareWorkCocde.length; i++) {
                for (var j = 0; j < vm.workingHours().length; j++) {
                    if (shareWorkCocde[i] == vm.workingHours()[j].code) {
                        let separator = shareWorkCocde.length - 1 == i ? "" : " + ";
                        workHour += vm.workingHours()[j].name != "" ? vm.workingHours()[j].name + " " + separator + " " : "";
                        break;
                    }
                }
            }
            return workHour;
        }

        /**
         * get I screen items list
         * @author rafiqul.islam
         * */

        loadKScreenListData() {
            const vm = this;
            vm.$ajax(API.getStartupInfo + "/07").then(data => {
                vm.scheduleAlarmCheckCond(new ScheduleAlarmCheckCond(data.code, data.conditionName, data.explanation));
                vm.kScreenGridListData(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
            });
        }

        registerKScreen() {
            const vm = this;
            vm.$errors("clear");
            if (vm.kScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#K7_2", "Msg_1844").then((valid: boolean) => {
                    $("#K7_2").focus();
                    vm.kScreenFoucs.isButtonFocus = true;
                });
            } else {
                let command = {
                    code: vm.kScreenWorkingHour.code(),
                    name: vm.kScreenWorkingHour.name(),
                    maxDay: vm.kScreenWorkingHour.numberOfConDays(),
                    workTimeCodes: vm.kScreenSeletedCodeList()
                };
                vm.$ajax(vm.isKScreenUpdateMode ? API.update : API.create, command).done((data) => {
                    vm.$dialog.info({messageId: "Msg_15"})
                        .then(() => {
                            vm.loadKScreenListData();
                            vm.currentCode(vm.kScreenWorkingHour.code());
                            vm.getDetailsKScreen(vm.kScreenWorkingHour.code());
                            $("#K6_3").focus();
                            vm.kScreenFoucs.isNameFocus = true;
                        });
                }).fail(function (error) {
                    vm.$dialog.error({messageId: error.messageId});
                }).always(() => {
                    vm.$blockui("clear");
                    vm.$errors("clear");
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

        kScreenClickNewButton() {
            const vm = this;
            vm.$errors("clear");
            vm.isKScreenUpdateMode = false;
            $("#K6_2").focus();
            vm.kScreenFoucs=new FocusItem(true,false,false);
            $("#K6_2").css("background-color", "white");
            vm.cleanKScreen();
            vm.currentCode("");
            vm.kScreenSeletedCodeList([]);
        }

        getDetailsKScreen(code: string) {
            const vm = this;
            if (code != "") {
                vm.kScreenFoucs = new FocusItem(false, true, false);
                vm.$ajax(API.geDetails + "/" + code).then(data => {
                    $("#K6_3").focus();
                    vm.kScreenWorkingHour.code(data.code);
                    vm.kScreenWorkingHour.name(data.name);
                    vm.kScreenWorkingHour.numberOfConDays(data.maxDay);
                    vm.kScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.kScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                });
            }
        }

        /**
         * prepare work hours name
         * @param workingHours
         * @return string
         * @author rafiqul.islam
         * */

        generateWorkHourName(workingHours: any): string {
            var workHour: string = "";
            var i = 0;
            for (let element of workingHours) {
                let separator = workingHours.length - 1 == i ? "" : " + ";
                workHour += element.name != "" ? element.name + " " + separator + " " : "";
                i++;
            }
            return workHour;
        }

        cleanKScreen() {
            const vm = this;
            vm.kScreenWorkingHour.code("");
            vm.kScreenWorkingHour.name("");
            vm.kScreenWorkingHour.numberOfConDays("");
            vm.kScreenWorkingHour.workHour("");
        }

        lScreenClickNewButton() {
            const vm = this;
            vm.$errors("clear");
            $("#L3_2").focus();
            $("#L3_2").css("background-color", "white");
            vm.lScreenWorkingHour.code("");
            vm.lScreenWorkingHour.name("");
            vm.lScreenWorkingHour.numberOfConDays("");
            vm.lScreenWorkingHour.workHour("");

        }

        /**
         * get selectable code
         * @param items
         * @param code
         * @return string
         * @author rafiqul.islam
         *
         * */

        getSelectableCode(items: any, code: string): string {
            var currentIndex = 0;
            var selectableCode = "";
            for (var i = 0; i < items.length; i++) {
                if (items[i].code == code) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex === items.length - 1 && items.length != 1) {
                selectableCode = items[currentIndex - 1].code;
            } else if (currentIndex === 0 && items.length > 1) {
                selectableCode = items[1].code;
            }
            else {
                if (items.length != 1) {
                    selectableCode = items[currentIndex + 1].code;
                }
            }
            return selectableCode;
        }

        kScreenClickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.kScreenGridListData(), vm.kScreenWorkingHour.code());
                    let command = {
                        code: vm.kScreenWorkingHour.code(),
                    };
                    vm.$ajax(API.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.cleanKScreen();
                                vm.loadKScreenListData();
                                vm.currentCode(selectableCode);
                                vm.getDetailsKScreen(selectableCode);
                                if (vm.kScreenGridListData().length === 0) {
                                    $("#K6_2").focus();
                                    vm.kScreenFoucs.isCodeFoucs = true;
                                }
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
            });
        }

        onOrganizationSelect() {
            const vm = this;
            setTimeout(function () {
                if (vm.lScreenFoucs.isCodeFoucs) {
                    $("#L3_2").focus();
                }
                if (vm.lScreenFoucs.isNameFocus) {
                    $("#L3_3").focus();
                }
                if (vm.kScreenFoucs.isButtonFocus) {
                    $("#L4_2").focus();
                }
            }, 50);
        }

        onCompanySelect() {
            const vm = this;
            setTimeout(function () {
                if (vm.kScreenFoucs.isCodeFoucs) {
                    $("#K6_2").focus();
                }
                if (vm.kScreenFoucs.isNameFocus) {
                    $("#K6_3").focus();
                }
                if (vm.kScreenFoucs.isButtonFocus) {
                    $("#K7_2").focus();
                }
            }, 50);

        }

        lScreenClickDeleteButton() {
            alert("L Screen Delete button has been clicked")
        }

        lScreenChooseButton() {
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

    class FocusItem {
        constructor(isCodeFoucs: boolean, isNameFocus: boolean, isButtonFocus: boolean) {
            this._isCodeFoucs = isCodeFoucs;
            this._isNameFocus = isNameFocus;
            this._isButtonFocus = isButtonFocus;
        }

        private _isCodeFoucs: boolean;

        get isCodeFoucs(): boolean {
            return this._isCodeFoucs;
        }

        set isCodeFoucs(value: boolean) {
            this._isCodeFoucs = value;
        }

        private _isNameFocus: boolean;

        get isNameFocus(): boolean {
            return this._isNameFocus;
        }

        set isNameFocus(value: boolean) {
            this._isNameFocus = value;
        }

        private _isButtonFocus: boolean;

        get isButtonFocus(): boolean {
            return this._isButtonFocus;
        }

        set isButtonFocus(value: boolean) {
            this._isButtonFocus = value;
        }
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

    class WorkingHour {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
