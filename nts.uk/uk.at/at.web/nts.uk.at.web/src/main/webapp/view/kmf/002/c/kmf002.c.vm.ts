module nts.uk.at.view.kmf002.c {

    import service = nts.uk.at.view.kmf002.c.service;
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {

            commonTableMonthDaySet: KnockoutObservable<nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet>;

            /* start declare variable CCG001 */
            ccgcomponent: GroupOption;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto[]>;
            /* end declare variable CCG001 */
            
            /* start declare variable KCP005 */
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            multiSelectedCode: KnockoutObservableArray<string[]>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel[]>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            /* end declare variable KCP005 */
            
            enableSave: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;
            
            constructor() {
                let _self = this;

                _self.enableSave = ko.observable(true);
                _self.enableDelete= ko.observable(true);
                _self.commonTableMonthDaySet = ko.observable(new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet());
                
                /* start declare variable CCG001 */
                _self.selectedEmployee = ko.observableArray([]);
                _self.showinfoSelectedEmployee = ko.observable(false);
                _self.baseDate = ko.observable(new Date());

                _self.ccgcomponent = {
                    baseDate: _self.baseDate,
                    //Show/hide options
                    isQuickSearchTab: true,
                    isAdvancedSearchTab: true,
                    isAllReferableEmployee: true,
                    isOnlyMe: true,
                    isEmployeeOfWorkplace: true,
                    isEmployeeWorkplaceFollow: true,
                    isMutipleCheck: true,
                    isSelectAllEmployee: true,
                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        _self.showinfoSelectedEmployee(true);
                        _self.selectedEmployee(dataList);
                        _self.employeeList.removeAll();
                        _.forEach(dataList, function(value: any) {
                            _self.employeeList.push({ code: value.employeeId, name: value.employeeName, workplaceName: value.workplaceName});  
                        });  
                        _self.findAllEmployeeRegister();
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        _self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        _self.selectedEmployee(dataEmployee);
                        _self.employeeList.removeAll();
                        _.forEach(data, function(value: any) {
                            _self.employeeList.push({ code: value.employeeId, name: value.employeeName, workplaceName: value.workplaceName});  
                        });
                        _self.findAllEmployeeRegister();
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        _self.showinfoSelectedEmployee(true);
                        _self.selectedEmployee(dataList);
                        _self.employeeList.removeAll();
                        _.forEach(dataList, function(value: any) {
                            _self.employeeList.push({ code: value.employeeId, name: value.employeeName, workplaceName: value.workplaceName});  
                        });
                        _self.findAllEmployeeRegister();
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        _self.showinfoSelectedEmployee(true);
                        _self.selectedEmployee(dataList);
                        _self.employeeList.removeAll();
                        _.forEach(dataList, function(value: any) {
                            _self.employeeList.push({ code: value.employeeId, name: value.employeeName, workplaceName: value.workplaceName});  
                        });
                        _self.findAllEmployeeRegister();
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        _self.showinfoSelectedEmployee(true);
                        _self.selectedEmployee(dataEmployee);
                        _self.employeeList.removeAll();
                        _.forEach(dataEmployee, function(value: any) {
                            _self.employeeList.push({ code: value.employeeId, name: value.employeeName, workplaceName: value.workplaceName});  
                        });
                        _self.findAllEmployeeRegister();
                    }
                }
                /* end declare variable CCG001 */
                
                /* start declare variable KCP005 */
                _self.baseDate = ko.observable(new Date());
                _self.selectedCode = ko.observable("");
                _self.multiSelectedCode = ko.observableArray([]);
                _self.isShowAlreadySet = ko.observable(true);
                _self.alreadySettingList = ko.observableArray([]);
                _self.isDialog = ko.observable(false);
                _self.isShowNoSelectRow = ko.observable(false);
                _self.isMultiSelect = ko.observable(false);
                _self.isShowWorkPlaceName = ko.observable(true);
                _self.isShowSelectAllButton = ko.observable(false);
                _self.employeeList = ko.observableArray<UnitModel>();
                _self.listComponentOption = {
                    isShowAlreadySet: _self.isShowAlreadySet(),
                    isMultiSelect: _self.isMultiSelect(),
                    listType: ListType.EMPLOYEE,
                    employeeInputList: _self.employeeList,
                    selectType: SelectType.NO_SELECT,
                    selectedCode: _self.selectedCode,
                    isDialog: _self.isDialog(),
                    isShowNoSelectRow: _self.isShowNoSelectRow(),
                    alreadySettingList: _self.alreadySettingList,
                    isShowWorkPlaceName: _self.isShowWorkPlaceName(),
                    isShowSelectAllButton: _self.isShowSelectAllButton(),
                    maxRows: 26
                };
                /* end declare variable KCP005 */
                
                /* include table */
                _self.commonTableMonthDaySet().visibleInfoSelect(true);
                _self.commonTableMonthDaySet().infoSelect1(nts.uk.resource.getText("Com_Person"));
//                _self.commonTableMonthDaySet.infoSelect2(_self.employeeList()[0].code);
//                _self.commonTableMonthDaySet.infoSelect3(_self.employeeList()[0].name);
                
                _self.selectedCode.subscribe(function(newValue: any) {
                    if (_.isNull(newValue)) {
                        _self.enableSave(false);
                    } else {
                        _self.enableSave(true);
                    }
                     _.forEach(_self.employeeList(), function(value: any) {
                        if (value.code == newValue) {
                            _self.commonTableMonthDaySet().infoSelect2(newValue);
                            _self.commonTableMonthDaySet().infoSelect3(value.name);
                        }
                    });   
                    if (_.isUndefined(_self.selectedCode()) || _.isEmpty(_self.selectedCode())) {
                        _self.enableDelete(false);
                    } else {
                        _self.enableDelete(true);
                    }
                    _self.getDataFromService();
                });
                
                _self.commonTableMonthDaySet().fiscalYear.subscribe(function(newValue) {
                    // change year
                    if (!nts.uk.ui.errors.hasError()) {
                        _self.getDataFromService(); 
                        _self.findAllEmployeeRegister();
                    }
                });
            }

            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let _self = this;
                _self.getDataFromService();
                 $.when($('#ccgcomponent').ntsGroupComponent(_self.ccgcomponent), 
                        $('#component-items-list').ntsListComponent(_self.listComponentOption)).done(function(data: any) {    
                    dfd.resolve();    
                });
                return dfd.promise();
            }
            
            private save(): void {
                let _self = this;
                if (!nts.uk.ui.errors.hasError()) {
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth(), _self.selectedCode()).done((data) => {
                        _self.getDataFromService();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });    
                } 
            }
            
            private remove(): void {
                let _self = this;
                 nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()).done((data) => {
                        _self.getDataFromService();
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });   
                }).ifNo(() => {
                }).ifCancel(() => {
                }).then(() => {
                });
            }
            
            private findAllEmployeeRegister(): JQueryPromise<any> {
                var dfd = $.Deferred<void>();
                let _self = this;
                $.when(service.findAllEmployeeRegister(_self.commonTableMonthDaySet().fiscalYear())).done(function(data: any) {
                    _self.alreadySettingList.removeAll();
                    _.forEach(data, function(code) {
                        _self.alreadySettingList.push({code: code, isAlreadySetting: true});
                    })
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            private getDataFromService(): void {
                let _self = this;
                if (!_.isNull(_self.selectedCode()) && !_.isEmpty(_self.selectedCode())) {
                    $.when(service.find(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()), 
                            service.findFirstMonth(),
                            _self.findAllEmployeeRegister()).done(function(data: any, data2: any) {
                        if (typeof data === "undefined") {
                            /** 
                             *   create value null for prepare create new 
                            **/
                            _.forEach(_self.commonTableMonthDaySet().arrMonth(), function(value: any) {
                                value.day(0);
                            });
                            _self.enableDelete(false);
                        } else {
                            _self.commonTableMonthDaySet().arrMonth.removeAll();
                            for (let i=data2.startMonth-1; i<12; i++) {
                                _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                              'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                              'enable': ko.observable(true)});    
                            }
                            for (let i=0; i<data2.startMonth-1; i++) {
                                _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                              'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                              'enable': ko.observable(true)});    
                            }
                            _self.enableDelete(true);
                        }  
                    });    
                }
            }
        }

        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
        }

        export interface GroupOption {
            baseDate?: KnockoutObservable<Date>;
            // クイック検索タブ
            isQuickSearchTab: boolean;
            // 参照可能な社員すべて
            isAllReferableEmployee: boolean;
            //自分だけ
            isOnlyMe: boolean;
            //おなじ部門の社員
            isEmployeeOfWorkplace: boolean;
            //おなじ＋配下部門の社員
            isEmployeeWorkplaceFollow: boolean;


            // 詳細検索タブ
            isAdvancedSearchTab: boolean;
            //複数選択 
            isMutipleCheck: boolean;

            //社員指定タイプ or 全社員タイプ
            isSelectAllEmployee: boolean;

            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;

            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;

            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;

            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
        }
        
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
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
}