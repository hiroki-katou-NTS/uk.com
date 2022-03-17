package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * 
 * @author huylq
 *
 */
public interface EmpInfoTerminalExport {

	void export(FileGeneratorContext generatorContext, List<EmpInfoTerminalExportDataSource> dataSource,
			DeclareSet declareSet, List<OvertimeWorkFrame> overtimeWorkFrames, List<WorkdayoffFrame> workdayoffFrames);
}
