package nts.uk.ctx.at.record.pub.remainnumber.annualleave.export;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 年休残明細Export
 * @author hayata_maekawa
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveRemainingDetailExport {
	
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 日数
	 */
	private Double days;
	
	/**
	 * 時間
	 */
	private Integer time;
}
