package nts.uk.ctx.at.record.dom.remainingnumber.publicholiday;

import lombok.Getter;

/**
 * 公休付与残数データ
 * @author Hop.NT
 *
 */
@Getter
public class PublicHolidayRemain {
	
	// 社員ID
	private String employeeId;
	
	// 残数
	private NumberDaysRemain numberDaysRemain;
}
