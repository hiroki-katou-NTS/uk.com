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
import java.util.Optional;

@Stateless
public class RomajiNameNotiCreSetExportPDFService extends ExportService<RomajiNameNotiCreSetExportQuery>{

    @Inject
    private RomajiNameNotiCreSetRepository romajiNameNotiCreSetRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    @Inject
    private RomajiNameNotiCreSetFileGenerator romajiNameNotiCreSetFileGenerator;

    @Inject
    private RomajiNameNotiCreSetExReposity romajiNameNotiCreSetExReposity;

    @Override
    protected void handle(ExportServiceContext<RomajiNameNotiCreSetExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        romajiNameNotiCreSetRepository.register(new RomajiNameNotiCreSetting(cid,AppContexts.user().userId(),exportServiceContext.getQuery().getAddressOutputClass()));
        CompanyInfor companyInfor = null;
        RomajiNameNotiCreSetting romajiNameNotiCreSetting = romajiNameNotiCreSetRepository.getRomajiNameNotiCreSettingById().get();
        if (romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_COMPANY_NAME.value) {
            companyInfor = new CompanyInfor("1008945", "千代田区", "霞ヶ関１－２－２", "年金サービス 株式会社", "年金 良一", "0312234567");
        } else if (romajiNameNotiCreSetting.getAddressOutputClass().value == BusinessDivision.OUTPUT_SIC_INSURES.value){
            List<SocialInsuranceOffice> list = socialInsuranceOfficeRepository.findByCid(cid);
            if (list.isEmpty()) {
                throw new BusinessException("MsgQ_93");
            }
        }

        List<String> empIdList = exportServiceContext.getQuery().getEmpIds();
        FamilyMember familyMember = new FamilyMember("1980-01-02", "HONG KILDONGS WIFE", "11" , 2, "ホン ギルトンノツマ");
        PersonInfo personInfo = new PersonInfo("1980-01-01", "HONG KILDONG", "ホン ギルトン", "ADB3171F-B5A7-40A7-9B8A-DAE80EECB44B", 1);
        List<RomajiNameNotiCreSetExport> empNameReportList = romajiNameNotiCreSetExReposity.getEmpNameReportList(empIdList, cid);
        if(empNameReportList.isEmpty()){
            throw new BusinessException("MsgQ_37");
        }
        List<String> empIds  = new ArrayList<>();
        for (RomajiNameNotiCreSetExport list: empNameReportList) {
            empIds.add(list.getEmpId());
        }

        List<RomajiNameNotiCreSetExport> socialInsuranceOfficeList = romajiNameNotiCreSetExReposity.getSocialInsuranceOfficeList(empIds, cid);
        List<RomajiNameNotiCreSetExport> empFamilySocialInsList = romajiNameNotiCreSetExReposity.getEmpFamilySocialInsList(empIds, cid, Integer.valueOf(familyMember.getFamilyMemberId()), exportServiceContext.getQuery().getDate());
        List<RomajiNameNotiCreSetExport> empBasicPenNumInforList = romajiNameNotiCreSetExReposity.getEmpBasicPenNumInforList(empIds, cid);
        List<RomajiNameNotiCreSetExport> dataList  = new ArrayList<>();
        empNameReportList.stream().forEach(i -> {
            RomajiNameNotiCreSetExport romajiNameNotiCreSetExport = new RomajiNameNotiCreSetExport();
            Optional<RomajiNameNotiCreSetExport> e = empFamilySocialInsList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> f = empBasicPenNumInforList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> l = socialInsuranceOfficeList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> k = empNameReportList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            if(e.isPresent()) {
                romajiNameNotiCreSetExport.setFmBsPenNum(e.get().getFmBsPenNum());
            }

            if(f.isPresent()) {
                romajiNameNotiCreSetExport.setBasicPenNumber(f.get().getBasicPenNumber());
            }

            if(l.isPresent()) {
                romajiNameNotiCreSetExport.setName(l.get().getName());
                romajiNameNotiCreSetExport.setRepresentativeName(l.get().getRepresentativeName());
                romajiNameNotiCreSetExport.setAddress1(l.get().getAddress1());
                romajiNameNotiCreSetExport.setAddress2(l.get().getAddress2());
                romajiNameNotiCreSetExport.setPhoneNumber(l.get().getPhoneNumber());
                romajiNameNotiCreSetExport.setPostalCode(l.get().getPostalCode());
            }

            if(k.isPresent()) {
                romajiNameNotiCreSetExport.setPersonalSetListed(k.get().getPersonalSetListed());
                romajiNameNotiCreSetExport.setPersonalAddressOverseas(k.get().getPersonalAddressOverseas());
                romajiNameNotiCreSetExport.setPersonalOtherReason(k.get().getPersonalOtherReason());
                romajiNameNotiCreSetExport.setPersonalResidentCard(k.get().getPersonalResidentCard());
                romajiNameNotiCreSetExport.setPersonalSetOther(k.get().getPersonalSetOther());
                romajiNameNotiCreSetExport.setPersonalShortResident(k.get().getPersonalShortResident());

                romajiNameNotiCreSetExport.setSpouseSetListed(k.get().getSpouseSetListed());
                romajiNameNotiCreSetExport.setPersonalShortResident(k.get().getPersonalShortResident());
                romajiNameNotiCreSetExport.setPersonalSetOther(k.get().getPersonalSetOther());
                romajiNameNotiCreSetExport.setPersonalResidentCard(k.get().getPersonalResidentCard());
                romajiNameNotiCreSetExport.setPersonalAddressOverseas(k.get().getPersonalAddressOverseas());
                romajiNameNotiCreSetExport.setPersonalOtherReason(k.get().getPersonalOtherReason());
            }
            dataList.add(romajiNameNotiCreSetExport);
        });

        RomajiNameNotification romajiNameNotification = new RomajiNameNotification(
                exportServiceContext.getQuery().getDate(),
                exportServiceContext.getQuery().getPersonTarget(),
                companyInfor,
                personInfo,
                familyMember,
                romajiNameNotiCreSetting,
                dataList
        );

        //export PDF
        romajiNameNotiCreSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), romajiNameNotification);
    }
}
