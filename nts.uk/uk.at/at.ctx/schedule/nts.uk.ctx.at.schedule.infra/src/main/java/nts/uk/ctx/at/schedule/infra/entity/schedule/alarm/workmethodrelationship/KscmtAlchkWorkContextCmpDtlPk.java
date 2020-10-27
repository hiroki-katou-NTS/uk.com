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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCompany;
import nts.uk.shr.com.context.AppContexts;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkWorkContextCmpDtlPk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "PREVIOUS_WORK_ATR")
	public int prevWorkMethod;
	
	@Column(name = "PREVIOUS_WKTM_CD")
	public String prevWorkTimeCode;
	
	@Column(name = "CURRENT_WKTM_CD")
	public String currentWorkTimeCode;
	
	/**
	 * <WARNING> is called only when currentWorkMethod is Attendance
	 * 
	 * @param domain
	 * @return
	 */
	public static List<KscmtAlchkWorkContextCmpDtlPk> fromDomain(String companyId, WorkMethodRelationshipCompany domain) {
		
		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();
		
		WorkMethod prevWorkMethod = relationship.getPrevWorkMethod();
		String prevWorkTimeCode = prevWorkMethod.getWorkMethodClassification()!= WorkMethodClassfication.ATTENDANCE ?
				KscmtAlchkWorkContextCmp.HOLIDAY_WORK_TIME_CODE : 
				((WorkMethodAttendance) prevWorkMethod).getWorkTimeCode().v();
		
		return relationship.getCurrentWorkMethodList().stream().map( current -> 
		
			 new KscmtAlchkWorkContextCmpDtlPk( 
					companyId, 
					prevWorkMethod.getWorkMethodClassification().value,
					prevWorkTimeCode, 
					((WorkMethodAttendance) current).getWorkTimeCode().v())
			 
		).collect(Collectors.toList());
		
	}

}
