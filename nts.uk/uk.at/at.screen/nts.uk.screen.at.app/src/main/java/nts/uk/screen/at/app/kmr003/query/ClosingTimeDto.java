package nts.uk.screen.at.app.kmr003.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 締め時刻
 * @author Le Huu Dat
 */
@Setter
@Getter
@AllArgsConstructor
public class ClosingTimeDto {
    /** 締め時刻枠 */
    private int closingTimeFrame;
    /** 名前 */
    private String name;
    /** 終了時刻 */
    private int endTime;
    /** 開始時刻 */
    private Integer startTime;
}
