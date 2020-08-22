package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class BentomenuJoinBentoDto {

    public String reservationFrameName1;

    public int reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public int reservationStartTime2;

    public int reservationEndTime2;

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
