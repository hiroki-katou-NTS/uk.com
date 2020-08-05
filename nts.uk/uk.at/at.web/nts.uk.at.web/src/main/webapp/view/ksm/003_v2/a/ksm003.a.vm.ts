/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm003.a {

    import _viewModel = nts.uk.ui._viewModel;

    @bean()
    class ViewModel extends ko.ViewModel {

        // Init
        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText('KSM003_26'), key: 'code', formatter: _.escape, width: 100 },
            { headerText: nts.uk.resource.getText('KSM003_27'), key: 'name', formatter: _.escape, width: 200 }
        ]);

        gListColumns: Array<any>;
        currentCodeList: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);

        selectedCode: KnockoutObservable<string> = ko.observable('');
        selectedName: KnockoutObservable<string> = ko.observable('');
        isEditting: KnockoutObservable<boolean> = ko.observable(false);

        itemLst: KnockoutObservableArray<DailyPatternItemDto> = ko.observableArray([]);
        mainModel: KnockoutObservable<DailyPatternDetailModel>;

        gridItems: KnockoutObservableArray<WorkingCycleDtl> = ko.observableArray([
            new WorkingCycleDtl('1','1','1',1),
            new WorkingCycleDtl('2','2','2',2),
            new WorkingCycleDtl('3','3','3',3),
        ]);

        currentCode: KnockoutObservable<any> = ko.observable('');
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        count: number = 100;

        constructor(params: any) {
            super();
        }

        created(params: any) {
            let vm = this;
            vm.gListColumns = vm.columnSetting();
            vm.getListWorkingCycle()
        }

        mounted() {
            let vm = this;

            vm.selectedCode.subscribe(function(codeChanged: string) {
                if (codeChanged) {
                    vm.getPatternValByPatternCd(codeChanged);
                } else {
                    vm.isEditting(false);
                    vm.clearError();
                }
            });

            var dailyPatternVals: Array<DailyPatternValModel> = [];
            dailyPatternVals.push(new DailyPatternValModel(null, "", "", null));
            vm.mainModel = ko.observable(new DailyPatternDetailModel("", "", dailyPatternVals));
        }

        getListWorkingCycle() {
            let vm = this;
            vm.$ajax('screen/at/ksm003/a/get').done((dataRes: Array<DailyPatternItemDto>) => {
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
            });
        }

        public switchNewMode(): void {
            let self = this;
            self.isEditting(false);
            self.selectedCode("");
            self.selectedName("");
            self.mainModel().patternName("");
            self.mainModel().resetModel();
            // $("#inpCode").focus();
            self.clearError();
        }

        // clear Error
        private clearError(): void {
            if ($('.nts-editor').ntsError("hasError")) {
                $('.nts-editor').ntsError('clear');
            }
            if ($('.buttonEvent').ntsError("hasError")) {
                $('.buttonEvent').ntsError('clear');
            }
        }

        changeDaysValue(input: any, id: string) {
            let vm = this;
            //Get data from grid
            let grid = $("#workingCycleDtl");
            let data = grid.igGrid("option", "dataSource");
            let itemChanged = _.findIndex(data, function(obj){
                    return obj.id == id;
                }
            )
            if (itemChanged != -1) {
                data[itemChanged].days = input.value;
                grid.igGrid("option", "dataSource", data);
            }
            return;
        }

        addLine() {
            let vm = this;
            let grid = $("#workingCycleDtl");
            let data = grid.igGrid("option", "dataSource");
            let lastItem = data[data.length-1];
            if (lastItem && lastItem.id == undefined) {
                return;
            }
            data.push(new WorkingCycleDtl(null, null, null, null));
            grid.igGrid("option", "dataSource", data);
        }

        getWorkingCycleValue(patternCode: string) {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];
            vm.$errors("clear");
            vm.$ajax('ctx/at/schedule/shift/pattern/daily/find' + '/' + patternCode).done((dataRes: any) => {
                if (dataRes !== undefined) {
                    vm.isEditting(true);
                    let dailyPatternVals = dataRes.dailyPatternVals.map(function(item) {
                        return new DailyPatternValModel(item.dispOrder, item.typeCode, item.timeCode, item.days);
                    });
                    let lstWorkTypeCode: Array<string> = [];
                    _.forEach(dailyPatternVals, (item, index) => {
                        if (item.typeCode() && item.typeCode() != '') {
                            lstWorkTypeCode.push(item.typeCode());
                        }
                    });
                    //find work type
                    vm.$ajax('at/share/worktype/findNotDeprecatedByListCode', lstWorkTypeCode).done((data: Array<WorkTypeDto>) => {
                        lstWorkType = data;
                        //init list work time code
                        let lstWorkTimeCode: Array<string> = [];
                        _.forEach(dailyPatternVals, (item, index) => {
                            if (item.timeCode() && item.timeCode() != '') {
                                lstWorkTimeCode.push(item.timeCode());
                            }
                        });
                        vm.$ajax('at/shared/worktimesetting/findByCodes', lstWorkTimeCode).done((data: Array<WorkTimeDto>) => {
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
                { headerText: 'id', key: 'id', hidden: true, formatter: _.escape, width: 100 },
                { headerText: '', key: 'patternCode', formatter: _.escape, width: 60, template: '<button>'+ vm.$i18n('KSM003_34') +'</button>' },
                { headerText: nts.uk.resource.getText('KSM003_30'), key: 'typeOfWork', formatter: _.escape, width: 100 },
                { headerText: nts.uk.resource.getText('KSM003_31'), key: 'workingHours', formatter: _.escape, width: 100 },
                { headerText: nts.uk.resource.getText('KSM003_31'), key: 'days', formatter: _.escape, width: 100, hidden: true },
                { headerText: nts.uk.resource.getText('KSM003_31'), key: 'daysText', width: 100,
                    template:'<span style="width: 20px"><input style="width: 20px;" data-bind="ntsNumberEditor: { value:${days} }" /></span><span style="width: 20px; padding-left: 5px">' + vm.$i18n('KSM003_34') +'</span>' }
            ];
        }

        // get Pattern Val By PatternCd form database
        public getPatternValByPatternCd(patternCode: string): void {
            var vm = this;
            let lstWorkType: Array<WorkTypeDto> = [];
            let lstWorkTime: Array<WorkTimeDto> = [];
            vm.$ajax('screen/at/ksm003/a/getByCode/' + patternCode).done(function(dataRes) {
                if (dataRes !== undefined) {
                    vm.isEditting(true);
                    //get list item
                    let dailyPatternVals = dataRes.infos.map(function(item) {
                        return new DailyPatternValModel(item.dispOrder, item.typeCode, item.timeCode, item.days);
                    })
                    //init list work type code
                    let lstWorkTypeCode: Array<string> = [];
                    _.forEach(dailyPatternVals, (item, index) => {
                        if (item.typeCode() && item.typeCode() != '') {
                            lstWorkTypeCode.push(item.typeCode());
                        }
                    });
                    //find work type
                    vm.$ajax('at/share/worktype/findNotDeprecatedByListCode', lstWorkTypeCode).done((data: Array<WorkTypeDto>) => {
                        lstWorkType = data;
                        //init list work time code
                        let lstWorkTimeCode: Array<string> = [];
                        _.forEach(dailyPatternVals, (item, index) => {
                            if (item.timeCode() && item.timeCode() != '') {
                                lstWorkTimeCode.push(item.timeCode());
                            }
                        });
                        vm.$ajax('at/shared/worktimesetting/findByCodes', lstWorkTimeCode).done((data: Array<WorkTimeDto>) => {
                            lstWorkTime = data;
                            vm.updateDataModel(dataRes, lstWorkType, lstWorkTime);
                        });
                    });

                }
            });
        }

        private updateDataModel(dataRes: DailyPatternDetailDto, lstWorkType: Array<WorkTypeDto>, lstWorkTime: Array<WorkTimeDto>) {
            let vm = this;
            //sort list by order
            let lstVal: Array<DailyPatternValDto> = dataRes.infos;
            lstVal = _.sortBy(lstVal, item => item.dispOrder);
            dataRes.infos = lstVal;
            //bind item code name
            vm.mainModel().patternCode(dataRes.code);
            vm.mainModel().patternName(dataRes.name);
            // for (let k = 0; k < lstVal.length; k++) {
            //     for (let i = 0; i < lstVal.length; i++) {
            //         if (lstVal[k].dispOrder == i) {
            //             vm.mainModel().dailyPatternVals()[i].days(lstVal[k].days);
            //             let workTimeCode = lstVal[k].timeCode;
            //             let workTypeCode = lstVal[k].typeCode;
            //             // add workTypeName to List
            //             if (workTypeCode && workTypeCode != '') {
            //                 vm.mainModel().dailyPatternVals()[i].typeCode(workTypeCode);
            //                 let workTypeName = _.find(lstWorkType, (i) => { return i.workTypeCode == workTypeCode }).name;
            //                 vm.mainModel().dailyPatternVals()[i].setWorkTypeName(workTypeName);
            //             }
            //             // add workHoursName to List
            //             if (workTimeCode && workTimeCode != '') {
            //                 vm.mainModel().dailyPatternVals()[i].timeCode(workTimeCode);
            //                 let workTimeName = _.find(lstWorkTime, (i) => { return i.code == workTimeCode }).name;
            //                 vm.mainModel().dailyPatternVals()[i].setWorkTimeName(workTimeName);
            //             }
            //             break;
            //         }
            //     }
            // }
        }

    }

    export class DailyPatternDetailModel {
        patternCode: KnockoutObservable<string>;
        patternName: KnockoutObservable<string>;
        dailyPatternVals: KnockoutObservableArray<DailyPatternValModel>;
        constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValModel>) {
            let self = this;
            self.patternCode = ko.observable(patternCode);
            self.patternName = ko.observable(patternName);
            self.dailyPatternVals = ko.observableArray(dailyPatternVals);
        }

        public toDto(): DailyPatternDetailDto {
            let lstVal: Array<DailyPatternValDto> = this.dailyPatternVals().map((item, index) => {
                if (item.typeCode() || item.timeCode() || item.days()) {
                    return item.toDto();
                }
            }).filter(function(el) {
                return el != null;
            });
            return new DailyPatternDetailDto(nts.uk.text.padLeft(this.patternCode(), '0', 2), this.patternName(), lstVal);
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

        constructor(dispOrder: number, typeCode: string, timeCode: string, days: number) {
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
            this.workTypeInfo(this.typeCode() + ' ' + workTypeName);
        }

        public setWorkTimeName(workTimeName: string) {
            this.workingInfo(this.timeCode() + ' ' + workTimeName);
        }

        public toDto(): DailyPatternValDto {
            return new DailyPatternValDto(this.dispOrder, this.typeCode(), this.timeCode(), this.days());
        }

        /**
         * open dialog KDL003 by Work Days
         */
        public openDialogWorkDays(): void {
            var self = this;
            nts.uk.ui.windows.setShared('parentCodes', {
                selectWorkTypeCode: self.typeCode,
                selectSiftCode: self.timeCode
            });
            nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml", { title: nts.uk.resource.getText('KDL003_1') }).onClosed(function() {
                var childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    self.typeCode(childData.selectedWorkTypeCode);
                    self.timeCode(childData.selectedWorkTimeCode);
                    self.setWorkTypeName(childData.selectedWorkTypeName);
                    self.setWorkTimeName(childData.selectedWorkTimeName);
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.nts-editor').ntsError('clear');
                    }
                    if ($('.buttonEvent').ntsError("hasError")) {
                        $('.buttonEvent').ntsError('clear');
                    }
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

        constructor(dispOrder: number, typeCode: string, timeCode: string, days: number) {
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
        typeOfWork : string;
        workingHours : string;
        days : number;
        dayGridText: string;

        constructor(id: string, typeOfWork: string, workingHours : string, days : number) {
            this.id = id;
            this.typeOfWork = typeOfWork;
            this.workingHours = workingHours;
            this.days = days;
            this.dayGridText = nts.uk.resource.getText('KSM003_33');
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