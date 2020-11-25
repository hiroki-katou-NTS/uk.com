package nts.uk.ctx.at.record.ws.workrecord.goout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.goout.OutManageCommand;
import nts.uk.ctx.at.record.app.command.workrecord.goout.OutManageCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.goout.OutManageDto;
import nts.uk.ctx.at.record.app.find.workrecord.goout.OutManageFinder;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * The Class OutManageWS.
 *
 * @author hoangdd
 */
@Path("at/shared/workrecord/goout")
@Produces(MediaType.APPLICATION_JSON)
public class OutManageWS extends WebService{
	
	/** The finder. */
	@Inject
	private OutManageFinder finder;
	
	/** The save handler. */
	@Inject
	private OutManageCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the out manage dto
	 */
	@Path("find")
	@POST
	public OutManageDto findData() {
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(OutManageCommand command) {
		this.saveHandler.handle(command);
	}

	/**
	 * Gets the go out reason enum.
	 *
	 * @return the go out reason enum
	 */
	@Path("enum/gooutreason")
	@POST
	public List<EnumConstant> getGoOutReasonEnum(){
		return EnumAdaptor.convertToValueNameList(GoingOutReason.class);
	}
}

