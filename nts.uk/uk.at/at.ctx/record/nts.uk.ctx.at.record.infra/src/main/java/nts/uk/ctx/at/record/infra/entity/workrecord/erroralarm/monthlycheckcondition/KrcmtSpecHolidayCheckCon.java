package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckCon;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity      
@Table(name = "KRCMT_SPEC_HOLIDAY_CHECK")
public class KrcmtSpecHolidayCheckCon extends UkJpaEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;
	
	@Column(name = "COMPARE_OPERATOR")
	public int compareOperator;
	
	@Column(name = "NUMBER_DAY_DIFF_HOLIDAY_1")
	public int numberDayDiffHoliday1;
	
	@Column(name = "NUMBER_DAY_DIFF_HOLIDAY_2")
	public Integer numberDayDiffHoliday2;
	
	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}

	public KrcmtSpecHolidayCheckCon(String errorAlarmCheckID, int compareOperator, int numberDayDiffHoliday1, Integer numberDayDiffHoliday2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.numberDayDiffHoliday1 = numberDayDiffHoliday1;
		this.numberDayDiffHoliday2 = numberDayDiffHoliday2;
	}
	
	public static KrcmtSpecHolidayCheckCon toEntity(SpecHolidayCheckCon domain) {
		return new KrcmtSpecHolidayCheckCon(
				domain.getErrorAlarmCheckID(),
				domain.getCompareOperator(),
				domain.getNumberDayDiffHoliday1().v().intValue(),
				!domain.getNumberDayDiffHoliday2().isPresent()?null:domain.getNumberDayDiffHoliday2().get().v().intValue()
				);
	}
	
	public SpecHolidayCheckCon toDomain() {
		return new SpecHolidayCheckCon(
				this.errorAlarmCheckID,
				this.compareOperator,
				new MonthlyDays(new Double(this.numberDayDiffHoliday1)),
				this.numberDayDiffHoliday2==null?null:new MonthlyDays(new Double(this.numberDayDiffHoliday2))
				);
	}

}
