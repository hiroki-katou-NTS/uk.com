package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.val;
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
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.*;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoExportQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass.PERSONAL_NAME;

@Stateless
public class EmpInsLossInfoPDFService extends ExportService<EmpInsLossInfoExportQuery>{

    @Inject
    private EmpInsReportSettingRepository empInsReportSettingRepository;

    @Inject
    private EmpInsReportTxtSettingRepository empInsReportTxtSettingRepository;

    @Inject
    private AddEmpInsRptSettingCommandHandler addEmpInsRptSttHandler;

    @Inject
    private UpdateEmpInsRptSettingCommandHandler updateEmpInsRptSttHandler;

    @Inject
    private AddEmpInsRptTxtSettingCommandHandler addEmpInsRptTxtSttHandler;

    @Inject
    private UpdateEmpInsRptTxtSettingCommandHandler updateEmpInsRptTxtSttHandler;

    @Inject
    private EmpInsHistRepository empInsHistRepository;

    @Inject
    private EmpInsLossInfoRepository empInsLossInfoRepository;

    @Inject
    private JapaneseErasAdapter jpErasAdapter;

    @Inject
    private CompanyInforAdapter mCompanyInforAdapter;

    @Inject
    private EmpEstabInsHistRepository empEstabInsHistRepository;

    @Inject
    private EmpInsNumInfoRepository mEmpInsNumInfoRepository;

    @Inject
    private PersonExportAdapter mPersonExportAdapter;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private LaborInsuranceOfficeRepository laborInsOfficeRepository;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Inject
    private EmpInsLossInfoFileGenerator generator;

    @Inject
    private RetirementReasonClsInfoRepository retiReasonClsInfoRepository;


