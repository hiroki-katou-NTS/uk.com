package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BentomenuJoinBentoDto {

    //名前
    public String reservationFrameName1;

    //開始
    public Integer reservationStartTime1;

    //終了
    public int reservationEndTime1;

    //名前
    public String reservationFrameName2;

    //開始
    public Integer reservationStartTime2;

    //終了
    public Integer reservationEndTime2;

    //Bento

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

    public String workLocationName;

}
