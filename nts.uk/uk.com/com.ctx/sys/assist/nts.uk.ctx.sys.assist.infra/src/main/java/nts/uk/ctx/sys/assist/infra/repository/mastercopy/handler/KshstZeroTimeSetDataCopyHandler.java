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
 * The Class KshstZeroTimeSetDataCopyHandler.
 */

@Getter
@Setter
@NoArgsConstructor
public class KshstZeroTimeSetDataCopyHandler extends DataCopyHandler {
	/** The current parameter. */
	private static final int CURRENT_COLUMN = 14;


	/**
	 * The insert query.
	 */
	private static final String INSERT_QUERY = "INSERT INTO KSHST_ZERO_TIME_SET(CID,CALC_FROM_ZERO_TIME,WKD_LEGAL_HD,WKD_NON_LEGAL_HD,WKD_NON_LEGAL_PUBLIC_HD,LHD_WEEKDAY,LHD_NON_LEGAL_HD,LHD_NON_LEGAL_PUBLIC_HD,SHD_WEEKDAY,SHD_LEGAL_HD,SHD_NON_LEGAL_HD,SFT_WEEKDAY,SFT_LEGAL_HD,SFT_NON_LEGAL_PUBLIC_HD) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID,CALC_FROM_ZERO_TIME,WKD_LEGAL_HD,WKD_NON_LEGAL_HD,WKD_NON_LEGAL_PUBLIC_HD,LHD_WEEKDAY,LHD_NON_LEGAL_HD,LHD_NON_LEGAL_PUBLIC_HD,SHD_WEEKDAY,SHD_LEGAL_HD,SHD_NON_LEGAL_HD,SFT_WEEKDAY,SFT_LEGAL_HD,SFT_NON_LEGAL_PUBLIC_HD FROM KSHST_ZERO_TIME_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_ZERO_TIME_SET WHERE CID = ?";

	/**
	 * Instantiates a new kshst zero time set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstZeroTimeSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 12, dataArr[11]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 13, dataArr[12]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 14, dataArr[13]);

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
