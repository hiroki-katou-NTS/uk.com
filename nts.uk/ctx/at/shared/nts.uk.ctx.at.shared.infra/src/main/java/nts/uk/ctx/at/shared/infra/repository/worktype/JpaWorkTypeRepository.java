package nts.uk.ctx.at.shared.infra.repository.worktype;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.ctx.at.shared.dom.worktype.WorkType;
import nts.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.KmnmtWorkType;
@Stateless
public class JpaWorkTypeRepository extends JpaRepository implements WorkTypeRepository{

	private final String SELECT_FROM_WORKTYPE = "SELECT c FROM KmnmtWorkType c";
	private final String SELECT_WORKTYPE = SELECT_FROM_WORKTYPE 
			+ " WHERE c.kmnmtWorkTypePK.companyId = :companyId"
			+ " AND c.kmnmtWorkTypePK.workTypeCode IN :lstPossible";
	private static WorkType toDomain(KmnmtWorkType entity){
		val domain = WorkType.createSimpleFromJavaType(
				entity.kmnmtWorkTypePK.companyId,
				entity.kmnmtWorkTypePK.workTypeCode,
				entity.sortOrder,
				entity.symbolicName,
				entity.name,
				entity.abbreviationName,
				entity.memo,
				entity.useAtr);
		return domain;
	}
	@Override
	public List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible) {
		return this.queryProxy().query(SELECT_WORKTYPE, KmnmtWorkType.class)
				.setParameter("companyId", companyId)
				.setParameter("lstPossible", lstPossible)
				.getList(c->toDomain(c));
	}

}
