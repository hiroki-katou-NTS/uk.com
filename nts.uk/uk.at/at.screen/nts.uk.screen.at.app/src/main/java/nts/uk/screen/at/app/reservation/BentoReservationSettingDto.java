package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BentoReservationSettingDto {

    //BentoSetting
    //予約の運用区別
    public int operationDistinction;

    //基準時間
    public int referenceTime;

    //予約済みの内容変更期限内容
    public int contentChangeDeadline;

    //予約済みの内容変更期限日数
    public int contentChangeDeadlineDay;

    //注文済み期限方法
    public int orderDeadline;

    //月別実績の集計
    public int monthlyResults;

    //日別実績の集計
    public int dailyResults;

    //注文済みデータ
    public int orderedData;

}
