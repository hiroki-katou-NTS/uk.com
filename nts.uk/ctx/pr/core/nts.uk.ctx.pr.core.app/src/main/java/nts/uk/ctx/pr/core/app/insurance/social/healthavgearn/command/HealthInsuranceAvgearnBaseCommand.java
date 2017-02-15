package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

@Getter
@Setter
public class HealthInsuranceAvgearnBaseCommand {
	/** The version. */
	private Long version;

	/** The history id. */
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValue personalAvg;

	public HealthInsuranceAvgearn toDomain(String historyId, Integer levelCode) {
		HealthInsuranceAvgearnBaseCommand command = this;

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
