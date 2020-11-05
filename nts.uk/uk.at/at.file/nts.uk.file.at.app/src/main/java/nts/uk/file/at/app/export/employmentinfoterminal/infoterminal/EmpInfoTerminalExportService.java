package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpInfoTerminalExportService extends ExportService<List<EmpInfoTerminalExportDataSource>>{
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private EmpInfoTerminalExport empInfoTerminalExport;

	@Override
	protected void handle(ExportServiceContext<List<EmpInfoTerminalExportDataSource>> context) {
		List<EmpInfoTerminalExportDataSource> dataSource = context.getQuery();
		
		empInfoTerminalExport.export(context.getGeneratorContext(), dataSource);
	}

	//就業情報端末のマスタリストを作成する
	public List<EmpInfoTerminalExportDataSource> getData(){
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		
		//1: get*(ログイン者の契約コード): 就業情報端末(List)
		List<EmpInfoTerminal> empInfoTerminalList = empInfoTerminalRepository.get(new ContractCode(contractCode));
		
		List<String> workLocationCDList = empInfoTerminalList.stream()
				.map(m->m.getCreateStampInfo().getWorkLocationCd().isPresent()?m.getCreateStampInfo().getWorkLocationCd().get().v():null)
				.filter(m->m!=null)
				.distinct().collect(Collectors.toList());
		
		//2: get(＜List＞端末情報.設置場所コード): List< 勤務場所>
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
		return dataSource;
	}
}
