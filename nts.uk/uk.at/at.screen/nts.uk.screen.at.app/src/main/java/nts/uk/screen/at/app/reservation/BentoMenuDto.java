package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.screen.at.app.worktype.WorkTypeSetDto;

import javax.persistence.Column;
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

