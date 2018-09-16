package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import java.util.List;

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
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class CcgstStandardMenuDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO CCGST_STANDARD_MENU(CID ,CODE,SYSTEM,MENU_CLS,TARGET_ITEMS,DISPLAY_NAME,DISPLAY_ORDER,MENU_ATR,URL,WEB_MENU_SETTING,AFTER_LOGIN_DISPLAY,LOG_SETTING_DISPLAY,PROGRAM_ID,SCREEN_ID,QUERY_STRING) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID ,CODE,SYSTEM,MENU_CLS,TARGET_ITEMS,DISPLAY_NAME,DISPLAY_ORDER,MENU_ATR,URL,WEB_MENU_SETTING,AFTER_LOGIN_DISPLAY,LOG_SETTING_DISPLAY,PROGRAM_ID,SCREEN_ID,QUERY_STRING FROM CCGST_STANDARD_MENU WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM CCGST_STANDARD_MENU WHERE CID = ?";
	
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
	@SuppressWarnings("unchecked")
	@Override
	public void doCopy() {
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		List<Object> zeroCompanyDatas = selectQuery.getResultList();
		if(zeroCompanyDatas.isEmpty()) return ;
		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			if (copyMethod == CopyMethod.ADD_NEW) {
				// get old data target by cid
				Query selectQueryTarget = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
						this.companyId);
				List<Object> oldDatas = selectQueryTarget.getResultList();
				// ignore data existed
				for (int i = 0; i < zeroCompanyDatas.size(); i++) {
					Object[] dataAttr = (Object[]) zeroCompanyDatas.get(i);
					for (int j = 0; j < oldDatas.size(); j++) {
						Object[] targetAttr = (Object[]) oldDatas.get(j);
						// compare keys and remove
						if (dataAttr[1].equals(targetAttr[1]) &&
							dataAttr[2].equals(targetAttr[2])	
							) {
							zeroCompanyDatas.remove(i);
							i -= 1;
							break;
						}
					}
				}
			}
			//copy data
			for (int i = 0, j = zeroCompanyDatas.size(); i < j; i++) {
				Query insertQuery = this.entityManager.createNativeQuery(INSERT_QUERY);
				Object[] dataArr = (Object[]) zeroCompanyDatas.get(i);
				insertQuery.setParameter(1, this.companyId);
				insertQuery.setParameter(2, dataArr[1]);
				insertQuery.setParameter(3, dataArr[2]);
				insertQuery.setParameter(4, dataArr[3]);
				insertQuery.setParameter(5, dataArr[4]);
				insertQuery.setParameter(6, dataArr[5]);
				insertQuery.setParameter(7, dataArr[6]);
				insertQuery.setParameter(8, dataArr[7]);
				insertQuery.setParameter(9, dataArr[8]);
				insertQuery.setParameter(10, dataArr[9]);
				insertQuery.setParameter(11, dataArr[10]);
				insertQuery.setParameter(12, dataArr[11]);
				insertQuery.setParameter(13, dataArr[12]);
				insertQuery.setParameter(14, dataArr[13]);
				insertQuery.setParameter(15, dataArr[14]);
				if(!StringUtils.isEmpty(insertQuery.toString())) insertQuery.executeUpdate();
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
