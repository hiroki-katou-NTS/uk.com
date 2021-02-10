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
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.file.com.app.personrole.PersonRoleColumn;
import nts.uk.file.com.app.personrole.PersonRoleRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class PersonRoleImpl implements PersonRoleRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String VALUE_TRUE = "可";
	private static final String VALUE_FALSE = "不可";
	private static Integer allowOther = null;
	private static Integer allowPer = null;
	
	private static final String GET_EXPORT_EXCEL = " 	SELECT "
			+ " 		CASE WHEN TH.ROW_NUMBER1 = 1 THEN TH.ROLE_CD ELSE NULL END ROLE_CD,"
			+ " 		CASE WHEN TH.ROW_NUMBER2 = 1 THEN TH.ROLE_NAME ELSE NULL END ROLE_NAME,"
			+ " 		CASE WHEN TH.ROW_NUMBER3 = 1 THEN TH.CATEGORY_NAME ELSE NULL END CATEGORY_NAME,"
			+ " 		CASE WHEN TH.ROW_NUMBER4 = 1 THEN TH.CATEGORY_TYPE ELSE NULL END CATEGORY_TYPE,"
			+ " 		CASE WHEN TH.ROW_NUMBER5 = 1 THEN TH.ALLOW_OTHER_REF_ATR ELSE NULL END ALLOW_OTHER_REF_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER6 = 1 THEN TH.ALLOW_PER_REF_ATR ELSE NULL END ALLOW_PER_REF_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER7 = 1 THEN TH.OTHER_ALLOW_ADD_MULTI_ATR ELSE NULL END OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER8 = 1 THEN TH.OTHER_ALLOW_DEL_MULTI_ATR ELSE NULL END OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER9 = 1 THEN TH.SELF_ALLOW_ADD_MULTI_ATR ELSE NULL END SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER10 = 1 THEN TH.SELF_ALLOW_DEL_MULTI_ATR ELSE NULL END SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER11 = 1 THEN TH.OTHER_ALLOW_ADD_HIS_ATR ELSE NULL END OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER12 = 1 THEN TH.OTHER_ALLOW_DEL_HIS_ATR ELSE NULL END OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER13 = 1 THEN TH.OTHER_FUTURE_HIS_AUTH_TYPE ELSE NULL END OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 		CASE WHEN TH.ROW_NUMBER14 = 1 THEN TH.OTHER_PAST_HIS_AUTH_TYPE ELSE NULL END OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 		CASE WHEN TH.ROW_NUMBER15 = 1 THEN TH.SELF_ALLOW_ADD_HIS_ATR ELSE NULL END SELF_ALLOW_ADD_HIS_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER16 = 1 THEN TH.SELF_ALLOW_DEL_HIS_ATR ELSE NULL END SELF_ALLOW_DEL_HIS_ATR,"
			+ " 		CASE WHEN TH.ROW_NUMBER17 = 1 THEN TH.SELF_FUTURE_HIS_AUTH_TYPE ELSE NULL END SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 		CASE WHEN TH.ROW_NUMBER18 = 1 THEN TH.SELF_PAST_HIS_AUTH_TYPE ELSE NULL END SELF_PAST_HIS_AUTH_TYPE,"
			+ " 		TH.ITEM_NAME,"
			+ " 		 TH.OTHER_PERSON_AUTH_TYPE,"
			+ " 		 TH.SELF_AUTH_TYPE,"
			+ " 		 TH.IsItemConfig"
			+ " "
			+ " 	 FROM"
			+ " 	("
			+ " 	SELECT "
			+ " 			TBL.ROLE_CD,"
			+ " 			TBL.ROLE_NAME,"
			+ " 			TBL.DISPORDER,"
			+ " 			TBL.CATEGORY_NAME,"
			+ " 			TBL.CATEGORY_TYPE,"
			+ " 			TBL.ALLOW_OTHER_REF_ATR,"
			+ " 			TBL.ALLOW_PER_REF_ATR,"
			+ " 			TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 			TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 			TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 			TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 			TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 			TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 			TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 			TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 			TBL.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 			TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 			TBL.SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 			TBL.SELF_PAST_HIS_AUTH_TYPE,"
			+ " 			TBL.ITEM_NAME,"
			+ " 		 TBL.OTHER_PERSON_AUTH_TYPE,"
			+ " 		 TBL.SELF_AUTH_TYPE,"
			+ " 		 TBL.IsItemConfig,"
			+ " 			TBL.ASSIGN_ATR,"
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_PAST_HIS_AUTH_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER18,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.SELF_FUTURE_HIS_AUTH_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER17,"
			+ " "
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER16,"
			+ " "
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER15,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER14,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER13,"
			+ " 			"
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER12,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER11,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER10,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER9,"
			+ " "
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER8,"
			+ " "
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER7,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.ALLOW_PER_REF_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER6,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.ALLOW_OTHER_REF_ATR,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER5,"
			+ " 			"
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.CATEGORY_TYPE"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.DISPORDER,"
			+ " 				TBL.CATEGORY_TYPE,"
			+ " 				TBL.orEnd"
			+ " 			) AS ROW_NUMBER4,"
			+ " 			"
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.CATEGORY_NAME,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER3,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME"
			+ " 			ORDER BY"
			+ " 				TBL.ROLE_CD,"
			+ " 				TBL.ROLE_NAME,"
			+ " 				TBL.DISPORDER,"
			+ "					TBL.orEnd"
			+ " 			) AS ROW_NUMBER2,"
			+ " "
			+ " 			ROW_NUMBER () OVER ("
			+ " 				PARTITION BY TBL.ROLE_CD"
			+ " 				ORDER BY"
			+ " 					TBL.ROLE_CD,"
			+ " 					TBL.DISPORDER,"
			+ "						TBL.orEnd"
			+ " 			) AS ROW_NUMBER1"
			+ " 			"
			+ " 	 FROM"
			+ " 	("
			+ " 	SELECT "
			+ " 	x.ROLE_CD,"
			+ " 	x.ROLE_NAME,"
			+ " 	c.CATEGORY_NAME,"
			+ " 	cm.CATEGORY_TYPE,"
			+ " 	xx.ALLOW_OTHER_REF_ATR,"
			+ " 	xx.ALLOW_PER_REF_ATR, "
			+ " 	xx.OTHER_ALLOW_ADD_MULTI_ATR,"
			+ " 	xx.OTHER_ALLOW_DEL_MULTI_ATR,"
			+ " 	xx.SELF_ALLOW_ADD_MULTI_ATR,"
			+ " 	xx.SELF_ALLOW_DEL_MULTI_ATR,"
			+ " 	xx.OTHER_ALLOW_ADD_HIS_ATR,"
			+ " 	xx.OTHER_ALLOW_DEL_HIS_ATR,"
			+ " 	xx.OTHER_FUTURE_HIS_AUTH_TYPE,"
			+ " 	xx.OTHER_PAST_HIS_AUTH_TYPE,"
			+ " 	xx.SELF_ALLOW_ADD_HIS_ATR,"
			+ " 	xx.SELF_ALLOW_DEL_HIS_ATR,"
			+ " 	xx.SELF_FUTURE_HIS_AUTH_TYPE,"
			+ " 	xx.SELF_PAST_HIS_AUTH_TYPE,"
			+ " 		item.ITEM_NAME,"
			+ " 		ctgau.OTHER_PERSON_AUTH_TYPE,"
			+ " 		ctgau.SELF_AUTH_TYPE,"
			+ " 		(select count(*) from PPEMT_ITEM ii where ii.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID and  ii.ABOLITION_ATR =0) as count_i,"
			+ " 		(select count(*) from PPEMT_ROLE_ITEM_AUTH ia where ia.PER_INFO_CTG_ID=c.PER_INFO_CTG_ID and ia.ROLE_ID=xx.ROLE_ID) as count_ia,"
			+ " 		CASE WHEN ctgau.PER_INFO_ITEM_DEF_ID IS NOT NULL  THEN 'True' ELSE 'False' END AS IsItemConfig,"
			+ " 		co.DISPORDER,"
			+ "			io.DISPORDER as orEnd,"
			+ "			x.ASSIGN_ATR "
			+ " 		FROM PPEMT_CTG c "
			+ " 		INNER JOIN PPEMT_CTG_COMMON cm"
			+ " 		ON c.CATEGORY_CD = cm.CATEGORY_CD AND c.CID = ?1 AND c.ABOLITION_ATR = 0"
			+ " 		AND cm.CONTRACT_CD = ?2 "
			+ " 		INNER JOIN PPEMT_CTG_SORT co"
			+ " 		ON c.PER_INFO_CTG_ID = co.PER_INFO_CTG_ID"
			+ " 		Cross JOIN (SELECT DISTINCT pp.ROLE_ID,r.ROLE_CD, r.ROLE_NAME,r.ASSIGN_ATR FROM PPEMT_ROLE_CTG_AUTH pp JOIN PPEMT_CTG c on pp.PER_INFO_CTG_ID = c.PER_INFO_CTG_ID JOIN SACMT_ROLE r on pp.ROLE_ID = r.ROLE_ID  WHERE c.CID = ?3 AND r.ROLE_TYPE = ?4) x"
			+ " 		LEFT JOIN PPEMT_ROLE_CTG_AUTH xx On c.PER_INFO_CTG_ID = xx.PER_INFO_CTG_ID AND xx.ROLE_ID = x.ROLE_ID"
			+ " 		"
			+ " 		INNER JOIN PPEMT_ITEM item ON c.PER_INFO_CTG_ID = item.PER_INFO_CTG_ID AND item.ABOLITION_ATR = 0"
			+ "			JOIN PPEMT_ITEM_SORT io ON io.PER_INFO_ITEM_DEFINITION_ID = item.PER_INFO_ITEM_DEFINITION_ID "
			+ " 		INNER JOIN PPEMT_ITEM_COMMON itemCM ON item.ITEM_CD = itemCM.ITEM_CD AND itemCM.CONTRACT_CD = ?5 AND c.CATEGORY_CD = itemCM.CATEGORY_CD AND itemCM.ITEM_PARENT_CD IS NULL"
			+ " 		LEFT JOIN PPEMT_ROLE_ITEM_AUTH ctgau ON item.PER_INFO_CTG_ID = ctgau.PER_INFO_CTG_ID AND x.ROLE_ID = ctgau.ROLE_ID AND ctgau.PER_INFO_ITEM_DEF_ID = item.PER_INFO_ITEM_DEFINITION_ID"
			+ " 		WHERE "
			+ " 		 ((cm.SALARY_USE_ATR = 1 AND ?6 = 1) OR (cm.PERSONNEL_USE_ATR = 1 AND ?7 = 1) OR (cm.EMPLOYMENT_USE_ATR = 1 AND ?8 = 1)) OR (?9 =  0 AND  ?10 = 0 AND ?11 = 0) "
			+ " 			"
			+ " 	) TBL WHERE TBL.count_i <= TBL.count_ia "
			+ " 	) TH ORDER BY TH.ASSIGN_ATR,TH.ROLE_CD,TH.DISPORDER";
			
			
	
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
			if(toMasterList(objects) != null)
			datas.add(toMasterList(objects));
		}
		return datas;
	}
	
	private MasterData toMasterList(Object[] object) {
		Map<String, MasterCellData> data = new HashMap<>();
		
		if(object[21].equals("False")) {
			return null;
		}
		if(object[2] != null) {
			allowOther = ((BigDecimal) object[4]).intValue();
			allowPer = ((BigDecimal) object[5]).intValue();
		}
		// A7_1
		data.put(PersonRoleColumn.CAS001_77, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_77)
                .value((String) object[0])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_2
		data.put(PersonRoleColumn.CAS001_78, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_78)
                .value((String) object[1])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_3
		data.put(PersonRoleColumn.CAS001_79, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_79)
                .value((String) object[2])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_4
		data.put(PersonRoleColumn.CAS001_81, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_81)
                .value(object[3] != null ? getTypeName(((BigDecimal) object[3]).intValue()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_5
		data.put(PersonRoleColumn.CAS001_82, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_82)
                .value(object[4] != null ? ((BigDecimal) object[4]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_6
		data.put(PersonRoleColumn.CAS001_89, MasterCellData.builder()
	                .columnId(PersonRoleColumn.CAS001_89)
	                .value(object[5] != null ? ((BigDecimal) object[5]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE : "")
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	                .build());
		//A7_7
		Integer cateType = object[3] != null ? ((BigDecimal) object[3]).intValue() : null;
		data.put(PersonRoleColumn.CAS001_83, MasterCellData.builder()
	                .columnId(PersonRoleColumn.CAS001_83)
	                .value(cateType == null || cateType != 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ? ""
	        				: object[6] == null ? "" : ((BigDecimal) object[6]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	                .build());
		//A7_8
		data.put(PersonRoleColumn.CAS001_84, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_84)
                .value(cateType == null || cateType != 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ?  ""
        				: object[7] == null ? "" : ((BigDecimal) object[7]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_9
		data.put(PersonRoleColumn.CAS001_85, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_85)
                .value(cateType == null || cateType != 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ?  ""
        				: object[8] == null ? "" : ((BigDecimal) object[8]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_10
		data.put(PersonRoleColumn.CAS001_90, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_90)
                .value(cateType == null || cateType != 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ? ""
        				: object[9] == null ? "" : ((BigDecimal) object[9]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_11
		data.put(PersonRoleColumn.CAS001_91, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_91)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ? ""
        				: object[10] == null ? "" : ((BigDecimal) object[10]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_12
		data.put(PersonRoleColumn.CAS001_86, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_86)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ? ""
        				: object[11] == null ? "" : ((BigDecimal) object[11]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_13
		data.put(PersonRoleColumn.CAS001_87, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_87)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ? ""
        				: object[12] == null ? "" : checkValue3(((BigDecimal) object[12]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_14
		data.put(PersonRoleColumn.CAS001_88, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_88)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[4] != null && ((BigDecimal) object[4]).intValue() == 0) ? ""
        				: object[13] == null ? "" : checkValue3(((BigDecimal) object[13]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_15
		data.put(PersonRoleColumn.CAS001_92, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_92)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ?  ""
        				: object[14] == null ? "" : ((BigDecimal) object[14]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_16
		data.put(PersonRoleColumn.CAS001_93, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_93)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ? ""
        				: object[15] == null ? "" : ((BigDecimal) object[15]).intValue() == 1 ? VALUE_TRUE : VALUE_FALSE)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_17
		data.put(PersonRoleColumn.CAS001_94, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_94)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ? ""
        				: object[16] == null ? "" : checkValue3(((BigDecimal) object[16]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_18
		data.put(PersonRoleColumn.CAS001_95, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_95)
                .value(cateType == null || cateType == 1 || cateType == 2 || (object[5] != null && ((BigDecimal) object[5]).intValue() == 0) ? ""
        				: object[17] == null ? "" : checkValue3(((BigDecimal) object[17]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_19
		data.put(PersonRoleColumn.CAS001_97, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_97)
                .value(object[18] != null ? (String) object[18] : null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_20
		data.put(PersonRoleColumn.CAS001_98, MasterCellData.builder()
                .columnId(PersonRoleColumn.CAS001_98)
                .value(object[19] != null ? allowOther != null ? allowOther == 1 ? 
                		getPersonInfoItemAuth(((BigDecimal) object[19]).intValue()) : ""  : "" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//A7_21
		data.put(PersonRoleColumn.CAS001_99, MasterCellData.builder()
		                .columnId(PersonRoleColumn.CAS001_99)
		                .value(object[20] != null ? allowPer != null ? allowPer == 1 ? 
		                		getPersonInfoItemAuth(((BigDecimal) object[20]).intValue()) : "" : "" : "")
		                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
		                .build());
		
		return MasterData.builder().rowData(data).build();
	}
	
	
	private String checkValue3(Integer authType) {
		String value = null;
		if (authType == 1) {
			value = I18NText.getText("CAS001_49");
		} else if (authType == 2) {
			value = I18NText.getText("CAS001_50");
		} else {
			value = I18NText.getText("CAS001_51");
		}
		return value;
	}

	private String getPersonInfoItemAuth(int type) {
		String nameType = null;
		PersonInfoAuthType personInfoAuthType = EnumAdaptor.valueOf(type, PersonInfoAuthType.class);
		switch (personInfoAuthType) {
			case HIDE:
				nameType = I18NText.getText("Enum_PersonInfoAuthTypes_HIDE");
				break;
			case REFERENCE:
				nameType = I18NText.getText("Enum_PersonInfoAuthTypes_REFERENCE");
				break;
			case UPDATE:
				/*nameType = I18NText.getText("Enum_PersonInfoAuthTypes_UPDATE");*/
				nameType = "更新可能";
				break;
			default:
				break;
		}
		return nameType;
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
