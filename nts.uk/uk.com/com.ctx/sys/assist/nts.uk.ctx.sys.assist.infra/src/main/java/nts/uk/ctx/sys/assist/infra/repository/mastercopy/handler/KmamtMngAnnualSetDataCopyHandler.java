package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KmamtMngAnnualSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KmamtMngAnnualSetDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KMAMT_MNG_ANNUAL_SET(CID, HALF_MAX_GRANT_DAY, HALF_MAX_DAY_YEAR, HALF_MANAGE_ATR, HALF_MAX_REFERENCE, HALF_MAX_UNIFORM_COMP, "
			+ "IS_WORK_DAY_CAL, RETENTION_YEAR, REMAINING_MAX_DAY, NEXT_GRANT_DAY_DISP_ATR, REMAINING_NUM_DISP_ATR, YEARLY_OF_DAYS, ROUND_PRO_CLA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, HALF_MAX_GRANT_DAY, HALF_MAX_DAY_YEAR, HALF_MANAGE_ATR, HALF_MAX_REFERENCE, HALF_MAX_UNIFORM_COMP, "
			+ "IS_WORK_DAY_CAL, RETENTION_YEAR, REMAINING_MAX_DAY, NEXT_GRANT_DAY_DISP_ATR, REMAINING_NUM_DISP_ATR, YEARLY_OF_DAYS, ROUND_PRO_CLA FROM KMAMT_MNG_ANNUAL_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KMAMT_MNG_ANNUAL_SET WHERE CID = ?";

	/** The paramater quantity. */
	private final int PARAMATER_QUANTITY = 13;

	/**
	 * Instantiates a new kmamt mng annual set data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KmamtMngAnnualSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();
		if (zeroCompanyDatas.length == 0) return;
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
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 1, this.companyId);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 2, dataArr[1]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 3, dataArr[2]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 4, dataArr[3]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 5, dataArr[4]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 6, dataArr[5]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 7, dataArr[6]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 8, dataArr[7]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 9, dataArr[8]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 10, dataArr[9]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 11, dataArr[10]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 12, dataArr[11]);
				insertQuery.setParameter(i * PARAMATER_QUANTITY + 13, dataArr[12]);
			}
			// Run insert query
			if (!StringUtils.isEmpty(insertQueryStr))
				insertQuery.executeUpdate();
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}
}
