package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpInsReportTxtSettingCsvExportService extends ExportService<EmpInsReportSettingExportQuery> {
	
	@Inject
	private EmpInsReportTxtSettingCsvGenerator csvGenerator;
	
	@Inject
	private EmpInsReportSettingRepository empInsReportSettingRepo;
	
	@Inject
	private EmpInsReportTxtSettingRepository empInsReportTxtSettingRepo;
	
	@Inject
	private AddEmpInsRptSettingCommandHandler addEmpInsRptSttHandler;
	
	@Inject
	private UpdateEmpInsRptSettingCommandHandler updateEmpInsRptSttHandler;
	
	@Inject
	private AddEmpInsRptTxtSettingCommandHandler addEmpInsRptTxtSttHandler;
	
	@Inject
	private UpdateEmpInsRptTxtSettingCommandHandler updateEmpInsRptTxtSttHandler;

	@Override
	protected void handle(ExportServiceContext<EmpInsReportSettingExportQuery> context) {
		String companyId = AppContexts.user().companyId();
		String userId = AppContexts.user().userId();
		EmpInsReportSettingExportQuery query = context.getQuery();
		
		// 雇用保険届設定更新処理
		if (empInsReportSettingRepo.getEmpInsReportSettingById(companyId, userId).isPresent()) {
			// 雇用保険届作成設定を更新する
			updateEmpInsRptSttHandler.handle(query.getEmpInsReportSettingCommand());
		} else {
			// 雇用保険届作成設定を追加する
			addEmpInsRptSttHandler.handle(query.getEmpInsReportSettingCommand());
		}
		if (empInsReportTxtSettingRepo.getEmpInsReportTxtSettingByUserId(companyId, userId).isPresent()) {
			updateEmpInsRptTxtSttHandler.handle(query.getEmpInsReportTxtSettingCommand());
		} else {
			addEmpInsRptTxtSttHandler.handle(query.getEmpInsReportTxtSettingCommand());
		}
		
		// 雇用保険被保険者資格喪失届テキスト出力処理
		csvGenerator.generate(context.getGeneratorContext(), null);
	}

}
