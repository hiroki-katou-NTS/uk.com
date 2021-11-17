package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ReservationRecTimeDto {
    /**
     * 受付名称
     */
    private String receptionName;

    /**
     * 開始時刻
     */
    private int startTime;

    /**
     * 終了時刻
     */
    private int endTime;

    public static ReservationRecTimeDto fromDomain(ReservationRecTime domain) {
        return new ReservationRecTimeDto(domain.getReceptionName().v(), domain.getStartTime().v(),
                domain.getEndTime().v());
    }
}
