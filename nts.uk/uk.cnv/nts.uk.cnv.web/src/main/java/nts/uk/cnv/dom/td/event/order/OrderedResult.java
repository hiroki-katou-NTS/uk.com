package nts.uk.cnv.dom.td.event.order;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

/**
 * 発注処理結果
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class OrderedResult {
	private List<AlterationSummary> errorList;
	private Optional<AtomTask> atomTask;

	public boolean hasError() {
		return (this.errorList.size() > 0);
	}
}
