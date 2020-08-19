package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;

/**
 * 注文済みにする
 * @author Hoang Anh Tuan
 */

@Value
public class BentoMakeOrderCommand {

    /** param : 予約対象日 */
    private ReservationDate reservationDate;

    /** param : <<List>> 予約情報: 予約登録情報 */
    private ReservationRegisterInfo reservationRegisterInfo;

}
