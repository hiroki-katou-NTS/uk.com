package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class BentoDto {

    //bento

    //枠番
    public int frameNo;

    //弁当名
    public String bentoName;

    //単位
    public String unitName;

    //金額１
    public int price1;

    //金額２
    public int price2;

    //締め時刻1で予約可能
    public boolean reservationAtr1;

    //締め時刻2で予約可能
    public boolean reservationAtr2;

    //勤務場所コード
    public String workLocationCode;

    public String workLocationName;

}
