package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BentoMenuJoinBentoSettingDto {

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
    private int orderedData;

    //BentoMenu

    public String reservationFrameName1;

    public int reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public int reservationStartTime2;

    public int reservationEndTime2;


    public static BentoMenuJoinBentoSettingDto setData(BentoMenuDto bentoMenuDto, BentoReservationSettingDto bentoReservationSettingDto){

        if (bentoMenuDto == null){
            return null;
        }
        return new BentoMenuJoinBentoSettingDto(
                bentoReservationSettingDto.operationDistinction,
                bentoReservationSettingDto.referenceTime,
                bentoReservationSettingDto.contentChangeDeadline,
                bentoReservationSettingDto.contentChangeDeadlineDay,
                bentoReservationSettingDto.orderDeadline,
                bentoReservationSettingDto.monthlyResults,
                bentoReservationSettingDto.dailyResults,
                bentoReservationSettingDto.orderedData,
                bentoMenuDto.reservationFrameName1,
                bentoMenuDto.reservationStartTime1,
                bentoMenuDto.reservationEndTime1,
                bentoMenuDto.reservationFrameName2,
                bentoMenuDto.reservationStartTime2,
                bentoMenuDto.reservationEndTime2
        );
    }
}
