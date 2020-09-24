package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCom;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
public class KscmtAlchkWorkContextComPk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "PREVIOUS_WORK_ATR")
	public int prevWorkMethod;
	
	@Column(name = "PREVIOUS_WKTM_CD")
	public String prevWorkTimeCode;
	
	public static KscmtAlchkWorkContextComPk fromDomain(WorkMethodRelationshipCom domain) {
		
		WorkMethod prevWorkMethod = domain.getWorkMethodRelationship().getPrevWorkMethod();
		
		if (prevWorkMethod.getWorkMethodClassification().isAttendance()) {
			String prevWorkTimeCode =  ((WorkMethodAttendance) prevWorkMethod).getWorkTimeCode().v();
			return new KscmtAlchkWorkContextComPk(
					domain.getCompanyId(), 
					WorkMethodClassfication.ATTENDANCE.value, 
					prevWorkTimeCode);
		}
		
		return new KscmtAlchkWorkContextComPk(
				domain.getCompanyId(), 
				WorkMethodClassfication.HOLIDAY.value, 
				KscmtAlchkWorkContextCom.HOLIDAY_WORK_TIME_CODE);
	}

}
