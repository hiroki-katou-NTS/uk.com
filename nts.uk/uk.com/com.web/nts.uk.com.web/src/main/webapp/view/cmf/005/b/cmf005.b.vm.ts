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
        systemTypeName: KnockoutObservable<string>;
        systemTypeId: KnockoutObservable<number>;

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

            //B5_2_2
            self.systemTypeName = ko.observable('');
            self.systemTypeId = ko.observable('');
            //B5_3
            self.listDataCategory = ko.observableArray([]);
            self.currentCode = ko.observable();
            self.currentCategory = ko.observableArray([]);
            //            for (let i = 1; i < 5; i++) {
            //                self.listDataCategory.push(new model.ItemCategory(i, '00' + i, 'catename' + i, 'aaaa', 'all'));
            //            }
            self.listColumnHeader = ko.observableArray([
                { headerText: '', key: 'cateItemNumber', width: 20, hidden: false },
                { headerText: '', key: 'categoryId', hidden: true },
                { headerText: getText('CMF005_24'), key: 'categoryName', width: 200, hidden: false },
                { headerText: getText('CMF005_25'), key: 'timeDeletion', width: 100, hidden: false },
                { headerText: getText('CMF005_26'), key: 'rangeDeletion', width: 100, hidden: false }
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
            self.itemTitleAtr = ko.observableArray([
                { value: 0, titleAtrName: resource.getText('CMF005_51') },
                { value: 1, titleAtrName: resource.getText('CMF005_52') }]);
            self.selectedTitleAtr = ko.observable(0);
            self.selectedTitleAtr.subscribe(function(value) {
                if(value == 0) {
                    self.applyKCP005ContentSearch([]);
                } 
                else {
                    self.applyKCP005ContentSearch(self.employeeList);
                }
            });
            self.initComponnentKCP005();
        }

        setDefault() {
            var self = this;
            //set B3_1 "ON"
            self.rdSelected = ko.observable(1);

            //B6_2_2
            let startEndDate = self.getDate();
            self.dateValue = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.monthValue = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.yearValue = ko.observable({ startDate: startEndDate.startYear, endDate: startEndDate.endYear });

            //B7_2_1
            self.isSaveBeforeDeleteFlg = ko.observable(model.SAVE_BEFOR_DELETE_ATR.YES);
            //B8_2_1
            self.isExistCompressPasswordFlg = ko.observable(true);
        }

        // get status display button select category
        isEnableBtnOpenC() {
            var self = this;
            if (self.rdSelected() == 1) {
                return true;
            } else {
                return false;
            }
        }

        // Open screen C
        openScreenC() {
            var self = this;
            let param = {
                lstCategory: self.listDataCategory,
                systemTypeName: self.systemTypeName
            };
            setShared("CMF005CParams", param);
            modal("/view/cmf/005/c/index.xhtml", { width: 800, title: "カテゴリの選択" }).onClosed(() => {
                let data = getShared("CMF005COutput");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    var listCategoryChoseAdd = _.map(data.listCategoryChose, item => {
                        let indexOfItem = _.findIndex(data.listCategoryChose, { categoryId: item.categoryId });
                        return new model.ItemCategory(indexOfItem, item.categoryId, item.categoryName, item.timeDeletion, item.rangeDeletion);
                    });
                    self.listDataCategory(listCategoryChoseAdd);
                    //                    let listDataCategory = data.listCategoryChose;
                    //                    cosole.log(listDataCategory.length);
                    //                    if (listDataCategory) {
                    //                        self.listDataCategory(listDataCategory);
                    //                        for (let i = 0; i < listDataCategory().length; i++) {
                    //                           let model.ItemCategory = listDataCategory ;
                    //                        }
                    //                    }

                    console.log(self.listDataCategory().length);
                    if (data.systemTypeId == model.SYSTEM_TYPE.PERSON_SYS) {
                        self.systemTypeName(getText('CMF005_170'));
                    }
                    if (data.systemTypeId == model.SYSTEM_TYPE.ATTENDANCE_SYS) {
                        self.systemTypeName(getText('CMF005_171'));
                    }
                    if (data.systemTypeId == model.SYSTEM_TYPE.PAYROLL_SYS) {
                        self.systemTypeName(getText('CMF005_172'));
                    }
                    if (data.systemTypeId == model.SYSTEM_TYPE.OFFICE_HELPER) {
                        self.systemTypeName(getText('CMF005_173'));
                    }
                    self.systemTypeId = data.systemTypeId;
                }
            });
        }


        // Open screen D
        nextScreenD() {
            let self = this;

             self.nextFromBToD();
//            if (self.listDataCategory().length > 0) {
//                // check so sanh hang ngay hang thang hang nam
//                if (self.checkDatePicker()) {
//                    // check pass word
//                    if (self.checkPass()) {
//                        self.next();
//                    }
//                } else {
//                    alertError({ messageId: 'Msg_465' });
//                }
//
//            } else {
//                alertError({ messageId: 'Msg_463' });
//            }
        }

        /**
         * function next wizard by on click button 
         */
        private nextFromBToD() {
            let self = this;
            self.loadScreenD();
            self.next();
        }
        
         /**
         * function next wizard by on click button 
         */
        private next() {
            $('#ex_accept_wizard').ntsWizard("next");
        }
        /**
         * function previous wizard by on click button 
         */
        private previous() {
            $('#ex_accept_wizard').ntsWizard("prev");
        }

        // Open screen A
        backScreenA() {
            nts.uk.request.jump("/view/cmf/005/a/index.xhtml");
        }


        //check date,month,year
        checkDatePicker() {
            return true;
        }
        // validate password
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
         *return 本日(NOW）－　１ヵ月　＋　１日
         */
        getDate() {
            var timeCurrent = moment.utc(new Date(), "YYYY/MM/DD");
            //            let dateCurrent = moment.utc("2000/3/28", "YYYY/MM/DD");
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
                    self.selectedEmployee(data.listEmployee);
                    self.applyKCP005ContentSearch(data.listEmployee);
                }
            }
            $('#ccgcomponent').ntsGroupComponent(self.loadCcg001Component).done(function() {
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.initComponnentKCP005).done(function() {
                });
            });
        }

        applyKCP005ContentSearch(dataEmployee: EmployeeSearchDto[]) {
            var self = this;
            var employeeSearchs: UnitModel[] = [];
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                employeeSearchs.push(new UnitModel(item.employeeCode, 
                    item.employeeName, item.workplaceName));
            });

            self.employeeList(employeeSearchs);
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
        }
                
        private previousB(): void {
                var self = this;
                self.previous();
        }
        
        private backToA() {
            let self = this;
            nts.uk.request.jump("/view/cmf/003/a/index.xhtml");
        }
        
        
        /**
             * function submit button
         */
        private validateD() : boolean {
             var self = this;
            if (self.selectedEmployee()) {
                return true;
            } else {
                nts.uk.ui.dialog.error({ messageId: "Msg_498", messageParams: ["X", "Y"] });
                return false;
            }
        }
        
        private nextFromDToE(): void {
            var self = this;
            if (self.validateD()) {
                self.next();
            }
        }
            
        private initE(): void {
            var self = this;
            $("#E3_3").html(self.dataSaveSetName());
            $("#E3_5").html(self.explanation());
            $("#E3_37").html(self.referenceDate);
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
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


