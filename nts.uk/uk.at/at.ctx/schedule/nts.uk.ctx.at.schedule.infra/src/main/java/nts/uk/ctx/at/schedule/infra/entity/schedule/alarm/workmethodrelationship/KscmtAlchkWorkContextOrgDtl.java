package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrganization;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_ORG_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkWorkContextOrgDtl extends ContractUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscmtAlchkWorkContextOrgDtl> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkWorkContextOrgDtl.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkWorkContextOrgDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static List<KscmtAlchkWorkContextOrgDtl> fromDomain(WorkMethodRelationshipOrganization domain) {
		
		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();
		
		List<WorkMethod> currentWorkMethodList = relationship.getCurrentWorkMethodList();
		if (currentWorkMethodList.get(0).getWorkMethodClassification() != WorkMethodClassfication.ATTENDANCE) {
			
			return new ArrayList<>();
		}
		
		return KscmtAlchkWorkContextOrgDtlPk.fromDomain(domain)
				.stream().map( pk -> new KscmtAlchkWorkContextOrgDtl(pk))
				.collect(Collectors.toList());
		
	}
	
	public WorkMethodAttendance toCurrentWorkMethodAttendance() {
		return new WorkMethodAttendance(new WorkTimeCode(this.pk.currentWorkTimeCode));
	}
	
}
