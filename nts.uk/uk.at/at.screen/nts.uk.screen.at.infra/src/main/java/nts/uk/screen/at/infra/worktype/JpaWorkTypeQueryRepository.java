package nts.uk.screen.at.infra.worktype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeQueryRepository;

@Stateless
public class JpaWorkTypeQueryRepository extends JpaRepository implements WorkTypeQueryRepository {

	private static final String SELECT_ALL_WORKTYPE;
	private static final String SELECT_BY_WORKTYPE_ATR;
	private static final String SELECT_ALL_WORKTYPE_SPE;
	private static final String SELECT_ALL_WORKTYPE_DISP;
	private static final String SELECT_WORKTYPE_KDW006;
	private static final String SELECT_WORKTYPE_KDW006G;

	static {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_ALL_WORKTYPE = stringBuilder.toString();

		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, 0) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append("AND c.oneDayAtr IN :workTypeAtr ");
		stringBuilder.append("OR c.morningAtr IN :workTypeAtr ");
		stringBuilder.append("OR c.afternoonAtr IN :workTypeAtr ");
		stringBuilder.append("ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_BY_WORKTYPE_ATR = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, 0) ");
		stringBuilder.append("FROM KshmtWorkType c ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId AND c.deprecateAtr = 0 ");
		stringBuilder.append("AND c.oneDayAtr = :workTypeAtr ");
//		stringBuilder.append("OR c.morningAtr IN :workTypeAtr ");
//		stringBuilder.append("OR c.afternoonAtr IN :workTypeAtr) ");
		stringBuilder.append("ORDER BY c.kshmtWorkTypePK.workTypeCode ASC ");
		SELECT_WORKTYPE_KDW006 = stringBuilder.toString();   
		
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, 0) ");
		stringBuilder.append("FROM KshmtWorkType c ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId AND c.deprecateAtr = 0 ");
		stringBuilder.append("AND (c.morningAtr = :workTypeAtr ");
		stringBuilder.append("OR c.afternoonAtr = :workTypeAtr) ");
		stringBuilder.append("ORDER BY c.kshmtWorkTypePK.workTypeCode ASC ");
		SELECT_WORKTYPE_KDW006G = stringBuilder.toString();   
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append("AND c.deprecateAtr = :abolishAtr ");
		stringBuilder.append("AND c.oneDayAtr = :oneDayAtr1");
		stringBuilder.append(" OR c.oneDayAtr = :oneDayAtr2");
		stringBuilder.append(" ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_ALL_WORKTYPE_SPE = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append("AND c.deprecateAtr = :abolishAtr ");
		stringBuilder.append(" ORDER BY  CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC ");
		SELECT_ALL_WORKTYPE_DISP= stringBuilder.toString();
	}

	@Override
	public List<WorkTypeDto> findAllWorkType(String companyId) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE, WorkTypeDto.class).setParameter("companyId", companyId)
				.getList();
	}

	@Override
	public List<WorkTypeDto> findAllWorkType(String companyId, List<Integer> workTypeAtrList) {
		
		return this.queryProxy().query(SELECT_BY_WORKTYPE_ATR, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("workTypeAtr", workTypeAtrList).getList();
	}
	
	@Override
	public List<WorkTypeDto> findWorkType(String companyId, List<Integer> workTypeAtrList) {
		List<WorkTypeDto> listNew = new ArrayList<>();
		if(!workTypeAtrList.isEmpty()){
			for(int item: workTypeAtrList){
				if(item == 0){
					Optional<WorkTypeDto> dto = this.queryProxy().query(SELECT_WORKTYPE_KDW006, WorkTypeDto.class).setParameter("companyId", companyId)
													.setParameter("workTypeAtr", item).getSingle();
					if(dto != null){
						listNew.add(dto.get());
					}
				}else{
					Optional<WorkTypeDto> typeDto = this.queryProxy().query(SELECT_WORKTYPE_KDW006G, WorkTypeDto.class).setParameter("companyId", companyId)
							.setParameter("workTypeAtr", item).getSingle();
					if(typeDto != null){
						listNew.add(typeDto.get());
					}
				}
			}
		}
		return listNew;
	}

	@Override
	public List<WorkTypeDto> findAllWorkTypeSPE(String companyId, int abolishAtr, int oneDayAtr1, int oneDayAtr2) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE_SPE, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr).setParameter("oneDayAtr1", oneDayAtr1)
				.setParameter("oneDayAtr2", oneDayAtr2).getList();
	}
	
	@Override
	public List<WorkTypeDto> findAllWorkTypeDisp(String companyId, int abolishAtr) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE_DISP, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr).getList();
	}

}
