/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm003.a {
    import _viewModel = nts.uk.ui._viewModel;

    @bean()
    class ViewModel extends ko.ViewModel {
        // Init
        columns: KnockoutObservableArray<any> = ko.observableArray([
            {
                headerText: nts.uk.resource.getText("KSM003_26"),
                key: "code",
                formatter: _.escape,
                width: 100,
            },
            {
                headerText: nts.uk.resource.getText("KSM003_27"),
                key: "name",
                formatter: _.escape,
                width: 200,
            },
        ]);

        gListColumns: Array<any>;
        currentCodeList: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray(
            []
        );

        selectedCode: KnockoutObservable<string> = ko.observable("");
        selectedName: KnockoutObservable<string> = ko.observable("");
        isEditting: KnockoutObservable<boolean> = ko.observable(false);
        hasWorkingCycleItems: KnockoutObservable<boolean> = ko.observable(false);
        lessThan99Items: KnockoutObservable<boolean> = ko.observable(true);
        itemLst: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        gridItems: KnockoutObservableArray<WorkingCycleDtl> = ko.observableArray([]);
        workingCycleItems: KnockoutObservableArray<WorkingCycleDtl> = ko.observableArray([]);
        mainModel: KnockoutObservable<DailyPatternDetailModel>;
        currentCode: KnockoutObservable<any> = ko.observable("");
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        count: number = 100;

        API = {
            getListWorkCycleDto: 'screen/at/ksm003/a/get',
            getListWorkCycleDtoByCode: 'screen/at/ksm003/a/getByCode/',
            findWordType: "at/share/worktype/findNotDeprecatedByListCode",
            findWorkTimeByCode :  "at/shared/worktimesetting/findByCodes",
        };
        constructor(params: any) {
            super();
        }

        created(params: any) {
            let vm = this;
            vm.gListColumns = vm.columnSetting();
            vm.getListWorkingCycle();
            vm.displayWorkingCycle();
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
            vm.currentCodeList.subscribe(function (codeSelected: string) {
                vm.hasWorkingCycleItems(codeSelected.length > 0);
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
                        //load Working Cycle listing
                        let partenCode = vm.itemLst()[0].code;
                        vm.getPatternValByPatternCd(partenCode);
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
            $("#inpCode").focus();
            self.clearError();
            //clear grid and add new line into WorkingCycleDtl
            self.addNewLineItem(true);
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

        addLine() {
            this.addNewLineItem(false);
        }

        getWorkingCycleValue(patternCode: string) {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];
            vm.$errors("clear");
            vm.$ajax(
                "ctx/at/schedule/shift/pattern/daily/find" + "/" + patternCode
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
                        "at/share/worktype/findNotDeprecatedByListCode",
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
                            "at/shared/worktimesetting/findByCodes",
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
            // console.log(dataRes);
            //console.log(lstWorkType);
            //console.log(lstWorkTime);
            let dailyPatternVals: Array<DailyPatternValModel> = [];
            let workingCycleDtl: Array<WorkingCycleDtl> = [];

            lstVal && lstVal.map( (item, i) => {
                let dailyPatternValModel = new DailyPatternValModel(
                    item.dispOrder,
                    item.typeCode,
                    item.timeCode,
                    item.days
                );
                let workTypeName = _.find(lstWorkType, (element) => {
                    return element.workTypeCode == item.typeCode }
                    );
                workTypeName = workTypeName && workTypeName.name || '';
                dailyPatternValModel.setWorkTypeName(workTypeName);

                let workTimeName = _.find(lstWorkTime, (element) => {
                    return element.code == item.timeCode }
                    );
                workTimeName = workTimeName && workTimeName.name || '';
                dailyPatternValModel.setWorkTimeName(workTimeName);

                dailyPatternVals.push( dailyPatternValModel );

                workingCycleDtl.push( new WorkingCycleDtl(
                    item.dispOrder.toString(),
                    dailyPatternValModel.workTypeInfo(),
                    dailyPatternValModel.workingInfo(),
                    1));
            });

            vm.gridItems(workingCycleDtl);
        }

        /**
         * Add new line
         * @param isAddNew : boolean
         * */
        addNewLineItem(isAddNew: boolean) {
            let vm = this;
            let id: number;
            id = Date.now();
            let totalItems: number =
                vm.gridItems().length > 0 ? vm.gridItems().length + 1 : 1;
            let workingCycleDtl = new WorkingCycleDtl(
                id.toString(),
                totalItems.toString(),
                null,
                null
            );
            if (!isAddNew) {
                vm.gridItems.push(workingCycleDtl);
            } else {
                vm.gridItems([workingCycleDtl]);
                vm.hasWorkingCycleItems(false);
            }
            vm.disableAddNewLine();
        }

        /**
         * Remove a/multiple item on grid
         * */
        removeLine() {
            let vm = this;
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
                vm.$blockui("show"); //lock screen
                if (result === 'no'  || result === 'cancel') vm.$blockui("hide");
                if (result === 'yes') {
                    let currentCodeList = vm.currentCodeList();
                    let currentDataList = vm.gridItems();
                    let newDataList = [];
                    currentCodeList &&
                    currentDataList &&
                    currentDataList.map((item, i) => {
                        const found = currentCodeList.some(
                            (element) => element == item.id
                        );
                        if (!found) newDataList.push(item);
                    });

                    vm.gridItems(newDataList);
                    vm.disableAddNewLine();
                    vm.$blockui("hide");
                }
            });
        }

        disableAddNewLine() {
            let vm = this;
            vm.lessThan99Items(vm.gridItems().length < 99);
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
            let self = this;
            nts.uk.ui.dialog
                .confirm({messageId: "Msg_18"})
                .ifYes(function () {
                    var dataHistory: DailyPatternItemDto[] = self.itemLst();

                    nts.uk.ui.block.grayout();

                    /*service.deleteDailyPattern(self.selectedCode()).done(function() {
                              nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                                  self.loadAllDailyPatternItems();

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
                          }).fail(function(res) {
                              nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                          }).always(function() {
                              nts.uk.ui.block.clear();
                          });
                          */
                })
                .ifNo(function () {
                    nts.uk.ui.block.clear();
                    return;
                });
        }

        /**
         * export excel
         */
        exportExcel() {
            /*nts.uk.at.view.ksm003.a.service.exportExcel().done(function(data) {
                  }).fail(function(res: any) {
                      nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                  }).always(()=>{
                      nts.uk.ui.block.clear;
                  });*/
        }

        /**
         * Save Working
         * */
        saveWorking() {
            alert("saveWorking");
        }

        displayWorkingCycle() {
            let vm = this;
            for (var i = 0; i < 99; i++) {
                let workingCycleDtl = new WorkingCycleDtl(
                    i.toString(),
                    'Type ' + i.toString(),
                    'hours ' + i.toString(),
                     i
                );
                vm.workingCycleItems.push(workingCycleDtl);
            }

            $("#grid2").ntsGrid({
                    height: '380px',
                    dataSource: vm.workingCycleItems()  || [],
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'Id', key: 'id', dataType: 'number', width: '0', hidden: true },
                        { headerText: '', key: 'selectedItem', dataType: 'boolean', width: '35px', ntsControl: 'Checkbox', showHeaderCheckbox: true },
                        { headerText: '',  key: "patternCode", dataType: 'string', width: '60px', unbound: true, ntsControl: 'Button' },
                        { headerText:  nts.uk.resource.getText("KSM003_30"), key: 'typeOfWork', dataType: 'string', width: '130px', },
                        { headerText: nts.uk.resource.getText("KSM003_31"), key: 'workingHours', dataType: 'string', width: '130px', },
                        { headerText: nts.uk.resource.getText("KSM003_31"), key: 'days',  width: '80px',
                            dataType: 'number', ntsControl: 'TextEditor', columnCssClass: 'daysWorking' },
                        { headerText: '', key: 'dayGridText', dataType: 'string', columnCssClass: 'daysWorking1', width: '0' },
                    ],
                    features: [
                        { type: 'local' }
                        ],
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1, text: '' },
                            optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        { name: 'Button', text: vm.$i18n("KSM003_34"), click: function() { alert("Button!!"); }, controlType: 'Button' },
                        { name: 'TextEditor', controlType: 'TextEditor',
                            constraint: { valueType: 'Integer', required: true, format: "Number_Separated"},
                            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                textmode: "text",
                                placeholder: "Placeholder for text editor",
                                width: "100px",
                                textalign: "left"
                            }))
                        },
                    ]
                });
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

        constructor(dispOrder: number,
                    typeCode: string,
                    timeCode: string,
                    days: number) {
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
        openDialogWorkDays(){
            alert(111);
            // var self = this;
            // nts.uk.ui.windows.setShared("parentCodes", {
            //     selectWorkTypeCode: self.typeCode,
            //     selectSiftCode: self.timeCode,
            // });
            // nts.uk.ui.windows.sub
            //     .modal("/view/kdl/003/a/index.xhtml", {
            //         title: nts.uk.resource.getText("KDL003_1"),
            //     })
            //     .onClosed(function () {
            //         var childData = nts.uk.ui.windows.getShared("childData");
            //         if (childData) {
            //             self.typeCode(childData.selectedWorkTypeCode);
            //             self.timeCode(childData.selectedWorkTimeCode);
            //             self.setWorkTypeName(childData.selectedWorkTypeName);
            //             self.setWorkTimeName(childData.selectedWorkTimeName);
            //             if ($(".nts-editor").ntsError("hasError")) {
            //                 $(".nts-editor").ntsError("clear");
            //             }
            //             if ($(".buttonEvent").ntsError("hasError")) {
            //                 $(".buttonEvent").ntsError("clear");
            //             }
            //         }
            //     });
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

        constructor(code: string, name: string, infos: Array<DailyPatternValDto>) {
            this.code = code;
            this.name = name;
            this.infos = infos;
        }
    }

    export class DailyPatternValDto {
        dispOrder: number;
        typeCode: string;
        workTypeName: string;
        timeCode: string;
        workingHoursName: string;
        days: number;

        constructor(dispOrder: number,
                    typeCode: string,
                    timeCode: string,
                    days: number) {
            this.dispOrder = dispOrder;
            this.typeCode = typeCode;
            this.timeCode = timeCode;
            this.days = days;
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
            this.dayGridText = '<span>' + nts.uk.resource.getText("KSM003_34") + '</span>';
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
