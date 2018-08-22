package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

// TODO: Auto-generated Javadoc

/**
 * The Class KrcstComRegMCalSetDataCopyHandler.
 */

@Getter
@Setter
@NoArgsConstructor
public class KrcstComRegMCalSetDataCopyHandler extends DataCopyHandler {
	/** The current parameter. */
	private static final int CURRENT_COLUMN = 7;

	/**
	 * The insert query.
	 */
	private static final String INSERT_QUERY = "INSERT INTO KRCST_COM_REG_M_CAL_SET(CID ,INCLUDE_LEGAL_OT,INCLUDE_HOLIDAY_OT,INCLUDE_EXTRA_OT,INCLUDE_LEGAL_AGGR,INCLUDE_HOLIDAY_AGGR,INCLUDE_EXTRA_AGGR) VALUES (?,?,?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID ,INCLUDE_LEGAL_OT,INCLUDE_HOLIDAY_OT,INCLUDE_EXTRA_OT,INCLUDE_LEGAL_AGGR,INCLUDE_HOLIDAY_AGGR,INCLUDE_EXTRA_AGGR FROM KRCST_COM_REG_M_CAL_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_COM_REG_M_CAL_SET WHERE CID = ?";

	/**
	 * Instantiates a new krcst com reg M cal set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KrcstComRegMCalSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
