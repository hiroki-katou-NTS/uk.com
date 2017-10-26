package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAI_PER_WORK_INFO")
public class KrcmtDaiPerWorkInfo extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDaiPerWorkInfoPK krcmtDaiPerWorkInfoPK;
	
	@Column(name = "RECORD_WORK_WORKTYPE_CODE")
	public String recordWorkWorktypeCode;

	@Column(name = "RECORD_WORK_WORKTIME_CODE") 
	public String recordWorkWorktimeCode;

	@Column(name = "SCHEDULE_WORK_WORKTYPE_CODE")
	public String scheduleWorkWorktypeCode;
	
	@Column(name = "SCHEDULE_WORK_WORKTIME_CODE")
	public String scheduleWorkWorktimeCode;

	@Column(name = "CALCULATION_STATE")
	public Integer calculationState;

	@Column(name = "GO_STRAIGHT_ATR")
	public Integer goStraightAttribute;
	
	@Column(name = "BACK_STRAIGHT_ATR")
	public Integer backStraightAttribute;

	@Column(name = "WORK_NO")
	public String workNo;

	@Column(name = "YMD")
	public BigDecimal ymd;

	@OneToMany(mappedBy="daiPerWorkInfo", cascade = CascadeType.ALL)
	public List<KrcmtWorkScheduleTime> scheduleTimes;
	
	@Override
	protected Object getKey() {
		return this.krcmtDaiPerWorkInfoPK;
	}
	
	public WorkInfoOfDailyPerformance toDomain() {
		WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(this.krcmtDaiPerWorkInfoPK.employeeId,
			new WorkInformation(this.recordWorkWorktimeCode, this.recordWorkWorktypeCode),
			new WorkInformation(this.scheduleWorkWorktimeCode, this.recordWorkWorktypeCode),
			EnumAdaptor.valueOf(this.calculationState, CalculationState.class),
			EnumAdaptor.valueOf(this.goStraightAttribute, NotUseAttribute.class),
			EnumAdaptor.valueOf(this.backStraightAttribute, NotUseAttribute.class),
			GeneralDate.fromString(this.ymd.toString(), "yyyyMMdd"),
			KrcmtWorkScheduleTime.toDomain(scheduleTimes));
		return domain;
	}

}
