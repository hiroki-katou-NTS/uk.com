module nts.uk.pr.view.qmm019.share.model {
    import getText = nts.uk.resource.getText;

    export enum CategoryAtr {
        // 支給項目
        PAYMENT_ITEM = 0,
        // 控除項目
        DEDUCTION_ITEM = 1,
        // 勤怠項目
        ATTEND_ITEM = 2,
        // 記事項目
        REPORT_ITEM = 3,
        // その他項目
        OTHER_ITEM = 4
    }

    export function getCategoryAtrText(e: CategoryAtr) {
        switch (e) {
            case CategoryAtr.PAYMENT_ITEM:
                // return getText("Enum_CategoryAtr_PAYMENT_ITEM");
                return getText("QMM019_106");
            case CategoryAtr.DEDUCTION_ITEM:
                // return getText("Enum_CategoryAtr_DEDUCTION_ITEM");
                return getText("QMM019_160");
            case CategoryAtr.ATTEND_ITEM:
                // return getText("Enum_CategoryAtr_ATTEND_ITEM");
                return getText("QMM019_168");
            case CategoryAtr.REPORT_ITEM:
                // return getText("Enum_CategoryAtr_REPORT_ITEM");
                return getText("QMM019_173");
            // case CategoryAtr.OTHER_ITEM:
                // return getText("Enum_CategoryAtr_OTHER_ITEM");
            default:
                return "";
        }
    }

    /**
     * 明細新規作成区分
     */
    export enum SpecCreateAtr {
        NEW = 0,
        COPY = 1
    }

    export function getSpecCreateAtr(): Array<BoxModel> {
        return [
            new model.BoxModel(SpecCreateAtr.NEW, getText('QMM019_178')),
            new model.BoxModel(SpecCreateAtr.COPY, getText('QMM019_179')),
        ];
    }

    export enum StatementLayoutPattern {
        LASER_PRINT_A4_PORTRAIT_ONE_PERSON = 0,
        LASER_PRINT_A4_PORTRAIT_TWO_PERSON = 1,
        LASER_PRINT_A4_PORTRAIT_THREE_PERSON = 2,
        LASER_PRINT_A4_LANDSCAPE_TWO_PERSON = 3,
        LASER_CRIMP_PORTRAIT_ONE_PERSON = 4,
        LASER_CRIMP_LANDSCAPE_ONE_PERSON = 5,
        DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON = 6
    }

    /**
     * 支給合計対象区分
     */
    export enum PaymentTotalObjAtr {
        // 合計対象内
        INSIDE = 0,
        // 合計対象外
        OUTSIDE = 1,
        // 合計対象内（現物）
        INSIDE_ACTUAL = 2,
        // 合計対象外（現物）
        OUTSIDE_ACTUAL = 3
    }

    export function getPaymentTotalObjAtr(printSet: StatementPrintAtr): Array<ItemModel> {
        if (printSet == StatementPrintAtr.DO_NOT_PRINT) {
            return [
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE, getText("Enum_PaymentTotalObjAtr_OUTSIDE")),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE_ACTUAL, getText("Enum_PaymentTotalObjAtr_OUTSIDE_ACTUAL"))
            ];
        }
        else {
            return [
                new model.ItemModel(PaymentTotalObjAtr.INSIDE, getText("Enum_PaymentTotalObjAtr_INSIDE")),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE, getText("Enum_PaymentTotalObjAtr_OUTSIDE")),
                new model.ItemModel(PaymentTotalObjAtr.INSIDE_ACTUAL, getText("Enum_PaymentTotalObjAtr_INSIDE_ACTUAL")),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE_ACTUAL, getText("Enum_PaymentTotalObjAtr_OUTSIDE_ACTUAL"))
            ];
        }
    }

    /**
     * 控除合計対象区分
     */
    export enum DeductionTotalObjAtr {
        // 合計対象外
        OUTSIDE = 0,
        // 合計対象内
        INSIDE = 1
    }

    export function getDeductionTotalObjAtr(printSet: StatementPrintAtr): Array<ItemModel> {
        if (printSet == StatementPrintAtr.DO_NOT_PRINT) {
            return [
                new model.ItemModel(DeductionTotalObjAtr.OUTSIDE, getText("Enum_DeductionTotalObjAtr_OUTSIDE")),
            ];
        }
        else {
            return [
                new model.ItemModel(DeductionTotalObjAtr.OUTSIDE, getText("Enum_DeductionTotalObjAtr_OUTSIDE")),
                new model.ItemModel(DeductionTotalObjAtr.INSIDE, getText("Enum_DeductionTotalObjAtr_INSIDE"))
            ];
        }
    }

    /**
     * 支給計算方法区分
     */
    export enum PaymentCaclMethodAtr {
        // 手入力
        MANUAL_INPUT = 0,
        //個人情報参照
        PERSON_INFO_REF = 1,
        //計算式
        CACL_FOMULA = 2,
        //賃金テーブル
        WAGE_TABLE = 3,
        //共通金額
        COMMON_AMOUNT = 4,
        //内訳項目
        BREAKDOWN_ITEM = 5
    }

    export function getPaymentCaclMethodAtr(e: BreakdownItemUseAtr): Array<ItemModel> {
        switch (e) {
            case BreakdownItemUseAtr.USE:
                return [
                    new model.ItemModel(PaymentCaclMethodAtr.BREAKDOWN_ITEM, getText("Enum_PaymentCaclMethodAtr_BREAKDOWN_ITEM"))
                ];
            case BreakdownItemUseAtr.NOT_USE:
                return [
                    new model.ItemModel(PaymentCaclMethodAtr.MANUAL_INPUT, getText("Enum_PaymentCaclMethodAtr_MANUAL_INPUT")),
                    new model.ItemModel(PaymentCaclMethodAtr.PERSON_INFO_REF, getText("Enum_PaymentCaclMethodAtr_PERSON_INFO_REF")),
                    new model.ItemModel(PaymentCaclMethodAtr.CACL_FOMULA, getText("Enum_PaymentCaclMethodAtr_CACL_FOMULA")),
                    new model.ItemModel(PaymentCaclMethodAtr.WAGE_TABLE, getText("Enum_PaymentCaclMethodAtr_WAGE_TABLE")),
                    new model.ItemModel(PaymentCaclMethodAtr.COMMON_AMOUNT, getText("Enum_PaymentCaclMethodAtr_COMMON_AMOUNT"))
                ];
            case null:
                return [
                    new model.ItemModel(PaymentCaclMethodAtr.MANUAL_INPUT, getText("Enum_PaymentCaclMethodAtr_MANUAL_INPUT")),
                    new model.ItemModel(PaymentCaclMethodAtr.PERSON_INFO_REF, getText("Enum_PaymentCaclMethodAtr_PERSON_INFO_REF")),
                    new model.ItemModel(PaymentCaclMethodAtr.CACL_FOMULA, getText("Enum_PaymentCaclMethodAtr_CACL_FOMULA")),
                    new model.ItemModel(PaymentCaclMethodAtr.WAGE_TABLE, getText("Enum_PaymentCaclMethodAtr_WAGE_TABLE")),
                    new model.ItemModel(PaymentCaclMethodAtr.COMMON_AMOUNT, getText("Enum_PaymentCaclMethodAtr_COMMON_AMOUNT")),
                    new model.ItemModel(PaymentCaclMethodAtr.BREAKDOWN_ITEM, getText("Enum_PaymentCaclMethodAtr_BREAKDOWN_ITEM"))
                ];
        }
    }

    /**
     * 控除計算方法区分
     */
    export enum DeductionCaclMethodAtr {
        // 手入力
        MANUAL_INPUT = 0,
        //個人情報参照
        PERSON_INFO_REF = 1,
        //計算式
        CACL_FOMULA = 2,
        //賃金テーブル
        WAGE_TABLE = 3,
        //共通金額
        COMMON_AMOUNT = 4,
        //支給相殺
        SUPPLY_OFFSET = 5,
        //内訳項目
        BREAKDOWN_ITEM = 6
    }

    export function getDeductionCaclMethodAtr(e: BreakdownItemUseAtr): Array<ItemModel> {
        switch (e) {
            case BreakdownItemUseAtr.USE:
                return [
                    new model.ItemModel(DeductionCaclMethodAtr.BREAKDOWN_ITEM, getText("Enum_DeductionCaclMethodAtr_BREAKDOWN_ITEM"))
                ];
            case BreakdownItemUseAtr.NOT_USE:
                return [
                    new model.ItemModel(DeductionCaclMethodAtr.MANUAL_INPUT, getText("Enum_DeductionCaclMethodAtr_MANUAL_INPUT")),
                    new model.ItemModel(DeductionCaclMethodAtr.PERSON_INFO_REF, getText("Enum_DeductionCaclMethodAtr_PERSON_INFO_REF")),
                    new model.ItemModel(DeductionCaclMethodAtr.CACL_FOMULA, getText("Enum_DeductionCaclMethodAtr_CACL_FOMULA")),
                    new model.ItemModel(DeductionCaclMethodAtr.WAGE_TABLE, getText("Enum_DeductionCaclMethodAtr_WAGE_TABLE")),
                    new model.ItemModel(DeductionCaclMethodAtr.COMMON_AMOUNT, getText("Enum_DeductionCaclMethodAtr_COMMON_AMOUNT")),
                    new model.ItemModel(DeductionCaclMethodAtr.SUPPLY_OFFSET, getText("Enum_DeductionCaclMethodAtr_SUPPLY_OFFSET")),
                ];
            case null:
                return [
                    new model.ItemModel(DeductionCaclMethodAtr.MANUAL_INPUT, getText("Enum_DeductionCaclMethodAtr_MANUAL_INPUT")),
                    new model.ItemModel(DeductionCaclMethodAtr.PERSON_INFO_REF, getText("Enum_DeductionCaclMethodAtr_PERSON_INFO_REF")),
                    new model.ItemModel(DeductionCaclMethodAtr.CACL_FOMULA, getText("Enum_DeductionCaclMethodAtr_CACL_FOMULA")),
                    new model.ItemModel(DeductionCaclMethodAtr.WAGE_TABLE, getText("Enum_DeductionCaclMethodAtr_WAGE_TABLE")),
                    new model.ItemModel(DeductionCaclMethodAtr.COMMON_AMOUNT, getText("Enum_DeductionCaclMethodAtr_COMMON_AMOUNT")),
                    new model.ItemModel(DeductionCaclMethodAtr.SUPPLY_OFFSET, getText("Enum_DeductionCaclMethodAtr_SUPPLY_OFFSET")),
                    new model.ItemModel(DeductionCaclMethodAtr.BREAKDOWN_ITEM, getText("Enum_DeductionCaclMethodAtr_BREAKDOWN_ITEM"))
                ];
        }
    }

    /**
     * 支給按分区分
     */
    export enum PaymentProportionalAtr {
        // 按分する
        PROPORTIONAL = 0,
        // 按分しない
        NOT_PROPORTIONAL = 1,
        // 月１回支給
        PAYMENT_ONE_A_MONTH = 2
    }

    export function getPaymentProportionalAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(PaymentProportionalAtr.NOT_PROPORTIONAL, getText('QMM019_116')),
            new model.ItemModel(PaymentProportionalAtr.PROPORTIONAL, getText('QMM019_117')),
            new model.ItemModel(PaymentProportionalAtr.PAYMENT_ONE_A_MONTH, getText('QMM019_118'))
        ];
    }

    /**
     * 控除按分区分
     */
    export enum DeductionProportionalAtr {
        // 按分する
        PROPORTIONAL = 0,
        // 按分しない
        NOT_PROPORTIONAL = 1,
        // 月１回控除
        DEDUCTION_ONCE_A_MONTH = 2
    }

    export function getDeductionProportionalAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(DeductionProportionalAtr.NOT_PROPORTIONAL, getText('QMM019_116')),
            new model.ItemModel(DeductionProportionalAtr.PROPORTIONAL, getText('QMM019_117')),
            new model.ItemModel(DeductionProportionalAtr.DEDUCTION_ONCE_A_MONTH, getText('QMM019_162'))
        ];
    }

    /**
     * 通勤区分
     */
    export enum WorkingAtr {
        // 交通機関
        TRANSPORT_FACILITIES = 0,
        // 交通用具
        TRANSPORT_EQUIPMENT = 1
    }

    export function getWorkingAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(WorkingAtr.TRANSPORT_FACILITIES, getText("Enum_WorkingAtr_TRANSPORT_FACILITIES")),
            new model.ItemModel(WorkingAtr.TRANSPORT_EQUIPMENT, getText('Enum_WorkingAtr_TRANSPORT_EQUIPMENT'))
        ];
    }

    /**
     * 範囲利用区分
     */
    export enum UseRangeAtr {
        // 設定する
        USE = 1,
        // 設定しない
        NOT_USE = 0
    }

    /**
     * 範囲値の属性
     */
    export enum RangeValueEnum {
        AMOUNT_OF_MONEY = 0,
        TIME = 1,
        TIMES = 2
    }

    /**
     * 按分方法区分
     */
    export enum ProportionalMethodAtr {
        // 割合で計算
        BY_PROPORTION = 0,
        // 日数控除
        DAYS_DEDUCTION = 1
    }

    export function getProportionalMethodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ProportionalMethodAtr.BY_PROPORTION, getText('Enum_ProportionalMethodAtr_BY_PROPORTION')),
            new model.ItemModel(ProportionalMethodAtr.DAYS_DEDUCTION, getText('Enum_ProportionalMethodAtr_DAYS_DEDUCTION'))
        ];
    }

    /**
     * 課税区分
     */
    export enum TaxAtr {
        // 課税
        TAXATION = 0,
        // 非課税（限度あり）
        LIMIT_TAX_EXEMPTION = 1,
        // 非課税（限度なし）
        NO_LIMIT_TAX_EXEMPTION = 2,
        // 通勤費（手入力）
        COMMUTING_EXPENSES_MANUAL = 3,
        // 通勤費（定期券利用）
        COMMUTING_EXPENSES_USING_COMMUTER = 4
    }

    export function getTaxAtrText(e: TaxAtr) {
        switch (e) {
            case TaxAtr.TAXATION:
                return getText("Enum_TaxAtr_TAXATION");
            case TaxAtr.LIMIT_TAX_EXEMPTION:
                return getText("Enum_TaxAtr_LIMIT_TAX_EXEMPTION");
            case TaxAtr.NO_LIMIT_TAX_EXEMPTION:
                return getText("Enum_TaxAtr_NO_LIMIT_TAX_EXEMPTION");
            case TaxAtr.COMMUTING_EXPENSES_MANUAL:
                return getText("Enum_TaxAtr_COMMUTING_EXPENSES_MANUAL");
            case TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER:
                return getText("Enum_TaxAtr_COMMUTING_EXPENSES_USING_COMMUTER");
            default:
                return "";
        }
    }

    /**
     * 控除項目区分
     */
    export enum DeductionItemAtr {
        // 任意控除項目
        OPTIONAL_DEDUCTION_ITEM = 0,
        // 社会保険項目
        SOCIAL_INSURANCE_ITEM = 1,
        // 所得税項目
        INCOME_TAX_ITEM = 2,
        // 住民税項目
        INHABITANT_TAX_ITEM = 3
    }

    export function getDeductionItemAtrText(e: DeductionItemAtr) {
        switch (e) {
            case DeductionItemAtr.OPTIONAL_DEDUCTION_ITEM:
                return getText("Enum_DeductionItemAtr_OPTIONAL_DEDUCTION_ITEM");
            case DeductionItemAtr.SOCIAL_INSURANCE_ITEM:
                return getText("Enum_DeductionItemAtr_SOCIAL_INSURANCE_ITEM");
            case DeductionItemAtr.INCOME_TAX_ITEM:
                return getText("Enum_DeductionItemAtr_INCOME_TAX_ITEM");
            case DeductionItemAtr.INHABITANT_TAX_ITEM:
                return getText("Enum_DeductionItemAtr_INHABITANT_TAX_ITEM");
            default:
                return "";
        }
    }

    /**
     * 平均賃金区分
     */
    export enum TimeCountAtr {
        // 時間
        TIME = 0,
        // 回数
        TIMES = 1
    }

    export function getTimeCountAtrText(e: TimeCountAtr) {
        switch (e) {
            case TimeCountAtr.TIME:
                return getText("Enum_TimeCountAtr_TIME");
            case TimeCountAtr.TIMES:
                return getText("Enum_TimeCountAtr_TIMES");
            default:
                return "";
        }
    }

    /**
     * 固定的賃金の設定区分
     */
    export enum SocialInsuranceCategory {
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getSocialInsuranceCategoryText(e: SocialInsuranceCategory) {
        switch (e) {
            case SocialInsuranceCategory.NOT_COVERED:
                return getText("Enum_SocialInsuranceCategory_NOT_COVERED");
            case SocialInsuranceCategory.COVERED:
                return getText("Enum_SocialInsuranceCategory_COVERED");
            default:
                return "";
        }
    }

    /**
     * 労働保険区分
     */
    export enum LaborInsuranceCategory {
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getLaborInsuranceCategoryText(e: LaborInsuranceCategory) {
        switch (e) {
            case LaborInsuranceCategory.NOT_COVERED:
                return getText("Enum_LaborInsuranceCategory_NOT_COVERED");
            case LaborInsuranceCategory.COVERED:
                return getText("Enum_LaborInsuranceCategory_COVERED");
            default:
                return "";
        }
    }

    /**
     * 固定的賃金の対象区分
     */
    export enum CategoryFixedWage {
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getCategoryFixedWageText(e: CategoryFixedWage): string {
        switch (e) {
            case CategoryFixedWage.NOT_COVERED:
                return getText("Enum_CategoryFixedWage_NOT_COVERED");
            case CategoryFixedWage.COVERED:
                return getText("Enum_CategoryFixedWage_COVERED");
            default:
                return "";
        }
    }

    /**
     * 平均賃金区分
     */
    export enum AverageWageAtr {
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getAverageWageAtrText(e: AverageWageAtr) {
        switch (e) {
            case AverageWageAtr.NOT_COVERED:
                return getText("Enum_AverageWageAtr_NOT_COVERED");
            case AverageWageAtr.COVERED:
                return getText("Enum_AverageWageAtr_COVERED");
            default:
                return "";
        }
    }

    /**
     * 既定区分
     */
    export enum DefaultAtr {
        // ユーザ作成
        USER_CREATE = 0,
        // システム既定
        SYSTEM_DEFAULT = 1
    }

    /**
     * 明細書印字区分
     */
    export enum StatementPrintAtr {
        PRINT = 1,
        DO_NOT_PRINT = 0
    }

    /**
     * 内訳項目利用区分
     */
    export enum BreakdownItemUseAtr {
        NOT_USE = 0,
        USE = 1
    }

    // 年月期間の汎用履歴項目
    export interface IYearMonthHistory {
        startMonth: number;
        endMonth: number;
        historyId: string;
        layoutPattern: number;
    }

    // 年月期間の汎用履歴項目
    export class YearMonthHistory {
        startMonth: number;
        endMonth: number;
        historyId: string;

        // for tree grid
        code: string;
        nodeText: string;
        history: Array<YearMonthHistory>;

        constructor(code: String, params: IYearMonthHistory) {
            this.code = code;
            this.startMonth = params.startMonth;
            this.endMonth = params.endMonth;
            this.historyId = params.historyId;
            this.nodeText = nts.uk.time.parseYearMonth(params.startMonth).format()
                + " ～ " + nts.uk.time.parseYearMonth(params.endMonth).format();
            this.history = [];
        }

        updateNodeText(){
            this.nodeText = nts.uk.time.parseYearMonth(this.startMonth).format()
                + " ～ " + nts.uk.time.parseYearMonth(this.endMonth).format();
        }
    }

    export interface IStatementLayout {
        statementCode: string;
        statementName: string;
        history: Array<IYearMonthHistory>;
    }

    export class StatementLayout {
        statementCode: string;
        statementName: string;
        history: Array<YearMonthHistory>;

        // for tree grid
        nodeText: string;
        historyId: string;

        constructor(params: IStatementLayout) {
            this.statementCode = params ? params.statementCode : null;
            this.statementName = params ? params.statementName : null;
            this.history = params ? params.history.map(function (item) {
                return new YearMonthHistory(params.statementCode, item);
            }) : [];

            this.historyId = params ? params.statementCode : null;
            this.nodeText = params ? params.statementCode + " " + _.escape(params.statementName) : null;
        }
    }

    export interface IStatementLayoutHistData {
        statementCode: string;
        statementName: string;
        historyId: string;
        startMonth: number;
        endMonth: number;
        statementLayoutSet: IStatementLayoutSet;
    }

    export interface IStatementLayoutSet {
        histId: string;
        layoutPattern: number;
        listSettingByCtg: Array<ISettingByCtg>;
    }

    export interface ISettingByCtg {
        ctgAtr: number;
        listLineByLineSet: Array<ILineByLineSetting>;
    }

    export interface ILineByLineSetting {
        printSet: number;
        lineNumber: number;
        listSetByItem: Array<ISettingByItem>;
    }

    export interface ISettingByItem {
        itemPosition: number;
        itemId: string;
        shortName: string;
        paymentItemDetailSet: IPaymentItemDetail;
        deductionItemDetailSet: IDeductionItemDetail;
        itemRangeSet: IItemRangeSet;
    }

    export interface IPaymentItemDetail {
        histId: string;
        salaryItemId: string;
        totalObj: string;
        proportionalAtr: string;
        proportionalMethod: string;
        calcMethod: string;
        calcFomulaCd: string;
        personAmountCd: string;
        commonAmount: number;
        wageTblCode: string;
        workingAtr: string;
    }

    export interface IDeductionItemDetail {
        histId: string;
        salaryItemId: string;
        totalObj: string;
        proportionalAtr: string;
        proportionalMethod: string;
        calcMethod: string;
        calcFormulaCd: string;
        personAmountCd: string;
        commonAmount: number;
        wageTblCd: string;
        supplyOffset: string;
    }

    export interface IItemRangeSet {
        histId: string;
        salaryItemId: string;
        rangeValAttribute: RangeValueEnum;
        errorUpperLimitSetAtr: UseRangeAtr;
        errorUpRangeValAmount: any;
        errorUpRangeValTime: any;
        errorUpRangeValNum: any;
        errorLowerLimitSetAtr: UseRangeAtr;
        errorLoRangeValAmount: any;
        errorLoRangeValTime: any;
        errorLoRangeValNum: any;
        alarmUpperLimitSetAtr: UseRangeAtr;
        alarmUpRangeValAmount: any;
        alarmUpRangeValTime: any;
        alarmUpRangeValNum: any;
        alarmLowerLimitSetAtr: UseRangeAtr;
        alarmLoRangeValAmount: any;
        alarmLoRangeValTime: any;
        alarmLoRangeValNum: any;
    }

    export class LayoutPattern {
        id: number;
        printerType: string;
        paper: string;
        direction: string;
        numberPersonInPage: string;
        numberOfDisplayItem: string;
        remarks: string;

        constructor(id: number, printerType: string, paper: string, direction: string,
                    numberPersonInPage: string, numberOfDisplayItem: string, remarks: string) {
            this.id = id;
            this.printerType = printerType;
            this.paper = paper;
            this.direction = direction;
            this.numberPersonInPage = numberPersonInPage;
            this.numberOfDisplayItem = numberOfDisplayItem;
            this.remarks = remarks;
        }
    }

    export function getLayoutPatternData(): Array<LayoutPattern> {
        let data: Array<LayoutPattern> = [];

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_ONE_PERSON,
            getText('QMM019_58'),
            getText('QMM019_59'),
            getText('QMM019_60'),
            getText('QMM019_61'),
            getText('QMM019_62'),
            getText('QMM019_63')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_TWO_PERSON,
            getText('QMM019_64'),
            getText('QMM019_65'),
            getText('QMM019_66'),
            getText('QMM019_67'),
            getText('QMM019_68'),
            getText('QMM019_69')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_THREE_PERSON,
            getText('QMM019_70'),
            getText('QMM019_71'),
            getText('QMM019_72'),
            getText('QMM019_73'),
            getText('QMM019_74'),
            getText('QMM019_75')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_PRINT_A4_LANDSCAPE_TWO_PERSON,
            getText('QMM019_76'),
            getText('QMM019_77'),
            getText('QMM019_78'),
            getText('QMM019_79'),
            getText('QMM019_80'),
            getText('QMM019_81')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_CRIMP_PORTRAIT_ONE_PERSON,
            getText('QMM019_82'),
            getText('QMM019_83'),
            getText('QMM019_84'),
            getText('QMM019_85'),
            getText('QMM019_86'),
            getText('QMM019_87')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON,
            getText('QMM019_88'),
            getText('QMM019_89'),
            getText('QMM019_90'),
            getText('QMM019_91'),
            getText('QMM019_92'),
            getText('QMM019_93')
        ));

        data.push(new LayoutPattern(
            StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON,
            getText('QMM019_94'),
            getText('QMM019_95'),
            getText('QMM019_96'),
            getText('QMM019_97'),
            getText('QMM019_98'),
            getText('QMM019_99')
        ));

        return data;
    }

    export function getMaxPrintLineOfLayoutPattern(layoutPattern: StatementLayoutPattern): number {
        switch (layoutPattern) {
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_ONE_PERSON:
                return 30;
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_TWO_PERSON:
                return 17;
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_THREE_PERSON:
                return 10;
            case StatementLayoutPattern.LASER_PRINT_A4_LANDSCAPE_TWO_PERSON:
                return 10;
            case StatementLayoutPattern.LASER_CRIMP_PORTRAIT_ONE_PERSON:
                return 17;
            case StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON:
                return 0;
            case StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON:
                return 0;
            default:
                return 0;
        }
    }

    export function getLayoutPatternText(e: StatementLayoutPattern): string {
        switch (e) {
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_ONE_PERSON:
                return getText('QMM019_33');
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_TWO_PERSON:
                return getText('QMM019_34');
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_THREE_PERSON:
                return getText('QMM019_35');
            case StatementLayoutPattern.LASER_PRINT_A4_LANDSCAPE_TWO_PERSON:
                return getText('QMM019_36');
            case StatementLayoutPattern.LASER_CRIMP_PORTRAIT_ONE_PERSON:
                return getText('QMM019_37');
            case StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON:
                return getText('QMM019_38');
            case StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON:
                return getText('QMM019_39');
            default:
                return "";
        }
    }

    export function getLayoutPatternContent(e: StatementLayoutPattern): string {
        switch (e) {
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_ONE_PERSON:
                // return "30行";
                return getText('QMM019_229');
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_TWO_PERSON:
                // return "17行";
                return getText('QMM019_230');
            case StatementLayoutPattern.LASER_PRINT_A4_PORTRAIT_THREE_PERSON:
                // return "10行";
                return getText('QMM019_231');
            case StatementLayoutPattern.LASER_PRINT_A4_LANDSCAPE_TWO_PERSON:
                // return "10行";
                return getText('QMM019_231');
            case StatementLayoutPattern.LASER_CRIMP_PORTRAIT_ONE_PERSON:
                // return "17行";
                return getText('QMM019_230');
            case StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON:
                // return "各カテゴリにつき6行 　52項目まで（記事項目は2行まで）";
                return getText('QMM019_232');
            case StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON:
                // return "印刷できる行数は固定";
                return getText('QMM019_233');
            default:
                return "";
        }
    }

    export function validateLayout(layoutPattern: number, totalLine: number, ctgAtr: number,
                                   printLineInCtg: number, noPrintLineInCtg: number, printSet: number): string {
        // if no print
        if (printSet == StatementPrintAtr.DO_NOT_PRINT) {
            if (noPrintLineInCtg >= 5) {
                return "MsgQ_20";
            } else {
                return null;
            }
        }

        if (layoutPattern == StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON) {
            return "MsgQ_21";
        }

        if (layoutPattern != StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON) {
            let maxPrintLine = getMaxPrintLineOfLayoutPattern(layoutPattern);

            if (totalLine >= maxPrintLine) {
                return "MsgQ_21";
            } else {
                return null;
            }
        }

        if (ctgAtr == CategoryAtr.REPORT_ITEM) {
            if (printLineInCtg >= 2) {
                return "MsgQ_21";
            } else {
                return null;
            }
        } else {
            if (printLineInCtg >= 6) {
                return "MsgQ_21";
            } else {
                return null;
            }
        }
    }

    export class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            let self = this;
            self.id = id;
            self.name = name;
        }
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: any, name: string) {
            this.code = code == null ? null : code.toString();
            this.name = name;
        }
    }
}