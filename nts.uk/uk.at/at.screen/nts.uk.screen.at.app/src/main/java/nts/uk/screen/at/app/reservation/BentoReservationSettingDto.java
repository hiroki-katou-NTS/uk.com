package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BentoReservationSettingDto {

    public int operationDistinction;

    public int referenceTime;

    public int orderDeadline;

    public int monthlyResults;

    public int dailyResults;

    public int contentChangeDeadline;

    public int contentChangeDeadlineDay;

}
