package nts.uk.file.at.app.export.statement.requestlist422;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WkpHistWithPeriodExport {
	/** The wkp code. */
	// 職場ID
	private String wkpId;

	/** The wkp display name. */
	// 職場情報履歴一覧
	private List<WkpInfoHistExport> wkpInfoHistLst;
}
