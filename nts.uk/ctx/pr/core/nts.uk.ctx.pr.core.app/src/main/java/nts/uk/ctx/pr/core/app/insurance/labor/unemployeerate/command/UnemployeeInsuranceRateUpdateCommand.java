/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;

@Getter
@Setter
public class UnemployeeInsuranceRateUpdateCommand extends BaseInsuranceCommand {
	/** The history info dto. */
	private UnemployeeInsuranceRateDto unemployeeInsuranceRate;

	/** The company code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public UnemployeeInsuranceRate toDomain() {
		UnemployeeInsuranceRateUpdateCommand command = this;
		UnemployeeInsuranceRate unemployeeInsuranceRate = new UnemployeeInsuranceRate(
				new UnemployeeInsuranceRateGetMemento() {

					@Override
					public Long getVersion() {
						// TODO Auto-generated method stub
						return command.unemployeeInsuranceRate.getVersion();
					}

					@Override
					public Set<UnemployeeInsuranceRateItem> getRateItems() {
						// TODO Auto-generated method stub
						return command.convertSetUnemployeeInsuranceRateItem(command.unemployeeInsuranceRate);
					}

					@Override
					public String getHistoryId() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public CompanyCode getCompanyCode() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public MonthRange getApplyRange() {
						// TODO Auto-generated method stub
						return null;
					}
				});
		// unemployeeInsuranceRate.setHistoryId(this.unemployeeInsuranceRate.getHistoryInsurance().getHistoryId());
		// unemployeeInsuranceRate
		// .setApplyRange(convertMonthRange(this.unemployeeInsuranceRate.getHistoryInsurance().getStartMonthRage(),
		// this.unemployeeInsuranceRate.getHistoryInsurance().getEndMonthRage()));
		// unemployeeInsuranceRate.setCompanyCode(new
		// CompanyCode(this.companyCode));
		// unemployeeInsuranceRate.setRateItems(defaultSetUnemployeeInsuranceRateItem());
		return unemployeeInsuranceRate;
	}

}
