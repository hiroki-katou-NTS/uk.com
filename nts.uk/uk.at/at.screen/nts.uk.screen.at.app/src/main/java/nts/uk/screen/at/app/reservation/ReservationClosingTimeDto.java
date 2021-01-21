package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationClosingTimeDto {

	//名前
    private String reservationFrameName1;

	//開始
	private int reservationStartTime1;

	//終了
    private int reservationEndTime1;

	//名前
	private String reservationFrameName2;

	//開始
	private Integer reservationStartTime2;

	//終了
	private Integer reservationEndTime2;
}

