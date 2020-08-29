package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class BentoDto {

    //bento

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
