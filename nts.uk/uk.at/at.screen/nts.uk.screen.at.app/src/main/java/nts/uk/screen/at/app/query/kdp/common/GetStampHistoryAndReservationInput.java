package nts.uk.screen.at.app.query.kdp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetStampHistoryAndReservationInput {

	private String sid;

	private GeneralDate startDate;

	private GeneralDate endDate;

}
