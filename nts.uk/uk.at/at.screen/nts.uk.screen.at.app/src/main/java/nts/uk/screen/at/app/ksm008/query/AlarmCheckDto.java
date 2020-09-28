package nts.uk.screen.at.app.ksm008.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCheckDto {
    //    コード
    private String code;
    //            条件名
    private String name;
    private List<SubConditionDto> subConditionList;

    public static AlarmCheckDto startScreen(WaitingDomain domain) {
        return new AlarmCheckDto(domain.getCode(), domain.getName(), Collections.emptyList());
    }
}

@Getter
@AllArgsConstructor
class WaitingDomain {
    private String code;
    private String name;
}
