module nts.uk.at.view.kmr005.a.viewmodel {
    import service = nts.uk.at.view.kmr005.a.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        
        // employee select
        ccgcomponent: GroupOption;
        lstSearchEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
        
        // employee
        lstPersonComponentOption: any;
        selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray([__viewContext.user.employeeCode]);
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        
        // period
        dateValue: KnockoutObservable<any> = ko.observable({});
        
        // title
        title: KnockoutObservable<string> = ko.observable('');
        
        // enum
        selectedOrdered: any = ko.observable(0);

		param: any = null;
		displayBackBtn: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor() {
            let self = this;
			if(nts.uk.request.location.current.isFromMenu) {
				sessionStorage.removeItem('nts.uk.request.STORAGE_KEY_TRANSFER_DATA');	
				self.displayBackBtn(false);
			} else {
				if(__viewContext.transferred.value) {
					self.param = __viewContext.transferred.value;
					if(self.param.employeeID) {
						self.displayBackBtn(true);	
					}
				}	
			}
            self.dateValue({
                startDate: moment().utc().format("YYYY/MM/DD"),
                endDate: moment().utc().format("YYYY/MM/DD")
            });
            self.dateValue.valueHasMutated();
            
            self.ccgcomponent = {
                /** Common properties */
                systemType: 2, // システム区分
                showEmployeeSelection: false, // 検索タイプ
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: false, // 基準日利用
                showClosure: true, // 就業締め日利用
                showAllClosure: true, // 全締め表示
                showPeriod: true, // 対象期間利用
                periodFormatYM: false, // 対象期間精度

                /** Required parameter */
                baseDate: moment().utc().format("YYYY-MM-DD"), // 基準日
                dateRangePickerValue: self.dateValue,
                inService: true, // 在職区分
                leaveOfAbsence: true, // 休職区分
                closed: true, // 休業区分
                retirement: true, // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: true, // 参照可能な社員すべて
                showOnlyMe: true, // 自分だけ
                showSameWorkplace: true, // 同じ職場の社員
                showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: true, // 雇用条件
                showWorkplace: true, // 職場条件
                showClassification: true, // 分類条件
                showJobTitle: true, // 職位条件
                showWorktype: true, // 勤種条件
                isMutipleCheck: true, // 選択モード

                /** Return data */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.lstSearchEmployee(data.listEmployee);
                    self.dateValue().startDate = moment(data.periodStart).format("YYYY/MM/DD");
                    self.dateValue().endDate = moment(data.periodEnd).format("YYYY/MM/DD");
                    self.dateValue.valueHasMutated();
                    self.applyKCP005ContentSearch(data.listEmployee);
                }
            }
            
            nts.uk.characteristics.restore("exportTitle").done((data) => {
                self.title(_.isEmpty(data) ? "" : data);            
            });
            
            nts.uk.characteristics.restore("ordered").done((data) => {
                self.selectedOrdered(_.isNumber(data) ? data : 0);            
            });
            
            self.title.subscribe(value => {
                nts.uk.characteristics.remove("exportTitle");
                nts.uk.characteristics.save("exportTitle", value);
            });
            
            self.selectedOrdered.subscribe(value => {
                nts.uk.characteristics.remove("ordered");
                nts.uk.characteristics.save("ordered", value);
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();   
            nts.uk.ui.block.invisible();
			let param: any = {
				employeeID: null,
				date: null
			};
			if(self.param) {
				if(self.param.employeeID) {
					param.employeeID = self.param.employeeID;
				}
				if(self.param.yearMonth) {
					param.date = moment(self.param.yearMonth).format("YYYY/MM/DD");
				}
			}
            service.startup(param).done((data) => {
				let a: any = [];
				if(self.param && self.param.employeeID) {
					a = [{
						affiliationName: data.affiliationName,
						code: data.code,
						id: data.id,
						isAlreadySetting: false,
						name: data.name
					}];
					if(self.param.yearMonth) {
						self.dateValue().startDate = moment(self.param.yearMonth).startOf('month').format("YYYY/MM/DD");
                		self.dateValue().endDate = moment(self.param.yearMonth).endOf('month').format("YYYY/MM/DD");
                		self.dateValue.valueHasMutated();	
					}
				} else {
					self.dateValue().startDate = data.startDate;
                	self.dateValue().endDate = data.endDate;
                	self.dateValue.valueHasMutated();	
				}
				$('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
					self.employeeList(a);
	                self.lstPersonComponentOption = {
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
		                isShowSelectAllButton: false,
		                maxWidth: 480,
		                maxRows: 15
		            };
	                // Load employee list component
	                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(() => {
	                    $(".caret-right.caret-background.bg-green").removeClass();    
	                });
	                
	            });
                nts.uk.ui.block.clear();
                dfd.resolve(); 
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({messageId : res.messageId}).then(function(){
                    nts.uk.ui.block.clear();
                });        
                dfd.reject();
            });
            
            return dfd.promise(); 
        }
        
        applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeList([]);
            let employeeSearchs: UnitModel[] = [];
            self.selectedEmployeeCode([]);
            for (var employeeSearch of dataList) {
                var employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                    affiliationName: employeeSearch.affiliationName,
                    id: employeeSearch.employeeId
                };
                employeeSearchs.push(employee);
                self.selectedEmployeeCode.push(employee.code);
            }
            self.employeeList(employeeSearchs);
            self.lstPersonComponentOption = {
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
                isShowSelectAllButton: false,
                maxWidth: 480,
                maxRows: 15
            };
        }
        
        exportFile() {
            let self = this;
            $("#exportTitle").trigger("validate");
            if(nts.uk.ui.errors.hasError()) {
                return;    
            }
            if(_.isEmpty(self.selectedEmployeeCode())) {
                dialog.alertError({ messageId : "Msg_1587" }).then(function(){
                    nts.uk.ui.block.clear();
                });
                return;    
            }
            let param = {
                empLst: _(self.employeeList()).filter((o) => _.includes(self.selectedEmployeeCode(), o.code)).map((o) => o.id).value(),
                title: self.title(),
                startDate: self.dateValue().startDate,
                endDate: self.dateValue().endDate,
                ordered: self.selectedOrdered() == 1 ? true : false 
            };
            nts.uk.ui.block.invisible();
			service.checkDataExportFile(param).then(() => {
				return service.exportFile(param);
			}).done((data) => {
                nts.uk.ui.block.clear();
            }).fail((res: any) => {
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){
                    nts.uk.ui.block.clear();
                });   
            });  
        }

		toKMR002() {
			const self = this;
			nts.uk.request.jump("/view/kmr/002/a/index.xhtml");
		}
    }

    // Note: Defining these interfaces are optional
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
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
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員
    
        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
        isTab2Lazy?: boolean;
    
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
    
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        affiliationName?: string;
        isAlreadySetting?: boolean;
        id?: string;
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

