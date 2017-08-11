package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.List;

import lombok.Getter;
import nts.uk.shr.com.primitive.Memo;

/**
 * 年休付与テーブル設定
 * 
 * @author TanLV
 *
 */

@Getter
public class YearHoliday {
	/* 会社ID */
	private String companyId;

	/* コード */
	private YearHolidayCode yearHolidayCode;

	/* 名称 */
	private YearHolidayName yearHolidayName;

	/* 計算方法 */
	private int calculationMethod;

	/* 計算基準 */
	private int standardCalculation;

	/* 一斉付与を利用する */
	private int useSimultaneousGrant;

	/* 一斉付与日 */
	private int simultaneousGrandMonthDays;

	/* 備考 */
	private Memo yearHolidayNote;
	
	private List<GrantingCondition> grantConditions;

	public YearHoliday(String companyId, YearHolidayCode yearHolidayCode, YearHolidayName yearHolidayName, int calculationMethod,
			int standardCalculation, int useSimultaneousGrant, int simultaneousGrandMonthDays, Memo yearHolidayNote, List<GrantingCondition> grantConditions) {

		this.companyId = companyId;
		this.yearHolidayCode = yearHolidayCode;
		this.yearHolidayName = yearHolidayName;
		this.calculationMethod = calculationMethod;
		this.standardCalculation = standardCalculation;
		this.useSimultaneousGrant = useSimultaneousGrant;
		this.simultaneousGrandMonthDays = simultaneousGrandMonthDays;
		this.yearHolidayNote = yearHolidayNote;
		this.grantConditions = grantConditions;
	}
}
