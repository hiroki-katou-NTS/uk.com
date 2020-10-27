package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfConsecutiveWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG
 * @author hiroko_miura
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG")
public class KscmtAlchkConsecutiveWktmOrg extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkConsecutiveWktmOrg> MAPPER = new JpaEntityMapper<>(KscmtAlchkConsecutiveWktmOrg.class);

	@EmbeddedId
	public KscmtAlchkConsecutiveWktmOrgPk pk;
	
	/** 名称 */
	@Column(name = "NAME")
	public String name;
	
	/** 組織の就業時間帯の連続勤務できる上限日数 */
	@Column(name = "MAX_CONSECUTIVE_DAYS")
	public int maxConsDays;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	
	/**
	 * convert to entity
	 * @param companyId
	 * @param domain
	 * @return
	 */
	public static KscmtAlchkConsecutiveWktmOrg of (String companyId, MaxDaysOfContinuousWorkTimeOrganization domain) {
		val pk = new KscmtAlchkConsecutiveWktmOrgPk();
		pk.companyId = companyId;
		pk.targetUnit = domain.getTargeOrg().getUnit().value;
		pk.targetId = domain.getTargeOrg().getTargetId();
		pk.code = domain.getCode().v();
		
		val entity = new KscmtAlchkConsecutiveWktmOrg();
		entity.pk = pk;
		entity.name = domain.getName().v();
		entity.maxConsDays = domain.getMaxDaysContiWorktime().getNumberOfDays().v();
		
		return entity;
	}
	
	/**
	 * convert to domain
	 * @param details
	 * @return
	 */
	public MaxDaysOfContinuousWorkTimeOrganization toDomain(List<KscmtAlchkConsecutiveWktmOrgDtl> details) {
		
		List<WorkTimeCode> worktimeLst = details.stream()
				.filter(dtl -> this.pk.code == dtl.pk.code)
				.map(dtl -> new WorkTimeCode(dtl.pk.wktmCode))
				.collect(Collectors.toList());
		
		MaxDaysOfConsecutiveWorkTime maxWorktime = new MaxDaysOfConsecutiveWorkTime(worktimeLst, new ConsecutiveNumberOfDays(this.maxConsDays));
		
		val domain = new MaxDaysOfContinuousWorkTimeOrganization(
				TargetOrgIdenInfor.createFromTargetUnit(TargetOrganizationUnit.valueOf(this.pk.targetUnit), this.pk.targetId)
				, new ConsecutiveWorkTimeCode(this.pk.code)
				, new ConsecutiveWorkTimeName(this.name)
				, maxWorktime);
		
		return domain;
	}
}
