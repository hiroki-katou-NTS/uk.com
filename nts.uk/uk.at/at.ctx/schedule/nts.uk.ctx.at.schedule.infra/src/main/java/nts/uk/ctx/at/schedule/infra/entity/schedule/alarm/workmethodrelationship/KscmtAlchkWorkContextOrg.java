package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.RelationshipSpecifiedMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodContinuousWork;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodHoliday;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrganization;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_ORG")
public class KscmtAlchkWorkContextOrg  extends ContractUkJpaEntity {
	
	public static final Function<NtsResultRecord, KscmtAlchkWorkContextOrg> mapper = 
							s -> new JpaEntityMapper<>(KscmtAlchkWorkContextOrg.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkWorkContextOrgPk pk;

	@Column(name = "PROHIBIT_ATR")
	public int specifiedMethod;
	
	@Column(name = "CURRENT_WORK_ATR")
	public int currentWorkingMethod;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KscmtAlchkWorkContextOrg fromDomain(String companyId, WorkMethodRelationshipOrganization domain) {
		
		return new KscmtAlchkWorkContextOrg(
				KscmtAlchkWorkContextOrgPk.fromDomain(companyId, domain), 
				domain.getWorkMethodRelationship().getSpecifiedMethod().value, 
				domain.getWorkMethodRelationship().getCurrentWorkMethodList().get(0).getWorkMethodClassification().value);
	}
		
	public WorkMethodRelationshipOrganization toDomain(List<KscmtAlchkWorkContextOrgDtl> dtlList) {
		
		
		return new WorkMethodRelationshipOrganization( this.pk.toTargetOrgIdenInfor(), 
				new WorkMethodRelationship( 
						this.pk.toPrevWorkMethod(), 
						this.toCurrentWorkMethod(dtlList), 
						RelationshipSpecifiedMethod.of(this.specifiedMethod)));
	}
	
	private List<WorkMethod> toCurrentWorkMethod(List<KscmtAlchkWorkContextOrgDtl> dtlList) {
		
		switch (WorkMethodClassfication.of(this.currentWorkingMethod)) {
			case ATTENDANCE:
				return dtlList.stream().map(dtl -> dtl.toCurrentWorkMethodAttendance()).collect(Collectors.toList()); 
			case HOLIDAY:
				return Arrays.asList(new WorkMethodHoliday());
			case CONTINUOSWORK:
			default:
				return Arrays.asList(new WorkMethodContinuousWork());
		}
	}
	

}
