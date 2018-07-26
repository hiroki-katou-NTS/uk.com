package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddAtWorkClsDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddCharacterDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddDateDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddInstantTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddNumberDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateAtWorkClsDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateCharacterDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateDateDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateInstantTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateNumberDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.AtWorkClsDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.CharacterDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.DateDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.InstantTimeDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.NumberDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.TimeDfsFinder;

@Path("exio/exo/dataformatsetting")
@Produces("application/json")
public class DataFormatSettingWebService extends WebService {
	@Inject
	private AddAtWorkClsDfsCommandHandler addAtWorkClsDfsCommandHandler;
	@Inject
	private AddCharacterDfsCommandHandler addCharacterDfsCommandHandler;
	@Inject
	private AddDateDfsCommandHandler addDateDfsCommandHandler;
	@Inject
	private AddInstantTimeDfsCommandHandler addInstantTimeDfsCommandHandler;
	@Inject
	private AddNumberDfsCommandHandler addNumberDfsCommandHandler;
	@Inject
	private AddTimeDfsCommandHandler addTimeDfsCommandHandler;
	@Inject
	private UpdateAtWorkClsDfsCommandHandler updateAtWorkClsDfsCommandHandler;
	@Inject
	private UpdateCharacterDfsCommandHandler updateCharacterDfsCommandHandler;
	@Inject
	private UpdateDateDfsCommandHandler updateDateDfsCommandHandler;
	@Inject
	private UpdateInstantTimeDfsCommandHandler updateInstantTimeDfsCommandHandler;
	@Inject
	private UpdateNumberDfsCommandHandler updateNumberDfsCommandHandler;
	@Inject
	private UpdateTimeDfsCommandHandler updateTimeDfsCommandHandler;
	@Inject
	private AtWorkClsDfsFinder atWorkClsDfsFinder;
	@Inject
	private CharacterDfsFinder characterDfsFinder;
	@Inject
	private DateDfsFinder dateDfsFinder;
	@Inject
	private InstantTimeDfsFinder instantTimeDfsFinder;
	@Inject
	private NumberDfsFinder numberDfsFinder;
	@Inject
	private TimeDfsFinder timeDfsFinder;

}
