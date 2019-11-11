package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.person.family.FamilyMemberAdapter;
import nts.uk.ctx.pr.core.dom.adapter.person.family.FamilyMemberInfoEx;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Inject
    private CompanyInforAdapter companyAdapter;

    @Inject
    private FamilyMemberAdapter familyMemberAdapter;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Override
    protected void handle(ExportServiceContext<RomajiNameNotiCreSetExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        RomajiNameNotiCreSetting romajiNameNotiCreSetting = new RomajiNameNotiCreSetting(cid,AppContexts.user().userId(),exportServiceContext.getQuery().getAddressOutputClass());
        romajiNameNotiCreSetRepository.register(romajiNameNotiCreSetting);
        CompanyInfor companyInfor = companyAdapter.getCompanyNotAbolitionByCid(cid);
        if (romajiNameNotiCreSetting.getAddressOutputClass() == BusinessDivision.OUTPUT_SIC_INSURES){
            List<SocialInsuranceOffice> list = socialInsuranceOfficeRepository.findByCid(cid);
            if (list.isEmpty()) {
                throw new BusinessException("MsgQ_93");
            }
        }
        List<String> empIdList = exportServiceContext.getQuery().getEmpIds();
        List<RomajiNameNotiCreSetExport> empNameReportList = romajiNameNotiCreSetExReposity.getEmpNameReportList(empIdList, cid);
        if(empNameReportList.isEmpty()){
            throw new BusinessException("MsgQ_37");
        }
        List<String> empIds  = new ArrayList<>();
        for (RomajiNameNotiCreSetExport list: empNameReportList) {
            empIds.add(list.getEmpId());
        }
        List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(empIds);
        List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
        List<RomajiNameNotiCreSetExport> socialInsuranceOfficeList = romajiNameNotiCreSetExReposity.getSocialInsuranceOfficeList(empIds, cid);
        List<RomajiNameNotiCreSetExport> empBasicPenNumInforList = romajiNameNotiCreSetExReposity.getEmpBasicPenNumInforList(empIds, cid);
        List<RomajiNameNotiCreSetExport> dataList  = new ArrayList<>();
        empNameReportList.forEach(i -> {
            RomajiNameNotiCreSetExport romajiNameNotiCreSetExport = new RomajiNameNotiCreSetExport();
            PersonExport p = getPersonInfor(employeeInfoList, personList,  i.getEmpId());
            romajiNameNotiCreSetExport.setPerson(p);
            List<FamilyMemberInfoEx> family = familyMemberAdapter.getRomajiOfFamilySpouseByPid(p.getPersonId());
            romajiNameNotiCreSetExport.setFamilyMember(!family.isEmpty() ? family.get(0) : null);
            List<RomajiNameNotiCreSetExport> empFamilySocialInsList = romajiNameNotiCreSetExport.getFamilyMember() != null ?
                    romajiNameNotiCreSetExReposity.getEmpFamilySocialInsList(empIds, cid, Integer.valueOf(romajiNameNotiCreSetExport.getFamilyMember().getFamilyId()), exportServiceContext.getQuery().getDate()) : new ArrayList<>();
            Optional<RomajiNameNotiCreSetExport> e = empFamilySocialInsList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> f = empBasicPenNumInforList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> l = socialInsuranceOfficeList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            Optional<RomajiNameNotiCreSetExport> k = empNameReportList.stream().filter(item -> item.getEmpId().equals(i.getEmpId())).findFirst();
            e.ifPresent(romajiNameNotiCreSetExport1 -> romajiNameNotiCreSetExport.setFmBsPenNum(romajiNameNotiCreSetExport1.getFmBsPenNum()));
            f.ifPresent(romajiNameNotiCreSetExport1 -> romajiNameNotiCreSetExport.setBasicPenNumber(romajiNameNotiCreSetExport1.getBasicPenNumber()));
            l.ifPresent(romajiNameNotiCreSetExport1 -> {
                romajiNameNotiCreSetExport.setName(romajiNameNotiCreSetExport1.getName());
                romajiNameNotiCreSetExport.setRepresentativeName(romajiNameNotiCreSetExport1.getRepresentativeName());
                romajiNameNotiCreSetExport.setAddress1(romajiNameNotiCreSetExport1.getAddress1());
                romajiNameNotiCreSetExport.setAddress2(romajiNameNotiCreSetExport1.getAddress2());
                romajiNameNotiCreSetExport.setPhoneNumber(romajiNameNotiCreSetExport1.getPhoneNumber());
                romajiNameNotiCreSetExport.setPostalCode(romajiNameNotiCreSetExport1.getPostalCode());
            });

            if(k.isPresent()) {
                romajiNameNotiCreSetExport.setPersonalSetListed(k.get().getPersonalSetListed());
                romajiNameNotiCreSetExport.setPersonalAddressOverseas(k.get().getPersonalAddressOverseas());
                romajiNameNotiCreSetExport.setPersonalOtherReason(k.get().getPersonalOtherReason());
                romajiNameNotiCreSetExport.setPersonalResidentCard(k.get().getPersonalResidentCard());
                romajiNameNotiCreSetExport.setPersonalSetOther(k.get().getPersonalSetOther());
                romajiNameNotiCreSetExport.setPersonalShortResident(k.get().getPersonalShortResident());

                romajiNameNotiCreSetExport.setSpouseSetListed(k.get().getSpouseSetListed());
                romajiNameNotiCreSetExport.setSpouseShortResident(k.get().getSpouseShortResident());
                romajiNameNotiCreSetExport.setSpouseSetOther(k.get().getSpouseSetOther());
                romajiNameNotiCreSetExport.setSpouseResidentCard(k.get().getSpouseResidentCard());
                romajiNameNotiCreSetExport.setSpouseAddressOverseas(k.get().getSpouseAddressOverseas());
                romajiNameNotiCreSetExport.setSpouseOtherReason(k.get().getSpouseOtherReason());
            }
            dataList.add(romajiNameNotiCreSetExport);
        });

        RomajiNameNotification romajiNameNotification = new RomajiNameNotification(
                exportServiceContext.getQuery().getDate(),
                exportServiceContext.getQuery().getPersonTarget(),
                companyInfor,
                romajiNameNotiCreSetting,
                dataList
        );

        //export PDF
        romajiNameNotiCreSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), romajiNameNotification);
    }

    private PersonExport getPersonInfor(List<EmployeeInfoEx> employeeInfoList, List<PersonExport> personList, String empId){
        PersonExport person = new PersonExport();
        Optional<EmployeeInfoEx> employeeInfoEx = employeeInfoList.stream().filter(item -> item.getEmployeeId().equals(empId)).findFirst();
        if(employeeInfoEx.isPresent()) {
            Optional<PersonExport> personEx = personList.stream().filter(item -> item.getPersonId().equals(employeeInfoEx.get().getPId())).findFirst();
            if (personEx.isPresent()){
                person.setPersonId(personEx.get().getPersonId());
                person.setGender(personEx.get().getGender());
                person.setPersonNameGroup(personEx.get().getPersonNameGroup());
                person.setBirthDate(personEx.get().getBirthDate());
            }
        }
        return person;
    }

}
