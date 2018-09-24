package nts.uk.screen.at.infra.worktype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private static final String SELECT_OT_KAF022;
	private static final String SELECT_ABSENCE_KAF022;
	private static final String SELECT_ABWKCANGE_KAF022;
	private static final String SELECT_BOUNCE_KAF022;
	private static final String SELECT_HDTIME_KAF022;
	private static final String SELECT_HDSHIP_KAF022;

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
		stringBuilder.append(" OR c.morningAtr = :morningAtr1");
		stringBuilder.append(" OR c.morningAtr = :morningAtr2");
		stringBuilder.append(" OR c.afternoonAtr = :afternoonAtr1");
		stringBuilder.append(" OR c.afternoonAtr = :afternoonAtr2");
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
		
		//KAF022
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND c.deprecateAtr = 0 ");
		stringBuilder.append(" AND (c.oneDayAtr = 0 OR c.oneDayAtr = 11 OR c.oneDayAtr = 7 OR c.oneDayAtr = 10 OR c.morningAtr = 0 OR c.morningAtr = 11 OR c.morningAtr = 7 OR c.morningAtr = 10 OR c.afternoonAtr = 0 OR c.afternoonAtr = 11 OR c.afternoonAtr = 7 OR c.afternoonAtr = 10 )");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_OT_KAF022 = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND ((c.worktypeAtr = 0 AND c.oneDayAtr = :oneDayAtr)");
		stringBuilder.append(" OR (c.worktypeAtr = 1 AND ((c.morningAtr = :morningAtr AND (c.afternoonAtr = 0 OR c.afternoonAtr = 7 OR c.afternoonAtr = 6 OR c.afternoonAtr = 1 OR c.afternoonAtr = 8 OR c.afternoonAtr = 4 OR c.afternoonAtr = 5 OR c.afternoonAtr = 9)) "
				+ " OR (c.afternoonAtr = :afternoonAtr AND (c.morningAtr = 0 OR c.morningAtr = 7 OR c.morningAtr = 6 OR c.morningAtr = 1 OR c.morningAtr = 8 OR c.morningAtr = 4 OR c.morningAtr = 5 OR c.morningAtr = 9)))))");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_ABSENCE_KAF022 = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND c.deprecateAtr = 0");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_ABWKCANGE_KAF022 = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND c.deprecateAtr = 0 AND (c.oneDayAtr = 0 ");
		stringBuilder.append(" OR c.oneDayAtr = 11 OR c.oneDayAtr = 7 OR c.morningAtr IN :halfDay OR c.afternoonAtr IN :halfDay)");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_BOUNCE_KAF022 = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND c.deprecateAtr = 0 AND c.oneDayAtr = 11 ");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_HDTIME_KAF022 = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
		stringBuilder.append(
				"(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
		stringBuilder.append(
				"ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
		stringBuilder.append(" AND ((c.worktypeAtr = 0 AND c.oneDayAtr = :oneDayAtr)");
		stringBuilder.append(" OR (c.worktypeAtr = 1 AND c.morningAtr = :morningAtr2 AND c.afternoonAtr IN :afternoon2 )");
		stringBuilder.append(" OR (c.worktypeAtr = 1 AND c.morningAtr IN :morningAtr3 AND c.afternoonAtr = :afternoon3 )");
		stringBuilder.append(" OR (c.worktypeAtr = 1 AND c.morningAtr = :morningAtr4 AND c.afternoonAtr IN :afternoon4 )");
		stringBuilder.append(" OR (c.worktypeAtr = 1 AND c.morningAtr IN :morningAtr5 AND c.afternoonAtr = :afternoon5 ))");
		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		SELECT_HDSHIP_KAF022 = stringBuilder.toString();
		
	}
	
	@Override
	public List<WorkTypeDto> findHdShipKaf022(String companyId, int oneDayAtr, int morningAtr2, List<Integer> afternoon2, List<Integer> morningAtr3, int afternoon3, int morningAtr4, List<Integer> afternoon4, List<Integer> morningAtr5, int afternoon5) {
		if(afternoon2.isEmpty() || morningAtr3.isEmpty() || afternoon4.isEmpty() || morningAtr5.isEmpty()){
			return new ArrayList<>();
		}
		return this.queryProxy().query(SELECT_HDSHIP_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("oneDayAtr", oneDayAtr)
				.setParameter("morningAtr2", morningAtr2)
				.setParameter("afternoon2", afternoon2)
				.setParameter("morningAtr3", morningAtr3)
				.setParameter("afternoon3", afternoon3)
				.setParameter("morningAtr4", morningAtr4)
				.setParameter("afternoon4", afternoon4)
				.setParameter("morningAtr5", morningAtr5)
			    .setParameter("afternoon5", afternoon5)
				.getList();
	}
	
	@Override
	public List<WorkTypeDto> findHdTimeKaf022(String companyId) {
		return this.queryProxy().query(SELECT_HDTIME_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
				.getList();
	}
	
	@Override
	public List<WorkTypeDto> findBounceKaf022(String companyId, List<Integer> halfDay) {
		if(halfDay.isEmpty()){
			return Collections.emptyList();
		}else{
			return this.queryProxy().query(SELECT_BOUNCE_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
					.setParameter("halfDay", halfDay)
					.getList();
		}
	}
	
	@Override
	public List<WorkTypeDto> findWkChangeKaf022(String companyId) {
		return this.queryProxy().query(SELECT_ABWKCANGE_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
				.getList();
	}
	
	@Override
	public List<WorkTypeDto> findAbsenceKaf022(String companyId, Integer oneDayAtr, Integer morningAtr, Integer afternoonAtr) {
		return this.queryProxy().query(SELECT_ABSENCE_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("oneDayAtr", oneDayAtr)
				.setParameter("morningAtr", morningAtr)
				.setParameter("afternoonAtr", afternoonAtr)
				.getList();
	}
	
	@Override
	public List<WorkTypeDto> findOtKaf022(String companyId) {
		return this.queryProxy().query(SELECT_OT_KAF022, WorkTypeDto.class).setParameter("companyId", companyId)
				.getList();
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
					List<WorkTypeDto> dto = this.queryProxy().query(SELECT_WORKTYPE_KDW006, WorkTypeDto.class).setParameter("companyId", companyId)
													.setParameter("workTypeAtr", item).getList();
					if(!dto.isEmpty()){
						listNew.addAll(dto);
					}
				}else{
					List<WorkTypeDto> typeDto = this.queryProxy().query(SELECT_WORKTYPE_KDW006G, WorkTypeDto.class).setParameter("companyId", companyId)
							.setParameter("workTypeAtr", item).getList();
					if(!typeDto.isEmpty()){
						listNew.addAll(typeDto);
					}
				}
			}
		}
		return listNew;
	}

	@Override
	public List<WorkTypeDto> findAllWorkTypeSPE(String companyId, int abolishAtr, int oneDayAtr1, int oneDayAtr2) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE_SPE, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr)
				.setParameter("oneDayAtr1", oneDayAtr1)
				.setParameter("oneDayAtr2", oneDayAtr2)
				.setParameter("morningAtr1", oneDayAtr1)
				.setParameter("morningAtr2", oneDayAtr2)
				.setParameter("afternoonAtr1", oneDayAtr1)
				.setParameter("afternoonAtr2", oneDayAtr2).getList();
	}
	
	@Override
	public List<WorkTypeDto> findAllWorkTypeDisp(String companyId, int abolishAtr) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE_DISP, WorkTypeDto.class).setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr).getList();
	}

}
