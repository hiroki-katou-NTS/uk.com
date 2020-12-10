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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompany;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_MAXDAYS_WKTM_CMP_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkMaxdaysWktmCmpDtl extends ContractUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscmtAlchkMaxdaysWktmCmpDtl> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkMaxdaysWktmCmpDtl.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkMaxdaysWktmCmpDtlPk pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static List<KscmtAlchkMaxdaysWktmCmpDtl> fromDomain(String companyId, MaxDayOfWorkTimeCompany domain) {
		
		return domain.getMaxDayOfWorkTime().getWorkTimeCodeList().stream()
				.map( wt -> new KscmtAlchkMaxdaysWktmCmpDtl(
						new KscmtAlchkMaxdaysWktmCmpDtlPk( companyId, domain.getCode().v(), wt.v())))
				.collect(Collectors.toList());
	}
	
	public WorkTimeCode toWorkTimeCode() {
		return new WorkTimeCode(this.pk.workTimeCode);
	}

}
