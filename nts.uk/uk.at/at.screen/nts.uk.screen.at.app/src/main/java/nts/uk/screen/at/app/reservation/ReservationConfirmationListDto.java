package nts.uk.screen.at.app.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.query.reservation.ReservationClosingTimeDto;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;

import java.util.List;

/**
 * @author 3si - Dang Huu Khai
 */
@Getter
@Setter
@NoArgsConstructor
public class ReservationConfirmationListDto {

    //予約の運用区別
    private OperationDistinction operationDistinction;

    // 弁当の予約締め時刻
    private BentoReservationClosingTime closingTime;

    // メニュー
    private List<Bento> menu;
}
