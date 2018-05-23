package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayOffManagement;

@Getter
@AllArgsConstructor
public class PayoutSubofHDManagementCommand {
	
	private String payoutID;
	
	private String sid;
	
	private Double remainNumber;
	
	private List<DayOffManagement> subofHD;
}
