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

/**
 * The Class KshstWorkdayoffFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstWorkdayoffFrameDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KSHST_WORKDAYOFF_FRAME(CID, WDO_FR_NO, EXCLUS_VER, USE_ATR, WDO_FR_NAME, TRANS_FR_NAME) VALUES (?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, WDO_FR_NO, EXCLUS_VER, USE_ATR, WDO_FR_NAME, TRANS_FR_NAME FROM KSHST_WORKDAYOFF_FRAME WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_WORKDAYOFF_FRAME WHERE CID = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {

		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();

		if (zeroCompanyDatas.length == 0)
			return;
		this.entityManager.joinTransaction();
		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
			Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
			for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
				Object[] dataArr = (Object[]) zeroCompanyDatas[i];
				insertQuery.setParameter(i * 6 + 1, this.companyId);
				insertQuery.setParameter(i * 6 + 2, dataArr[1]);
				insertQuery.setParameter(i * 6 + 3, dataArr[2]);
				insertQuery.setParameter(i * 6 + 4, dataArr[3]);
				insertQuery.setParameter(i * 6 + 5, dataArr[4]);
				insertQuery.setParameter(i * 6 + 6, dataArr[5]);
			}

			// Run insert query
			insertQuery.executeUpdate();
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}

	}

	/**
	 * Instantiates a new kshst workdayoff frame data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstWorkdayoffFrameDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
}
