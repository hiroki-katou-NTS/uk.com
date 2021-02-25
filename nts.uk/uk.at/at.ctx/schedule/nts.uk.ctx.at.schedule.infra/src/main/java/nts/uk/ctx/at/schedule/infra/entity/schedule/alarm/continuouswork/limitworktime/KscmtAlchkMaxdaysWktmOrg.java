package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.limitworktime;

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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDay;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_MAXDAYS_WKTM_ORG")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkMaxdaysWktmOrg extends ContractUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscmtAlchkMaxdaysWktmOrg> mapper = 
			s -> new JpaEntityMapper<>(KscmtAlchkMaxdaysWktmOrg.class).toEntity(s);
	
	@EmbeddedId
	public KscmtAlchkMaxdaysWktmOrgPk pk;
	
	@Column(name = "NAME")
	public String name;
	
	@Column(name = "MAX_DAYS")
	public int maxDays;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KscmtAlchkMaxdaysWktmOrg fromDomain(String companyId, MaxDayOfWorkTimeOrganization domain) {
		return new KscmtAlchkMaxdaysWktmOrg(
				new KscmtAlchkMaxdaysWktmOrgPk(
						companyId, 
						domain.getTargeOrg().getUnit().value, 
						domain.getTargeOrg().getTargetId(), 
						domain.getCode().v()), 
				domain.getName().v(), 
				domain.getMaxDayOfWorkTime().getMaxDay().v());
	}
	
	public MaxDayOfWorkTimeOrganization toDomain(List<KscmtAlchkMaxdaysWktmOrgDtl> dtlList) {
		
		return new MaxDayOfWorkTimeOrganization(
				TargetOrgIdenInfor.createFromTargetUnit(
						TargetOrganizationUnit.valueOf(this.pk.targetUnit), 
						this.pk.targetId),
				new MaxDayOfWorkTimeCode(this.pk.code), 
				new MaxDayOfWorkTimeName(this.name), 
				new MaxDayOfWorkTime(
						dtlList.stream().map( dtl -> dtl.toWorkTimeCode()).collect(Collectors.toList()), 
						new MaxDay(this.maxDays)));
	}

}
