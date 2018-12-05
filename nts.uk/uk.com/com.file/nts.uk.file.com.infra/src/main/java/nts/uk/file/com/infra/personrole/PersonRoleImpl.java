package nts.uk.file.com.infra.personrole;

import java.math.BigDecimal;
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
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.file.com.app.personrole.PersonRoleColumn;
import nts.uk.file.com.app.personrole.PersonRoleRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class PersonRoleImpl implements PersonRoleRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String GET_EXPORT_EXCEL = "SELECT x.ROLE_CD,x.ROLE_NAME,c.CATEGORY_NAME, "
			+ " CASE WHEN xx.PER_INFO_CTG_ID IS NOT NULL  THEN 'True' ELSE 'False' END AS IsConfig,"
			+ " ctgau.OTHER_PERSON_AUTH_TYPE, xx.ALLOW_PER_REF_ATR, xx.ALLOW_OTHER_REF_ATR,"
			+ " xx.SELF_PAST_HIS_AUTH_TYPE,xx.SELF_FUTURE_HIS_AUTH_TYPE,xx.SELF_ALLOW_ADD_HIS_ATR,"
			+ " xx.SELF_ALLOW_DEL_HIS_ATR,xx.OTHER_PAST_HIS_AUTH_TYPE,xx.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " xx.OTHER_ALLOW_ADD_HIS_ATR,xx.OTHER_ALLOW_DEL_HIS_ATR,xx.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " xx.SELF_ALLOW_DEL_MULTI_ATR,xx.OTHER_ALLOW_ADD_MULTI_ATR,xx.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " CASE WHEN ctgau.PER_INFO_ITEM_DEF_ID IS NOT NULL  THEN 'True' ELSE 'False' END AS IsItemConfig,"
			+ " cm.CATEGORY_TYPE,"
			+ " item.ITEM_NAME,"
			+ " ctgau.SELF_AUTH_TYPE,"
			+ " ctgau.OTHER_PERSON_AUTH_TYPE,"
			+ " (select count(*) from PPEMT_PER_INFO_ITEM ii where ii.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID and  ii.ABOLITION_ATR =0) as count_i,"
			+ "	(select count(*) from PPEMT_PERSON_ITEM_AUTH ia where ia.PER_INFO_CTG_ID=c.PER_INFO_CTG_ID and ia.ROLE_ID=xx.ROLE_ID) as count_ia"
			+ " FROM PPEMT_PER_INFO_CTG c "
			+ " INNER JOIN PPEMT_PER_INFO_CTG_CM cm"
			+ " ON c.CATEGORY_CD = cm.CATEGORY_CD AND c.CID = ?1 AND c.ABOLITION_ATR = 0"
			+ " AND cm.CONTRACT_CD = ?2 "
			+ " INNER JOIN PPEMT_PER_INFO_CTG_ORDER co"
			+ " ON c.PER_INFO_CTG_ID = co.PER_INFO_CTG_ID"
			+ " Cross JOIN (SELECT DISTINCT pp.ROLE_ID,r.ROLE_CD, r.ROLE_NAME FROM PPEMT_PERSON_CTG_AUTH pp JOIN PPEMT_PER_INFO_CTG c on pp.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID JOIN SACMT_ROLE r on pp.ROLE_ID = r.ROLE_ID  WHERE c.CID = ?3 AND r.ROLE_TYPE = ?4) x"
			+ " LEFT JOIN PPEMT_PERSON_CTG_AUTH xx On c.PER_INFO_CTG_ID = xx.PER_INFO_CTG_ID AND xx.ROLE_ID = x.ROLE_ID"
			+ " INNER JOIN PPEMT_PER_INFO_ITEM item ON c.PER_INFO_CTG_ID = item.PER_INFO_CTG_ID AND item.ABOLITION_ATR = 0"
			+ " INNER JOIN PPEMT_PER_INFO_ITEM_CM itemCM ON item.ITEM_CD = itemCM.ITEM_CD AND itemCM.CONTRACT_CD = ?5 AND c.CATEGORY_CD = itemCM.CATEGORY_CD AND itemCM.ITEM_PARENT_CD IS NULL"
			+ " LEFT JOIN PPEMT_PERSON_ITEM_AUTH ctgau ON item.PER_INFO_CTG_ID = ctgau.PER_INFO_CTG_ID AND x.ROLE_ID = ctgau.ROLE_ID AND ctgau.PER_INFO_ITEM_DEF_ID = item.PER_INFO_ITEM_DEFINITION_ID"
			+ " WHERE"
			+ " ((cm.SALARY_USE_ATR = 1 AND ?6 = 1) OR (cm.PERSONNEL_USE_ATR = 1 AND ?7 = 1) OR (cm.EMPLOYMENT_USE_ATR = 1 AND ?8 = 1)) OR (?9 =  0 AND  ?10 = 0 AND ?11 = 0) "
			+ " AND item.ABOLITION_ATR = 0 ORDER BY ROLE_CD,co.DISPORDER";
	
	
	@Override
	public List<MasterData> getDataExport(int salaryUseAtr, int personnelUseAtr, int employmentUseAtr) {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString())
				.setParameter(1, cid)
				.setParameter(2, contractCd)
				.setParameter(3, cid)
				.setParameter(4, 8)
				.setParameter(5, contractCd)
				.setParameter(6, salaryUseAtr)
				.setParameter(7, personnelUseAtr)
				.setParameter(8, employmentUseAtr)
				.setParameter(9, salaryUseAtr)
				.setParameter(10, personnelUseAtr)
				.setParameter(11, employmentUseAtr);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContent(objects), null, ""));
		}
		return datas;
	}
	
	
	private Map<String, Object> dataContent(Object[] object) {
		boolean isHigher = Integer.valueOf(object[24].toString()) > Integer.valueOf(object[25].toString());
		boolean isCateConfig = !isHigher ? Boolean.valueOf(object[3].toString()) : false;
		Map<String, Object> data = new HashMap<>();
		// A6_1
		data.put(PersonRoleColumn.CAS001_77, (String) object[0]);
		// A6_2
		data.put(PersonRoleColumn.CAS001_78, (String) object[1]);
		// A6_3
		data.put(PersonRoleColumn.CAS001_79, (String) object[2]);
		// A6_4
		data.put(PersonRoleColumn.CAS001_80, isCateConfig ? "●" : "");
		// A6_5
		data.put(PersonRoleColumn.CAS001_81, getTypeName(object[20] != null ? ((BigDecimal) object[20]).intValue() : null));
		// A6_6
		data.put(PersonRoleColumn.CAS001_82, object[6] != null ? ((BigDecimal) object[6]).intValue() == 1 ? "○" : "ー" : "");
		// A6_7
		Integer cateType = object[20] != null ? ((BigDecimal) object[20]).intValue() : null;
		data.put(PersonRoleColumn.CAS001_83,  cateType != 2 ? "ー"
				: object[15] == null ? "" : ((BigDecimal) object[15]).intValue() == 1 ? "○" : "ー");
		// A6_8
		data.put(PersonRoleColumn.CAS001_84, cateType != 2 ? "ー"
				: object[16] == null ? "" : ((BigDecimal) object[16]).intValue() == 1 ? "○" : "ー");
		// A6_9
		data.put(PersonRoleColumn.CAS001_85, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[9] == null ? "" : ((BigDecimal) object[9]).intValue() == 1 ? "○" : "ー");
		// A6_10
		data.put(PersonRoleColumn.CAS001_86, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[10] == null ? "" : ((BigDecimal) object[10]).intValue() == 1 ? "○" : "ー");
		// A6_11
		data.put(PersonRoleColumn.CAS001_87, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[12] == null ? "" : checkValue3(((BigDecimal) object[12]).intValue(), 1, 0));
		// A6_12
		data.put(PersonRoleColumn.CAS001_88, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[11] == null ? "" : checkValue3(((BigDecimal) object[11]).intValue(), 1, 0));
		// A6_13
		data.put(PersonRoleColumn.CAS001_89, object[5] != null ? ((BigDecimal) object[5]).intValue() == 1 ? "○" : "ー" : "");
		// A6_14
		data.put(PersonRoleColumn.CAS001_90, checkValue1(cateType) != null ? checkValue1(cateType)
				: object[15] == null ? "" : ((BigDecimal) object[15]).intValue() == 1 ? "○" : "ー");
		// A6_15
		data.put(PersonRoleColumn.CAS001_91, checkValue2(cateType) != null ? checkValue1(cateType)
				: object[16] == null ? "" : ((BigDecimal) object[16]).intValue() == 1 ? "○" : "ー");
		// A6_16
		data.put(PersonRoleColumn.CAS001_92, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[9] == null ? "" : ((BigDecimal) object[9]).intValue() == 1 ? "○" : "ー");
		// A6_17
		data.put(PersonRoleColumn.CAS001_93, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[10] == null ? "" : ((BigDecimal) object[10]).intValue() == 1 ? "○" : "ー");
		// A6_18
		data.put(PersonRoleColumn.CAS001_94, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[8] == null ? "" : checkValue3(((BigDecimal) object[8]).intValue(), 1, 0));
		// A6_19
		data.put(PersonRoleColumn.CAS001_95, checkValue2(cateType) != null ? checkValue2(cateType)
				: object[7] == null ? "" : checkValue3(((BigDecimal) object[7]).intValue(), 1, 0));
		// A6_20
		data.put(PersonRoleColumn.CAS001_96, object[19].equals("True") ? "●" : "");
		// A6_21
		data.put(PersonRoleColumn.CAS001_97, object[21] != null ? (String) object[21] : null);
		// A6_22
		data.put(PersonRoleColumn.CAS001_98, checkValue3(object[22] != null ? ((BigDecimal) object[22]).intValue() : null, object[6] != null ? ((BigDecimal) object[6]).intValue() : null, 0));
		// A6_23
		data.put(PersonRoleColumn.CAS001_99, checkValue3(object[23] != null ? ((BigDecimal) object[23]).intValue() : null, object[5] != null ? ((BigDecimal) object[5]).intValue() : null, 0));
		return data;
	}

	private String checkValue1(Integer categoryType) {
		String value = null;
		if (categoryType == null)
			value = "";
		CategoryType type = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		if (type.value != 2)
			value = "ー";
		return value;
	}

	private String checkValue2(Integer categoryType) {
		String value = null;
		if (categoryType == null)
			value = "";
		CategoryType type = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		if (type.value != 3 && type.value != 4) {
			value = "ー";
		}
		return value;
	}

	private String checkValue3(Integer authType, Integer check, Integer paramCheck) {
		String value = null;
		if (check == paramCheck || authType == null) {
			value = "ー";
		} else if (authType == 1) {
			value = I18NText.getText("CAS001_49");
		} else if (authType == 2) {
			value = I18NText.getText("CAS001_50");
		} else {
			value = I18NText.getText("CAS001_51");
		}
		return value;
	}

	private String getTypeName(int categoryType) {
		String nameType = null;
		CategoryType type = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		switch (type) {
		case SINGLEINFO:
			nameType = I18NText.getText("Enum_CategoryType_SINGLE_INFO");
			break;
		case MULTIINFO:
			nameType = I18NText.getText("Enum_CategoryType_MULTI_INFO");
			break;
		case CONTINUOUSHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_CONTINUOUS_HISTORY");
			break;
		case NODUPLICATEHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_NODUPLICATE_HISTORY");
			break;
		case DUPLICATEHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_DUPLICATE_HISTORY");
			break;
		case CONTINUOUS_HISTORY_FOR_ENDDATE:
			nameType = I18NText.getText("Enum_CategoryType_CONTINUOUS_HISTORY");
			break;
		default:
			break;
		}
		return nameType;
	}
	
	
}
