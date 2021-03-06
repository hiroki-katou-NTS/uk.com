module nts.uk.at.view.kfp001.c {
    import getText = nts.uk.resource.getText;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    export module viewmodel {
        import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

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
            reintegration: KnockoutObservable<boolean> = ko.observable(false);
            //Declare employee filter component
            ccg001ComponentOption: any;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;
            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            //close period
            periodStartDate: any = ko.observable(null);
            dScreenmodel: any = ko.observable(null);
            periodEndDate: any = ko.observable(null);


            constructor() {

                var self = this;

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
                self.isShowSelectAllButton = ko.observable(false);

                this.employeeList = ko.observableArray<UnitModel>([]);

                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.multiSelectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: self.isShowSelectAllButton(),
                    isSelectAllAfterReload: true
                };


                //Init employee filter component
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());
                self.reloadCcg001();
                self.dScreenmodel = new nts.uk.at.view.kfp001.d.viewmodel.ScreenModel();

            }

            public reloadCcg001(): void {
                let self = this;
                if ($('.ccg-sample-has-error').ntsError('hasError')) {
                    return;
                }
                //                let dataB = nts.uk.ui.windows.getShared("KFP001_DATAB");
                //                if (dataB != null) {
                //                    self.periodStartDate(moment.utc(dataB.periodStartDate).toISOString());
                //                    self.periodEndDate(moment.utc(dataB.periodEndDate).toISOString());
                //                } else {
                //                    self.periodStartDate(moment.utc().toISOString());
                //                    self.periodEndDate(moment.utc().toISOString());
                //                }

                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2, // ??????????????????
                    showEmployeeSelection: false, // ???????????????
                    showQuickSearchTab: true, // ??????????????????
                    showAdvancedSearchTab: true, // ????????????
                    showBaseDate: false, // ???????????????
                    showClosure: true, // ?????????????????????
                    showAllClosure: true, // ???????????????
                    showPeriod: true, // ??????????????????
                    periodFormatYM: false, // ??????????????????

                    /** Required parameter */
                    baseDate: moment().toISOString(), // ?????????
                    periodStartDate: self.periodStartDate(), // ?????????????????????
                    periodEndDate: self.periodEndDate(), // ?????????????????????
                    inService: true, // ????????????
                    leaveOfAbsence: false, // ????????????
                    closed: false, // ????????????
                    retirement: false, // ????????????

                    /** Quick search tab options */
                    showAllReferableEmployee: true, // ??????????????????????????????
                    showOnlyMe: true, // ????????????
                    showSameWorkplace: true, // ?????????????????????
                    showSameWorkplaceAndChild: true, // ????????????????????????????????????

                    /** Advanced search properties */
                    showEmployment: false, // ????????????
                    showWorkplace: true, // ????????????
                    showClassification: true, // ????????????
                    showJobTitle: true, // ????????????
                    showWorktype: true, // ????????????
                    isMutipleCheck: true, // ???????????????

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(data.listEmployee);

                        //Convert list Object from server to view model list
                        let items = _.map(data.listEmployee, item => {
                            return {  
                                code: item.employeeCode,
                                name: item.employeeName,
                                affiliationName: item.affiliationName,
                                isAlreadySetting: true
                            }
                        });
                        self.employeeList(items);

                        //Fix bug 42, bug 43
                        let selectList = _.map(data.listEmployee, item => {
                            return item.employeeCode;
                        });
                        self.multiSelectedCode([]);
                        self.multiSelectedCode(selectList);
                        $('#component-items-list').ntsListComponent(self.listComponentOption);
                    }
                }

                // Start component
                //                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
            }

            start() {
                var self = this;
                let reintegration =   nts.uk.ui.windows.getShared("B_CHECKED");
                if(!isNullOrUndefined(reintegration)){
                    self.reintegration(reintegration);
                }
                self.reloadCcg001();
                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
                $('#component-items-list').ntsListComponent(self.listComponentOption);
                $("#button-2").focus();
            }


            opendScreenBorJ() {
                let self = this;
                if (self.selectedEmployee().length <= 0 || self.multiSelectedCode().length <= 0) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_206" });
                    return;
                }
                $("#wizard").ntsWizard("next").done(function() {
                    $('#checkBox1').focus();
                });
                nts.uk.ui.windows.setShared("B_CHECKED", self.reintegration());
                nts.uk.ui.windows.setShared("KFP001_DATAC", self.selectedEmployee());
                nts.uk.ui.windows.setShared("KFP001_DATAC_SELECT", self.multiSelectedCode().length);
                self.dScreenmodel.start();
            }
            navigateView() {
                $("#wizard").ntsWizard("prev").done(function() {
                    $('#checkBox1').focus();

                });
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
            affiliationName: string;
            isAlreadySetting: boolean;
            constructor(x: EmployeeSearchDto) {
                let self = this;
                if (x) {
                    self.code = x.employeeCode;
                    self.name = x.employeeName;
                    self.affiliationName = x.workplaceName;
                    self.isAlreadySetting = false;
                } else {
                    self.code = "";
                    self.name = "";
                    self.affiliationName = "";
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
            // ????????????????????????
            isQuickSearchTab: boolean;
            // ??????????????????????????????
            isAllReferableEmployee: boolean;
            //????????????
            isOnlyMe: boolean;
            //????????????????????????
            isEmployeeOfWorkplace: boolean;
            //?????????????????????????????????
            isEmployeeWorkplaceFollow: boolean;


            // ??????????????????
            isAdvancedSearchTab: boolean;
            //???????????? 
            isMutipleCheck: boolean;

            //????????????????????? or ??????????????????
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
