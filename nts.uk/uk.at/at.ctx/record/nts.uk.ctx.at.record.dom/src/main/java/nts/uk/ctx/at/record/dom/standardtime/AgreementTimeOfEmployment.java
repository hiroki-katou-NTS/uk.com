package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 雇用３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfEmployment extends AggregateRoot {
	// 会社ID
	private String companyId;
	// 基本設定
	private String basicSettingId;
	// 労働制
	private LaborSystemtAtr laborSystemAtr;
	// 雇用区分コード
	private String employmentCategoryCode;
	// 上限規制
	private UpperAgreementSetting upperAgreementSetting;

	public AgreementTimeOfEmployment(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			String employmentCategoryCode, UpperAgreementSetting upperAgreementSetting) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.employmentCategoryCode = employmentCategoryCode;
		this.upperAgreementSetting = upperAgreementSetting;
	}

	public static AgreementTimeOfEmployment createJavaType(String companyId, String basicSettingId,
			int laborSystemAtr, String employmentCategoryCode, int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfEmployment(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), employmentCategoryCode,
				new UpperAgreementSetting(new AgreementOneMonthTime(upperMonth),
						new AgreementOneMonthTime(upperMonthAverage)));
	}
}
