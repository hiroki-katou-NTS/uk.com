/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm003.a {

    import baseService = nts.uk.at.view.kdl023.base.service;
    import ReflectionSetting = baseService.model.ReflectionSetting;
    import DayOffSetting = baseService.model.DayOffSetting;

    const PATH_API = {
        getListWorkCycleDto: 'screen/at/ksm003/a/get',
        getListWorkCycleDtoByCode: 'screen/at/ksm003/a/getByCode/',
        findWordType: "at/share/worktype/findNotDeprecatedByListCode",
        findWorkTimeByCode: "at/shared/worktimesetting/findByCodes",
        patternDailyUpdate: "ctx/at/schedule/pattern/work/cycle/update",
        patternDailyDelete: "ctx/at/schedule/pattern/work/cycle/delete",
        patternDailyRegister: "ctx/at/schedule/pattern/work/cycle/register",
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        // Init
        items: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable(null);
        selectedName: KnockoutObservable<string> = ko.observable(null);
        isEditting: KnockoutObservable<boolean> = ko.observable(false);
        enableRemoveItem: KnockoutObservable<boolean> = ko.observable(false);
        lessThan99Items: KnockoutObservable<boolean> = ko.observable(true);
        itemLst: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        mainModel: KnockoutObservable<DailyPatternDetailModel> = ko.observable(null);
        currentCode: KnockoutObservable<any> = ko.observable(null);
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        dailyPatternValModel: KnockoutObservableArray<DailyPatternValModel> = ko.observableArray([]);
        selectedCheckAll: KnockoutObservable<boolean> = ko.observable(false);
        dayIsRequired: KnockoutObservable<boolean> = ko.observable(false);

        maxWorkingTimeItems: number = 99;

        constructor(params: any) {
            super();
            let vm = this,
                dailyPatternVals: Array<DailyPatternValModel> = [];

            dailyPatternVals.push(new DailyPatternValModel(null, "", "", null));
            vm.mainModel(new DailyPatternDetailModel("", "", dailyPatternVals));
        }

        created(params: any) {
            let vm = this;
            vm.getListWorkingCycle();

            _.extend(window, { vm });
        }

        mounted() {

            let vm = this;

            $("#fixedTable").ntsFixedTable({ height: 442, width: 450 });

            vm.selectedCode.subscribe(function (codeChanged: string) {
                vm.displayWorkingInfo(codeChanged);
            });

            //enable remove button
            vm.currentCodeList.subscribe(function (codeSelected) {
                vm.enableRemoveItem(codeSelected.length > 0);
            });

            vm.selectedCheckAll.subscribe(function (value: boolean) {
                let dailyPatternItems: Array<DailyPatternValModel> = [];
                let checkedAll: boolean = value;
                let hasOneItemChecked: boolean = false;
                let totalItemCheked: number = 0;
                vm.enableRemoveItem(false);
                vm.dailyPatternValModel() && vm.dailyPatternValModel().map((item, i) => {
                    if (checkedAll) item.isChecked(checkedAll);
                    dailyPatternItems.push(item);
                    if (item.isChecked()) totalItemCheked++;
                    if (!hasOneItemChecked) hasOneItemChecked = item.isChecked();
                });

                if (totalItemCheked == vm.dailyPatternValModel().length && !checkedAll) {
                    vm.enableRemoveItem(false);
                    dailyPatternItems = [];
                    vm.dailyPatternValModel().map((item, i) => {
                        item.isChecked(false);
                        dailyPatternItems.push(item);
                    });
                } else
                    vm.enableRemoveItem(hasOneItemChecked);

                vm.dailyPatternValModel([]);
                vm.dailyPatternValModel(dailyPatternItems);
            });
        }

        getListWorkingCycle() {
            let vm = this;
            vm.$ajax(PATH_API.getListWorkCycleDto).done(
                (dataRes: Array<DailyPatternItemDto>) => {
                    if (dataRes === undefined || dataRes.length == 0) {
                        vm.itemLst([]);
                        vm.switchNewMode();
                    } else {
                        vm.itemLst([]);
                        vm.itemLst(dataRes);

                        if (nts.uk.util.isNullOrEmpty(vm.selectedCode())) {
                            vm.selectedCode(vm.itemLst()[0].code);
                            vm.selectedName(vm.itemLst()[0].name);
                        }

                        $("#inpPattern").focus();
                    }
                }
            ).fail((error) => {
                vm.$dialog.info({ messageId: error.messageId }).then(() => {
                    return;
                });
            });
        }

        public switchNewMode(): void {
            let self = this;

            nts.uk.ui.errors.clearAll();

            self.isEditting(false);
            //model
            self.selectedCode("");
            self.selectedName("");

            self.mainModel().patternName("");
            self.mainModel().resetModel();

            self.selectedCheckAll(false);
            self.addNewLineItem(true);

            $("#inpCode").focus();
        }

        enableDisableRemove(flag: boolean, obj: any) {
            let vm = this;
            let dailyPatternValModel = vm.dailyPatternValModel();
            let totalChecked: number = 0;

            dailyPatternValModel && dailyPatternValModel.map((item, i) => {
                if (item.dispOrder == obj.dispOrder) {
                    dailyPatternValModel[i].isChecked(flag);
                }

                if (item.isChecked()) totalChecked++;
            });

            vm.enableRemoveItem(totalChecked > 0);
            vm.selectedCheckAll(totalChecked == dailyPatternValModel.length);
        }

        addNewItem() {
            let vm = this;
            vm.dayIsRequired(true);
            vm.addNewLineItem(false);
            vm.selectedCheckAll(false);
        }

        // get Pattern Val By PatternCd form database
        public getPatternValByPatternCd(patternCode: string): void {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];

            vm.$blockui('show');

            vm.$ajax(PATH_API.getListWorkCycleDtoByCode + patternCode)
                .done(function (dataRes) {
                    if (dataRes !== undefined) {

                        nts.uk.ui.errors.clearAll();

                        vm.isEditting(true);
                        vm.dayIsRequired(true);

                        vm.selectedCode(dataRes.code);
                        vm.selectedName(dataRes.name);

                        //disabel addNew button
                        vm.lessThan99Items(dataRes.infos.length <= vm.maxWorkingTimeItems);

                        //get list item
                        let dailyPatternVals = dataRes.infos.map(function (item) {
                            return new DailyPatternValModel(
                                item.dispOrder,
                                item.typeCode,
                                item.timeCode,
                                item.days
                            );
                        });

                        //init list work type code
                        let lstWorkTypeCode: Array<string> = [];
                        _.forEach(dailyPatternVals, (item, index) => {
                            if (item.typeCode() && item.typeCode() != "") {
                                lstWorkTypeCode.push(item.typeCode());
                            }
                        });

                        //find work type
                        vm.$ajax(
                            PATH_API.findWordType,
                            lstWorkTypeCode
                        ).done((data: Array<WorkTypeDto>) => {
                            lstWorkType = data;
                            //init list work time code
                            let lstWorkTimeCode: Array<string> = [];
                            _.forEach(dailyPatternVals, (item, index) => {
                                if (item.timeCode() && item.timeCode() != "") {
                                    lstWorkTimeCode.push(item.timeCode());
                                }
                            });

                            vm.$ajax(
                                PATH_API.findWorkTimeByCode,
                                lstWorkTimeCode
                            ).done((data: Array<WorkTimeDto>) => {
                                lstWorkTime = data;
                                vm.updateDataModel(dataRes, lstWorkType, lstWorkTime);
                            });

                            vm.$blockui('hide');

                        }).always(() => {
                            vm.$blockui('hide');
                            $("#inpPattern").focus();
                        });
                    }
                }).always(() => vm.$blockui('hide'));
        }

        private updateDataModel(dataRes: DailyPatternDetailDto,
            lstWorkType: Array<WorkTypeDto>,
            lstWorkTime: Array<WorkTimeDto>) {

            let vm = this;
            //sort list by order
            let lstVal: Array<DailyPatternValDto> = dataRes.infos;
            lstVal = _.sortBy(lstVal, (item) => item.dispOrder);

            dataRes.infos = lstVal;

            //reset pattern Code & Name
            vm.mainModel().patternCode(dataRes.code);
            vm.mainModel().patternName(dataRes.name);

            let dailyPatternVals: Array<DailyPatternValModel> = [];

            lstVal && lstVal.map((item, i) => {
                let dailyPatternValModel = new DailyPatternValModel(
                    item.dispOrder,
                    item.typeCode,
                    item.timeCode,
                    item.days
                );

                let workType = _.find(lstWorkType, (element) => {
                    return element.workTypeCode == item.typeCode
                });

                let workTypeName = nts.uk.util.isNullOrUndefined(workType) ? vm.$i18n('KSM003_2') : workType.name;
                dailyPatternValModel.setWorkTypeName(workTypeName);

                let workTimeName = '';
                if (nts.uk.util.isNullOrEmpty(item.timeCode)) {
                    workTimeName = '';
                } else {
                    let workTime = _.find(lstWorkTime, (element) => {
                        return element.code == item.timeCode
                    });

                    workTimeName = nts.uk.util.isNullOrUndefined(workTime) ? vm.$i18n('KSM003_2') : workTime.name;
                }

                dailyPatternValModel.setWorkTimeName(workTimeName);
                dailyPatternVals.push(dailyPatternValModel);
            });

            vm.dailyPatternValModel(dailyPatternVals);
            vm.mainModel().dailyPatternVals(dailyPatternVals);
        }

        /**
         * Add new line
         * @param isAddNew : boolean
         * */
        addNewLineItem(isAddNew: boolean) {
            let vm = this;
            //let id: number;
            let dailyPatternVals = vm.mainModel().dailyPatternVals();
            let totalItems: number = dailyPatternVals.length > 0 ? dailyPatternVals.length + 1 : 1;

            if (totalItems >= vm.maxWorkingTimeItems) {
                vm.lessThan99Items(false);
            } else
                vm.lessThan99Items(true);

            /* if (totalItems > vm.maxWorkingTimeItems) {
                vm.$dialog.info({ messageId: "Msg_1688" }).then(() => {
                    vm.lessThan99Items(false);
                });
                return;
            } */

            nts.uk.ui.errors.clearAll();

            let maxDisplayOrder = 0;
            dailyPatternVals && dailyPatternVals.map((item, i) => {
                if (item.dispOrder > maxDisplayOrder) maxDisplayOrder = item.dispOrder;
            });

            let dailyPatternValModel = new DailyPatternValModel(maxDisplayOrder + 1, null, null, null);
            //add to model
            if (isAddNew) {
                let empty: Array<DailyPatternValModel> = [];
                vm.mainModel().dailyPatternVals(empty);
                vm.enableRemoveItem(false);
                vm.dailyPatternValModel([]);
                dailyPatternVals = [];
            }

            dailyPatternVals.push(dailyPatternValModel);
            vm.dailyPatternValModel(dailyPatternVals);
            vm.mainModel().dailyPatternVals(dailyPatternVals);
        }

        /**
         * Remove a/multiple item on grid
         * */
        removeLine() {
            const vm = this;
            let currentCodeList = vm.currentCodeList();
            let currentDataList = vm.mainModel().dailyPatternVals();
            let dailyPatternValModel: Array<DailyPatternValModel> = [];
            currentCodeList && currentDataList && currentDataList.map((item, i) => {
                if (!item.isChecked()) {
                    dailyPatternValModel.push(item);
                }
            });
            //update model
            vm.mainModel().dailyPatternVals([]);
            vm.mainModel().dailyPatternVals(dailyPatternValModel);
            //update data resource
            vm.dailyPatternValModel([]);
            vm.dailyPatternValModel(dailyPatternValModel);
            vm.lessThan99Items(true);
            if (dailyPatternValModel.length <= 0) vm.selectedCheckAll(false);
            vm.enableRemoveItem(false);

            nts.uk.ui.errors.clearAll();
        }

        /*
        * Disable Addnew button
        * */
        disableAddNewLine() {
            let vm = this;
            let dailyPatternVals = vm.mainModel().dailyPatternVals();
            vm.lessThan99Items(dailyPatternVals.length <= vm.maxWorkingTimeItems);
        }

        //click button open Dialog Working
        openDialogWorking() {
            let self = this;
            let data: ReflectionSetting = {
                selectedPatternCd: self.selectedCode(),
                patternStartDate: moment(new Date()).startOf('month').format('YYYY-MM-DD').toString(),
                reflectionMethod: 0,
                statutorySetting: self.convertWorktypeSetting(true, ''),
                holidaySetting: self.convertWorktypeSetting(true, ''),
                nonStatutorySetting: self.convertWorktypeSetting(true, '')
            };

            self.$window.storage('reflectionSetting', ko.toJS(data));
            self.$window.modal("/view/kdl/023/a/index.xhtml", {
                title: nts.uk.resource.getText("KDL023_1"),
            }).then(() => { });
        }

        convertWorktypeSetting(use: boolean, worktypeCode: string): DayOffSetting {
            let data: DayOffSetting = {
                useClassification: use,
                workTypeCode: worktypeCode
            };
            return data;
        }

        // delete Pattern
        deletePattern() {
            let vm = this;
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result) => {

                vm.$blockui('show');

                if (result === 'yes') {

                    let dataHistory: DailyPatternItemDto[] = vm.itemLst();

                    if (nts.uk.util.isNullOrEmpty(vm.selectedCode())) {
                        vm.$blockui('hide');
                        return;
                    }

                    vm.$ajax(PATH_API.patternDailyDelete, { workCycleCode: vm.selectedCode() })
                        .done((response) => {

                            vm.$dialog.info({ messageId: "Msg_16" }).then(function () {
                                vm.reloadAfterDelete(dataHistory);
                            });

                            vm.$blockui('hide');

                        }).fail(function (res) {

                            vm.$dialog.error(res.message).then(() => {
                                vm.$blockui('hide');
                            });

                        }).always(function () {
                            vm.$blockui('hide');
                        });

                } else {
                    vm.$blockui('hide');
                    return;
                }
            });
        }

        /*
        * Re-Selected
        * */
        reloadAfterDelete(dataHistory) {
            let vm = this;
            //reload
            vm.getListWorkingCycle();
            //re-selected
            if (nts.uk.util.isNullOrEmpty(vm.itemLst())) {
                vm.switchNewMode();
            }

            //get first, last item and set to default
            let newSelectedItem: DailyPatternItemDto = null;
            let totalRecord = dataHistory.length;

            dataHistory && dataHistory.some((item, i) => {
                if (item.code == vm.selectedCode()) {
                    if (totalRecord == 1) {
                        vm.itemLst([]);
                        vm.switchNewMode()
                        return;
                    } else if (i < totalRecord - 1) {
                        newSelectedItem = dataHistory[i + 1];
                    } else {
                        newSelectedItem = dataHistory[i - 1];
                    }

                    vm.getPatternValByPatternCd(newSelectedItem.code);
                    vm.isEditting(true);

                    return;
                }
            });
        }

        /**
         * export excel
         */
        downloadExcel() {
            let self = this;
            self.exportExcel()
                .done(function (data) {
                }).fail(function (res: any) {
                    self.$dialog.error(res).then(() => self.$blockui('clear'));
                }).always(() => {
                    self.$blockui('clear');
                });
        }

        exportExcel(): JQueryPromise<any> {
            let self = this,
                program = self.$program.programName.split(" ");
            let domainType = "KSM003";
            if (program.length > 1) {
                program.shift();
                domainType = domainType + program.join(" ");
            }
            return nts.uk.request.exportFile('/masterlist/report/print', {
                domainId: "RegisterPattern",
                domainType: domainType,
                languageId: 'ja',
                reportType: 0
            });
        }

        /**
         * Save Working
         * */
        saveWorking() {
            let vm = this;
            let workingTimeCycleList = vm.mainModel().dailyPatternVals();

            vm.$blockui('show');

            //登録の時には勤務内容一覧に一行もない //register & update
            if (workingTimeCycleList.length <= 0) {
                vm.$errors({
                    "#fixed-table-list": {
                        messageId: "Msg_1690",
                        messageParams: [vm.$i18n("KSM003_23")]
                    }
                }).then((valid: boolean) => {
                    vm.$blockui('hide');
                });

                return;

            } else if (workingTimeCycleList.length > vm.maxWorkingTimeItems) {
                vm.$dialog.info({ messageId: "Msg_1688" }).then(() => {
                    vm.$blockui('hide');
                });

                return;
            }

            if (vm.validate()) {
                vm.$blockui('hide');
                return;
            }

            let messageIds: Array<string> = ["Msg_23", "Msg_24", , "Msg_25", "Msg_389", "Msg_390",
                "Msg_416", "Msg_417", "Msg_434", "Msg_435", "Msg_3", "Msg_1608", "Msg_1609"];


            let detailDto = vm.mainModel().toDto();

            if (!vm.isEditting()) {
                let selectedCode = vm.selectedCode();
                let selectedName = vm.selectedName();
                if (selectedCode !== '') {
                    vm.mainModel().patternCode(selectedCode);
                    vm.mainModel().patternName(selectedName);
                    let dailyPatternVals = vm.mainModel().dailyPatternVals();
                    let lstVal: Array<DailyPatternValDto> = [];

                    dailyPatternVals && dailyPatternVals.map((item, index) => {
                        let dailyPatternValDto = new DailyPatternValDto(
                            item.dispOrder,
                            item.typeCode(),
                            item.timeCode(),
                            item.days()
                        );
                        lstVal.push(dailyPatternValDto);
                    })

                    detailDto = new DailyPatternDetailDto(selectedCode, selectedName, lstVal);
                }
            } else {
                detailDto.workCycleName = detailDto.name; //vm.selectedName();
            }

            this.saveDailyPattern(detailDto).done(function (res) {
                let MsgId = '';
                let infosData = detailDto.infos;
                if (res.errorStatusList.length > 0) {
                    res.errorStatusList.map((error_type, i) => {
                        switch (error_type) {
                            case 'WORKTIME_WAS_DELETE':
                                MsgId = "Msg_1609";
                                break;
                            case 'WORKTYPE_WAS_DELETE':
                                MsgId = "Msg_1608";
                                break;
                            case 'WORKTIME_ARE_REQUIRE_NOT_SET':
                                MsgId = "Msg_435";
                                break;
                            case 'WORKTIME_ARE_SET_WHEN_UNNECESSARY':
                                MsgId = "Msg_434";
                                break;
                            case 'WORKTYPE_WAS_ABOLISHED':
                                MsgId = "Msg_416";
                                break;
                            case 'WORKTIME_HAS_BEEN_ABOLISHED':
                                MsgId = "Msg_417";
                                break;
                            default: //NORMAL
                                MsgId = '';
                                break;
                        }

                        if (!nts.uk.util.isNullOrEmpty(MsgId)) {
                            $('#btnVal' + infosData[i].dispOrder).ntsError('set', { messageId: MsgId });
                        }
                    })

                    vm.$blockui('hide');
                    return;
                }

                //register / update i ok
                vm.$dialog.info({ messageId: "Msg_15" }).then(() => { });

                let patternCode = vm.mainModel().patternCode();

                vm.selectedCode(patternCode);
                vm.selectedCheckAll(false);
                vm.enableRemoveItem(false);

                $("#inpPattern").focus();

                if (!vm.isEditting()) vm.isEditting(true);

                vm.getListWorkingCycle();
                vm.getPatternValByPatternCd(patternCode);

                vm.$blockui("hide");

            }).fail(function (res) {
                let isSetError = messageIds.some(item => item == res.messageId);
                if (isSetError) {
                    vm.$errors("#inpCode", res.messageId).then((valid: boolean) => { });
                } else {
                    vm.$dialog.error({ messageId: res.message }).then(() => { });
                }
                vm.$blockui("hide");
            }).always(function () {
                vm.$blockui("hide");
            });
        }

        //validate form
        private validate(): boolean {

            let self = this;
            $('#inpCode').ntsEditor('validate');
            $('#inpPattern').ntsEditor('validate');

            if (nts.uk.ui.errors.hasError()) {
                return true;
            }

            if (nts.uk.util.isNullOrEmpty(self.mainModel().dailyPatternVals())) {
                $('.fixed-table').ntsError('set', { messageId: "Msg_31" });
            }

            let hasRowIsNull: boolean = false;

            self.mainModel().dailyPatternVals().map((item, index) => {
                //day > 0 & workingCode null
                if (nts.uk.text.isNullOrEmpty(item.typeCode())) {
                    $('#btnVal' + item.dispOrder).ntsError('set', { messageId: "Msg_1690", messageParams: [self.$i18n('KSM003_23')] });
                    hasRowIsNull = true;
                }

                if (!hasRowIsNull && (nts.uk.text.isNullOrEmpty(item.days()) || item.days() <= 0)) {
                    $('#days' + item.dispOrder).ntsEditor('validate');
                    hasRowIsNull = true;
                }
            });

            return hasRowIsNull;
        }

        saveDailyPattern(dto: DailyPatternDetailDto): JQueryPromise<Array<DailyPatternDetailModel>> {
            let self = this,
                apiUrl = (!this.isEditting()) ? PATH_API.patternDailyRegister : PATH_API.patternDailyUpdate;
            return self.$ajax(apiUrl, dto);
        }

        /**
         * Move up calculator item.
         */
        upBtn() {
            var self = this;
            var prevIdx = -1;
            var $scope = _.clone(self.dailyPatternValModel());
            var temp = [];

            var multiRowSelected = _.filter($scope, function (item: DailyPatternValModel) {
                return item.isChecked();
            });

            _.forEach(multiRowSelected, function (item) {
                var idx = _.findIndex($scope, function (o: DailyPatternValModel) { return o.dispOrder == item.dispOrder; });
                if (idx - 1 === prevIdx) {
                    prevIdx = idx
                } else if (idx > 0) {
                    var itemToMove = $scope.splice(idx, 1)
                    $scope.splice(idx - 1, 0, itemToMove[0]);
                }
            });

            _.forEach($scope, function (item, index) {
                item.dispOrder = index + 1;
                temp.push(item);
            });

            self.dailyPatternValModel(temp);
        }

        /**
         * Move down calculator item.
         */
        downBtn() {
            var self = this;
            var $scope = _.clone(self.dailyPatternValModel());
            var prevIdx = $scope.length;
            var temp = [];

            var multiRowSelected = _.filter($scope, function (item: DailyPatternValModel) {
                return item.isChecked();
            });

            var revPerson = multiRowSelected.concat();
            revPerson.reverse();

            for (var i = 0; i < revPerson.length; i++) {
                var item = revPerson[i];
                var idx = _.findIndex($scope, function (o: DailyPatternValModel) { return o.dispOrder == item.dispOrder; });
                if (idx + 1 === prevIdx) {
                    prevIdx = idx
                } else if (idx < $scope.length - 1) {
                    var itemToMove = $scope.splice(idx, 1)
                    $scope.splice(idx + 1, 0, itemToMove[0]);
                }
            }

            _.forEach($scope, function (item, index) {
                item.dispOrder = index + 1;
                temp.push(item);
            });

            self.dailyPatternValModel(temp);
        }

        /**
         * open dialog KDL003 by Work Days
         */
        public openDialogToSetWorkDays(data: DailyPatternValModel) {
            var self = this,
                currentRow: DailyPatternValModel = data;

            self.$window.storage("parentCodes", {
                selectedWorkTypeCode: currentRow.typeCode(),
                selectedWorkTimeCode: currentRow.timeCode()
            });

            //self.$window.storage("childData", undefined).then(() => {
            self.$window
                .modal("/view/kdl/003/a/index.xhtml", { title: self.$i18n("KDL003_1") })
                .then(function () {
                    self.$window.storage("childData").then((resultData) => {
                        if (resultData) {
                            currentRow.typeCode(resultData.selectedWorkTypeCode);
                            currentRow.timeCode(resultData.selectedWorkTimeCode);
                            currentRow.setWorkTypeName(resultData.selectedWorkTypeName);
                            currentRow.setWorkTimeName(resultData.selectedWorkTimeName);

                            //update model
                            let dailyPatternVals = self.mainModel().dailyPatternVals();
                            dailyPatternVals.map((item, i) => {
                                if (item.displayOrder == currentRow.dispOrder) {
                                    dailyPatternVals[i].typeCode(resultData.selectedWorkTypeCode);
                                    dailyPatternVals[i].timeCode(resultData.selectedWorkTimeCode);
                                }
                            });

                            self.mainModel().dailyPatternVals([]);
                            self.mainModel().dailyPatternVals(dailyPatternVals);

                            nts.uk.ui.errors.clearAll();
                        }
                    });
                });
            //});
        }

        displayWorkingInfo(patternCode: string) {
            let vm = this;

            vm.dayIsRequired(true);
            vm.selectedCheckAll(false);
            vm.enableRemoveItem(false);

            //vm.$errors('clear');
            nts.uk.ui.errors.clearAll();

            if (!nts.uk.util.isNullOrEmpty(patternCode)) {
                vm.getPatternValByPatternCd(patternCode);
            } else {
                vm.isEditting(false);
            }
        }
    }

    export class DailyPatternDetailModel {
        patternCode: KnockoutObservable<string>;
        patternName: KnockoutObservable<string>;
        dailyPatternVals: KnockoutObservableArray<DailyPatternValModel>;

        constructor(patternCode: string,
            patternName: string,
            dailyPatternVals: Array<DailyPatternValModel>) {
            let self = this;
            self.patternCode = ko.observable(patternCode);
            self.patternName = ko.observable(patternName);
            self.dailyPatternVals = ko.observableArray(dailyPatternVals);
        }

        public toDto(): DailyPatternDetailDto {
            let lstVal: Array<DailyPatternValDto> = this.dailyPatternVals()
                .map((item, index) => {
                    if (item.typeCode() || item.timeCode() || item.days()) {
                        return item.toDto();
                    }
                })
                .filter(function (el) {
                    return el != null;
                });
            return new DailyPatternDetailDto(
                nts.uk.text.padLeft(this.patternCode(), "0", 2),
                this.patternName(),
                lstVal
            );
        }

        public resetModel() {
            let self = this;
            self.patternCode("");
            self.patternName("");
            _.forEach(self.dailyPatternVals(), (item, index) => {
                item.resetModel(index);
            });
        }
    }

    export class DailyPatternValModel {
        dispOrder: number;
        typeCode: KnockoutObservable<string>;
        workTypeInfo: KnockoutObservable<string>;
        workingInfo: KnockoutObservable<string>;
        timeCode: KnockoutObservable<string>;
        days: KnockoutObservable<number>;
        isSetting: KnockoutComputed<boolean>;

        isChecked: KnockoutObservable<boolean> = ko.observable(false);

        constructor(dispOrder: number,
            typeCode: string,
            timeCode: string,
            days: number,
            isChecked: boolean = false
        ) {
            this.dispOrder = dispOrder;
            this.typeCode = ko.observable(typeCode);
            this.timeCode = ko.observable(timeCode);
            this.days = ko.observable(days);
            this.workTypeInfo = ko.observable(typeCode);
            this.workingInfo = ko.observable(timeCode);
            this.isSetting = ko.computed(() => {
                if (this.typeCode() || this.timeCode() || this.days()) {
                    return true;
                }
                return false;
            });

            if (isChecked) this.isChecked(isChecked);

            this.isChecked.subscribe((value) => {
                nts.uk.ui._viewModel.content.enableDisableRemove(value, this);
            });
        }

        public resetModel(displayOrder: number) {
            this.dispOrder = displayOrder;
            this.typeCode("");
            this.timeCode("");
            this.days(null);
            this.workTypeInfo("");
            this.workingInfo("");
        }

        public setWorkTypeName(workTypeName: string): void {
            this.workTypeInfo((this.typeCode() || '') + " " + (workTypeName || ''));
        }

        public setWorkTimeName(workTimeName: string) {
            this.workingInfo((this.timeCode() || '') + " " + (workTimeName || ''));
        }

        public toDto(): DailyPatternValDto {
            return new DailyPatternValDto(
                this.dispOrder,
                this.typeCode(),
                this.timeCode(),
                this.days()
            );
        }
    }

    export interface DailyPatternItemDto {
        code: string;
        name: string;
    }

    export class DailyPatternDetailDto {
        isEditting: boolean = true;
        code: string;
        name: string;
        infos: Array<DailyPatternValDto>;
        //send to api
        workCycleCode: string;
        workCycleName: string;
        workInformations: Array<DailyPatternValDto>;

        constructor(code: string, name: string, infos: Array<DailyPatternValDto>) {
            this.code = code;
            this.name = name;
            this.infos = infos;
            //send to server
            this.workCycleCode = code;
            this.workCycleName = name;
            this.workInformations = infos;
        }
    }

    export class DailyPatternValDto {
        dispOrder: number;
        workTypeCode: string;
        workTimeCode: string;
        days: number;
        workingHoursName: string;
        workTypeName: string;
        typeCode: string;
        timeCode: string;

        constructor(dispOrder: number,
            typeCode: string,
            timeCode: string,
            days: number) {
            this.dispOrder = dispOrder;
            this.typeCode = typeCode;
            this.timeCode = timeCode;
            this.days = days;
            this.workTypeCode = typeCode;
            this.workTimeCode = timeCode;
        }

        updateDto(workTime: WorkTimeDto, workType: WorkTypeDto): void {
            this.typeCode = workType.workTypeCode;
            this.workTypeName = workType.name;
            this.timeCode = workTime.code;
            this.workingHoursName = workTime.name;
        }
    }

    export interface WorkTypeDto {
        workTypeCode: string;
        name: string;
    }

    export interface WorkTimeDto {
        code: string;
        name: string;
    }

    export class DailyPattern {
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        constructor(data?: DailyPatternItemDto) {
            this.code(data && data.code || null);
            this.name(data && data.name || null);
        }
    }
}
