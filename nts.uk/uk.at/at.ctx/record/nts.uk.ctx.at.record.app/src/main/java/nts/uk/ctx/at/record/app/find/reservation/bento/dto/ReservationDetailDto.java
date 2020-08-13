package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

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
public class ReservationDetailDto {
    /** 個数 */
    private int number;
    /** 弁当メニュー枠番 */
    private int bentoMenuNo;
}
