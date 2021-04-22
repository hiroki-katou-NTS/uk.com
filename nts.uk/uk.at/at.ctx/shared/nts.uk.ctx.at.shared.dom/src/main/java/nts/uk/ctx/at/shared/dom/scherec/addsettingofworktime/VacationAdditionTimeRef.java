package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import lombok.AllArgsConstructor;

/**
 *  休暇加算時間参照先
 */
@AllArgsConstructor
public enum VacationAdditionTimeRef {

    // 日別実績の就業時間帯を参照（なければ会社設定を参照）
    REFER_ACTUAL_WORK_OR_COM_SET(0, "日別実績の就業時間帯を参照（なければ会社設定を参照）"),

    // 日別実績の就業時間帯を参照（なければ個人別設定を参照）
    REFER_ACTUAL_WORK_OR_INDIVIDUAL_SET(1, "日別実績の就業時間帯を参照（なければ個人別設定を参照）"),

    // 常に個人別設定を参照
    REFER_PERSONAL_SET(2, "常に個人別設定を参照");

    public final int value;

    public final String name;


}
