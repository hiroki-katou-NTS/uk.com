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
 * The Class KrqstStampRequestSettingDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstStampRequestSettingDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQST_STAMP_REQUEST_SET(CID, TOP_COMMENT_TEXT, TOP_COMMENT_FONT_COLOR, TOP_COMMENT_FONT_WEIGHT, BOTTOM_COMMENT_TEXT, BOTTOM_COMMENT_FONT_COLOR, "
			+ "BOTTOM_COMMENT_FONT_WEIGHT, RESULT_DISP_FLG, SUP_FRAME_DISP_NO, STAMP_PLACE_DISP_FLG, STAMP_ATR_WORK_DISP_FLG, STAMP_ATR_GO_OUT_DISP_FLG, "
			+ "STAMP_ATR_CARE_DISP_FLG, STAMP_ATR_SUP_DISP_FLG, STAMP_ATR_CHILD_CARE_DISP_FLG, STAMP_OUT_PRI_DISP_FLG, STAMP_OUT_PUB_DISP_FLG, STAMP_OUT_COMP_DISP_FLG, STAMP_OUT_UNION_DISP_FLG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, TOP_COMMENT_TEXT, TOP_COMMENT_FONT_COLOR, TOP_COMMENT_FONT_WEIGHT, BOTTOM_COMMENT_TEXT, BOTTOM_COMMENT_FONT_COLOR, "
			+ "BOTTOM_COMMENT_FONT_WEIGHT, RESULT_DISP_FLG, SUP_FRAME_DISP_NO, STAMP_PLACE_DISP_FLG, STAMP_ATR_WORK_DISP_FLG, STAMP_ATR_GO_OUT_DISP_FLG, "
			+ "STAMP_ATR_CARE_DISP_FLG, STAMP_ATR_SUP_DISP_FLG, STAMP_ATR_CHILD_CARE_DISP_FLG, STAMP_OUT_PRI_DISP_FLG, STAMP_OUT_PUB_DISP_FLG, STAMP_OUT_COMP_DISP_FLG, STAMP_OUT_UNION_DISP_FLG "
			+ "FROM KRQST_STAMP_REQUEST_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_STAMP_REQUEST_SET WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 19 + 1, this.companyId);
					insertQuery.setParameter(i * 19 + 2, dataArr[1]);
					insertQuery.setParameter(i * 19 + 3, dataArr[2]);
					insertQuery.setParameter(i * 19 + 4, dataArr[3]);
					insertQuery.setParameter(i * 19 + 5, dataArr[4]);
					insertQuery.setParameter(i * 19 + 6, dataArr[5]);
					insertQuery.setParameter(i * 19 + 7, dataArr[6]);
					insertQuery.setParameter(i * 19 + 8, dataArr[7]);
					insertQuery.setParameter(i * 19 + 9, dataArr[8]);
					insertQuery.setParameter(i * 19 + 10, dataArr[9]);
					insertQuery.setParameter(i * 19 + 11, dataArr[10]);
					insertQuery.setParameter(i * 19 + 12, dataArr[11]);
					insertQuery.setParameter(i * 19 + 13, dataArr[12]);
					insertQuery.setParameter(i * 19 + 14, dataArr[13]);
					insertQuery.setParameter(i * 19 + 15, dataArr[14]);
					insertQuery.setParameter(i * 19 + 16, dataArr[15]);
					insertQuery.setParameter(i * 19 + 17, dataArr[16]);
					insertQuery.setParameter(i * 19 + 18, dataArr[17]);
					insertQuery.setParameter(i * 19 + 19, dataArr[18]);
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
	 * Instantiates a new krqst stamp request setting data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstStampRequestSettingDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod,
			String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
}
