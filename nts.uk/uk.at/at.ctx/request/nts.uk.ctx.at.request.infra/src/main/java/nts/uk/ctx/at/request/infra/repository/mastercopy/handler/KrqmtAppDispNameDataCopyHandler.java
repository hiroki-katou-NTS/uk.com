package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtAppDispName;
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtAppDispNamePK;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class KrqmtAppDispNameDataCopyHandler.
 */

/**
 * Gets the insert query.
 *
 * @return the insert query
 */
@Getter

/**
 * Sets the insert query.
 *
 * @param INSERT_QUERY the new insert query
 */
@Setter

/**
 * Instantiates a new krqmt app disp name data copy handler.
 */
@NoArgsConstructor

/**
 * Instantiates a new krqmt app disp name data copy handler.
 *
 * @param copyMethod the copy method
 * @param companyId the company id
 * @param INSERT_QUERY the insert query
 */
@AllArgsConstructor
public class KrqmtAppDispNameDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT c FROM KrqmtAppDispName c WHERE c.krqmtAppDispNamePK.companyId = :companyId";
	
	/**
	 * Instantiates a new krqmt app disp name data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqmtAppDispNameDataCopyHandler(CopyMethod copyMethod, String companyId) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		// Get all company zero data
		List<KrqmtAppDispName> entityZeroData = this.queryProxy().query(SELECT_BY_CID, KrqmtAppDispName.class)
				.setParameter("companyId", AppContexts.user().zeroCompanyIdInContract())
				.getList();
		
		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			List<KrqmtAppDispName> oldEntities = this.queryProxy().query(SELECT_BY_CID, KrqmtAppDispName.class)
			.setParameter("companyId", this.companyId)
			.getList();
			this.commandProxy().removeAll(oldEntities);
		case ADD_NEW:
			// Insert Data
			List<KrqmtAppDispName> newEntities = entityZeroData.stream()
					.map(zeroData -> new KrqmtAppDispName(new KrqmtAppDispNamePK(this.companyId, zeroData.krqmtAppDispNamePK.appType), zeroData.dispName))
					.collect(Collectors.toList());
			this.commandProxy().insertAll(newEntities);
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
		
	}

}
