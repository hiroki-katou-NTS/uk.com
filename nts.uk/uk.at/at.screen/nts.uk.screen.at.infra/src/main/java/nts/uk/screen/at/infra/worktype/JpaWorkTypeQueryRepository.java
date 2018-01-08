package nts.uk.screen.at.infra.worktype;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeQueryRepository;

@Stateless
public class JpaWorkTypeQueryRepository extends JpaRepository implements WorkTypeQueryRepository {
	
	private static final String SELECT_ALL_WORKTYPE;
	private static final String SELECT_BY_WORKTYPE_ATR;
	
	static {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append("(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append("ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_ALL_WORKTYPE = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append("(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, 0) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append("ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append("AND c.oneDayAtr IN :workTypeAtr ");
		stringBuilder.append("OR c.morningAtr IN :workTypeAtr ");
		stringBuilder.append("OR c.afternoonAtr IN :workTypeAtr ");
		stringBuilder.append("ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_BY_WORKTYPE_ATR = stringBuilder.toString();
	}
	
	@Override
	public List<WorkTypeDto> findAllWorkType(String companyId) {		
		return this.queryProxy().query(SELECT_ALL_WORKTYPE, WorkTypeDto.class)
				.setParameter("companyId", companyId)
				.getList();
	}
	
	@Override
	public List<WorkTypeDto> findAllWorkType(String companyId, List<Integer> workTypeAtrList) {
		return this.queryProxy().query(SELECT_BY_WORKTYPE_ATR, WorkTypeDto.class)
				.setParameter("companyId", companyId)
				.setParameter("workTypeAtr", workTypeAtrList)
				.getList();
	}
	
}
