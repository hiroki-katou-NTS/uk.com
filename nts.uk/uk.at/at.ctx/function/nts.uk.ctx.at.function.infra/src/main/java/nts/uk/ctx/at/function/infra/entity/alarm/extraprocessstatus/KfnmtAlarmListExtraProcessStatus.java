package nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALARM_PATTERN_SET")
public class KfnmtAlarmListExtraProcessStatus  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KfnmtAlarmListExtraProcessStatusPK kfnmtAlarmListExtraProcessStatusPK;
	
	@Column(name = "SID")
	public String employeeID;
	
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	@Column(name = "END_TIME")
	public int endTime;
	
	
	@Override
	protected Object getKey() {
		return kfnmtAlarmListExtraProcessStatusPK;
	}
	
	public KfnmtAlarmListExtraProcessStatus(KfnmtAlarmListExtraProcessStatusPK kfnmtAlarmListExtraProcessStatusPK,
			String employeeID, GeneralDate endDate, int endTime) {
		super();
		this.kfnmtAlarmListExtraProcessStatusPK = kfnmtAlarmListExtraProcessStatusPK;
		this.employeeID = employeeID;
		this.endDate = endDate;
		this.endTime = endTime;
	}
	
	public static KfnmtAlarmListExtraProcessStatus  toEntity(AlarmListExtraProcessStatus domain) {
		return new KfnmtAlarmListExtraProcessStatus(
				new KfnmtAlarmListExtraProcessStatusPK(
						domain.getCompanyID(),
						domain.getStartDate(),
						domain.getStartTime()
						),
				domain.getEmployeeID(),
				domain.getEndDate().get(),
				domain.getEndTime().intValue()
				);
	}
	
	public AlarmListExtraProcessStatus toDomain() {
		return new AlarmListExtraProcessStatus(
				this.kfnmtAlarmListExtraProcessStatusPK.companyID,
				this.kfnmtAlarmListExtraProcessStatusPK.startDate,
				this.kfnmtAlarmListExtraProcessStatusPK.startTime,
				this.employeeID,
				Optional.of(this.endDate),
				new Integer(this.endTime)
				);
	}

	
}
