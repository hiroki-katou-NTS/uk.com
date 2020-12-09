package nts.uk.file.com.infra.maintenance;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.maintenance.MaintenanceLayoutData;
import nts.uk.file.com.app.maintenance.MaintenanceLayoutExportRepository;
@Stateless
public class JpaMaintenanceExportRepository extends JpaRepository implements MaintenanceLayoutExportRepository{
	
	@Override                                                                                       
	public List<MaintenanceLayoutData> getAllMaintenanceLayout(String companyId, String contractCd) {
		String GET_CPS008 = (new StringBuffer()
				.append("SELECT g.LAYOUT_CD, g.LAYOUT_NAME, d.CATEGORY_NAME, c.ITEM_NAME, c.ITEM_NAME, e.ITEM_PARENT_CD, e.DATA_TYPE, e.ITEM_CD, a.LAYOUT_ITEM_TYPE FROM  PPEMT_LAYOUT_MAINTENANCE g")
				.append(" LEFT JOIN  PPEMT_LAYOUT_ITEM_CLS a ON g.LAYOUT_ID = a.LAYOUT_ID")
				.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS_DF b ON b.LAYOUT_ID = g.LAYOUT_ID and b.LAYOUT_DISPORDER = a.DISPORDER ")
				.append(" LEFT JOIN PPEMT_ITEM c ON c.PER_INFO_ITEM_DEFINITION_ID = b.PER_INFO_ITEM_DEF_ID ")
				.append(" AND c.PER_INFO_CTG_ID = a.PER_INFO_CATEGORY_ID AND c.ABOLITION_ATR = 0 ")
				.append(" LEFT JOIN PPEMT_CTG d ON d.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID AND d.ABOLITION_ATR = c.ABOLITION_ATR AND d.CID = g.CID")
				.append(" LEFT JOIN PPEMT_ITEM_COMMON e ON e.CATEGORY_CD = d.CATEGORY_CD AND e.ITEM_CD = c.ITEM_CD AND e.CONTRACT_CD = '")
				.append(contractCd)
				.append("' LEFT JOIN PPEMT_ITEM_SORT f ON f.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEFINITION_ID ")
				.append(" AND f.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID")
				.append(" WHERE g.CID = '")
				.append(companyId)
				.append("' AND ((b.LAYOUT_DISPORDER IS NOT NULL AND d.PER_INFO_CTG_ID IS NOT NULL ")
				.append(" AND c.PER_INFO_ITEM_DEFINITION_ID IS NOT NULL)  OR (b.LAYOUT_DISPORDER IS NULL AND a.LAYOUT_ITEM_TYPE = 2))")
				.append(" ORDER BY g.LAYOUT_CD ASC, a.DISPORDER ASC, b.DISPORDER ASC"))
				.toString();
		
		
		List<?> data = this.getEntityManager()
				.createNativeQuery(GET_CPS008).getResultList();
		
		return  data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
	}
	
	private static MaintenanceLayoutData toDomainWorkMonthlySet(Object[] object) {
	
		String layoutCd = (String) object[0];
		String layoutName =(String) object[1];
		String categoryName = (String) object[2];
		String itemName = (String) object[3];
		String itemNameC = (String) object[4];
		String itemParentCD= (String) object[5];
		int dataType;
		if(((BigDecimal)  object[6]) ==null){
			dataType =0;
		}else{
			dataType =((BigDecimal)  object[6]).intValue();
		}
		String itemCD = (String) object[7];
		int layoutItemType;
		if(((BigDecimal)  object[8]) ==null){
			layoutItemType =0;
		}else{
			layoutItemType =((BigDecimal)  object[8]).intValue();
		}
		
		MaintenanceLayoutData maintenanceLayoutData = new MaintenanceLayoutData(layoutCd, layoutName,categoryName,itemName, itemNameC,itemParentCD, dataType,itemCD,layoutItemType);
		return maintenanceLayoutData;
			
		
	}
}

