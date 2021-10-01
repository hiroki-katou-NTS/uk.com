package nts.uk.ctx.at.record.pub.remainnumber.annualleave.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上限データExport
 * @author hayata_maekawa
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveMaxDataExport {
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 半日上限回数
	 */
	private Integer halfdayAnnualLeaveMaxTimes;
	
	/**
	 * 半日上限使用回数
	 */
	private Integer halfdayAnnualLeaveMaxUsedTimes;
	
	/**
	 * 半日上限残回数
	 */
	private Integer halfdayAnnualLeaveMaxRemainingTimes;
	
	/**
	 * 上限時間
	 */
	private Integer timeAnnualLeaveMaxMinutes;
	
	/**
	 * 上限使用時間
	 */
	private Integer timeAnnualLeaveMaxusedMinutes;
	/**
	 * 上限残時間
	 */
	private Integer timeAnnualLeaveMaxremainingMinutes;
	
}
