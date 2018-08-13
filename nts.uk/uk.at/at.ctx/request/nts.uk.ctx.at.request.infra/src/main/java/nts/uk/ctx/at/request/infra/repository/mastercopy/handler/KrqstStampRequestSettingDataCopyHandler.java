package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.stamp.KrqstStampRequestSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstStampRequestSettingDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstStampRequestSettingDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
    /** The Constant SELECT_BY_KEY_STRING. */
    private static final String SELECT_BY_KEY_STRING = "SELECT f FROM KrqstStampRequestSetting f WHERE  f.companyID =:cid ";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Optional<KrqstStampRequestSetting> entityZeroData = this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstStampRequestSetting.class)
        .setParameter("cid", AppContexts.user().zeroCompanyIdInContract()).getSingle();

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			this.commandProxy().remove(KrqstStampRequestSetting.class, this.companyId); 
		case ADD_NEW:
			// Insert Data
			if (entityZeroData.isPresent()) {
				KrqstStampRequestSetting entityZero = entityZeroData.get();
				KrqstStampRequestSetting entity = new KrqstStampRequestSetting();
				entity.bottomComment = entityZero.bottomComment;
				entity.bottomCommentFontColor = entityZero.bottomCommentFontColor;
				entity.bottomCommentFontWeight = entityZero.bottomCommentFontWeight;
				entity.companyID = this.companyId;
				entity.resultDisp = entityZero.resultDisp;
				entity.stampAtr_Care_Disp = entityZero.stampAtr_Care_Disp;
				entity.stampAtr_Child_Care_Disp = entityZero.stampAtr_Child_Care_Disp;
				entity.stampAtr_GoOut_Disp = entityZero.stampAtr_GoOut_Disp;
				entity.stampAtr_Sup_Disp = entityZero.stampAtr_Sup_Disp;
				entity.stampAtr_Work_Disp = entityZero.stampAtr_Work_Disp;
				entity.stampGoOutAtr_Compensation_Disp = entityZero.stampGoOutAtr_Compensation_Disp;
				entity.stampGoOutAtr_Private_Disp = entityZero.stampGoOutAtr_Private_Disp;
				entity.stampGoOutAtr_Public_Disp = entityZero.stampGoOutAtr_Public_Disp;
				entity.stampGoOutAtr_Union_Disp = entityZero.stampGoOutAtr_Union_Disp;
				entity.stampPlaceDisp = entityZero.stampPlaceDisp;
				entity.supFrameDispNO = entityZero.supFrameDispNO;
				entity.topComment = entityZero.topComment;
				entity.topCommentFontColor = entityZero.topCommentFontColor;
				entity.topCommentFontWeight = entityZero.topCommentFontWeight;
				this.commandProxy().insert(entityZero);
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
