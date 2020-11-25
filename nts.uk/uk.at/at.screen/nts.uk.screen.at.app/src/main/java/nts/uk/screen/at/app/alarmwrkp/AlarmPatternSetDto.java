package nts.uk.screen.at.app.alarmwrkp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlarmPatternSetDto {
    /**
     * アラームリストパターンコード
     */
    private String alarmPatternCD;
    /**
     * アラームリストパターン名称
     */
    private String alarmPatternName;
}
