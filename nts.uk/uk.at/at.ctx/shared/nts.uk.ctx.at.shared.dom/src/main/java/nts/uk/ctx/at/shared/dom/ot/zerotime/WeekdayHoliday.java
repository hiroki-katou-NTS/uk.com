package nts.uk.ctx.at.shared.dom.ot.zerotime;

import java.math.BigDecimal;

/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;

/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class WeekdayHoliday extends DomainObject {

	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private OvertimeWorkFrameNo overworkFrameNo;

	/** 変更後の残業枠NO */
	private BreakoutFrameNo weekdayNo;

	/** 変更後の法定外休出NO */
	private BreakoutFrameNo excessHolidayNo;

	/** 変更後の祝日休出NO */
	private BreakoutFrameNo excessSphdNo;

	/**
	 * Create from Java Type of WeekdayHoliday
	 * 
	 * @param companyId
	 * @param overTimeFrameNo
	 * @param legalHdNo
	 * @param nonLegalHdNo
	 * @param nonLegalPublicHdNo
	 * @return
	 */
	public static WeekdayHoliday createFromJavaType(String companyId, BigDecimal overworkFrameNo, int weekdayNo,
			int excessHolidayNo, int excessSphdNo) {
		return new WeekdayHoliday(companyId, new OvertimeWorkFrameNo(overworkFrameNo), new BreakoutFrameNo(weekdayNo),
				new BreakoutFrameNo(excessHolidayNo), new BreakoutFrameNo(excessSphdNo));
	}
}
