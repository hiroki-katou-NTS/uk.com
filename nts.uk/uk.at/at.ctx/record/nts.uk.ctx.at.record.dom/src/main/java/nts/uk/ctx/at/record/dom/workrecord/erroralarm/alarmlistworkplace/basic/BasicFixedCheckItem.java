package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).固定のチェック基本項目
 */

@RequiredArgsConstructor
public enum BasicFixedCheckItem {

    //職場関連マスタ未登録
    WORKPLACE_MASTER_NOT_REGISTER(1, "職場関連マスタ未登録"),
    // 基準時間の未設定
    NO_REF_TIME_SET(2, "基準時間の未設定"),
    // 36協定目安時間の未設定
    EST36_TIME_NOT_SET(3, "36協定目安時間の未設定"),
    // 公休日数の未設定
    UNSET_PUBLIC_HD(4, "公休日数の未設定"),
    // 目安時間・金額時間の未設定
    EST_TIME_AMOUNT_NOT_SET(5, "目安時間・金額時間の未設定"),
    // 必須データ不十分
    INS_REQUIRED_DATA(6, "必須データ不十分"),
    // 雇用コード確認
    EMPLOYMENT_CODE_CONFIRMATION(7, "雇用コード確認"),
    // 部門コード確認
    DEPARTMENT_CODE_CONFIRMATION(8, "部門コード確認"),
    // 分類コード確認
    CLS_CODE_CONFIRMATION(9, "分類コード確認"),
    // 職位コード確認
    POSITION_CODE_CONFIRMATION(10, "職位コード確認"),
    // 職場コード確認
    WORKPLACE_CODE_CONFIRMATION(11, "職場コード確認");

    public final int value;
    public final String nameId;

    public static BasicFixedCheckItem of(int value) {
        return EnumAdaptor.valueOf(value, BasicFixedCheckItem.class);
    }

}
