package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCorrectionLogCommand {
	
	private List<IntegrationOfDaily> domainOld;
	
	private List<IntegrationOfDaily> domainNew;

}
