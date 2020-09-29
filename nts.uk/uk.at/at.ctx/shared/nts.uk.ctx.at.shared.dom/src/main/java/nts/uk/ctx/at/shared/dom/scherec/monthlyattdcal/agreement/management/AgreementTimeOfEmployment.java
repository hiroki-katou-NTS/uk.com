package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 雇用３６協定時間
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfEmployment extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	/** 労働制 */
	private LaborSystemtAtr laborSystemAtr;
	/** 雇用コード*/
	private EmploymentCode employmentCategoryCode;
	/** ３６協定基本設定 */
	private BasicAgreementSetting setting;

	public AgreementTimeOfEmployment(String companyId, LaborSystemtAtr laborSystemAtr,
			EmploymentCode employmentCategoryCode, BasicAgreementSetting setting) {
		super();
		this.companyId = companyId;
		this.laborSystemAtr = laborSystemAtr;
		this.employmentCategoryCode = employmentCategoryCode;
		this.setting = setting;
	}
}
