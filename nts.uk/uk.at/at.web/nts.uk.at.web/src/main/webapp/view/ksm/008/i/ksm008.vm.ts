module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API = {
        create: 'screen/at/ksm008/i/createWorkHourSetting',
        update: 'screen/at/ksm008/i/updateWorkHourSetting',
        delete: 'screen/at/ksm008/i/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/i/getStartupInfo',
        getList: 'screen/at/ksm008/i/getWorkingHourList',
        getAllWorkingHours: 'at/shared/worktimesetting/findAll'
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> = ko.observable(new ScheduleAlarmCheckCond(null, null, null));
        workPlace: CodeName = new CodeName('S0000000', 'test data');
        // mock data for J3_2,J3_3
        iScreenWorkingHour: JscreenWorkHour = new JscreenWorkHour('', '', '', '');

        jScreenWorkingHour: JscreenWorkHour = new JscreenWorkHour('0000', '数字3文字', '数字3文字 mock', '100');
        // I5_1 mock data
        items: KnockoutObservableArray<ItemModel>;
        iScreenWorkingHours: KnockoutObservableArray<WorkingHour>;

        // J2_1 mock data
        jItems: KnockoutObservableArray<ItemModel>;
        J4_1MOckContrain: string = "";
        backButon: string = "/view/ksm/008/a/index.xhtml";
        item: KnockoutObservable<ItemModel>;
        currentCode: KnockoutObservable<any>;
        seletedCodeList: KnockoutObservableArray<any>;
        seletableCodeList: KnockoutObservableArray<any>;
        iCodeList: KnockoutObservableArray<string>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        simpleValue: KnockoutObservable<string>;
        initialFocus: KnockoutObservable<any>;
        isUpdateMode: boolean = false;

        constructor() {
            super();
            const vm = this;
            // I5_1 mock data creation
            this.items = ko.observableArray([]);
            this.iScreenWorkingHours = ko.observableArray([]);
            this.item = ko.observable(new ItemModel('0001', '基本給', 1));
            this.iCodeList = ko.observableArray([]);

            // I2_1 mock data creation
            vm.jItems = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                vm.jItems.push(new ItemModel('00J' + i, '基本給 J' + i, i));
            }
            vm.currentCode = ko.observable();
            vm.seletedCodeList = ko.observableArray([]);
            vm.seletableCodeList = ko.observableArray([]);

            // intial startup  new mode
            vm.isUpdateMode = false;
            vm.initialFocus = ko.observable(true)
            $(document).ready(
                function () {
                    setTimeout(function () {
                        vm.initialFocus(true);
                    }, 0);
                });
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getDetails(newValue);
                    this.isUpdateMode = true;
                    $('#I6_3').focus();
                }
            })
            vm.iScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#I7_2");
            });
            vm.iScreenWorkingHour.code.subscribe((newValue: any) => {
                vm.$errors("clear", "#I6_2");
            });
            vm.loadIScreenLIistData();
            vm.$ajax(API.getAllWorkingHours).then(data => {
                vm.iCodeList(data.map(function (item: any) {
                    return item.code;
                }));
                vm.iScreenWorkingHours(_.map(data, function (item: any) {
                    return new WorkingHour(item.code, item.name)
                }));
            });
        }

        openModal() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.seletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.iCodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.seletedCodeList(shareWorkCocde);
                var workHour: string = "";
                for (var i = 0; i < shareWorkCocde.length; i++) {
                    for (var j = 0; j < vm.iScreenWorkingHours().length; j++) {
                        if (shareWorkCocde[i] == vm.iScreenWorkingHours()[j].code) {
                            let separator = shareWorkCocde.length - 1 == i ? "" : " + ";
                            workHour += vm.iScreenWorkingHours()[j].name != "" ? vm.iScreenWorkingHours()[j].name + " " + separator + " " : "";
                            break;
                        }
                    }
                }
                vm.iScreenWorkingHour.workHour(workHour);
            });

        }

        loadIScreenLIistData() {
            const vm = this;
            vm.$ajax(API.getStartupInfo + "/06").then(data => {
                vm.scheduleAlarmCheckCond(new ScheduleAlarmCheckCond(data.code, data.conditionName, data.explanation));
                vm.items(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
            });
        }

        getDetails(code: string) {
            const vm = this;
            if (code != "") {
                vm.$ajax(API.getList + "/" + code).then(data => {
                    vm.iScreenWorkingHour.code(data.code);
                    vm.iScreenWorkingHour.name(data.name);
                    vm.iScreenWorkingHour.numberOfConDays(data.maxDaysContiWorktime)
                    var workHour: string = "";
                    var i = 0;
                    for (let element of data.workingHours) {
                        let separator = data.workingHours.length - 1 == i ? "" : " + ";
                        workHour += element.name != "" ? element.name + " " + separator + " " : "";
                        i++;
                    }
                    vm.seletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.iScreenWorkingHour.workHour(workHour);
                });
            }
        }

        iregisterKScreen() {
            const vm = this;
            vm.$errors("clear");
            if (vm.iScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#I7_2", "Msg_1844").then((valid: boolean) => {
                    $("#I7_2").focus();
                });
            } else {
                let command = {
                    code: vm.iScreenWorkingHour.code(),
                    name: vm.iScreenWorkingHour.name(),
                    maxDaysContiWorktime: {
                        workTimeCodes: vm.seletedCodeList(),
                        numberOfDays: vm.iScreenWorkingHour.numberOfConDays()
                    }
                };
                vm.$ajax(vm.isUpdateMode ? API.update : API.create, command).done((data) => {
                    vm.$dialog.info({messageId: "Msg_15"})
                        .then(() => {
                            vm.loadIScreenLIistData();
                            vm.currentCode(vm.iScreenWorkingHour.code());
                            $("#I6_3").focus();
                        });
                }).fail(function (error) {
                    vm.$dialog.error({messageId: error.messageId});
                }).always(() => {
                    vm.$blockui("clear");
                    vm.$errors("clear");
                });
            }
        }

        iScreenClickNewButton() {
            const vm = this;
            vm.isUpdateMode = false;
            vm.$errors("clear");
            $("#I6_2").focus();
            vm.currentCode("");
            vm.cleanInputItem();
            vm.seletedCodeList([]);
        }

        cleanInputItem() {
            const vm = this;
            vm.iScreenWorkingHour.code("");
            vm.iScreenWorkingHour.name("");
            vm.iScreenWorkingHour.numberOfConDays("");
            vm.iScreenWorkingHour.workHour("");
        }

        iScreenClickDeleteButton() {
            const vm = this;

            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var currentIndex = 0;
                    var selectableCode = "";
                    for (var i = 0; i < vm.items().length; i++) {
                        if (vm.items()[i].code == vm.iScreenWorkingHour.code()) {
                            currentIndex = i;
                            break;
                        }
                    }
                    if (currentIndex === vm.items().length - 1 && vm.items().length != 1) {
                        selectableCode = vm.items()[currentIndex - 1].code;
                    } else if (currentIndex === 0 && vm.items().length > 1) {
                        selectableCode = vm.items()[1].code;
                    }
                    else {
                        if (vm.items().length != 1) {
                            selectableCode = vm.items()[currentIndex + 1].code;
                        }
                    }
                    let command = {
                        code: vm.iScreenWorkingHour.code(),
                    };
                    vm.$ajax(API.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.cleanInputItem();
                                vm.loadIScreenLIistData();
                                vm.currentCode(selectableCode);
                                vm.getDetails(selectableCode);
                                if (vm.items().length === 0) {
                                    $("#I6_2").focus();
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
    }

    class CommonCode {
        code: KnockoutObservable<string>;//I3_1
    }

    class ScheduleAlarmCheckCond extends CommonCode {
        conditionName: KnockoutObservable<string>;
        codeAndName: KnockoutObservable<string>;
        descriptions: KnockoutObservable<string>;// TODO
        constructor(code: string, conditionName: string, descriptions: string) {
            super()
            this.code = ko.observable(code);
            this.conditionName = ko.observable(conditionName);
            this.codeAndName = ko.observable(code + " " + conditionName);//I3_1
            this.descriptions = ko.observable(descriptions);//I4_2
        };
    }

    class CodeName {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class JscreenWorkHour extends CodeName {
        workHour: KnockoutObservable<string>;
        numberOfConDays: KnockoutObservable<string>;

        constructor(code: string, name: string, workHour: string, numberOfConDays: string) {
            super(code, name)
            this.workHour = ko.observable(workHour);
            this.numberOfConDays = ko.observable(numberOfConDays);
        };
    }

    class ItemModel {
        maxNumberOfDay: Number;
        code: string;
        name: string;

        constructor(code: string, name: string, maxNumberOfDay: Number) {
            this.code = code;//I3_1
            this.name = name;//I3_1
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

