package nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALEX_PROCESS_STATUS")
public class KfnmtAlarmListExtraProcessStatus  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "EXTRA_PRO_STATUS_ID")	
	public String extraProcessStatusID;
	
	@Column(name = "CID")	
	public String companyID;
	
	@Column(name = "START_DATE")	
	public GeneralDate startDate;
	
	@Column(name = "START_TIME")		
	public Integer startTime;
	
	@Column(name = "SID")
	public String employeeID;
	
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	@Column(name = "END_TIME")
	public Integer endTime;
	
	@Column(name = "STATUS")
	public int status;
	
	
	@Override
	protected Object getKey() {
		return extraProcessStatusID;
	}
	
	
	
	public static KfnmtAlarmListExtraProcessStatus  toEntity(AlarmListExtraProcessStatus domain) {
		return new KfnmtAlarmListExtraProcessStatus(
				domain.getExtraProcessStatusID(),
				domain.getCompanyID(),
				domain.getStartDate(),
				domain.getStartTime(),
				domain.getEmployeeID().isPresent() ==true?domain.getEmployeeID().get(): null,
				domain.getEndDate().isPresent()==true? domain.getEndDate().get(): null,
				domain.getEndTime().isPresent()==true? domain.getEndTime().get(): null,
				domain.getStatus().value
				);
	}
	
	public AlarmListExtraProcessStatus toDomain() {
		return new AlarmListExtraProcessStatus(
				this.extraProcessStatusID,
				this.companyID,
				this.startDate,
				this.startTime,
				this.employeeID,
				this.endDate,
				this.endTime,
				EnumAdaptor.valueOf(this.status, ExtractionState.class) 
				);
	}



	public KfnmtAlarmListExtraProcessStatus(String extraProcessStatusID, String companyID, GeneralDate startDate,
			Integer startTime, String employeeID, GeneralDate endDate, Integer endTime, int status) {
		super();
		this.extraProcessStatusID = extraProcessStatusID;
		this.companyID = companyID;
		this.startDate = startDate;
		this.startTime = startTime;
		this.employeeID = employeeID;
		this.endDate = endDate;
		this.endTime = endTime;
		this.status = status;
	}



	

	
}
