package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqstHdAppSet;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqstHdAppSetPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstHdAppSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstHdAppSetDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
    /** The Constant SELECT_BY_KEY_STRING. */
    private static final String SELECT_BY_KEY_STRING = "SELECT f FROM KrqstHdAppSet f WHERE  f.krqstHdAppSetPK.companyId =:cid ";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Optional<KrqstHdAppSet> entityZeroData = this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstHdAppSet.class)
        .setParameter("cid", AppContexts.user().zeroCompanyIdInContract()).getSingle();

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			this.commandProxy().remove(KrqstHdAppSet.class, new KrqstHdAppSetPK(this.companyId)); 
		case ADD_NEW:
			
			// Insert Data
			KrqstHdAppSet entity = new KrqstHdAppSet();
			if (entityZeroData.isPresent()) {
				KrqstHdAppSet entityZero = entityZeroData.get();
				
				entity.krqstHdAppSetPK = new KrqstHdAppSetPK(this.companyId);
				entity.absenteeism = entityZero.absenteeism == null ? null : entityZero.absenteeism;
				entity.furikyuName = entityZero.furikyuName == null ? null : entityZero.furikyuName;
				entity.hdName = entityZero.hdName == null ? null : entityZero.hdName;
				entity.obstacleName = entityZero.obstacleName == null ? null : entityZero.obstacleName;
				entity.specialVaca = entityZero.specialVaca == null ? null : entityZero.specialVaca;
				entity.timeDigest = entityZero.timeDigest == null ? null : entityZero.timeDigest;
				entity.yearHdName = entityZero.yearHdName == null ? null : entityZero.yearHdName ;
				entity.yearResig = entityZero.yearResig == null ? null : entityZero.yearResig;

				entity.actualDisp = entityZero.actualDisp;
				entity.appDateContra = entityZero.appDateContra;
				entity.changeWrkHour = entityZero.changeWrkHour;
				entity.ckuperLimit = entityZero.ckuperLimit;
				entity.concheckDateRelease = entityZero.concheckDateRelease;
				entity.concheckOutLegal = entityZero.concheckOutLegal;
				entity.displayUnselect = entityZero.displayUnselect;
				entity.pridigCheck = entityZero.pridigCheck;
				entity.regisInsuff = entityZero.regisInsuff;
				entity.regisLackPubHd = entityZero.regisLackPubHd;
				entity.regisNumYear = entityZero.regisNumYear;
				entity.regisShortLostHd = entityZero.regisShortLostHd;
				entity.regisShortReser = entityZero.regisShortReser;
				entity.use60h = entityZero.use60h;
				entity.useGener = entityZero.useGener;
				entity.useYear = entityZero.useYear;
				entity.wrkHours = entityZero.wrkHours;
				this.commandProxy().insert(entity);
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
		
	}

}
