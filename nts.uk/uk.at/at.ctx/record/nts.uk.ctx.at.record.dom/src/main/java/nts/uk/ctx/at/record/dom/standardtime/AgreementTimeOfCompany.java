package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 会社３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfCompany extends AggregateRoot {
	// 会社ID
	private String companyId;
	// 基本設定
	private String basicSettingId;
	// 労働制
	private LaborSystemtAtr laborSystemAtr;
	// 上限規制
	private UpperAgreementSetting upperAgreementSetting;

	public AgreementTimeOfCompany(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			UpperAgreementSetting upperAgreementSetting) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.upperAgreementSetting = upperAgreementSetting;
	}

	public static AgreementTimeOfCompany createFromJavaType(String companyId, String basicSettingId, int laborSystemAtr,
			int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfCompany(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), new UpperAgreementSetting(
						new AgreementOneMonthTime(upperMonth), new AgreementOneMonthTime(upperMonthAverage)));
	}

}
