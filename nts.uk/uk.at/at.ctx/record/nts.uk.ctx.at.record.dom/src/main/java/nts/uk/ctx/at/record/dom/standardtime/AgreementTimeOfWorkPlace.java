package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class AgreementTimeOfWorkPlace extends AggregateRoot {
	// 職場ID 1
	private String workplaceId;
	// 基本設定 TODO KHÔNG DÙNG TRONG CMM024
	private String basicSettingId;
	// 労働制 2
	private LaborSystemtAtr laborSystemAtr;
	// 上限規制 TODO KHÔNG DÙNG TRONG CMM024
	private UpperAgreementSetting upperAgreementSetting;
	// ３６協定基本設定 3 TODO BỔ SUNG VÀ THAY ĐỔI TRONG CMM024
	private BasicAgreementSetting basicAgreementSetting;
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

	// 	[C-0] 職場３６協定時間 (職場ID,労働制,時間設定)	TODO BỔ SUNG TRONG CMM 024
	public AgreementTimeOfWorkPlace(String  workplaceId, LaborSystemtAtr laborSystemAtr, BasicAgreementSetting basicAgreementSetting){
		this.workplaceId = workplaceId;
		this.laborSystemAtr = laborSystemAtr;
		this.basicAgreementSetting = basicAgreementSetting;
	}
}
