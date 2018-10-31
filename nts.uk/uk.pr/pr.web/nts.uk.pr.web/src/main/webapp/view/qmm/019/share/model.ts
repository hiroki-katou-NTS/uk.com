module nts.uk.pr.view.qmm019.share.model {
    import getText = nts.uk.resource.getText;

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
     * 控除合計対象区分
     */
    export enum DeductionTotalObjAtr {
        // 合計対象外
        OUTSIDE = 0,
        // 合計対象内
        INSIDE = 1
    }

    export function getDeductionTotalObjAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(DeductionTotalObjAtr.OUTSIDE, '合計対象外'),
            new model.ItemModel(DeductionTotalObjAtr.INSIDE, '合計対象内'),
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
     * 按分設定区分
     */
    export enum ProportionalDivisionSetAtr {
        // 按分しない
        DO_NOT = 0,
        //按分する
        DO = 1,
        //計算式
        ONCE_MONTH = 2
    }

    export function getProportionalDivisionSetAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ProportionalDivisionSetAtr.DO_NOT, getText('QMM019_116')),
            new model.ItemModel(ProportionalDivisionSetAtr.DO, getText('QMM019_117')),
            new model.ItemModel(ProportionalDivisionSetAtr.ONCE_MONTH, getText('QMM019_162'))
        ];
    }

    /**
     * 按分割合設定区分
     */
    export enum ProportionalDivisionRatioSetAtr {
        ENUM_1 = 0,
        ENUM_2 = 1,
        ENUM_3 = 2,
        ENUM_4 = 3
    }

    export function getProportionalDivisionRatioSetAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(ProportionalDivisionRatioSetAtr.ENUM_1, getText('ENUM_1')),
            new model.ItemModel(ProportionalDivisionRatioSetAtr.ENUM_2, getText('ENUM_2')),
            new model.ItemModel(ProportionalDivisionRatioSetAtr.ENUM_3, getText('ENUM_3')),
            new model.ItemModel(ProportionalDivisionRatioSetAtr.ENUM_4, getText('ENUM_4'))
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