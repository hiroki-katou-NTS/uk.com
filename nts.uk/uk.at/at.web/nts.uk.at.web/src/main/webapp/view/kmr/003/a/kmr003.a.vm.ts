/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr003.a {

    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import validation = nts.uk.ui.validation;

    const API = {
        BENTO_RESERVATTIONS: 'screen/at/record/reservation/bento_modify/getReservations'
    };

    @bean()
    export class KMR003AViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<any> = ko.observableArray([]);
        stampToSuppress: KnockoutObservable<any> = ko.observable({});
        date: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());

        //A2_5 A2_6
        searchConditions: KnockoutObservableArray<SearchCondition> = ko.observableArray();
        searchConditionValue: KnockoutObservable<number> = ko.observable(4);

        //A2_7 A2_8
        closingTimeFrames: KnockoutObservableArray<ClosingTimeFrame> = ko.observableArray();
        closingTimeFrameValue: KnockoutObservable<number> = ko.observable(1);

        ccg001ComponentOption: GroupOption = null;

        fixedColumns = [
            { headerText: "reservationCardNo", key: 'reservationCardNo', dataType: 'string', hidden: true },
            { headerText: getText("KMR003_21"), key: 'reservationMemberCode', dataType: 'string', width: '150px', height: '', ntsControl: "Label" },
            { headerText: getText("KMR003_22"), key: 'reservationMemberName', dataType: 'string', width: '150px', ntsControl: "Label" },
            {
                headerText: getText("KMR003_23"), group: [
                    { headerText: '', key: 'isDelete', dataType: 'boolean', width: '100px', checkbox: true, ntsControl: "isDelCheckBox" }
                ]
            },
            { headerText: getText("KMR003_24"), key: 'closingTimeFrame', dataType: 'string', width: '100px', ntsControl: "Label" },
            {
                headerText: getText("KMR003_25"),
                group: [
                    { headerText: '', key: 'ordered', dataType: 'boolean', width: '100px', checkbox: true, ntsControl: "isOrderCheckBox" }
                ]
            },
        ];

        dynamicColumns = [];
        flag: KnockoutObservable<boolean>;

        datas: Array<ReservationModifyEmployeeDto> = [];
        // dataConverted = [
        //     {
        //         id: 1, employeeCode: "A001", employeeName: "emp 1", timeOrder: "8:30", bento1: 1, bento3: 1, bento4: 1, bento5: 1,
        //         bento6: 1, bento13: 1, bento19: 1, bento29: 1
        //     },
        //     {
        //         id: 2, employeeCode: "A002", employeeName: "emp 2", timeOrder: "8:30", bento2: 1, bento5: 1, bento6: 1,
        //         bento9: 1, bento11: 1, bento18: 1, bento25: 1, bento37: 1
        //     },
        //     {
        //         id: 3, employeeCode: "A003", employeeName: "emp 2", timeOrder: "8:30", bento3: 1, bento6: 1, bento9: 1,
        //         bento12: 1, bento15: 1, bento19: 1, bento22: 1, bento33: 1
        //     }
        // ];
        listBento = [];
        //KnockoutObservableArray<BentomenuJoinBentoDto>;

        empSearchItems: Array<EmployeeSearchDto> = [];
        employIdLogin: any;

        constructor() {
            super();
            let vm = this;

            vm.searchConditions.push( new SearchCondition(4, '全部'));
            vm.searchConditions.push( new SearchCondition(0, '１商品２件以上'));
            vm.searchConditions.push( new SearchCondition(1, '注文済み'));
            vm.searchConditions.push( new SearchCondition(2, '未注文'));
            vm.searchConditions.push(  new SearchCondition(3, '新規注文'));

           

            //vm.closingTimeFrame = ko.observable(1);

            // bentos: Array<BentomenuJoinBentoDto>
            for (let i = 1; i < 41; ++i) {
                //this.listBento = this.listBento.concat([new BentomenuJoinBentoDto(i, "bento " + i)]);
            }

            // this.listBento.forEach(function(x){
            //     this.dynamicColumns = this.dynamicColumns.concat(
            //         [{headerText: x.bentoName, key: "bento"+String(x.bentoID), dataType: 'string', width: '100px', ntsControl: "Label"}]
            //     );
            // })
            // for (let x of this.listBento) {
            //     this.dynamicColumns = this.dynamicColumns.concat(
            //         [{
            //             headerText: x.bentoName,
            //             key: "bento" + String(x.bentoID),
            //             dataType: 'string',
            //             width: '100px',
            //             ntsControl: "Label"
            //         }]
            //     );
            // }

            // for(let x of this.datas){
            //     var ans = {};
            //     x.lstStatus.forEach(function (item) {
            //         ans = $.extend(true, ans, new BentoTransfer("bento"+String(item.bentoID), item.amount))
            //     })
            //     this.dataConverted = this.dataConverted.concat([ans]);
            // }

            vm.ccg001ComponentOption = <GroupOption>{
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: null,

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: false,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: false,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: null,
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

            vm.closingTimeFrameValue.subscribe(value => {
                console.log(value)
            })
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            vm.loadMGrid();
            vm.initData();
            _.extend(window, { vm });
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
                dataSource: self.datas,
                primaryKey: 'reservationCardNo',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                errorsOnPage: false,
                columns: self.fixedColumns.concat(self.dynamicColumns),
                ntsControls: [
                    {
                        name: 'isDelCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: false,
                    },
                    {
                        name: 'isOrderCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
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
                                columnKey: "reservationMemberCode",
                                isFixed: true
                            },
                            {
                                columnKey: "reservationMemberName",
                                isFixed: true
                            }
                        ]
                    }
                ]
            }).create();
            //self.setPageStatus();
        }

        initData(): JQueryPromise<any>{
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            let param = self.createParamGet();
            self.$ajax(API.BENTO_RESERVATTIONS, param).done((res: IReservationModifyDto) => {
                self.closingTimeFrames.removeAll();
                _.forEach(res.bentoClosingTimes, (item : IClosingTimeDto) => {
                    //add A2_8 A2_9 A2_10
                    self.closingTimeFrames.push(new ClosingTimeFrame(item));
                })
                self.dynamicColumns = [];
                _.forEach(res.bentos, (item: IHeaderInfoDto) => {
                    self.dynamicColumns.push({
                        headerText: item.bentoName,
                        key: "bento" + String(item.frameNo),
                        dataType: 'string',
                        width: '100px',
                        ntsControl: "Label"
                    });
                })
                self.datas = _.map(res.reservationModifyEmps, (item: IReservationModifyEmployeeDto) => {
                    let dto = new ReservationModifyEmployeeDto(item);
                    dto.convertData();
                    return dto;
                });
                $("#grid").mGrid("destroy");
                self.loadMGrid();
            }).always(() => {
                block.clear();
                dfd.resolve();
            })
            return dfd.promise();
        }

        createParamGet() {
            let self = this;
            let empIds = _.map(self.empSearchItems, (item: EmployeeSearchDto) => {
                return item.employeeId;
            });
            let param = {
                empIds: empIds,
                date: self.date(),
                closingTimeFrame: self.closingTimeFrameValue(),
                searchCondition: self.searchConditionValue()
            };
            return param;
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
        affiliationCode: string;
        affiliationId: string;
        affiliationName: string;
    }

    class SearchCondition {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            this.id = id;
            this.name = name;
        }
    }

    class ClosingTimeFrame {
        id: number;
        name: string;
        endTime: number;
        startTime: number;

        constructor(item: IClosingTimeDto) {
            this.id = item.closingTimeFrame;
            this.name = item.name;
            this.startTime = item.startTime;
            this.endTime = item.endTime;
        }
    }

    // 予約の修正起動情報
    class IReservationModifyDto {
        bentos: Array<IHeaderInfoDto>;

        bentoClosingTimes: Array<IClosingTimeDto>;

        empFinishs: Array<IEmployeeInfoMonthFinishDto>;

        reservationModifyEmps: Array<IReservationModifyEmployeeDto>;
    }
    
    // 弁当ヘッダー
    interface IHeaderInfoDto {
        frameNo: number;
        bentoName: string;
        unit: string;
    }

    // 弁当メニューの締め時刻
    interface IClosingTimeDto {
        closingTimeFrame: number;
        name: string;
        endTime: number;
        startTime: number;
    } 
    
    // 月締め処理が済んでいる社員情報
    interface IEmployeeInfoMonthFinishDto {
        employeeCode: string;
        employeeName: string;
    }

    // 社員の予約情報
    interface IReservationModifyEmployeeDto {
        reservationDetails: Array<IReservationModifyDetailDto>;
        reservationCardNo: string;
        reservationMemberId: string;
        reservationMemberCode: string;
        reservationMemberName: string;
        reservationDate: Date;
        reservationTime: number;
        activity: boolean;
        ordered: boolean;
        closingTimeFrame: number;
    }

    // 社員の予約情報
    interface IReservationModifyDetailDto {
        bentoCount: number;
        frameNo: number;
    }


    // 社員の予約情報
    class ReservationModifyEmployeeDto {
        reservationDetails: Array<ReservationModifyDetailDto>;
        reservationCardNo: string;
        reservationMemberId: string;
        reservationMemberCode: string;
        reservationMemberName: string;
        reservationDate: Date;
        reservationTime: number;
        activity: boolean;
        ordered: boolean;
        closingTimeFrame: number;

        isDelete: boolean;

        constructor(item: IReservationModifyEmployeeDto){
            this.reservationDetails = _.map(item.reservationDetails, (x : IReservationModifyDetailDto) => {
                return new ReservationModifyDetailDto(x);
            })

            this.reservationCardNo = item.reservationCardNo;
            this.reservationMemberId = item.reservationMemberId;
            this.reservationMemberCode = item.reservationMemberCode;
            this.reservationMemberName = item.reservationMemberName;
            this.reservationDate = item.reservationDate;
            this.reservationTime = item.reservationTime;
            this.activity = item.activity;
            this.ordered = item.ordered;
            this.closingTimeFrame = item.closingTimeFrame;

            this.isDelete = false;
        }

        convertData(){
            let self = this;
            _.forEach(this.reservationDetails, (item: ReservationModifyDetailDto) => {
                self["bento" + String(item.frameNo)] = item.bentoCount;
            })
        }
    }

    // 社員の予約情報
    class ReservationModifyDetailDto {
        bentoCount: number;
        frameNo: number;
        constructor(item: IReservationModifyDetailDto){
            this.bentoCount = item.bentoCount;
            this.frameNo = item.frameNo;
        }
    }
}
