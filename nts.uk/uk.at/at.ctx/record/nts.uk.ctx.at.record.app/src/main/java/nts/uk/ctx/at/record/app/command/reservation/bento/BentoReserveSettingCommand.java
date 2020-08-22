package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;

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
    Integer start1;
    String name2;
    Integer end2;
    Integer start2;

}
