package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class AffHistoryExport {

	private List<AffJobTitleHistoryExport> jobTitleHistoryExports;

	private List<AffWorkplaceHistoryItemExport> historyItems;

}
