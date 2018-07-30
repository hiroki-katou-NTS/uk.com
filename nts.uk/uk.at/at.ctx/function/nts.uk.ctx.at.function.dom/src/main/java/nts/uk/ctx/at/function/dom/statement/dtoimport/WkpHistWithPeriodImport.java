package nts.uk.ctx.at.function.dom.statement.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WkpHistWithPeriodImport {
	/** The wkp code. */
	// 職場ID
	private String wkpId;

	/** The wkp display name. */
	// 職場情報履歴一覧
	private List<WkpInfoHistImport> wkpInfoHistLst;
}
