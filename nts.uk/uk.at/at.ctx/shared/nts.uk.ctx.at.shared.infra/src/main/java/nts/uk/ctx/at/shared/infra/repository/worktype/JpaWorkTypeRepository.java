/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktype;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypePK;

@Stateless
public class JpaWorkTypeRepository extends JpaRepository implements WorkTypeRepository {

	private final String SELECT_FROM_WORKTYPE = "SELECT c FROM KshmtWorkType c";

	private final String SELECT_WORKTYPE = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.kshmtWorkTypePK.workTypeCode IN :lstPossible";


	private final String SELECT_BY_CID_DISPLAY_ATR = SELECT_FROM_WORKTYPE
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.displayAtr = :displayAtr ORDER BY c.sortOrder ASC";

	private final String FIND_NOT_DEPRECATED_BY_LIST_CODE = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.kshmtWorkTypePK.workTypeCode IN :codes AND c.deprecateAtr = 0";

	private final String FIND_NOT_DEPRECATED = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = 0";

	private static WorkType toDomain(KshmtWorkType entity) {
		val domain = WorkType.createSimpleFromJavaType(entity.kshmtWorkTypePK.companyId,
				entity.kshmtWorkTypePK.workTypeCode, entity.symbolicName,
				entity.name, entity.abbreviationName, entity.memo,
				entity.worktypeAtr, entity.oneDayAtr, entity.morningAtr, entity.afternoonAtr,
				entity.deprecateAtr,entity.calculatorMethod);
		return domain;
	}

	@Override
	public List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible) {
		return this.queryProxy().query(SELECT_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.setParameter("lstPossible", lstPossible).getList(c -> toDomain(c));
	}

	@Override
	public List<WorkType> findByCompanyId(String companyId) {
		String query = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId";
		return this.queryProxy().query(query, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}


	/**
	 * Find by companyId and displayAtr = DISPLAY sort by SORT_ORDER
	 */
	@Override
	public List<WorkType> findByCIdAndDisplayAtr(String companyId, int displayAtr) {
		return this.queryProxy().query(SELECT_BY_CID_DISPLAY_ATR, KshmtWorkType.class)
				.setParameter("companyId", companyId).setParameter("displayAtr", displayAtr).getList(c -> toDomain(c));
	}

	@Override
	public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
		return this.queryProxy().find(new KshmtWorkTypePK(companyId, workTypeCd), KshmtWorkType.class)
				.map(x -> toDomain(x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository#findNotDeprecated(
	 * java.lang.String)
	 */
	@Override
	public List<WorkType> findNotDeprecated(String companyId) {
		return this.queryProxy().query(FIND_NOT_DEPRECATED, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository#
	 * findNotDeprecatedByListCode(java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkType> findNotDeprecatedByListCode(String companyId, List<String> codes) {
		return this.queryProxy().query(FIND_NOT_DEPRECATED_BY_LIST_CODE, KshmtWorkType.class)
				.setParameter("companyId", companyId).setParameter("codes", codes).getList(c -> toDomain(c));
	}

	@Override
	public void add(WorkType worktype) {
		// TODO Auto-generated method stub
		
	}

}
