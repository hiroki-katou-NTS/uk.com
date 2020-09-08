package nts.uk.screen.at.ws.kmr.kmr003.a;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

@Setter
@Getter
public class ReservationModifyParam {
    private List<String> empIds;
    private GeneralDate date;
    private int closingTimeFrame;
    private int searchCondition;
}
