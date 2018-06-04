package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubOfHDManagement;

@Getter
@AllArgsConstructor
public class SubOfHDPayoutManaDataCommand {
	
	private String subOfHDID;
	
	private String sid;
	
	private Double remainNumber;
	
	private List<SubOfHDManagement> payout;
}
