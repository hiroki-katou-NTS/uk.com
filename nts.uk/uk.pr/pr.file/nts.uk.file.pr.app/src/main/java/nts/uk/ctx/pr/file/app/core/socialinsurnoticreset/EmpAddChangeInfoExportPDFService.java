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

        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        if(end.before(start)) {
            throw new BusinessException("Msg_812");
        }
        List<EmpAddChangeInfoExport> empAddChangeInfoExportList = empAddChangeInfoExReposity.getListPerson(empIds, cid);
        if (printPersonNumber == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER.value || printPersonNumber == PersonalNumClass.DO_NOT_OUTPUT.value
                && !empAddChangeInfoExportList.isEmpty()){
            EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData();
            empAddChangeInforData.setBaseDate(baseDate);
            List<EmpFamilySocialInsCtgInfo> empFamilySocialInsCtgInfoList = empAddChangeInfoExReposity.getEmpFamilySocialInsCtgInfoList(empIds, cid);
            List<EmpHealInsurQInfo> empHealInsurQInfoList = empAddChangeInfoExReposity.getEmpHealInsurQInfoList(empIds, cid);
            List<EmpWelfarePenInsQualiInfo> empWelfarePenInsQualiInforList = empAddChangeInfoExReposity.getEmpWelfarePenInsQualiInfoList(empIds, cid);
            List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository.findByCid(cid);
            List<EmpCorpOffHisInfo> empCorpOffHisInfoList = empAddChangeInfoExReposity.getEmpCorpOffHisInfo(empIds, cid);
            List<HealInsurPortPerIntellInfo> healInsurPortPerIntellInfoList = empAddChangeInfoExReposity.getHealInsurPortPerIntellInfo(empIds, cid);
            List<EmPensionFundPartiPeriodInfo> emPensionFunList = empAddChangeInfoExReposity.getEmPensionFundPartiPeriodInfo(empIds, cid);
            List<EmpBasicPenNumInfor> empBasicPenNumInforList = empBasicPenNumInforRepository.getAllEmpBasicPenNumInfor();
            List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
            List<CurrentPersonResidence> currentPersonAddressList = new ArrayList<>();
            List<CurrentFamilyResidence> currentFamilyResidenceList = new ArrayList<>();
            GeneralDate birthdate = GeneralDate.fromString("1992/10/1","YYYYMMDD" );
            GeneralDate startDate = GeneralDate.fromString("2018/10/10","YYYYMMDD" );
            GeneralDate baseDateF = GeneralDate.fromString("2018/10/10","YYYYMMDD" );
            GeneralDate birthDateF = GeneralDate.fromString("1996/10/10","YYYYMMDD" );
            GeneralDate startDateF = GeneralDate.fromString("2018/10/10","YYYYMMDD" );
            empAddChangeInfoExportList.forEach(i->{
                CurrentPersonResidence c = new  CurrentPersonResidence("1234567", "address1Kana", "address2Kana","address1", "address2", startDate, "personNameKana", "personName", "todokedeNameKana", "todokedeName", birthdate, "beforeAddress1", "beforeAddress2");
                c.setPersonId(i.getPersonId());
                currentPersonAddressList.add(c);

                CurrentFamilyResidence f =  new CurrentFamilyResidence("9876446", "address1Kana", "address2Kana", "address1", "address2", baseDateF, true, "pctogte", "address1KanaTogether", "address2KanaTogether", "address1Together", "address2Together", "add1BeforeChange", "add2BeforeChange", "add1BeforeChangeTogether", "add2BeforeChangeTogether",  birthDateF, "nameKana", "name", "reportNameKana", "reportName", startDateF);
                f.setFamilyId(i.getFamilyId());
                currentFamilyResidenceList.add(f);
            });

            //Imported（給与）「個人現住所」
            currentPersonAddressList.forEach(q->{
            Optional<EmpAddChangeInfoExport> e = empAddChangeInfoExportList.stream().filter(i->i.getPersonId().equals(q.getPersonId())).findFirst();
            if(e.isPresent()){
                e.get().setPersonAddChangeDate(q.getStartDate());
            }});

            //Imported（給与）「家族情報」
            //Imported（給与）「家族現住所」
            currentFamilyResidenceList.forEach(l->{
                Optional<EmpAddChangeInfoExport> f = empAddChangeInfoExportList.stream().filter(g->g.getFamilyId().equals(l.getFamilyId())).findFirst();
                if(f.isPresent()){
                    f.get().setSpouseAddChangeDate(l.getStartDate());
                }
            });

            empFamilySocialInsCtgInfoList.forEach(p->{
                Optional<CurrentFamilyResidence> y =  currentFamilyResidenceList.stream().filter(item->item.getFamilyId().equals(p.getFamilyId())).findFirst();
                Optional<EmpAddChangeInfoExport> x = empAddChangeInfoExportList.stream().filter(z->z.getSpouseAddChangeDate().afterOrEquals(p.getStartDate())
                        && z.getSpouseAddChangeDate().beforeOrEquals(p.getEndDate())
                        && z.getEmpId().equals(p.getEmpId())
                        && z.getFamilyId().equals(y.get().getFamilyId())).findFirst();
                if(!x.isPresent()) {
                    empAddChangeInfoExportList.remove(x.orElseGet(null));
                }
            });

            if (!empHealInsurQInfoList.isEmpty()) {
                empHealInsurQInfoList.stream().forEach(k -> {
                    Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                            && item.getPersonAddChangeDate() != null
                            && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                            && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                    if (em.isPresent()) {
                        em.get().setHealthInsurance(true);
                        em.get().setHealInsurNumber(k.getHealInsurNumber());
                    }
                });
            }

            if (!empWelfarePenInsQualiInforList.isEmpty()) {
                empWelfarePenInsQualiInforList.stream().forEach(k -> {
                    Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                            && item.getPersonAddChangeDate() != null
                            && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                            && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                    if (em.isPresent()) {
                        em.get().setEmpPenInsurance(true);
                    }
                });
            }

            if(!empAddChangeInfoExportList.isEmpty()){
                empAddChangeInfoExportList.forEach(i->{
                    if(i.getSpouseAddChangeDate() == null && i.getPersonAddChangeDate() == null || !i.isEmpPenInsurance() && !i.isHealthInsurance()){
                        empAddChangeInfoExportList.remove(i);
                    }
                });
            }
            //end of list emp add change information

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
                });
            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM) {

                if (!empHealInsurQInfoList.isEmpty()) {
                    empHealInsurQInfoList.stream().forEach(k -> {
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item -> item.getEmpId().equals(k.getEmpId())
                                && item.getPersonAddChangeDate() != null
                                && item.getPersonAddChangeDate().afterOrEquals(k.getStartDate())
                                && item.getPersonAddChangeDate().beforeOrEquals(k.getEndDate())).findFirst();
                        if (em.isPresent()) {
                            em.get().setHealInsurNumber(k.getHealInsurNumber());
                        }
                    });
                }

            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER) {

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
                    Optional<EmpAddChangeInfoExport> em =  empAddChangeInfoExportList.stream().filter(i->i.getPersonId().equals(k.getPersonId())).findFirst();
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
                        em.get().setAdd1BeforeChange(t.getAdd1BeforeChangeTogether());
                        em.get().setAdd2BeforeChange(t.getAdd2BeforeChangeTogether());
                    } else {
                        em.get().setPostalCodeF(t.getPostCode());
                        em.get().setAdd1KanaF(t.getAddress1Kana());
                        em.get().setAdd2KanaF(t.getAddress2Kana());
                        em.get().setAdd1F(t.getAddress1());
                        em.get().setAdd2F(t.getAddress2());
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
            empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);
        }
    }
}
