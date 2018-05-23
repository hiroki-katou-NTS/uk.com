package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayOffManagement;

@Getter
public class PayoutSubofHDManagementCommand {
	
	private String payoutID;
	
	private String sid;
	
	private List<DayOffManagement> subofHD;
}
