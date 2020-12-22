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
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalCondition;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalSubjects;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalSubjectsPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimes;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimesPK;

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
		KshstTotalTimesPK pk = entity.getKshstTotalTimesPK();
		pk.setCid(companyId);
		this.entity.setKshstTotalTimesPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
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
		KshstTotalCondition kshstTotalCondition = this.entity.getTotalCondition();
		totalCondition.saveToMemento(
				new JpaTotalConditionSetMemento(this.entity.getKshstTotalTimesPK().getCid(),
						this.entity.getKshstTotalTimesPK().getTotalTimesNo(), kshstTotalCondition));
		this.entity.setTotalCondition(kshstTotalCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalSubjects(java.util.List)
	 */
	@Override
	public void setSummaryList(SummaryList summaryList) {
		String companyId = this.entity.getKshstTotalTimesPK().getCid();
		int totalTimeNo = this.entity.getKshstTotalTimesPK().getTotalTimesNo();
		
		List<KshstTotalSubjects> lstTotalSubjectCommand = new ArrayList<>();
		
		List<KshstTotalSubjects> lstTotalSubjectDB = this.entity.listTotalSubjects;
		
		if (!CollectionUtil.isEmpty(summaryList.getWorkTimeCodes())) {
			summaryList.getWorkTimeCodes().stream().forEach(workTimeCode -> {
				// find entity existed
				KshstTotalSubjects entityTotal = lstTotalSubjectDB.stream().filter(entity -> {
					KshstTotalSubjectsPK pk = entity.getKshstTotalSubjectsPK();
					return pk.getCid().equals(companyId) && pk.getTotalTimesNo() == totalTimeNo
							&& pk.getWorkTypeAtr() == WorkTypeAtr.WORKINGTIME.value
							&& pk.getWorkTypeCd().equals(workTimeCode);
				}).findFirst().orElse(new KshstTotalSubjects(new KshstTotalSubjectsPK(companyId,
						totalTimeNo, WorkTypeAtr.WORKINGTIME.value, workTimeCode)));

				// add list total subjects
				lstTotalSubjectCommand.add(entityTotal);
			});
		}

		if (!CollectionUtil.isEmpty(summaryList.getWorkTypeCodes())) {
			summaryList.getWorkTypeCodes().stream().forEach(workTypeCode -> {
				// find entity existed
				KshstTotalSubjects entityTotal = lstTotalSubjectDB.stream().filter(entity -> {
					KshstTotalSubjectsPK pk = entity.getKshstTotalSubjectsPK();
					return pk.getCid().equals(companyId) && pk.getTotalTimesNo() == totalTimeNo
							&& pk.getWorkTypeAtr() == WorkTypeAtr.WORKTYPE.value
							&& pk.getWorkTypeCd().equals(workTypeCode);
				}).findFirst().orElse(new KshstTotalSubjects(new KshstTotalSubjectsPK(companyId,
						totalTimeNo, WorkTypeAtr.WORKTYPE.value, workTypeCode)));

				// add list total subjects
				lstTotalSubjectCommand.add(entityTotal);
			});
		}
		
		this.entity.setListTotalSubjects(lstTotalSubjectCommand);
	}

}
