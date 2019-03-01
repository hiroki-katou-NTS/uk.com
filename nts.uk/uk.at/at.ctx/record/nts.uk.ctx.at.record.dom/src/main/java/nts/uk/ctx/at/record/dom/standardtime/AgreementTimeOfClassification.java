package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 分類３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfClassification extends AggregateRoot {
	// 会社ID
	private String companyId;
	// 基本設定
	private String basicSettingId;
	// 労働制
	private LaborSystemtAtr laborSystemAtr;
	// 分類コード
	private String classificationCode;
	// 上限規制
	private UpperAgreementSetting upperAgreementSetting;
	
	public AgreementTimeOfClassification(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			String classificationCode, UpperAgreementSetting upperAgreementSetting) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.classificationCode = classificationCode;
		this.upperAgreementSetting = upperAgreementSetting;
	}

	public static AgreementTimeOfClassification createJavaType(String companyId, String basicSettingId,
			int laborSystemAtr, String classificationCode, int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfClassification(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), classificationCode,
				new UpperAgreementSetting(new AgreementOneMonthTime(upperMonth),
						new AgreementOneMonthTime(upperMonthAverage)));
	}
}
