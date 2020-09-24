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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCom;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_CMP_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkWorkContextComDtl extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscmtAlchkWorkContextComDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static Function<NtsResultRecord, KscmtAlchkWorkContextComDtl> mapper = s -> 
			new KscmtAlchkWorkContextComDtl(
					new KscmtAlchkWorkContextComDtlPk(
							s.getString("CID"), 
							s.getInt("PREVIOUS_WORK_ATR"), 
							s.getString("PREVIOUS_WKTM_CD"), 
							s.getString("TGT_WKTM_CD")));
			
	public static List<KscmtAlchkWorkContextComDtl> fromDomain(WorkMethodRelationshipCom domain) {
		
		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();
		
		List<WorkMethod> currentWorkMethodList = relationship.getCurrentWorkMethodList();
		if (!currentWorkMethodList.get(0).getWorkMethodClassification().isAttendance()) {
			return new ArrayList<>();
		} 
		
		return KscmtAlchkWorkContextComDtlPk.fromDomain(domain)
				.stream().map( pk -> new KscmtAlchkWorkContextComDtl(pk))
				.collect(Collectors.toList());
		
	}
	
	public WorkMethodAttendance toWorkMethodAttendance() {
		return new WorkMethodAttendance(new WorkTimeCode(this.pk.currentWorkTimeCode));
	}
	
}
