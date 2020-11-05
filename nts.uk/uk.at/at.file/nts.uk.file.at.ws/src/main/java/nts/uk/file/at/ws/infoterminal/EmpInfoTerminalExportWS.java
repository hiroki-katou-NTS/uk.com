package nts.uk.file.at.ws.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
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
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private EmpInfoTerminalExportService empInfoTerminalExportService;
	
	@POST
	@Path("export")
	public ExportServiceResult generate() {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<EmpInfoTerminal> empInfoTerminalList = empInfoTerminalRepository.get(new ContractCode(contractCode));
		/**
		 * QA
		 */
		List<String> workLocationCDList = empInfoTerminalList.stream()
				.map(m->m.getCreateStampInfo().getWorkLocationCd().isPresent()?m.getCreateStampInfo().getWorkLocationCd().get().v():null)
				.filter(m->m!=null)
				.distinct().collect(Collectors.toList());
		
		List<WorkLocation> workLocationList = workLocationRepository.findByCodes(companyId, workLocationCDList);
		
		List<EmpInfoTerminalExportDataSource> dataSource = new ArrayList<EmpInfoTerminalExportDataSource>();
		
		for (EmpInfoTerminal empInfoTerminal : empInfoTerminalList) {
			boolean hasWorkLocation = false;
			for (WorkLocation workLocation : workLocationList) {
				if(empInfoTerminal.getCreateStampInfo().getWorkLocationCd().isPresent()
						&&empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v().equals(workLocation.getWorkLocationCD().v())) {
					EmpInfoTerminalExportDataSource data = EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, workLocation);
					dataSource.add(data);
					hasWorkLocation = true;
					break;
				}
			}
			if(!hasWorkLocation) {
				EmpInfoTerminalExportDataSource data = EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, null);
				dataSource.add(data);
			}
		}		
		 
		
//		for(int i=0; i<10; i++) {
//			EmpInfoTerminalExportDataSource data = new EmpInfoTerminalExportDataSource("000"+i, ""+i, "NRL-1", "mac", "ip", "serial", "wlCode", "wlName", i, "〇", "置き換えなし", "replace", "reason", "〇", "longgggggg gggggg ggggggggggggg gggggg ggggggggggggggg memo");
//			if(i%2==0) {
//				data = new EmpInfoTerminalExportDataSource("000"+i, ""+i, "NRL-1", "mac", "", "serial", "wlCode", "wlName", i, "ー", "置き換えなし", "replace", "reason", "ー", "memo");
//			}
//			dataSource.add(data);
//		}
		return this.empInfoTerminalExportService.start(dataSource);
	}
}
