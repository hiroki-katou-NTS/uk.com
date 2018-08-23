package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import java.util.List;

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
 * The Class KrcstMonsetLglTrnsSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrcstMonsetLglTrnsSetDataCopyHandler extends DataCopyHandler {
	/** The current parameter. */
	private static final int CURRENT_COLUMN = 17;

	/**
	 * The insert query.
	 */
	private static final String INSERT_QUERY = "INSERT INTO KRCST_MONSET_LGL_TRNS_SET(CID ,OVER_TIME_ORDER_01,OVER_TIME_ORDER_02,OVER_TIME_ORDER_03,OVER_TIME_ORDER_04,OVER_TIME_ORDER_05,OVER_TIME_ORDER_06,OVER_TIME_ORDER_07,OVER_TIME_ORDER_08,OVER_TIME_ORDER_09,OVER_TIME_ORDER_10,HDWK_TIME_ORDER_01,HDWK_TIME_ORDER_02,HDWK_TIME_ORDER_03,HDWK_TIME_ORDER_04,HDWK_TIME_ORDER_05,HDWK_TIME_ORDER_06,HDWK_TIME_ORDER_07,HDWK_TIME_ORDER_08,HDWK_TIME_ORDER_09,HDWK_TIME_ORDER_10) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID ,OVER_TIME_ORDER_01,OVER_TIME_ORDER_02,OVER_TIME_ORDER_03,OVER_TIME_ORDER_04,OVER_TIME_ORDER_05,OVER_TIME_ORDER_06,OVER_TIME_ORDER_07,OVER_TIME_ORDER_08,OVER_TIME_ORDER_09,OVER_TIME_ORDER_10,HDWK_TIME_ORDER_01,HDWK_TIME_ORDER_02,HDWK_TIME_ORDER_03,HDWK_TIME_ORDER_04,HDWK_TIME_ORDER_05,HDWK_TIME_ORDER_06,HDWK_TIME_ORDER_07,HDWK_TIME_ORDER_08,HDWK_TIME_ORDER_09,HDWK_TIME_ORDER_10 FROM KRCST_MONSET_LGL_TRNS_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_MONSET_LGL_TRNS_SET WHERE CID = ?";

	/**
	 * Instantiates a new krcst monset lgl trns set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KrcstMonsetLglTrnsSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
		List<Object> zeroCompanyDatas = selectQuery.getResultList();

		if (zeroCompanyDatas.isEmpty())
			return;
		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			// Get all company zero data
			Query query = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1, this.companyId);
			List<Object> curentCompanyDatas = query.getResultList();

			if (!curentCompanyDatas.isEmpty())
				return;
			// Create quuery string base on zero company data
			String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.size());
			if (!StringUtils.isEmpty(insertQueryStr)) {
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);

				// Loop to set parameter to query
				for (int i = 0, j = zeroCompanyDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas.get(i);
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
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 12, dataArr[11]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 13, dataArr[12]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 14, dataArr[13]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 15, dataArr[14]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 16, dataArr[15]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 17, dataArr[16]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 18, dataArr[17]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 19, dataArr[18]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 20, dataArr[19]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 21, dataArr[20]);
				}

				// Run insert query
				insertQuery.executeUpdate();
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
