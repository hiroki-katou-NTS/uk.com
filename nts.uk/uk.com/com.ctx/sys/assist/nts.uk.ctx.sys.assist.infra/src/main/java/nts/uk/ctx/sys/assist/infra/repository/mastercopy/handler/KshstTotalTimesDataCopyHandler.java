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
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstTotalTimesDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private static final String INSERT_QUERY = "INSERT INTO KSHST_TOTAL_TIMES(CID ,TOTAL_TIMES_NO,USE_ATR,COUNT_ATR,TOTAL_TIMES_NAME,TOTAL_TIMES_ABNAME,SUMMARY_ATR) VALUES (?, ?, ?, ?, ?,?,?);";

	/** The select by cid query. */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID ,TOTAL_TIMES_NO,USE_ATR,COUNT_ATR,TOTAL_TIMES_NAME,TOTAL_TIMES_ABNAME,SUMMARY_ATR FROM KSHST_TOTAL_TIMES WHERE CID = ?";

	/** The delete by cid query. */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_TOTAL_TIMES WHERE CID = ?";
	
	private static final int PARAMETER_QUANTITY = 7;

	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyCd
	 *            the company cd
	 */
	public KshstTotalTimesDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
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
			String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
			Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
			for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
				Object[] dataArr = (Object[]) zeroCompanyDatas[i];
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 1, this.companyId);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 2, dataArr[1]);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 3, dataArr[2]);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 4, dataArr[3]);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 5, dataArr[4]);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 6, dataArr[5]);
				insertQuery.setParameter(i * PARAMETER_QUANTITY + 7, dataArr[6]);
			}
			// Run insert query
			if(!insertQueryStr.equals("")) insertQuery.executeUpdate();
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
