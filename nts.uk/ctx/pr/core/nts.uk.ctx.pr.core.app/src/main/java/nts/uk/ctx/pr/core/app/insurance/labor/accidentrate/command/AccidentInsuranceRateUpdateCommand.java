/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.InsuBizRateItemDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;

/**
 * The Class AccidentInsuranceRateUpdateCommand.
 */
@Getter
@Setter
public class AccidentInsuranceRateUpdateCommand {

	/** The accident insurance rate. */
	private AccidentInsuranceRateDto accidentInsuranceRate;

	/**
	 * To domain.
	 *
	 * @return the accident insurance rate
	 */
	public AccidentInsuranceRate toDomain(String companyCode) {
		AccidentInsuranceRateUpdateCommand command = this;

		// Transfer data
		AccidentInsuranceRate accidentInsuranceRate = new AccidentInsuranceRate(new AccidentInsuranceRateGetMemento() {

			@Override
			public Set<InsuBizRateItem> getRateItems() {
				Set<InsuBizRateItem> setInsuBizRateItem = new HashSet<>();
				for (InsuBizRateItemDto item : command.accidentInsuranceRate.getRateItems()) {
					InsuBizRateItem itemInsuBizRateItem = new InsuBizRateItem();
					itemInsuBizRateItem.setInsuBizType(BusinessTypeEnum.valueOf(item.getInsuBizType()));
					itemInsuBizRateItem.setInsuRate(item.getInsuRate());
					itemInsuBizRateItem.setInsuRound(RoundingMethod.valueOf(item.getInsuRound()));
					setInsuBizRateItem.add(itemInsuBizRateItem);
				}
				return setInsuBizRateItem;
			}

			@Override
			public String getHistoryId() {
				return command.accidentInsuranceRate.getHistoryInsurance().getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(command.accidentInsuranceRate.getHistoryInsurance().getStartMonthRage(),
						command.accidentInsuranceRate.getHistoryInsurance().getEndMonthRage(), "/");
			}
		});
		return accidentInsuranceRate;

	}
}
