package nts.uk.file.com.infra.person.info.category;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.person.info.category.ChangePerInforDefinitionExRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

/**
 * The Class JpaChangePerInforDefinitionRepository.
 */
@Stateless
public class JpaChangePerInforDefinitionRepository extends JpaRepository implements ChangePerInforDefinitionExRepository {

	private static final String GET_PER_INFOR_DEFINITION;
	  static {
	      StringBuilder exportSQL = new StringBuilder();
	     exportSQL.append(" SELECT ");
	     exportSQL.append("                     CASE WHEN temp.ROW_NUMBER = 1 THEN temp.CATEGORY_NAME ELSE NULL END, ");
	     exportSQL.append("                     CASE WHEN temp.ROW_NUMBER = 1 THEN temp.ABOLITION_ATR ELSE NULL END, ");
	     exportSQL.append("                     CASE WHEN temp.ROW_NUMBER = 1 THEN temp.NAME_CTG_DEFAULT ELSE NULL END, ");
	     exportSQL.append("                     CASE WHEN temp.ROW_NUMBER = 1 THEN temp.CATEGORY_TYPE ELSE NULL END, ");
	     exportSQL.append("                     temp.ITEM_NAME,  ");
	     exportSQL.append("                     temp.NAME_ITEM_DEFAULT, ");
	     exportSQL.append("                     temp.REQUIRED_ATR, ");
	     exportSQL.append("                     temp.ABOLITION_ATR1");
	     exportSQL.append("             FROM ( ");
	     exportSQL.append("            SELECT ROW_NUMBER() OVER (PARTITION BY i.CATEGORY_NAME ORDER BY  o.DISPORDER,ITEM_DISPORDER) AS ROW_NUMBER, ");
	     exportSQL.append("               i.CATEGORY_NAME, ");
	     exportSQL.append("          i.ABOLITION_ATR, ");
	     exportSQL.append("          n.CATEGORY_NAME as NAME_CTG_DEFAULT, ");
	     exportSQL.append("          c.CATEGORY_TYPE, ");
	     exportSQL.append("        c.CONTRACT_CD,");
	     exportSQL.append("          item.ITEM_NAME, ");
	     exportSQL.append("               ni.ITEM_NAME as NAME_ITEM_DEFAULT, ");
	     exportSQL.append("        ni.ITEM_CD,");
	     exportSQL.append("        ni.CATEGORY_CD,");
	     exportSQL.append("          item.REQUIRED_ATR, ");
	     exportSQL.append("          item.ABOLITION_ATR as ABOLITION_ATR1, ");
	     exportSQL.append("       o.DISPORDER,");
	     exportSQL.append("       ITEM_DISPORDER");
	     exportSQL.append("            FROM (  ");
	     exportSQL.append("              SELECT CATEGORY_NAME , ");
	     exportSQL.append("                ABOLITION_ATR, ");
	     exportSQL.append("                cc.CATEGORY_CD, ");
	     exportSQL.append("                cc.PER_INFO_CTG_ID,");
	     exportSQL.append("                CID ");
	     exportSQL.append("              FROM PPEMT_CTG cc");
	     exportSQL.append("          INNER JOIN PPEMT_CTG_COMMON ccm");
	     exportSQL.append("          ON cc.CATEGORY_CD = ccm.CATEGORY_CD");
	     exportSQL.append("              WHERE CID = ?cid ");
	     exportSQL.append("       AND ((ccm.SALARY_USE_ATR = 1 AND ?salaryUseAtr = 1) OR  (ccm.PERSONNEL_USE_ATR = 1 AND ?personnelUseAtr = 1)");
	     exportSQL.append("       OR  (ccm.EMPLOYMENT_USE_ATR = 1 AND ?employmentUseAtr = 1))");
	     exportSQL.append("       OR (?salaryUseAtr =  0 AND  ?personnelUseAtr = 0 AND ?employmentUseAtr = 0 )");
	     exportSQL.append("          ) i ");
	     exportSQL.append("            INNER JOIN (SELECT CATEGORY_CD, ");
	     exportSQL.append("                       CATEGORY_TYPE, ");
	     exportSQL.append("               CATEGORY_PARENT_CD,");
	     exportSQL.append("               CONTRACT_CD");
	     exportSQL.append("                       FROM PPEMT_CTG_COMMON ");
	     exportSQL.append("                       WHERE CONTRACT_CD = ?contracCd");
	     exportSQL.append("                   ) c ");
	     exportSQL.append("                     ON i.CATEGORY_CD = c.CATEGORY_CD ");
	     exportSQL.append("            INNER JOIN PPEMT_CTG_SORT o ON i.CID = o.CID AND o.PER_INFO_CTG_ID = i.PER_INFO_CTG_ID ");
	     exportSQL.append("            INNER JOIN ( ");
	     exportSQL.append("               SELECT CATEGORY_NAME , i2.CATEGORY_CD , i2.CID ");
	     exportSQL.append("               FROM ");
	     exportSQL.append("                 (SELECT CATEGORY_NAME, CATEGORY_CD, CID ");
	     exportSQL.append("                 FROM PPEMT_CTG ");
	     exportSQL.append("                 WHERE CID = ?zeroCid) i2 ");
	     exportSQL.append("               ) n ");
	     exportSQL.append("            ON i.CATEGORY_CD = n.CATEGORY_CD ");
	     exportSQL.append(" LEFT JOIN (SELECT p.ITEM_CD, p.ITEM_NAME, p.REQUIRED_ATR, p.ABOLITION_ATR, p.PER_INFO_CTG_ID, ctg.CID,");
	     exportSQL.append("                       io.DISPORDER AS ITEM_DISPORDER");
	     exportSQL.append("			FROM PPEMT_ITEM p ");
	     exportSQL.append("			INNER JOIN PPEMT_CTG ctg ");
	     exportSQL.append("				ON p.PER_INFO_CTG_ID = ctg.PER_INFO_CTG_ID");
	     exportSQL.append("			INNER JOIN PPEMT_ITEM_COMMON icm");
	     exportSQL.append("				ON p.ITEM_CD = icm.ITEM_CD AND icm.CONTRACT_CD = ?contracCd");
	     exportSQL.append("					AND icm.CATEGORY_CD = ctg.CATEGORY_CD AND icm.ITEM_PARENT_CD IS NULL");
	     exportSQL.append("                   INNER JOIN PPEMT_ITEM_SORT io ON p.PER_INFO_ITEM_DEFINITION_ID = io.PER_INFO_ITEM_DEFINITION_ID");
	     exportSQL.append("							AND io.PER_INFO_CTG_ID = ctg.PER_INFO_CTG_ID");
	     exportSQL.append("	) item ON item.PER_INFO_CTG_ID = o.PER_INFO_CTG_ID AND item.CID = i.CID");
	     exportSQL.append("                     LEFT JOIN  (SELECT ITEM_NAME, ");
	     exportSQL.append("                                             ITEM_CD, ");
	     exportSQL.append("                                             CATEGORY_CD");
	     exportSQL.append("                                     FROM ");
	     exportSQL.append("                                             (SELECT ");
	     exportSQL.append("                                                     CATEGORY_CD , ");
	     exportSQL.append("                                                     PER_INFO_CTG_ID ");
	     exportSQL.append("                                             FROM PPEMT_CTG ");
	     exportSQL.append("                                             WHERE CID = ?zeroCid ");
	     exportSQL.append("                                             ) k ");
	     exportSQL.append("                                     INNER JOIN PPEMT_ITEM q ON q.PER_INFO_CTG_ID = k.PER_INFO_CTG_ID ");
	     exportSQL.append("                   ) ni  ");
	     exportSQL.append("                     ON  ni.ITEM_CD = item.ITEM_CD AND ni.CATEGORY_CD = i.CATEGORY_CD ");
	     exportSQL.append("                 )temp");
	     exportSQL.append("    ORDER BY  temp.DISPORDER, temp.ITEM_DISPORDER;");

	     GET_PER_INFOR_DEFINITION = exportSQL.toString();
	  }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getChangePerInforDefinitionToExport(String cid, String contracCd,String companyIdRoot,int salaryUseAtr, int personnelUseAtr, int employmentUseAtr) {
		List<Object[]> resultQuery = null;
		try {

			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(GET_PER_INFOR_DEFINITION)
					.setParameter("cid", cid)
					.setParameter("salaryUseAtr",salaryUseAtr)
					.setParameter("personnelUseAtr",personnelUseAtr)
					.setParameter("employmentUseAtr",employmentUseAtr)
					.setParameter("contracCd", contracCd)
					.setParameter("zeroCid", companyIdRoot)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

}
