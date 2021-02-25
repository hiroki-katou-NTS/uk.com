package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

/**
 * 分類３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfClassification extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	/** 労働制 */
	private LaborSystemtAtr laborSystemAtr;
	/** 分類コード */
	private ClassificationCode classificationCode;
	/** ３６協定基本設定 */
	private BasicAgreementSetting setting;
	
	public AgreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr,
			ClassificationCode classificationCode, BasicAgreementSetting setting) {
		super();
		this.companyId = companyId;
		this.laborSystemAtr = laborSystemAtr;
		this.classificationCode = classificationCode;
		this.setting = setting;
	}
}
