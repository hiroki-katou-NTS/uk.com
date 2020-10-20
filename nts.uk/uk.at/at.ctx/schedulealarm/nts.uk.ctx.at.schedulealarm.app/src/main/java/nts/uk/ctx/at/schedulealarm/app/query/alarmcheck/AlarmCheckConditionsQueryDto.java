package nts.uk.ctx.at.schedulealarm.app.query.alarmcheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCheckConditionsQueryDto {
    /**
     * 条件名
     */
    private String conditionName;

    /**
     * サブ条件リスト.説明
     */
    private List<String> explanationList;
}


