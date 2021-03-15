package nts.uk.cnv.dom.td.event;

import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AltarationSummary;

/**
 * 発注処理結果
 * @author ai_muto
 *
 */
public class OrderedResult {
	private List<AltarationSummary> errorList;
	private Optional<AtomTask> atomTask;

}
