package nts.uk.ctx.at.schedule.dom.shift.total.times;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * Gets the summary list.
 *
 * @return the summary list
 */
@Getter
public class TotalTimes extends AggregateRoot {

	/** The company ID. */
	// 会社ID
	private CompanyId companyID;

	/** The total count no. */
	// 回数集計NO
	private Integer totalCountNo;

	/** The count atr. */
	// 半日勤務カウント区分
	private CountAtr countAtr;

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
	private TotalSubjects totalSubjects;

	/**
	 * Instantiates a new total times.
	 *
	 * @param companyID the company ID
	 * @param totalCountNo the total count no
	 * @param countAtr the count atr
	 * @param useAtr the use atr
	 * @param totalTimesName the total times name
	 * @param totalTimesABName the total times AB name
	 * @param summaryAtr the summary atr
	 * @param totalCondition the total condition
	 * @param totalSubjects the summary list
	 */
	public TotalTimes(CompanyId companyID, Integer totalCountNo, CountAtr countAtr, UseAtr useAtr,
			TotalTimesName totalTimesName, TotalTimesABName totalTimesABName, SummaryAtr summaryAtr,
			TotalCondition totalCondition, TotalSubjects totalSubjects) {
		this.companyID = companyID;
		this.totalCountNo = totalCountNo;
		this.countAtr = countAtr;
		this.useAtr = useAtr;
		this.totalTimesName = totalTimesName;
		this.totalTimesABName = totalTimesABName;
		this.summaryAtr = summaryAtr;
		this.totalCondition = totalCondition;
		this.totalSubjects = totalSubjects;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TotalTimesSetMemento memento) {
		memento.setCompanyId(this.companyID);
		memento.setCountAtr(this.countAtr);
		memento.setSummaryAtr(this.summaryAtr);
		memento.setTotalSubjects(this.totalSubjects);
		memento.setTotalCondition(this.totalCondition);
		memento.setTotalCountNo(this.totalCountNo);
		memento.setTotalTimesABName(this.totalTimesABName);
		memento.setTotalTimesName(this.totalTimesName);
		memento.setUseAtr(this.useAtr);
	}
	
}
