module nts.uk.at.view.kdm001.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        closureID: string;
        selectedEmployeeObject: any;
        periodOptionItem: KnockoutObservableArray<ItemModel>;
        selectedPeriodItem: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        dispTotalRemain: KnockoutObservable<string> = ko.observable(null);
        expirationDate: KnockoutObservable<string> = ko.observable(null);
        //_____CCG001________
        ccgcomponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009> = ko.observableArray([]);
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.EMPLOYMENT);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        targetBtnText: string = getText("KCP009_3");
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        tabindex: number = -1;
        compositePayOutSubMngData: KnockoutObservableArray<CompositePayOutSubMngData>;
        
        constructor() {
            var self = this;
            
            self.compositePayOutSubMngData = ko.observableArray([]);
            self.periodOptionItem = ko.observableArray([
                new ItemModel(0, getText("KDM001_4")),
                new ItemModel(1, getText("KDM001_5"))
            ]);
            self.selectedPeriodItem = ko.observable(0);
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.dateValue = ko.observable({});

            self.startDateString.subscribe(function(value) {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe(function(value) {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });
            
            //_____CCG001________
            self.selectedEmployee = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.ccgcomponent = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: false,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
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
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }
            
            $('#ccgcomponentA').ntsGroupComponent(self.ccgcomponent);
            
            self.selectedItem.subscribe(x =>{
                if(self.selectedEmployee().length > 0) {
                    self.selectedEmployeeObject = _.find(self.selectedEmployee(), item => { return item.employeeId === x; });
                }
                    
                self.updateDataList();
            });
            
            $("#compositePayOutSubMngDataGrid").ntsGrid({
                height: '520px',
                name: 'Grid name',
                dataSource: self.compositePayOutSubMngData(),
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'string', width: '0px', hidden: true },
                    { headerText: 'linked', key: 'isLinked', dataType: 'string', width: '0px', hidden: true },
                    { headerText: '', key: 'expiredDate', dataType: 'string', width: '0px', hidden: true },
                    { headerText: getText('KDM001_8'), template: '<div style="float:right"> ${dayoffDatePyout} </div>', key: 'dayoffDatePyout', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_9'), template: '<div style="float:right"> ${occurredDays} </div>', key: 'occurredDays', dataType: 'Number', width: '80px' },
                    { headerText: getText('KDM001_124'), key: 'payoutTied', dataType: 'string', width: '80px' },
                    { headerText: getText('KDM001_10'), template: '<div style="float:right"> ${dayoffDateSub} </div>', key: 'dayoffDateSub', dataType: 'string', width: '120px' },
                    { headerText: getText('KDM001_11'), template: '<div style="float:right"> ${requiredDays} </div>', key: 'requiredDays', dataType: 'Number', width: '80px' },
                    { headerText: getText('KDM001_124'), key: 'subTied', dataType: 'string', width: '80px' },
                    { headerText: getText('KDM001_12'), template: '<div style="float:right"> ${unUsedDaysInGrid} </div>', key: 'unUsedDaysInGrid', dataType: 'Number', width: '80px' },
                    { headerText: getText('KDM001_13'), template: '<div style="float:right"> ${expriedDaysInGrid} </div>', key: 'expriedDaysInGrid', dataType: 'Number', width: '80px' },
                    { headerText: getText('KDM001_14'), formatter: getLawAtr, key: 'lawAtr', dataType: 'string', width: '100px' },
                    { headerText: '', key: 'link', dataType: 'string', width: '85px', unbound: true, ntsControl: 'ButtonPegSetting' },
                    { headerText: '', key: 'edit', dataType: 'string', width: '55px', unbound: true, ntsControl: 'ButtonCorrection' }
                ],
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 14
                    }
                ],
                ntsControls: [
                    { name: 'ButtonPegSetting', text: getText('KDM001_22'), click: function(value) { self.pegSetting(value) }, controlType: 'Button' },
                    { name: 'ButtonCorrection', text: getText('KDM001_23'), click: function(value) { self.doCorrection(value) }, controlType: 'Button' }
                ]
            });
        }
        
        openNewSubstituteData() {
            let self = this;
            setShared('KDM001_D_PARAMS', {selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID});
            
            modal("/view/kdm/001/d/index.xhtml").onClosed(function() {
                let params = getShared('KDM001_A_PARAMS');
                
                if (params.isSuccess) {
                    self.updateDataList();
                }
                
                $('#compositePayOutSubMngDataGrid').focus();
            });
        }
        
        goToKDR004() {
            let self = this;
            setShared('KDM001_PARAMS', {selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID, dateRange: self.dateValue(),
                    selectedPeriodItem: self.selectedPeriodItem(), dispTotalRemain: self.dispTotalRemain(), expirationDate: self.expirationDate()});
            nts.uk.request.jump("/view/kdr/004/a/index.xhtml");
        }
        
        clickGetDataList() {
            let self = this;
            
            $(".ntsDatepicker").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                self.updateDataList();
                $('#compositePayOutSubMngDataGrid').focus();
            }
        }
        
        updateDataList() {
            let self = this;
            let empId = self.selectedItem();
            let isPeriod = self.selectedPeriodItem() == 0 ? false : true;
            let startDate = isPeriod ? moment.utc(self.dateValue().startDate, 'YYYY/MM/DD').format('YYYY-MM-DD') : null;
            let endDate = isPeriod ? moment.utc(self.dateValue().endDate, 'YYYY/MM/DD').format('YYYY-MM-DD') : null;
            
            service.getFurikyuMngDataExtraction(empId, startDate, endDate, isPeriod).done(function(res: any) {
                let arrayResponse = res.compositePayOutSubMngData;
                let compositePayOutSubMngDataArray: Array<CompositePayOutSubMngData> = [];
                for (var i = 0 ; i < arrayResponse.length; i++){
                    compositePayOutSubMngDataArray.push(new CompositePayOutSubMngData(arrayResponse[i]));
                }
                
                // update data to view
                self.compositePayOutSubMngData = ko.observableArray(compositePayOutSubMngDataArray);
                self.dispTotalRemain(res.numberOfDayLeft);
                self.expirationDate(self.getExpirationTime(res.expirationDate));
                self.closureID = res.closureID;
                
                if(arrayResponse.length == 0) {
                    dialog.alertError({ messageId: "Msg_725" });
                }
                
                // update grid
                $("#compositePayOutSubMngDataGrid").igGrid("dataSourceObject", self.compositePayOutSubMngData()).igGrid("dataBind");
                
                // disable edit button
                _.forEach(self.compositePayOutSubMngData(), function(value) {
                    if (value.isLinked){
                        let rowId = value.id;
                        $("#compositePayOutSubMngDataGrid").ntsGrid("disableNtsControlAt", rowId, 'edit', 'Button');
                    }
                });
            }).fail(function(res: any) {
                console.log(res);
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            
            service.getInfoEmLogin().done(function(emp) {
                service.getWpName().done(function(wp) {
                    self.selectedEmployeeObject = {employeeId: emp.sid, employeeCode: emp.employeeCode, employeeName: emp.employeeName, 
                            workplaceId: wp.workplaceId, workplaceCode: wp.code, workplaceName: wp.name};
                    self.employeeInputList.push(new EmployeeKcp009(emp.sid,
                            emp.employeeCode, emp.employeeName, wp.name, wp.name));
                    self.initKCP009();
                });
            });

            dfd.resolve();
            return dfd.promise();
        }
        
        initKCP009() {
            let self = this;
            //_______KCP009_______
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeInputList([]);
            _.each(dataList, function(item) {
                self.employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            if (dataList.length == 0) {
                self.selectedItem('');
            } else {
                let item = self.findIdSelected(dataList, self.selectedItem());
                let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                if (item == undefined) self.selectedItem(sortByEmployeeCode[0].employeeId);
            }
            
            __viewContext.viewModel.viewmodelB.screenItem().employeeInputList(ko.toJS(self.employeeInputList));
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function(obj) {
                return obj.employeeId == selectedItem;
            })
        }
        
        pegSetting(value) {
            let self = this;
            let selectedRowData = value;
            setShared('KDM001_EFGH_PARAMS', {rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID});
            
            if (value.dayoffDatePyout.length == 0) {
                modal("/view/kdm/001/e/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                
                    if (params.isSuccess) {
                        self.updateDataList();
                    }
                });
            } else {
                modal("/view/kdm/001/f/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                
                    if (params.isSuccess) {
                        self.updateDataList();
                    }
                });
            }
        }
        
        doCorrection(value) {
            let self = this;
            let selectedRowData = value;
            setShared('KDM001_EFGH_PARAMS', {rowValue: value, selectedEmployee: self.selectedEmployeeObject, closureId: self.closureID});
            
            if (value.dayoffDatePyout.length > 0) {
                modal("/view/kdm/001/g/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                
                    if (params.isSuccess) {
                        self.updateDataList();
                    }
                });
            } else {
                modal("/view/kdm/001/h/index.xhtml").onClosed(function() {
                    let params = getShared('KDM001_A_PARAMS');
                
                    if (params.isSuccess) {
                        self.updateDataList();
                    }
                });
            }
        }
        
        getExpirationTime(value) {
            if(value == 0) {
                return "当月";
            } else if(value == 1) {
                return "法定外休日";
            } else if(value == 2) {
                return "年度末クリア";
            } else if(value == 3) {
                return "1ヶ月";
            } else if(value == 4) {
                return "2ヶ月";
            } else if(value == 5) {
                return "3ヶ月";
            } else if(value == 6) {
                return "4ヶ月";
            } else if(value == 7) {
                return "5ヶ月";
            } else if(value == 8) {
                return "6ヶ月";
            } else if(value == 9) {
                return "7ヶ月";
            } else if(value == 10) {
                return "8ヶ月";
            } else if(value == 11) {
                return "9ヶ月";
            } else if(value == 12) {
                return "10ヶ月";
            } else if(value == 13) {
                return "11ヶ月";
            } else if(value == 14) {
                return "1年";
            } else if(value == 15) {
                return "2年";
            } else if(value == 16) {
                return "3年";
            } else if(value == 17) {
                return "4年";
            } else if(value == 18) {
                return "5年";
            } else {
                return "";
            }
        }
    }
    
    export class ItemModel {
        value: number;
        name: string;
        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    //__________KCP009_________
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeKcp009>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }

    export class EmployeeKcp009 {
        id: string;
        code: string;
        businessName: string;
        workplaceName: string;
        depName: string;
        constructor(id: string, code: string, businessName: string, workplaceName: string, depName: string) {
            this.id = id;
            this.code = code;
            this.businessName = businessName;
            this.workplaceName = workplaceName;
            this.depName = depName;
        }
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

    //Interfaces
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab: boolean; // クイック検索
        showAdvancedSearchTab: boolean; // 詳細検索
        showBaseDate: boolean; // 基準日利用
        showClosure: boolean; // 就業締め日利用
        showAllClosure: boolean; // 全締め表示
        showPeriod: boolean; // 対象期間利用
        periodFormatYM: boolean; // 対象期間精度
        isInDialog?: boolean;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee: boolean; // 参照可能な社員すべて
        showOnlyMe: boolean; // 自分だけ
        showSameWorkplace: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment: boolean; // 雇用条件
        showWorkplace: boolean; // 職場条件
        showClassification: boolean; // 分類条件
        showJobTitle: boolean; // 職位条件
        showWorktype: boolean; // 勤種条件
        isMutipleCheck: boolean; // 選択モード

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }
    
    export class CompositePayOutSubMngData {
        id: string;
        payoutId: string;
        cID: string;
        sID: string;
        unknownDatePayout: boolean;
        dayoffDatePyout: string;
        expiredDate: string;
        lawAtr: number;
        occurredDays: number;
        unUsedDays: number;
        stateAtr: number;
        payoutTied: boolean;
        subOfHDID: string;
        unknownDateSub: boolean;
        dayoffDateSub: string;
        requiredDays: number;
        remainDays: number;
        subTied: boolean;
        
        //add to fill grid A4_2_5
        unUsedDaysInGrid: number;
        
        //add to fill grid A4_2_6
        expriedDaysInGrid: number;
        
        //add to check enable button
        isLinked: boolean;
        
        constructor(params) {
            this.payoutId = params.payoutId;
            this.cID = params.cid;
            this.sID = params.sid;
            this.unknownDatePayout = params.unknownDatePayout;
            this.dayoffDatePyout = params.unknownDatePayout ? params.dayoffDatePyout + "※" : params.dayoffDatePyout;
            this.expiredDate = params.expiredDate;
            this.lawAtr = params.lawAtr;
            this.occurredDays = params.occurredDays;
            this.unUsedDays = params.unUsedDays;
            this.stateAtr = params.stateAtr;
            this.payoutTied = params.payoutTied ? "有" : "";
            this.subOfHDID = params.subOfHDID;
            this.unknownDateSub = params.unknownDateSub;
            this.dayoffDateSub = params.unknownDateSub ? params.dayoffDateSub + "※" : params.dayoffDateSub;
            this.requiredDays = params.requiredDays;
            this.remainDays = params.remainDays;
            this.subTied = params.subTied ? "有" : "";
            
            if ((params.payoutId != null) && (params.payoutId != "")) {
                this.id = params.payoutId;
                
                if (moment.utc(params.expiredDate, 'YYYY/MM/DD').diff(moment.utc()) > 0) {
                    this.unUsedDaysInGrid = params.unUsedDays;
                    this.expriedDaysInGrid = "";
                } else {
                    this.unUsedDaysInGrid = "";
                    this.expriedDaysInGrid = params.unUsedDays;
                }
            } else if ((params.subOfHDID != null) && (params.subOfHDID != "")) {
                this.id = params.subOfHDID;
                
                this.unUsedDaysInGrid = params.remainDays * (-1);
                this.expriedDaysInGrid = "";
            }
            
            this.isLinked = (params.payoutTied || params.subTied) ? true : false;
        }
    }
    
    function getLawAtr(value, row) {
        if (value && value === '0') {
            return '法定内休日';
        } else if (value && value === '1') {
            return '法定外休日';
        } else if (value && value === '2') {
            return '祝日';
        } else {
            return '';
        }
    }
}

