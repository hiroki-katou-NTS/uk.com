package nts.uk.ctx.at.shared.dom.monthly.agreement.management;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting.BasicAgreementSetting;

/**
 * 会社３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfCompany extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	/** 労働制 */
	private LaborSystemtAtr laborSystemAtr;
	/** ３６協定基本設定 */
	private BasicAgreementSetting setting;

	public AgreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr,
			BasicAgreementSetting setting) {
		super();
		this.companyId = companyId;
		this.laborSystemAtr = laborSystemAtr;
		this.setting = setting;
	}
}
