package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOfficeRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.AddEmpInsRptSettingCommandHandler;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.AddEmpInsRptTxtSettingCommandHandler;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.UpdateEmpInsRptSettingCommandHandler;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.UpdateEmpInsRptTxtSettingCommandHandler;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsOutOrder;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOfficeRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class EmpInsReportTxtSettingCsvExportService extends ExportService<EmpInsLossInfoExportQuery> {

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

	@Inject
	private PublicEmploymentSecurityOfficeRepository pubEmpSecurityOfficeRepo;

	@Override
	protected void handle(ExportServiceContext<EmpInsLossInfoExportQuery> context) {
		String companyId = AppContexts.user().companyId();
		String userId = AppContexts.user().userId();
		EmpInsLossInfoExportQuery query = context.getQuery();

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
		exportData.setCreateDate(query.getFillingDate());
		CompanyInfor company = companyInforAdapter.getCompanyNotAbolitionByCid(companyId);
		exportData.setCompanyInfo(company);
		exportData.setEmpInsReportSetting(query.getEmpInsReportSettingCommand());
		exportData.setEmpInsReportTxtSetting(query.getEmpInsReportTxtSettingCommand());
		this.getLossEmpInsuranceInfo(exportData, companyId, query.getEmployeeIds(), query.getStartDate(),
				query.getEndDate(), query.getEmpInsReportSettingCommand().getOfficeClsAtr());
		EmpInsOutOrder outputOrder = EnumAdaptor.valueOf(query.getEmpInsReportSettingCommand().getOutputOrderAtr(),
				EmpInsOutOrder.class);
		switch (outputOrder) {
		case INSURANCE_NUMBER:
			exportData.getRowsData().stream()
					.sorted((r1, r2) -> r1.getInsuranceNumber().compareTo(r2.getInsuranceNumber()))
					.collect(Collectors.toList());
			break;
		case DEPARTMENT_EMPLOYEE:
			break;
		case EMPLOYEE_CODE:
			exportData.getRowsData().stream().sorted((r1, r2) -> r1.getEmployeeCode().compareTo(r2.getEmployeeCode()))
					.collect(Collectors.toList());
			break;
		case EMPLOYEE:
			exportData.getRowsData().stream()
					.sorted((r1, r2) -> r1.getPersonNameKana().compareTo(r2.getPersonNameKana()))
					.collect(Collectors.toList());
			break;
		}
		csvGenerator.generate(context.getGeneratorContext(), exportData);
	}

	private void getLossEmpInsuranceInfo(EmpInsReportSettingExportData exportData, String companyId,
			List<String> employeeIds, GeneralDate startDate, GeneralDate endDate, int officeCls) {
		if (startDate.after(endDate)) {
			throw new BusinessException("Msg_812");
		}

		List<EmpInsHist> employeeInsuranceHistories = empInsHistRepo.getByEmpIdsAndEndDateInPeriod(companyId,
				employeeIds, startDate, endDate);
		if (employeeInsuranceHistories.isEmpty()) {
			throw new BusinessException("MsgQ_51");
		}

		employeeIds = employeeInsuranceHistories.stream().map(h -> h.getSid()).collect(Collectors.toList());

		Map<String, EmpInsLossInfo> empInsLossInfos = empInsLossInfoRepo.getListEmpInsLossInfo(companyId, employeeIds)
				.stream().collect(Collectors.toMap(i -> i.getSId(), i -> i));
		Map<String, EmployeeInfoEx> employeeInfos = employeeInfoAdapter.findBySIds(employeeIds).stream()
				.collect(Collectors.toMap(i -> i.getEmployeeId(), i -> i));
		Map<String, PersonExport> personInfos = personExportAdapter
				.findByPids(
						employeeInfos.entrySet().stream().map(e -> e.getValue().getPId()).collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(p -> p.getPersonId(), p -> p));
		Map<String, CurrentPersonResidence> currentPersonAddressList = CurrentPersonResidence
				.createListPerson(personExportAdapter.findByPids(
						employeeInfos.entrySet().stream().map(e -> e.getValue().getPId()).collect(Collectors.toList())))
				.stream().collect(Collectors.toMap(i -> i.getPId(), i -> i));
		Map<String, DateHistoryItem> empInsHists = employeeInsuranceHistories.stream()
				.collect(Collectors.toMap(h -> h.getSid(), h -> h.items().get(0)));

		List<EmpInsLossInfoExportRow> rowsData = new ArrayList<>();

		for (String employeeId : employeeIds) {
			DateHistoryItem history = empInsHists.get(employeeId);
			EmpInsNumInfo empInsNumInfo = empInsNumInfoRepo
					.getEmpInsNumInfoById(companyId, employeeId, history.identifier()).orElse(null);

			Optional<EmpInsOffice> empInsOffice = empInsOfficeRepo.getEmpInsOfficeById(companyId, employeeId,
					history.identifier());
			LaborInsuranceOffice laborInsuranceOffice = null;
			PublicEmploymentSecurityOffice pubEmpSecOffice = null;
			if (empInsOffice.isPresent()) {
				laborInsuranceOffice = laborInsuranceOfficeRepo
						.getLaborInsuranceOfficeById(empInsOffice.get().getLaborInsCd().v()).orElse(null);
				pubEmpSecOffice = pubEmpSecurityOfficeRepo.getPublicEmploymentSecurityOfficeById(companyId,
						laborInsuranceOffice.getLaborOfficeCode().v().substring(0, 4)).orElse(null);
			}
			// ForeignerResHistInfo dummyForResHistInfo = new
			// ForeignerResHistInfo("", 1, 1,
			// GeneralDate.fromString("2015/01/01", "yyy/MM/dd"),
			// GeneralDate.fromString("2019/01/01", "yyy/MM/dd"), "高度専門職",
			// "ベトナム");
			// foreignerResHistInfors.put(employeeId, dummyForResHistInfo);
			EmployeeInfoEx employee = employeeInfos.get(employeeId);
			rowsData.add(new EmpInsLossInfoExportRow(employeeId, employeeInfos.get(employeeId), history,
					exportData.getCompanyInfo(), empInsNumInfo, laborInsuranceOffice, empInsLossInfos.get(employeeId),
					pubEmpSecOffice, personInfos.get(employee.getPId()),
					currentPersonAddressList.get(employee.getPId())));
		}
		exportData.setRowsData(rowsData);
	}
}
