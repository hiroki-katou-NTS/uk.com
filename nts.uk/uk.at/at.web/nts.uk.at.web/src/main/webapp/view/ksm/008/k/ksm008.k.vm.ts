module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API_KSCREEN = {
        create: 'screen/at/ksm008/k/createWorkHourSetting',
        update: 'screen/at/ksm008/k/updateWorkHourSetting',
        delete: 'screen/at/ksm008/k/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/k/getStartupInfo',
        geDetails: 'screen/at/ksm008/k/getWorkHoursDetails',
        getAllWorkingHours: 'at/shared/worktimesetting/findAll',
    };

    const API_LSCREEN = {
        create: 'screen/at/ksm008/l/createWorkHourSetting',
        update: 'screen/at/ksm008/l/updateWorkHourSetting',
        delete: 'screen/at/ksm008/l/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/l/getStartupInfo',
        getWorkHoursList: 'screen/at/ksm008/l/getWorkHoursList',
        getWorkHoursDetails: 'screen/at/ksm008/l/getWorkTimeDetails',
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> =
            ko.observable(new ScheduleAlarmCheckCond('',
                '',
                ''));
        workPlace: WorkPlace = new WorkPlace(null, "", "", "", "", "");
        kScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('', '', '', '');
        lScreenWorkingHour: KscreenWorkHour = new KscreenWorkHour('', '', '', '');
        kScreenGridListData: KnockoutObservableArray<ItemModel>;
        lScreenGridListData: KnockoutObservableArray<ItemModel>;
        backButon: string = "/view/ksm/008/a/index.xhtml";
        kScreenCurrentCode: KnockoutObservable<any>;
        lScreenCurrentCode: KnockoutObservable<any>;
        kScreenSeletedCodeList: KnockoutObservableArray<any>;
        lScreenSeletedCodeList: KnockoutObservableArray<any>;
        isKScreenUpdateMode: KnockoutObservable<boolean>;
        isLScreenUpdateMode: KnockoutObservable<boolean>;
        isLScreenStart: boolean = true;
        isKScreenStart: boolean = true;
        isKDL046StateChanged: boolean = true;
        initialCodeList: KnockoutObservableArray<string>;
        workingHours: KnockoutObservableArray<WorkingHour>;
        date: KnockoutObservable<string>;

        constructor() {
            super();
            const vm = this;
            vm.kScreenGridListData = ko.observableArray([]);
            vm.lScreenGridListData = ko.observableArray([]);
            vm.kScreenCurrentCode = ko.observable();
            vm.lScreenCurrentCode = ko.observable();
            vm.kScreenSeletedCodeList = ko.observableArray([]);
            vm.lScreenSeletedCodeList = ko.observableArray([]);
            vm.isKScreenUpdateMode = ko.observable(false);
            vm.isLScreenUpdateMode = ko.observable(false);
            vm.isLScreenStart = true;
            vm.isKScreenStart = true;
            vm.initialCodeList = ko.observableArray([]);
            vm.workingHours = ko.observableArray([]);
            vm.date = ko.observable(new Date().toISOString());
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.kScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#K7_2");
            })
            vm.lScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#L4_2");
            });
            vm.kScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getDetailsKScreen(newValue).done(() => {
                        this.isKScreenUpdateMode(true);
                        $('#I6_3').focus();
                    });
                }
            });
            vm.lScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getDetailsLScreen(newValue).done(() => {
                        this.isLScreenUpdateMode(true);
                        $('#L3_3').focus();
                    });
                }
            });
            vm.loadKScreenListData();
            vm.loadLScreenListData();
            vm.$blockui("invisible");
            vm.$ajax(API_KSCREEN.getAllWorkingHours).then(data => {
                vm.initialCodeList(data.map(function (item: any) {
                    return item.code;
                }));
                vm.workingHours(_.map(data, function (item: any) {
                    return new WorkingHour(item.code, item.name)
                }));
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));
        }

        /**
         * This function is responsible to open KDL001 modal for K screen
         *
         * @author rafiqul.islam
         * */
        openKdl001ModalKScreen() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.kScreenSeletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.initialCodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.kScreenSeletedCodeList(shareWorkCocde);
                vm.kScreenWorkingHour.workHour(vm.prepareWorkHoursName(shareWorkCocde));
                if (vm.isKScreenUpdateMode()) {
                    $("#K6_3").focus();
                } else {
                    $("#K6_2").focus();
                }
            });
        }

        /**
         * This function is responsible to open KDL001 modal for L screen
         *
         * @author rafiqul.islam
         * */
        openKdl001ModalLScreen() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.lScreenSeletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.initialCodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.lScreenSeletedCodeList(shareWorkCocde);
                vm.lScreenWorkingHour.workHour(vm.prepareWorkHoursName(shareWorkCocde));
                if (vm.isLScreenUpdateMode()) {
                    $("#L3_3").focus();
                } else {
                    $("#L3_2").focus();
                }
            });
        }

        /**
         * prepare work hours name
         *
         * @param shareWorkCocde
         * @return string
         * @author rafiqul.islam
         * */
        prepareWorkHoursName(shareWorkCocde: any): string {
            const vm = this;
            var workHour: string = "";
            for (var i = 0; i < shareWorkCocde.length; i++) {
                var matched = _.find(vm.workingHours(), function (currentItem: WorkingHour) {
                    return currentItem.code === shareWorkCocde[i]
                });
                if (matched) {
                    if (i === shareWorkCocde.length - 1) {
                        workHour += matched.name != "" ? matched.name : "";
                    } else {
                        workHour += matched.name != "" ? matched.name + "+" : "";
                    }
                }
            }
            return workHour;
        }

        /**
         * get K screen items list
         *
         * @author rafiqul.islam
         * */
        loadKScreenListData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API_KSCREEN.getStartupInfo + "/07").then(data => {
                let explanation = data.explanation;
                explanation = explanation.replace(/\\r/g, "\r");
                explanation = explanation.replace(/\\n/g, "\n");
                vm.scheduleAlarmCheckCond(new ScheduleAlarmCheckCond(data.code, data.conditionName, explanation.trim()));
                vm.kScreenGridListData(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (vm.isKScreenStart) {
                    if (data.workTimeList.length > 0) {
                        vm.isKScreenUpdateMode(true);
                        vm.kScreenCurrentCode(data.workTimeList[0].code);
                    }
                    vm.isKScreenStart = false;
                }
                if (data.workTimeList.length === 0) {
                    vm.kScreenClickNewButton();
                }
                dfd.resolve();
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        /**
         * get L screen items list
         *
         * @author rafiqul.islam
         * */
        loadLScreenListData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API_LSCREEN.getStartupInfo).then(data => {
                vm.workPlace.unit(data.unit);
                vm.workPlace.workplaceId(data.workplaceId);
                vm.workPlace.workplaceGroupId(data.workplaceGroupId);
                vm.workPlace.workplaceTarget(data.workplaceTarget);
                vm.workPlace.workplaceCode(data.workplaceCode);
                vm.workPlace.workplaceName(data.workplaceName);
                vm.lScreenGridListData(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (vm.isLScreenStart) {
                    if (data.workTimeList.length > 0) {
                        vm.isLScreenUpdateMode(true);
                        vm.lScreenCurrentCode(data.workTimeList[0].code);
                    }
                    vm.isLScreenStart = false;
                }
                if (data.workTimeList.length === 0) {
                    vm.lScreenClickNewButton();
                }
                dfd.resolve();
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        /**
         * This function is responsible to register K screen data
         *
         * @author rafiqul.islam
         * */
        registerKScreen() {
            const vm = this;
            vm.$validate("#K6_3", "#K6_2", "#K8_2").then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.kScreenWorkingHour.workHour().length === 0) {
                    vm.$errors("#K7_2", "Msg_1844").then((valid: boolean) => {
                        $("#K7_2").focus();
                    });
                } else {
                    let codeList = vm.kScreenSeletedCodeList().filter(function (el) {
                        return el != "";
                    });
                    let command = {
                        code: vm.kScreenWorkingHour.code(),
                        name: vm.kScreenWorkingHour.name(),
                        maxDay: vm.kScreenWorkingHour.numberOfConDays(),
                        workTimeCodes: codeList
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isKScreenUpdateMode() ? API_KSCREEN.update : API_KSCREEN.create, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                vm.loadKScreenListData();
                                vm.kScreenCurrentCode(vm.kScreenWorkingHour.code());
                                vm.getDetailsKScreen(vm.kScreenWorkingHour.code());
                                $("#K6_3").focus();
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                    });
                }
            });
        }

        /**
         * This function is responsible to register L screen data
         *
         * @author rafiqul.islam
         * */
        registerLScreen() {
            const vm = this;
            vm.$validate('#L3_2', '#L3_3', '#L5_2').then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.lScreenWorkingHour.workHour().length === 0) {
                    vm.$errors("#L4_2", "Msg_1844").then((valid: boolean) => {
                        $("#L4_2").focus();
                    });
                } else {
                    let codeList = vm.lScreenSeletedCodeList().filter(function (el) {
                        return el != "";
                    });
                    let command = {
                        code: vm.lScreenWorkingHour.code(),
                        name: vm.lScreenWorkingHour.name(),
                        workPlaceUnit: vm.workPlace.unit(),
                        workPlaceId: vm.workPlace.workplaceId(),
                        workPlaceGroup: vm.workPlace.workplaceGroupId(),
                        workTimeCodes: codeList,
                        numberOfDays: vm.lScreenWorkingHour.numberOfConDays()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isLScreenUpdateMode() ? API_LSCREEN.update : API_LSCREEN.create, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                vm.lScreenCurrentCode(vm.lScreenWorkingHour.code());
                                vm.loadLScreenListDataByTarget();
                                $("#L3_3").focus();
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                    });
                }
            });
        }

        /**
         * This function is responsible to make new mode in k screen
         *
         * @author rafiqul.islam
         * */
        kScreenClickNewButton() {
            const vm = this;
            vm.$errors("clear", ".nts-editor", "button");
            vm.isKScreenUpdateMode(false);
            $("#K6_2").focus();
            vm.cleanKScreen();
            vm.kScreenCurrentCode("");
            vm.kScreenSeletedCodeList([]);
        }

        /**
         * this function is responsible for making J screen new mode
         *
         * @author rafiqul.islam
         * */
        lScreenClickNewButton() {
            const vm = this;
            vm.isLScreenUpdateMode(false);
            vm.$errors("clear", ".nts-editor", "button");
            $("#L3_2").focus();
            vm.lScreenCurrentCode("");
            vm.cleanLScreen();
            vm.lScreenSeletedCodeList([]);
        }

        /**
         * Get details data for k screen
         *
         * @param code
         * @return void
         * @author rafiqul.islam
         * */
        getDetailsKScreen(code: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            if (code != "") {
                vm.$blockui("invisible");
                vm.$ajax(API_KSCREEN.geDetails + "/" + code).then(data => {
                    $("#K6_3").focus();
                    vm.kScreenWorkingHour.code(data.code);
                    vm.kScreenWorkingHour.name(data.name);
                    vm.kScreenWorkingHour.numberOfConDays(data.maxDay);
                    vm.kScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.kScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                    dfd.resolve();
                }).fail(err => {
                    vm.$dialog.error(err);
                }).always(() => vm.$blockui("clear"));
            }
            return dfd.promise();
        }

        /**
         * Get details data for k screen
         *
         * @param code
         * @return void
         * @author rafiqul.islam
         * */
        getDetailsLScreen(code: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            if (code != "") {
                let command = {
                    workPlaceUnit: vm.workPlace.unit(),
                    workPlaceId: vm.workPlace.workplaceId(),
                    workPlaceGroup: vm.workPlace.workplaceGroupId(),
                    code: code
                };
                vm.$blockui("invisible");
                vm.$ajax(API_LSCREEN.getWorkHoursDetails, command).then(data => {
                    vm.lScreenWorkingHour.code(data.code);
                    vm.lScreenWorkingHour.name(data.name);
                    vm.lScreenWorkingHour.numberOfConDays(data.maxDays);
                    vm.lScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.lScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                    dfd.resolve();
                }).fail(err => {
                    vm.$dialog.error(err);
                }).always(() => vm.$blockui("clear"));
            }
            return dfd.promise();
        }

        /**
         * prepare work hours name
         *
         * @param workingHours
         * @return string
         * @author rafiqul.islam
         * */
        generateWorkHourName(workingHours: any): string {
            return _.map(workingHours, function (item: any) {
                return item.name;
            }).join("+");
        }

        /**
         * clean the k screen input items
         *
         * @author rafiqul.islam
         * */
        cleanKScreen() {
            const vm = this;
            vm.kScreenWorkingHour.code("");
            vm.kScreenWorkingHour.name("");
            vm.kScreenWorkingHour.numberOfConDays("");
            vm.kScreenWorkingHour.workHour("");
        }

        /**
         * clean the L screen input items
         *
         * @author rafiqul.islam
         */
        cleanLScreen() {
            const vm = this;
            vm.lScreenWorkingHour.code("");
            vm.lScreenWorkingHour.name("");
            vm.lScreenWorkingHour.numberOfConDays("");
            vm.lScreenWorkingHour.workHour("");
        }

        /**
         * get selectable code after delete an item
         *
         * @param items
         * @param code
         * @return string
         * @author rafiqul.islam         *
         */
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

        /**
         * This function is responsible to delete an selected item from K screen
         *
         * @return void
         * @author rafiqul.islam         *
         */
        kScreenClickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.kScreenGridListData(), vm.kScreenWorkingHour.code());
                    let command = {
                        code: vm.kScreenWorkingHour.code(),
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API_KSCREEN.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.cleanKScreen();
                                vm.loadKScreenListData();
                                vm.kScreenCurrentCode(selectableCode);
                                vm.getDetailsKScreen(selectableCode);
                                if (vm.kScreenGridListData().length === 0) {
                                    $("#K6_2").focus();
                                }
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
                $("#K6_3").focus();
            });
        }

        /**
         * Execute while organization tab selected
         *
         * @return void
         * @author rafiqul.islam
         */
        onOrganizationSelect() {
            const vm = this;
            vm.isLScreenStart = true;
            vm.loadLScreenListData();
            vm.$errors("clear", ".nts-editor", "button")
            setTimeout(function () {
                if (vm.lScreenGridListData().length > 0) {
                    $("#L3_3").focus();
                } else {
                    vm.lScreenClickNewButton();
                }
            }, 0);
            vm.getDetailsLScreen(vm.lScreenGridListData().length > 0 ? vm.lScreenGridListData()[0].code : "");
        }

        /**
         * Execute while company tab selected
         *
         * @return void
         * @author rafiqul.islam
         */
        onCompanySelect() {
            const vm = this;
            vm.isKScreenStart = true;
            vm.loadKScreenListData();
            vm.$errors("clear", ".nts-editor", "button")
            setTimeout(function () {
                if (vm.kScreenGridListData().length > 0) {
                    $("#K6_3").focus();
                } else {
                    vm.kScreenClickNewButton();
                }
            }, 0);
            vm.getDetailsKScreen(vm.kScreenGridListData().length > 0 ? vm.kScreenGridListData()[0].code : "");
        }

        /**
         * This function is responsible to delete an selected item from L screen
         *
         * @return void
         * @author rafiqul.islam
         */
        lScreenClickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.lScreenGridListData(), vm.lScreenWorkingHour.code());
                    let command = {
                        workPlaceUnit: vm.workPlace.unit(),
                        workPlaceId: vm.workPlace.workplaceId(),
                        workPlaceGroup: vm.workPlace.workplaceGroupId(),
                        code: vm.lScreenWorkingHour.code()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API_LSCREEN.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.loadLScreenListDataByTarget();
                                vm.lScreenCurrentCode(selectableCode);
                                vm.getDetailsLScreen(selectableCode);
                                if (vm.lScreenGridListData().length === 0) {
                                    $("#L3_2").focus();
                                }
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
                $("#L3_3").focus();
            });
        }

        /**
         * This function is responsible to open KDL046 modal
         *
         * @return void
         * @author rafiqul.islam
         */
        openModalKDL046() {
            const vm = this
            let request: any = {
                unit: vm.workPlace.unit()
            };
            if (request.unit === 1) {
                request.workplaceGroupId = vm.workPlace.workplaceGroupId();
                request.workplaceGroupCode = vm.workPlace.workplaceCode();
                request.workplaceGroupName = vm.workPlace.workplaceName();
            } else {
                request.workplaceId = vm.workPlace.workplaceId();
                request.enableDate = true;
                request.workplaceCode = vm.workPlace.workplaceCode();
                request.workplaceName = vm.workPlace.workplaceName();
                request.date = vm.date();
            }
            request.showBaseDate = true;
            request.baseDate = vm.date();
            setShared('dataShareDialog046', request);
            vm.$window.modal('/view/kdl/046/a/index.xhtml')
                .then((result: any) => {
                    if (!!nts.uk.ui.windows.getShared('dataShareKDL046')) {
                        let selectedData = nts.uk.ui.windows.getShared('dataShareKDL046');
                        console.log("selected target data:", selectedData);
                        vm.workPlace.unit(selectedData.unit);
                        if (selectedData.unit === 0) {
                            if (selectedData.workplaceCode != vm.workPlace.workplaceCode()) {
                                vm.isKDL046StateChanged = true;
                            }
                            vm.workPlace.workplaceName(selectedData.workplaceName);
                            vm.workPlace.workplaceCode(selectedData.workplaceCode);
                            vm.workPlace.workplaceId(selectedData.workplaceId);
                        } else {
                            if (selectedData.workplaceGroupCode != vm.workPlace.workplaceCode()) {
                                vm.isKDL046StateChanged = true;
                            }
                            vm.workPlace.workplaceName(selectedData.workplaceGroupName);
                            vm.workPlace.workplaceGroupId(selectedData.workplaceGroupID);
                            vm.workPlace.workplaceCode(selectedData.workplaceGroupCode);
                        }
                    }
                    vm.loadLScreenListDataByTarget();
                });
        }

        /**
         * get L screen items list by target
         *
         * @author rafiqul.islam
         * */
        loadLScreenListDataByTarget(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            let command = {
                workPlaceUnit: vm.workPlace.unit(),
                workPlaceId: vm.workPlace.workplaceId(),
                workPlaceGroup: vm.workPlace.workplaceGroupId(),
            };
            vm.$blockui("invisible");
            vm.$ajax(API_LSCREEN.getWorkHoursList, command).then(data => {
                vm.lScreenGridListData(_.map(data, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (data.length === 0) {
                    vm.lScreenClickNewButton();
                }
                if (data.length > 0 && vm.isKDL046StateChanged) {
                    vm.lScreenCurrentCode(data[0].code);
                    vm.isKDL046StateChanged = false;
                }
                dfd.resolve();
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => {
                if (vm.lScreenGridListData().length > 0) {
                    vm.lScreenCurrentCode(vm.lScreenGridListData()[0].code);
                    $("#L3_3").focus();
                } else {
                    vm.lScreenClickNewButton();
                }
                vm.$blockui("clear")
            });
            return dfd.promise();
        }
    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 |条件名 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class ScheduleAlarmCheckCond extends CodeName {
        /** 説明 */
        descriptions: KnockoutObservable<string>;
        codeAndName: KnockoutObservable<string>;

        constructor(code: string, name: string, descriptions: string) {
            super(code, name)
            this.descriptions = ko.observable(descriptions);
            this.codeAndName = ko.observable(code + " " + name);
        };
    }

    class KscreenWorkHour extends CodeName {
        workHour: KnockoutObservable<string>;
        /** 連続日数 */
        numberOfConDays: KnockoutObservable<string>;

        constructor(code: string, name: string, workHour: string, numberOfConDays: string) {
            super(code, name)
            this.workHour = ko.observable(workHour);
            this.numberOfConDays = ko.observable(numberOfConDays);
        };
    }

    class ItemModel {
        /** 上限日数 */
        maxNumberOfDay: string;
        /** コード */
        code: string;
        /** 名称 */
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

    class WorkPlace {
        /** 単位 */
        unit: KnockoutObservable<number>;
        /** 職場ID */
        workplaceId: KnockoutObservable<string>;
        /** 職場グループID */
        workplaceGroupId: KnockoutObservable<string>;
        /** コ職場ターゲット */
        workplaceTarget: KnockoutObservable<string>;
        /** 職場コード */
        workplaceCode: KnockoutObservable<string>;
        /** 職場名称 */
        workplaceName: KnockoutObservable<string>;

        constructor(unit: number, workplaceId: string, workplaceGroupId: string, workplaceTarget: string, workplaceCode: string, workplaceName: string) {
            this.unit = ko.observable(unit);
            this.workplaceId = ko.observable(workplaceId);
            this.workplaceGroupId = ko.observable(workplaceGroupId);
            this.workplaceTarget = ko.observable(workplaceTarget);
            this.workplaceCode = ko.observable(workplaceCode);
            this.workplaceName = ko.observable(workplaceName);
        }
    }
}
