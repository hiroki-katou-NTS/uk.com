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

    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    
    export class ScreenModel {


        //Radio button
        rdSelected: KnockoutObservable<any>;
        optionCategory: KnockoutObservable<any>;
        optionDeleteSet: KnockoutObservable<any>;

        //information category
        deleteSetName: KnockoutObservable<string>;

               
        // B5_2_2
        systemType: KnockoutObservable<string>;

        //B5_3
        listDataCategory: KnockoutObservableArray<ItemCategory>;
        listColumnHeader: KnockoutObservableArray<NtsGridListColumn>;
        selectedCsvItemNumber: KnockoutObservable<number> = ko.observable(null);
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        
        //datepicker
        enable: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        dateValue1: KnockoutObservable<any>;
        dateValue2: KnockoutObservable<any>;
        dateValue3: KnockoutObservable<any>;
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
        ccg001ComponentOption: GroupOption;
        
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
            self.systemType= ko.observable('');
            //B5_3
            self.listDataCategory = ko.observableArray([]);
            for(let i = 1; i < 5; i++) {
                self.listDataCategory.push(new model.ItemCategory(i, '00'+i, 'catename' + i, 'aaaa', 'all'));
            }
            self.listColumnHeader = ko.observableArray([
                { headerText: '', key: 'cateItemNumber', width: 10, hidden: false },
                { headerText: '', key: 'cateId', width: 10, hidden: true },
                { headerText: 'abc', key: 'cateName', width: 150, hidden: false },
                { headerText: '123', key: 'timeDeletion', width: 20, hidden: false },
                { headerText: '456', key: 'rangeDeletion', width: 30 }
            ]);

            //DatePcicker B6_1
            self.enable = ko.observable(true);
            self.required = ko.observable(true);

            self.startDateDailyString = ko.observable("");
            self.endDateDailyString = ko.observable("");

            self.startDateMothlyString = ko.observable("");
            self.endDateMothlyString = ko.observable("");

            self.startDateYearlyString = ko.observable("");
            self.endDateYearlyString = ko.observable("");

            self.dateValue1 = ko.observable({});
            self.dateValue2 = ko.observable({});
            self.dateValue3 = ko.observable({});

            self.startDateDailyString.subscribe(function(value) {
                self.dateValue1().startDate = value;
                self.dateValue1.valueHasMutated();
            });
            self.endDateDailyString.subscribe(function(value) {
                self.dateValue1().endDate = value;
                self.dateValue1.valueHasMutated();
            });

            self.startDateMothlyString.subscribe(function(value) {
                self.dateValue2().startDate = value;
                self.dateValue2.valueHasMutated();
            });
            self.endDateMothlyString.subscribe(function(value) {
                self.dateValue2().endDate = value;
                self.dateValue2.valueHasMutated();
            });

            self.startDateYearlyString.subscribe(function(value) {
                self.dateValue3().startDate = value;
                self.dateValue3.valueHasMutated();
            });
            self.endDateYearlyString.subscribe(function(value) {
                self.dateValue3().endDate = value;
                self.dateValue3.valueHasMutated();
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
                lstCategory:self.listDataCategory,
                systemType: self.systemType
            };
            setShared("paramCmf005b", param);
            modal("/view/cmf/005/c/index.xhtml",{ width: 800, title: "カテゴリの選択" }).onClosed(() => {
                let data = getShared("paramCmf005b");
                if (!nts.uk.util.isNullOrUndefined(data))
                    self.listDataCategory(data.lstDataCategoryChose);
                self.systemType(data.systemType);
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
         * return 本日(NOW）－　１ヵ月　＋　１日
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
        
        
        //load D screen
        loadScreenD() {
            let self = this;
            // Set component option
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: true,
                showAllClosure: true,
                showPeriod: true,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

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

                /**
                * Self-defined function: Return data from CCG001
                * @param: data: the data return from CCG001
                */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);
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


