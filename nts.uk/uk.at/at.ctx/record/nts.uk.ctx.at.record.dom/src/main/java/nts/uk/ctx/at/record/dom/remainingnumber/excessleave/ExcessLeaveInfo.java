package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 * domain 超過有休基本情報
 * @author Hop.NT
 *
 */
@Getter
public class ExcessLeaveInfo {
	
	// 社員ID
	private String employeeId;
	
	// 使用区分
	private UseAtr useClassification;
	
	// 発生単位
	private OccurrenceUnit occurrenceUnit;
	
	
	// 精算方法
	private PaymentMethod paymentMethod;
}
