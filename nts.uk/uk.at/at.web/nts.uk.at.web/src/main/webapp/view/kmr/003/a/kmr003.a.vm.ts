/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr003.a {

    import validation = nts.uk.ui.validation;
    import parseTime = nts.uk.time.parseTime;

    const API = {
        BENTO_RESERVATTIONS: 'screen/at/record/reservation/bento_modify/getReservations',
        BENTO_UPDATE: 'at/record/reservation/bento/force-update',
        BENTO_DELETE: 'at/record/reservation/bento/force-delete'
    };

    const PATH = {
        KMR003_B: '/view/kmr/003/b/index.xhtml'
    };

    @bean()
    export class KMR003AViewModel extends ko.ViewModel {
        date: KnockoutObservable<string> = ko.observable(this.$date.now().toISOString().substr(0, 10) + 'T00:00:00.000Z');

        //A2_5 A2_6
        searchConditions: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.BentoReservationSearchConditionDto);
        searchConditionValue: KnockoutObservable<number> = ko.observable(4);

        //A2_7 A2_8
        closingTimeFrames: KnockoutObservableArray<ClosingTimeFrame> = ko.observableArray();
        closingTimeFrameValue: KnockoutObservable<number> = ko.observable(1);
        closingTimeTime: KnockoutObservable<any> = ko.observable();

        ccg001ComponentOption: GroupOption = null;

        dynamicColumns = [];
        datas: Array<ReservationModifyEmployeeDto> = [];
        hasData: KnockoutObservable<boolean> = ko.observable(false);
        listBento = [];
        headerInfos: Array<HeaderInfoDto> = [];
        empSearchItems: Array<EmployeeSearchDto> = [];

        canDelete: KnockoutObservable<boolean> = ko.observable(false);
        deleteItems: Array<string> = [];

        constructor() {
            super();
            const vm = this;
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
                baseDate: vm.$date.now().toISOString(),
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
        }

        created() {
            const vm = this;
            $('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption);
            vm.loadMGrid();
            vm.initData();

            vm.date.subscribe((value: string) => {
                let momentDate = moment(value);
                if (momentDate instanceof moment && !momentDate.isValid()) {
                    return;
                }

                vm.initData();
            })
            vm.searchConditionValue.subscribe(() => {
                vm.initData();
            })
            vm.closingTimeFrameValue.subscribe(value => {
                vm.setClosingTimeTime(value);
                vm.initData();
            })

            _.extend(window, { vm });
        }

        isNewMode() {
            const vm = this;
            return vm.searchConditionValue() == 3
        }

        getFixedColumns(): Array<any> {
            const vm = this;

            let isNewMode = vm.isNewMode();
            var fixedColumns = [];
            fixedColumns.push({ headerText: "key", key: 'key', dataType: 'string', hidden: true });
            fixedColumns.push({
                headerText: vm.$i18n("KMR003_21"), key: 'reservationMemberCode', dataType: 'number', width: '115px', ntsControl: "Label"
            });
            fixedColumns.push({ headerText: vm.$i18n("KMR003_22"), key: 'reservationMemberName', dataType: 'string', width: '150px', ntsControl: "Label" });
            if (!isNewMode) {
                fixedColumns.push({
                    headerText: vm.$i18n("KMR003_23"),
                    group: [
                        { headerText: '', key: 'isDelete', dataType: 'boolean', width: '60px', checkbox: true, ntsControl: "isDelCheckBox" }
                    ]
                });
            }

            fixedColumns.push({ headerText: vm.$i18n("KMR003_24"), key: 'reservationTime', dataType: 'string', width: '65px', ntsControl: "Label", columnCssClass: "halign-right" });

            if (isNewMode) {
                fixedColumns.push({ headerText: vm.$i18n("KMR003_25"), key: 'ordered', dataType: 'boolean', width: '60px', ntsControl: "Label" });
            } else {
                fixedColumns.push({
                    headerText: vm.$i18n("KMR003_25"),
                    group: [
                        { headerText: '', key: 'ordered', dataType: 'boolean', width: '60px', checkbox: true, ntsControl: "isOrderCheckBox" }
                    ]
                });
            }

            return fixedColumns;
        }

        loadMGrid() {
            const vm = this;
            let height = $(window).height() - 90 - 290;
            let width = $(window).width() + 20 - 1170;

            let cellStates: Array<CellState> = [];
            _.forEach(vm.datas, (data: ReservationModifyEmployeeDto) => {
                if (!vm.isNewMode()) {
                    cellStates.push(new CellState(data.key, "isDelete", ["center-align"]));
                }

                cellStates.push(new CellState(data.key, "ordered", ["center-align"]));
            });

            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1170px",
                height: "200px",
                subWidth: width + "px",
                subHeight: height + "px",
                headerHeight: '60px',
                dataSource: vm.datas,
                primaryKey: 'key',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                columns: vm.getFixedColumns().concat(vm.dynamicColumns),
                ntsControls: [
                    {
                        name: 'isDelCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId, columnKey, value, rowData) {
                            vm.checkDelete(rowId, value);
                        }
                    },
                    {
                        name: 'isOrderCheckBox', options: { value: 1, text: '' }, optionsValue: 'value',
                        optionsText: 'text', controlType: 'CheckBox', enable: true,
                        onChange: function (rowId, columnKey, value, rowData) {
                            vm.setBentoInput(rowId, value);
                        }
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
                            },
                            {
                                columnKey: "isDelete",
                                isFixed: true
                            },
                            {
                                columnKey: "reservationTime",
                                isFixed: true
                            },
                            {
                                columnKey: "ordered",
                                isFixed: true
                            }
                        ]
                    },
                    {
                        name: 'CellStyles',
                        states: cellStates
                    }
                ]
            }).create();
            vm.setPageStatus();
        }

        setPageStatus() {
            const vm = this;
            let data = $("#grid").mGrid("dataSource", true);
            _.each(data, (item: ReservationModifyEmployeeDto) => {
                if (!item.activity) {
                    // vm.disableControl(item.key, "reservationMemberCode", true);
                    // vm.disableControl(item.key, "reservationMemberName", true);
                    vm.disableControl(item.key, "isDelete", true);
                    // vm.disableControl(item.key, "reservationTime", true);
                    vm.disableControl(item.key, "ordered", true);

                    vm.setBentoInput(item.key, true);
                } else {
                    if (vm.isNewMode()) {
                        vm.disableControl(item.key, "ordered", true);
                    }
                    vm.setBentoInput(item.key, item.ordered);
                }
            })
        }

        checkDelete(rowId: any, check: any) {
            const vm = this;
            if (check) {
                vm.deleteItems.push(rowId);
            } else {
                const index = vm.deleteItems.indexOf(rowId);
                if (index > -1) {
                    vm.deleteItems.splice(index, 1);
                }
            }

            vm.canDelete(vm.deleteItems.length > 0);
        }

        setBentoInput(rowId: any, isDisable: any) {
            const vm = this;
            _.forEach(vm.headerInfos, (bento: HeaderInfoDto) => {
                vm.disableControl(rowId, bento.key, isDisable);
            })
        }

        disableControl(rowId, columnKey, isDisable) {
            if (isDisable) {
                $("#grid").mGrid("disableNtsControlAt", rowId, columnKey)
            } else {
                $("#grid").mGrid("enableNtsControlAt", rowId, columnKey)
            }
        }

        initData(): JQueryPromise<any> {
            const vm = this,
                dfd = $.Deferred();
            vm.$blockui("invisible");
            let param = vm.createParamGet();
            vm.datas = [];
            vm.dynamicColumns = [];
            vm.deleteItems = [];
            vm.canDelete(false);
            vm.$ajax(API.BENTO_RESERVATTIONS, param).done((res: IReservationModifyDto) => {
                if (!res) return;

                if (!_.isEmpty(res.bentoClosingTimes)) {
                    vm.closingTimeFrames.removeAll();
                }

                _.forEach(res.bentoClosingTimes, (item: IClosingTimeDto) => {
                    //add A2_8 A2_9 A2_10
                    vm.closingTimeFrames.push(new ClosingTimeFrame(item));
                })
                vm.setClosingTimeTime(vm.closingTimeFrameValue());
                vm.headerInfos = _.map(res.bentos, (item: IHeaderInfoDto) => { return new HeaderInfoDto(item); });
                _.forEach(vm.headerInfos, (item: HeaderInfoDto) => {
                    vm.dynamicColumns.push({
                        headerText: _.escape(item.bentoName),
                        group: [
                            {
                                headerText: item.unitLabel,
                                key: item.key,
                                dataType: 'string',
                                width: '80px',
                                columnCssClass: 'halign-right',
                                constraint: {
                                    primitiveValue: 'BentoReservationCount',
                                    required: false
                                }
                            }
                        ]
                    });
                });

                vm.datas = _.map(res.reservationModifyEmps, (item: IReservationModifyEmployeeDto) => {
                    let dto = new ReservationModifyEmployeeDto(item);
                    dto.convertData(vm.headerInfos);
                    return dto;
                });

                if (!_.isEmpty(res.errors)) {
                    let errors = [];
                    _.forEach(res.errors, error => {
                        errors.push({
                            message: error.message,
                            messageId: error.messageId,
                            supplements: {}
                        })
                    });

                    nts.uk.ui.dialog.bundledErrors({ errors: errors });
                }
            }).fail(err => {
                vm.$dialog.error(err);
                vm.$blockui("clear");
            }).always(() => {
                $("#grid").mGrid("destroy");
                vm.loadMGrid();
                vm.$blockui("clear");

                // check focus
                if (_.isEmpty(vm.datas)) {
                    vm.hasData(false);
                    $("#ccg001-btn-search-drawer").focus()
                } else {
                    vm.hasData(true);
                    $("#A1_2").focus();
                }
                dfd.resolve();
            })
            return dfd.promise();
        }

        createParamGet() {
            const vm = this;
            let empIds = _.map(vm.empSearchItems, (item: EmployeeSearchDto) => {
                return item.employeeId;
            });
            let param = {
                empIds: empIds,
                date: vm.date(),
                closingTimeFrame: vm.closingTimeFrameValue(),
                searchCondition: vm.searchConditionValue()
            };
            return param;
        }

        setClosingTimeTime(frameId: number) {
            const vm = this;
            let frame = _.find(vm.closingTimeFrames(), (item: ClosingTimeFrame) => { return item.id == frameId });
            if (frame) {
                vm.closingTimeTime(parseTime(frame.startTime, true).format() + "～" + parseTime(frame.endTime, true).format())
            } else {
                vm.closingTimeTime("");
            }
        }

        updateReservation() {
            const vm = this;
            vm.$blockui("invisible");
            let reservations: Array<ReservationModifyEmployeeDto> = $("#grid").mGrid("dataSource", true);

            let commandUpdate = new ForceUpdateBentoReserveCommand(vm.date(), vm.isNewMode(), vm.closingTimeFrameValue());
            let errors: Array<ErrorDto> = [];
            commandUpdate.setReservationInfos(reservations, vm.headerInfos, errors);

            if (!_.isEmpty(errors)) {
                vm.$blockui("clear");
                vm.$window.modal('at', PATH.KMR003_B, errors);
                return;
            }

            vm.$ajax(API.BENTO_UPDATE, commandUpdate).done(() => {
                vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                    vm.$blockui("clear");
                    if (vm.isNewMode()) {
                        vm.searchConditionValue(4);
                    } else {
                        vm.initData();
                    }
                });
            }).always(() => vm.$blockui("clear"));
        }

        deleteReservation() {
            const vm = this;
            vm.$dialog.confirm({ messageId: 'Msg_18' }).then(res => {
                if (res == "yes") {
                    vm.$blockui("invisible");
                    let reservations: Array<ReservationModifyEmployeeDto> = $("#grid").mGrid("dataSource", true);
                    let reservationDeletes = _.filter(reservations, (reservation: ReservationModifyEmployeeDto) => { return reservation.isDelete; });

                    let commandDelete = new ForceDeleteBentoReserveCommand(vm.date(), vm.closingTimeFrameValue());
                    commandDelete.setReservationInfos(reservationDeletes);
                    vm.$ajax(API.BENTO_DELETE, commandDelete).done(() => {
                        vm.$dialog.info({ messageId: "Msg_16" }).then(function () {
                            vm.$blockui("clear");
                            vm.initData();
                        });
                    }).always(() => vm.$blockui("clear"));
                }
            });
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

    class CellState {
        rowId: any;
        columnKey: string;
        state: Array<any>
        constructor(rowId: any, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
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

        errors: Array<any>;
    }

    // 弁当ヘッダー
    interface IHeaderInfoDto {
        frameNo: number;
        bentoName: string;
        unit: string;
    }

    class HeaderInfoDto {
        frameNo: number;
        bentoName: string;
        unit: string;

        key: string;
        unitLabel: string;

        bentoValidation: any;
        constructor(item: IHeaderInfoDto) {
            this.frameNo = item.frameNo;
            this.bentoName = item.bentoName;
            this.unit = item.unit;

            this.key = item.frameNo.toString();
            this.unitLabel = "(" + item.unit + ")";

            this.bentoValidation = new validation.NumberValidator(this.bentoName, "BentoReservationCount", { required: false });
        }
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

        key: string;
        isDelete: boolean;

        constructor(item: IReservationModifyEmployeeDto) {
            this.reservationDetails = _.map(item.reservationDetails, (x: IReservationModifyDetailDto) => {
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

            this.key = item.reservationMemberCode;
            this.isDelete = false;
        }

        convertData(headerInfos: Array<HeaderInfoDto>) {
            let self = this;
            _.forEach(this.reservationDetails, (item: ReservationModifyDetailDto) => {
                let header = _.find(headerInfos, (x: HeaderInfoDto) => { return x.frameNo == item.frameNo; });
                if (header) {
                    self[header.key] = item.bentoCount;
                }
            })
        }
    }

    // 社員の予約情報
    class ReservationModifyDetailDto {
        bentoCount: number;
        frameNo: number;
        constructor(item: IReservationModifyDetailDto) {
            this.bentoCount = item.bentoCount;
            this.frameNo = item.frameNo;
        }
    }

    class ErrorDto {
        key: string;
        employee: string;
        bento: string;
        message: string;

        constructor(employee: string, bento: string, message: string) {
            this.key = uk.util.randomId();
            this.employee = employee
            this.bento = bento
            this.message = message
        }
    }

    class ForceUpdateBentoReserveCommand {
        reservationInfos: Array<BentoReserveInfoCommand>;
        date: any;
        isNew: boolean;
        closingTimeFrame: number;

        constructor(date: any, isNew: boolean, closingTimeFrame: number) {
            this.reservationInfos = [];
            this.date = date;
            this.isNew = isNew;
            this.closingTimeFrame = closingTimeFrame;
        }

        setReservationInfos(reservations: Array<ReservationModifyEmployeeDto>, bentos: Array<HeaderInfoDto>, errors: Array<ErrorDto>) {
            this.reservationInfos = _.map(reservations, (reservation: ReservationModifyEmployeeDto) => {
                return new BentoReserveInfoCommand(reservation, bentos, errors);
            });
        }
    }

    class BentoReserveInfoCommand {
        reservationCardNo: String;
        ordered: boolean;
        details: Array<BentoReserveDetailCommand>
        constructor(reservation: ReservationModifyEmployeeDto, bentos: Array<HeaderInfoDto>, errors: Array<ErrorDto>) {
            let self = this;
            self.reservationCardNo = reservation.reservationCardNo;
            self.ordered = reservation.ordered;
            self.details = [];
            _.forEach(bentos, (bento: HeaderInfoDto) => {
                let bentoCount = reservation[bento.key];
                let check = bento.bentoValidation.validate(bentoCount == null ? null : bentoCount.toString());
                if (!check.isValid) {
                    errors.push(new ErrorDto(reservation.reservationMemberCode + "　" + reservation.reservationMemberName, bento.bentoName, check.errorMessage));
                } else {
                    if (!isNaN(bentoCount) && bentoCount != null && !nts.uk.text.isNullOrEmpty(bentoCount.toString().trim())) {
                        self.details.push(new BentoReserveDetailCommand(bento.frameNo, bentoCount));
                    }
                }
            })
        }
    }

    class BentoReserveDetailCommand {
        frameNo: number;
        bentoCount: number;

        constructor(frameNo: number, bentoCount: number) {
            this.frameNo = frameNo;
            this.bentoCount = bentoCount;
        }
    }

    class ForceDeleteBentoReserveCommand {
        reservationInfos: Array<ReservationInfoCommand>;
        date: any;
        closingTimeFrame: number;

        constructor(date: any, closingTimeFrame: number) {
            this.date = date;
            this.closingTimeFrame = closingTimeFrame;
        }

        setReservationInfos(reservations: Array<ReservationModifyEmployeeDto>) {
            this.reservationInfos = _.map(reservations, (reservation: ReservationModifyEmployeeDto) => {
                return new ReservationInfoCommand(reservation.reservationCardNo, reservation.reservationMemberId);
            });
        }
    }

    class ReservationInfoCommand {
        reservationCardNo: String;
        empployeeId: String;

        constructor(reservationCardNo: String, empployeeId: String) {
            this.reservationCardNo = reservationCardNo;
            this.empployeeId = empployeeId;
        }
    }
}
