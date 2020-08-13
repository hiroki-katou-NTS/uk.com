/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm003.a {

    @bean()
    class ViewModel extends ko.ViewModel {
        // Init
        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSM003_26"),key: "code",formatter: _.escape,idth: 100 },
            { headerText: nts.uk.resource.getText("KSM003_27"),key: "name",formatter: _.escape,width: 200 },
        ]);
        items: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable("");
        selectedName: KnockoutObservable<string> = ko.observable("");
        isEditting: KnockoutObservable<boolean> = ko.observable(false);
        enableRemoveItem: KnockoutObservable<boolean> = ko.observable(false);
        lessThan99Items: KnockoutObservable<boolean> = ko.observable(true);
        itemLst: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        gridItems: KnockoutObservableArray<WorkingCycleDtl> = ko.observableArray([]);
        mainModel: KnockoutObservable<DailyPatternDetailModel>;
        currentCode: KnockoutObservable<any> = ko.observable("");
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        dailyPatternValModel: KnockoutObservableArray<DailyPatternValModel> = ko.observableArray([]);
        selectedCheckAll: KnockoutObservable<boolean> = ko.observable(false);

        API = {
            getListWorkCycleDto: 'screen/at/ksm003/a/get',
            getListWorkCycleDtoByCode: 'screen/at/ksm003/a/getByCode/',
            findWordType: "at/share/worktype/findNotDeprecatedByListCode",
            findWorkTimeByCode: "at/shared/worktimesetting/findByCodes",
            patternDailyUpdate: "ctx/at/schedule/pattern/work/cycle/update",
            patternDailyDelete: "ctx/at/schedule/pattern/work/cycle/delete",
            patternDailyRegister: "ctx/at/schedule/pattern/work/cycle/register",
            getDailyByPattenCode: "ctx/at/schedule/shift/pattern/daily/find/",
            //findNotDeprecatedByListCode: "at/share/worktype/findNotDeprecatedByListCode",
        };

        maxWorkingTimeItems: number = 15;

        constructor(params: any) {
            super();
            $("#fixedTable").ntsFixedTable({ height: 365, width: 450});
        }

        created(params: any) {
            let vm = this;
            vm.getListWorkingCycle();
        }

        mounted() {
            let vm = this;

            vm.selectedCode.subscribe(function (codeChanged: string) {
                if (codeChanged) {
                    vm.getPatternValByPatternCd(codeChanged);
                } else {
                    vm.isEditting(false);
                    vm.clearError();
                }
                vm.disableAddNewLine();
            });

            //enable remove button
            vm.currentCodeList.subscribe(function(codeSelected: string){
                vm.enableRemoveItem(codeSelected.length > 0);
            });

            vm.selectedCheckAll.subscribe(function(value: boolean){
                let dailyPatternItems : Array<DailyPatternValModel> = [];
                let checkedAll: boolean = value;
                let hasOneItemChecked : boolean = false;
                let totalItemCheked: number = 0;

                vm.enableRemoveItem(false);
                vm.dailyPatternValModel() && vm.dailyPatternValModel().map( ( item, i) => {
                    item.isChecked(checkedAll ? checkedAll : item.isChecked());
                    dailyPatternItems.push(item);
                    if( item.isChecked()) totalItemCheked++;
                    if( !hasOneItemChecked ) hasOneItemChecked = item.isChecked();
                });

                if( totalItemCheked == vm.dailyPatternValModel().length && !checkedAll) {
                    vm.enableRemoveItem(false);
                    dailyPatternItems = [];
                    vm.dailyPatternValModel().map( ( item, i) => {
                        item.isChecked(false);
                        dailyPatternItems.push(item);
                    });
                } else
                    vm.enableRemoveItem(hasOneItemChecked);

                vm.dailyPatternValModel([]);
                vm.dailyPatternValModel(dailyPatternItems);
            });

            var dailyPatternVals: Array<DailyPatternValModel> = [];
            dailyPatternVals.push(new DailyPatternValModel(null, "", "", null));
            vm.mainModel = ko.observable(
                new DailyPatternDetailModel("", "", dailyPatternVals)
            );
        }

        getListWorkingCycle() {
            let vm = this;
            vm.$ajax(vm.API.getListWorkCycleDto).done(
                (dataRes: Array<DailyPatternItemDto>) => {
                    if (dataRes === undefined || dataRes.length == 0) {
                        vm.itemLst([]);
                        vm.switchNewMode();
                    } else {
                        vm.itemLst([]);
                        vm.itemLst(dataRes);
                        vm.selectedCode(vm.itemLst()[0].code);
                        vm.selectedName(vm.itemLst()[0].name);
                        $("#inpPattern").focus();
                    }
                }
            );
        }

        public switchNewMode(): void {
            let self = this;
            self.isEditting(false);
            self.selectedCode("");
            self.selectedName("");
            self.mainModel().patternName("");
            self.mainModel().resetModel();
            self.clearError();
            self.addNewLineItem(true);
            $("#inpCode").focus();
        }

        // clear Error
        private clearError(): void {
            if ($(".nts-editor").ntsError("hasError")) {
                $(".nts-editor").ntsError("clear");
            }
            if ($(".buttonEvent").ntsError("hasError")) {
                $(".buttonEvent").ntsError("clear");
            }
        }

        changeDaysValue(input: any, id: string) {
            let vm = this;
            //Get data from grid
            let grid = $("#workingCycleDtl");
            let data = grid.igGrid("option", "dataSource");
            let itemChanged = _.findIndex(data, function (obj) {
                return obj.id == id;
            });
            if (itemChanged != -1) {
                data[itemChanged].days = input.value;
                grid.igGrid("option", "dataSource", data);
            }
            return;
        }

        enableDisableRemove(flag: boolean, obj: any) {
            let vm = this;
            let dailyPatternValModel = vm.dailyPatternValModel();
            let totalChecked: number = 0;
            dailyPatternValModel && dailyPatternValModel.map((item, i) => {
                if( item.dispOrder == obj.dispOrder) {
                    dailyPatternValModel[i].isChecked(flag);
                }
                if( item.isChecked()) totalChecked++;
            });

            vm.enableRemoveItem(totalChecked > 0);
            vm.selectedCheckAll(totalChecked == dailyPatternValModel.length);
        }

        addNewItem() {
            this.addNewLineItem(false);
        }

        getWorkingCycleValue(patternCode: string) {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];
            vm.$errors("clear");
            vm.$ajax(
                vm.API.getDailyByPattenCode + patternCode
            ).done((dataRes: any) => {
                if (dataRes !== undefined) {
                    vm.isEditting(true);
                    let dailyPatternVals = dataRes.dailyPatternVals.map(function (item) {
                        return new DailyPatternValModel(
                            item.dispOrder,
                            item.typeCode,
                            item.timeCode,
                            item.days
                        );
                    });
                    let lstWorkTypeCode: Array<string> = [];
                    _.forEach(dailyPatternVals, (item, index) => {
                        if (item.typeCode() && item.typeCode() != "") {
                            lstWorkTypeCode.push(item.typeCode());
                        }
                    });
                    //find work type
                    vm.$ajax(
                        vm.API.findWordType,
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
                            vm.API.findWorkTimeByCode,
                            lstWorkTimeCode
                        ).done((data: Array<WorkTimeDto>) => {
                            lstWorkTime = data;
                            vm.updateDataModel(dataRes, lstWorkType, lstWorkTime);
                        });
                    });
                    $("#inpPattern").focus();
                }
            });
        }

        private columnSetting(): Array<any> {
            let vm = this;
            return [
                {
                    headerText: "id",
                    key: "id",
                    hidden: true,
                    formatter: _.escape,
                    width: 100,
                },
                {
                    headerText: "",
                    key: "patternCode",
                    formatter: _.escape,
                    width: 60,
                    template: '<button data-bind="attr: {id: \'btnVal\' + dispOrder}, click: openDialogWorkDays">' + vm.$i18n("KSM003_34") + '</button>',
                },
                {
                    headerText: nts.uk.resource.getText("KSM003_30"),
                    key: "typeOfWork",
                    formatter: _.escape,
                    width: 100,
                },
                {
                    headerText: nts.uk.resource.getText("KSM003_31"),
                    key: "workingHours",
                    formatter: _.escape,
                    width: 100,
                },
                {
                    headerText: nts.uk.resource.getText("KSM003_31"),
                    key: "days",
                    formatter: _.escape,
                    width: 100,
                    hidden: true,
                },
                {
                    headerText: nts.uk.resource.getText("KSM003_31"),
                    key: "daysText",
                    width: 100,
                    template:
                    '<span style="width: 20px"><input style="width: 20px;" ' +
                    'data-bind="ntsNumberEditor: { constraint: \'Days\' , value: \'${days}\' }" /></span>' +
                    '<span style="width: 20px; padding-left: 5px">' + vm.$i18n("KSM003_34") + "</span>"
                },
            ];
        }

        // get Pattern Val By PatternCd form database
        public getPatternValByPatternCd(patternCode: string): void {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];
            vm.$ajax(vm.API.getListWorkCycleDtoByCode + patternCode).done(function (dataRes) {
                if (dataRes !== undefined) {
                    vm.isEditting(true);
                    vm.selectedCode(dataRes.code);
                    vm.selectedName(dataRes.name);
                    vm.clearError();
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
                        vm.API.findWordType,
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
                            vm.API.findWorkTimeByCode,
                            lstWorkTimeCode
                        ).done((data: Array<WorkTimeDto>) => {
                            lstWorkTime = data;
                            vm.updateDataModel(dataRes, lstWorkType, lstWorkTime);
                        });
                    });
                }
            });
        }

        private updateDataModel(dataRes: DailyPatternDetailDto,
                                lstWorkType: Array<WorkTypeDto>,
                                lstWorkTime: Array<WorkTimeDto>) {
            let vm = this;
            //sort list by order
            let lstVal: Array<DailyPatternValDto> = dataRes.infos;
            lstVal = _.sortBy(lstVal, (item) => item.dispOrder);
            dataRes.infos = lstVal;
            //bind item code name

            vm.mainModel().patternCode(dataRes.code);
            vm.mainModel().patternName(dataRes.name);

            let dailyPatternVals: Array<DailyPatternValModel> = [];
            let workingCycleDtl: Array<WorkingCycleDtl> = [];

            lstVal && lstVal.map((item, i) => {
                let dailyPatternValModel = new DailyPatternValModel(
                    item.dispOrder,
                    item.typeCode,
                    item.timeCode,
                    item.days
                );
                let workTypeName = _.find(lstWorkType, (element) => {
                        return element.workTypeCode == item.typeCode
                    }
                );
                workTypeName = workTypeName && workTypeName.name || '';
                dailyPatternValModel.setWorkTypeName(workTypeName);

                let workTimeName = _.find(lstWorkTime, (element) => {
                        return element.code == item.timeCode
                    }
                );
                workTimeName = workTimeName && workTimeName.name || '';
                dailyPatternValModel.setWorkTimeName(workTimeName);

                dailyPatternVals.push(dailyPatternValModel);

                workingCycleDtl.push(new WorkingCycleDtl(
                    item.dispOrder.toString(),
                    dailyPatternValModel.workTypeInfo(),
                    dailyPatternValModel.workingInfo(),
                    item.days));
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
            let id: number;
            let dailyPatternVals = vm.mainModel().dailyPatternVals();
            let totalItems: number = dailyPatternVals.length > 0 ? dailyPatternVals.length + 1 : 1;
            if (totalItems > vm.maxWorkingTimeItems) {
                this.$dialog.info({messageId: "Msg_1688"});
                return;
            }
            id = totalItems + 1; //< 99
            let dailyPatternValModel = new DailyPatternValModel(id, null, null, null);
            //add to model
            if (isAddNew) {
                let empty: Array<DailyPatternValModel> = [];
                vm.mainModel().dailyPatternVals(empty);
                vm.enableRemoveItem(false);
                vm.dailyPatternValModel([]);
                dailyPatternVals = [];
            }

            vm.selectedCheckAll(false);
            dailyPatternVals.push(dailyPatternValModel);
            vm.dailyPatternValModel(dailyPatternVals);
            vm.mainModel().dailyPatternVals(dailyPatternVals);
            vm.disableAddNewLine();
        }

        /**
         * Remove a/multiple item on grid
         * */
        removeLine() {
            let vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes' | 'cancel') => {
                vm.$blockui("show"); //lock screen
                if (result === 'no' || result === 'cancel') vm.$blockui("hide");
                if (result === 'yes') {
                    let currentCodeList = vm.currentCodeList();
                    let currentDataList = vm.mainModel().dailyPatternVals();
                    let newDataList = [];
                    let dailyPatternValModel: Array<DailyPatternValModel> = [];

                    currentCodeList && currentDataList && currentDataList.map((item, i) => {
                        if (!item.isChecked()) {
                            dailyPatternValModel.push(
                                new DailyPatternValModel(
                                    item.dispOrder, item.workTypeInfo(),
                                    item.workingInfo(), item.days()
                                ));
                        }
                    });
                    //update model
                    vm.mainModel().dailyPatternVals([]);
                    vm.mainModel().dailyPatternVals(dailyPatternValModel);
                    //update data resource
                    vm.dailyPatternValModel([]);
                    vm.dailyPatternValModel(dailyPatternValModel);
                    vm.disableAddNewLine();
                    vm.$blockui("hide");
                }
            });
        }

        disableAddNewLine() {
            let vm = this;
            vm.lessThan99Items(vm.gridItems().length <= vm.maxWorkingTimeItems);
        }

        //click button open Dialog Working
        openDialogWorking() {
            let self = this;
            nts.uk.ui.windows.setShared("patternCode", self.selectedCode());
            nts.uk.ui.windows.sub.modal("/view/kdl/023/a/index.xhtml", {
                title: nts.uk.resource.getText("KDL023_1"),
            });
        }

        // delete Pattern
        deletePattern() {
            let vm = this;
            nts.uk.ui.dialog
                .confirm({messageId: "Msg_18"})
                .ifYes(function () {
                    let dataHistory: DailyPatternItemDto[] = vm.itemLst();

                    vm.$blockui('show');

                    /*if( nts.uk.util.isNullOrEmpty( self.selectedCode() )) {
                        return;
                    }*/
                    vm.$ajax(
                        vm.API.patternDailyDelete,
                        { workCycleCode: vm.selectedCode() }
                    ).done((response) => {
                        //console.log(response);
                        vm.$dialog.info({messageId: "Msg_16"}).then(function () {
                            vm.reloadAfterDelete( dataHistory );
                        });
                    }).fail(function (res) {
                        vm.$dialog.error(res.message).then(() => {
                            vm.$blockui('hide');
                        });
                    }).always(function () {
                        vm.$blockui('hide');
                    });

                    /*vm.deleteDailyPattern(vm.selectedCode()).done(function (response) {
                        console.log(vm.selectedCode());
                        nts.uk.ui.dialog.info({messageId: "Msg_16"}).then(function () {
                            // check end visible
                            var indexSelected: number = 0;
                            if (self.itemLst()) {
                                for (var index: number = 0; index < dataHistory.length; index++) {
                                    if (dataHistory[index].patternCode == self.selectedCode()) {
                                        indexSelected = index;
                                        break;
                                    }
                                }
                            }
                            // check list control is 0
                            if (nts.uk.util.isNullOrEmpty(self.itemLst())) {
                                self.itemLst([]);
                                self.switchNewMode()
                            }
                            // check next visible
                            else if (dataHistory[dataHistory.length - 1].patternCode == self.selectedCode()) {
                                if (self.itemLst()[self.itemLst().length - 2]) {
                                    self.selectedCode(self.itemLst()[self.itemLst().length - 2].patternCode);
                                }
                            }
                            // check previous visible
                            else if (dataHistory[dataHistory.length - 1].patternCode != self.selectedCode()) {
                                self.selectedCode(self.itemLst()[indexSelected + 1].patternCode);
                            }
                        });
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => {
                            vm.$blockui('hide');
                        });
                    }).always(function () {
                        vm.$blockui('hide');
                    });*/
                })
                .ifNo(function () {
                    vm.$blockui('hide');
                    return;
                });
        }

        /**
         * delete divergence reason
         */
        deleteDailyPattern(patternCd: string): JQueryPromise<any> {
            console.log(patternCd);
            return nts.uk.request.ajax("at", this.API.patternDailyDelete, patternCd);
        }

        /*
        * Re-Selected
        * */
        reloadAfterDelete( dataHistory ) {
            let vm = this;
            //reload
            vm.getListWorkingCycle();
                //re-selected
                if( nts.uk.util.isNullOrEmpty(vm.itemLst())) {
                vm.switchNewMode();
            }

            //get first, last item and set to default
            let newSelectedItem: DailyPatternItemDto = null;
            dataHistory && dataHistory.some( ( item, i ) => {
                if( item.code == vm.selectedCode()) {
                    if( dataHistory.length == 1 ) {
                        vm.itemLst([]);
                        vm.switchNewMode()
                        return;
                    } else if(  i == 0 ) {
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

            this.exportExcel()
                .done(function (data) {
                }).fail(function (res: any) {
                nts.uk.ui.dialog.alertError(res).then(function () {
                    nts.uk.ui.block.clear();
                });
            }).always(() => {
                nts.uk.ui.block.clear;
            });
        }

        exportExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
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

            //登録の時には勤務内容一覧に一行もない //register & update
            if (workingTimeCycleList.length <= 0) {
                $('.working-time').ntsError('set', {
                    messageId: "Msg_1690",
                    messageParams: [nts.uk.resource.getText("KSM003_23")]
                });
                return;
            }

            vm.$blockui("show");

            /*if (vm.validate()) {
                vm.$blockui("hide");
                return;
            }*/

            let messageIds: Array<string> = ["Msg_23", "Msg_24", , "Msg_25", "Msg_389", "Msg_390",
                "Msg_416", "Msg_417", "Msg_434", "Msg_435", "Msg_3", "Msg_1608", "Msg_1609"];

            //addnew
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
                detailDto.workCycleName = vm.selectedName();
            }

            this.saveDailyPattern(detailDto).done(function (res) {
                nts.uk.ui.dialog.info({messageId: "Msg_15"});

                let patternCode = vm.mainModel().patternCode();
                vm.selectedCode(vm.mainModel().patternCode());
                $("#inpPattern").focus();
                if (!vm.isEditting()) vm.isEditting(true);
                //vm.selectedCode(nts.uk.text.padLeft(vm.mainModel().patternCode(), '0', 2));

                vm.getPatternValByPatternCd(vm.mainModel().patternCode());

            }).fail(function (res) {
                let isSetError = messageIds.some(item => item == res.messageId);
                if (isSetError) {
                    $('#inpCode').ntsError('set', {messageId: res.messageId});
                } else {
                    nts.uk.ui.dialog.alertError(res.message);
                }
            }).always(function () {
                vm.$blockui("hide");
            });
        }

        //validate form
        private validate(): boolean {
            let self = this;
            $('#inpCode').ntsEditor('validate');
            $('#inpPattern').ntsEditor('validate');

            if (nts.uk.util.isNullOrEmpty(self.mainModel().dailyPatternVals())) {
                $('#days1').ntsError('set', {messageId: "Msg_31"});
            }

            self.mainModel().dailyPatternVals().filter(i => i.isSetting()).forEach((item) => {
                $('#days' + item.dispOrder).ntsEditor('validate');

                if (!nts.uk.text.isNullOrEmpty(item.days()) && nts.uk.text.isNullOrEmpty(item.workTypeSetCd())) {
                    $('#btnVal' + item.dispOrder).ntsError('set', {messageId: "Msg_22"});
                }

                if (nts.uk.text.isNullOrEmpty(item.days()) && !nts.uk.text.isNullOrEmpty(item.workTypeSetCd())) {
                    $('#days' + item.dispOrder).ntsError('set', {messageId: "Msg_25"});
                }
            });

            return $('.nts-input').ntsError('hasError') || $('.buttonEvent').ntsError('hasError');
        }

        saveDailyPattern(dto: DailyPatternDetailDto): JQueryPromise<Array<DailyPatternDetailModel>> {
            //dto.patternCode = nts.uk.text.padLeft(dto.patternCode, '0', 2);
            //dto.code = nts.uk.text.padLeft(dto.code, '0', 2);
            let apiUrl = (!this.isEditting()) ? this.API.patternDailyRegister : this.API.patternDailyUpdate;
            return nts.uk.request.ajax("at", apiUrl, dto);
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

        isChecked: KnockoutObservable<boolean>;

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
            this.isChecked = ko.observable(isChecked ? isChecked : this.isChecked);

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
            this.workTypeInfo(this.typeCode() + " " + workTypeName);
        }

        public setWorkTimeName(workTimeName: string) {
            this.workingInfo(this.timeCode() + " " + workTimeName);
        }

        public toDto(): DailyPatternValDto {
            return new DailyPatternValDto(
                this.dispOrder,
                this.typeCode(),
                this.timeCode(),
                this.days()
            );
        }

        /**
         * open dialog KDL003 by Work Days
         */
        public  openDialogWorkDays() {
            var self = this;
            nts.uk.ui.windows.setShared("parentCodes", {
                selectWorkTypeCode: self.typeCode,
                selectSiftCode: self.timeCode,
            });
            nts.uk.ui.windows.sub
                .modal("/view/kdl/003/a/index.xhtml", {
                    title: nts.uk.resource.getText("KDL003_1"),
                })
                .onClosed(function () {
                    var childData = nts.uk.ui.windows.getShared("childData");
                    if (childData) {
                        self.typeCode(childData.selectedWorkTypeCode);
                        self.timeCode(childData.selectedWorkTimeCode);
                        self.setWorkTypeName(childData.selectedWorkTypeName);
                        self.setWorkTimeName(childData.selectedWorkTimeName);
                        if ($(".nts-neditor").ntsError("hasError")) (".nts-neditor").ntsError("clear");
                        if ($(".buttonEvent").ntsError("hasError")) $(".buttonEvent").ntsError("clear");
                        console.log(self);
                    }
                });
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

    // cos ver van de nam o day
    // Trong knockoutjs,
    // vaf trong javascript, doi tuong la bat cu kieu gi, vis duj khoi tao tu class, khoi tao nhu moojt anonymus object.

    // Viết lại định nghĩa đối tượng sẽ bind vào danh sách và bind vào trong gridlist/

    // Interface là giao diện nhìn của một đối tượng nào đó
    // tức là khi ta chỉ định một đối tượng nào đó có kiểu như thế nào, ta sẽ chỉ định interface cho nó.
    // vì igGrid là control của jquery, nó không phải là một observable, nên ta chỉ cần các đối tượng js đơn giản (chỉ chứa các primitive value)
    // là được, (dùng interface để định nghĩa cấu trúc cho data thôi), không cần tạo class.
    interface WorkingCycleData {
        id: string;
        typeOfWork: string;
        workHours: string;
        days: number;
        link: string;
        flag: boolean;
    }

    export class WorkingCycleDtl {
        id: string;
        typeOfWork: string;
        workingHours: string;
        days: number;
        dayGridText: string;
        selectedItem: boolean;

        constructor(id: string,
                    typeOfWork: string,
                    workingHours: string,
                    days: number) {
            this.id = id;
            this.typeOfWork = typeOfWork;
            this.workingHours = workingHours;
            this.days = days;
            this.dayGridText = '<span>' + nts.uk.resource.getText("KSM003_33") + '</span>';
            this.selectedItem = false;
        }
    }

    export interface WorkingCycleDto {
        name: string;
        code: string;
    }
}

function changeDaysValue(params, code) {
    nts.uk.ui._viewModel.content.changeDaysValue(params, code);
}
