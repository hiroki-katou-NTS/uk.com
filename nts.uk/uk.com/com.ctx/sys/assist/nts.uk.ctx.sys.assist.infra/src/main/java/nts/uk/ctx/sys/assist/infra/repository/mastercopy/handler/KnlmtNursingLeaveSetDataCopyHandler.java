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
 * The Class KnlmtNursingLeaveSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KnlmtNursingLeaveSetDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KNLMT_NURSING_LEAVE_SET(CID, NURSING_TYPE, MANAGE_ATR, STR_MD, NUM_LEAVE_DAY, NUM_PERSON, SPE_HOLIDAY, WORK_ABS) VALUES (?,?,?,?,?,?,?,?)";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, NURSING_TYPE, MANAGE_ATR, STR_MD, NUM_LEAVE_DAY, NUM_PERSON, SPE_HOLIDAY, WORK_ABS FROM KNLMT_NURSING_LEAVE_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KNLMT_NURSING_LEAVE_SET WHERE CID = ?";

	/** The paramater quantity. */
	private final int PARAMATER_QUANTITY = 8;

	/**
	 * Instantiates a new knlmt nursing leave set data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KnlmtNursingLeaveSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 6, dataArr[5]);
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 7, dataArr[6]);
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 8, dataArr[7]);
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
