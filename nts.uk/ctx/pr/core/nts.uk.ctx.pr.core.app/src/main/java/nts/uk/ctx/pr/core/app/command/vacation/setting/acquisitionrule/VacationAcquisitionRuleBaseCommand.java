package nts.uk.ctx.pr.core.app.command.vacation.setting.acquisitionrule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.Category;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionOrder;

@Getter
@Setter
public class VacationAcquisitionRuleBaseCommand implements AcquisitionRuleGetMemento {
	
	/** The company id. */
	public String companyId;
	
	/** The setting classification. */
	public Category settingClassification;
	
	public List<AcquisitionOrder> vaAcRule;

	@Override
	public Category getSettingclassification() {
		return this.settingClassification;
	}

	@Override
	public List<AcquisitionOrder> getAcquisitionOrder() {
		return this.vaAcRule;
	}

	@Override
	public String getCompanyId() {
		return this.companyId;
	}
}
