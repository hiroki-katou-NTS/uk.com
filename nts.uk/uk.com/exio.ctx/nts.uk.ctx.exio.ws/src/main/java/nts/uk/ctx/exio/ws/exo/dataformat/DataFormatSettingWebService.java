package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddAtWorkClsDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddCharacterDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddDateDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddInstantTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddNumberDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AddTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AtWorkClsDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.CharacterDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.DateDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.InstantTimeDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.NumberDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.TimeDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateAtWorkClsDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateCharacterDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateDateDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateInstantTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateNumberDfsCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.UpdateTimeDfsCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.AtWorkClsDfsDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.AtWorkClsDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.CharacterDfsDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.CharacterDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.DateDfsDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.DateDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.InstantTimeDfsDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.InstantTimeDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.NumberDfsDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.NumberDfsFinder;
import nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting.TimeDfsDto;
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

	@POST
	@Path("getAtWorkClsDfs/{cndSetCd}/{outItemCode}")
	public AtWorkClsDfsDto getAtWorkClsDfs(@PathParam("cndSetCd") String cndSetCd,
			@PathParam("outItemCode") String outItemCode) {
		return this.atWorkClsDfsFinder.getAtWorkClsDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("getCharacterDfs/{cndSetCd}/{outItemCode}")
	public CharacterDfsDto getCharacterDfs(@PathParam("cndSetCd") String cndSetCd,
			@PathParam("outItemCode") String outItemCode) {
		return this.characterDfsFinder.getCharacterDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("getDateDfs/{cndSetCd}/{outItemCode}")
	public DateDfsDto getDateDfs(@PathParam("cndSetCd") String cndSetCd, @PathParam("outItemCode") String outItemCode) {
		return this.dateDfsFinder.getDateDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("getInstantTimeDfs/{cndSetCd}/{outItemCode}")
	public InstantTimeDfsDto getInstantTimeDfs(@PathParam("cndSetCd") String cndSetCd,
			@PathParam("outItemCode") String outItemCode) {
		return this.instantTimeDfsFinder.getInstantTimeDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("getNumberDfs/{cndSetCd}/{outItemCode}")
	public NumberDfsDto getNumberDfs(@PathParam("cndSetCd") String cndSetCd,
			@PathParam("outItemCode") String outItemCode) {
		return this.numberDfsFinder.getNumberDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("getTimeDfs/{cndSetCd}/{outItemCode}")
	public TimeDfsDto getTimeDfs(@PathParam("cndSetCd") String cndSetCd, @PathParam("outItemCode") String outItemCode) {
		return this.timeDfsFinder.getTimeDfs(cndSetCd, outItemCode);
	}

	@POST
	@Path("addAtWorkClsDfs")
	public void addAtWorkClsDfs(AtWorkClsDfsCommand comand) {
		this.addAtWorkClsDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("addCharacterDfs")
	public void addCharacterDfs(CharacterDfsCommand comand) {
		this.addCharacterDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("addDateDfs")
	public void addDateDfs(DateDfsCommand comand) {
		this.addDateDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("addInstantTimeDfs")
	public void addInstantTimeDfs(InstantTimeDfsCommand comand) {
		this.addInstantTimeDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("addNumberDfs")
	public void addNumberDfs(NumberDfsCommand comand) {
		this.addNumberDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("addTimeDfs")
	public void addTimeDfs(TimeDfsCommand comand) {
		this.addTimeDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateAtWorkClsDfs")
	public void updateAtWorkClsDfs(AtWorkClsDfsCommand comand) {
		this.updateAtWorkClsDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateCharacterDfs")
	public void updateCharacterDfs(CharacterDfsCommand comand) {
		this.updateCharacterDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateDateDfs")
	public void updateDateDfs(DateDfsCommand comand) {
		this.updateDateDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateInstantTimeDfs")
	public void updateInstantTimeDfs(InstantTimeDfsCommand comand) {
		this.updateInstantTimeDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateNumberDfs")
	public void updateNumberDfs(NumberDfsCommand comand) {
		this.updateNumberDfsCommandHandler.handle(comand);
	}

	@POST
	@Path("updateTimeDfs")
	public void updateTimeDfs(TimeDfsCommand comand) {
		this.updateTimeDfsCommandHandler.handle(comand);
	}
}
