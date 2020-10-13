package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryCategoryCommand {
	public String categoryId;
	public String endOfPeriod;
	public String startOfPeriod;
	public String tableNo;
	public String dataStorageProcessingId;
}
