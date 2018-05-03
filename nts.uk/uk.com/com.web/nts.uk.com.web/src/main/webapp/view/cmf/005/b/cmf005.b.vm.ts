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

        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        listDataCategory: KnockoutObservableArray<model.ItemCategory>;
        listColumnHeader: KnockoutObservableArray<NtsGridListColumn>;
        selectedCsvItemNumber: KnockoutObservable<number> = ko.observable(null);
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;

        //datepicker
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


        //D3_1
        ccg001ComponentOption: GroupOption;
        employeeInputList: KnockoutObservableArray<UnitModel>;

        constructor() {
            var self = this;
            self.initComponents();
            self.setDefault();

            self.employeeInputList = ko.observableArray([]);
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
//            for (let i = 1; i < 5; i++) {
//                self.listDataCategory.push(new model.ItemCategory(i, '00' + i, 'catename' + i, 'aaaa', 'all'));
//            }
            self.listColumnHeader = ko.observableArray([
                { headerText: '', key: 'cateItemNumber', width: 10, hidden: false },
                { headerText: '', key: 'categoryId', width: 10, hidden: true },
                { headerText: 'abc', key: 'categoryName', width: 150, hidden: false },
                { headerText: '123', key: 'timeDeletion', width: 20, hidden: false },
                { headerText: '456', key: 'rangeDeletion', width: 30 }
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
        }

        setDefault() {
            var self = this;
            //set B3_1 "ON"
            self.rdSelected = ko.observable(1);

            //B6_2_2
            let startEndDate = self.getDate();
            self.dateValue1 = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.dateValue2 = ko.observable({ startDate: startEndDate.startDate, endDate: startEndDate.endDate });
            self.dateValue3 = ko.observable({ startDate: startEndDate.startYear, endDate: startEndDate.endYear });

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
                        return new model.ItemCategory(indexOfItem,item.categoryId,item.categoryName,item.timeDeletion,item.rangeDeletion);
                    });
                    self.listDataCategory = listCategoryChoseAdd;
                    //                    let listDataCategory = data.listCategoryChose;
                    //                    cosole.log(listDataCategory.length);
                    //                    if (listDataCategory) {
                    //                        self.listDataCategory(listDataCategory);
                    //                        for (let i = 0; i < listDataCategory().length; i++) {
                    //                           let model.ItemCategory = listDataCategory ;
                    //                        }
                    //                    }
                      
                      console.log(listCategoryChoseAdd.length);
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
            self.loadScreenD();
            $('#ex_accept_wizard').ntsWizard("next");
        }

        // Open screen A
        backScreenA() {
            nts.uk.request.jump("/view/cmf/005/a/index.xhtml");
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
            self.showEmployeeList();
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
                    self.searchEmployee(data.listEmployee);
                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
        }

        searchEmployee(dataEmployee: EmployeeSearchDto[]) {
            var self = this;
            self.employeeInputList.removeAll();
            _.forEach(dataEmployee, function(item: EmployeeSearchDto) {
                self.employeeInputList.push(new UnitModel({
                    id: item.employeeId,
                    code: item.employeeCode,
                    businessName: item.employeeName,
                    workplaceName: item.workplaceName,
                }));
            });

            self.showEmployeeList();
        }

        showEmployeeList() {
            var self = this;
            //            console.log(self.employeeInputList);
            var listComponentOption: ComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeInputList,
                selectType: SelectType.SELECT_ALL,
                isDialog: false,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: true
            };

            $('#component-employees-list').ntsListComponent(listComponentOption);
        }

    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        switchValue: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
            this.deletable = deletable;
            this.switchValue = ((code % 3) + 1).toString();
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
}


