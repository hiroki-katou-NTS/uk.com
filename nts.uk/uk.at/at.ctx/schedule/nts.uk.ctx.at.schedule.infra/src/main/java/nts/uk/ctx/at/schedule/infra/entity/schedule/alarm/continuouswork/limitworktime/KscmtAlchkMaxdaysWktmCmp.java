package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.limitworktime;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime.MaxDay;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime.MaxDayOfWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime.MaxDayOfWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime.WorkTimeMaximumCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime.WorkTimeMaximumName;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_MAXDAYS_WKTM_CMP")
@AllArgsConstructor
public class KscmtAlchkMaxdaysWktmCmp extends ContractUkJpaEntity {
	
	public static final Function<NtsResultRecord, KscmtAlchkMaxdaysWktmCmp> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkMaxdaysWktmCmp.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkMaxdaysWktmCmpPk pk;
	
	@Column(name = "NAME")
	public String name;
	
	@Column(name = "MAX_DAYS")
	public int maxDays;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KscmtAlchkMaxdaysWktmCmp fromDomain(String companyId, MaxDayOfWorkTimeCompany domain) {
		
		return new KscmtAlchkMaxdaysWktmCmp(
				new KscmtAlchkMaxdaysWktmCmpPk( companyId, domain.getCode().v()), 
				domain.getName().v(), 
				domain.getMaxDayOfWorkTime().getMaxDay().v());
	}
	
	public MaxDayOfWorkTimeCompany toDomain(List<KscmtAlchkMaxdaysWktmCmpDtl> dtlList) {
		
		return new MaxDayOfWorkTimeCompany(
				new WorkTimeMaximumCode(this.pk.code), 
				new WorkTimeMaximumName(this.name), 
				new MaxDayOfWorkTime(
						dtlList.stream().map( dtl -> dtl.toWorkTimeCode()).collect(Collectors.toList()), 
						new MaxDay(this.maxDays)));
	}

}
