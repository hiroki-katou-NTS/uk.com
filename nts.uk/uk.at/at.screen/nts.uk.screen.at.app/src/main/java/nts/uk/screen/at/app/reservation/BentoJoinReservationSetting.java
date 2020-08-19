package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.time.GeneralDate;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BentoJoinReservationSetting {

    //BentoSetting
    //予約の運用区別
    public int operationDistinction;

    //bentomenu
    public String reservationFrameName1;

    public int reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public int reservationStartTime2;

    public int reservationEndTime2;

    //bento
    public GeneralDate startDate;

    public GeneralDate endDate;

    public int frameNo;

    public String bentoName;

    public String unitName;

    public int price1;

    public int price2;

    public boolean reservationAtr1;

    public boolean reservationAtr2;

    public String workLocationCode;



    public static List<BentoJoinReservationSetting> setData(List<BentoDto> bentoDtos, BentoReservationSettingDto bentoReservationSettingDto){
        if (bentoDtos == null) return null;
        List<BentoJoinReservationSetting> listFullJoin = new ArrayList<>();
        for(BentoDto x : bentoDtos){
            listFullJoin.add(new BentoJoinReservationSetting(
                    bentoReservationSettingDto.getOperationDistinction(),
                    x.getReservationFrameName1(),
                    x.getReservationStartTime1(),
                    x.getReservationEndTime1(),
                    x.getReservationFrameName2(),
                    x.getReservationStartTime2(),
                    x.getReservationEndTime2(),
                    x.getStartDate(),
                    x.getEndDate(),
                    x.getFrameNo(),
                    x.getBentoName(),
                    x.getUnitName(),
                    x.getPrice1(),
                    x.getPrice2(),
                    x.isReservationAtr1(),
                    x.isReservationAtr2(),
                    x.getWorkLocationCode()
            ));
        }
        return listFullJoin;
    }
}
