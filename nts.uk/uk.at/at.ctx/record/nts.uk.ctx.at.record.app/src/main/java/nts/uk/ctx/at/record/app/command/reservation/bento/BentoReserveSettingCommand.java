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
    private int operationDistinction;

    private int referenceTime;
    private int dailyResults;
    private int monthlyResults;

    private int contentChangeDeadline;
    private int contentChangeDeadlineDay;
    private int orderedData;
    private int orderDeadline;

    private String name1;
    private int end1;
    private int start1;
    private String name2;
    private int end2;
    private int start2;

//    /** param : 実績集計
//     */
//    private AchievementValueObject achievements;
//
//    /** param : 予約修正内容
//     */
//    private CorrectionContentValueObject correctionContent;
//
//    /** param : 予約締め時刻
//     */
//    private ClosingTimeValueObject closingTime;

}
