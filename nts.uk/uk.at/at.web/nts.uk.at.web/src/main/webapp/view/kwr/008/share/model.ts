module nts.uk.at.view.kwr008.share.model {

    export interface EnumConstantDto {
        value: number;
        fieldName: string;
        localizedName: string;
    }

    export interface OutputSettingCodeDto {
        cd: string;
        name: string;
        outNumExceedTime36Agr: boolean;
        displayFormat: number;
    }

    export class ItemModel {
        code: any;
        name: any;
        constructor(code: any, name: any) {
            this.code = code;
            this.name = name;
        }
    }

    export class SetOutputItemOfAnnualWorkSchDto {
        cd: string;             // コード
        name: string;           // 名称
        printForm: number;      // 印刷形式
        layoutId: string;       // 項目設定ID
        settingType: number;    // 項目選択種類
        listItemsOutput: ItemsOutputToBookTableDto[];
        outNumExceedTime36Agr: boolean;
        multiMonthDisplay: boolean;
        newMode: boolean;

        constructor(init?: Partial<SetOutputItemOfAnnualWorkSchDto>) {
            $.extend(this, init);
        }
    }

    export interface ItemsOutputToBookTableDto {
        /** 並び順. */
        sortBy: number;

        /** 使用区分. */
        useClass: boolean;

        /** 値の出力形式. */
        valOutFormat: number;

        /** 出力対象項目 */
        listOperationSetting: CalculationFormulaOfItemDto[];

        /** 見出し名称. */
        headingName: string;
    }

    export interface CalculationFormulaOfItemDto {
        /** オペレーション. */
        operation: number;
        /** 勤怠項目. */
        attendanceItemId: number;
    }

    export class SelectionClassification {
        static STANDARD = 0;
        static FREE_SETTING = 1;
    }

    export class AnnualWorkSheetPrintingForm {
        // 勤怠チェックリスト
        static TIME_CHECK_LIST = 0;
        // 36協定チェックリスト
        static AGREEMENT_CHECK_36 = 1;
    }

    export class AttendanceItemDto {
        /** 勤怠項目ID */
        attendanceItemId: number;
        /** 勤怠項目名称 */
        attendanceItemName: string;
        /** 勤怠項目の属性 */
        attendanceAtr: number;
        /** マスタの種類 */
        masterType: number | null;
        /** 表示番号 */
        displayNumbers: number;
    }

    export class AtdItemKDL048Model {
        id: any;
        name: any;
        attendanceAtr: any;
        indicatesNumber: any;

        constructor(init?: Partial<AtdItemKDL048Model>) {
          $.extend(this, init);
        }
    }

    export class SelectedTimeItem {
        itemId: any;
        operator: string;

        constructor(init?: Partial<SelectedTimeItem>) {
            $.extend(this, init);
        }
    }

    export class AttendanceItemShare {
        // タイトル行
        titleLine: TitleLine = new TitleLine();
        // 項目名行
        itemNameLine: ItemNameLine = new ItemNameLine();
        // 属性
        attribute: Attribute = new Attribute();
        // List<勤怠項目>
        attendanceItems: Array<AttendanceItemDto> = [];
        // 選択済み勤怠項目ID
        selectedTime: number;
        // 加減算する項目
        selectedTimeList: Array<SelectedTimeItem>;
        // columnIndex
        columnIndex: number;
        // position
        position: number;
        // exportAtr
        exportAtr: number;
        //List<勤怠項目> for KDL048
        diligenceProjectList: any;

        constructor(init?: Partial<AttendanceItemShare>) {
            $.extend(this, init);
        }
    }

    export class TitleLine {
        // 表示フラグ
        displayFlag: boolean;
        // 出力項目コード
        layoutCode: String;
        // 出力項目名
        layoutName: String;
        // コメント
        directText: String;
    }

    export class ItemNameLine {
        // 表示フラグ
        displayFlag: boolean;
        // 表示入力区分
        displayInputCategory: number;
        // 名称
        name: String;
    }

    export class Attribute {
        // 選択区分
        selectionCategory: number;
        // List<属性>
        attributeList: Array<AttendaceType>;
        // 選択済み
        selected: number;
    }
    export class AttendaceType {
        attendanceTypeCode: number;
        attendanceTypeName: string;
        constructor(attendanceTypeCode: number, attendanceTypeName: string) {
            this.attendanceTypeCode = attendanceTypeCode;
            this.attendanceTypeName = attendanceTypeName;
        }
    }
}