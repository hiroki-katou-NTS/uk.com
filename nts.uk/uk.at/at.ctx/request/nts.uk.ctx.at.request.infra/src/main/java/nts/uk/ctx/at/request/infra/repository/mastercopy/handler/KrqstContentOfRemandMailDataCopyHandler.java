package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.remandsetting.KrqstContentOfRemandMail;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.remandsetting.KrqstContentOfRemandMailPk;

/**
 * The Class KrqstContentOfRemandMailDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstContentOfRemandMailDataCopyHandler extends JpaRepository implements DataCopyHandler {
	
	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqstContentOfRemandMail f";
    
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.remandMailPk.cid =:cid ";

	/**
	 * Instantiates a new krqst content of remand mail data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstContentOfRemandMailDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		Optional<ContentOfRemandMail> entityZeroData = this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstContentOfRemandMail.class)
        .setParameter("cid", this.companyId).getSingle(c->c.toDomain());

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			this.commandProxy().remove(KrqstContentOfRemandMail.class, new KrqstContentOfRemandMailPk(this.companyId)); 
		case ADD_NEW:
			// Insert Data
			ContentOfRemandMail domain = new ContentOfRemandMail(this.companyId, entityZeroData.get().getMailTitle().toString(), entityZeroData.get().getMailBody().toString());
		    this.commandProxy().insert(KrqstContentOfRemandMail.toEntity(domain));
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}

	}

}
