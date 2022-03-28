package nts.uk.screen.at.app.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;

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
    
    // 予約修正内容.発注機能管理区分
    private boolean orderMngAtr;

    // 弁当の予約締め時刻
    private ReservationClosingTimeDto closingTime;

    // メニュー
    private List<BentoItemDto> menu;
}