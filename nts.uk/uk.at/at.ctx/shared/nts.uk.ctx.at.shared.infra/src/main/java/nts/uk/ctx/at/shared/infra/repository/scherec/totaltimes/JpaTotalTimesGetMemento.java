/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalTimesGetMemento;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalSubjects;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalSubjectsPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalTimes;

/**
 * The Class JpaTotalTimesGetMemento.
 */
public class JpaTotalTimesGetMemento implements TotalTimesGetMemento {

	/** The entity. */
	private KshmtTotalTimes entity;

	/**
	 * Instantiates a new jpa total times get memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalTimesGetMemento(KshmtTotalTimes totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtTotalTimesPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getTotalCountNo()
	 */
	@Override
	public Integer getTotalCountNo() {
		return this.entity.getKshmtTotalTimesPK().getTotalTimesNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getCountAtr()
	 */
	@Override
	public CountAtr getCountAtr() {
		return CountAtr.valueOf(this.entity.getCountAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.entity.getUseAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getTotalTimesName()
	 */
	@Override
	public TotalTimesName getTotalTimesName() {
		return new TotalTimesName(this.entity.getTotalTimesName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getTotalTimesABName()
	 */
	@Override
	public TotalTimesABName getTotalTimesABName() {
		return new TotalTimesABName(this.entity.getTotalTimesAbname());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getSummaryAtr()
	 */
	@Override
	public SummaryAtr getSummaryAtr() {
		return SummaryAtr.valueOf(this.entity.getSummaryAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getTotalCondition()
	 */
	@Override
	public TotalCondition getTotalCondition() {
		return new TotalCondition(new JpaTotalConditionGetMemento(this.entity.getTotalCondition()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesGetMemento#
	 * getSummaryList()
	 */
	@Override
	public SummaryList getSummaryList() {
		SummaryList summaryList = new SummaryList();
		summaryList.setWorkTimeCodes(this.entity.getListTotalSubjects().stream()
				.filter(item -> WorkTypeAtr.WORKINGTIME.equals(
						WorkTypeAtr.valueOf(item.getKshmtTotalSubjectsPK().getWorkTypeAtr())))
				.map(KshmtTotalSubjects::getKshmtTotalSubjectsPK)
				.map(KshmtTotalSubjectsPK::getWorkTypeCd).collect(Collectors.toList()));
		summaryList.setWorkTypeCodes(this.entity.getListTotalSubjects().stream()
				.filter(item -> WorkTypeAtr.WORKTYPE.equals(
						WorkTypeAtr.valueOf(item.getKshmtTotalSubjectsPK().getWorkTypeAtr())))
				.map(KshmtTotalSubjects::getKshmtTotalSubjectsPK)
				.map(KshmtTotalSubjectsPK::getWorkTypeCd).collect(Collectors.toList()));
		
		return summaryList;
	}

}
