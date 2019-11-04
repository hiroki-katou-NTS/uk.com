package nts.uk.ctx.pr.file.app.core.empinsreportsetting;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmpInsReportSettingPDFService extends ExportService<EmpInsReportSettingExportQuery> {

    @Inject
    EmpInsReportSettingRepository mEmpInsReportSettingRepository;

    @Inject
    EmpInsHistRepository mEmpInsHistRepository;

    @Inject
    CompanyInforAdapter mCompanyInforAdapter;

    @Inject
    EmpEstabInsHistRepository mEmpEstabInsHistRepository;

    @Inject
    EmpInsReportSettingExRepository empInsReportSettingExRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter mPersonExportAdapter;



    @Override
    protected void handle(ExportServiceContext<EmpInsReportSettingExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        EmpInsReportSettingExport mEmpInsReportSettingExport =  exportServiceContext.getQuery().getEmpInsReportSettingExport();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        EmpInsReportSetting empInsReportSetting = new EmpInsReportSetting(
                cid,
                userId,
                mEmpInsReportSettingExport.getSubmitNameAtr(),
                mEmpInsReportSettingExport.getOutputOrderAtr(),
                mEmpInsReportSettingExport.getOfficeClsAtr(),
                mEmpInsReportSettingExport.getMyNumberClsAtr(),
                mEmpInsReportSettingExport.getNameChangeClsAtr()
        );
        mEmpInsReportSettingRepository.update(empInsReportSetting);
        // data export
        List<EmpInsReportSettingExportData> listDataExport = new ArrayList<>();
        List<EmployeeInfoEx> employee = employeeInfoAdapter.findBySIds(empIds);
        List<PersonExport> personExports = mPersonExportAdapter.findByPids(employee.stream().map(element -> element.getPId()).collect(Collectors.toList()));

        employee.forEach(e ->{

            EmpInsReportSettingExportData temp = new EmpInsReportSettingExportData();
            Optional<EmpInsHist> mEmpInsHist = mEmpInsHistRepository.getEmpInsHistById(cid,e.getEmployeeId());
            if(!mEmpInsHist.isPresent()){
                return;
            }
            switch (empInsReportSetting.getOfficeClsAtr()){
                case OUTPUT_COMPANY:{
                    temp.setCompanyInfor(mCompanyInforAdapter.getCompanyNotAbolitionByCid(cid));
                    break;
                }
                case OUPUT_LABOR_OFFICE:{
                    temp.setLaborInsuranceOffice(empInsReportSettingExRepository.getListEmpInsHistByDate(cid,e.getEmployeeId(),fillingDate).get(0));
                    break;
                }
                case DO_NOT_OUTPUT:{
                    break;
                }
            }
            // dummy data thuật toán ドメインモデル「外国人在留履歴情報」を取得する
            personExports.stream().filter(dataPerson -> {
                if(dataPerson.getPersonId().equals(e.getPId())){
                    temp.setBrithDay(dataPerson.getBirthDate().toString());
                    temp.setFullName(dataPerson.getPersonNameGroup().getPersonName());

                }
            });



        });
    }
}
