/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr003.a {

    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import validation = nts.uk.ui.validation;

    const API = {
        SETTING: 'at/record/stamp/management/personal/startPage',
        HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
    };

    @bean()
    export class KMR003AViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<any> = ko.observableArray([]);
        stampToSuppress: KnockoutObservable<any> = ko.observable({});
        dateData: KnockoutObservable<string>;

        //A2_5 A2_6
        itemsConditionClassification: KnockoutObservableArray<ExtractConditionClassfication>;
        conditionClassification: KnockoutObservable<ExtractConditionClassfication> = ko.observable();

        //A2_7 A2_8
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        ccg001ComponentOption: GroupOption = null;

        fixedColumns = [
            {headerText: "ID", key: 'sid', dataType: 'string', hidden: true},
            {headerText: getText("KMR003_21"), key: 'employeeCode', dataType: 'string', width: '150px', height: '', ntsControl: "Label"},
            {headerText: getText("KMR003_22"), key: 'employeeName', dataType: 'string', width: '150px', ntsControl: "Label"},
            {headerText: getText("KMR003_23"), group: [
                    {headerText: '', key: 'isDel', dataType: 'boolean', width: '100px', checkbox: true, ntsControl: "isDelCheckBox"}
                ]
            },
            {headerText: getText("KMR003_24"), key: 'timeOrder', dataType: 'string', width: '100px', ntsControl: "Label"},
            {headerText: getText("KMR003_25"),
                group: [
                    {headerText: '', key: 'isOrder', dataType: 'boolean', width: '100px', checkbox: true, ntsControl: "isOrderCheckBox"}
                ]
            },
        ];

        dynamicColumns = [];
        flag: KnockoutObservable<boolean>;

        datas: Array<BookingModify> = [
            {
                id: 1, employeeCode: "A001", employeeName: "emp 1", timeOrder: "8:30", lstStatus: [
                    {bentoID: 1, amount: 1},
                    {bentoID: 3, amount: 1},
                    {bentoID: 4, amount: 1},
                    {bentoID: 5, amount: 1},
                    {bentoID: 6, amount: 1},
                    {bentoID: 13, amount: 1},
                    {bentoID: 19, amount: 1},
                    {bentoID: 29, amount: 1}
                ]
            },
            {
                id: 2, employeeCode: "A002", employeeName: "emp 2", timeOrder: "8:30", lstStatus: [
                    {bentoID: 2, amount: 1},
                    {bentoID: 5, amount: 1},
                    {bentoID: 6, amount: 1},
                    {bentoID: 9, amount: 1},
                    {bentoID: 11, amount: 1},
                    {bentoID: 18, amount: 1},
                    {bentoID: 25, amount: 1},
                    {bentoID: 37, amount: 1}
                ]
            },
            {
                id: 3, employeeCode: "A003", employeeName: "emp 2", timeOrder: "8:30", lstStatus: [
                    {bentoID: 3, amount: 1},
                    {bentoID: 6, amount: 1},
                    {bentoID: 9, amount: 1},
                    {bentoID: 12, amount: 1},
                    {bentoID: 15, amount: 1},
                    {bentoID: 19, amount: 1},
                    {bentoID: 22, amount: 1},
                    {bentoID: 33, amount: 1}
                ]
            }
        ];
        dataConverted = [
            {id: 1, employeeCode: "A001", employeeName: "emp 1", timeOrder: "8:30", bento1: 1, bento3: 1, bento4: 1, bento5: 1,
                bento6: 1, bento13: 1, bento19: 1, bento29: 1},
            {id: 2, employeeCode: "A002", employeeName: "emp 2", timeOrder: "8:30", bento2: 1, bento5: 1, bento6: 1,
                bento9: 1, bento11: 1, bento18: 1, bento25: 1, bento37: 1},
            {id: 3, employeeCode: "A003", employeeName: "emp 2", timeOrder: "8:30", bento3: 1, bento6: 1, bento9: 1,
                bento12: 1, bento15: 1, bento19: 1, bento22: 1, bento33: 1}
        ];
        listBento = [];
        //KnockoutObservableArray<BentoDto>;

        empSearchItems: Array<EmployeeSearchDto>;
        employIdLogin: any;

        constructor() {
            super();
            let vm = this;
            vm.dateData = ko.observable('20000101');

            vm.itemsConditionClassification = ko.observableArray([
                new ExtractConditionClassfication(1, 'option 1'),
                new ExtractConditionClassfication(2, 'option 2'),
                new ExtractConditionClassfication(3, 'option 3')
            ]);

            //add A2_8 A2_9
            vm.roundingRules = ko.observableArray([
                {code: '1', name: '昼'},
                {code: '2', name: '夜'}
            ]);
            vm.selectedRuleCode = ko.observable(1);

            // bentos: Array<BentoDto>
            for (let i = 1; i < 41; ++i) {
                this.listBento = this.listBento.concat([new BentoDto(i, "bento " + i)]);
            }

            // this.listBento.forEach(function(x){
            //     this.dynamicColumns = this.dynamicColumns.concat(
            //         [{headerText: x.bentoName, key: "bento"+String(x.bentoID), dataType: 'string', width: '100px', ntsControl: "Label"}]
            //     );
            // })
            for (let x of this.listBento) {
                this.dynamicColumns = this.dynamicColumns.concat(
                    [{
                        headerText: x.bentoName,
                        key: "bento" + String(x.bentoID),
                        dataType: 'string',
                        width: '100px',
                        ntsControl: "Label"
                    }]
                );
            }

            // for(let x of this.datas){
            //     var ans = {};
            //     x.lstStatus.forEach(function (item) {
            //         ans = $.extend(true, ans, new BentoTransfer("bento"+String(item.bentoID), item.amount))
            //     })
            //     this.dataConverted = this.dataConverted.concat([ans]);
            // }

            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 3,
                showEmployeeSelection: true,
                showQuickSearchTab: false,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: null,
                showAllClosure: null,
                showPeriod: null,
                periodFormatYM: null,

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: null,
                showOnlyMe: null,
                showSameWorkplace: null,
                showSameWorkplaceAndChild: null,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: true,
                showWorkplace: false,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,
                tabindex: 6,
                showOnStart: true,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    vm.empSearchItems = data.listEmployee;
                    vm.initData();
                }
            }
        }

        created() {
            const vm = this;

            vm.$blockui('show')
            //.then(() => vm.$ajax('at', API.SETTING))
            //.then($('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption))
                .then((data: any) => {
                    if (data) {
                        if (data.stampSetting) {
                            vm.tabs(data.stampSetting.pageLayouts);
                        }
                        if (data.stampToSuppress) {
                            vm.stampToSuppress(data.stampToSuppress);
                        }
                    }
                })
                .fail((res) => {
                    vm.$dialog.error({messageId: res.messageId})
                        .then(() => vm.$jump("com", "/view/ccg/008/a/index.xhtml"));
                })
                .always(() => vm.$blockui('clear'));
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            vm.loadMGrid();
            _.extend(window, {vm});
        }

        loadMGrid() {
            let self = this;
            let height = $(window).height() - 90 - 290;
            let width = $(window).width() + 20 - 1170;
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '60px',
                dataSource: self.dataConverted,
                primaryKey: 'sid',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                errorsOnPage: false,
                columns: this.fixedColumns.concat(this.dynamicColumns),
                ntsControls: [
                    {
                        name: 'isDelCheckBox', options: {value: 1, text: ''}, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: false,
                    },
                    {
                        name: 'isOrderCheckBox', options: {value: 1, text: ''}, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                    }
                ],
                features: [
                    {
                        name: "ColumnFixing",
                        showFixButtons: false,
                        fixingDirection: 'left',
                        columnSettings: [
                            {
                                columnKey: "employeeCode",
                                isFixed: true
                            },
                            {
                                columnKey: "employeeName",
                                isFixed: true
                            }
                        ]
                    }
                ]
            }).create();
            //self.setPageStatus();
        }

        initData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            //$("#A2_3").ntsError('check');
            if (nts.uk.ui.errors.hasError()) {
                $("#grid").mGrid("destroy");
                self.datas = [];
                self.loadMGrid();
                block.clear();
                return;
            }
            let param = self.createParamGet();
            let gridData = [];

            $.when(gridData).done((depts, amounts) => {
                self.datas = gridData;
                $("#grid").mGrid("destroy");
                self.loadMGrid();
            }).always(() => {
                //$("#A2_3").focus();.
                block.clear();
                dfd.resolve();
            })
            return dfd.promise();
        }

        createParamGet() {
            let self = this;
            let listSId = _.map(self.empSearchItems, (item: EmployeeSearchDto) => {
                return item.employeeId;
            });
            let param = {
                listSId: listSId
            };
            return param;
        }

    }

    class ExtractConditionClassfication {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            this.id = id;
            this.name = name;
        }
    }

    interface GroupOption {
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

    interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        hourBooking: string;
        lstBooking: Array<listBooking>;
    }

    interface listBooking {
        bentoID: number
        amount: number;
    }

    class BookingModify {
        id: number;
        employeeCode: string;
        employeeName: string;
        timeOrder: string;
        lstStatus: Array<listBooking>;

        constructor(id: number, employeeCode: string, employeeName: string, timeOrder: string, lstStatus: Array<listBooking>) {
            this.id = id;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.timeOrder = timeOrder;
            this.lstStatus = lstStatus;
        }
    }

    class BentoDto {
        bentoID: number;
        bentoName: string;

        constructor(bentoID: number, bentoName: string) {
            this.bentoID = bentoID;
            this.bentoName = bentoName;
        }
    }

    class BentoTransfer {
        bentoKey: string;
        bentoValue: number;

        constructor(bentoKey: string, bentoValue: number) {
            this.bentoKey = bentoKey;
            this.bentoValue = bentoValue;
        }
    }


    /**
     * 住民税入力区分
     */
    enum ResidentTaxInputAtr {
        ALL_MONTH = 1,
        NOT_ALL_MONTH = 0
    }

    declare interface ObjectConstructor {
        assign(...objects: Object[]): Object;
    }

}
