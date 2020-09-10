package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 分類３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
@NoArgsConstructor
public class AgreementTimeOfClassification extends AggregateRoot {
	// 会社ID 1
	private String companyId;
	// 基本設定 TODO KHÔNG DÙNG TRONG CMM024
	private String basicSettingId;
	// 労働制 2
	private LaborSystemtAtr laborSystemAtr;
	// 分類コード 3 - TODO CMM024 CHUYỂN THÀNH TYPE : ClassificationCode
	private String classificationCode;
	// 上限規制 TODO KHÔNG DÙNG TRONG CMM024
	private UpperAgreementSetting upperAgreementSetting;
	//３６協定基本設定 4 TODO MỚI BỔ SUNG VÀ THAY ĐỔI TRONG CMM024
	private BasicAgreementSetting basicAgreementSetting;
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
	//	[C-0] 分類３６協定時間 (会社ID,分類コード,労働制,時間設定) TODO BỔ SUNG TRONG CMM024
	public AgreementTimeOfClassification(String companyId, String classificationCode, LaborSystemtAtr laborSystemAtr, BasicAgreementSetting basicAgreementSetting) {
		this.companyId = companyId;
		this.classificationCode = classificationCode;
		this.laborSystemAtr = laborSystemAtr;
		this.basicAgreementSetting = basicAgreementSetting;
	}
}
