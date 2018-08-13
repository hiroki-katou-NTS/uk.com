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
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtHdAppDispName;
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtHdAppDispNamePK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqmtHdAppDispNameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqmtHdAppDispNameDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "";

	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID = "SELECT c FROM KrqmtHdAppDispName c WHERE c.krqmtHdAppDispNamePK.companyId = :companyId";

	/**
	 * Instantiates a new krqmt hd app disp name data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqmtHdAppDispNameDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		List<KrqmtHdAppDispName> entityZeroData = this.queryProxy().query(SELECT_BY_CID, KrqmtHdAppDispName.class)
				.setParameter("companyId", AppContexts.user().zeroCompanyIdInContract())
				.getList();
		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			List<KrqmtHdAppDispName> oldEntities = this.queryProxy().query(SELECT_BY_CID, KrqmtHdAppDispName.class)
			.setParameter("companyId", this.companyId)
			.getList();
			this.commandProxy().removeAll(oldEntities);
		case ADD_NEW:
			// Insert Data
			List<KrqmtHdAppDispName> newEntities = entityZeroData.stream()
					.map(zeroData -> new KrqmtHdAppDispName(new KrqmtHdAppDispNamePK(this.companyId, zeroData.krqmtHdAppDispNamePK.hdAppType),zeroData.dispName))
					.collect(Collectors.toList());
			this.commandProxy().insertAll(newEntities);
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}

	}

}
