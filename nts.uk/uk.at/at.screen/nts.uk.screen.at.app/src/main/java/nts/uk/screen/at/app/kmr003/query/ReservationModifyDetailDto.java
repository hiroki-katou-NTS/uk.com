package nts.uk.screen.at.app.kmr003.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 予約明細
 * @author Le Huu Dat
 */
@Setter
@Getter
@AllArgsConstructor
public class ReservationModifyDetailDto {
    /** 個数 */
    private int bentoCount;
    /** 弁当メニュー枠番 */
    private int frameNo;
}
