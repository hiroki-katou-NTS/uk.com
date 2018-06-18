package nts.uk.ctx.at.shared.pub.remainingnumber.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveGrantRemainDataExport {
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 明細．付与数．日数
	 */
	private Double grantNumberDays;

}
