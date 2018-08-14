package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstOtRestAppComSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstOtRestAppComSetDataCopyHandler implements DataCopyHandler {

	/** The entity manager. */
	private EntityManager entityManager;
	
	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQST_OT_REST_APP_COM_SET(CID, APP_TYPE, DIVERGENCE_REASON_INPUT_ATR, DIVERGENCE_REASON_FORM_ATR, DIVERGENCE_REASON_REQUIRED, PRE_DISPLAY_ATR, PRE_EXCESS_DISPLAY_SETTING "
			+ ", BONUS_TIME_DISPLAY_ATR ,OUTING_SETTING_ATR ,PERFORMANCE_DISPLAY_ATR, PERFORMANCE_EXCESS_ATR, INTRUCT_DISPLAY_ATR, EXTRATIME_DISPLAY_ATR ,"
			+ "EXTRATIME_EXCESS_ATR ,APP_DATE_CONTRADICTION_ATR ,CAL_OVERTIME_DISPLAY_ATR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, APP_TYPE, DIVERGENCE_REASON_INPUT_ATR, DIVERGENCE_REASON_FORM_ATR, DIVERGENCE_REASON_REQUIRED, PRE_DISPLAY_ATR, PRE_EXCESS_DISPLAY_SETTING "
			+ ", BONUS_TIME_DISPLAY_ATR ,OUTING_SETTING_ATR ,PERFORMANCE_DISPLAY_ATR, PERFORMANCE_EXCESS_ATR, INTRUCT_DISPLAY_ATR, EXTRATIME_DISPLAY_ATR ,"
			+ "EXTRATIME_EXCESS_ATR ,APP_DATE_CONTRADICTION_ATR ,CAL_OVERTIME_DISPLAY_ATR "
			+ "FROM KRQST_OT_REST_APP_COM_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_OT_REST_APP_COM_SET WHERE CID = ?";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY)
				.setParameter(1, AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();
		
		if(zeroCompanyDatas.length == 0)
			return;
		this.entityManager.joinTransaction();
		switch (copyMethod) {
			case REPLACE_ALL:
				Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY)
					.setParameter(1, this.companyId);
				deleteQuery.executeUpdate();
			case ADD_NEW:
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * 16 + 1, this.companyId);
					insertQuery.setParameter(i * 16 + 2, dataArr[1]);
					insertQuery.setParameter(i * 16 + 3, dataArr[2]);
					insertQuery.setParameter(i * 16 + 4, dataArr[3]);
					insertQuery.setParameter(i * 16 + 5, dataArr[4]);
					insertQuery.setParameter(i * 16 + 6, dataArr[5]);
					insertQuery.setParameter(i * 16 + 7, dataArr[6]);
					insertQuery.setParameter(i * 16 + 8, dataArr[7]);
					insertQuery.setParameter(i * 16 + 9, dataArr[8]);
					insertQuery.setParameter(i * 16 + 10, dataArr[9]);
					insertQuery.setParameter(i * 16 + 11, dataArr[10]);
					insertQuery.setParameter(i * 16 + 12, dataArr[11]);
					insertQuery.setParameter(i * 16 + 13, dataArr[12]);
					insertQuery.setParameter(i * 16 + 14, dataArr[13]);
					insertQuery.setParameter(i * 16 + 15, dataArr[14]);
					insertQuery.setParameter(i * 16 + 16, dataArr[15]);
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
	 * Instantiates a new krqst ot rest app com set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstOtRestAppComSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
