package nts.uk.file.at.ws.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportDataSource;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author huylq
 *
 */
@Path("file/empInfoTerminal/report")
@Produces("application/json")
public class EmpInfoTerminalExportWS extends WebService {
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private EmpInfoTerminalExportService empInfoTerminalExportService;
	
	@POST
	@Path("export")
	public ExportServiceResult generate() {
		String contractCode = AppContexts.user().contractCode();
		List<EmpInfoTerminal> empInfoTerminalList = empInfoTerminalRepository.get(new ContractCode(contractCode));
		/**
		 * QA
		 */
		/*List<WorkLocation> workLocationList = empInfoTerminalList.stream().map(m->m.getCreateStampInfo().getWorkLocationCd()).distinct().collect(Collectors.toList());
		  foreach empInfoTerminalList
			foreach workLocationList
		 		if empInfoTerminalList.workLocationCd = workLocationList.workLocationCd
		 			EmpInfoTerminalExportDatasource.convertToDatasource(empTe, workLoca);
		 		
		 */
		List<EmpInfoTerminalExportDataSource> dataSource = null;
		return this.empInfoTerminalExportService.start(dataSource);
	}
}
