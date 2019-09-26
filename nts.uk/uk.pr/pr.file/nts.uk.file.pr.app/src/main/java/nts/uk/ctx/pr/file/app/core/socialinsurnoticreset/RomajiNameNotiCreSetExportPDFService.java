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
                AppContexts.user().userId(),
                cid,
                exportServiceContext.getQuery().getAddressOutputClass()
        ));

        RomajiNameNotiCreSetting romajiNameNotiCreSetting = romajiNameNotiCreSetRepository.getRomajiNameNotiCreSettingById().get();

        CompanyInfor companyInfor =  null;
        SocialInsuranceOffice socialInsuranceOffice = null ;
        if (romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_COMPANY_NAME.value ||
                romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_SIC_INSURES.value) {
            //companyInfor = notificationOfLossInsExRepository.getCompanyInfor(cid);
            companyInfor = new CompanyInfor("1008945", "千代田区", "霞ヶ関１－２－２", "年金サービス 株式会社", "年金 良一", "0312234567");
        } else {
            List<SocialInsuranceOffice> list = socialInsuranceOfficeRepository.findByCid(cid);
            if (list.isEmpty()) {
                throw new BusinessException("MsgQ_93");
            }
        }

        List<String> listEmp = exportServiceContext.getQuery().getEmpIds();
        String empId = null;
        String isSpouse = exportServiceContext.getQuery().getPersonTarget();
        List<RomajiNameNotification> romajiNameNotificationList = new ArrayList<RomajiNameNotification>();
        for (int i = 0; i < listEmp.size(); i++) {
            EmpBasicPenNumInfor empBasicPenNumInfor = null;
            PersonInfo personInfo = null;
            empId = listEmp.get(i);
            EmpNameReport empNameReport = null;
            EmpFamilySocialIns empFamilySocialIns  = null;
            EmpFamilyInsHis empFamilyInsHis = null;
            FamilyMember familyMember = null ;
            EmpCorpHealthOffHis empCorpHealthOffHis = null;
            AffOfficeInformation affOfficeInformation = null;
            GeneralDate date  = exportServiceContext.getQuery().getDate();
            if (isSpouse.equals("0")) {
                empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId).orElse(null);
                //personInfo = romajiNameNotiCreSetExReposity.getPersonInfo(empId);

            } else {
                //familyMember = romajiNameNotiCreSetExReposity.getFamilyInfo(empId, isSpouse);
                familyMember = new FamilyMember("1980-01-01", "HONG KILDONG", "11" , 2);
                if (familyMember != null ){
                    int familyId  = Integer.parseInt(familyMember.getFamilyMemberId());
                    empFamilyInsHis = empFamilyInsHisRepository.getListEmFamilyHis(empId, familyId).orElse(null);
                    //get history id
                    List<DateHistoryItem> dateHistoryItemList = empFamilyInsHis.getDateHistoryItem();

                    String historyId = null;
                    for (int j = 0; j < dateHistoryItemList.size(); j ++ ) {
                        if(date.compareTo(dateHistoryItemList.get(j).start()) >= 0 && date.compareTo(dateHistoryItemList.get(j).end()) < 0 ) {
                            historyId = dateHistoryItemList.get(j).identifier();
                            empFamilySocialIns = empFamilySocialInsRepository.getEmpFamilySocialInsById(historyId).orElse(null);
                        }
                    }
                }
                //personInfo = romajiNameNotiCreSetExReposity.getPersonInfo(familyMember.getPersonId());
            }

            personInfo = new PersonInfo("1980-01-01", "洪吉童", "ホン ギルトン", "ADB3171F-B5A7-40A7-9B8A-DAE80EECB44B", 1);

            //get code
            List<String> emps = new ArrayList<String>();
            emps.add(empId);
            empCorpHealthOffHis = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(emps, date).orElse(null);

            if (empCorpHealthOffHis != null ){
                affOfficeInformation = affOfficeInformationRepository.getAffOfficeInformationById(empId, empCorpHealthOffHis.getPeriod().get(0).identifier()).orElse(null);
                if (affOfficeInformation != null ){
                    socialInsuranceOffice = socialInsuranceOfficeRepository.findByCodeAndCid(cid, affOfficeInformation.getSocialInsurOfficeCode().toString()).orElse(null);
                }
            }

            empNameReport = empNameReportRepository.getEmpNameReportById(empId).orElse(null);
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

        if(romajiNameNotificationList.isEmpty()){
            throw new BusinessException("MsgQ_37");
        }

        //export PDF
        romajiNameNotiCreSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), romajiNameNotificationList);
    }
}
