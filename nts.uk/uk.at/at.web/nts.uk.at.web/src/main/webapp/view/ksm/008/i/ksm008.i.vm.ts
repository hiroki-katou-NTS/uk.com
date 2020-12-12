module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API_ISCREEN = {
        create: 'screen/at/ksm008/i/createWorkHourSetting',
        update: 'screen/at/ksm008/i/updateWorkHourSetting',
        delete: 'screen/at/ksm008/i/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/i/getStartupInfo',
        getList: 'screen/at/ksm008/i/getWorkingHourList',
        getAllWorkingHours: 'at/shared/worktimesetting/findAll',
    };
    const API_JSCREEN = {
        create: 'screen/at/ksm008/j/createWorkHourSetting',
        update: 'screen/at/ksm008/j/updateWorkHourSetting',
        delete: 'screen/at/ksm008/j/deleteWorkHourSetting',
        getStartupInfo: 'screen/at/ksm008/j/getStartupInfo',
        getWorkHoursList: 'screen/at/ksm008/j/getWorkHoursList',
        getWorkHoursDetails: 'screen/at/ksm008/j/getWorkHoursDetails',
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> = ko.observable(new ScheduleAlarmCheckCond('', '', ''));
        workPlace: WorkPlace = new WorkPlace(null, "", "", "", "", "");
        iScreenWorkingHour: JscreenWorkHour = new JscreenWorkHour('', '', '', '');
        jScreenWorkingHour: JscreenWorkHour = new JscreenWorkHour('', '', '', '');
        items: KnockoutObservableArray<ItemModel>;
        workingHours: KnockoutObservableArray<WorkingHour>;
        jItems: KnockoutObservableArray<ItemModel>;
        backButon: string = "/view/ksm/008/a/index.xhtml";
        item: KnockoutObservable<ItemModel>;
        currentCode: KnockoutObservable<any>;
        iScreenSeletedCodeList: KnockoutObservableArray<any>;
        seletableCodeList: KnockoutObservableArray<any>;
        initialCodeList: KnockoutObservableArray<string>;
        initialFocus: KnockoutObservable<any>;
        jInitialFocus: boolean = true;
        isIScreenUpdateMode: KnockoutObservable<boolean>;
        isIScreenStart: boolean = true;
        isJScreenStart: boolean = true;
        isKDL004StateChanged: boolean = false;
        jScreenCurrentCode: KnockoutObservable<any>;
        jScreenSeletedCodeList: KnockoutObservableArray<any>;
        jScreenSeletableCodeList: KnockoutObservableArray<any>;
        jScreenCodeList: KnockoutObservableArray<string>;
        isJScreenUpdateMode: KnockoutObservable<boolean>;
        date: KnockoutObservable<string>;

        constructor() {
            super();
            const vm = this;
            vm.items = ko.observableArray([]);
            vm.workingHours = ko.observableArray([]);
            vm.initialCodeList = ko.observableArray([]);
            vm.jItems = ko.observableArray([]);
            vm.currentCode = ko.observable();
            vm.iScreenSeletedCodeList = ko.observableArray([]);
            vm.seletableCodeList = ko.observableArray([]);
            vm.jScreenCurrentCode = ko.observable();
            vm.jScreenSeletedCodeList = ko.observableArray([]);
            vm.jScreenSeletableCodeList = ko.observableArray([]);
            vm.jScreenCodeList = ko.observableArray([]);
            vm.isIScreenUpdateMode = ko.observable(false);
            vm.isJScreenUpdateMode = ko.observable(false);
            vm.isIScreenStart = true;
            vm.isJScreenStart = true;
            vm.isKDL004StateChanged = false;
            vm.jInitialFocus = true;
            vm.date = ko.observable(new Date().toISOString());
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getIScreenDetails(newValue).done(() => {
                        this.isIScreenUpdateMode(true);
                        $('#I6_3').focus();
                    });
                }
            });
            vm.jScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getJScreenDetails(newValue).done(() => {
                        this.isJScreenUpdateMode(true);
                        $('#J3_3').focus();
                    });
                }
            });
            vm.iScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#I7_2");
            });
            vm.jScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#J4_2");
            });
            vm.$blockui("invisible");
            vm.$ajax(API_ISCREEN.getAllWorkingHours).then(data => {
                vm.initialCodeList(data.map(function (item: any) {
                    return item.code;
                }));
                vm.workingHours(_.map(data, function (item: any) {
                    return new WorkingHour(item.code, item.name)
                }));
            }).fail(err => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));
            vm.loadIScreenListData();
            vm.loadJScreenListData();
        }

        /**
         * Function execute after organization tab select
         *
         * @author rafiqul.islam
         * */
        onOrganizationSelect() {
            const vm = this;
            vm.isJScreenStart = true;
            vm.loadJScreenListData();
            vm.$errors("clear", ".nts-editor", "button")
            setTimeout(function () {
                if (vm.jItems().length > 0) {
                    $("#J3_3").focus();
                } else {
                    vm.jScreenClickNewButton();
                }
            }, 0);
            vm.getJScreenDetails(vm.jItems().length > 0 ? vm.jItems()[0].code : "");
        }

        /**
         * Function execute after company tab select
         *
         * @author rafiqul.islam
         * */
        onCompanySelect() {
            const vm = this;
            vm.isIScreenStart = true;
            vm.loadIScreenListData();
            vm.$errors("clear", ".nts-editor", "button")
            setTimeout(function () {
                if (vm.items().length > 0) {
                    $("#I6_3").focus();
                } else {
                    vm.iScreenClickNewButton();
                }
            }, 0);
            vm.getIScreenDetails(vm.items().length > 0 ? vm.items()[0].code : "");
        }

        /**
         * open kdl001a modal for I screen
         *
         * @author rafiqul.islam
         * */
        openKdl001ModalIScreen() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.iScreenSeletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.initialCodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.iScreenSeletedCodeList(shareWorkCocde);
                vm.iScreenWorkingHour.workHour(vm.prepareWorkHoursName(shareWorkCocde));
                if (vm.isIScreenUpdateMode()) {
                    $("#I6_3").focus();
                } else {
                    $("#I6_2").focus();
                }
            });
        }

        /**
         * open kdl001a modal for J screen
         *
         * @author rafiqul.islam
         * */
        openKdl001ModalJScreen() {
            const vm = this;
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", vm.jScreenSeletedCodeList());
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.initialCodeList());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                vm.jScreenSeletedCodeList(shareWorkCocde);
                vm.jScreenWorkingHour.workHour(vm.prepareWorkHoursName(shareWorkCocde));
                if (vm.isJScreenUpdateMode()) {
                    $("#J3_3").focus();
                } else {
                    $("#J3_2").focus();
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
         * get I screen items list
         *
         * @author rafiqul.islam
         * */
        loadIScreenListData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API_ISCREEN.getStartupInfo + "/06").then(data => {
                let explanation = data.explanation;
                explanation = explanation.replace(/\\r/g, "\r");
                explanation = explanation.replace(/\\n/g, "\n");
                vm.scheduleAlarmCheckCond(new ScheduleAlarmCheckCond(data.code, data.conditionName, explanation.trim()));
                vm.items(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (vm.isIScreenStart) {
                    if (data.workTimeList.length > 0) {
                        vm.isIScreenUpdateMode(true);
                        vm.currentCode(data.workTimeList[0].code);
                        $("#I6_3").focus();
                    }
                    vm.isIScreenStart = false;
                }
                if (data.workTimeList.length === 0) {
                    vm.iScreenClickNewButton();
                }
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        /**
         * get J screen items list
         *
         * @author rafiqul.islam
         * */
        loadJScreenListData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API_JSCREEN.getStartupInfo).then(data => {
                vm.workPlace.unit(data.unit);
                vm.workPlace.workplaceId(data.workplaceId);
                vm.workPlace.workplaceGroupId(data.workplaceGroupId);
                vm.workPlace.workplaceTarget(data.workplaceTarget);
                vm.workPlace.workplaceCode(data.workplaceCode);
                vm.workPlace.workplaceName(data.workplaceName);
                vm.jItems(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (vm.isJScreenStart) {
                    if (data.workTimeList.length > 0) {
                        vm.isJScreenUpdateMode(true);
                        vm.jScreenCurrentCode(data.workTimeList[0].code);
                        $("J3_3").focus();
                    }
                    vm.isJScreenStart = false;
                }
                if (data.workTimeList.length === 0) {
                    vm.jScreenClickNewButton();
                }
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => vm.$blockui("clear"));
            return dfd.promise();
        }

        /**
         * get I screen details
         *
         * @param code
         * @author rafiqul.islam
         * */
        getIScreenDetails(code: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            if (code != "") {
                $("#I6_3").focus();
                vm.$blockui("invisible");
                vm.$ajax(API_ISCREEN.getList + "/" + code).then(data => {
                    vm.iScreenWorkingHour.code(data.code);
                    vm.iScreenWorkingHour.name(data.name);
                    vm.iScreenWorkingHour.numberOfConDays(data.maxDaysContiWorktime);
                    vm.iScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.iScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                    dfd.resolve();
                }).fail(err => {
                    dfd.reject();
                }).always(() => vm.$blockui("clear"));
            }
            return dfd.promise();
        }

        /**
         * get J screen details
         *
         * @param code
         * @author rafiqul.islam
         * */
        getJScreenDetails(code: string): JQueryPromise<any> {
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
                vm.$ajax(API_JSCREEN.getWorkHoursDetails, command).then(data => {
                    vm.jScreenWorkingHour.code(data.code);
                    vm.jScreenWorkingHour.name(data.name);
                    vm.jScreenWorkingHour.numberOfConDays(data.maxDaysContiWorktime);
                    vm.jScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.jScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                    dfd.resolve();
                }).fail(err => {
                    dfd.reject();
                }).always(() => vm.$blockui("clear"));
            }
            return dfd.promise();
        }

        /**
         * get J screen items list by target
         *
         * @author rafiqul.islam
         * */
        loadJScreenListDataByTarget(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            let command = {
                workPlaceUnit: vm.workPlace.unit(),
                workPlaceId: vm.workPlace.workplaceId(),
                workPlaceGroup: vm.workPlace.workplaceGroupId(),
            };
            vm.$blockui("invisible");
            vm.$ajax(API_JSCREEN.getWorkHoursList, command).then(data => {
                vm.jItems(_.map(data, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (data.length === 0) {
                    vm.jScreenClickNewButton();
                }
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => {
                if (vm.isKDL004StateChanged && vm.jItems().length > 0) {
                    if (vm.jItems()[0].code != vm.jScreenCurrentCode()) {
                        vm.jScreenCurrentCode(vm.jItems()[0].code);
                    } else {
                        vm.getJScreenDetails(vm.jItems()[0].code);
                    }
                    vm.isKDL004StateChanged = false;
                    $("#J3_3").focus();
                } else if (vm.jItems().length > 0) {
                    vm.getJScreenDetails(vm.jScreenCurrentCode());
                    $("#J3_3").focus();
                } else {
                    vm.jScreenClickNewButton();
                }
                vm.$blockui("clear")
            });
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
         * register I screen data
         *
         * @author rafiqul.islam
         * */
        iScreenClickRegister() {
            const vm = this;
            vm.$validate('#I6_2', '#I6_3', '#I8_2').then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.iScreenWorkingHour.workHour().length === 0) {
                    vm.$errors("#I7_2", "Msg_1844").then((valid: boolean) => {
                        $("#I7_2").focus();
                    });
                }
                else {
                    let codeList = vm.iScreenSeletedCodeList();
                    codeList = codeList.filter(function (el) {
                        return el != "";
                    });
                    let command = {
                        code: vm.iScreenWorkingHour.code(),
                        name: vm.iScreenWorkingHour.name(),
                        maxDaysContiWorktime: {
                            workTimeCodes: codeList,
                            numberOfDays: vm.iScreenWorkingHour.numberOfConDays()
                        }
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isIScreenUpdateMode() ? API_ISCREEN.update : API_ISCREEN.create, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                vm.loadIScreenListData();
                                vm.currentCode(vm.iScreenWorkingHour.code());
                                vm.getIScreenDetails(vm.iScreenWorkingHour.code());
                                $("#I6_3").focus();
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
         * this function is responsible for making I screen new mode
         *
         * @author rafiqul.islam
         * */
        iScreenClickNewButton() {
            const vm = this;
            vm.isIScreenUpdateMode(false);
            vm.$errors("clear", ".nts-editor", "button").then(() => {
                $("#I6_2").focus();
                vm.currentCode("");
                vm.cleanIScreenInputItem();
                vm.iScreenSeletedCodeList([]);
            });
        }

        /**
         * this function is responsible for making J screen new mode
         *
         * @author rafiqul.islam
         * */
        jScreenClickNewButton() {
            const vm = this;
            vm.isJScreenUpdateMode(false);
            vm.$errors("clear", ".nts-editor", "button");
            $("#J3_2").focus();
            vm.jScreenCurrentCode("");
            vm.cleanJScreenInputItem();
            vm.jScreenSeletedCodeList([]);
        }

        /**
         * register J screen data
         *
         * @author rafiqul.islam
         * */
        jScreenClickRegister() {
            const vm = this;
            vm.$validate('#J3_2', '#J3_3', '#J5_2').then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.jScreenWorkingHour.workHour().length === 0) {
                    vm.$errors("#J4_2", "Msg_1844").then((valid: boolean) => {
                        $("#J4_2").focus();
                    });
                } else {
                    let codeList = vm.jScreenSeletedCodeList().filter(function (el) {
                        return el != "";
                    });
                    let command = {
                        code: vm.jScreenWorkingHour.code(),
                        name: vm.jScreenWorkingHour.name(),
                        workPlaceUnit: vm.workPlace.unit(),
                        workPlaceId: vm.workPlace.workplaceId(),
                        workPlaceGroup: vm.workPlace.workplaceGroupId(),
                        workTimeCodes: codeList,
                        numberOfDays: vm.jScreenWorkingHour.numberOfConDays()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isJScreenUpdateMode() ? API_JSCREEN.update : API_JSCREEN.create, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                vm.jScreenCurrentCode(vm.jScreenWorkingHour.code());
                                vm.loadJScreenListDataByTarget();
                                $("#J3_3").focus();
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
         * get selectable code
         *
         * @param items
         * @param code
         * @return string
         * @author rafiqul.islam         *
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

        /**
         * Thi function is responsible to delete an item from the list
         *
         * @author rafiqul.islam
         * */
        jScreenClickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.jItems(), vm.jScreenWorkingHour.code());
                    let command = {
                        workPlaceUnit: vm.workPlace.unit(),
                        workPlaceId: vm.workPlace.workplaceId(),
                        workPlaceGroup: vm.workPlace.workplaceGroupId(),
                        code: vm.jScreenWorkingHour.code()
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API_JSCREEN.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.loadJScreenListDataByTarget();
                                vm.jScreenCurrentCode(selectableCode);
                                vm.getJScreenDetails(selectableCode);
                                if (vm.jItems().length === 0) {
                                    $("#J3_2").focus();
                                }
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
                $("#J3_3").focus();
            });
        }

        /**
         * Thi function is responsible to open KDL046 modal
         *
         * @author rafiqul.islam
         * */
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
                        console.log(selectedData);
                        vm.workPlace.unit(selectedData.unit);
                        if (selectedData.unit === 0) {
                            if (selectedData.workplaceId != vm.workPlace.workplaceId()) {
                                vm.isKDL004StateChanged = true;
                            }
                            vm.workPlace.workplaceName(selectedData.workplaceName);
                            vm.workPlace.workplaceCode(selectedData.workplaceCode);
                            vm.workPlace.workplaceId(selectedData.workplaceId);
                        } else {
                            if (selectedData.workplaceGroupId != vm.workPlace.workplaceGroupId()) {
                                vm.isKDL004StateChanged = true;
                            }
                            vm.workPlace.workplaceName(selectedData.workplaceGroupName);
                            vm.workPlace.workplaceGroupId(selectedData.workplaceGroupID);
                            vm.workPlace.workplaceCode(selectedData.workplaceGroupCode);
                        }
                    }
                })
                .always(() => vm.loadJScreenListDataByTarget());
        }

        /**
         * This function is responsible to clean I screen input item
         *
         * @author rafiqul.islam
         * */
        cleanIScreenInputItem() {
            const vm = this;
            vm.iScreenWorkingHour.code("");
            vm.iScreenWorkingHour.name("");
            vm.iScreenWorkingHour.numberOfConDays("");
            vm.iScreenWorkingHour.workHour("");
        }

        /**
         * This function is responsible to clean J screen input item
         *
         * @author rafiqul.islam
         * */
        cleanJScreenInputItem() {
            const vm = this;
            vm.jScreenWorkingHour.code("");
            vm.jScreenWorkingHour.name("");
            vm.jScreenWorkingHour.numberOfConDays("");
            vm.jScreenWorkingHour.workHour("");
        }

        /**
         * This function is responsible to delete an item from I screen
         *
         * @author rafiqul.islam
         * */
        iScreenClickDeleteButton() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    var selectableCode = vm.getSelectableCode(vm.items(), vm.iScreenWorkingHour.code());
                    let command = {
                        code: vm.iScreenWorkingHour.code(),
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(API_ISCREEN.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.cleanIScreenInputItem();
                                vm.loadIScreenListData();
                                vm.currentCode(selectableCode);
                                vm.getIScreenDetails(selectableCode);
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                        vm.$errors("clear");
                    });
                }
                $("#I6_3").focus();
            });
        }
    }

    class CommonCode {
        /** コード */
        code: KnockoutObservable<string>;
    }

    class ScheduleAlarmCheckCond extends CommonCode {
        /** 条件名 */
        conditionName: KnockoutObservable<string>;
        codeAndName: KnockoutObservable<string>;
        /** 説明 */
        descriptions: KnockoutObservable<string>;

        constructor(code: string, conditionName: string, descriptions: string) {
            super()
            this.code = ko.observable(code);
            this.conditionName = ko.observable(conditionName);
            this.codeAndName = ko.observable(code + " " + conditionName);
            this.descriptions = ko.observable(descriptions);
        };
    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class JscreenWorkHour extends CodeName {
        /** 選択 */
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
        maxNumberOfDay: Number;
        /** コード */
        code: string;
        /** 名称 */
        name: string;

        constructor(code: string, name: string, maxNumberOfDay: Number) {
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

