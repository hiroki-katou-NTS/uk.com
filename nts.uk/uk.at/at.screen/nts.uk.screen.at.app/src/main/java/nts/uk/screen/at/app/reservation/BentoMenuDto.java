package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
@AllArgsConstructor
public class BentoMenuDto {

    //BentoMenu

    public String reservationFrameName1;

    public int reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public int reservationStartTime2;

    public int reservationEndTime2;

}

