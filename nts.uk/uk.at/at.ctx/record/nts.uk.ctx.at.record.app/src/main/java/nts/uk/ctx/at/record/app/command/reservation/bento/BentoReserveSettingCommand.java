package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;

@Value
public class BentoReserveSettingCommand {

    //予約の運用区別
    int operationDistinction;

    //基準時間
    int referenceTime;

    //日別実績の集計
    int dailyResults;

    //月別実績の集計
    int monthlyResults;

    //予約済みの内容変更期限
    int contentChangeDeadline;

    //予約済みの内容変更期限日数
    int contentChangeDeadlineDay;

    //注文済みデータ
    int orderedData;

    //注文済みデータ
    int orderDeadline;

    //名前
    String name1;

    //終了
    int end1;

    //開始
    Integer start1;

    //名前
    String name2;

    //終了
    Integer end2;

    //開始
    Integer start2;

}
