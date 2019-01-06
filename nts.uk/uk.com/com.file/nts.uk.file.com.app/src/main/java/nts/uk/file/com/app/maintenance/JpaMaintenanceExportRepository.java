package nts.uk.file.com.app.maintenance;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
@Stateless
public class JpaMaintenanceExportRepository extends JpaRepository implements MaintenanceLayoutExportRepository{
	
	
//	private static final String GET_CPS008 = (new StringBuffer()
//			.append("SELECT DISTINCT  a.LAYOUT_CD, a.LAYOUT_NAME,")
//			.append(" (case WHEN b.PER_INFO_CATEGORY_ID IS NULL")
//			.append(" THEN NULL ELSE ")
//			.append(" (SELECT d.ITEM_NAME from PPEMT_PER_INFO_ITEM d")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG e on b.PER_INFO_CATEGORY_ID = e.PER_INFO_CTG_ID ")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG_CM f ON e.CATEGORY_CD = f.CATEGORY_CD")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG_ORDER g ON e.CID = g.CID AND e.PER_INFO_CTG_ID = g.PER_INFO_CTG_ID")
//			.append(" INNER JOIN PPEMT_PER_INFO_ITEM_CM h ON e.CATEGORY_CD = h.CATEGORY_CD ")
//			.append(" AND d.ITEM_CD = h.ITEM_CD AND h.CONTRACT_CD = f.CONTRACT_CD AND h.ITEM_PARENT_CD IS NULL")
//			.append(" where d.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEF_ID AND b.PER_INFO_CATEGORY_ID = d.PER_INFO_CTG_ID")
//			.append(" AND e.ABOLITION_ATR = 0 AND d.ABOLITION_ATR = 0 AND f.CONTRACT_CD = ?contractCd1 AND e.CID = ?companyId1")
//			.append(" AND ((f.SALARY_USE_ATR = 1 AND ?forPayroll = 1)")
//			.append(" OR (f.PERSONNEL_USE_ATR = 1 AND ?forPersonnel = 1)")
//			.append(" OR (f.EMPLOYMENT_USE_ATR = 1 AND ?forAttendance = 1))")
//			.append(" OR (?forPayroll = 0 AND ?forPersonnel = 0 AND ?forAttendance = 0))")
//			.append(" end) as ITEM_NAME, ")
//			.append(" (case WHEN b.PER_INFO_CATEGORY_ID IS NULL")
//			.append(" THEN NULL ELSE ")
//			.append(" (SELECT e.CATEGORY_NAME from PPEMT_PER_INFO_ITEM d")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG e on b.PER_INFO_CATEGORY_ID = e.PER_INFO_CTG_ID ")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG_CM f ON e.CATEGORY_CD = f.CATEGORY_CD")
//			.append(" INNER JOIN PPEMT_PER_INFO_CTG_ORDER g ON e.CID = g.CID AND e.PER_INFO_CTG_ID = g.PER_INFO_CTG_ID")
//			.append(" INNER JOIN PPEMT_PER_INFO_ITEM_CM h ON e.CATEGORY_CD = h.CATEGORY_CD ")
//			.append(" AND d.ITEM_CD = h.ITEM_CD AND h.CONTRACT_CD = f.CONTRACT_CD AND h.ITEM_PARENT_CD IS NULL")
//			.append(" where d.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEF_ID ")
//			.append(" AND b.PER_INFO_CATEGORY_ID = d.PER_INFO_CTG_ID")
//			.append(" AND e.ABOLITION_ATR = 0 AND d.ABOLITION_ATR = 0 AND f.CONTRACT_CD = ?contractCd1 AND e.CID = ?companyId1")
//			.append(" AND ((f.SALARY_USE_ATR = 1 AND ?forPayroll = 1)")
//			.append(" OR (f.PERSONNEL_USE_ATR = 1 AND ?forPersonnel = 1)")
//			.append(" OR (f.EMPLOYMENT_USE_ATR = 1 AND ?forAttendance = 1))")
//			.append(" OR (?forPayroll = 0 AND ?forPersonnel = 0 AND ?forAttendance = 0))")
//			.append(" end) as CATEGORY_NAME FROM PPEMT_MAINTENANCE_LAYOUT a")
//			.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS b ON a.LAYOUT_ID = b.LAYOUT_ID ")
//			.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS_DF c ON a.LAYOUT_ID = c.LAYOUT_ID ")
//			.append(" WHERE a.CID = ?companyId3")
//			.append(" ORDER BY a.LAYOUT_CD ASC")).toString();

