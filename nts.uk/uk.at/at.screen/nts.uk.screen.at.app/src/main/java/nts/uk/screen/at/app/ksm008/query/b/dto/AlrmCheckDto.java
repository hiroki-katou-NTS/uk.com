package nts.uk.screen.at.app.ksm008.query.b.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.screen.at.app.ksm008.find.AlarmCheckMsgContentDto;
import nts.uk.screen.at.app.ksm008.find.SubConditionDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlrmCheckDto {

    // コード
    private String code;
    // 条件名
    private String name;

    private List<SubConditionDto> subConditionList;

    public static AlrmCheckDto fromDomain(AlarmCheckConditionSchedule domain) {
        if (domain == null) return null;
        return new AlrmCheckDto(
                domain.getCode().v(),
                domain.getConditionName(),
                domain.getSubConditions().stream().map(i -> new SubConditionDto(
                        i.getSubCode().v(),
                        i.getExplanation(),
                        new AlarmCheckMsgContentDto(i.getMessage().getDefaultMsg().v(), i.getMessage().getMessage().v())
                )).collect(Collectors.toList())
        );
    }

}
