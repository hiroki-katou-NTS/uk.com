package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc

/**
 * The Class KrcstComDeforMCalSetDataCopyHandler.
 */

@Getter
@Setter

/**
 * Instantiates a new krcst com defor M cal set data copy handler.
 */
@NoArgsConstructor
@AllArgsConstructor
public class KrcstComDeforMCalSetDataCopyHandler implements DataCopyHandler {
	/** The current parameter. */
	private final int CURRENT_COLUMN = 11;

	/**
	 * The entity manager.
	 */
	private EntityManager entityManager;

	/**
	 * The copy method.
	 */
	private CopyMethod copyMethod;

	/**
	 * The company Id.
	 */
	private String companyId;

	/**
	 * The insert query.
	 */
	private String INSERT_QUERY = "INSERT INTO KRCST_COM_DEFOR_M_CAL_SET(CID ,INCLUDE_LEGAL_OT,INCLUDE_HOLIDAY_OT,INCLUDE_EXTRA_OT,INCLUDE_LEGAL_AGGR,INCLUDE_HOLIDAY_AGGR,INCLUDE_EXTRA_AGGR,IS_OT_IRG,PERIOD,REPEAT_ATR,STR_MONTH) VALUES (?,?,?,?,?,?,?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private String SELECT_BY_CID_QUERY = "SELECT CID ,INCLUDE_LEGAL_OT,INCLUDE_HOLIDAY_OT,INCLUDE_EXTRA_OT,INCLUDE_LEGAL_AGGR,INCLUDE_HOLIDAY_AGGR,INCLUDE_EXTRA_AGGR,IS_OT_IRG,PERIOD,REPEAT_ATR,STR_MONTH FROM KRCST_COM_DEFOR_M_CAL_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_COM_DEFOR_M_CAL_SET WHERE CID = ?";

	/**
	 * Instantiates a new krcst com defor M cal set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KrcstComDeforMCalSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {

		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();

		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			// Create quuery string base on zero company data
			if (zeroCompanyDatas.length > 0) {
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				if (!StringUtils.isEmpty(insertQueryStr)) {
					Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);

					// Loop to set parameter to query
					for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
						Object[] dataArr = (Object[]) zeroCompanyDatas[i];
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 1, this.companyId);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 2, dataArr[1]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 3, dataArr[2]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 4, dataArr[3]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 5, dataArr[4]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 6, dataArr[5]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 7, dataArr[6]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 8, dataArr[7]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 9, dataArr[8]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 10, dataArr[9]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 11, dataArr[10]);
					}

					// Run insert query
					insertQuery.executeUpdate();
				}
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}
}
