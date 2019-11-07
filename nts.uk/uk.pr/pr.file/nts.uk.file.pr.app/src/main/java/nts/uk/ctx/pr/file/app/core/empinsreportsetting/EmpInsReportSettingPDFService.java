package nts.uk.ctx.pr.file.app.core.empinsreportsetting;

import nts.arc.error.BusinessException;
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
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsOutOrder.INSURANCE_NUMBER;

@Stateless
public class EmpInsReportSettingPDFService extends ExportService<EmpInsReportSettingExportQuery> {

    @Inject
    private EmpInsReportSettingRepository mEmpInsReportSettingRepository;

    @Inject
    private EmpInsHistRepository mEmpInsHistRepository;

    @Inject
    private CompanyInforAdapter mCompanyInforAdapter;

    @Inject
    private EmpEstabInsHistRepository mEmpEstabInsHistRepository;

    @Inject
    private EmpInsReportSettingExRepository empInsReportSettingExRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter mPersonExportAdapter;

    @Inject
    private EmpInsNumInfoRepository mEmpInsNumInfoRepository;

    @Inject
    private EmpInsReportSettingExFileGenerator generator;



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
            };
            String hisId = mEmpInsHist.get().getHistoryItem().get(0).identifier();
            Optional<EmpInsNumInfo> empInsNumInfo = mEmpInsNumInfoRepository.getEmpInsNumInfoById(cid,e.getEmployeeId(),hisId);
            temp.setEmpInsNumInfo(empInsNumInfo.isPresent() ? empInsNumInfo.get() : new EmpInsNumInfo(hisId,""));
            // dummy data thuật toán ドメインモデル「外国人在留履歴情報」を取得する
            personExports.stream().filter(dataPerson -> {
                if(dataPerson.getPersonId().equals(e.getPId())){
                    temp.setBrithDay(dataPerson.getBirthDate().toString());
                    temp.setName(dataPerson.getPersonNameGroup().getPersonName().getFullName());
                    temp.setNameKana(dataPerson.getPersonNameGroup().getPersonName().getFullNameKana());
                    temp.setFullName(dataPerson.getPersonNameGroup().getPersonRomanji().getFullName());
                    temp.setFullNameKana(dataPerson.getPersonNameGroup().getPersonRomanji().getFullNameKana());
                    temp.setReportFullName(dataPerson.getPersonNameGroup().getTodokedeFullName().getFullName());
                    temp.setReportFullNameKana(dataPerson.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    temp.setGender(dataPerson.getGender());
                    temp.setBrithDay(dataPerson.getBirthDate().toString());
                    temp.setChangeDate("");
                    temp.setEmpInsHist(mEmpInsHist.isPresent() ? mEmpInsHist.get() : null);
                }
                return true;
            });
            temp.setEmployeeCode(e.getEmployeeCode());
            listDataExport.add(temp);


        });
        if(listDataExport.isEmpty()){
            throw new BusinessException("MsgQ_51");
        }
        if(empInsReportSetting.getOutputOrderAtr() == INSURANCE_NUMBER){
            Collections.sort(listDataExport, new Comparator<EmpInsReportSettingExportData>() {
                @Override
                public int compare(EmpInsReportSettingExportData o1, EmpInsReportSettingExportData o2) {
                    return o1.getEmpInsNumInfo().getEmpInsNumber().v().compareTo(o2.getEmpInsNumInfo().getEmpInsNumber().v());
                }
            });
        }
        else{
            Collections.sort(listDataExport, new Comparator<EmpInsReportSettingExportData>() {
                @Override
                public int compare(EmpInsReportSettingExportData o1, EmpInsReportSettingExportData o2) {
                    return o1.getEmployeeCode().compareTo(o2.getEmployeeCode());
                }
            });
        }
//        switch (empInsReportSetting.getOutputOrderAtr()){
//            case INSURANCE_NUMBER:{
//
//                break;
//            }
//            case DEPARTMENT_EMPLOYEE:{
//
//                break;
//            }
//            case EMPLOYEE_CODE:{
//                Collections.sort(listDataExport, new Comparator<EmpInsReportSettingExportData>() {
//                    @Override
//                    public int compare(EmpInsReportSettingExportData o1, EmpInsReportSettingExportData o2) {
//                        return o1.getEmployeeCode().compareTo(o2.getEmployeeCode());
//                    }
//                });
//                break;
//            }
//            case EMPLOYEE:{
//                Collections.sort(listDataExport, new Comparator<EmpInsReportSettingExportData>() {
//                    @Override
//                    public int compare(EmpInsReportSettingExportData o1, EmpInsReportSettingExportData o2) {
//                        return o1.getEmployeeCode().compareTo(o2.getEmployeeCode());
//                    }
//                });
//                break;
//            }
//
//        }
        generator.generate(exportServiceContext.getGeneratorContext(),listDataExport);
    }
}
