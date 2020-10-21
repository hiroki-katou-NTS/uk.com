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
        iScreenFoucs: FocusItem = new FocusItem(true, false, false);
        jScreenFoucs: FocusItem = new FocusItem(true, false, false);
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
        isIScreenUpdateMode: boolean = false;
        isIScreenStart: boolean = true;
        isJScreenStart: boolean = true;
        jScreenCurrentCode: KnockoutObservable<any>;
        jScreenSeletedCodeList: KnockoutObservableArray<any>;
        jScreenSeletableCodeList: KnockoutObservableArray<any>;
        jScreenCodeList: KnockoutObservableArray<string>;
        isJScreenUpdateMode: boolean = false;

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
            vm.isIScreenUpdateMode = false;
            vm.isJScreenUpdateMode = false;
            vm.isIScreenStart = true;
            vm.isJScreenStart = true;
            vm.initialFocus = ko.observable(true);
            vm.jInitialFocus = true;
            $(document).ready(
                function () {
                    setTimeout(function () {
                        vm.initialFocus(true);
                    }, 300);
                });
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getIScreenDetails(newValue);
                    this.isIScreenUpdateMode = true;
                    $('#I6_3').focus();
                }
            });
            vm.jScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                if (newValue != "") {
                    vm.getJScreenDetails(newValue);
                    this.isJScreenUpdateMode = true;
                    $('#J3_3').focus();
                }
            });
            vm.iScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#I7_2");
            });
            vm.iScreenWorkingHour.code.subscribe((newValue: any) => {
                vm.$errors("clear", "#I6_2");
            });
            vm.jScreenWorkingHour.workHour.subscribe((newValue: any) => {
                vm.$errors("clear", "#J4_2");
            });
            vm.jScreenWorkingHour.code.subscribe((newValue: any) => {
                vm.$errors("clear", "#J3_2");
            });
            vm.$ajax(API_ISCREEN.getAllWorkingHours).then(data => {
                vm.initialCodeList(data.map(function (item: any) {
                    return item.code;
                }));
                vm.workingHours(_.map(data, function (item: any) {
                    return new WorkingHour(item.code, item.name)
                }));
            });
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
            setTimeout(function () {
                if (vm.jScreenFoucs.isCodeFoucs) {
                    $("#J3_2").focus();
                }
                if (vm.jScreenFoucs.isNameFocus) {
                    $("#J3_3").focus();
                }
                if (vm.jScreenFoucs.isButtonFocus) {
                    $("#J4_2").focus();
                }
            }, 0);
        }

        /**
         * Function execute after company tab select
         *
         * @author rafiqul.islam
         * */
        onCompanySelect() {
            const vm = this;
            setTimeout(function () {
                if (vm.iScreenFoucs.isCodeFoucs) {
                    $("#I6_2").focus();
                }
                if (vm.iScreenFoucs.isNameFocus) {
                    $("#I6_3").focus();
                }
                if (vm.iScreenFoucs.isButtonFocus) {
                    $("#I7_2").focus();
                }
            }, 0);
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
         *
         * @author rafiqul.islam
         * */
        loadIScreenListData() {
            const vm = this;
            vm.$ajax(API_ISCREEN.getStartupInfo + "/06").then(data => {
                vm.scheduleAlarmCheckCond(new ScheduleAlarmCheckCond(data.code, data.conditionName, data.explanation));
                vm.items(_.map(data.workTimeList, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
                if (vm.isIScreenStart) {
                    if (data.workTimeList.length > 0) {
                        vm.isIScreenUpdateMode = true;
                        vm.currentCode(data.workTimeList[0].code);
                        vm.iScreenFoucs = new FocusItem(false, true, false);
                    }
                    vm.isIScreenStart = false;
                }
                if(data.workTimeList.length===0){
                    vm.iScreenClickNewButton();
                }
            });
        }

        /**
         * get J screen items list
         *
         * @author rafiqul.islam
         * */
        loadJScreenListData() {
            const vm = this;
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
                        vm.isJScreenUpdateMode = true;
                        vm.jScreenCurrentCode(data.workTimeList[0].code);
                    }
                    vm.isJScreenStart = false;
                }
                if(data.workTimeList.length===0){
                    vm.jScreenClickNewButton();
                }
            });
        }

        /**
         * get I screen details
         *
         * @param code
         * @author rafiqul.islam
         * */
        getIScreenDetails(code: string) {
            const vm = this;
            if (code != "") {
                $("#I6_3").focus();
                vm.iScreenFoucs = new FocusItem(false, true, false);
                vm.$ajax(API_ISCREEN.getList + "/" + code).then(data => {
                    vm.iScreenWorkingHour.code(data.code);
                    vm.iScreenWorkingHour.name(data.name);
                    vm.iScreenWorkingHour.numberOfConDays(data.maxDaysContiWorktime);
                    vm.iScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.iScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                });
            }
        }

        /**
         * get J screen details
         *
         * @param code
         * @author rafiqul.islam
         * */
        getJScreenDetails(code: string) {
            const vm = this;
            if (code != "") {
                vm.jScreenFoucs = new FocusItem(false, true, false);
                let command = {
                    workPlaceUnit: vm.workPlace.unit(),
                    workPlaceId: vm.workPlace.workplaceId(),
                    workPlaceGroup: vm.workPlace.workplaceGroupId(),
                    code: code
                };
                vm.$ajax(API_JSCREEN.getWorkHoursDetails, command).then(data => {
                    vm.jScreenWorkingHour.code(data.code);
                    vm.jScreenWorkingHour.name(data.name);
                    vm.jScreenWorkingHour.numberOfConDays(data.maxDaysContiWorktime);
                    vm.jScreenSeletedCodeList(_.map(data.workingHours, function (item: any) {
                        return new String(item.code)
                    }));
                    vm.jScreenWorkingHour.workHour(vm.generateWorkHourName(data.workingHours));
                });
            }
        }

        /**
         * get J screen items list by target
         *
         * @author rafiqul.islam
         * */
        loadJScreenListDataByTarget() {
            const vm = this;
            let command = {
                workPlaceUnit: vm.workPlace.unit(),
                workPlaceId: vm.workPlace.workplaceId(),
                workPlaceGroup: vm.workPlace.workplaceGroupId(),
            };
            vm.$ajax(API_JSCREEN.getWorkHoursList, command).then(data => {
                vm.jItems(_.map(data, function (item: any) {
                    return new ItemModel(item.code, item.name, item.maxNumbeOfWorkingDays)
                }));
            });
        }

        /**
         * prepare work hours name
         *
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

        /**
         * register I screen data
         *
         * @author rafiqul.islam
         * */
        iScreenClickRegister() {
            const vm = this;
            vm.$errors("clear");
            if (vm.iScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#I7_2", "Msg_1844").then((valid: boolean) => {
                    $("#I7_2").focus();
                    vm.iScreenFoucs.isButtonFocus = true;
                });
            } else {
                let command = {
                    code: vm.iScreenWorkingHour.code(),
                    name: vm.iScreenWorkingHour.name(),
                    maxDaysContiWorktime: {
                        workTimeCodes: vm.iScreenSeletedCodeList(),
                        numberOfDays: vm.iScreenWorkingHour.numberOfConDays()
                    }
                };
                vm.$ajax(vm.isIScreenUpdateMode ? API_ISCREEN.update : API_ISCREEN.create, command).done((data) => {
                    vm.$dialog.info({messageId: "Msg_15"})
                        .then(() => {
                            vm.loadIScreenListData();
                            vm.currentCode(vm.iScreenWorkingHour.code());
                            vm.getIScreenDetails(vm.iScreenWorkingHour.code());
                            $("#I6_3").focus();
                            vm.iScreenFoucs.isNameFocus = true;
                        });
                }).fail(function (error) {
                    vm.$dialog.error({messageId: error.messageId});
                }).always(() => {
                    vm.$blockui("clear");
                    vm.$errors("clear");
                });
            }
        }

        /**
         * this function is responsible for making I screen new mode
         *
         * @author rafiqul.islam
         * */
        iScreenClickNewButton() {
            const vm = this;
            vm.isIScreenUpdateMode = false;
            vm.$errors("clear");
            $("#I6_2").focus();
            vm.iScreenFoucs = new FocusItem(true, false, false);
            vm.currentCode("");
            vm.cleanIScreenInputItem();
            vm.iScreenSeletedCodeList([]);
        }

        /**
         * this function is responsible for making J screen new mode
         *
         * @author rafiqul.islam
         * */
        jScreenClickNewButton() {
            const vm = this;
            vm.isJScreenUpdateMode = false;
            vm.$errors("clear");
            $("#J3_2").focus();
            vm.jScreenFoucs = new FocusItem(true, false, false);
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
            vm.$errors("clear");
            if (vm.jScreenWorkingHour.workHour().length === 0) {
                vm.$errors("#J4_2", "Msg_1844").then((valid: boolean) => {
                    $("#J4_2").focus();
                    this.jScreenFoucs.isButtonFocus = true;
                });
            } else {
                let command = {
                    code: vm.jScreenWorkingHour.code(),
                    name: vm.jScreenWorkingHour.name(),
                    workPlaceUnit: vm.workPlace.unit(),
                    workPlaceId: vm.workPlace.workplaceId(),
                    workPlaceGroup: vm.workPlace.workplaceGroupId(),
                    workTimeCodes: vm.jScreenSeletedCodeList(),
                    numberOfDays: vm.jScreenWorkingHour.numberOfConDays()
                };
                vm.$ajax(vm.isJScreenUpdateMode ? API_JSCREEN.update : API_JSCREEN.create, command).done((data) => {
                    vm.$dialog.info({messageId: "Msg_15"})
                        .then(() => {
                            vm.loadJScreenListData();
                            vm.jScreenCurrentCode(vm.jScreenWorkingHour.code());
                            vm.getJScreenDetails(vm.jScreenWorkingHour.code());
                            $("#J3_3").focus();
                            this.jScreenFoucs.isNameFocus = true;
                        });
                }).fail(function (error) {
                    vm.$dialog.error({messageId: error.messageId});
                }).always(() => {
                    vm.$blockui("clear");
                    vm.$errors("clear");
                });
            }
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
                    vm.$ajax(API_JSCREEN.delete, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_16"})
                            .then(() => {
                                vm.cleanJScreenInputItem();
                                vm.loadJScreenListData();
                                vm.jScreenCurrentCode(selectableCode);
                                vm.getJScreenDetails(selectableCode);
                                if (vm.jItems().length === 0) {
                                    $("#J3_2").focus();
                                    this.jScreenFoucs.isCodeFoucs = true;
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
            }
            const data = {
                dataShareDialog046: request
            };
            setShared('dataShareDialog046', request);
            vm.$window.modal('/view/kdl/046/a/index.xhtml')
                .then((result: any) => {
                    let selectedData = nts.uk.ui.windows.getShared('dataShareDialog046');
                    console.log(selectedData);
                    vm.workPlace.unit(selectedData.unit);
                    if (selectedData.unit === 0) {
                        if (selectedData.workplaceCode != vm.workPlace.workplaceCode()) {
                            vm.jScreenClickNewButton();
                        }
                        vm.workPlace.workplaceName(selectedData.workplaceName);
                        vm.workPlace.workplaceCode(selectedData.workplaceCode);
                        vm.workPlace.workplaceId(selectedData.workplaceId);
                    } else {
                        if (selectedData.workplaceGroupCode != vm.workPlace.workplaceCode()) {
                            vm.jScreenClickNewButton();
                        }
                        vm.workPlace.workplaceName(selectedData.workplaceGroupName);
                        vm.workPlace.workplaceGroupId(selectedData.workplaceGroupID);
                        vm.workPlace.workplaceCode(selectedData.workplaceGroupCode);
                    }
                    vm.loadJScreenListDataByTarget();
                });
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
            });
        }
    }

    class CommonCode {
        code: KnockoutObservable<string>;
    }

    class ScheduleAlarmCheckCond extends CommonCode {
        conditionName: KnockoutObservable<string>;
        codeAndName: KnockoutObservable<string>;
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

    class WorkPlace {
        unit: KnockoutObservable<number>;
        workplaceId: KnockoutObservable<string>;
        workplaceGroupId: KnockoutObservable<string>;
        workplaceTarget: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;
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

