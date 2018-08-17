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
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CcgstStandardMenuDataCopyHandler implements DataCopyHandler {

	private EntityManager entityManager;

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO CCGST_STANDARD_MENU(CID ,CODE,SYSTEM,MENU_CLS,TARGET_ITEMS,DISPLAY_NAME,DISPLAY_ORDER,MENU_ATR,URL,WEB_MENU_SETTING,AFTER_LOGIN_DISPLAY,LOG_SETTING_DISPLAY,PROGRAM_ID,SCREEN_ID,QUERY_STRING) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID ,CODE,SYSTEM,MENU_CLS,TARGET_ITEMS,DISPLAY_NAME,DISPLAY_ORDER,MENU_ATR,URL,WEB_MENU_SETTING,AFTER_LOGIN_DISPLAY,LOG_SETTING_DISPLAY,PROGRAM_ID,SCREEN_ID,QUERY_STRING FROM CCGST_STANDARD_MENU WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM CCGST_STANDARD_MENU WHERE CID = ?";
	
	private final int QUANTITY_PARAMETER = 15;

	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyCd
	 *            the company cd
	 */
	public CcgstStandardMenuDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 1, this.companyId);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 2, dataArr[1]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 3, dataArr[2]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 4, dataArr[3]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 5, dataArr[4]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 6, dataArr[5]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 7, dataArr[6]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 8, dataArr[7]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 9, dataArr[8]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 10, dataArr[9]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 11, dataArr[10]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 12, dataArr[11]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 13, dataArr[12]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 14, dataArr[13]);
				insertQuery.setParameter(i * QUANTITY_PARAMETER + 15, dataArr[14]);
			}
			// Run insert query
			insertQuery.executeUpdate();
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
