module nts.uk.com.view.cmf005.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf005.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    //D Screen
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import UnitAlreadySettingModel = kcp.share.list.UnitAlreadySettingModel;

    export class ScreenModel {


        //Radio button
        rdSelected: KnockoutObservable<any>;
        optionCategory: KnockoutObservable<any>;
        optionDeleteSet: KnockoutObservable<any>;

        //information category
        deleteSetName: KnockoutObservable<string>;


        // B5_2_2
        systemTypeCbb: KnockoutObservable<model.ItemModel>;

        //B5_3
        listDataCategory: KnockoutObservableArray<model.ItemCategory>;
        listColumnHeader: KnockoutObservableArray<NtsGridListColumn>;
        currentCategory: KnockoutObservableArray<any>;

        //datepicker B5_2_2
        enable: KnockoutObservable<boolean>;
        requiredDate: KnockoutObservable<boolean>;
        requiredMonth: KnockoutObservable<boolean>;
        requiredYear: KnockoutObservable<boolean>;
        dateValue: KnockoutObservable<any>;
        monthValue: KnockoutObservable<any>;
        yearValue: KnockoutObservable<any>;
        startDateDailyString: KnockoutObservable<string>;
        endDateDailyString: KnockoutObservable<string>;
        startDateMothlyString: KnockoutObservable<string>;
        endDateMothlyString: KnockoutObservable<string>;
        startDateYearlyString: KnockoutObservable<string>;
        endDateYearlyString: KnockoutObservable<string>;

        //B7_1
        saveBeforDeleteOption: KnockoutObservableArray<model.ItemModel>;
        isSaveBeforeDeleteFlg: number;

        //B8_1
        isExistCompressPasswordFlg: KnockoutObservable<boolean>;
        passwordForCompressFile: KnockoutObservable<string>;
        confirmPasswordForCompressFile: KnockoutObservable<string>;

        //B9_2
        supplementExplanation: KnockoutObservable<string>;




        //D
        //Radio button
        itemTitleAtr: KnockoutObservableArray<any>;
        selectedTitleAtr: KnockoutObservable<number>;

        ccg001ComponentOption: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

        listComponentOption: any;
        selectedEmployeeCode: KnockoutObservableArray<string>;
        employeeName: KnockoutObservable<string>;
        employeeList: KnockoutObservableArray<UnitModel>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;

        constructor() {
            var self = this;
            self.initComponents();
            self.setDefault();

            self.itemTitleAtr = ko.observableArray([
                { value: 0, titleAtrName: resource.getText('CMF005_51') },
                { value: 1, titleAtrName: resource.getText('CMF005_52') }]);
            self.selectedTitleAtr = ko.observable(0);

        }

        initComponents() {
            var self = this;

            //View menu step
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.activeStep = ko.observable(0);
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });

            //Radio button
            self.optionCategory = ko.observable({ value: 1, text: nts.uk.resource.getText("CMF005_15") });
            self.optionDeleteSet = ko.observable({ value: 2, text: nts.uk.resource.getText("CMF005_16") });
            self.deleteSetName = ko.observable('');

            //B5_3
            self.listDataCategory = ko.observableArray([]);
            self.currentCode = ko.observable();
            self.currentCategory = ko.observableArray([]);
            self.listColumnHeader = ko.observableArray([
                { headerText: '', key: 'cateItemNumber', width: 20 },
                { headerText: '', key: 'categoryId', hidden: true },
                { headerText: getText('CMF005_24'), key: 'categoryName', width: 220 },
                { headerText: getText('CMF005_25'), key: 'timeStore', width: 100, formatter: timeStore },
                { headerText: getText('CMF005_26'), key: 'storageRangeSaved', width: 100, formatter: storageRangeSaved }
            ]);

            //DatePcicker B6_1
            self.enable = ko.observable(true);
            self.requiredDate = ko.observable(true);
            self.requiredMonth = ko.observable(true);
            self.requiredYear = ko.observable(true);

            self.startDateDailyString = ko.observable("");
            self.endDateDailyString = ko.observable("");

            self.startDateMothlyString = ko.observable("");
            self.endDateMothlyString = ko.observable("");

            self.startDateYearlyString = ko.observable("");
            self.endDateYearlyString = ko.observable("");

            self.dateValue = ko.observable({});
            self.monthValue = ko.observable({});
            self.yearValue = ko.observable({});

            self.startDateDailyString.subscribe(function(value) {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });
            self.endDateDailyString.subscribe(function(value) {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });

            self.startDateMothlyString.subscribe(function(value) {
                self.monthValue().startDate = value;
                self.monthValue.valueHasMutated();
            });
            self.endDateMothlyString.subscribe(function(value) {
                self.monthValue().endDate = value;
                self.monthValue.valueHasMutated();
            });

            self.startDateYearlyString.subscribe(function(value) {
                self.yearValue().startDate = value;
                self.yearValue.valueHasMutated();
            });
            self.endDateYearlyString.subscribe(function(value) {
                self.yearValue().endDate = value;
                self.yearValue.valueHasMutated();
            });


            //B7_1
            self.saveBeforDeleteOption = ko.observableArray([
                new model.ItemModel(model.SAVE_BEFOR_DELETE_ATR.YES, getText('CMF005_35')),
                new model.ItemModel(model.SAVE_BEFOR_DELETE_ATR.NO, getText('CMF005_36'))
            ]);

            //B8_1
            self.passwordForCompressFile = ko.observable("");
            self.confirmPasswordForCompressFile = ko.observable("");

            //B9_2
            supplementExplanation = ko.observable("");

            //D
            self.employeeList = ko.observableArray([]);
            self.selectedEmployee = ko.observableArray([]);
            self.selectedEmployeeCode = ko.observableArray([]);
            self.alreadySettingPersonal = ko.observableArray([]);
        }

        /**
         * Setting value default  screen B
         */
        setDefault() {
            var self = this;
            //set B3_1 "ON"
            self.rdSelected = ko.observable(1);

            //B6_2_2
            let startEndDate = self.getDateDefault();
            self.dateValue = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.monthValue = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.yearValue = ko.observable({ startDate: startEndDate.startYear, endDate: startEndDate.endYear });

            //B7_2_1
            self.isSaveBeforeDeleteFlg = ko.observable(model.SAVE_BEFOR_DELETE_ATR.YES);
            //B8_2_1
            self.isExistCompressPasswordFlg = ko.observable(true);
        }

        /**
         *Get status display button select category 
         */
        isEnableBtnOpenC() {
            var self = this;
            if (self.rdSelected() == 1) {
                return true;
            } else {
                return false;
            }
        }

        /**
         *Open screen C 
         */
        openScreenC() {
            var self = this;
            setShared("CMF005CParams_ListCategory", self.listDataCategory());
            setShared("CMF005CParams_SystemType", self.systemTypeCbb);
            modal("/view/cmf/005/c/index.xhtml").onClosed(() => {

                let categoryC = getShared('CMF005COutput_ListCategoryChose');
                let systemTypeC = getShared('CMF005COutput_SystemTypeChose');

                if (systemTypeC) {
                    self.systemTypeCbb = systemTypeC;
                    $("#B5_2_2").html(systemTypeC.name);
                }
                if (categoryC && (categoryC.length > 0)) {
                    self.listDataCategory.removeAll();
                    for (let i = 0; i < categoryC.length; i++) {
                        categoryC[i].cateItemNumber = i + 1;
                        self.listDataCategory.push(categoryC[i]);
                    }

                    self.setRangePickerRequire();
                }

                $("#B4_2").focus();
            });
        }

         openScreenF() {
            var self = this;
           
            modal("/view/cmf/005/f/index.xhtml").onClosed(() => {
                alert('ok!!!!!!!!!!!!!') });
        }
        
        /**
         *Open screen D 
         */
        nextScreenD() {
            let self = this;
            if (self.validateForm()) {
                if (self.listDataCategory().length > 0) {
                    // check so sanh hang ngay hang thang hang nam
                    if (self.validateDatePicker()) {
                        // check pass word
                        if (self.checkPass()) {
                            self.next();
                        }
                    } else {
                        alertError({ messageId: 'Msg_465' });
                    }

                } else {
                    alertError({ messageId: 'Msg_463' });
                }
            }
        }

        /**
         *Check validate client
         */
        ScreenModel.prototype.validateForm = function() {
            $(".validate_form").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        };

        /**
         * function next wizard by on click button 
         */
        private next() {
            let self = this;
            self.loadScreenD();
            $('#ex_accept_wizard').ntsWizard("next");
        }

        /**
        * Validate  DatePicker
        * return : boolean (true: Valid ,false: Invalid)
        */
        validateDatePicker() {
            let self = this;
            if (self.dateValue().startDate > self.dateValue().endDate) {
                return false;
            }
            if (self.monthValue().startDate > self.monthValue().endDate) {
                return false;
            }
            if (self.yearValue().startDate > self.yearValue().endDate) {
                return false;
            }

            return true;
        }

        /**
        * Validate Compress Password 
        * return : boolean (true: Valid ,false: Invalid)
        */
        checkPass() {
            let self = this;

            //check pass on
            if (self.isExistCompressPasswordFlg()) {
                // check pass empty
                if (self.passwordForCompressFile() && self.confirmPasswordForCompressFile()) {
                    // compare pass
                    if (self.passwordForCompressFile() == self.confirmPasswordForCompressFile()) {
                        return true;
                    } else {
                        alertError({ messageId: 'Msg_566' });
                        return false;
                    }
                } else {
                    alertError({ messageId: 'Msg_463' });
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
        * Setting require for RangePicker
        */
        private setRangePickerRequire(): void {
            let self = this;

            self.requiredDate = ko.observable(false);
            self.requiredMonth = ko.observable(false);
            self.requiredYear = ko.observable(false);
            for (var i = 0; i < self.listDataCategory().length; i++) {
                if (self.listDataCategory()[i].timeStore == 0) {
                    self.requiredMonth = ko.observable(true);
                } else if (self.listDataCategory()[i].timeStore == 1) {
                    self.requiredYear = ko.observable(true);
                } else if (self.listDataCategory()[i].timeStore == 2) {
                    self.requiredDate = ko.observable(true);
                }
            }
        }

        /**
         * Open screen A
         */
        backScreenA() {
            nts.uk.request.jump("/view/cmf/005/a/index.xhtml");
        }


        /**
         * Get date default
         *return 本日(NOW）－　１ヵ月　＋　１日
         */
        getDateDefault() {
            var timeCurrent = moment.utc(new Date(), "YYYY/MM/DD");
            //let dateCurrent = moment.utc("2000/3/28", "YYYY/MM/DD");
            var dateNow = timeCurrent.add(1, "M");
            let currentYear = timeCurrent.get('year');
            let date = dateNow.get('date');
            let moth = dateNow.get('month');
            let year = dateNow.get('year');

            let newMonth = moth - 1 == 0 ? 12 : moth - 1;
            var newYear = newMonth == 12 ? year - 1 : year;
            let newDate = date + 1;
            if (newMonth == 4 || newMonth == 6 || newMonth == 9 || newMonth == 11) {
                newDate = newDate - 30 > 0 ? newDate - 30 : newDate;
                newMonth = newDate - 30 > 0 ? newMonth + 1 : newMonth;
            } else {
                newDate = newDate - 31 > 0 ? newDate - 31 : newDate;
                newMonth = newDate - 31 > 0 ? newMonth + 1 : newMonth;
            }

            //if current year is a leap year
            if (moment([currentYear]).isLeapYear()) {

                if (newMonth == 2) {
                    let sub = newDate - 29;
                    if (sub > 0) {
                        newMonth = newMonth + 1;
                        newDate = sub;
                    }
                }
            }
            //if current year is not a leap year
            else {
                if (newMonth == 2) {
                    let sub = newDate - 28;
                    if (sub > 0) {
                        newMonth = newMonth + 1;
                        newDate = sub;
                    }
                }
            }
            return new model.ItemDate(newYear + "/" + newMonth + "/" + newDate, year + "/" + moth + "/" + date, newYear, year);
        }

        loadScreenD() {
            let self = this;
            self.loadCcg001Component();
            self.initComponnentKCP005();
            //            self.reloadEmployeeList();
        }


        //load D screen
        loadCcg001Component() {
            let self = this;
            // Set component option
            self.ccg001ComponentOption = {
                /** Common properties */
                showEmployeeSelection: true,
                systemType: 1,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,


                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.applyKCP005ContentSearch(data.listEmployee);
                }
            }

            $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
        }

        applyKCP005ContentSearch(dataEmployee: EmployeeSearchDto[]) {
            var self = this;
            var employeeSearchs: UnitModel[] = [];
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                employeeSearchs.push(new UnitModel(item.employeeCode,
                    item.employeeName, item.workplaceName));
            });

            self.employeeList(employeeSearchs);
            //            self.reloadEmployeeList();
        }

        initComponnentKCP005() {
            //KCP005
            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCode,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: true,
                maxWidth: 550,
                maxRows: 15
            };

            //            $('#employeeSearch').ntsListComponent(self.listComponentOption);
        }

        //        reloadEmployeeList() {
        //            $('#employeeSearch').ntsListComponent(self.listComponentOption)   //        }

        private previousB(): void {
            var self = this;
            self.previous();
        }

        private backToA() {
            let self = this;
            nts.uk.request.jump("/view/cmf/003/a/index.xhtml");
        }

        private nextFromDToE(): void {
            var self = this;
            self.initE();
            self.next();
        }

        private initE(): void {
            var self = this;
            $("#E3_3").html(self.dataSaveSetName());
            $("#E3_5").html(self.explanation());
            $("#E3_37").html(self.referenceDate);
        }
    }

    function timeStore(value, row) {
        if (value && value === '0') {
            return getText('Enum_TimeStore_MONTHLY');
        } else if (value && value === '1') {
            return getText('Enum_TimeStore_ANNUAL');
        } else if (value && value === '2') {
            return getText('Enum_TimeStore_FULL_TIME');
        } else if (value && value === '3') {
            return getText('Enum_TimeStore_DAILY');
        }
    }

    function storageRangeSaved(value, row) {
        if (value && value === '0') {
            return getText('Enum_Storage_Range_Saved_EARCH_EMP');
        } else if (value && value === '1') {
            return getText('Enum_Storage_Range_Saved_ALL_EMP');
        }
    }

    export class UnitModel {
        code: string;
        name: string;
        workplaceName: string;

        constructor(code: string, name: string, workplaceName: string) {
            this.code = code;
            this.name = name;
            this.workplaceName = workplaceName;
        }
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
}


