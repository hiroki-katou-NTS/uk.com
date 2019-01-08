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

	private static final String GET_PER_INFOR_DEFINITION =
            "SELECT "+
                    "CASE WHEN temp.ROW_NUMBER = 1 THEN temp.CATEGORY_NAME ELSE NULL END, "+
                    "CASE WHEN temp.ROW_NUMBER = 1 THEN temp.ABOLITION_ATR ELSE NULL END, "+
                    "CASE WHEN temp.ROW_NUMBER = 1 THEN temp.NAME_CTG_DEFAULT ELSE NULL END, "+
                    "CASE WHEN temp.ROW_NUMBER = 1 THEN temp.CATEGORY_TYPE ELSE NULL END, "+
                    "temp.ITEM_NAME,  "+
                    "temp.NAME_ITEM_DEFAULT, "+
                    "temp.REQUIRED_ATR, "+
                    "temp.ABOLITION_ATR1 "+
            "FROM ( "+
			        "SELECT ROW_NUMBER() OVER (PARTITION BY i.CATEGORY_NAME ORDER BY  o.DISPORDER, ITEM_DISPORDER) AS ROW_NUMBER, "+
                        "i.CATEGORY_NAME, "+
					    "i.ABOLITION_ATR, "+
					    "n.CATEGORY_NAME as NAME_CTG_DEFAULT, "+
					    "c.CATEGORY_TYPE, "+
					    "p.ITEM_NAME, "+
                        "ni.ITEM_NAME as NAME_ITEM_DEFAULT, "+
					    "p.REQUIRED_ATR, "+
					    "p.ABOLITION_ATR as ABOLITION_ATR1, "+
						"o.DISPORDER, " +
						"ITEM_DISPORDER" +
			        "FROM ( " +
					        "SELECT CATEGORY_NAME , "+
							        "ABOLITION_ATR, "+
							        "CATEGORY_CD, "+
							        "PER_INFO_CTG_ID, "+
							        "CID "+
					        "FROM PPEMT_PER_INFO_CTG cc "+
							"INNER JOIN PPEMT_PER_INFO_CTG_CM ccm " +
							"ON cc.CATEGORY_CD = ccm.CATEGORY_CD " +
					        "WHERE CID = ?cid "+
							"AND ((ccm.SALARY_USE_ATR = 1 AND ?salaryUseAtr = 1) " +
							"OR (ccm.PERSONNEL_USE_ATR = 1 AND ?personnelUseAtr = 1 ) " +
							"OR (ccm.EMPLOYMENT_USE_ATR = 1 AND  ?employmentUseAtr = 1)) " +
							"OR (?salaryUseAtr = 0 AND ?personnelUseAtr = 0 AND ?employmentUseAtr = 0) " +
				            ") i "+
			        "INNER JOIN (SELECT CATEGORY_CD, "+
						                "CATEGORY_TYPE, "+
										"CATEGORY_PARENT_CD "+
						                "FROM PPEMT_PER_INFO_CTG_CM "+
						                "WHERE CONTRACT_CD = ?contracCd AND CATEGORY_PARENT_CD IS NULL "+
						            ") c "+
                    "ON i.CATEGORY_CD = c.CATEGORY_CD "+
			        "INNER JOIN PPEMT_PER_INFO_CTG_ORDER o ON i.CID = o.CID AND o.PER_INFO_CTG_ID = i.PER_INFO_CTG_ID "+
			        "INNER JOIN ( "+
						        "SELECT CATEGORY_NAME , i2.CATEGORY_CD , i2.CID "+
						        "FROM "+
								        "(SELECT CATEGORY_NAME, CATEGORY_CD, CID "+
								        "FROM PPEMT_PER_INFO_CTG "+
								        "WHERE CID = ?zeroCid) i2 "+
					
						        ") n "+
			        "ON i.CATEGORY_CD = n.CATEGORY_CD "+
			        "INNER JOIN PPEMT_PER_INFO_ITEM p ON p.PER_INFO_CTG_ID = o.PER_INFO_CTG_ID "+
                    "INNER JOIN  (SELECT ITEM_NAME, "+
                                            "ITEM_CD, "+
                                            "CATEGORY_CD, io.DISPORDER AS ITEM_DISPORDER"+
                                    "FROM "+
                                            "(SELECT "+
                                                    "CATEGORY_CD , "+
                                                    "PER_INFO_CTG_ID "+
                                            "FROM PPEMT_PER_INFO_CTG "+
                                            "WHERE CID = ?zeroCid "+
                                            ") k "+
                                    "INNER JOIN PPEMT_PER_INFO_ITEM q ON q.PER_INFO_CTG_ID = k.PER_INFO_CTG_ID "+
									"INNER JOIN PPEMT_PER_INFO_ITEM_ORDER io ON q.PER_INFO_ITEM_DEFINITION_ID = io.PER_INFO_ITEM_DEFINITION_ID" +
						            ") ni " +
                    "ON  ni.ITEM_CD = p.ITEM_CD AND ni.CATEGORY_CD = i.CATEGORY_CD "+
                ")temp "+
					" INNER JOIN PPEMT_PER_INFO_ITEM_CM icm ON icm.CONTRACT_CD = temp.CONTRACT_CD" +
					" AND icm.ITEM_CD = temp.ITEM_CD" +
					" AND icm.CATEGORY_CD = temp.CATEGORY_CD" +
					" WHERE ITEM_PARENT_CD IS NULL" +
			"ORDER BY temp.DISPORDER, , temp.ITEM_DISPORDER";

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
