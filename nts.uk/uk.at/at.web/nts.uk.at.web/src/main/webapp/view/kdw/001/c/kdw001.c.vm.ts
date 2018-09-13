module nts.uk.at.view.kdw001.c {
    import getText = nts.uk.resource.getText;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    export module viewmodel {
        export class ScreenModel {

            //Declare screenName flag to forward screen B or screen C
            screenName: string;

            //Declare kcp005 list properties
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;

            //Declare time range input
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            // startDate for validate
            startDateValidate: KnockoutObservable<string>;

            //Declare employee filter component
            ccg001ComponentOption: any;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;
            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            //close period
            periodStartDate: any;

            closureId: KnockoutObservable<any> = ko.observable(1);

            constructor() {

                var self = this;

                //Get screenName value from a screen
                __viewContext.transferred.ifPresent(data => {
                    self.screenName = data.screenName;
                });

                //Init kcp005 properties
                self.baseDate = ko.observable(new Date());
                self.selectedCode = ko.observable(null);
                self.multiSelectedCode = ko.observableArray([]);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([
                    { code: '1', isAlreadySetting: true },
                    { code: '2', isAlreadySetting: true }
                ]);
                self.isDialog = ko.observable(true);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.isShowWorkPlaceName = ko.observable(true);
                self.isShowSelectAllButton = ko.observable(true);

                this.employeeList = ko.observableArray<UnitModel>([]);

                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.multiSelectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: false
                };



                //Init time range input
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                let today = new Date;
                self.dateValue = ko.observable({});
                self.dateValue().startDate = "2017/11/08";
                self.dateValue().endDate = today;
                self.startDateValidate = ko.observable("");


                //var closureID = __viewContext.transferred.value.closureID;
                //self.closureId = ko.observable(1);
                let closureID = '1';
                service.findPeriodById(Number(closureID)).done((data) => {
                    self.startDateValidate = data.startDate;
                    self.periodStartDate = data.startDate.toString();
                    self.dateValue().startDate = data.startDate.toString();
                    self.dateValue().endDate = data.endDate.toString();
                    self.dateValue.valueHasMutated();
                    self.reloadCcg001();
                    $('#ccgcomponent').focus();
                    $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                }).always(() => {
                    self.startDateString = ko.observable("");
                    self.endDateString = ko.observable("");

                    self.startDateString.subscribe(function(value) {
                        self.dateValue().startDate = value;
                        self.dateValue.valueHasMutated();
                    });

                    self.endDateString.subscribe(function(value) {
                        self.dateValue().endDate = value;
                        self.dateValue.valueHasMutated();
                    });
                    self.reloadCcg001();
                    $('#ccgcomponent').focus();
                    $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                });

                self.closureId.subscribe(function(value) {
                    service.findPeriodById(Number(value)).done((data) => {
                        self.startDateValidate = data.startDate;
                        self.periodStartDate = data.startDate.toString();
                        self.dateValue().startDate = data.startDate.toString();
                        self.dateValue().endDate = data.endDate.toString();
                        self.dateValue.valueHasMutated();
//                        self.reloadCcg001();
//                        $('#ccgcomponent').focus();
//                        $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                    }).always(() => {
                        self.startDateString = ko.observable("");
                        self.endDateString = ko.observable("");

                        self.startDateString.subscribe(function(value) {
                            self.dateValue().startDate = value;
                            self.dateValue.valueHasMutated();
                        });

                        self.endDateString.subscribe(function(value) {
                            self.dateValue().endDate = value;
                            self.dateValue.valueHasMutated();
                        });
//                        self.reloadCcg001();
//                        $('#ccgcomponent').focus();
//                        $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                    });
                });
                
//                if(self.dateValue().startDate != "2017/11/08" && self.dateValue().endDate != today){
//                    
//                    self.dateValue.subscribe(function(value){
//                        self.reloadCcg001();
//                        $('#ccgcomponent').focus();
//                        $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
//                    });
//                }

                //                self.dateValue.subscribe(function(value){
                //                    if(self.startDateValidate() != "" && (value.startDate < self.startDateValidate())) {
                //                        $('#daterangepicker').ntsError('set', {messageId:"Msg_1349"});
                //                    } else {
                //                        $('#daterangepicker').ntsError('clear');   
                //                    }
                //                });


                //Init employee filter component
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());



            }

            public reloadCcg001(): void {
                let self = this;
                if ($('.ccg-sample-has-error').ntsError('hasError')) {
                    return;
                }
                //                if (!self.showBaseDate() && !self.showClosure() && !self.showPeriod()){
                //                    nts.uk.ui.dialog.alertError("Base Date or Closure or Period must be shown!" );
                //                    return;
                //                }
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2, // シスッ�区�
                    showEmployeeSelection: false, // 検索タイ�
                    showQuickSearchTab: true, // クイヂ�検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: false, // 基準日利用
                    showClosure: true, // 就業�め日利用
                    showAllClosure: false, // 全�め表示
                    showPeriod: true, // 対象期間利用
                    periodFormatYM: false, // 対象期間精度

                    /** Required parameter */
                    baseDate: moment().toISOString(), // 基準日
                    periodStartDate: self.dateValue().startDate,
                    periodEndDate: self.dateValue().endDate,
                    inService: true, // 在職区�
                    leaveOfAbsence: true, // 休�区�
                    closed: true, // 休業区�
                    retirement: false, // 退職区�

                    /** Quick search tab options */
                    showAllReferableEmployee: true, // 参�可能な社員すべて
                    showOnlyMe: true, // 自刁��
                    showSameWorkplace: true, // 同じ職場の社員
                    showSameWorkplaceAndChild: true, // 同じ職場とそ�配下�社員

                    /** Advanced search properties */
                    showEmployment: false, // 雔�条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 刡�条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モー�

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(data.listEmployee);
                        self.closureId(data.closureId);

                        //Convert list Object from server to view model list
                        let items = _.map(data.listEmployee, item => {
                            return new UnitModel(item);
                        });
                        self.employeeList(items);

                        //Fix bug 42, bug 43
                        let selectList = _.map(data.listEmployee, item => {
                            return item.employeeCode;
                        });
                        self.multiSelectedCode(selectList);
                    }
                }

                // Start component
                //                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
            }

            opendScreenBorJ() {
                let self = this;
                var closureID = '1';
                if (self.dateValue().startDate < self.startDateValidate) {
                    //                    $('#daterangepicker  input[id$=-startInput],#daterangepicker  input[id$=-endInput]'').ntsError('clear');
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1349" });
                    return;
                }
                if (!nts.uk.ui.errors.hasError()) {
                    service.findPeriodById(Number(self.closureId())).done((data) => {
                        if (data) {
                            let listEmpSelected = self.listComponentOption.selectedCode();
                            if (listEmpSelected == undefined || listEmpSelected.length <= 0) {
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_206" });
                                return;
                            }
                            let startDateS = self.dateValue().startDate.split("/");
                            let endDateS = self.dateValue().endDate.split("/");
                            let startDate = new Date(startDateS[0], startDateS[1], startDateS[2]);
                            let endDate = new Date(endDateS[0], endDateS[1], endDateS[2]);
                            let startDate_unixtime = parseInt(startDate.getTime() / 1000);
                            let endDate_unixtime = parseInt(endDate.getTime() / 1000);
                            var timeDifference = endDate_unixtime - startDate_unixtime;
                            var timeDifferenceInHours = timeDifference / 60 / 60;
                            var timeDifferenceInDays = timeDifferenceInHours / 24;

                            if (timeDifferenceInDays > 31) {
                                nts.uk.ui.dialog.confirm('対象期間�か月を趁�てぁ�すがよろしいですか).ifYes(() => {
                                    let yearPeriodStartDate = self.periodStartDate.split("/")[0];
                                    let monthPeriodStartDate = self.periodStartDate.split("/")[1];
                                    let dayPeriodStartDate = self.periodStartDate.split("/")[2];
                                    let yearStartDate = Number(self.dateValue().startDate.split("/")[0]);
                                    let monthStartDate = Number(self.dateValue().startDate.split("/")[1]);
                                    let dayStartDate = Number(self.dateValue().startDate.split("/")[2]);
                                    if (yearStartDate < yearPeriodStartDate || monthStartDate < monthPeriodStartDate || dayStartDate < dayPeriodStartDate) {
                                        nts.uk.ui.dialog.alertError('�め�琜�間より過去の日付�挮�できません');
                                        return;
                                    }


                                    let listEmpSelectedId = [];
                                    _.forEach(self.selectedEmployee(), function(value) {
                                        if (_.includes(listEmpSelected, value.employeeCode)) {
                                            listEmpSelectedId.push(value.employeeId);
                                        }
                                    });




                                    __viewContext["viewmodel"].params.setParamsScreenC({
                                        closureID: self.closureId(),
                                        lstEmployeeID: listEmpSelectedId,
                                        periodStartDate: self.dateValue().startDate,
                                        periodEndDate: self.dateValue().endDate
                                    });
                                    $("#wizard").ntsWizard("next").done(function() {
                                        $('#checkBox1').focus();
                                    });

                                })

                            } else {
                                let monthNow = data.month; // thieu thang hien tai cua  domain ��
                                let monthStartDate = Number(self.dateValue().startDate.split("/")[1]);
                                if (monthStartDate < monthNow) {
                                    nts.uk.ui.dialog.alertError('�め�琜�間より過去の日付�挮�できません');
                                    return;
                                }

                                let listEmpSelectedId = [];
                                _.forEach(self.selectedEmployee(), function(value) {
                                    if (_.includes(listEmpSelected, value.employeeCode)) {
                                        listEmpSelectedId.push(value.employeeId);
                                    }
                                });

                                __viewContext["viewmodel"].params.setParamsScreenC({
                                    closureID: self.closureId(),
                                    lstEmployeeID: listEmpSelectedId,
                                    periodStartDate: self.dateValue().startDate,
                                    periodEndDate: self.dateValue().endDate
                                });
                                $("#wizard").ntsWizard("next").done(function() {
                                    $('#checkBox1').focus();
                                });

                            }
                        }
                    });
                }//end if

            }

            start() {
                var self = this;
                $('#ccgcomponent').focus();
                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                $('#component-items-list').ntsListComponent(self.listComponentOption);
                $('#ccgcomponent').attr('tabindex',1);
                $("#com-kcp-searchbox *").attr('tabindex', -1);
                $("table").attr('tabindex', 4);
                $('#ccg001-btn-search-drawer').focus();
            }

        }

        //Object for kcp005
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export class UnitModel {
            code: string;
            name: string;
            workplaceName: string;
            isAlreadySetting: boolean;
            constructor(x: EmployeeSearchDto) {
                let self = this;
                if (x) {
                    self.code = x.employeeCode;
                    self.name = x.employeeName;
                    self.workplaceName = x.workplaceName;
                    self.isAlreadySetting = false;
                } else {
                    self.code = "";
                    self.name = "";
                    self.workplaceName = "";
                    self.isAlreadySetting = false;
                }
            }
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

        //Object for filter component        
        export interface GroupOption {
            baseDate?: KnockoutObservable<Date>;
            // クイヂ�検索タ�
            isQuickSearchTab: boolean;
            // 参�可能な社員すべて
            isAllReferableEmployee: boolean;
            //自刁��
            isOnlyMe: boolean;
            //おなじ部門の社員
            isEmployeeOfWorkplace: boolean;
            //おなじ＋�下部門の社員
            isEmployeeWorkplaceFollow: boolean;


            // 詳細検索タ�
            isAdvancedSearchTab: boolean;
            //褕�選�
            isMutipleCheck: boolean;

            //社員挮�タイ�or 全社員タイ�
            isSelectAllEmployee: boolean;

            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;

            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;

            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;

            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
        }

        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
        }

    }
}
