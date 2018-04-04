package nts.uk.ctx.pereg.app.command.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.specialholiday.SHDomainEventTest;

@Stateless
public class SpecialHDEventHandler {

	@Inject
	private SHDomainEventTest test;
	
	public void handler(SpecialHolidayTestDto obj){
		test.handlerTest(obj.isEffective(), obj.getName(), obj.getCode());
	}
}
