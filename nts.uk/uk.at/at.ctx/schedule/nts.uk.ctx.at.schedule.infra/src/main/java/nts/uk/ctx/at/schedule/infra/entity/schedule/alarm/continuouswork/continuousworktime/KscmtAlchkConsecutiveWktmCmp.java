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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP
 * @author hiroko_miura
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP")
public class KscmtAlchkConsecutiveWktmCmp extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkConsecutiveWktmCmp> MAPPER = new JpaEntityMapper<>(KscmtAlchkConsecutiveWktmCmp.class);

	@EmbeddedId
	public KscmtAlchkConsecutiveWktmCmpPk pk;
	
	/**
	 * 名称
	 */
	@Column(name = "NAME")
	public String name;
	
	/**
	 * 会社の就業時間帯の連続勤務できる上限日数
	 */
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
	public static KscmtAlchkConsecutiveWktmCmp of (String companyId, MaxDaysOfContinuousWorkTimeCompany domain) {
		val pk = new KscmtAlchkConsecutiveWktmCmpPk();
		pk.companyId = companyId;
		pk.code = domain.getCode().v();
		
		val entity = new KscmtAlchkConsecutiveWktmCmp();
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
	public MaxDaysOfContinuousWorkTimeCompany toDomain(List<KscmtAlchkConsecutiveWktmCmpDtl> details) {
		
		List<WorkTimeCode> worktimeLst = details.stream()
				.filter(dtl -> dtl.pk.code == this.pk.code)
				.map(dtl -> new WorkTimeCode(dtl.pk.wktmCode))
				.collect(Collectors.toList());
		
		val maxWorktime = new MaxDaysOfConsecutiveWorkTime(worktimeLst, new ConsecutiveNumberOfDays(this.maxConsDays));
		
		val domain = new MaxDaysOfContinuousWorkTimeCompany(
				new ConsecutiveWorkTimeCode(this.pk.code)
				, new ConsecutiveWorkTimeName(this.name)
				, maxWorktime);
		
		return domain;
	}
}
