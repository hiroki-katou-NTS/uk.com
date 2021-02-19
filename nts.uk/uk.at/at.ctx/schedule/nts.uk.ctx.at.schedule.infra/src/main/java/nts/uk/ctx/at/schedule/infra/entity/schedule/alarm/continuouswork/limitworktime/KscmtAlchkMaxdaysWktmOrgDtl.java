package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.limitworktime;

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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "	KSCMT_ALCHK_MAXDAYS_WKTM_ORG_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkMaxdaysWktmOrgDtl extends ContractUkJpaEntity {
	
	public static final Function<NtsResultRecord, KscmtAlchkMaxdaysWktmOrgDtl> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkMaxdaysWktmOrgDtl.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkMaxdaysWktmOrgDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public WorkTimeCode toWorkTimeCode() {
		return new WorkTimeCode(this.pk.workTimeCode);
	}
	
	public static List<KscmtAlchkMaxdaysWktmOrgDtl> fromDomain(String companyId, MaxDayOfWorkTimeOrganization domain) {
		
		return domain.getMaxDayOfWorkTime().getWorkTimeCodeList().stream()
				.map( wt -> new KscmtAlchkMaxdaysWktmOrgDtl(
								new KscmtAlchkMaxdaysWktmOrgDtlPk(
									companyId, 
									domain.getTargeOrg().getUnit().value, 
									domain.getTargeOrg().getTargetId(), 
									domain.getCode().v(), 
									wt.v())))
				.collect(Collectors.toList());
	}

}
