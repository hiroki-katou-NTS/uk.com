package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
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
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoImport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

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

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Inject
    private FamilyMemberAdapter familyMemberAdapter;

    @Inject
    private CompanyInforAdapter companyInforAdapter;

    private List<EmpAddChangeInfoExport> empAddChangeInfoExportList;

    private List<EmpAddChangeInfoExport> eList;

    private List<CurrentFamilyResidence> currentFamilyResidenceList;

    @Override
    protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
        GeneralDate baseDate = exportServiceContext.getQuery().getReference();
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
        if (end.before(start)) {
            throw new BusinessException("Msg_812");
        }

        List<EmployeeInfoEx> employeeInfoExList = employeeInfoAdapter.findBySIds(empIds);
        List<EmpFamilySocialInsCtgInfo> empFamilySocialInsCtgInfoList = empAddChangeInfoExReposity.getEmpFamilySocialInsCtgInfoList(empIds, cid);
        List<EmpHealInsurQInfo> empHealInsurQInfoList = empAddChangeInfoExReposity.getEmpHealInsurQInfoList(empIds, cid);
        List<EmpWelfarePenInsQualiInfo> empWelfarePenInsQualiInforList = empAddChangeInfoExReposity.getEmpWelfarePenInsQualiInfoList(empIds, cid);
        List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository.findByCid(cid);
        List<EmpCorpOffHisInfo> empCorpOffHisInfoList = empAddChangeInfoExReposity.getEmpCorpOffHisInfo(empIds, cid);
        List<HealInsurPortPerIntellInfo> healInsurPortPerIntellInfoList = empAddChangeInfoExReposity.getHealInsurPortPerIntellInfo(empIds, cid);
        List<EmPensionFundPartiPeriodInfo> emPensionFunList = empAddChangeInfoExReposity.getEmPensionFundPartiPeriodInfo(empIds, cid);
        List<EmpBasicPenNumInfor> empBasicPenNumInforList = empBasicPenNumInforRepository.getAllEmpBasicPenNumInfor(empIds);
        List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
        List<String> pIds = new ArrayList<>();
        eList = new ArrayList<>();
        currentFamilyResidenceList = new ArrayList<>();
        empAddChangeInfoExportList = new ArrayList<>();
        employeeInfoExList.forEach(i -> {
            pIds.add(i.getPId());
            CurrentFamilyResidence cr = CurrentFamilyResidence.getListFamily(familyMemberAdapter.getRomajiOfFamilySpouseByPid(i.getPId()), i.getPId());
            if (cr != null) {
                currentFamilyResidenceList.add(cr);
            }
        });

        List<CurrentPersonResidence> currentPersonAddressList = CurrentPersonResidence.createListPerson(personExportAdapter.findByPids(pIds));
        currentPersonAddressList.forEach(ix -> {
            EmpAddChangeInfoExport exx = new EmpAddChangeInfoExport();
            Optional<EmployeeInfoEx> elx = employeeInfoExList.stream().filter(ee -> ee.getPId().equals(ix.getPId())
                    && ix.getStartDate() != null
                    && ix.getStartDate().afterOrEquals(start)
                    && ix.getStartDate().beforeOrEquals(end)).findFirst();
            if (elx.isPresent()) {
                exx.setPId(ix.getPId());
                exx.setEmpId(elx.get().getEmployeeId());
                exx.setScd(elx.get().getEmployeeCode());
                exx.setPersonAddChangeDate(ix.getStartDate());
            }
            Optional<CurrentFamilyResidence> cf = currentFamilyResidenceList.stream().filter(f -> f.getPersonId().equals(ix.getPId())
                    && f.getStartDate().afterOrEquals(start)
                    && f.getStartDate().beforeOrEquals(end)).findFirst();
            if (cf.isPresent()) {
                exx.setSpouseAddChangeDate(cf.get().getStartDate());
                exx.setFamilyId(cf.get().getFamilyId());
            }
            empAddChangeInfoExportList.add(exx);
        });

        empAddChangeInfoExportList.forEach(e -> {
            Optional<EmpFamilySocialInsCtgInfo> ef = empFamilySocialInsCtgInfoList.stream().filter(fe -> fe.getFamilyId().equals(e.getFamilyId())
                    && e.getEmpId().equals(fe.getEmpId())
                    && e.getSpouseAddChangeDate() != null
                    && e.getSpouseAddChangeDate().beforeOrEquals(fe.getEndDate())
                    && e.getSpouseAddChangeDate().afterOrEquals(fe.getStartDate())
                    && fe.getDependent() == 1).findFirst();
            if (!ef.isPresent()) {
                e.setSpouseAddChangeDate(null);
            }

            Optional<EmpHealInsurQInfo> eh = empHealInsurQInfoList.stream().filter(item -> item.getEmpId().equals(e.getEmpId())
                    && e.getPersonAddChangeDate() != null
                    && e.getPersonAddChangeDate().afterOrEquals(item.getStartDate())
                    && e.getPersonAddChangeDate().beforeOrEquals(item.getEndDate())).findFirst();
            if (eh.isPresent()) {
                e.setHealthInsurance(true);
                if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM) {
                    e.setHealInsurNumber(eh.get().getHealInsurNumber());
                }
            }

            Optional<EmpWelfarePenInsQualiInfo> ew = empWelfarePenInsQualiInforList.stream().filter(item -> item.getEmpId().equals(e.getEmpId())
                    && (e.getPersonAddChangeDate() != null
                    && e.getPersonAddChangeDate().afterOrEquals(item.getStartDate())
                    && e.getPersonAddChangeDate().beforeOrEquals(item.getEndDate()))
                    || (e.getPersonAddChangeDate() == null //1
                    && e.getSpouseAddChangeDate() != null
                    && e.getSpouseAddChangeDate().afterOrEquals(item.getStartDate())
                    && e.getSpouseAddChangeDate().beforeOrEquals(item.getEndDate()))).findFirst();

            if (ew.isPresent()) {
                e.setEmpPenInsurance(true);
            }
            //end of list emp add change information
            if ((e.getSpouseAddChangeDate() != null || e.getPersonAddChangeDate() != null) && (e.isEmpPenInsurance() || e.isHealthInsurance())) {
                eList.add(e);
            }
        });

        if (eList.isEmpty()) {
            throw new BusinessException("Msg_37");
        }

        eList.forEach(el -> {
            Optional<EmpCorpOffHisInfo> corp = empCorpOffHisInfoList.stream().filter(cor -> cor.getEmpId().equals(el.getEmpId())
                    && el.getPersonAddChangeDate() != null
                    && el.getPersonAddChangeDate().afterOrEquals(cor.getStartDate())
                    && el.getPersonAddChangeDate().beforeOrEquals(cor.getEndDate())).findFirst();

            if (corp.isPresent()) {
                Optional<SocialInsuranceOffice> socialInsuranceOffice = socialInsuranceOfficeList.stream().filter(soc -> soc.getCompanyID().equals(corp.get().getCid())
                        && soc.getCode().equals(corp.get().getSocialInsurOfficeCode())).findFirst();
                if (socialInsuranceOffice.isPresent() && domain.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES) {
                    el.setBussinessName(socialInsuranceOffice.get().getName().v());
                    if (socialInsuranceOffice.get().getBasicInformation() != null) {
                        if (socialInsuranceOffice.get().getBasicInformation().getAddress().isPresent()) {
                            el.setAddress1(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getAddress1().map(l -> l.v()).orElse(null));
                            el.setAddress2(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getAddress2().map(l -> l.v()).orElse(null));
                            el.setPhoneNumber(socialInsuranceOffice.get().getBasicInformation().getAddress().get().getPhoneNumber().map(l -> l.v()).orElse(null));
                        }
                        el.setReferenceName(socialInsuranceOffice.get().getBasicInformation().getRepresentativeName().map(l -> l.v()).orElse(null));
                    }
                }

                if (socialInsuranceOffice.isPresent() && domain.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL) {
                    if (socialInsuranceOffice.get().getInsuranceMasterInformation() != null
                            && socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber() != null) {
                        if (socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().isPresent()) {
                            el.setBusinessEstCode1(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v());
                        }

                        if (socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().isPresent()) {
                            el.setBusinessEstCode2(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v());
                        }
                    }
                } else if (socialInsuranceOffice.isPresent() && domain.getBusinessArrSymbol() == BussEsimateClass.EMPEN_ESTAB_REARSIGN) {
                    if (socialInsuranceOffice.get().getInsuranceMasterInformation() != null
                            && socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber() != null) {
                        if (socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().isPresent()) {
                            el.setBusinessEstCode1(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().get().v());
                        }

                        if (socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().isPresent()) {
                            el.setBusinessEstCode2(socialInsuranceOffice.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().get().v());
                        }
                    }
                }

            }
            if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER) {
                Optional<EmpWelfarePenInsQualiInfo> welff = empWelfarePenInsQualiInforList.stream().filter(wel -> wel.getEmpId().equals(el.getEmpId())
                        && el.getPersonAddChangeDate() != null
                        && el.getPersonAddChangeDate().afterOrEquals(wel.getStartDate())
                        && el.getPersonAddChangeDate().beforeOrEquals(wel.getEndDate())).findFirst();
                welff.ifPresent(empWelfarePenInsQualiInfo -> el.setHealInsurNumber(empWelfarePenInsQualiInfo.getWelPenNumber()));
            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION) {
                Optional<HealInsurPortPerIntellInfo> heal = healInsurPortPerIntellInfoList.stream().filter(hea -> hea.getEmpId().equals(el.getEmpId())
                        && el.getPersonAddChangeDate() != null
                        && el.getPersonAddChangeDate().afterOrEquals(hea.getStartDate())
                        && el.getPersonAddChangeDate().beforeOrEquals(hea.getEndDate())).findFirst();
                heal.ifPresent(healInsurPortPerIntellInfo -> el.setHealInsurNumber(healInsurPortPerIntellInfo.getHealInsurUnionNumber()));
            } else if (domain.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER) {
                Optional<EmPensionFundPartiPeriodInfo> pens = emPensionFunList.stream().filter(pen -> pen.getEmpId().equals(el.getEmpId())
                        && el.getPersonAddChangeDate() != null
                        && el.getPersonAddChangeDate().afterOrEquals(pen.getStartDate())
                        && el.getPersonAddChangeDate().beforeOrEquals(pen.getEndDate())).findFirst();

                pens.ifPresent(emPensionFundPartiPeriodInfo -> el.setHealInsurNumber(emPensionFundPartiPeriodInfo.getMembersNumber()));
            }

            if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER
                    || domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER) {
                Optional<EmpBasicPenNumInfor> bas = empBasicPenNumInforList.stream().filter(base -> base.getEmployeeId().equals(el.getEmpId())
                        && el.isEmpPenInsurance()
                        && el.getPersonAddChangeDate() != null).findFirst();
                bas.ifPresent(empBasicPenNumInfor -> el.setBasicPenNumber(empBasicPenNumInfor.getBasicPenNumber().map(PrimitiveValueBase::v).orElse(null)));
                Optional<EmpFamilySocialInsCtgInfo> fam = empFamilySocialInsCtgInfoList.stream().filter(famik -> famik.getEmpId().equals(el.getEmpId())
                        && el.getSpouseAddChangeDate() != null
                        && el.getSpouseAddChangeDate().afterOrEquals(famik.getStartDate())
                        && el.getSpouseAddChangeDate().beforeOrEquals(famik.getEndDate())
                        && el.getFamilyId().equals(famik.getFamilyId())).findFirst();
                fam.ifPresent(empFamilySocialInsCtgInfo -> el.setFmBsPenNum(empFamilySocialInsCtgInfo.getFmBsPenNum()));
            }

            //Imported(給与)「個人情報」
            //Imported（給与）「個人現住所」
            //Imported（給与）「個人前住所」
            Optional<CurrentPersonResidence> curP = currentPersonAddressList.stream().filter(cure -> cure.getPId().equals(el.getPId())
                    && el.getPersonAddChangeDate() != null
                    && el.getPersonAddChangeDate().afterOrEquals(start)
                    && el.getPersonAddChangeDate().beforeOrEquals(end)).findFirst();

            if (curP.isPresent() && domain.getSubmittedName() == SubNameClass.PERSONAL_NAME) {
                el.setNameKanaPs(curP.get().getPersonNameKana());
                el.setFullNamePs(curP.get().getPersonName());
            } else if (curP.isPresent() && domain.getSubmittedName() == SubNameClass.REPORTED_NAME) {
                el.setNameKanaPs(curP.get().getTodokedeNameKana());
                el.setFullNamePs(curP.get().getTodokedeName());
            }
            if (curP.isPresent()) {
                el.setBirthDatePs(curP.get().getBirthDate());
                el.setPostCodePs(curP.get().getPostCode());
                el.setAdd1KanaPs(curP.get().getAddress1Kana());
                el.setAdd2KanaPs(curP.get().getAddress2Kana());
                el.setAdd1Ps(curP.get().getAddress1());
                el.setAdd2Ps(curP.get().getAddress2());
                el.setAdd1BeforeChangePs(curP.get().getAddress1BeforeChangePs());
                el.setAdd2BeforeChangePs(curP.get().getAddress2BeforeChangePs());
                el.setStartDatePs(curP.get().getStartDate());
            }

            Optional<EmpAddChangeInfo> emp = empAddChangeInfoList.stream().filter(ea -> ea.getSid().equals(el.getEmpId())).findFirst();
            if (emp.isPresent()) {
                el.setShortResidentAtr(emp.get().getPersonalSet().getShortResident());
                el.setLivingAbroadAtr(emp.get().getPersonalSet().getLivingAbroadAtr());
                el.setResidenceOtherResidentAtr(emp.get().getPersonalSet().getResidenceOtherResidentAtr());
                el.setOtherAtr(emp.get().getPersonalSet().getOtherAtr());
                el.setOtherReason(emp.get().getPersonalSet().getOtherReason().map(i -> i.v()).orElse(null));

                el.setSpouseShortResidentAtr(emp.get().getSpouse().getShortResident());
                el.setSpouseLivingAbroadAtr(emp.get().getSpouse().getLivingAbroadAtr());
                el.setSpouseResidenceOtherResidentAtr(emp.get().getSpouse().getResidenceOtherResidentAtr());
                el.setSpouseOtherAtr(emp.get().getSpouse().getOtherAtr());
                el.setSpouseOtherReason(emp.get().getSpouse().getOtherReason().map(i -> i.v()).orElse(null));
            }

            //Imported（給与）「家族情報」
            Optional<CurrentFamilyResidence> cur = currentFamilyResidenceList.stream().filter(cu -> cu.getFamilyId().equals(el.getFamilyId())
                    && el.getPId().equals(cu.getPersonId())
                    && el.getSpouseAddChangeDate() != null).findFirst();
            if (cur.isPresent()) {
                if (domain.getSubmittedName() == SubNameClass.PERSONAL_NAME) {
                    el.setNameKanaF(cur.get().getNameKana());
                    el.setFullNameF(cur.get().getName());
                } else if (domain.getSubmittedName() == SubNameClass.REPORTED_NAME) {
                    el.setNameKanaF(cur.get().getReportNameKana());
                    el.setFullNameF(cur.get().getReportName());
                }
                el.setBirthDateF(cur.get().getBirthDate());
                el.setStartDateF(el.getSpouseAddChangeDate());
                if (el.getSpouseAddChangeDate().beforeOrEquals(end)
                        && el.getSpouseAddChangeDate().afterOrEquals(start)) {
                    //Imported（給与）「家族現住所」
                    //Imported（給与）「家族前住所」
                    el.setPostalCodeF(cur.get().getPostCode());
                    el.setAdd1KanaF(cur.get().getAddress1Kana());
                    el.setAdd2KanaF(cur.get().getAddress2Kana());
                    el.setAdd1F(cur.get().getAddress1());
                    el.setAdd2F(cur.get().getAddress2());
                    el.setAdd1BeforeChangeF(cur.get().getAddress1BeforeChangeF());
                    el.setAdd2BeforeChangeF(cur.get().getAddress2BeforeChangeF());
                    el.setInsuredLivingTogether(true);
                    if (!cur.get().isLivingTogether() || el.getPersonAddChangeDate() == null) {
                        el.setInsuredLivingTogether(false);
                    }
                    if (cur.get().isLivingTogether() && el.getPersonAddChangeDate() == null) {
                        //Imported（給与）「家族現同居住所」
                        //Imported（給与）「家族前同居住所」
                        //re-set item post code , address at before - dummy data is living separately not together
                    }
                }
            }
        });

        if (domain.getOfficeInformation() == BusinessDivision.OUTPUT_COMPANY_NAME) {
            CompanyInfor c = companyInforAdapter.getCompanyNotAbolitionByCid(cid);
            eList.forEach(k -> {
                k.setPhoneNumber(c.getPhoneNum() != null && c.getPhoneNum().length() > 0 ? c.getPhoneNum() : "");
                k.setReferenceName(c.getRepname() != null && c.getRepname().length() > 0 ? c.getRepname() : "");
                k.setAddress1(c.getAdd_1() != null && c.getAdd_1().length() > 0 ? c.getAdd_1() : "");
                k.setAddress2(c.getAdd_2() != null && c.getAdd_2().length() > 0 ? c.getAdd_2() : "");
                k.setBussinessName(c.getCompanyName() != null && c.getCompanyName().length() > 0 ? c.getCompanyName() : "");
            });
        }

        //order
        if (!eList.isEmpty()) {
            if (domain.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_CODE_ORDER) {
                eList = eList.stream().sorted(Comparator.comparing(EmpAddChangeInfoExport::getScd, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
            } else if (domain.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_KANA_ORDER) {
                eList = eList.stream().sorted(Comparator.comparing(EmpAddChangeInfoExport::getNameKanaPs, Comparator.nullsLast(String::compareTo)).thenComparing(EmpAddChangeInfoExport::getScd, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
            } else if ((domain.getOutputOrder() == SocialInsurOutOrder.INSURED_PER_NUMBER_ORDER && domain.getInsuredNumber() != InsurPersonNumDivision.DO_NOT_OUPUT)
                    || domain.getOutputOrder() != SocialInsurOutOrder.HEAL_INSUR_NUMBER_ORDER
                    || domain.getOutputOrder() != SocialInsurOutOrder.WELF_AREPEN_NUMBER_ORDER
                    || domain.getOutputOrder() != SocialInsurOutOrder.HEAL_INSUR_NUMBER_UNION_ORDER
                    || domain.getOutputOrder() != SocialInsurOutOrder.ORDER_BY_FUND) {
                eList = eList.stream().sorted(Comparator.comparing(EmpAddChangeInfoExport::getHealInsurNumber, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
            }
        }

        EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData(eList, baseDate);
        empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);
    }
}
