package nts.uk.file.com.infra.Indivigrant;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.file.com.app.Indivigrant.IndivigrantColumn;
import nts.uk.file.com.app.Indivigrant.IndivigrantRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class IndivigrantImpl implements IndivigrantRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String GET_EXPORT_EXCEL = 
			" SELECT CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.ROLE_TYPE ELSE NULL END ROLE_TYPE, "
			+ " CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.ROLE_CD ELSE NULL END ROLE_CD,"
			+ " CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.ROLE_NAME ELSE NULL END ROLE_NAME,"
			+ " TBL.LOGIN_ID, TBL.BUSINESS_NAME, TBL.STR_D, TBL.END_D, TBL.ROW_NUMBER FROM "
			+ " (SELECT role.ROLE_TYPE, role.ROLE_CD, role.ROLE_NAME, us.LOGIN_ID, per.BUSINESS_NAME, gr.STR_D, gr.END_D," 
			+ " ROW_NUMBER() OVER (PARTITION BY role.ROLE_TYPE, role.ROLE_CD "
			+ " ORDER BY  role.ROLE_TYPE ASC, role.ROLE_CD ASC, us.LOGIN_ID ASC) AS ROW_NUMBER "
			+ " FROM SACMT_ROLE_INDIVI_GRANT gr INNER JOIN SACMT_USER us "
			+ " ON gr.USER_ID = us.USER_ID "
			+ " INNER JOIN BPSMT_PERSON per "
			+ " ON per.PID = us.ASSO_PID"
			+ " INNER JOIN SACMT_ROLE role "
			+ " ON gr.ROLE_ID = role.ROLE_ID"
			+ " WHERE gr.CID = ?cid " 
			+ " AND (role.ROLE_TYPE = "+ RoleType.EMPLOYMENT.value +" OR role.ROLE_TYPE = "+ RoleType.PERSONAL_INFO.value +") AND role.ASSIGN_ATR = 0) TBL";

	@Override
	public List<MasterData> getDataExport() {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString()).setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContent(objects), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContent(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		// R7_1
		data.put(IndivigrantColumn.CAS013_44, object[0] != null ? getTypeName(((BigDecimal) object[0]).intValue()) : null);
		// R7_2
		data.put(IndivigrantColumn.CAS013_45, object[1] != null ? (String) object[1] : null);
		// R7_3
		data.put(IndivigrantColumn.CAS013_46, object[2] != null ? (String) object[2] : null);
		// R7_4
		data.put(IndivigrantColumn.CAS013_47, object[3] != null ? (String) object[3] : null);
		// A7_5
		data.put(IndivigrantColumn.CAS013_48, object[4] != null ? (String) object[4] : null);
		// A7_6
		data.put(IndivigrantColumn.CAS013_49,  object[5] != null ? GeneralDate.localDate(((Date) object[5]).toLocalDate()) : null);
		// A7_7
		data.put(IndivigrantColumn.CAS013_50,  object[6] != null ? GeneralDate.localDate(((Date) object[6]).toLocalDate()) : null);
		return data;
	}
	
	private String getTypeName(int roleType) {
		String nameType = null;
		RoleType type = EnumAdaptor.valueOf(roleType, RoleType.class);
		switch (type) {
		case SYSTEM_MANAGER:
			nameType = I18NText.getText("Enum_RoleType_systemManager");
			break;
		case COMPANY_MANAGER:
			nameType = I18NText.getText("Enum_RoleType_companyManager");
			break;
		case GROUP_COMAPNY_MANAGER:
			nameType = I18NText.getText("Enum_RoleType_groupCompanyManager");
			break;
		case EMPLOYMENT:
			nameType = I18NText.getText("Enum_RoleType_employment");
			break;
		case SALARY:
			nameType = I18NText.getText("Enum_RoleType_salary");
			break;
		case HUMAN_RESOURCE:
			nameType = I18NText.getText("Enum_RoleType_humanResource");
			break;
		case OFFICE_HELPER:
			nameType = I18NText.getText("Enum_RoleType_officeHelper");
			break;
		case MY_NUMBER:
			nameType = I18NText.getText("Enum_RoleType_myNumber");
			break;
		case PERSONAL_INFO:
			nameType = I18NText.getText("Enum_RoleType_personalInfo");
			break;
		default:
			break;
		}
		return nameType;
	}
	
}
