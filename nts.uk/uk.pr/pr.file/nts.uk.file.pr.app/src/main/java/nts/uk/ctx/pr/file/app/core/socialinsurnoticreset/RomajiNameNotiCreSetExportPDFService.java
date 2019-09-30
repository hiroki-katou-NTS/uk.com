package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RomajiNameNotiCreSetExportPDFService extends ExportService<RomajiNameNotiCreSetExportQuery>{

    @Inject
    private RomajiNameNotiCreSetRepository romajiNameNotiCreSetRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private EmpFamilyInsHisRepository empFamilyInsHisRepository;

    @Inject
    private EmpFamilySocialInsRepository empFamilySocialInsRepository;

    @Inject
    private EmpNameReportRepository empNameReportRepository;

    @Inject
    private RomajiNameNotiCreSetExReposity romajiNameNotiCreSetExReposity;

    @Inject
    private NotificationOfLossInsExRepository notificationOfLossInsExRepository;

    @Inject
    private RomajiNameNotiCreSetFileGenerator romajiNameNotiCreSetFileGenerator;

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Override
    protected void handle(ExportServiceContext<RomajiNameNotiCreSetExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();

        romajiNameNotiCreSetRepository.register(new RomajiNameNotiCreSetting(
                cid,
                AppContexts.user().userId(),
                exportServiceContext.getQuery().getAddressOutputClass()
        ));

        RomajiNameNotiCreSetting romajiNameNotiCreSetting = romajiNameNotiCreSetRepository.getRomajiNameNotiCreSettingById().get();

        CompanyInfor companyInfor =  null;
        SocialInsuranceOffice socialInsuranceOffice = null ;
        if (romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_COMPANY_NAME.value) {
            //companyInfor = notificationOfLossInsExRepository.getCompanyInfor(cid);
            companyInfor = new CompanyInfor("1008945", "千代田区", "霞ヶ関１－２－２", "年金サービス 株式会社", "年金 良一", "0312234567");
        } else if (romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_SIC_INSURES.value){
            List<SocialInsuranceOffice> list = socialInsuranceOfficeRepository.findByCid(cid);
            if (list.isEmpty()) {
                throw new BusinessException("MsgQ_93");
            }
        }

        List<String> listEmp = exportServiceContext.getQuery().getEmpIds();
        GeneralDate date  = exportServiceContext.getQuery().getDate();
        String empId = null;
        String isSpouse = exportServiceContext.getQuery().getPersonTarget();
        List<RomajiNameNotification> romajiNameNotificationList = new ArrayList<RomajiNameNotification>();
        EmpCorpHealthOffHis empCorpHealthOffHis = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(listEmp, date).orElse(null);
        EmpBasicPenNumInfor empBasicPenNumInfor = null;
        PersonInfo personInfo = null;
        EmpNameReport empNameReport = null;
        EmpFamilySocialIns empFamilySocialIns  = null;
        EmpFamilyInsHis empFamilyInsHis = null;
        FamilyMember familyMember = null ;
        AffOfficeInformation affOfficeInformation = null;
        for (int i = 0; i < listEmp.size(); i++) {
            empId = listEmp.get(i);
            empNameReport = empNameReportRepository.getEmpNameReportById(empId).orElse(null);
            if( empNameReport !=  null) {
                if (isSpouse.equals("0")) {
                    empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId).orElse(null);
                    //personInfo = romajiNameNotiCreSetExReposity.getPersonInfo(empId);

                } else {
                    //familyMember = romajiNameNotiCreSetExReposity.getFamilyInfo(empId, isSpouse);
                    familyMember = new FamilyMember("1980-01-02", "HONG KILDONGS WIFE", "11" , 2, "ホン ギルトンノツマ");
                    if (familyMember != null ){
                        int  familyId  = Integer.valueOf(familyMember.getFamilyMemberId());
                        empFamilyInsHis = empFamilyInsHisRepository.getListEmFamilyHis(empId, familyId).orElse(null);
                        if (empFamilyInsHis != null) {
                            //get history id
                            List<DateHistoryItem> dateHistoryItemList = empFamilyInsHis.getDateHistoryItem();
                           if(!this.getHistory(dateHistoryItemList, date).isEmpty()){
                               empFamilySocialIns = empFamilySocialInsRepository.getEmpFamilySocialInsById(empId, familyId, this.getHistory(dateHistoryItemList, date)).orElse(null);
                           }
                        }
                    }
                    //personInfo = romajiNameNotiCreSetExReposity.getPersonInfo(familyMember.getPersonId());
                }

                personInfo = new PersonInfo("1980-01-01", "HONG KILDONG", "ホン ギルトン", "ADB3171F-B5A7-40A7-9B8A-DAE80EECB44B", 1);

                if (empCorpHealthOffHis != null ){
                    affOfficeInformation = affOfficeInformationRepository.getAffOfficeInformationById(empId, empCorpHealthOffHis.getPeriod().get(0).identifier()).orElse(null);
                    if (affOfficeInformation != null ){
                        socialInsuranceOffice = socialInsuranceOfficeRepository.findByCodeAndCid(cid, affOfficeInformation.getSocialInsurOfficeCode().toString()).orElse(null);
                    }
                }

                RomajiNameNotification romajiNameNotification  = new RomajiNameNotification(
                        empNameReport,
                        empFamilySocialIns,
                        familyMember,
                        empBasicPenNumInfor,
                        personInfo,
                        companyInfor,
                        exportServiceContext.getQuery().getDate(),
                        exportServiceContext.getQuery().getPersonTarget(),
                        socialInsuranceOffice,
                        romajiNameNotiCreSetting
                );
                romajiNameNotificationList.add( romajiNameNotification );
            }
        }

        if(romajiNameNotificationList.isEmpty()){
            throw new BusinessException("MsgQ_37");
        }

        //export PDF
        romajiNameNotiCreSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), romajiNameNotificationList);
    }

    private String getHistory(List<DateHistoryItem> dateHistoryItemList, GeneralDate date){
        if(!dateHistoryItemList.isEmpty()) {
            String historyId = null;
            for (int j = 0; j < dateHistoryItemList.size(); j ++ ) {
                if(date.compareTo(dateHistoryItemList.get(j).start()) >= 0 && date.compareTo(dateHistoryItemList.get(j).end()) < 0 ) {
                    historyId = dateHistoryItemList.get(j).identifier();
                }
            }
            return historyId;
        } else {
            return null;
        }
    }
}
