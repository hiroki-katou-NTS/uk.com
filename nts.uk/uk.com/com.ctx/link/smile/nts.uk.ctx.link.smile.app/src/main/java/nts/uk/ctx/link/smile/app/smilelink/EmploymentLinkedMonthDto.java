package nts.uk.ctx.link.smile.app.smilelink;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class EmploymentLinkedMonthDto {
	// 連動月調整
	private int interlockingMonthAdjustment;
	
	// 選択雇用コード
	private String scd;

	public EmploymentLinkedMonthDto(int interlockingMonthAdjustment, String scd) {
		super();
		this.interlockingMonthAdjustment = interlockingMonthAdjustment;
		this.scd = scd;
	}
}
