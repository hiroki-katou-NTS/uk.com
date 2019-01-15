package nts.uk.file.com.app.newlayout;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaNewLayoutExportRepository extends JpaRepository implements NewLayoutExportRepository{

	@Override
	public List<NewLayoutExportData> getAllMaintenanceLayout(String companyId, String contractCd, int forAttendance,
			int forPayroll, int forPersonnel) {
		String GET_CPS007 = (new StringBuffer()
				.append("SELECT a.CATEGORY_CD, a.CATEGORY_NAME,a.PER_INFO_CTG_ID, d.ITEM_CD, d.ITEM_NAME, c.DISPORDER FROM PPEMT_PER_INFO_CTG a")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_CM b ON a.CATEGORY_CD = b.CATEGORY_CD")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG_ORDER c ON a.CID = c.CID AND a.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID")
				.append(" INNER JOIN PPEMT_PER_INFO_ITEM d ON d.PER_INFO_CTG_ID = a.PER_INFO_CTG_ID AND d.ABOLITION_ATR = a.ABOLITION_ATR")
				.append(" INNER JOIN PPEMT_PER_INFO_CTG e ON e.PER_INFO_CTG_ID = d.PER_INFO_CTG_ID")
				.append(" INNER JOIN PPEMT_PER_INFO_ITEM_CM f ON f.CATEGORY_CD = e.CATEGORY_CD AND f.ITEM_CD = d.ITEM_CD")
				.append(" INNER JOIN PPEMT_PER_INFO_ITEM_ORDER g ON g.PER_INFO_ITEM_DEFINITION_ID = d.PER_INFO_ITEM_DEFINITION_ID")
				.append(" AND g.PER_INFO_CTG_ID = d.PER_INFO_CTG_ID WHERE b.CONTRACT_CD = '")
				.append(contractCd)
				.append("' AND a.CID = '")
				.append(companyId)
				.append("' AND a.ABOLITION_ATR = 0 ")
				.append(" AND b.CATEGORY_PARENT_CD IS NULL")
				.append(" AND f.ITEM_PARENT_CD IS NULL")
				.append(" AND ((b.SALARY_USE_ATR = 1 AND ")
				.append(forPayroll)
				.append(" = 1) OR (b.PERSONNEL_USE_ATR = 1 AND ")
				.append(forPersonnel)
				.append(" = 1) OR (b.EMPLOYMENT_USE_ATR = 1 AND ")
				.append(forAttendance)
				.append(" = 1))")
				.append(" OR (")
				.append(forPayroll)
				.append(" = 0 AND ")
				.append(forPersonnel)
				.append(" = 0 AND ")
				.append(forAttendance)
				.append(" = 0)  ORDER BY c.DISPORDER ASC")
				).toString();
		
		List<?> data = this.getEntityManager()
				.createNativeQuery(GET_CPS007).getResultList();
		return  data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
	}
	private static NewLayoutExportData toDomainWorkMonthlySet(Object[] object) {
		
		String categoryCd= (String) object[0];
		String categoryName= (String) object[1];
		String perInfoCtgId= (String) object[2];
		String itemCd= (String) object[3];
		String itemName= (String) object[4];
		int  dispoder = ((BigDecimal)  object[5]).intValue();
		
		NewLayoutExportData newLayoutExportData = NewLayoutExportData.createFromJavaType(
				categoryCd, categoryName,perInfoCtgId,itemCd, itemName, dispoder);
		return newLayoutExportData;
		
	}

}
