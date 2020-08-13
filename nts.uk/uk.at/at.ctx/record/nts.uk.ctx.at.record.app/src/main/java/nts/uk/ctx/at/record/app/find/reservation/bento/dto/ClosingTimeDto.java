package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

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
    /** 名前 */
    private String name;
    /** 終了時刻 */
    private int endTime;
    /** 開始時刻 */
    private Integer startTime;
}
