package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.experimental.var;
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
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExRepository;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class EmpInsLossInfoPDFService extends ExportService<EmpInsGetQualifReportQuery>{

    @Inject
    private EmpInsReportSettingRepository empInsReportSettingRepository;

    @Inject
    private EmpInsReportTxtSettingRepository empInsReportTxtSettingRepository;

    @Inject
    private EmpInsHistRepository empInsHistRepository;

    @Inject
    private EmpInsLossInfoRepository empInsLossInfoRepository;

    @Inject
    private JapaneseErasAdapter jpErasAdapter;

    @Inject
    private EmpInsGetInfoRepository empInsGetInfoRepository;

    @Inject
    private CompanyInforAdapter mCompanyInforAdapter;

    @Inject
    private EmpEstabInsHistRepository empEstabInsHistRepository;

    @Inject
    private NotifiOfChangInNameInsPerExRepository empInsReportSettingExRepository;

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


    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();
        GeneralDate startDate = exportServiceContext.getQuery().getStartDate();
        GeneralDate endDate = exportServiceContext.getQuery().getEndDate();
        DatePeriod period = new DatePeriod(startDate, endDate);
        List<String> listEmpId = exportServiceContext.getQuery().getEmpIds();
        EmpInsReportSettingExport reportSettingExport = exportServiceContext.getQuery().getEmpInsReportSetting();
        EmpInsRptTxtSettingExport reportTxtSettingExport = exportServiceContext.getQuery().getEmpInsRptTxtSetting();
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

        EmpInsReportTxtSetting reportTxtSetting = new EmpInsReportTxtSetting(
                cid,
                userId,
                reportTxtSettingExport.getOfficeAtr(),
                reportTxtSettingExport.getFdNumber(),
                reportTxtSettingExport.getLineFeedCode()
        );

//        empInsReportSettingRepository.update(reportSetting);
//        empInsReportTxtSettingRepository.update(reportTxtSetting);

        if(endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        // 社員雇用保険履歴を取得する
        List<EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndStartDate(listEmpId, startDate);
        if (empInsHists.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        //Loop Employee
        List<String> empInsHistEmpIds = empInsHists.stream().map(EmpInsHist::getSid).collect(Collectors.toList());
        List<EmployeeInfoEx> employee = employeeInfoAdapter.findBySIds(empInsHistEmpIds);
        List<String> pIds = employee.stream().map( e -> e.getPId()).collect(Collectors.toList());
        List<PersonExport> personExports = mPersonExportAdapter.findByPids(employee.stream().map(element -> element.getPId()).collect(Collectors.toList()));

        List<String> empInsHistIds = empInsHists.stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());

        Map<String, CurrentPersonResidence> currentPersonResidence = CurrentPersonResidence
                .createListPerson(personExportAdapter.findByPids(pIds)).stream().collect(Collectors.toMap(CurrentPersonResidence::getPId, Function.identity()));

        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();

        List<EmpInsLossInfoExportData> listDataExport = new ArrayList<>();
        employee.forEach( e-> {
            EmpInsLossInfoExportData temp = new EmpInsLossInfoExportData();
            temp.setEmpInsReportSetting(reportSetting);
            temp.setFormType(1);
            temp.setFillingDate(fillingDate);
            //社員IDと期間から社員雇用保険履歴IDを取得
            Optional<EmpInsHist> empInsHis = empInsHistRepository.getByEmpIdsAndPeriod(e.getEmployeeId(), period);
            if (!empInsHis.isPresent()) {
                return;
            }
            temp.setEmpInsHist(empInsHis.get());

            //社員IDから雇用保険喪失時情報を取得する
            Optional<EmpInsLossInfo> lossInfo = empInsLossInfoRepository.getEmpInsLossInfoById(cid, e.getEmployeeId());
            temp.setEmpInsLossInfo(lossInfo.isPresent() ? lossInfo.get() : null);

            //社員雇用保険履歴IDから雇用保険番号情報を取得する
            String insHisId = empInsHis.get().getHistoryItem().get(0).identifier();
            Optional<EmpInsNumInfo> empInsNumInfo = mEmpInsNumInfoRepository.getEmpInsNumInfoById(cid,e.getEmployeeId(),insHisId);
            temp.setEmpInsNumInfo(empInsNumInfo.isPresent() ? empInsNumInfo.get() : null);

            if(currentPersonResidence.containsKey(e.getPId())) {
                temp.setCurrentPersonResidence(currentPersonResidence.get(0));
            }

            switch (reportSetting.getOfficeClsAtr()) {
                case OUTPUT_COMPANY:
                    CompanyInfor cInfo = mCompanyInforAdapter.getCompanyNotAbolitionByCid(cid);
                    break;
                case OUPUT_LABOR_OFFICE:
                    List<EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndDate(empInsHistIds, endDate);
                    if (!empInsOffices.isEmpty()) {
                        Optional<LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getLaborInsuranceOfficeById(empInsOffices.get(0).getLaborInsCd().toString());
                        temp.setLaborInsuranceOffice(laborInsuranceOffices.isPresent() ? laborInsuranceOffices.get() : null);
                    }
                    break;
                default: break;
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

            temp.setSid(e.getEmployeeId());
            listDataExport.add(temp);
        });

        if(listDataExport.isEmpty()){
            throw new BusinessException("MsgQ_51");
        }

        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
    }

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.jpErasAdapter.getAllEras().eraOf(date);
        return era.map(japaneseEraName -> new JapaneseDate(date, japaneseEraName)).orElse(null);
    }
}
