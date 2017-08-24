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
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalCondition;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalSubjects;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalTimes;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalTimesPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaTotalTimesSetMemento.
 */
public class JpaTotalTimesSetMemento implements TotalTimesSetMemento {

	/** The entity. */
	private KshstTotalTimes entity;

	/**
	 * Instantiates a new jpa total times set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalTimesSetMemento(KshstTotalTimes totalTimes) {
		if (entity.getKshstTotalTimesPK() == null) {
			entity.setKshstTotalTimesPK(new KshstTotalTimesPK());
		}
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		KshstTotalTimesPK pk = entity.getKshstTotalTimesPK();
		pk.setCid(companyId.v());
		this.entity.setKshstTotalTimesPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalCountNo(java.lang.Integer)
	 */
	@Override
	public void setTotalCountNo(Integer totalCountNo) {
		KshstTotalTimesPK pk = entity.getKshstTotalTimesPK();
		pk.setTotalTimesNo(totalCountNo);
		this.entity.setKshstTotalTimesPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setCountAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.CountAtr)
	 */
	@Override
	public void setCountAtr(CountAtr countAtr) {
		this.entity.setCountAtr(countAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setUseAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
		this.entity.setUseAtr(useAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesName(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * TotalTimesName)
	 */
	@Override
	public void setTotalTimesName(TotalTimesName totalTimesName) {
		this.entity.setTotalTimesName(totalTimesName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesABName(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * TotalTimesABName)
	 */
	@Override
	public void setTotalTimesABName(TotalTimesABName totalTimesABName) {
		this.entity.setTotalTimesAbname(totalTimesABName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setSummaryAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.SummaryAtr)
	 */
	@Override
	public void setSummaryAtr(SummaryAtr summaryAtr) {
		this.entity.setSummaryAtr(summaryAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalCondition(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * TotalCondition)
	 */
	@Override
	public void setTotalCondition(TotalCondition totalCondition) {
		KshstTotalCondition kshstTotalCondition = new KshstTotalCondition();
		totalCondition.saveToMemento(
				new JpaTotalConditionSetMemento(this.entity.getKshstTotalTimesPK().getCid(),
						this.entity.getKshstTotalTimesPK().getTotalTimesNo(), kshstTotalCondition));
		this.entity.setTotalCondition(kshstTotalCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalSubjects(java.util.List)
	 */
	@Override
	public void setTotalSubjects(List<TotalSubjects> summaryList) {

		List<KshstTotalSubjects> listTotalSubjects = summaryList.stream().map(item -> {
			KshstTotalSubjects entity = new KshstTotalSubjects();
			item.saveToMemento(
					new JpaTotalSubjectsSetMemento(this.entity.getKshstTotalTimesPK().getCid(),
							this.entity.getKshstTotalTimesPK().getTotalTimesNo(), entity));
			return entity;
		}).collect(Collectors.toList());

		this.entity.setListTotalSubjects(listTotalSubjects);
	}

}
