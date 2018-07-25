/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class TotalTimes.
 */
@Getter
// 回数集計
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
	private Optional<SummaryList> summaryList = Optional.empty();
//	private List<TotalSubjects> totalSubjects;

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
		this.summaryList = memento.getSummaryList();

		// Validate.
		if (this.useAtr == UseAtr.NotUse) {
			return;
		}
		
		if ((this.summaryAtr.equals(SummaryAtr.DUTYTYPE)
				|| this.summaryAtr.equals(SummaryAtr.COMBINATION)) && this.summaryList.isPresent()
				&& CollectionUtil.isEmpty(this.summaryList.get().getWorkTypeCodes())) {
			throw new BusinessException("Msg_216", "KMK009_8");
		}

		if ((this.summaryAtr.equals(SummaryAtr.WORKINGTIME)
				|| this.summaryAtr.equals(SummaryAtr.COMBINATION)) && this.summaryList.isPresent()
				&& CollectionUtil.isEmpty(this.summaryList.get().getWorkTimeCodes())) {
			throw new BusinessException("Msg_216", "KMK009_9");
		}
		
		
//		if (CollectionUtil.isEmpty(this.totalSubjects)) {
//			throw new BusinessException("Msg_216", "KMK009_8");
//		} else {
//			if ((this.summaryAtr.equals(SummaryAtr.DUTYTYPE) || this.summaryAtr.equals(SummaryAtr.COMBINATION)) &&  !this.totalSubjects.stream()
//					.anyMatch(item -> item.getWorkTypeAtr().equals(WorkTypeAtr.WORKTYPE))) {
//				throw new BusinessException("Msg_10");
//			}
//
//			if ((this.summaryAtr.equals(SummaryAtr.WORKINGTIME) || this.summaryAtr.equals(SummaryAtr.COMBINATION)) && !this.totalSubjects.stream()
//					.anyMatch(item -> item.getWorkTypeAtr().equals(WorkTypeAtr.WORKINGTIME))) {
//				throw new BusinessException("Msg_29");
//			}
//		}
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
		memento.setSummaryList(this.summaryList);
		memento.setTotalCondition(this.totalCondition);
		memento.setTotalCountNo(this.totalCountNo);
		memento.setTotalTimesABName(this.totalTimesABName);
		memento.setTotalTimesName(this.totalTimesName);
		memento.setUseAtr(this.useAtr);
	}

}
