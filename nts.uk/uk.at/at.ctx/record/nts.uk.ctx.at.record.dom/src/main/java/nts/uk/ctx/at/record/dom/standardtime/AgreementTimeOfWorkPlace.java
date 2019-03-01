package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 職場３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfWorkPlace extends AggregateRoot {
	// 職場ID
	private String workplaceId;
	// 基本設定
	private String basicSettingId;
	// 労働制
	private LaborSystemtAtr laborSystemAtr;
	// 上限規制
	private UpperAgreementSetting upperAgreementSetting;

	public AgreementTimeOfWorkPlace(String workplaceId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			UpperAgreementSetting upperAgreementSetting) {
		super();
		this.workplaceId = workplaceId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.upperAgreementSetting = upperAgreementSetting;
	}

	public static AgreementTimeOfWorkPlace createJavaType(String workplaceId, String basicSettingId,
			int laborSystemAtr, int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfWorkPlace(workplaceId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class),
				new UpperAgreementSetting(new AgreementOneMonthTime(upperMonth),
						new AgreementOneMonthTime(upperMonthAverage)));
	}

}
