module nts.uk.com.view.cmf002.o.viewmodel {
    import model = cmf002.share.model;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listCondition: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedConditionCd: KnockoutObservable<string> = ko.observable('');
        selectedConditionName: KnockoutObservable<string> = ko.observable('');

        periodDateValue: KnockoutObservable<any> = ko.observable({});

        listOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<string> = ko.observable('');

        listOutputCondition: KnockoutObservableArray<OutputCondition> = ko.observableArray([]);
        selectedOutputConditionItem: KnockoutObservable<string> = ko.observable('');
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
        // setup ccg001
        ccgcomponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        //set up kcp 005
        lstSearchEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        selectedEmployeeCode: KnockoutObservableArray<string>;
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
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' },
                { content: '.step-4' }
            ];
            self.stepSelected = ko.observable({ id: 'step-4', content: '.step-4' });
            self.alreadySettingPersonal = ko.observableArray([]);
            self.loadListCondition();
            self.selectedEmployeeCode = ko.observableArray([]);
            self.selectedConditionCd.subscribe(function(data: any) {
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: model.ItemModel) => x.code == data);
                    self.selectedConditionName(item.name);
                }
                else {
                    self.selectedConditionName('');
                }
            });
            self.baseDate = ko.observable(new Date());
            self.selectedEmployee = ko.observableArray([]);

            //set up kcp 005
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                { code: '1', isAlreadySetting: true },
                { code: '2', isAlreadySetting: true }
            ]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(true);
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
                maxRows: 12
            };

        }
        /**
       * apply ccg001 search data to kcp005
       */
        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            let self = this;
            let employeeSearchs: UnitModel[] = [];
            for (let employeeSearch of dataList) {
                let employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                    workplaceName: employeeSearch.workplaceName,
                    isAlreadySetting: false
                };
                employeeSearchs.push(employee);
            }
            self.employeeList(employeeSearchs);
          

        }

        selectStandardMode() {
            $('#ex_output_wizard').ntsWizard("next");
        }

        next() {
            let self = this;
            $('#ex_output_wizard').ntsWizard("next");
        }
        previous() {
            $('#ex_output_wizard').ntsWizard("prev");
        }

        todoScreenQ() {
            let self = this;
            self.loadScreenQ();
            $('#ex_output_wizard').ntsWizard("goto", 2);
        }

        nextToScreenR() {
            let self = this;
            self.next();
            
            service.getExOutSummarySetting("conditionSetCd").done(res => {

                service.getExOutSummarySetting("conditionSetCd").done(function(res: any) {
                    self.listOutputCondition(res.ctgItemDataCustomList);
                    self.listOutputItem(res.ctdOutItemCustomList);
                }).fail(res => {
                    console.log("getExOutSummarySetting fail");
                });
            }
        }
        
        createExOutText() {
            let self = this;
            
            //TODO set command
            let command = new CreateExOutTextCommand();
            service.createExOutText(command).done(res => {
                let params = {
                    storeProcessingId: res,
                };

                setShared("CMF002_R_PARAMS", params);
                nts.uk.ui.windows.sub.modal("/view/cmf/002/s/index.xhtml").onClosed(() => {
                    //TODO
                    //disable nut
                    //$("").focus();
                });
            }).fail(res => {
                console.log("createExOutText fail");
            });
        }

        loadListCondition() {
            let self = this;

            self.listCondition([
                new model.ItemModel(111, 'test a'),
                new model.ItemModel(222, 'test b'),
                new model.ItemModel(333, 'test c'),
                new model.ItemModel(444, 'test d')
            ]);
            self.selectedConditionCd('1');
            self.selectedConditionName('test a');
        }

        loadScreenQ() {
            let self = this;
            self.ccgcomponent = {
                /** Common properties */
                systemType: 2, // システム区分
                showEmployeeSelection: true, // 検索タイプ
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: false, // 基準日利用
                showClosure: false, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: true, // 対象期間利用
                periodFormatYM: false, // 対象期間精度
                /** Required parameter */
                baseDate: moment.utc().toISOString(), // 基準日
                periodStartDate: moment.utc(self.periodDateValue().startDate, "YYYY/MM/DD").toISOString(), // 対象期間開始日
                periodEndDate: moment.utc(self.periodDateValue().endDate, "YYYY/MM/DD").toISOString(), // 対象期間終了日
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



                    self.applyKCP005ContentSearch(data.listEmployee);

                }
            }
            $('#component-items-list').ntsListComponent(self.listComponentOption);
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
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
    class OutputCondition {
        itemName: string;
        condition: string;
        constructor(itemName: string, condition: string) {
            this.itemName = itemName;
            this.condition = condition;
        }
    }

    class CreateExOutTextCommand {
        conditionSetCd: string;
        userId: string;
        startDate: string;
        endDate: string;
        referenceDate: string;
        standardType: boolean;
        sidList: Array<string>;
        
        constructor(conditionSetCd: string, userId: string, startDate: string, endDate: string
                , referenceDate: string, standardType: boolean, sidList: Array<string>) {
            this.conditionSetCd = conditionSetCd;
            this.userId = userId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.referenceDate = referenceDate;
            this.standardType = standardType;
            this.sidList = sidList;
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
        // showDepartment: boolean; // 部門条件 not covered
        // showDelivery: boolean; not covered
        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

}