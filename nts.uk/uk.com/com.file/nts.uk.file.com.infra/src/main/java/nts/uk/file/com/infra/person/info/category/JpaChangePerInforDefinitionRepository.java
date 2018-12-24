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
			        "SELECT ROW_NUMBER() OVER (PARTITION BY i.CATEGORY_NAME ORDER BY  o.DISPORDER) AS ROW_NUMBER, "+
                        "i.CATEGORY_NAME, "+
					    "i.ABOLITION_ATR, "+
					    "n.CATEGORY_NAME as NAME_CTG_DEFAULT, "+
					    "c.CATEGORY_TYPE, "+
					    "p.ITEM_NAME, "+
                        "ni.ITEM_NAME as NAME_ITEM_DEFAULT, "+
					    "p.REQUIRED_ATR, "+
					    "p.ABOLITION_ATR as ABOLITION_ATR1, "+
						"o.DISPORDER " +
			        "FROM ( " +
					        "SELECT CATEGORY_NAME , "+
							        "ABOLITION_ATR, "+
							        "CATEGORY_CD, "+
							        "PER_INFO_CTG_ID, "+
							        "CID "+
					        "FROM PPEMT_PER_INFO_CTG "+
					        "WHERE CID = ?cid "+
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
								        "INNER JOIN PPEMT_PER_INFO_CTG_CM c2 ON i2.CATEGORY_CD = c2.CATEGORY_CD "+
						        ") n "+
			        "ON i.CATEGORY_CD = n.CATEGORY_CD "+
			        "INNER JOIN PPEMT_PER_INFO_ITEM p ON p.PER_INFO_CTG_ID = o.PER_INFO_CTG_ID "+
                    "INNER JOIN  (SELECT ITEM_NAME, "+
                                            "ITEM_CD, "+
                                            "CATEGORY_CD "+
                                    "FROM "+
                                            "(SELECT "+
                                                    "CATEGORY_CD , "+
                                                    "PER_INFO_CTG_ID "+
                                            "FROM PPEMT_PER_INFO_CTG "+
                                            "WHERE CID = ?zeroCid "+
                                            ") k "+
                                    "INNER JOIN PPEMT_PER_INFO_ITEM q ON q.PER_INFO_CTG_ID = k.PER_INFO_CTG_ID "+
						            ") ni " +
                    "ON  ni.ITEM_CD = p.ITEM_CD AND ni.CATEGORY_CD = i.CATEGORY_CD "+
                ")temp "+
			"ORDER BY temp.DISPORDER ";

	@Override
	public List<Object[]> getChangePerInforDefinitionToExport(String cid, String contracCd,String companyIdRoot) {
		List<Object[]> resultQuery = null;
		try {

			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(GET_PER_INFOR_DEFINITION)
					.setParameter("cid", cid)
					.setParameter("contracCd", contracCd)
					.setParameter("zeroCid", companyIdRoot)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery;
	}

}
