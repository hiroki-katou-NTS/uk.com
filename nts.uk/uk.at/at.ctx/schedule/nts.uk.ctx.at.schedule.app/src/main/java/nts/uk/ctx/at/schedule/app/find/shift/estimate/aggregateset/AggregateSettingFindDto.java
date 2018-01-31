package nts.uk.ctx.at.schedule.app.find.shift.estimate.aggregateset;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.schedule.app.common.shift.estimate.aggregateset.dto.MonthlyWorkingDaySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class AggregateSettingFindDto.
 */
@Data
public class AggregateSettingFindDto implements AggregateSettingSetMemento{
	
	/** The premium no. */
	private List<Integer> premiumNo;
	
	/** The monthly working day setting. */
	private MonthlyWorkingDaySettingDto monthlyWorkingDaySettingDto;
	
	/**
	 * Instantiates a new aggregate setting find dto.
	 */
	public AggregateSettingFindDto(){
		super();
		this.premiumNo = new ArrayList<>();
		this.monthlyWorkingDaySettingDto = new MonthlyWorkingDaySettingDto();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// no code here
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setPremiumNo(java.util.List)
	 */
	@Override
	public void setPremiumNo(List<ExtraTimeItemNo> premiumNo) {
		premiumNo.forEach(e -> {
			this.premiumNo.add(e.v());
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setMonthlyWorkingDaySetting(nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting)
	 */
	@Override
	public void setMonthlyWorkingDaySetting(MonthlyWorkingDaySetting monthlyWorkingDaySetting) {
		this.monthlyWorkingDaySettingDto.setHalfDayAtr(monthlyWorkingDaySetting.getHalfDayAtr().value);
		this.monthlyWorkingDaySettingDto.setYearHdAtr(monthlyWorkingDaySetting.getYearHdAtr().value);
		this.monthlyWorkingDaySettingDto.setSphdAtr(monthlyWorkingDaySetting.getSphdAtr().value);
		this.monthlyWorkingDaySettingDto.setHavyHdAtr(monthlyWorkingDaySetting.getHavyHdAtr().value);
	}
	
}
