package nts.uk.screen.at.app.ksm008.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AlarmCheckDto {
    /**
     * コード
     */
    private String code;
    /**
     * 条件名
     */
    private String name;
    /**
     * サブ条件リスト
     */
    private List<SubConditionDto> subConditions;

    public static AlarmCheckDto toDto(AlarmCheckConditionSchedule domain) {
        return new AlarmCheckDto(domain.getCode().v(), domain.getConditionName(), domain.getSubConditions().stream().map(subCondition -> SubConditionDto.toDto(subCondition)).collect(Collectors.toList()));
    }
}