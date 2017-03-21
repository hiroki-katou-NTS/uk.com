package nts.uk.ctx.basic.app.command.organization.position;





import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Getter
@Setter
public class UpdateHistoryCommand {

	private GeneralDate endDate;

	private String companyCode;

	private String historyId;

	private GeneralDate startDate; 
	

}
