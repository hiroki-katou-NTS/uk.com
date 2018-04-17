module nts.uk.com.view.cmf003.d.viewmodel {
    import model = cmf003.share.model;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;




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

        //Button
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        // setup ccg001
        ccgcomponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        
        //List MULTISELECT
        dataSource: any;
        dataSource2: any;      
        singleSelectedCode: any;
        singleSelectedCode2: any;
        selectedCodes: any;
        selectedCodes2: any;
        headers: any;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            
            
            
            
            
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);



            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' },
                { content: '.step-4' }
            ];
            self.stepSelected = ko.observable({ id: 'step-4', content: '.step-4' });

            self.loadListCondition();

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

            self.loadListOutputItem();
            self.loadListOutputCondition()
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

        loadListOutputItem() {
            let self = this;

            for (let i = 0; i < 20; i++) {
                self.listOutputItem.push(new model.StandardOutputItem('00' + i, 'Test ' + i, '', '', 0));
            }
        }

        loadListOutputCondition() {
            let self = this;

            for (let i = 0; i < 10; i++) {
                self.listOutputCondition.push(new OutputCondition('item ' + i, 'Condition ' + i));
            }
        }

        
    }

    class OutputCondition {
        itemName: string;
        condition: string;
        constructor(itemName: string, condition: string) {
            this.itemName = itemName;
            this.condition = condition;
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

       
    }



}