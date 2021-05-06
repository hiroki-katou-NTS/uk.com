package nts.uk.ctx.at.aggregation.app.command.schedulecounter.initcompanyinfo;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompanyInfoRegisterCommand {
	
	private List<CriterionAmountByNoCommand> months = Collections.emptyList();
	
	private List<CriterionAmountByNoCommand> years = Collections.emptyList();
	
	private List<Integer> framNos = Collections.emptyList();
}
