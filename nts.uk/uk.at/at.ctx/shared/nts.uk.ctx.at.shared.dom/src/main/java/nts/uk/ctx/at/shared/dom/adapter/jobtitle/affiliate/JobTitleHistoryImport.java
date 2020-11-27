package nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JobTitleHistoryImport {

	private List<AffJobTitleHistoryImport> histories;

	private List<AffJobTitleHistoryItemImport> historyItems;

}
