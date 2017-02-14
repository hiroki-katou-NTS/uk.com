package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

public class UpdateHealthInsuranceAvgearnCommand extends HealthInsuranceAvgearnBaseCommand {
	public HealthInsuranceAvgearn toDomain(String historyId, Integer levelCode) {
		UpdateHealthInsuranceAvgearnCommand command = this;

		// Transfer data
		HealthInsuranceAvgearn updatedHealthInsuranceAvgearn = new HealthInsuranceAvgearn(
				new HealthInsuranceAvgearnGetMemento() {

					@Override
					public Long getVersion() {
						return command.getVersion();
					}

					@Override
					public HealthInsuranceAvgearnValue getPersonalAvg() {
						// TODO convert command -> domain
						return null;
					}

					@Override
					public Integer getLevelCode() {
						return command.getLevelCode();
					}

					@Override
					public String getHistoryId() {
						return command.getHistoryId();
					}

					@Override
					public HealthInsuranceAvgearnValue getCompanyAvg() {
						// TODO convert command -> domain
						return null;
					}
				});
		return updatedHealthInsuranceAvgearn;
	}
}
