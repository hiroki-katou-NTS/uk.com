package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.OfficeCls;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOfficeRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsQualifyInfoQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

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

	@Inject
	private EmpInsHistRepository empInsHistRepo;

	@Inject
	private EmpInsQualifyInfoQuery empInsQualifyInfoQuery;

	@Inject
	private EmpInsLossInfoRepository empInsLossInfoRepo;

	@Inject
	private EmpInsNumInfoRepository empInsNumInfoRepo;

	@Inject
	private CompanyInforAdapter companyInforAdapter;

	@Inject
	private EmpInsOfficeRepository empInsOfficeRepo;

	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepo;

	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;

	@Inject
	private PersonExportAdapter personExportAdapter;

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
		EmpInsReportSettingExportData exportData = new EmpInsReportSettingExportData();
		exportData.setEmployeeIds(query.getEmployeeIds());
		exportData.setCreateDate(query.getFillDate());
		CompanyInfor company = companyInforAdapter.getCompanyNotAbolitionByCid(companyId);
		exportData.setCompanyInfo(company);
		exportData.setEmpInsReportSetting(query.getEmpInsReportSettingCommand());
		exportData.setEmpInsReportTxtSetting(query.getEmpInsReportTxtSettingCommand());
		this.getLossEmpInsuranceInfo(exportData, companyId, query.getEmployeeIds(), query.getStartDate(),
				query.getEndDate(), query.getEmpInsReportSettingCommand().getOfficeClsAtr());
		csvGenerator.generate(context.getGeneratorContext(), exportData);
	}

	private void getLossEmpInsuranceInfo(EmpInsReportSettingExportData exportData, String companyId,
			List<String> employeeIds, GeneralDate startDate, GeneralDate endDate, int officeCls) {
		if (startDate.after(endDate)) {
			throw new BusinessException("Msg_812");
		}
		List<EmpInsHist> employeeInsuranceHistories = empInsHistRepo.getByEmpIdsAndPeriod(employeeIds, startDate,
				endDate);
		if (employeeInsuranceHistories.isEmpty()) {
			throw new BusinessException("MsgQ_51");
		}

		Map<String, EmpInsLossInfo> empInsLossInfos = empInsLossInfoRepo.getByEmpIds(companyId, employeeIds)
				.stream().collect(Collectors.toMap(i -> i.getSId(), i -> i));
		exportData.setEmpInsLossInfoMap(empInsLossInfos);
		Map<String, EmployeeInfoEx> employeeInfos = employeeInfoAdapter.findBySIds(employeeIds).stream()
				.collect(Collectors.toMap(i -> i.getEmployeeId(), i -> i));
		exportData.setEmployeeInfoMap(employeeInfos);
		Map<String, CurrentPersonResidence> currentPersonAddressList = CurrentPersonResidence
				.createListPerson(personExportAdapter.findByPids(
						employeeInfos.entrySet().stream().map(e -> e.getValue().getPId()).collect(Collectors.toList())))
				.stream().collect(Collectors.toMap(i -> i.getPId(), i -> i));
		exportData.setCurrentPersonAddressMap(currentPersonAddressList);
		Map<String, EmpInsNumInfo> empInsNumInfos = new HashMap<>();
		Map<String, LaborInsuranceOffice> laborInsuranceOffices = new HashMap<>();
		Map<String, DateHistoryItem> empInsHists = new HashMap<>();
		Map<String, ForeignerResHistInfo> foreignerResHistInfors = new HashMap<>();
		for (String employeeId : employeeIds) {
			DateHistoryItem history = empInsQualifyInfoQuery.getEmployeeInsuranceHistoryId(employeeId, startDate,
					endDate, 1);
			if (history == null) {
				continue;
			}
			empInsHists.put(employeeId, history);
//			EmpInsLossInfo empInsLossInfo = empInsLossInfos.get(employeeId);
			EmpInsNumInfo empInsNumInfo = empInsNumInfoRepo
					.getEmpInsNumInfoById(companyId, employeeId, history.identifier()).orElse(null);
			empInsNumInfos.put(employeeId, empInsNumInfo);
			if (officeCls == OfficeCls.OUTPUT_COMPANY.value) {
//				CompanyInfor company = companyInforAdapter.getCompanyNotAbolitionByCid(companyId);
			}
			if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value) {
				Optional<EmpInsOffice> empInsOffice = empInsOfficeRepo.getEmpInsOfficeById(companyId, employeeId,
						history.identifier());
				if (empInsOffice.isPresent()) {
					LaborInsuranceOffice laborInsuranceOffice = laborInsuranceOfficeRepo
							.getLaborInsuranceOfficeById(empInsOffice.get().getLaborInsCd().v()).orElse(null);
					laborInsuranceOffices.put(employeeId, laborInsuranceOffice);
				}
			}
			ForeignerResHistInfo dummyForResHistInfo = new ForeignerResHistInfo("", 1, 1,
					GeneralDate.fromString("2015/01/01", "yyy/MM/dd"),
					GeneralDate.fromString("2019/01/01", "yyy/MM/dd"), "高度専門職", "ベトナム");
			foreignerResHistInfors.put(employeeId, dummyForResHistInfo);
//			EmployeeInfoEx employeeInfo = employeeInfos.get(employeeId);
		}
		exportData.setEmpInsHistMap(empInsHists);
		exportData.setEmpInsNumInfoMap(empInsNumInfos);
		exportData.setLaborInsuranceOfficeMap(laborInsuranceOffices);
		exportData.setForeignerResHistInforMap(foreignerResHistInfors);
	}

}
