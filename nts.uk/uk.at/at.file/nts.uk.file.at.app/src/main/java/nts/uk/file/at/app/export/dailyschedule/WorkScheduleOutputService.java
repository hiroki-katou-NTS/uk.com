package nts.uk.file.at.app.export.dailyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.shr.com.context.AppContexts;

public class WorkScheduleOutputService extends ExportService<WorkScheduleOutputQuery> {
	
	@Inject
	private WorkScheduleOutputGenerator generator;
	
	@Inject 
	private WorkScheduleOutputConditionRepository workScheRepo;
	
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemRepo;
	
	@Inject
	private WorkplaceConfigInfoRepository workplaceRepo;

	@Override
	public void handle(ExportServiceContext<WorkScheduleOutputQuery> context) {
		
		// Get data query
		WorkScheduleOutputQuery query = context.getQuery();
		
		// get companycode by user login
		String companyId = AppContexts.user().companyId();
		
		
		// ドメインモデル「日別勤務表の出力項目」を取得する
		Optional<OutputItemDailyWorkSchedule> optOutputItemDailyWork = outputItemRepo.findByCode(query.getOutputCode());
		if (!optOutputItemDailyWork.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_1141"));
		}
		
		// 選択した社員リストをもとにアルゴリズム「職場IDから職場と階層コードを取得する」を実行する
		Optional<WorkplaceConfigInfo> optWorkplace = workplaceRepo.find(companyId, query.getEndDate(), query.getWorkplaceId());
		
		// ユーザ固有情報「日別勤務表の出力条件」を更新する
		Optional<WorkScheduleOutputCondition> optWkScheOutCon = workScheRepo.findByCid(companyId, query.getUserId());
		
		
		WorkScheduleOutputCondition condition;
		if (optWkScheOutCon.isPresent()) {
			// ユーザ固有情報「日別勤務表の出力条件」を更新する
			condition = optWkScheOutCon.get();
		}
		else {
			// ユーザ固有情報「日別勤務表の出力条件」を新規追加する
			condition = new WorkScheduleOutputCondition();
		}
		condition = updateOutputCondition(condition, query);
		
		
		
		generator.generate(condition, optWorkplace.get(), query);
	}
	
	// Currently dummy data for exporting excel
	public static WorkScheduleOutputCondition updateOutputCondition(WorkScheduleOutputCondition condition, WorkScheduleOutputQuery query) {
		condition.setCompanyId(new CompanyId("Company"));
		condition.setUserId("User");
		condition.setConditionSetting(OutputConditionSetting.USE_CONDITION);
		List<OutputItemSettingCode> lstCode = new ArrayList<>();
		// Dummy 48 items
		for (int i = 1; i <= 48; i++) {
			lstCode.add(new OutputItemSettingCode(i));
		}
		condition.setCode(lstCode);
		return condition;
	}
	
	
}
