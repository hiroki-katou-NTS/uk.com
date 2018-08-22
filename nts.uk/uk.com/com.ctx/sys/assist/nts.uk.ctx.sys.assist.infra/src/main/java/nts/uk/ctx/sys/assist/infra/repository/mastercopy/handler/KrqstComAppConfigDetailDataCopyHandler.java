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
 * The Class KrqstComAppConfigDetailDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqstComAppConfigDetailDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_COM_APP_CF_DETAIL(CID, APP_TYPE, MEMO, USE_ATR, PREREQUISITE_FORPAUSE_FLG, OT_APP_SETTING_FLG, HOLIDAY_TIME_APP_CAL_FLG, "
			+ "LATE_OR_LEAVE_APP_CANCEL_FLG, LATE_OR_LEAVE_APP_SETTING_FLG, BREAK_INPUTFIELD_DIS_FLG, BREAK_TIME_DISPLAY_FLG, ATWORK_TIME_BEGIN_DIS_FLG, GOOUT_TIME_BEGIN_DIS_FLG, TIME_CAL_USE_ATR, "
			+ "TIME_INPUT_USE_ATR, TIME_END_DIS_FLG, REQUIRED_INSTRUCTION_FLG, INSTRUCTION_ATR, INSTRUCTION_MEMO, INSTRUCTION_USE_ATR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, APP_TYPE, MEMO, USE_ATR, PREREQUISITE_FORPAUSE_FLG, OT_APP_SETTING_FLG, HOLIDAY_TIME_APP_CAL_FLG, "
			+ "LATE_OR_LEAVE_APP_CANCEL_FLG, LATE_OR_LEAVE_APP_SETTING_FLG, BREAK_INPUTFIELD_DIS_FLG, BREAK_TIME_DISPLAY_FLG, ATWORK_TIME_BEGIN_DIS_FLG, GOOUT_TIME_BEGIN_DIS_FLG, TIME_CAL_USE_ATR, "
			+ "TIME_INPUT_USE_ATR, TIME_END_DIS_FLG, REQUIRED_INSTRUCTION_FLG, INSTRUCTION_ATR, INSTRUCTION_MEMO, INSTRUCTION_USE_ATR "
			+ "FROM KRQST_COM_APP_CF_DETAIL WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_COM_APP_CF_DETAIL WHERE CID = ?";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY)
				.setParameter(1, AppContexts.user().zeroCompanyIdInContract());
		List<Object> sourceDatas = selectQuery.getResultList();
		
		if(sourceDatas.isEmpty())
			return;

		switch (copyMethod) {
			case REPLACE_ALL:
				Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY)
					.setParameter(1, this.companyId);
				deleteQuery.executeUpdate();
			case ADD_NEW:
				
				// get old data target by cid
				Query selectQueryTarget = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
						this.companyId);
				List<Object> oldDatas = selectQueryTarget.getResultList();
				// ignore data existed
				for (int i = 0; i < sourceDatas.size(); i++) {
					Object[] dataAttr = (Object[]) sourceDatas.get(i);
					for (int j = 0; j < oldDatas.size(); j++) {
						Object[] targetAttr = (Object[]) oldDatas.get(j);
						// compare keys and remove
						if (dataAttr[1].equals(targetAttr[1])) {
							sourceDatas.remove(i);
							i -= 1;
							break;
						}
					}
				}
				
				if(sourceDatas.isEmpty())
					return;
				
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, sourceDatas.size());
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = sourceDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) sourceDatas.get(i);
					insertQuery.setParameter(i * 20 + 1, this.companyId);
					insertQuery.setParameter(i * 20 + 2, dataArr[1]);
					insertQuery.setParameter(i * 20 + 3, dataArr[2]);
					insertQuery.setParameter(i * 20 + 4, dataArr[3]);
					insertQuery.setParameter(i * 20 + 5, dataArr[4]);
					insertQuery.setParameter(i * 20 + 6, dataArr[5]);
					insertQuery.setParameter(i * 20 + 7, dataArr[6]);
					insertQuery.setParameter(i * 20 + 8, dataArr[7]);
					insertQuery.setParameter(i * 20 + 9, dataArr[8]);
					insertQuery.setParameter(i * 20 + 10, dataArr[9]);
					insertQuery.setParameter(i * 20 + 11, dataArr[10]);
					insertQuery.setParameter(i * 20 + 12, dataArr[11]);
					insertQuery.setParameter(i * 20 + 13, dataArr[12]);
					insertQuery.setParameter(i * 20 + 14, dataArr[13]);
					insertQuery.setParameter(i * 20 + 15, dataArr[14]);
					insertQuery.setParameter(i * 20 + 16, dataArr[15]);
					insertQuery.setParameter(i * 20 + 17, dataArr[16]);
					insertQuery.setParameter(i * 20 + 18, dataArr[17]);
					insertQuery.setParameter(i * 20 + 19, dataArr[18]);
					insertQuery.setParameter(i * 20 + 20, dataArr[19]);
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
	 * Instantiates a new krqst com app config detail data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstComAppConfigDetailDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod,
			String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
}
