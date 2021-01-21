/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalTimesSetMemento;

/**
 * The Class TotalTimesDetailDto.
 */
@Getter
@Setter
public class TotalTimesItemDto implements TotalTimesSetMemento {

	/** The total count no. */
	private Integer totalCountNo;

	/** The summary atr. */
	private Integer summaryAtr;

	/** The use atr. */
	private Integer useAtr;

	/** The total times name. */
	private String totalTimesName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(String setCompanyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setCountAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr)
	 */
	@Override
	public void setCountAtr(CountAtr countAtr) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesName(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * TotalTimesName)
	 */
	@Override
	public void setTotalTimesName(TotalTimesName setTotalTimesName) {
		this.totalTimesName = setTotalTimesName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesABName(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * TotalTimesABName)
	 */
	@Override
	public void setTotalTimesABName(TotalTimesABName setTotalTimesABName) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setSummaryAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr)
	 */
	@Override
	public void setSummaryAtr(SummaryAtr summaryAtr) {
		this.summaryAtr = summaryAtr.value;
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
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setUseAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr setUseAtr) {
		this.useAtr = setUseAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#
	 * setTotalCountNo(java.lang.Integer)
	 */
	@Override
	public void setTotalCountNo(Integer setTotalCountNo) {
		this.totalCountNo = setTotalCountNo.intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesSetMemento#setSummaryList(java.util.Optional)
	 */
	@Override
	public void setSummaryList(SummaryList summaryList) {
		// Do nothing
	}

}
