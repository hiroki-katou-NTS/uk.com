package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ReservationRecTimeZoneDto {

    /**
     * 受付時間帯
     */
    private ReservationRecTimeDto receptionHours;

    /**
     * 枠NO
     */
    private int frameNo;

    public static ReservationRecTimeZoneDto fromDomain(ReservationRecTimeZone domain) {
        return new ReservationRecTimeZoneDto(ReservationRecTimeDto.fromDomain(domain.getReceptionHours()),
                domain.getFrameNo().value);
    }
}
