package nts.uk.file.com.infra.rolesetmenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.uk.file.com.app.rolesetmenu.RoleSetMenuColumn;
import nts.uk.file.com.app.rolesetmenu.RoleSetMenuRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaRoleSetMenuImpl implements RoleSetMenuRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static String QUERY_EXPORT = "SELECT "
			+ " aa.ROLE_SET_CD, "
			+ " aa.ROLE_SET_NAME,"
			+ " (SELECT ROLE_SET_CD from SACMT_ROLESET_DEFAULT where CID = ?cid) AS DEFAULT_MENU,"
			+ " bb.ROLE_CD,bb.ROLE_NAME,ee.ROLE_CD,"
			+ " ee.ROLE_NAME,aa.APPROVAL_AUTHORITY,"
			+ " cc.WEB_MENU_CD,dd.WEB_MENU_NAME "
			+ " FROM SACMT_ROLESET aa "
			+ " Left JOIN SACMT_ROLE bb ON   aa.EMPLOYMENT_ROLE = bb.ROLE_ID "
			+ " Left JOIN SACMT_ROLE ee ON   aa.PERSON_INF_ROLE = ee.ROLE_ID "
			+ " JOIN SPTMT_ROLE_SET_WEB_MENU cc "
			+ " ON aa.ROLE_SET_CD = cc.ROLE_SET_CD "
			+ " LEFT JOIN SPTMT_WEB_MENU dd "
			+ " ON cc.WEB_MENU_CD = dd.WEB_MENU_CD AND cc.CID = dd.CID "
			+ " where aa.CID = ?cid AND cc.CID = ?cid "
			+ " ORDER BY ROLE_SET_CD, cc.WEB_MENU_CD";

	@Override
	public List<MasterData> exportDataExcel() {
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(QUERY_EXPORT.toString()).
				setParameter("cid", cid);

		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		HashMap<String , Object[]> dataHashMap = new HashMap<>();
		String V_CAS011_41 = "";
		for (Object[] objects : data) {
			if(dataHashMap.containsKey((String)objects[0])) {
				if(objects[9] != null){
					V_CAS011_41 = V_CAS011_41 +  "," + objects[8] +(String)objects[9] ;
				}else{
					V_CAS011_41 = V_CAS011_41 +"," + objects[8] + "マスタ未登録";
				}
				objects[9] = V_CAS011_41;
				dataHashMap.put((String)objects[0], objects);
			} else {
				V_CAS011_41 = "";
				if(objects[9] != null){
					V_CAS011_41 = V_CAS011_41 + objects[8] + (String) objects[9];
				}else{
					V_CAS011_41 = V_CAS011_41 + objects[8] + "マスタ未登録";
				}
				objects[9] = V_CAS011_41;

				dataHashMap.put((String)objects[0], objects);
			}
		}
		Object[] keys = dataHashMap.keySet().toArray();
		Arrays.sort(keys);
		return toData(dataHashMap,keys);
	}
	
	
	private List<MasterData> toData(HashMap<String , Object[]> dataHashMap,Object[] keys) {
		List<MasterData> datas = new ArrayList<>();
		for(Object key : keys) {
			datas.add(toData((Object[]) dataHashMap.get(key)));
		}
		return datas;
	}
	
	private MasterData toData(Object[] datarow) {
		Map<String,MasterCellData> data = new HashMap<>();
        data.put(RoleSetMenuColumn.CAS011_35, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_35)
            .value(datarow[0])
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_36, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_36)
            .value(datarow[1])
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_37, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_37)
            .value(datarow[2].equals(datarow[0]) ? "○" :"")
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_38, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_38)
            .value((String)datarow[3] != null ? (String)datarow[3]+(String)datarow[4] : "なし")
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_39, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_39)
            .value((String)datarow[5] != null ? (String)datarow[5]+(String)datarow[6] : "なし")
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_40, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_40)
            .value(((BigDecimal)datarow[7]).intValue() == 0 ? "なし" : "あり")
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(RoleSetMenuColumn.CAS011_41, MasterCellData.builder()
            .columnId(RoleSetMenuColumn.CAS011_41)
            .value(datarow[9])
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
    return MasterData.builder().rowData(data).build();
	}
	
	
}
