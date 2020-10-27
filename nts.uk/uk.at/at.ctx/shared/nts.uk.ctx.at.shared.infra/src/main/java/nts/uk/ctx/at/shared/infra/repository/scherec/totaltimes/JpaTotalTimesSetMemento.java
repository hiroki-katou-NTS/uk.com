/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalTimesSetMemento;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalCondition;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalSubjects;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalSubjectsPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalTimes;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalTimesPK;

/**
 * The Class JpaTotalTimesSetMemento.
 */
public class JpaTotalTimesSetMemento implements TotalTimesSetMemento {

	/** The entity. */
	private KshmtTotalTimes entity;
	
	/**
	 * Instantiates a new jpa total times set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalTimesSetMemento(KshmtTotalTimes totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(String companyId) {
		KshmtTotalTimesPK pk = entity.getKshmtTotalTimesPK();
		pk.setCid(companyId);
		this.entity.setKshmtTotalTimesPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalCountNo(java.lang.Integer)
	 */
	@Override
	public void setTotalCountNo(Integer totalCountNo) {
		KshmtTotalTimesPK pk = entity.getKshmtTotalTimesPK();
		pk.setTotalTimesNo(totalCountNo);
		this.entity.setKshmtTotalTimesPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setCountAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr)
	 */
	@Override
	public void setCountAtr(CountAtr countAtr) {
		this.entity.setCountAtr(countAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setUseAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
		this.entity.setUseAtr(useAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesName(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * TotalTimesName)
	 */
	@Override
	public void setTotalTimesName(TotalTimesName totalTimesName) {
		this.entity.setTotalTimesName(totalTimesName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesABName(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * TotalTimesABName)
	 */
	@Override
	public void setTotalTimesABName(TotalTimesABName totalTimesABName) {
		this.entity.setTotalTimesAbname(totalTimesABName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setSummaryAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr)
	 */
	@Override
	public void setSummaryAtr(SummaryAtr summaryAtr) {
		this.entity.setSummaryAtr(summaryAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalCondition(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * TotalCondition)
	 */
	@Override
	public void setTotalCondition(TotalCondition totalCondition) {
		KshmtTotalCondition kshmtTotalCondition = this.entity.getTotalCondition();
		totalCondition.saveToMemento(
				new JpaTotalConditionSetMemento(this.entity.getKshmtTotalTimesPK().getCid(),
						this.entity.getKshmtTotalTimesPK().getTotalTimesNo(), kshmtTotalCondition));
		this.entity.setTotalCondition(kshmtTotalCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalSubjects(java.util.List)
	 */
	@Override
	public void setSummaryList(SummaryList summaryList) {
		String companyId = this.entity.getKshmtTotalTimesPK().getCid();
		int totalTimeNo = this.entity.getKshmtTotalTimesPK().getTotalTimesNo();
		
		List<KshmtTotalSubjects> lstTotalSubjectCommand = new ArrayList<>();
		
		List<KshmtTotalSubjects> lstTotalSubjectDB = this.entity.listTotalSubjects;
		
		if (!CollectionUtil.isEmpty(summaryList.getWorkTimeCodes())) {
			summaryList.getWorkTimeCodes().stream().forEach(workTimeCode -> {
				// find entity existed
				KshmtTotalSubjects entityTotal = lstTotalSubjectDB.stream().filter(entity -> {
					KshmtTotalSubjectsPK pk = entity.getKshmtTotalSubjectsPK();
					return pk.getCid().equals(companyId) && pk.getTotalTimesNo() == totalTimeNo
							&& pk.getWorkTypeAtr() == WorkTypeAtr.WORKINGTIME.value
							&& pk.getWorkTypeCd().equals(workTimeCode);
				}).findFirst().orElse(new KshmtTotalSubjects(new KshmtTotalSubjectsPK(companyId,
						totalTimeNo, WorkTypeAtr.WORKINGTIME.value, workTimeCode)));

				// add list total subjects
				lstTotalSubjectCommand.add(entityTotal);
			});
		}

		if (!CollectionUtil.isEmpty(summaryList.getWorkTypeCodes())) {
			summaryList.getWorkTypeCodes().stream().forEach(workTypeCode -> {
				// find entity existed
				KshmtTotalSubjects entityTotal = lstTotalSubjectDB.stream().filter(entity -> {
					KshmtTotalSubjectsPK pk = entity.getKshmtTotalSubjectsPK();
					return pk.getCid().equals(companyId) && pk.getTotalTimesNo() == totalTimeNo
							&& pk.getWorkTypeAtr() == WorkTypeAtr.WORKTYPE.value
							&& pk.getWorkTypeCd().equals(workTypeCode);
				}).findFirst().orElse(new KshmtTotalSubjects(new KshmtTotalSubjectsPK(companyId,
						totalTimeNo, WorkTypeAtr.WORKTYPE.value, workTypeCode)));

				// add list total subjects
				lstTotalSubjectCommand.add(entityTotal);
			});
		}
		
		this.entity.setListTotalSubjects(lstTotalSubjectCommand);
	}

}
