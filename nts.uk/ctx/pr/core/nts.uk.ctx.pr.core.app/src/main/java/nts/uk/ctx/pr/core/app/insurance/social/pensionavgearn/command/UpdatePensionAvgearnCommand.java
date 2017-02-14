package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

public class UpdatePensionAvgearnCommand extends PensionAvgearnBaseCommand {

	public PensionAvgearn toDomain(String historyId, Integer levelCode) {
		UpdatePensionAvgearnCommand command = this;

		// Transfer data
		PensionAvgearn updatedPensionAvgearn = new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public Long getVersion() {
				return null;
			}

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return null;
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer getLevelCode() {
				return levelCode;
			}

			@Override
			public String getHistoryId() {
				return historyId;
			}

			@Override
			public PensionAvgearnValue getCompanyPension() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(command.getChildContributionAmount());
			}
		});
		return updatedPensionAvgearn;
	}
}
