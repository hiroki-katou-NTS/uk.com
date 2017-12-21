package nts.uk.ctx.at.shared.dom.calculation.holiday.time;
/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

@AllArgsConstructor
@Getter
public class WeekdayHoliday extends DomainObject {

	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private int overTimeFrameNo;

	/** 変更後の残業枠NO */
	private int legalHdNo;

	/** 変更後の法定外休出NO */
	private int nonLegalHdNo;

	/** 変更後の祝日休出NO */
	private int nonLegalPublicHdNo;

	/**
	 * Create from Java Type of WeekdayHoliday
	 * @param companyId
	 * @param overTimeFrameNo
	 * @param legalHdNo
	 * @param nonLegalHdNo
	 * @param nonLegalPublicHdNo
	 * @return
	 */
	public static WeekdayHoliday createFromJavaType(String companyId, int overTimeFrameNo, int legalHdNo,
			int nonLegalHdNo, int nonLegalPublicHdNo) {
		return new WeekdayHoliday(companyId, overTimeFrameNo, legalHdNo, nonLegalHdNo, nonLegalPublicHdNo);
	}
}
