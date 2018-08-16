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
 * The Class KrqstAppOvertimeSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstAppOvertimeSetDataCopyHandler implements DataCopyHandler {
	
	/** The entity manager. */
	private EntityManager entityManager;
	
	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQST_APP_OVERTIME_SET(CID, FLEX_EXCESS_USE_SET_ATR, PRE_TYPE_SIFT_REFLECT_FLG, PRE_OVERTIME_REFLECT_FLG, POST_TYPESIFT_REFLECT_FLG"
			+ ", POST_BREAK_REFLECT_FLG, POST_WORKTIME_REFLECT_FLG, CALENDAR_DISP_ATR, EARLY_OVER_TIME_USE_ATR, "
			+ "INSTRUCT_EXCESS_OT_ATR, PRIORITY_STAMP_SET_ATR, UNIT_ASSIGNMENT_OVERTIME, NORMAL_OVERTIME_USE_ATR, DATTENDANCE_ID, USE_OT_WORK, REST_ATR, WORKTYPE_PERMISSION_FLAG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, FLEX_EXCESS_USE_SET_ATR, PRE_TYPE_SIFT_REFLECT_FLG, PRE_OVERTIME_REFLECT_FLG, POST_TYPESIFT_REFLECT_FLG"
			+ ", POST_BREAK_REFLECT_FLG, POST_WORKTIME_REFLECT_FLG, CALENDAR_DISP_ATR, EARLY_OVER_TIME_USE_ATR, "
			+ "INSTRUCT_EXCESS_OT_ATR, PRIORITY_STAMP_SET_ATR, UNIT_ASSIGNMENT_OVERTIME, NORMAL_OVERTIME_USE_ATR, DATTENDANCE_ID, USE_OT_WORK, REST_ATR, WORKTYPE_PERMISSION_FLAG "
			+ "FROM KRQST_APP_OVERTIME_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_APP_OVERTIME_SET WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 17 + 1, this.companyId);
					insertQuery.setParameter(i * 17 + 2, dataArr[1]);
					insertQuery.setParameter(i * 17 + 3, dataArr[2]);
					insertQuery.setParameter(i * 17 + 4, dataArr[3]);
					insertQuery.setParameter(i * 17 + 5, dataArr[4]);
					insertQuery.setParameter(i * 17 + 6, dataArr[5]);
					insertQuery.setParameter(i * 17 + 7, dataArr[6]);
					insertQuery.setParameter(i * 17 + 8, dataArr[7]);
					insertQuery.setParameter(i * 17 + 9, dataArr[8]);
					insertQuery.setParameter(i * 17 + 10, dataArr[9]);
					insertQuery.setParameter(i * 17 + 11, dataArr[10]);
					insertQuery.setParameter(i * 17 + 12, dataArr[11]);
					insertQuery.setParameter(i * 17 + 13, dataArr[12]);
					insertQuery.setParameter(i * 17 + 14, dataArr[13]);
					insertQuery.setParameter(i * 17 + 15, dataArr[14]);
					insertQuery.setParameter(i * 17 + 16, dataArr[15]);
					insertQuery.setParameter(i * 17 + 17, dataArr[16]);
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
	 * Instantiates a new krqst app overtime set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstAppOvertimeSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
