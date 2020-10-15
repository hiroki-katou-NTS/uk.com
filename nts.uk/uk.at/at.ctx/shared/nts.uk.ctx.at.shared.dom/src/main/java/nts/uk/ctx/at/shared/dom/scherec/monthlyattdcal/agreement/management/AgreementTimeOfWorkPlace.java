package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

/**
 * 職場３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfWorkPlace extends AggregateRoot {
	/** 職場ID */
	private String workplaceId;
	/** 労働制 */
	private LaborSystemtAtr laborSystemAtr;
	/** ３６協定基本設定 */
	private BasicAgreementSetting setting;

	public AgreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr,
			BasicAgreementSetting setting) {
		super();
		this.workplaceId = workplaceId;
		this.laborSystemAtr = laborSystemAtr;
		this.setting = setting;
	}
}
