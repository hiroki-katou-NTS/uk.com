package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 会社３６協定時間
 *
 * @author nampt
 *
 */
@Getter
@NoArgsConstructor
public class AgreementTimeOfCompany extends AggregateRoot {
	// 会社ID 1
	private String companyId;
	// 基本設定  TODO Không dùng trong cmm024 new
	private String basicSettingId;
	// 労働制 2
	private LaborSystemtAtr laborSystemAtr;
	// 上限規制  TODO Không dùng trong cmm024 new
	private UpperAgreementSetting upperAgreementSetting;
	//３６協定基本設定 3 TODO : sửa trong cmm024 new
	private BasicAgreementSetting basicAgreementSetting;
	// TODO : KHÔNG DÙNG TRONG CMM 024
	public AgreementTimeOfCompany(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			UpperAgreementSetting upperAgreementSetting) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.upperAgreementSetting = upperAgreementSetting;
	}
	// TODO : KHÔNG DÙNG TRONG CMM 024
	public static AgreementTimeOfCompany createFromJavaType(String companyId, String basicSettingId, int laborSystemAtr,
			int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfCompany(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), new UpperAgreementSetting(
						new AgreementOneMonthTime(upperMonth), new AgreementOneMonthTime(upperMonthAverage)));
	}
    //[C-0] 会社３６協定時間 (会社ID,労働制,時間設定)
	public AgreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr, BasicAgreementSetting basicAgreementSetting) {
		this.companyId = companyId;
		this.laborSystemAtr = laborSystemAtr;
		this.basicAgreementSetting = basicAgreementSetting;
	}

}
