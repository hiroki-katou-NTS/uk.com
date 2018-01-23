package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TargetServiceType {
    /* 全て */
    ALL(0, "Enum_TargetServiceType_All"),
    /* 選択 */
    SELECTION(1, "Enum_TargetServiceType_Selection"),
    /* 選択以外*/
    OTHER_SELECTION(2, "Enum_TargetServiceType_OtherSelection");
    
    public final int value;

    public final String nameId;
}
