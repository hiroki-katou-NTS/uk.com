package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationClosingTimeDto {
    private String reservationFrameName1;
	private int reservationStartTime1;
    private int reservationEndTime1;
	private String reservationFrameName2;
	private Integer reservationStartTime2;
	private Integer reservationEndTime2;
}

