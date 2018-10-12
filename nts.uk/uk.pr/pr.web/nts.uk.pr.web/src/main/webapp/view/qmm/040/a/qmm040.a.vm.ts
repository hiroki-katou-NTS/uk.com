module nts.uk.pr.view.qmm040.a.viewmodel {
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        //SalIndAmountName
        salIndAmountNames: KnockoutObservableArray<SalIndAmountName>;
        salIndAmountNamesSelectedCode: KnockoutObservable<string>;

        //ccg001
        referenceDate: KnockoutObservable<string> = ko.observable('');
        ccgcomponent: GroupOption;
        employeeList: KnockoutObservableArray<ItemModel>;
        tilteTable: KnockoutObservableArray<any>;
        selectedEmployeeCode: KnockoutObservable<string>;
        //amount
        amountList: KnockoutObservableArray<Amount>;


        yearMonth: KnockoutObservable<number> = ko.observable(201804);
        onTab: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        individualPriceCode: KnockoutObservable<string>;
        individualPriceName: KnockoutObservable<string>;

        constructor() {
            var self = this;
            $("#A5_9").ntsFixedTable({height: 350, width: 520});
            self.tilteTable = ko.observableArray([
                {headerText: getText('QMM040_8'), key: 'individualPriceCode', width: 100},
                {headerText: getText('QMM040_9'), key: 'individualPriceName', width: 200}

            ]);

            self.salIndAmountNames = ko.observableArray([]);
            self.salIndAmountNamesSelectedCode = ko.observable('');


            self.amountList = ko.observableArray([]);

            self.selectedEmployeeCode = ko.observable('1');
            self.individualPriceCode = ko.observable('');
            self.individualPriceName = ko.observable('');
            self.onSelectTab(self.onTab);

            self.employeeList = ko.observableArray([]);

            self.salIndAmountNamesSelectedCode.subscribe(function (data) {
                let temp = _.find(self.salIndAmountNames(), function (o) {
                    return o.individualPriceCode == data;
                });
                if (temp) {
                    self.individualPriceCode(temp.individualPriceCode);
                    self.individualPriceName(temp.individualPriceName);
                }

            });
            self.showSubstiteDataGrid();
            self.reloadCcg001();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            dfd.resolve(self);
            return dfd.promise();
        }


        onSelectTab(param) {
            let self = this;
            switch (param) {
                case 0:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 1:
                    //TODO
                    self.onTab(1);
                    self.titleTab(getText('QMM040_4'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 2:
                    //TODO
                    self.onTab(2);
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    self.titleTab(getText('QMM040_5'));
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 3:
                    //TODO
                    self.onTab(3);
                    self.titleTab(getText('QMM040_6'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                default:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    $("#sidebar").ntsSideBar("active", 0)
                    break;
            }
        }

        public loadSalIndAmountName(cateIndicator: number): void {
            let self = this;
            service.salIndAmountNameByCateIndicator(cateIndicator).done((data) => {
                if(data){
                    self.salIndAmountNames(data);
                    self.salIndAmountNamesSelectedCode(self.salIndAmountNames()[0].individualPriceCode);
                }

            });
        }


        public reloadCcg001(): void {
            let self = this;

            self.ccgcomponent = {
                /** Common properties */
                systemType: 3, // システム区分
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: true,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment(new Date('06/05/2017')).format("YYYY-MM-DD"), // 基準日
                periodStartDate: moment(new Date('06/05/1990')).format("YYYY-MM-DD"), // 対象期間開始日
                periodEndDate: moment(new Date('06/05/2018')).format("YYYY-MM-DD"), // 対象期間終了日
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: false,
                showSameWorkplace: false,
                showSameWorkplaceAndChild: false,

                /** Advanced search properties */
                showEmployment: false,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,
                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    //self.selectedEmployee(data.listEmployee);
                    if (data) {
                        self.employeeList(data.listEmployee);
                        self.selectedEmployeeCode(self.employeeList()[0].employeeCode);
                    }


                    self.referenceDate(moment.utc(data.baseDate).format("YYYY/MM/DD"));
                }
            }
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
        }


        showSubstiteDataGrid() {
            let self = this;
            $("#substituteDataGrid").ntsGrid({
                height: '520px',
                name: 'Grid name',
                dataSource: self.employeeList(),
                primaryKey: 'id',
                virtualization: true,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: getText('QMM040_16'), key: 'employeeId', dataType: 'string', width: '130px'},
                    {headerText: getText('QMM040_17'), key: 'individualPriceName', dataType: 'string', width: '102px'},
                    {headerText: getText('QMM040_18'), key: 'workplaceID', dataType: 'string', width: '170px'},
                    {headerText: getText('QMM040_19'), key: 'workplaceCode', dataType: 'string', width: '120px'},
                    // <th class="ui-widget-header">#{i18n.getText('QMM040_16')}</th>
                    //     <th class="ui-widget-header">#{i18n.getText('QMM040_17')}</th>
                    //     <th class="ui-widget-header">#{i18n.getText('QMM040_18')}</th>
                    //     <th class="ui-widget-header">#{i18n.getText('QMM040_19')}</th>

                ],
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 20
                    },
                    {
                        name: 'Resizing',
                        columnSettings: [
                            {columnKey: "employeeId", allowResizing: false},
                            {columnKey: "individualPriceName", allowResizing: false},
                            {columnKey: "workplaceID", allowResizing: false},
                            {columnKey: "workplaceCode", allowResizing: false},

                        ],
                    }
                ]
            });
        }


    }


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

    export interface TargetEmployee {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        sid: string;
        scd: string;
        businessname: string;
    }


    export interface IitemModel {
        employeeCode: string,
        employeeId: string,
        employeeName: string,
        workplaceCode: string,
        workplaceId: string,
        workplaceName: string
    }

    export class ItemModel {

        employeeCode: string;
        employeeId: string;
        employeeName: string
        workplaceCode: string
        workplaceId: string
        workplaceName: string;

        constructor(param: IitemModel) {
            this.employeeCode = param.employeeCode;
            this.employeeId = param.employeeId;
            this.employeeName = param.employeeName;
            this.workplaceCode = param.workplaceCode;
            this.workplaceId = param.workplaceId;
            this.workplaceName = param.workplaceName;
        }


    }

    export interface IAmount {
        employeeId: string;
        employeeName: string;
        periodStart: string;
        periodEnd: string;
    }


    export class Amount {
        employeeId: string;
        employeeName: string;
        period: string
        AmountofMoney: KnockoutObservable<number>;

        constructor(param: IAmount) {
            this.employeeId = param.employeeId;
            this.employeeName = param.employeeName;
            this.period = moment(param.periodStart).format("YYYY-MM") + "~" + moment(param.periodEnd).format("YYYY-MM");
            this.AmountofMoney = ko.observable(null);
        }

    }

    export interface ISalIndAmountName {
        individualPriceCode: string,
        individualPriceName: string
    }


    export class SalIndAmountName {
        individualPriceCode: string;
        individualPriceName: string;

        constructor(param: ISalIndAmountName) {
            this.individualPriceCode = param.individualPriceCode;
            this.individualPriceName = param.individualPriceName;
        }
    }


    export enum PerValueCateCls {
        SUPPLY = 0,
        DEDUCTION = 1
    }


     export class ListOfItemsByItem{
         personalValueCode:string;
         personalAmounts:KnockoutObservableArray<PersonalAmount>;
         constructor(personalValueCode:string,personalAmounts:Array<PersonalAmount>){
             this.personalAmounts=ko.observableArray([]);
             this.personalAmounts=ko.observableArray(personalAmounts);
             this.personalValueCode=personalValueCode;
         }
     }

    export class PersonalAmount{
        historyID:string;
        period:string;
        amount:number;
        businessName:string;
        employeecode:string;

        constructor(){
            // this.historyID=
        }

    }


}