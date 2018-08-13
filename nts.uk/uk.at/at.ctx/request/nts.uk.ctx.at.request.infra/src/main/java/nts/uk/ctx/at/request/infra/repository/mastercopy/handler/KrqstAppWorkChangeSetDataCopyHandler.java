package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSetPk;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class KrqstAppWorkChangeSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstAppWorkChangeSetDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	

	private static final String SELECT_BY_KEY_STRING = "SELECT f FROM KrqstAppWorkChangeSet f WHERE f.appWorkChangeSetPk.cid =:companyID";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Optional<KrqstAppWorkChangeSet> entityZeroData = this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstAppWorkChangeSet.class)
        .setParameter("companyID", AppContexts.user().zeroCompanyIdInContract()).getSingle();

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			this.commandProxy().remove(KrqstAppWorkChangeSet.class, new KrqstAppWorkChangeSetPk(this.companyId)); 
		case ADD_NEW:
			// Insert Data
			if (entityZeroData.isPresent()) {
				KrqstAppWorkChangeSet entityZero = entityZeroData.get();
				this.commandProxy().insert(new KrqstAppWorkChangeSet(new KrqstAppWorkChangeSetPk(this.companyId),
								entityZero.excludeHoliday, entityZero.workChangeTimeAtr, entityZero.displayResultAtr,
								entityZero.initDisplayWorktime,
								entityZero.commentContent1 == null ? null : entityZero.commentContent1,
								entityZero.commentFontWeight1, entityZero.commentFontColor1,
								entityZero.commentContent2 == null ? null : entityZero.commentContent2,
								entityZero.commentFontWeight2, entityZero.commentFontColor2));
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