	@Override                                                                                       
	public List<MaintenanceLayoutData> getAllMaintenanceLayout(String companyId, String contractCd, int forAttendance, int forPayroll, int forPersonnel) {
		String GET_CPS008 = (new StringBuffer()
				.append("SELECT DISTINCT  a.LAYOUT_CD, a.LAYOUT_NAME,")
				.append(" (case WHEN b.PER_INFO_CATEGORY_ID IS NULL")
				.append(" THEN NULL ELSE ")
				.append(" (SELECT d.ITEM_NAME from PPEMT_PER_INFO_ITEM d")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG e on b.PER_INFO_CATEGORY_ID = e.PER_INFO_CTG_ID ")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_CM f ON e.CATEGORY_CD = f.CATEGORY_CD")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_ORDER g ON e.CID = g.CID AND e.PER_INFO_CTG_ID = g.PER_INFO_CTG_ID")
				.append(" INNER JOIN PPEMT_PER_INFO_ITEM_CM h ON e.CATEGORY_CD = h.CATEGORY_CD ")
				.append(" AND d.ITEM_CD = h.ITEM_CD AND h.CONTRACT_CD = f.CONTRACT_CD AND h.ITEM_PARENT_CD IS NULL")
				.append(" where d.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEF_ID AND b.PER_INFO_CATEGORY_ID = d.PER_INFO_CTG_ID")
				.append(" AND e.ABOLITION_ATR = 0 AND d.ABOLITION_ATR = 0 AND f.CONTRACT_CD = '")
				.append(contractCd)
				.append("' AND e.CID = '")
				.append(companyId)
				.append("' AND ((f.SALARY_USE_ATR = 1 AND ")
				.append(forPayroll)
				.append(" = 1)")
				.append(" OR (f.PERSONNEL_USE_ATR = 1 AND ")
				.append(forPersonnel)
				.append(" = 1)")
				.append(" OR (f.EMPLOYMENT_USE_ATR = 1 AND ")
				.append(forAttendance)
				.append(" = 1))")
				.append(" OR (")
				.append(forPayroll)
				.append(" = 0 AND ")
				.append(forPersonnel)
				.append(" = 0 AND ")
				.append(forAttendance)
				.append(" = 0))")
				.append(" end) as ITEM_NAME, ")
				.append(" (case WHEN b.PER_INFO_CATEGORY_ID IS NULL")
				.append(" THEN NULL ELSE ")
				.append(" (SELECT e.CATEGORY_NAME from PPEMT_PER_INFO_ITEM d")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG e on b.PER_INFO_CATEGORY_ID = e.PER_INFO_CTG_ID ")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_CM f ON e.CATEGORY_CD = f.CATEGORY_CD")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_ORDER g ON e.CID = g.CID AND e.PER_INFO_CTG_ID = g.PER_INFO_CTG_ID")
				.append(" INNER JOIN PPEMT_PER_INFO_ITEM_CM h ON e.CATEGORY_CD = h.CATEGORY_CD ")
				.append(" AND d.ITEM_CD = h.ITEM_CD AND h.CONTRACT_CD = f.CONTRACT_CD AND h.ITEM_PARENT_CD IS NULL")
				.append(" where d.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEF_ID ")
				.append(" AND b.PER_INFO_CATEGORY_ID = d.PER_INFO_CTG_ID")
				.append(" AND e.ABOLITION_ATR = 0 AND d.ABOLITION_ATR = 0 AND f.CONTRACT_CD = '")
				.append(contractCd)
				.append("' AND e.CID = '")
				.append(companyId)
				.append("' AND ((f.SALARY_USE_ATR = 1 AND ")
				.append(forPayroll)
				.append(" = 1)")
				.append(" OR (f.PERSONNEL_USE_ATR = 1 AND ")
				.append(forPersonnel)
				.append(" = 1)")
				.append(" OR (f.EMPLOYMENT_USE_ATR = 1 AND ")
				.append(forAttendance)
				.append(" = 1))")
				.append(" OR (")
				.append(forPayroll)
				.append(" = 0 AND ")
				.append(forPersonnel)
				.append(" = 0 AND ")
				.append(forAttendance)
				.append(" = 0))")
				.append(" end) as CATEGORY_NAME FROM PPEMT_MAINTENANCE_LAYOUT a")
				.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS b ON a.LAYOUT_ID = b.LAYOUT_ID ")
				.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS_DF c ON a.LAYOUT_ID = c.LAYOUT_ID ")
				.append(" WHERE a.CID = '")
				.append(companyId)
				.append("' ORDER BY a.LAYOUT_CD ASC")).toString();
		
		
		List<?> data = this.getEntityManager()
				.createNativeQuery(GET_CPS008).getResultList();
		
		return  data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
	}
	
	private static MaintenanceLayoutData toDomainWorkMonthlySet(Object[] object) {
	
		String layoutCd = (String) object[0];
		String layoutName =(String) object[1];
		String categoryName = (String) object[2];
		String itemName = (String) object[3];
		
		MaintenanceLayoutData maintenanceLayoutData = MaintenanceLayoutData.createFromJavaType(
				layoutCd, layoutName,categoryName,itemName);
		return maintenanceLayoutData;
			
		
	}
}

