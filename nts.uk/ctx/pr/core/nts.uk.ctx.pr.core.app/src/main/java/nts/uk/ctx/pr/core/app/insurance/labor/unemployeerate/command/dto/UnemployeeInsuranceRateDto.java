package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto.HistoryUnemployeeInsuranceFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;

@Data
public class UnemployeeInsuranceRateDto {
	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;

	public UnemployeeInsuranceRate toDomain(String companyCode) {
		UnemployeeInsuranceRateDto dto = this;
		return new UnemployeeInsuranceRate(new UnemployeeInsuranceRateGetMemento() {

			@Override
			public Set<UnemployeeInsuranceRateItem> getRateItems() {
				Set<UnemployeeInsuranceRateItem> setUnemployeeInsuranceRateItem = new HashSet<>();
				for (UnemployeeInsuranceRateItemDto unemployeeInsuranceRateItem : dto.rateItems) {
					setUnemployeeInsuranceRateItem.add(unemployeeInsuranceRateItem.toDomain(companyCode));
				}
				return setUnemployeeInsuranceRateItem;
			}

			@Override
			public String getHistoryId() {
				return historyInsurance.getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(dto.historyInsurance.getStartMonthRage(),
						dto.historyInsurance.getEndMonthRage(), "/");
			}
		});
	}
}
