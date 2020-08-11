package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;

@Value
public class BentoReserveSettingCommand {

    /** param : 予約の運用区別
     */
    private OperationDistinction operationDistinction;

    /** param : 実績集計
     */
    private Achievements achievements;

    /** param : 予約修正内容
     */
    private CorrectionContent correctionContent;

    /** param : 予約締め時刻
     */
    private BentoReservationClosingTime closingTime;

}
