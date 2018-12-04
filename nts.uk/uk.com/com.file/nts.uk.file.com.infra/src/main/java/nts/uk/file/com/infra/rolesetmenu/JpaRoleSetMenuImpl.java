package nts.uk.file.com.infra.rolesetmenu;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaRoleSetMenuImpl implements RoleSetMenuRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static String QUERY_TAB_1 = "SELECT bb.ROLE_SET_CD,bb.ROLE_SET_NAME,bb.ROLE_NAME,cc.ROLE_NAME,cc.APPROVAL_AUTHORITY FROM ("
			+ " SELECT aa.ROLE_SET_CD, aa.ROLE_SET_NAME, aa.EMPLOYMENT_ROLE, aa.PERSON_INF_ROLE,bb.ROLE_NAME, bb.ROLE_ID "
			+ " FROM SACMT_ROLE_SET aa"
			+ " JOIN SACMT_ROLE bb ON aa.EMPLOYMENT_ROLE = bb.ROLE_ID"
			+ " where aa.CID = ?1 ) bb"
			+ " JOIN ("
			+ " SELECT aa.ROLE_SET_CD, aa.ROLE_SET_NAME, aa.EMPLOYMENT_ROLE, aa.PERSON_INF_ROLE,aa.APPROVAL_AUTHORITY,bb.ROLE_NAME, bb.ROLE_ID"
			+ " FROM SACMT_ROLE_SET aa JOIN SACMT_ROLE bb ON"
			+ " aa.PERSON_INF_ROLE = bb.ROLE_ID"
			+ " where aa.CID = ?2 ) cc"
			+ " ON bb.ROLE_SET_CD = cc.ROLE_SET_CD"
			+ " ORDER BY cc.ROLE_SET_CD";
	
	private static String QUERY_TAB_2 = "SELECT aa.ROLE_SET_CD,aa.ROLE_SET_NAME,bb.WEB_MENU_CD,cc.WEB_MENU_NAME "
			+ " FROM SACMT_ROLE_SET aa JOIN SPTMT_ROLE_SET_WEB_MENU bb "
			+ " ON aa.ROLE_SET_CD = bb.ROLE_SET_CD"
			+ " JOIN CCGST_WEB_MENU cc "
			+ " ON bb.WEB_MENU_CD = cc.WEB_MENU_CD"
			+ " where aa.CID = ?1 AND bb.CID = ?2 AND cc.CID = ?3"
			+ " ORDER BY aa.ROLE_SET_CD";
	
	@Override
	public List<MasterData> findTable1() {
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(QUERY_TAB_1.toString())
				.setParameter(1, AppContexts.user().companyId())
				.setParameter(2, AppContexts.user().companyId());
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContentTabOne(objects), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> dataContentTabOne(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data. put(RoleSetMenuColumn.CAS011_39, (String)object[0]);
		data. put(RoleSetMenuColumn.CAS011_40, (String)object[1]);
		data. put(RoleSetMenuColumn.CAS011_41, (String)object[2]);
		data. put(RoleSetMenuColumn.CAS011_42, (String)object[3]);
		data. put(RoleSetMenuColumn.CAS011_43, Math.round(((BigDecimal)object[4]).floatValue()) == 1 ? "○" : "ー");
		return data;
	}
	
	@Override
	public List<MasterData> findTable2() {
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(QUERY_TAB_2)
				.setParameter(1, AppContexts.user().companyId())
				.setParameter(2, AppContexts.user().companyId())
				.setParameter(3, AppContexts.user().companyId());
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContentTabTwo(objects), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> dataContentTabTwo(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data. put(RoleSetMenuColumn.CAS011_39, (String)object[0]);
		data. put(RoleSetMenuColumn.CAS011_40, (String)object[1]);
		data. put(RoleSetMenuColumn.CAS011_44, (String)object[2]);
		data. put(RoleSetMenuColumn.CAS011_45, (String)object[3]);
		return data;
	}
	
}
