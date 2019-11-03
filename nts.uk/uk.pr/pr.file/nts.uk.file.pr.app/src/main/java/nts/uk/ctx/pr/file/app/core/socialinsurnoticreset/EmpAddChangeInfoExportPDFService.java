package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpAddChangeInfoExportPDFService extends ExportService<NotificationOfLossInsExportQuery> {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private EmpAddChangeInfoRepository empAddChangeInfoRepository;

    @Inject
    private EmpAddChangeInfoFileGenerator empAddChangeInfoFileGenerator;

    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private EmpFamilySocialInsRepository empFamilySocialInsRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    @Inject
    private EmpAddChangeInfoExReposity empAddChangeInfoExReposity;


    @Override
    protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
        GeneralDate baseDate = exportServiceContext.getQuery().getReference();
        int printPersonNumber = exportServiceContext.getQuery().getSocialInsurNotiCreateSet().getPrintPersonNumber();
        NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(userId, cid,
                socialInsurNotiCreateSet.getOfficeInformation(),
                socialInsurNotiCreateSet.getBusinessArrSymbol(),
                socialInsurNotiCreateSet.getOutputOrder(),
                socialInsurNotiCreateSet.getPrintPersonNumber(),
                socialInsurNotiCreateSet.getSubmittedName(),
                socialInsurNotiCreateSet.getInsuredNumber(),
                socialInsurNotiCreateSet.getFdNumber(),
                socialInsurNotiCreateSet.getTextPersonNumber(),
                socialInsurNotiCreateSet.getOutputFormat(),
                socialInsurNotiCreateSet.getLineFeedCode()
        );
        socialInsurNotiCrSetRepository.update(domain);
        if(end.before(start)) {
            throw new BusinessException("Msg_812");
        }

        if (printPersonNumber == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER.value || printPersonNumber == PersonalNumClass.DO_NOT_OUTPUT.value ){
            List<EmpAddChangeInfoExport> empAddChangeInfoExportList = EmpAddChangeInfoExport.getListExport(empIds);
            /*List<EmpFamilySocialInsCtgInfo> empFamilySocialInsCtgInfoList = empAddChangeInfoExReposity.getEmpFamilySocialInsCtgInfoList(empIds, cid);
            List<EmpHealInsurQInfo> empHealInsurQInfoList = empAddChangeInfoExReposity.getEmpHealInsurQInfoList(empIds, cid);
            List<EmpWelfarePenInsQualiInfo> empWelfarePenInsQualiInforList = empAddChangeInfoExReposity.getEmpWelfarePenInsQualiInfoList(empIds, cid);
            List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository.findByCid(cid);
            List<EmpCorpOffHisInfo> empCorpOffHisInfoList = empAddChangeInfoExReposity.getEmpCorpOffHisInfo(empIds, cid);
            List<HealInsurPortPerIntellInfo> healInsurPortPerIntellInfoList = empAddChangeInfoExReposity.getHealInsurPortPerIntellInfo(empIds, cid);
            List<EmPensionFundPartiPeriodInfo> emPensionFunList = empAddChangeInfoExReposity.getEmPensionFundPartiPeriodInfo(empIds, cid);
            List<EmpBasicPenNumInfor> empBasicPenNumInforList = empBasicPenNumInforRepository.getAllEmpBasicPenNumInfor();
            List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
            List<CurrentPersonResidence> currentPersonAddressList = CurrentPersonResidence.createListPerson(empIds);
            List<CurrentFamilyResidence> currentFamilyResidenceList = CurrentFamilyResidence.getListFamily();
            List<EmpAddChangeInfoExport> empAddChangeInfoExportList = EmpAddChangeInfoExport.getListExport(empIds);

            empAddChangeInfoExportList.forEach(e->{
                //Imported（給与）「個人現住所」
                if(!currentPersonAddressList.isEmpty()){
                    Optional<CurrentPersonResidence> cp = currentPersonAddressList.stream().filter(p->p.getEmpId().equals(e.getEmpId())).findFirst();
                    if(cp.isPresent()) {
                        e.setPersonAddChangeDate(cp.get().getStartDate());
                    }
                }

                //Imported（給与）「家族情報」
                //Imported（給与）「家族現住所」
                if(!currentFamilyResidenceList.isEmpty()){
                    Optional<CurrentFamilyResidence> cf = currentFamilyResidenceList.stream().filter(f->f.getFamilyId().equals(e.getFamilyId())).findFirst();
                    if(cf.isPresent()) {
                        e.setSpouseAddChangeDate(cf.get().getStartDate());
                    }

                }

                if(!empFamilySocialInsCtgInfoList.isEmpty()){
                    Optional<EmpFamilySocialInsCtgInfo> ef = empFamilySocialInsCtgInfoList.stream().filter(fe->fe.getFamilyId().equals(e.getFamilyId())
                            && e.getEmpId().equals(fe.getEmpId())
                            && e.getSpouseAddChangeDate().beforeOrEquals(fe.getEndDate())
                            && e.getSpouseAddChangeDate().afterOrEquals(fe.getStartDate())
                            && fe.getDependent() == 1).findFirst();
                    if(!ef.isPresent()) {
                        e.setSpouseAddChangeDate(null);
                    }
                }

                if(!empHealInsurQInfoList.isEmpty()){
                    Optional<EmpHealInsurQInfo> eh = empHealInsurQInfoList.stream().filter(item -> item.getEmpId().equals(e.getEmpId())
                            && e.getPersonAddChangeDate() != null
                            && e.getPersonAddChangeDate().afterOrEquals(item.getStartDate())
                            && e.getPersonAddChangeDate().beforeOrEquals(item.getEndDate())).findFirst();
                    if (eh.isPresent()) {
                        e.setHealthInsurance(true);
                    }

                    if(domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM) {
                        e.setHealInsurNumber(eh.get().getHealInsurNumber());
                    }
                }

                if(!empWelfarePenInsQualiInforList.isEmpty()){
                    Optional<EmpWelfarePenInsQualiInfo> ew = empWelfarePenInsQualiInforList.stream().filter(item -> item.getEmpId().equals(e.getEmpId())
                            && (e.getPersonAddChangeDate() != null
                            && e.getPersonAddChangeDate().afterOrEquals(item.getStartDate())
                            && e.getPersonAddChangeDate().beforeOrEquals(item.getEndDate()))
                            || (e.getPersonAddChangeDate() == null
                            && e.getSpouseAddChangeDate().afterOrEquals(item.getStartDate())
                            && e.getSpouseAddChangeDate().beforeOrEquals(item.getEndDate()))).findFirst();

                    if(ew.isPresent()) {
                        e.setEmpPenInsurance(true);
                    }
                }

                //end of list emp add change information
                if( e.getSpouseAddChangeDate() == null && e.getPersonAddChangeDate() == null || !e.isEmpPenInsurance() && !e.isHealthInsurance()) {
                    empAddChangeInfoExportList.remove(e);
                }
            });

            if (!empCorpOffHisInfoList.isEmpty()){
                empCorpOffHisInfoList.forEach(i-> {
                   Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item->item.getEmpId().equals(i.getEmpId())
                           && item.getSpouseAddChangeDate().afterOrEquals(i.getStartDate())
                           && item.getSpouseAddChangeDate().beforeOrEquals(i.getEndDate())).findFirst();
                   Optional<SocialInsuranceOffice> socialInsuranceOffice = socialInsuranceOfficeList.stream().filter(s->s.getCompanyID().equals(i.getCid())
                           && s.getCode().equals(i.getSocialInsurOfficeCode())).findFirst();
                   if(em.isPresent() && socialInsuranceOffice.isPresent() && domain.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){
                       em.get().setPhoneNumber(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getPhoneNumber().map(l -> l.v()).orElse(null));
                       em.get().setBussinessName(socialInsuranceOffice.get().getName().v());
                       em.get().setAddress1(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getAddress1().map(l -> l.v()).orElse(null));
                       em.get().setAddress2(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getAddress2().map(l -> l.v()).orElse(null));
                       em.get().setReferenceName(socialInsuranceOffice.get().getBasicInformation().getRepresentativeName().map(l -> l.v()).orElse(null));
                   }
                    if(em.isPresent() && socialInsuranceOffice.isPresent() && domain.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL){
                        em.get().setBusinessEstCode1(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().toString());
                        em.get().setBusinessEstCode2(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().toString());

                    } else  if(em.isPresent() && socialInsuranceOffice.isPresent() && domain.getBusinessArrSymbol() == BussEsimateClass.EMPEN_ESTAB_REARSIGN){
                        em.get().setBusinessEstCode1(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().toString());
                        em.get().setBusinessEstCode2(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().toString());
                    }

                });
            }

            if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER) {

                if (!empWelfarePenInsQualiInforList.isEmpty()) {
                    empWelfarePenInsQualiInforList.stream().forEach(k -> {
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                                && item.getPersonAddChangeDate() != null
                                && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                                && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                        if (em.isPresent()) {
                            em.get().setHealInsurNumber(k.getWelPenNumber());
                        }
                    });
                }

            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION) {

                if (!healInsurPortPerIntellInfoList.isEmpty()) {
                    healInsurPortPerIntellInfoList.stream().forEach(k -> {
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                                && item.getPersonAddChangeDate() != null
                                && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                                && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                        if (em.isPresent()) {
                            em.get().setHealInsurNumber(k.getHealInsurUnionNumber());
                        }
                    });
                }


            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER) {
                if(!emPensionFunList.isEmpty()) {
                    emPensionFunList.stream().forEach(k -> {
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                                && item.getPersonAddChangeDate() != null
                                && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                                && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                        if (em.isPresent()) {
                            em.get().setHealInsurNumber(k.getMembersNumber());
                        }
                    });
                }
            }

            if(domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER ) {
                if(!empBasicPenNumInforList.isEmpty()) {
                    empBasicPenNumInforList.stream().forEach(k -> {
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmployeeId())
                                && item.isEmpPenInsurance()
                                && item.getPersonAddChangeDate() != null).findFirst();
                        if (em.isPresent()) {
                            em.get().setBasicPenNumber(k.getBasicPenNumber().map(i -> i.v()).orElse(null));
                        }
                    });
                }
            }

            //Imported(給与)「個人情報」 with condition1,2
            //Imported（給与）「個人現住所」
            //Imported（給与）「個人前住所」
            if(!currentPersonAddressList.isEmpty()) {
                currentPersonAddressList.forEach(k->{
                    Optional<EmpAddChangeInfoExport> em =  empAddChangeInfoExportList.stream().filter(i->i.getEmpId().equals(k.getEmpId())).findFirst();
                    if(em.isPresent()&& domain.getSubmittedName() == SubNameClass.PERSONAL_NAME) {
                        em.get().setNameKanaPs(k.getPersonNameKana());
                        em.get().setFullNamePs(k.getPersonName());
                    } else if(em.isPresent()&& domain.getSubmittedName() == SubNameClass.REPORTED_NAME) {
                        em.get().setNameKanaPs(k.getTodokedeNameKana());
                        em.get().setFullNamePs(k.getTodokedeName());
                    }

                    if(em.isPresent()) {
                        em.get().setBirthDatePs(k.getBirthDate());
                        em.get().setPostCodePs(k.getPostCode());
                        em.get().setAdd1KanaPs(k.getAddress1Kana());
                        em.get().setAdd2KanaPs(k.getAddress2Kana());
                        em.get().setAdd1Ps(k.getAddress1());
                        em.get().setAdd2Ps(k.getAddress2());
                        em.get().setAdd1BeforeChange(k.getBeforeAddress1());
                        em.get().setAdd2BeforeChange(k.getBeforeAddress2());
                        em.get().setStartDatePs(k.getStartDate());
                    }
                });
            }

            //取得した「社会保険届作成設定.出力順」をチェックする--> chưa làm --> ouput order
            if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER ){
                empFamilySocialInsCtgInfoList.forEach(p->{
                    //Imported（給与）「家族情報」
                    Optional<CurrentFamilyResidence> y =  currentFamilyResidenceList.stream().filter(item->item.getFamilyId().equals(p.getFamilyId())).findFirst();
                    Optional<EmpAddChangeInfoExport> x = empAddChangeInfoExportList.stream().filter(z->z.getSpouseAddChangeDate() != null
                            && z.getSpouseAddChangeDate().afterOrEquals(p.getStartDate())
                            && z.getSpouseAddChangeDate().beforeOrEquals(p.getEndDate())
                            && z.getEmpId().equals(p.getEmpId())
                            && z.getFamilyId().equals(y.get().getFamilyId())).findFirst();
                    if(!x.isPresent()) {
                        x.get().setFmBsPenNum(p.getFmBsPenNum());
                    }
                });
            }

            //Imported（給与）「家族現住所」
            //Imported（給与）「家族現同居住所」
            //Imported（給与）「家族前住所」
            //Imported（給与）「家族前同居住所」
            currentFamilyResidenceList.forEach(t->{
                Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(o->o.getFamilyId().equals(t.getFamilyId())
                        && o.getSpouseAddChangeDate() != null
                        && o.getPersonAddChangeDate()== null).findFirst();
                if(em.isPresent()) {
                    if(t.isLivingTogether()) {
                        em.get().setPostalCodeF(t.getPostCodeTogether());
                        em.get().setAdd1KanaF(t.getAddress1KanaTogether());
                        em.get().setAdd2KanaF(t.getAddress2KanaTogether());
                        em.get().setAdd1F(t.getAddress1Together());
                        em.get().setAdd2F(t.getAddress2Together());
                    } else {
                        em.get().setPostalCodeF(t.getPostCode());
                        em.get().setAdd1KanaF(t.getAddress1Kana());
                        em.get().setAdd2KanaF(t.getAddress2Kana());
                        em.get().setAdd1F(t.getAddress1());
                        em.get().setAdd2F(t.getAddress2());
                    }

                    if(t.isLivingTogetherBefore()) {
                        em.get().setAdd1BeforeChange(t.getAdd1BeforeChangeTogether());
                        em.get().setAdd2BeforeChange(t.getAdd2BeforeChangeTogether());
                    } else {
                        em.get().setAdd1BeforeChange(t.getAdd1BeforeChange());
                        em.get().setAdd2BeforeChange(t.getAdd2BeforeChange());
                    }

                    if (domain.getSubmittedName() == SubNameClass.PERSONAL_NAME){
                        em.get().setNameKanaF(t.getNameKana());
                        em.get().setFullNameF(t.getName());
                    } else if (domain.getSubmittedName() == SubNameClass.REPORTED_NAME){
                        em.get().setNameKanaF(t.getReportNameKana());
                        em.get().setFullNameF(t.getReportName());
                    }

                    em.get().setStartDateF(em.get().getSpouseAddChangeDate());
                    em.get().setBirthDateF(t.getBirthDate());
                    em.get().setInsuredLivingTogether(true);

                    if(!t.isLivingTogether() && !t.isLivingTogetherBefore() || em.get().getPersonAddChangeDate() == null) {
                        em.get().setInsuredLivingTogether(false);
                    }
                }
            });

            empAddChangeInfoList.forEach(p->{
                Optional<EmpAddChangeInfoExport> x = empAddChangeInfoExportList.stream().filter(z->z.getEmpId().equals(p.getSid())).findFirst();
                if(!x.isPresent()) {
                    x.get().setShortResidentAtr(p.getPersonalSet().getShortResident());
                    x.get().setLivingAbroadAtr(p.getPersonalSet().getLivingAbroadAtr());
                    x.get().setResidenceOtherResidentAtr(p.getPersonalSet().getResidenceOtherResidentAtr());
                    x.get().setOtherAtr(p.getPersonalSet().getOtherAtr());
                    x.get().setOtherReason(p.getPersonalSet().getOtherReason().map(i -> i.v()).orElse(null));

                    x.get().setSpouseShortResidentAtr(p.getSpouse().getShortResident());
                    x.get().setSpouseLivingAbroadAtr(p.getSpouse().getLivingAbroadAtr());
                    x.get().setSpouseResidenceOtherResidentAtr(p.getSpouse().getResidenceOtherResidentAtr());
                    x.get().setSpouseOtherAtr(p.getSpouse().getOtherAtr());
                    x.get().setSpouseOtherReason(p.getSpouse().getOtherReason().map(i -> i.v()).orElse(null));
                }
            });

            if(domain.getOfficeInformation() == BusinessDivision.OUTPUT_COMPANY_NAME) {
                CompanyInformation  c = new CompanyInformation();
                empAddChangeInfoExportList.forEach(k->{
                    k.setPhoneNumber(c.getPhoneNumber());
                    k.setReferenceName(c.getCompanyNameReference());
                    k.setAddress1(c.getAdd1());
                    k.setAddress2(c.getAdd2());
                    k.setBussinessName(c.getCompanyName());
                });
            }
*/
            EmpAddChangeInfoExport empAddChangeInfoExport = new EmpAddChangeInfoExport(
                    "1233",
                    "1233",
                    11,
                    "1234567",
                    "nameKanaPs",
                    "fullNamePs",
                    start,
                    "1233121",
                    "add1KanaPs",
                    "add2KanaPs",
                    "add1Ps",
                    "add2Ps",
                    "add1BeforeChangePs1",
                    "add2BeforeChangePs2",
                    start,
                    "7654321",
                    1,
                    1,
                    1,
                    1,
                    "otherReason",
                    1,
                    1,
                    1,
                    1,
                    "spousesOtherReason",
                    start,
                    "nameKanaF",
                    "fullNameF",
                    "1233234",
                    "add1KanaF",
                    "add2KanaF",
                    "add1F",
                    "add2F",
                    start,
                    "add1BeforeChange",
                    "add2BeforeChange",
                    "address1",
                    "address2",
                    "businessName",
                    "referenceName",
                    "phoneNumber",
                    true,
                    true,
                    start,
                    start,
                    "1234567",
                    true,
                    "12",
                    "67"
            );
            empAddChangeInfoExportList.add(empAddChangeInfoExport);
            EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData( empAddChangeInfoExportList,baseDate );
            empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);
        } else {
            throw new BusinessException("Msg_37");
        }
    }
}
