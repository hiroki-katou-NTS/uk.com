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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkWorkContextOrgPk {
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	@Column(name = "TARGET_ID")
	public String targetId;

	@Column(name = "PREVIOUS_WORK_ATR")
	public int prevWorkMethod;
	
	@Column(name = "PREVIOUS_WKTM_CD")
	public String prevWorkTimeCode;
	
	public static KscmtAlchkWorkContextOrgPk fromDomain(String companyId,WorkMethodRelationshipOrganization domain) {
		
		WorkMethod prevWorkMethod = domain.getWorkMethodRelationship().getPrevWorkMethod();
		String prevWorkTimeCode = prevWorkMethod.getWorkMethodClassification() == WorkMethodClassfication.ATTENDANCE ?
				((WorkMethodAttendance) prevWorkMethod).getWorkTimeCode().v() :
				KscmtAlchkWorkContextCmp.HOLIDAY_WORK_TIME_CODE;
		
		return new KscmtAlchkWorkContextOrgPk(
				companyId, 
				domain.getTargetOrg().getUnit().value, 
				domain.getTargetOrg().getTargetId(), 
				prevWorkMethod.getWorkMethodClassification().value, 
				prevWorkTimeCode);
	}
	
	public TargetOrgIdenInfor toTargetOrgIdenInfor() {
		
		return this.targetUnit == TargetOrganizationUnit.WORKPLACE.value ?
				TargetOrgIdenInfor.creatIdentifiWorkplace(this.targetId) : 
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(targetId);
	}
	
	public WorkMethod toPrevWorkMethod() {
		
		return this.prevWorkMethod == WorkMethodClassfication.ATTENDANCE.value ?
				new WorkMethodAttendance(new WorkTimeCode(this.prevWorkTimeCode)) :
				new WorkMethodHoliday();
	}
	
}
