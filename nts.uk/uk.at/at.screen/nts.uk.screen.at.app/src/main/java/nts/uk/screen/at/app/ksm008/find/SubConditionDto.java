package nts.uk.screen.at.app.ksm008.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCondition;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubConditionDto {
    //    サブ条件リスト.サブコード
    private String subCode;
    //    サブ条件リスト.説明
    private String explanation;
    /*
    サブ条件リスト.メッセージ.任意のメッセージ
    */
    private AlarmCheckMsgContentDto message;

    public static SubConditionDto toDto(SubCondition subCondition) {
        return new SubConditionDto(
                subCondition.getSubCode().v(),
                subCondition.getExplanation(),
                new AlarmCheckMsgContentDto(subCondition.getMessage().getDefaultMsg().v(), StringUtils.isNoneBlank(subCondition.getMessage().getMessage().v()) ? subCondition.getMessage().getMessage().v() : subCondition.getMessage().getDefaultMsg().v())
        );
    }
}
