/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.totaltimes;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class TotalTimes.
 */
@Getter
public class TotalTimes extends AggregateRoot {

	/** The company ID. */
	// 会社ID
	private CompanyId companyId;

	/** The total count no. */
	// 回数集計NO
	private Integer totalCountNo;

	/** The use atr. */
	// するしない区分
	private UseAtr useAtr;

	/** The total times name. */
	// 回数集計名称
	private TotalTimesName totalTimesName;

	/** The total times AB name. */
	// 回数集計略名
	private TotalTimesABName totalTimesABName;

	/** The summary atr. */
	// 回数集計区分
	private SummaryAtr summaryAtr;

	/** The total condition. */
	// 回数集計条件
	private TotalCondition totalCondition;

	/** The summary list. */
	// 集計対象一覧
	private List<TotalSubjects> totalSubjects;

	/** The count atr. */
	// 半日勤務カウント区分
	private CountAtr countAtr;

	public TotalTimes(TotalTimesGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.totalCountNo = memento.getTotalCountNo();
		this.countAtr = memento.getCountAtr();
		this.useAtr = memento.getUseAtr();
		this.totalTimesName = memento.getTotalTimesName();
		this.totalTimesABName = memento.getTotalTimesABName();
		this.summaryAtr = memento.getSummaryAtr();
		this.totalCondition = memento.getTotalCondition();
		this.totalSubjects = memento.getTotalSubjects();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(TotalTimesSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCountAtr(this.countAtr);
		memento.setSummaryAtr(this.summaryAtr);
		memento.setTotalSubjects(this.totalSubjects);
		memento.setTotalCondition(totalCondition);
		memento.setTotalCountNo(this.totalCountNo);
		memento.setTotalTimesABName(this.totalTimesABName);
		memento.setTotalTimesName(this.totalTimesName);
		memento.setUseAtr(this.useAtr);
	}

}
