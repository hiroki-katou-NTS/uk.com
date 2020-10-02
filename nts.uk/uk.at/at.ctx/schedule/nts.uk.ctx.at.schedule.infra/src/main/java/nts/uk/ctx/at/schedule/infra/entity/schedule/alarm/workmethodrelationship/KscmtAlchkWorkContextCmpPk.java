package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodHoliday;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCompany;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkWorkContextCmpPk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "PREVIOUS_WORK_ATR")
	public int prevWorkMethod;
	
	@Column(name = "PREVIOUS_WKTM_CD")
	public String prevWorkTimeCode;
	
	public static KscmtAlchkWorkContextCmpPk fromDomain(String companyId, WorkMethodRelationshipCompany domain) {
		
		WorkMethod prevWorkMethod = domain.getWorkMethodRelationship().getPrevWorkMethod();
		
		if (prevWorkMethod.getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE) {
			String prevWorkTimeCode =  ((WorkMethodAttendance) prevWorkMethod).getWorkTimeCode().v();
			return new KscmtAlchkWorkContextCmpPk(
					companyId, 
					WorkMethodClassfication.ATTENDANCE.value, 
					prevWorkTimeCode);
		}
		
		return new KscmtAlchkWorkContextCmpPk(
				companyId, 
				WorkMethodClassfication.HOLIDAY.value, 
				KscmtAlchkWorkContextCmp.HOLIDAY_WORK_TIME_CODE);
	}
	
	public WorkMethod toPrevWorkMethod() {
		
		return this.prevWorkMethod == WorkMethodClassfication.ATTENDANCE.value ?
				new WorkMethodAttendance(new WorkTimeCode(this.prevWorkTimeCode)) : 
					new WorkMethodHoliday();
	}

}
