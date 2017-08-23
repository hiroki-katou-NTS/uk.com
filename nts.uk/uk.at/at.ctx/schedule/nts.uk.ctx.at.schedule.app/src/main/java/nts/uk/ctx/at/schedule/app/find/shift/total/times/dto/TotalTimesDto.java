package nts.uk.ctx.at.schedule.app.find.shift.total.times.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalCondition;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalTimesSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Getter
@Setter
public class TotalTimesDto implements TotalTimesSetMemento {

	/** The total count no. */
	// 回数集計NO
	private int totalCountNo;

	/** The count atr. */
	// 半日勤務カウント区分
	private String countAtr;

	/** The use atr. */
	// するしない区分
	private int useAtr;

	/** The total times name. */
	// 回数集計名称
	private String totalTimesName;

	/** The total times AB name. */
	// 回数集計略名
	private String totalTimesABName;

	/** The summary atr. */
	// 回数集計区分
	private String summaryAtr;

	/** The total condition. */
	// 回数集計条件
	private TotalConditionDto totalCondition;

	/** The summary list. */
	// 集計対象一覧
	private TotalSubjectsDto summaryList;

	public TotalTimesDto(int totalCountNo, String countAtr, int useAtr, String totalTimesName, String totalTimesABName,
			String summaryAtr, TotalConditionDto totalCondition, TotalSubjectsDto summaryList) {
		this.totalCountNo = totalCountNo;
		this.countAtr = countAtr;
		this.useAtr = useAtr;
		this.totalTimesName = totalTimesName;
		this.totalTimesABName = totalTimesABName;
		this.summaryAtr = summaryAtr;
		this.totalCondition = totalCondition;
		this.summaryList = summaryList;
	}

	@Override
	public void setCompanyId(CompanyId setCompanyId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCountAtr(CountAtr setCountAtr) {
		this.countAtr = setCountAtr.toName();
	}

	@Override
	public void setTotalTimesName(TotalTimesName setTotalTimesName) {
		this.totalTimesName = setTotalTimesName.v();
	}

	@Override
	public void setTotalTimesABName(TotalTimesABName setTotalTimesABName) {
		this.totalTimesABName = setTotalTimesABName.v();
	}

	@Override
	public void setSummaryAtr(SummaryAtr setSummaryAtr) {
		this.summaryAtr = setSummaryAtr.toString();
	}

	@Override
	public void setTotalCondition(TotalCondition setTotalCondition) {
		this.totalCondition = new TotalConditionDto(setTotalCondition.getUpperLimitSettingAtr(),
				setTotalCondition.getLowerLimitSettingAtr(), setTotalCondition.getThresoldUpperLimit().v(),
				setTotalCondition.getThresoldLowerLimit().v());
	}

	@Override
	public void setTotalSubjects(TotalSubjects setTotalSubjects) {
		this.summaryList = new TotalSubjectsDto(setTotalSubjects.getWorkTypeCode().v(),
				setTotalSubjects.getWorkTypeAtr());
	}

	@Override
	public void setUseAtr(UseAtr setUseAtr) {
		this.useAtr = setUseAtr.value;
	}

	@Override
	public void setTotalCountNo(Integer setTotalCountNo) {
		this.totalCountNo = setTotalCountNo.intValue();
	}

	
	
}
