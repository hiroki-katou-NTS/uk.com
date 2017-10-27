/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalCondition;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalTimes;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaTotalTimesGetMemento.
 */
public class JpaTotalTimesGetMemento implements TotalTimesGetMemento {

	/** The entity. */
	private KshstTotalTimes entity;

	/**
	 * Instantiates a new jpa total times get memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalTimesGetMemento(KshstTotalTimes totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstTotalTimesPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getTotalCountNo()
	 */
	@Override
	public Integer getTotalCountNo() {
		return this.entity.getKshstTotalTimesPK().getTotalTimesNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getCountAtr()
	 */
	@Override
	public CountAtr getCountAtr() {
		return CountAtr.valueOf(this.entity.getCountAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.entity.getUseAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getTotalTimesName()
	 */
	@Override
	public TotalTimesName getTotalTimesName() {
		return new TotalTimesName(this.entity.getTotalTimesName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getTotalTimesABName()
	 */
	@Override
	public TotalTimesABName getTotalTimesABName() {
		return new TotalTimesABName(this.entity.getTotalTimesAbname());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getSummaryAtr()
	 */
	@Override
	public SummaryAtr getSummaryAtr() {
		return SummaryAtr.valueOf(this.entity.getSummaryAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getTotalCondition()
	 */
	@Override
	public TotalCondition getTotalCondition() {
		return new TotalCondition(new JpaTotalConditionGetMemento(this.entity.getTotalCondition()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
	 * getTotalSubjects()
	 */
	@Override
	public List<TotalSubjects> getTotalSubjects() {
		return this.entity.getListTotalSubjects().stream()
				.map(item -> new TotalSubjects(new JpaTotalSubjectsGetMemento(item)))
				.collect(Collectors.toList());
	}

}
