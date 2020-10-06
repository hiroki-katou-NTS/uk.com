package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkWorkContextOrgDtlPk {
	
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
	
	@Column(name = "TGT_WKTM_CD")
	public String currentWorkTimeCode;
	
	public static List<KscmtAlchkWorkContextOrgDtlPk> fromDomain(WorkMethodRelationshipOrganization domain) {
		
		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();
		
		WorkMethod prevWorkMethod = relationship.getPrevWorkMethod();
		String prevWorkTimeCode = prevWorkMethod.getWorkMethodClassification() != WorkMethodClassfication.ATTENDANCE ?
				KscmtAlchkWorkContextCmp.HOLIDAY_WORK_TIME_CODE : 
				((WorkMethodAttendance) prevWorkMethod).getWorkTimeCode().v();
		
		return relationship.getCurrentWorkMethodList().stream().map( current -> 
		
			 new KscmtAlchkWorkContextOrgDtlPk( 
					AppContexts.user().companyId(), 
					domain.getTargetOrg().getUnit().value,
					domain.getTargetOrg().getTargetId(),
					prevWorkMethod.getWorkMethodClassification().value, 
					prevWorkTimeCode, 
					((WorkMethodAttendance) current).getWorkTimeCode().v())
		 
			).collect(Collectors.toList());
	}
	
	public TargetOrgIdenInfor toTargetOrgIdenInfor() {
		
		return this.targetUnit == TargetOrganizationUnit.WORKPLACE.value ?
				TargetOrgIdenInfor.creatIdentifiWorkplace(this.targetId) : 
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(targetId);
	}
	
	
}
