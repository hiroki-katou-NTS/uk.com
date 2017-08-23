/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.totaltimes.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalCondition;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class TotalTimesDetailDto.
 */
@Getter
@Setter
public class TotalTimesDetailDto implements TotalTimesSetMemento {

	/** The total count no. */
	// 回数集計NO
	private Integer totalCountNo;

	/** The count atr. */
	// 半日勤務カウント区分
	private Integer countAtr;

	/** The use atr. */
	// するしない区分
	private Integer useAtr;

	/** The total times name. */
	// 回数集計名称
	private String totalTimesName;

	/** The total times AB name. */
	// 回数集計略名
	private String totalTimesABName;

	/** The summary atr. */
	// 回数集計区分
	private Integer summaryAtr;

	/** The total condition. */
	// 回数集計条件
	private TotalConditionDto totalCondition;

	/** The summary list. */
	// 集計対象一覧
	private List<TotalSubjectsDto> summaryList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId setCompanyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setCountAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.CountAtr)
	 */
	@Override
	public void setCountAtr(CountAtr countAtr) {
		this.countAtr = countAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesName(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * TotalTimesName)
	 */
	@Override
	public void setTotalTimesName(TotalTimesName setTotalTimesName) {
		this.totalTimesName = setTotalTimesName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalTimesABName(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * TotalTimesABName)
	 */
	@Override
	public void setTotalTimesABName(TotalTimesABName setTotalTimesABName) {
		this.totalTimesABName = setTotalTimesABName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setSummaryAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.SummaryAtr)
	 */
	@Override
	public void setSummaryAtr(SummaryAtr summaryAtr) {
		this.summaryAtr = summaryAtr.value;
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
		TotalConditionDto dto = new TotalConditionDto();
		totalCondition.saveToMemento(dto);
		this.totalCondition = dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalSubjects(java.util.List)
	 */
	@Override
	public void setTotalSubjects(List<TotalSubjects> totalSubjects) {
		this.summaryList = totalSubjects.stream().map(item -> {
			TotalSubjectsDto dto = new TotalSubjectsDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setUseAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr setUseAtr) {
		this.useAtr = setUseAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento#
	 * setTotalCountNo(java.lang.Integer)
	 */
	@Override
	public void setTotalCountNo(Integer setTotalCountNo) {
		this.totalCountNo = setTotalCountNo.intValue();
	}

}
