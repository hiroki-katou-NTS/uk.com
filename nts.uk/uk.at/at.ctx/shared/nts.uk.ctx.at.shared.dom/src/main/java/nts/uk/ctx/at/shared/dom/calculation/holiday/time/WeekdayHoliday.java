package nts.uk.ctx.at.shared.dom.calculation.holiday.time;
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

@AllArgsConstructor
@Getter
public class WeekdayHoliday extends DomainObject {

	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private OvertimeWorkFrameNo overTimeFrameNo;

	/** 変更後の残業枠NO */
	private BreakoutFrameNo legalHdNo;

	/** 変更後の法定外休出NO */
	private BreakoutFrameNo nonLegalHdNo;

	/** 変更後の祝日休出NO */
	private BreakoutFrameNo nonLegalPublicHdNo;

	/**
	 * Create from Java Type of WeekdayHoliday
	 * @param companyId
	 * @param overTimeFrameNo
	 * @param legalHdNo
	 * @param nonLegalHdNo
	 * @param nonLegalPublicHdNo
	 * @return
	 */
	public static WeekdayHoliday createFromJavaType(String companyId, BigDecimal overTimeFrameNo, int legalHdNo,
			int nonLegalHdNo, int nonLegalPublicHdNo) {
		return new WeekdayHoliday(companyId, new OvertimeWorkFrameNo(overTimeFrameNo), new BreakoutFrameNo(legalHdNo), new BreakoutFrameNo(nonLegalHdNo), new BreakoutFrameNo(nonLegalPublicHdNo));
	}
}
