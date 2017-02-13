package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find;

import lombok.Builder;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

@Builder
public class HealthInsuranceAvgearnDto implements HealthInsuranceAvgearnSetMemento {

	/** The history id. */
	public String historyId;

	/** The level code. */
	public Integer levelCode;

	/** The company avg. */
	public HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	public HealthInsuranceAvgearnValue personalAvg;

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	@Override
	public void setCompanyAvg(HealthInsuranceAvgearnValue companyAvg) {
		this.companyAvg = companyAvg;
	}

	@Override
	public void setPersonalAvg(HealthInsuranceAvgearnValue personalAvg) {
		this.personalAvg = personalAvg;
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
	}
}
