package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
@AllArgsConstructor
public class BentoMenuDto {

    public String contractCD;

    public String reservationFrameName1;

    public Integer reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public Integer reservationStartTime2;

    public Integer reservationEndTime2;

    public GeneralDate startDate;

    public GeneralDate endDate;

    public List<BentoDto> bentos;

    public void setWorkTypeSet(List<BentoDto> bentos) {
        this.bentos = bentos;
    }

}