    @Override
    protected void handle(ExportServiceContext<EmpInsLossInfoExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();
        GeneralDate startDate = exportServiceContext.getQuery().getStartDate();
        GeneralDate endDate = exportServiceContext.getQuery().getEndDate();
        List<String> listEmpId = exportServiceContext.getQuery().getEmployeeIds();
        EmpInsRptSettingCommand reportSettingExport = exportServiceContext.getQuery().getEmpInsReportSettingCommand();
        EmpInsRptTxtSettingCommand reportTxtSettingExport = exportServiceContext.getQuery().getEmpInsReportTxtSettingCommand();
        // update setting
        EmpInsReportSetting reportSetting = new EmpInsReportSetting(
                cid,
                userId,
                reportSettingExport.getSubmitNameAtr(),
                reportSettingExport.getOutputOrderAtr(),
                reportSettingExport.getOfficeClsAtr(),
                reportSettingExport.getMyNumberClsAtr(),
                reportSettingExport.getNameChangeClsAtr()
        );
        // 雇用保険届設定更新処理
        if (empInsReportSettingRepository.getEmpInsReportSettingById(cid, userId).isPresent()) {
            // 雇用保険届作成設定を更新する
            updateEmpInsRptSttHandler.handle(reportSettingExport);
        } else {
            // 雇用保険届作成設定を追加する
            addEmpInsRptSttHandler.handle(reportSettingExport);
        }
        if (empInsReportTxtSettingRepository.getEmpInsReportTxtSettingByUserId(cid, userId).isPresent()) {
            updateEmpInsRptTxtSttHandler.handle(reportTxtSettingExport);
        } else {
            addEmpInsRptTxtSttHandler.handle(reportTxtSettingExport);
        }


        if(endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        // 社員雇用保険履歴を取得する
        Map<String, EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndEndDateInPeriod(cid, listEmpId, startDate, endDate).stream().collect(Collectors.toMap(EmpInsHist::getSid, Function.identity()));
        if (empInsHists.isEmpty()) {
            throw new BusinessException("MsgQ_51");
        }
        List<String> insHistEmpIds = new ArrayList<>(empInsHists.keySet());
        CompanyInfor cInfo = mCompanyInforAdapter.getCompanyNotAbolitionByCid(cid);

        List<String> empInsHistIds = empInsHists.values().stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());
        List<EmployeeInfoEx> employee = employeeInfoAdapter.findBySIds(insHistEmpIds);
        List<String> pIds = employee.stream().map( e -> e.getPId()).collect(Collectors.toList());
        List<PersonExport> personExports = mPersonExportAdapter.findByPids(employee.stream().map(element -> element.getPId()).collect(Collectors.toList()));
        Map<String, EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndEndDateInPeriod(empInsHistIds, startDate, endDate).stream().collect(Collectors.toMap(e -> e.getHistId(), Function.identity()));
        Map<String, CurrentPersonResidence> currentPersonResidence = CurrentPersonResidence
                .createListPerson(personExportAdapter.findByPids(pIds)).stream().collect(Collectors.toMap(CurrentPersonResidence::getPId, Function.identity()));
        Map<String, EmpInsLossInfo> empLossInfos = empInsLossInfoRepository.getListEmpInsLossInfo(cid, listEmpId).stream().collect(Collectors.toMap(EmpInsLossInfo::getSId, Function.identity()));
        Map<String, String> causeOfLossIns = retiReasonClsInfoRepository.getRetirementReasonClsInfoById(cid).stream().collect(Collectors.toMap(e -> e.getRetirementReasonClsCode().v(), e -> e.getRetirementReasonClsName().v()));
        Map<String, EmpInsNumInfo> empNumInfos = mEmpInsNumInfoRepository.getByCidAndHistIds(cid, empInsHistIds).stream().collect(Collectors.toMap(EmpInsNumInfo::getHistId, Function.identity()));
        List<String> laborOfficeCodes = empInsOffices.values().stream().map(e -> e.getLaborInsCd().v()).collect(Collectors.toList());
        Map<String, LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getByCidAndCodes(cid, laborOfficeCodes).stream().collect(Collectors.toMap(e -> e.getLaborOfficeCode().v(), Function.identity()));

        List<EmpInsLossInfoExportData> listDataExport = new ArrayList<>();
        employee.forEach( e-> {
            EmpInsLossInfoExportData temp = new EmpInsLossInfoExportData();
            temp.setEmpInsReportSetting(reportSetting);
            temp.setPeriodOfStay("20190101");
            temp.setWorkCategory(1);
            temp.setNationality("ベトナム");
            temp.setResidenceStatus("高度専門職");
            temp.setFillingDate(fillingDate);

            String laborCode = " ";
            //社員IDと期間から社員雇用保険履歴IDを取得
            if (empInsHists.containsKey(e.getEmployeeId())) {
                String insHisId = empInsHists.get(e.getEmployeeId()).getHistoryItem().get(0).identifier();
                temp.setEmpInsHist(empInsHists.get(e.getEmployeeId()));
                if (empNumInfos.containsKey(insHisId)) {
                    temp.setEmpInsNumInfo(empNumInfos.get(insHisId));
                }
                if (empInsOffices.containsKey(insHisId)) {
                    laborCode = empInsOffices.get(insHisId).getLaborInsCd().toString();
                }
            }

            //社員IDから雇用保険喪失時情報を取得する
            if (empLossInfos.containsKey(e.getEmployeeId())) {
                val lossInfo = empLossInfos.get(e.getEmployeeId());
                temp.setEmpInsLossInfo(lossInfo);
                if (lossInfo.getCauseOfLossEmpInsurance().isPresent()) {
                    temp.setRetirementReasonClsInfo(causeOfLossIns.get(lossInfo.getCauseOfLossEmpInsurance().get().v()));
                }
            }

            if(currentPersonResidence.containsKey(e.getPId())) {
                temp.setCurrentPersonResidence(currentPersonResidence.get(e.getPId()));
            }
            temp.setCompanyInfor(cInfo);
            if (laborInsuranceOffices.containsKey(laborCode)) {
                temp.setLaborInsuranceOffice(laborInsuranceOffices.get(laborCode));
            }

            Optional<PersonExport> person = personExports.stream().filter(dataPerson -> dataPerson.getPersonId().equals(e.getPId())).findFirst();
            if(person.isPresent()){
                temp.setBrithDay(person.get().getBirthDate().toString());
                temp.setName(person.get().getPersonNameGroup().getPersonName().getFullName());
                temp.setNameKana(person.get().getPersonNameGroup().getPersonName().getFullNameKana());
                temp.setFullName(person.get().getPersonNameGroup().getPersonRomanji().getFullName());
                temp.setFullNameKana(person.get().getPersonNameGroup().getPersonRomanji().getFullNameKana());
                temp.setReportFullName(person.get().getPersonNameGroup().getTodokedeFullName().getFullName());
                temp.setReportFullNameKana(person.get().getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                temp.setGender(person.get().getGender());
                temp.setOldName(person.get().getPersonNameGroup().getOldName().getFullName());
                temp.setOldNameKana(person.get().getPersonNameGroup().getOldName().getFullNameKana());
            }

            temp.setEmployeeCode(e.getEmployeeCode());
            listDataExport.add(temp);
        });

        if(listDataExport.isEmpty()){
            throw new BusinessException("MsgQ_51");
        }
        switch (reportSetting.getOutputOrderAtr()){
            case INSURANCE_NUMBER:{
                listDataExport.sort(Comparator.comparing(o -> o.getEmpInsNumInfo().getEmpInsNumber().v()));
                break;
            }
            case DEPARTMENT_EMPLOYEE:{
                listDataExport.sort(Comparator.comparing(EmpInsLossInfoExportData::getEmployeeCode));
                break;
            }
            case EMPLOYEE_CODE:{
                listDataExport.sort(Comparator.comparing(EmpInsLossInfoExportData::getEmployeeCode));
                break;
            }
            case EMPLOYEE:{
                if(reportSetting.getSubmitNameAtr() == PERSONAL_NAME ){
                    listDataExport.sort((o1, o2) -> {
                        if (o1.getNameKana().compareTo(o2.getNameKana()) == 0) {
                            return o1.getEmployeeCode().compareTo(o2.getEmployeeCode());
                        } else {
                            return o1.getNameKana().compareTo(o2.getNameKana());
                        }
                    });
                }
                else{
                    listDataExport.sort((o1, o2) -> {
                        if (o1.getReportFullNameKana().compareTo(o2.getReportFullNameKana()) == 0) {
                            return o1.getEmployeeCode().compareTo(o2.getEmployeeCode());
                        } else {
                            return o1.getReportFullNameKana().compareTo(o2.getReportFullNameKana());
                        }
                    });
                }
                break;
            }

        }

        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
    }
}
