package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

@Data
public class AccidentInsuranceRateDto {
	/** The history insurance. */
	private HistoryAccidentInsuranceRateDto historyInsurance;

	/** The rate items. */
	private List<InsuBizRateItemDto> rateItems;

	public AccidentInsuranceRate toDomain(String companyCode) {
		AccidentInsuranceRateDto dto = this;
		AccidentInsuranceRate accidentInsuranceRate = new AccidentInsuranceRate(new AccidentInsuranceRateGetMemento() {

			@Override
			public Set<InsuBizRateItem> getRateItems() {
				Set<InsuBizRateItem> setInsuBizRateItem = new HashSet<>();
				for (InsuBizRateItemDto insuBizRateItemDto : dto.rateItems) {
					setInsuBizRateItem.add(insuBizRateItemDto.toDomain());
				}
				return setInsuBizRateItem;
			}

			@Override
			public String getHistoryId() {
				return dto.historyInsurance.getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				return dto.historyInsurance.toDomain();
			}
		});
		return accidentInsuranceRate;
	}

}
