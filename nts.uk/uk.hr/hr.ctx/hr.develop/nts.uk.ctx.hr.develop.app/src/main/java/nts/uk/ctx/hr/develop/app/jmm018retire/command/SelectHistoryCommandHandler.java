package nts.uk.ctx.hr.develop.app.jmm018retire.command;

import javax.ejb.Stateless;

import nts.uk.ctx.hr.develop.app.jmm018retire.dto.StartDto;

@Stateless
public class SelectHistoryCommandHandler{

	public void selcectHistory(StartDto startdto, String hist) {
		if(startdto.getRelateMaster() == null) {
			
		}
	}
}
