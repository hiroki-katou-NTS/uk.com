package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FixedExtractionConditionDto {

    // ID
    private String id;

    // No
    private int no;

    // 使用区分
    private boolean useAtr;

    // 表示するメッセージ
    private String displayMessage;

}
