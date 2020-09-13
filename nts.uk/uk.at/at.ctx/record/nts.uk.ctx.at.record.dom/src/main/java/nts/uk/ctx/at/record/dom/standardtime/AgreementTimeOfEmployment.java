package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class AgreementTimeOfEmployment extends AggregateRoot {
	// 会社ID 1
	private  String companyId;
	// 基本設定 TODO KHÔNG DÙNG TRONG CMM024
	private String basicSettingId;
	// 雇用コード 2 TODO BỔ SUNG TRONG CMM024
	private String employmentCD;
	// 労働制 3
	private LaborSystemtAtr laborSystemAtr;
	// 雇用区分コード TODO KHÔNG DÙNG TRONG CMM024
	private String employmentCategoryCode;
	// 上限規制 TODO KHÔNG DÙNG TRONG CMM024
	private UpperAgreementSetting upperAgreementSetting;
	// 	３６協定基本設定 TODO BỔ SUNG TRONG CMM024
	private BasicAgreementSetting basicAgreementSetting;

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
	// 	[C-0] 雇用３６協定時間 (会社ID,雇用コード,労働制,時間設定)	TODO BỔ SUNG TRONG CMM024
	public AgreementTimeOfEmployment(String  companyId, String employmentCD, LaborSystemtAtr laborSystemAtr, BasicAgreementSetting basicAgreementSetting ){
		this.companyId = companyId;
		this.employmentCD = employmentCD;
		this.laborSystemAtr = laborSystemAtr;
		this.basicAgreementSetting = basicAgreementSetting;
	}
}
