package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.*;

import java.util.Optional;

@Value
public class BentoReserveSettingCommand {

    /** param : 予約の運用区別
     */
    int operationDistinction;

    int referenceTime;
    int dailyResults;
    int monthlyResults;

    int contentChangeDeadline;
    int contentChangeDeadlineDay;
    int orderedData;
    int orderDeadline;

    String name1;
    int end1;
    int start1;
    String name2;
    int end2;
    int start2;

//    /** param : 実績集計
//     */
//    AchievementValueObject achievements;
//
//    /** param : 予約修正内容
//     */
//    CorrectionContentValueObject correctionContent;
//
//    /** param : 予約締め時刻
//     */
//    ClosingTimeValueObject closingTime;

}
