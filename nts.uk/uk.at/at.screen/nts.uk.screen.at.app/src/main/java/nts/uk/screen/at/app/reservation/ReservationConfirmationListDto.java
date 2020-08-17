package nts.uk.screen.at.app.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;

/**
 * @author 3si - Dang Huu Khai
 */
@Getter
@Setter
@NoArgsConstructor
public class ReservationConfirmationListDto {
    private OperationDistinction operationDistinction;
}
