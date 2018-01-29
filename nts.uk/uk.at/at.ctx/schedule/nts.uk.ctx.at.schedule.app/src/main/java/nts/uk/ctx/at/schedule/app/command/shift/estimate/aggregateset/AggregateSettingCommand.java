package nts.uk.ctx.at.schedule.app.command.shift.estimate.aggregateset;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.schedule.app.common.shift.estimate.aggregateset.dto.MonthlyWorkingDaySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.HalfDayWorkCountCat;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.NotUseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggregateSettingCommand.
 */
@Data
public class AggregateSettingCommand implements AggregateSettingGetMemento{
	/** The premium no. */
	private List<Integer> premiumNo;
	
	/** The monthly working day setting. */
	private MonthlyWorkingDaySettingDto monthlyWorkingDaySettingDto;
	
	/**
	 * Instantiates a new aggregate setting command.
	 */
	public AggregateSettingCommand(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getPremiumNo()
	 */
	@Override
	public List<ExtraTimeItemNo> getPremiumNo() {
		List<ExtraTimeItemNo> list = new ArrayList<>();
		this.premiumNo.stream().forEach(e -> {
			list.add(new ExtraTimeItemNo(e));
		});
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getMonthlyWorkingDaySetting()
	 */
	@Override
	public MonthlyWorkingDaySetting getMonthlyWorkingDaySetting() {
		return new MonthlyWorkingDaySetting(HalfDayWorkCountCat.valueOf(this.monthlyWorkingDaySettingDto.getHalfDayAtr()),
				NotUseAtr.valueOf(this.monthlyWorkingDaySettingDto.getYearHdAtr()),
				NotUseAtr.valueOf(this.monthlyWorkingDaySettingDto.getSphdAtr()),
				NotUseAtr.valueOf(this.monthlyWorkingDaySettingDto.getHavyHdAtr()));
	}

}
