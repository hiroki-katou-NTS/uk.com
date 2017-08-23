package nts.uk.ctx.at.schedule.infra.repository.shift.total.times;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.UseAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalCondition;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalTimesGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesName;
import nts.uk.ctx.at.schedule.infra.entity.shift.total.times.KshstTotalTimes;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public class JpaTotalTimesGetMemento implements TotalTimesGetMemento{
	
	private KshstTotalTimes totalTimes;

	public JpaTotalTimesGetMemento(KshstTotalTimes totalTimes) {
		this.totalTimes = totalTimes;
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(totalTimes.getKshstTotalTimesPK().getCid());
	}

	@Override
	public Integer getTotalCountNo() {
		return (int)totalTimes.getKshstTotalTimesPK().getTotalTimesNo();
	}

	@Override
	public CountAtr getCountAtr() {
		return null;
	}

	@Override
	public UseAtr getUseAtr() {
		return null;
	}

	@Override
	public TotalTimesName getTotalTimesName() {
		return new TotalTimesName(totalTimes.getTotalTimesName());
	}

	@Override
	public TotalTimesABName getTotalTimesABName() {
		return new TotalTimesABName(totalTimes.getTotalTimesAbname());
	}

	@Override
	public SummaryAtr getSummaryAtr() {
		return null;
	}

	@Override
	public TotalCondition getTotalCondition() {
		return new TotalCondition(String.valueOf(totalTimes.getTotalCondition().getUpperLimitSetAtr()) ,
				String.valueOf(totalTimes.getTotalCondition().getLowerLimitSetAtr()),
			new ConditionThresholdLimit(Long.valueOf(totalTimes.getTotalCondition().getThresoldUpperLimit())) ,
			new ConditionThresholdLimit(Long.valueOf(totalTimes.getTotalCondition().getThresoldLowerLimit())));
	}

	@Override
	public TotalSubjects getTotalSubjects() {
		return null;
	}

}
