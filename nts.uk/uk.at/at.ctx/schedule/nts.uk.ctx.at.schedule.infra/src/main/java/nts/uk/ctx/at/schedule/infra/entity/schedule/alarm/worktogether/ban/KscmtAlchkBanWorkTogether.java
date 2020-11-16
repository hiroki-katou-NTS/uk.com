package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban;

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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.ApplicableTimeZoneCls;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.MaxOfNumberEmployeeTogether;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_BAN_WORK_TOGETHER
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_WORK_TOGETHER")
public class KscmtAlchkBanWorkTogether extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkBanWorkTogether> MAPPER = new JpaEntityMapper<>(KscmtAlchkBanWorkTogether.class);

	@EmbeddedId
	public KscmtAlchkBanWorkTogetherPk pk;
	
	/**
	 * 同時出勤禁止名称
	 */
	@Column(name = "NAME")
	public String name;
	
	/**
	 * 適用する時間帯 (0:全日帯/1:夜勤時間帯)
	 */
	@Column(name = "APPLY_TS")
	public int applyTs;
	
	/**
	 * 許容する人数 (設定内で同時に勤務可能な上限人数)
	 */
	@Column(name = "UPPER_LIMIT")
	public int upperLimit;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	/**
	 * convert to entity
	 * @param domain
	 * @param companyId
	 * @return
	 */
	public static KscmtAlchkBanWorkTogether of (BanWorkTogether domain, String companyId) {
		
		val pk = new KscmtAlchkBanWorkTogetherPk();
		pk.companyId = companyId;
		pk.targetUnit = domain.getTargetOrg().getUnit().value;
		pk.targetId = domain.getTargetOrg().getTargetId();
		pk.code = domain.getCode().v();
		
		val entity = new KscmtAlchkBanWorkTogether();
		entity.pk = pk;
		entity.contractCd = AppContexts.user().contractCode();
		entity.name = domain.getName().v();
		entity.applyTs = domain.getApplicableTimeZoneCls().value;
		entity.upperLimit = domain.getUpperLimit();
		
		return entity;
	}
	
	/**
	 * convert to domain
	 * @param dtl
	 * @return
	 */
	public BanWorkTogether toDomain (List<KscmtAlchkBanWorkTogetherDtl> dtl) {
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.createFromTargetUnit(
				  TargetOrganizationUnit.valueOf(this.pk.targetUnit)
				, this.pk.targetId);
		
		return new BanWorkTogether(
				  targetOrg
				, new BanWorkTogetherCode(this.pk.code)
				, new BanWorkTogetherName(this.name)
				, ApplicableTimeZoneCls.of(this.applyTs)
				, dtl.stream().map(x -> x.pk.employeeId).collect(Collectors.toList())
				, this.upperLimit
				);
	}
}
