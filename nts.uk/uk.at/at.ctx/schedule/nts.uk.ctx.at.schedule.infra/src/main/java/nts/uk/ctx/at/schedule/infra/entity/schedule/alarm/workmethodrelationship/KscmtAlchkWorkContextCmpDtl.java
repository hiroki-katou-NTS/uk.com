package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import java.util.ArrayList;
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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCompany;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_CMP_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkWorkContextCmpDtl extends ContractUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscmtAlchkWorkContextCmpDtl> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkWorkContextCmpDtl.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkWorkContextCmpDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static List<KscmtAlchkWorkContextCmpDtl> fromDomain(String companyId, WorkMethodRelationshipCompany domain) {

		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();

		List<WorkMethod> currentWorkMethodList = relationship.getCurrentWorkMethodList();
		if (currentWorkMethodList.get(0).getWorkMethodClassification() != WorkMethodClassfication.ATTENDANCE) {
			return new ArrayList<>();
		}
		List<KscmtAlchkWorkContextCmpDtl> cmpDtls = new ArrayList<>();
		return KscmtAlchkWorkContextCmpDtlPk.fromDomain(companyId, domain)
				.stream().map( pk -> {
					KscmtAlchkWorkContextCmpDtl item = new KscmtAlchkWorkContextCmpDtl(pk);
					item.contractCd = AppContexts.user().contractCode();
					return item;
				})
				.collect(Collectors.toList());

	}

	public WorkMethodAttendance toWorkMethodAttendance() {
		return new WorkMethodAttendance(new WorkTimeCode(this.pk.currentWorkTimeCode));
	}
	
}
