package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.knr.knr002.c.UpdateRemoteSetCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.c.UpdateRemoteSetCommandHandler;
import nts.uk.screen.at.app.query.knr.knr002.L.GetProductionSwitchDateSC;

@Stateless
public class UpdateRemoteSettingSc {

	@Inject
	private UpdateRemoteSetCommandHandler commandHandler;
	
	public void updateRemoteSetting(UpdateInput input) {
		
		List<String> listEmpInfoTerminalCode = input.getListEmpTerminalCode();
		
		for (String code : listEmpInfoTerminalCode) {
			commandHandler.handle(new UpdateRemoteSetCommand(code));
		}
	}
}
