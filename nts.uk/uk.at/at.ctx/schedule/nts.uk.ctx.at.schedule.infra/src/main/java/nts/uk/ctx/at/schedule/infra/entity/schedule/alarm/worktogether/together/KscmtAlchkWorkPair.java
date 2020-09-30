package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.together;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * KSCMT_ALCHK_WORK_PAIR
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_WORK_PAIR")
public class KscmtAlchkWorkPair extends ContractCompanyUkJpaEntity implements Serializable {

private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtAlchkWorkPair> MAPPER = new JpaEntityMapper<>(KscmtAlchkWorkPair.class);

	@EmbeddedId
	public KscmtAlchkWorkPairPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	/**
	 * convert to entityList
	 * @param domain
	 * @return
	 */
	public static List<KscmtAlchkWorkPair> toEntityList (WorkTogether domain) {
		
		return domain.getEmpMustWorkTogetherLst().stream()
				.map(sid -> {
					KscmtAlchkWorkPairPk pk = new KscmtAlchkWorkPairPk(domain.getSid(), sid);
					return new KscmtAlchkWorkPair(pk);
					})
				.collect(Collectors.toList());
	}
	
	public static WorkTogether toDomain (String sid, List<KscmtAlchkWorkPair> lstEntity) {
		
		List<String> pairList = lstEntity.stream()
				.map(e -> e.pk.pairEmployeeId)
				.collect(Collectors.toList());
		
		return new WorkTogether(sid, pairList);
	}
}
