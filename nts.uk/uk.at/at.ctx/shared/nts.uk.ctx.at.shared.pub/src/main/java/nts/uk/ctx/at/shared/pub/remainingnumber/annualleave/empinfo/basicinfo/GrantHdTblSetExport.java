package nts.uk.ctx.at.shared.pub.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.Getter;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.CalculationMethod;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.UseSimultaneousGrant;

/**
 * 年休付与テーブル設定Export
 * @author shuichi_ishida
 */
@Getter
public class GrantHdTblSetExport {

	/** コード */
	private String code;
	/** 計算方法 */
	private CalculationMethod calculationMethod;
	/** 計算基準 */
	private StandardCalculation standardCalculation;
	/** 一斉付与を利用する */
	private UseSimultaneousGrant useSimultaneousGrant;

	public GrantHdTblSetExport(
			String yearHolidayCode,
			CalculationMethod calculationMethod,
			StandardCalculation standardCalculation,
			UseSimultaneousGrant useSimultaneousGrant) {

		this.code = yearHolidayCode;
		this.calculationMethod = calculationMethod;
		this.standardCalculation = standardCalculation;
		this.useSimultaneousGrant = useSimultaneousGrant;
	}
}
