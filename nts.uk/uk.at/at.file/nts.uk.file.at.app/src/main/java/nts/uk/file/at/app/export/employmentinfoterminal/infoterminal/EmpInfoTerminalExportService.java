package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSetRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.就業情報端末のマスタリストを作成する.就業情報端末のマスタリストを作成する
 * @author huylq
 *
 */
@Stateless
public class EmpInfoTerminalExportService extends ExportService<List<EmpInfoTerminalExportDataSource>>{
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private EmpInfoTerminalExport empInfoTerminalExport;
	
	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private DeclareSetRepository declareSetRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

	@Override
	protected void handle(ExportServiceContext<List<EmpInfoTerminalExportDataSource>> context) {
		String companyId = AppContexts.user().companyId();
		List<EmpInfoTerminalExportDataSource> dataSource = context.getQuery();
		
		// get 残業枠枠 with 会社ID、使用区分＝する
		List<OvertimeWorkFrame> overtimeWorkFrames = overtimeWorkFrameRepository
						.getOvertimeWorkFrameByFrameByCom(companyId, 1);

		// get 休出枠 with ログイン会社ID、使用区分＝する
		List<WorkdayoffFrame> workdayoffFrames = workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		
		empInfoTerminalExport.export(context.getGeneratorContext(), dataSource, this.getDeclareSet(), overtimeWorkFrames, workdayoffFrames);
	}

	//	就業情報端末のマスタリストを作成する
	public List<EmpInfoTerminalExportDataSource> getData(){
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		GeneralDate systemDate = GeneralDate.today();
		
		//1: get*(ログイン者の契約コード): 就業情報端末(List)
		List<EmpInfoTerminal> empInfoTerminalList = empInfoTerminalRepository.get(new ContractCode(contractCode));
		
		List<String> workLocationCDList = empInfoTerminalList.stream()
				.map(m -> m.getCreateStampInfo().getWorkLocationCd().isPresent() ? m.getCreateStampInfo().getWorkLocationCd().get().v() : null)
				.filter(m -> m != null)
				.distinct().collect(Collectors.toList());
		
		//2: get(＜List＞端末情報.設置場所コード): List< 勤務場所>
		List<WorkLocation> workLocationList = workLocationRepository.findByCodes(contractCode, workLocationCDList);
		
		List<String> listWorkPlaceId = empInfoTerminalList.stream().filter(e -> e.getCreateStampInfo().getWorkPlaceId().isPresent())
														  .map(e -> e.getCreateStampInfo().getWorkPlaceId().get().v())
														  .collect(Collectors.toList());
		
		//2.1: get*(ログイン会社ID、List<職場ID>、システム日付): List<対象職場>
		List<TargetWorkplace> listTargetWorkPlace = workplacePub.getWorkplaceInforByWkpIds(companyId, listWorkPlaceId, systemDate)
														.stream()
														.map(e -> new TargetWorkplace(e.getWorkplaceId(), e.getWorkplaceCode(), e.getWorkplaceName()))
														.collect(Collectors.toList());

		List<EmpInfoTerminalExportDataSource> dataSource = empInfoTerminalList.stream().map(empInfoTerminal -> {
			Optional<WorkLocation> workLocation = workLocationList.stream()
					.filter(wl -> empInfoTerminal.getCreateStampInfo().getWorkLocationCd().isPresent()
					&& empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v().equals(wl.getWorkLocationCD().v())).findFirst();
			
			if (empInfoTerminal.getCreateStampInfo().getWorkPlaceId().isPresent()) {
				TargetWorkplace targetWorkplace = listTargetWorkPlace.stream().filter(e -> e.getWorkplaceId() == empInfoTerminal.getCreateStampInfo().getWorkPlaceId().get().v()).findFirst().get();
				
				return workLocation.isPresent() ? EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, workLocation.get(), targetWorkplace) : 
					EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, null, targetWorkplace);
			} else {
				return workLocation.isPresent() ? EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, workLocation.get(), new TargetWorkplace()) : 
					EmpInfoTerminalExportDataSource.convertToDatasource(empInfoTerminal, null, new TargetWorkplace());
			}
			
		}).collect(Collectors.toList());
		
		return dataSource;
	}
	
	// Get Declaset()
	public DeclareSet getDeclareSet() {
		String companyId = AppContexts.user().companyId();
		Optional<DeclareSet> optionalDeclareSet = declareSetRepository.find(companyId);
		if(optionalDeclareSet.isPresent()) {
			return optionalDeclareSet.get();
		}
		return new DeclareSet(companyId);
	}
}
