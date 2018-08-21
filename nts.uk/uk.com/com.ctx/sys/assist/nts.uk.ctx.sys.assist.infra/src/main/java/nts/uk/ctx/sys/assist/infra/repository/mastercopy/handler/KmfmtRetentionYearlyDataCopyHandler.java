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

/**
 * The Class KmfmtRetentionYearlyDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KmfmtRetentionYearlyDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KMFMT_RETENTION_YEARLY(CID, NUMBER_OF_YEAR, MAX_NUMBER_OF_DAYS, LEAVE_AS_WORK_DAYS, MANAGEMENT_YEARLY_ATR) VALUES (?,?,?,?,?)";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, NUMBER_OF_YEAR, MAX_NUMBER_OF_DAYS, LEAVE_AS_WORK_DAYS, MANAGEMENT_YEARLY_ATR FROM KMFMT_RETENTION_YEARLY WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KMFMT_RETENTION_YEARLY WHERE CID = ?";
	
	/** The paramater quantity. */
	private final int PARAMATER_QUANTITY = 5;

	/**
	 * Instantiates a new kmfmt retention yearly data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KmfmtRetentionYearlyDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
		if (zeroCompanyDatas.length == 0) {
			return;
		} else {
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
				}

				// Run insert query
				if(!StringUtils.isEmpty(insertQueryStr)) insertQuery.executeUpdate();
			case DO_NOTHING:
				// Do nothing
			default:
				break;
			}
		}

	}
}
