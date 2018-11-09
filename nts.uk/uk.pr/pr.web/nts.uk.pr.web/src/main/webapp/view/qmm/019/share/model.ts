module nts.uk.pr.view.qmm019.share.model {
    import getText = nts.uk.resource.getText;

    export enum CategoryAtr{
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
                return "支給項目";
            case CategoryAtr.DEDUCTION_ITEM:
                return "控除項目";
            case CategoryAtr.ATTEND_ITEM:
                return "勤怠項目";
            case CategoryAtr.REPORT_ITEM:
                return "記事項目";
            case CategoryAtr.OTHER_ITEM:
                return "その他項目";
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
        if(printSet == StatementPrintAtr.PRINT){
            return [
                new model.ItemModel(PaymentTotalObjAtr.INSIDE, '合計対象内'),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE, '合計対象外'),
                new model.ItemModel(PaymentTotalObjAtr.INSIDE_ACTUAL, '合計対象内（現物）'),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE_ACTUAL, '合計対象外（現物）')
            ];
        }
        else{
            return [
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE, '合計対象外'),
                new model.ItemModel(PaymentTotalObjAtr.OUTSIDE_ACTUAL, '合計対象外（現物）')
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
        if (printSet == StatementPrintAtr.PRINT) {
            return [
                new model.ItemModel(DeductionTotalObjAtr.OUTSIDE, '合計対象外'),
                new model.ItemModel(DeductionTotalObjAtr.INSIDE, '合計対象内')
            ];
        }
        else {
            return [
                new model.ItemModel(DeductionTotalObjAtr.OUTSIDE, '合計対象外')
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

    export function getPaymentCaclMethodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(PaymentCaclMethodAtr.MANUAL_INPUT, '手入力'),
            new model.ItemModel(PaymentCaclMethodAtr.PERSON_INFO_REF, '個人情報参照'),
            new model.ItemModel(PaymentCaclMethodAtr.CACL_FOMULA, '計算式'),
            new model.ItemModel(PaymentCaclMethodAtr.WAGE_TABLE, '賃金テーブル'),
            new model.ItemModel(PaymentCaclMethodAtr.COMMON_AMOUNT, '共通金額'),
            new model.ItemModel(PaymentCaclMethodAtr.BREAKDOWN_ITEM, '内訳項目')
        ];
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

    export function getDeductionCaclMethodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(DeductionCaclMethodAtr.MANUAL_INPUT, '手入力'),
            new model.ItemModel(DeductionCaclMethodAtr.PERSON_INFO_REF, '個人情報参照'),
            new model.ItemModel(DeductionCaclMethodAtr.CACL_FOMULA, '計算式'),
            new model.ItemModel(DeductionCaclMethodAtr.WAGE_TABLE, '賃金テーブル'),
            new model.ItemModel(DeductionCaclMethodAtr.COMMON_AMOUNT, '共通金額'),
            new model.ItemModel(DeductionCaclMethodAtr.SUPPLY_OFFSET, '支給相殺'),
            new model.ItemModel(DeductionCaclMethodAtr.BREAKDOWN_ITEM, '内訳項目')
        ];
    }

    /**
     * 支給按分区分
     */
    export enum PaymentProportionalAtr {
        // 按分しない
        PROPORTIONAL = 0,
        //按分する
        NOT_PROPORTIONAL = 1,
        //月１回支給
        PAYMENT_ONE_A_MONTH = 2
    }

    export function getPaymentProportionalAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(PaymentProportionalAtr.PROPORTIONAL, getText('QMM019_116')),
            new model.ItemModel(PaymentProportionalAtr.NOT_PROPORTIONAL, getText('QMM019_117')),
            new model.ItemModel(PaymentProportionalAtr.PAYMENT_ONE_A_MONTH, getText('QMM019_118'))
        ];
    }

    /**
     * 控除按分区分
     */
    export enum DeductionProportionalAtr {
        // 按分しない
        PROPORTIONAL = 0,
        //按分する
        NOT_PROPORTIONAL = 1,
        //月１回控除
        DEDUCTION_ONCE_A_MONTH = 2
    }

    export function getDeductionProportionalAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(DeductionProportionalAtr.PROPORTIONAL, getText('QMM019_116')),
            new model.ItemModel(DeductionProportionalAtr.NOT_PROPORTIONAL, getText('QMM019_117')),
            new model.ItemModel(DeductionProportionalAtr.DEDUCTION_ONCE_A_MONTH, getText('QMM019_162'))
        ];
    }

    /**
     * 通勤区分
     */
    export enum WorkingAtr{
        // 交通機関
        TRANSPORT_FACILITIES = 0,
        // 交通用具
        TRANSPORT_EQUIPMENT = 1
    }

    export function getWorkingAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(WorkingAtr.TRANSPORT_FACILITIES, getText('交通機関')),
            new model.ItemModel(WorkingAtr.TRANSPORT_EQUIPMENT, getText('交通用具'))
        ];
    }

    /**
     * 範囲利用区分
     */
    export enum UseRangeAtr{
        // 設定する
        USE = 1,
        // 設定しない
        NOT_USE = 0
    }

    /**
     * 範囲値の属性
     */
    export enum RangeValueEnum{
        AMOUNT_OF_MONEY = 0,
        TIME = 1,
        TIMES = 2
    }

    /**
     * 按分方法区分
     */
    export enum ProportionalMethodAtr{
        // 割合で計算
        BY_PROPORTION = 0,
        // 日数控除
        DAYS_DEDUCTION = 1
    }

    export function getProportionalMethodAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ProportionalMethodAtr.BY_PROPORTION, getText('割合で計算')),
            new model.ItemModel(ProportionalMethodAtr.DAYS_DEDUCTION, getText('日数控除'))
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
                return "課税";
            case TaxAtr.LIMIT_TAX_EXEMPTION:
                return "非課税（限度あり）";
            case TaxAtr.NO_LIMIT_TAX_EXEMPTION:
                return "非課税（限度なし）";
            case TaxAtr.COMMUTING_EXPENSES_MANUAL:
                return "通勤費（手入力）";
            case TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER:
                return "通勤費（定期券利用）";
            default:
                return "";
        }
    }

    /**
     * 固定的賃金の設定区分
     */
    export enum SocialInsuranceCategory{
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getSocialInsuranceCategoryText(e: SocialInsuranceCategory){
        switch (e){
            case SocialInsuranceCategory.NOT_COVERED: return "対象外";
            case SocialInsuranceCategory.COVERED: return "対象";
            default: return "";
        }
    }

    /**
     * 労働保険区分
     */
    export enum LaborInsuranceCategory{
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getLaborInsuranceCategoryText(e: LaborInsuranceCategory){
        switch (e){
            case LaborInsuranceCategory.NOT_COVERED: return "対象外";
            case LaborInsuranceCategory.COVERED: return "対象";
            default: return "";
        }
    }

    /**
     * 固定的賃金の対象区分
     */
    export enum CategoryFixedWage{
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getCategoryFixedWageText(e: CategoryFixedWage): string {
        switch (e) {
            case CategoryFixedWage.NOT_COVERED:
                return "対象外";
            case CategoryFixedWage.COVERED:
                return "対象";
            default:
                return "";
        }
    }

    /**
     * 平均賃金区分
     */
    export enum AverageWageAtr{
        // 対象外
        NOT_COVERED = 0,
        // 対象
        COVERED = 1
    }

    export function getAverageWageAtrText(e: AverageWageAtr){
        switch (e){
            case AverageWageAtr.NOT_COVERED: return "対象外";
            case AverageWageAtr.COVERED: return "対象";
            default: return "";
        }
    }

    /**
     * 既定区分
     */
    export enum DefaultAtr{
        // ユーザ作成
        USER_CREATE = 0,
        // システム既定
        SYSTEM_DEFAULT = 1
    }

    /**
     * 明細書印字区分
     */
    export enum StatementPrintAtr{
        PRINT = 0,
        DO_NOT_PRINT = 1
    }


    // 年月期間の汎用履歴項目
    export interface IGenericHistoryYearMonthPeiod {
        startMonth: string;
        endMonth: string;
        historyId: string;
    }

    // 年月期間の汎用履歴項目
    export class GenericHistoryYearMonthPeiod {
        startMonth: string;
        endMonth: string;
        historyId: string;

        // for tree grid
        nodeText: string;
        history: Array<GenericHistoryYearMonthPeiod>;

        constructor(params: IGenericHistoryYearMonthPeiod) {
            this.startMonth = params.startMonth;
            this.endMonth = params.endMonth;
            this.historyId = params.historyId;
            this.nodeText = params.startMonth + " ~ " + params.endMonth;
            this.history = [];
        }
    }

    export interface ISpecificationLayoutHist {
        specCode: string;
        name: string;
        history: Array<IGenericHistoryYearMonthPeiod>;
    }

    export class SpecificationLayoutHist {
        specCode: string;
        name: string;
        history: Array<GenericHistoryYearMonthPeiod>;

        // for tree grid
        nodeText: string;
        historyId: string;

        constructor(params: ISpecificationLayoutHist) {
            this.specCode = params ? params.specCode : null;
            this.name = params ? params.name : null;
            this.history = params ? params.history.map(function (item) {
                return new GenericHistoryYearMonthPeiod(item);
            }) : [];

            this.historyId = params ? params.specCode : null;
            this.nodeText = params ? params.specCode + " " + params.name : null;
        }
    }

    export class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
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