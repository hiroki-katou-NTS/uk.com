package nts.uk.file.com.app.newlayout;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaNewLayoutExportRepository extends JpaRepository implements NewLayoutExportRepository{

	@Override
	public List<NewLayoutExportData> getAllMaintenanceLayout(String companyId, String contractCd) {
		String GET_CPS007 = (new StringBuffer()
				.append("SELECT a.DISPORDER, d.CATEGORY_NAME, c.ITEM_NAME,c.ITEM_NAME, e.ITEM_PARENT_CD, e.DATA_TYPE  FROM  PPEMT_NEW_LAYOUT g")
				.append(" LEFT JOIN  PPEMT_LAYOUT_ITEM_CLS a ON g.LAYOUT_ID = a.LAYOUT_ID")
				.append(" LEFT JOIN PPEMT_LAYOUT_ITEM_CLS_DF b ON b.LAYOUT_ID = g.LAYOUT_ID and b.LAYOUT_DISPORDER = a.DISPORDER ")
				.append(" LEFT JOIN PPEMT_PER_INFO_ITEM c ON c.PER_INFO_ITEM_DEFINITION_ID = b.PER_INFO_ITEM_DEF_ID and c.PER_INFO_CTG_ID = a.PER_INFO_CATEGORY_ID AND c.ABOLITION_ATR = 0 ")
				.append(" LEFT JOIN PPEMT_PER_INFO_CTG d ON d.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID AND d.ABOLITION_ATR = c.ABOLITION_ATR AND d.CID = g.CID")
				.append(" LEFT JOIN PPEMT_PER_INFO_ITEM_CM e ON e.CATEGORY_CD = d.CATEGORY_CD AND e.ITEM_CD = c.ITEM_CD AND e.CONTRACT_CD = '")
				.append(contractCd)
				.append("' LEFT JOIN PPEMT_PER_INFO_ITEM_ORDER f ON f.PER_INFO_ITEM_DEFINITION_ID = c.PER_INFO_ITEM_DEFINITION_ID ")
				.append(" AND f.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID ")
				.append(" WHERE g.CID = '")
				.append(companyId)
//				.append("' AND (e.ITEM_PARENT_CD IS NULL OR e.ITEM_PARENT_CD = '')")
				.append("' AND ((b.LAYOUT_DISPORDER IS NOT NULL AND d.PER_INFO_CTG_ID IS NOT NULL ")
				.append(" AND c.PER_INFO_ITEM_DEFINITION_ID IS NOT NULL)  OR (b.LAYOUT_DISPORDER IS NULL AND a.LAYOUT_ITEM_TYPE = 2))")
				.append(" ORDER BY a.DISPORDER ASC, b.DISPORDER asc")
				).toString();
		
		List<?> data = this.getEntityManager()
				.createNativeQuery(GET_CPS007).getResultList();
		return  data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
	}
	private static NewLayoutExportData toDomainWorkMonthlySet(Object[] object) {
		int dispoder = ((BigDecimal)  object[0]).intValue();
		String categoryName= (String) object[1];
		String itemName= (String) object[2];
		String itemNameC= (String) object[3];
		String itemParentCD = (String) object[4];
		int dataType;
		if(((BigDecimal)  object[5]) ==null){
			dataType =0;
		}else{
			dataType =((BigDecimal)  object[5]).intValue();
		}
		
		
		NewLayoutExportData newLayoutExportData = NewLayoutExportData.createFromJavaType(dispoder, categoryName,itemName, itemNameC , itemParentCD,dataType);
		return newLayoutExportData;
		
	}

}
