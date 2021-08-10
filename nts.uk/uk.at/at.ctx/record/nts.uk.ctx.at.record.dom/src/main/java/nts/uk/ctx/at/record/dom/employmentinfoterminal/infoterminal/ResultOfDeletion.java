package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;

/**
 * 削除の結果Temporary									
 * @author dungbn
 *
 */
@AllArgsConstructor
@Getter
public class ResultOfDeletion {
	
	boolean isError;
	
	Optional<AtomTask> deleteEmpInfoTerminal;
	
	Optional<AtomTask> deleteEmpInfoTerminalComStatus;
}
