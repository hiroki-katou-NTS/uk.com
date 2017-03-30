package nts.uk.ctx.basic.app.command.organization.position;





import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateHistoryCommand {

	private JobHist jobHist;

	private String checkUpdate;

	private String checkDelete;
	

}
