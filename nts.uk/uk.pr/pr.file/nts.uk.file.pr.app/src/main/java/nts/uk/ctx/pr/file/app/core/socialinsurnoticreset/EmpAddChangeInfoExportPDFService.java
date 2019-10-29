package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

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
    private EmpFamilyInsHisRepository empFamilyInsHisRepository;

    @Inject
    private EmpFamilySocialInsCtgRepository empFamilySocialInsCtgRepository;

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Inject
    private WelfPenNumInformationRepository welfPenNumInformationRepository;

    @Inject
    private HealInsurPortPerIntellRepository healInsurPortPerIntellRepository;

    @Inject
    private HealthCarePortInforRepository healthCarePortInforRepository;

    @Inject
    private EmPensionFundPartiPeriodInforRepository emPensionFundPartiPeriodInforRepository;

    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private EmpFamilySocialInsRepository empFamilySocialInsRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

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

        if (printPersonNumber == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER.value || printPersonNumber == PersonalNumClass.DO_NOT_OUTPUT.value){
            List<EmpFamilySocialInsCtgInfo> empFamilySocialInsCtgInfoList = new ArrayList<>();
            List<FamilyCurrentAddress> familyCurrentAddressList = new ArrayList<>();
            List<FamilyInformation> familyInformationList = new ArrayList<>();
            List<EmpAddChangeInfoExport> empAddChangeInfoExportList = new ArrayList<>();
            List<PersonCurrentAddress> personCurrentAddressList = new ArrayList<>();
            List<FamilyResidence> familyResidenceList = new ArrayList<>();
            List<PreFamilyAddress> preFamilyAddressList = new ArrayList<>();

                empIds.forEach(j->{
                    empAddChangeInfoExportList.add(new EmpAddChangeInfoExport(j));
                });


                /*PersonCurrentAddress personCurrentAddress = new PersonCurrentAddress();
                if (personCurrentAddress != null) {
                    empAddChangeInformation.setPersonAddChangeDate(personCurrentAddress.getStartDate());
                }
                //Imported（給与）「個人現住所」PersonCurrentAddress*/

                personCurrentAddressList.forEach(q->{
                Optional<EmpAddChangeInfoExport> e = empAddChangeInfoExportList.stream().filter(i->i.getPersonId().equals(q.getPersonId())).findFirst();
                if(e.isPresent()){
                    e.get().setPersonAddChangeDate(q.getStartDate());
                }});

                //Imported（給与）「家族情報」
                FamilyInformation familyInformation = new FamilyInformation();
                if (familyInformation != null) {
                    //Imported（給与）「家族現住所」
                    FamilyCurrentAddress familyCurrentAddress = new FamilyCurrentAddress();
                    if (familyCurrentAddress != null) {
                        familyCurrentAddressList.forEach(l->{
                            Optional<EmpAddChangeInfoExport> f = empAddChangeInfoExportList.stream().filter(g->g.getFamilyId().equals(l.getFamilyId())).findFirst();
                            if(f.isPresent()){
                                f.get().setSpouseAddChangeDate(l.getStartDate());
                            }
                        });

                        empFamilySocialInsCtgInfoList.forEach(p->{
                            Optional<FamilyCurrentAddress> y =  familyCurrentAddressList.stream().filter(item->item.getFamilyId().equals(p.getFamilyId())).findFirst();
                            Optional<EmpAddChangeInfoExport> x = empAddChangeInfoExportList.stream().filter(z->z.getSpouseAddChangeDate().afterOrEquals(p.getStartDate())
                                    && z.getSpouseAddChangeDate().beforeOrEquals(p.getEndDate())
                                    && z.getEmpId().equals(p.getEmpId())
                                    && z.getFamilyId().equals(y.get().getFamilyId())).findFirst();
                            if(!x.isPresent()) {
                                //x.get().setSpouseAddChangeDate(null);
                                empAddChangeInfoExportList.remove(x.orElseGet(null));
                            }
                        });
                    }
                }
                    List<EmpHealInsurQInfo> empHealInsurQInfoList = new ArrayList<>(); //con : empIds, cid
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

                    List<EmpWelfarePenInsQualiInfo> empWelfarePenInsQualiInforList = new ArrayList<>(); //con : empIds, cid
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
                //-----------------------------
            if(!empAddChangeInfoExportList.isEmpty()){
                empAddChangeInfoExportList.forEach(i->{
                    if(i.getSpouseAddChangeDate() == null && i.getPersonAddChangeDate() == null || !i.isEmpPenInsurance() && !i.isHealthInsurance()){
                        empAddChangeInfoExportList.remove(i);
                    }
                });
            }
                //end of list emp add change information

                List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository.findByCid(cid);
                List<EmpCorpOffHisInfo> empCorpOffHisInfoList = new ArrayList<>(); //con : empIds, cid
                if(!empCorpOffHisInfoList.isEmpty()){
                    empCorpOffHisInfoList.forEach(i-> {
                       Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(item->item.getEmpId().equals(i.getEmpId())
                               && item.getSpouseAddChangeDate().afterOrEquals(i.getStartDate())
                               && item.getSpouseAddChangeDate().beforeOrEquals(i.getEndDate())).findFirst();
                       Optional<SocialInsuranceOffice> socialInsuranceOffice = socialInsuranceOfficeList.stream().filter(s->s.getCompanyID().equals(i.getCid())
                               && s.getCode().equals(i.getSocialInsurOfficeCode())).findFirst();
                       if(em.isPresent() && socialInsuranceOffice.isPresent() && domain.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){
                           //what area for data to use
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

                    List<HealInsurPortPerIntellInfo> healInsurPortPerIntellInfoList = new ArrayList<>(); //con : empIds, cid
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

                    List<EmPensionFundPartiPeriodInfo> emPensionFunList = new ArrayList<>(); //con : empIds, cid
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

                /* if(empAddChangeInfor.getPersonAddChangeDate() != null && empAddChangeInfor.isEmpPenInsurance()) {*/
                if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_PER_NUMBER) {
                    //ドメインモデル「マイナンバー」（仮）を取得する
                    //may be remove() cause related condition
                }

                if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER) {
                    //ドメインモデル「マイナンバー」（仮）を取得する
                    //check if has not data get ドメインモデル「社員基礎年金番号情報」を取得する process as same as next logic
                    //Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(cid, empAddChangeInfor.getEmpId());
                    //may be remove() cause related condition
                }

                if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER) {
                    //Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(cid, empAddChangeInfor.getEmpId());
                    List<EmpBasicPenNumInfor> empBasicPenNumInforList = empBasicPenNumInforRepository.getAllEmpBasicPenNumInfor();
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
                //}

                if(domain.getSubmittedName() == SubNameClass.PERSONAL_NAME) {
                    //Imported(給与)「個人情報」を取得する with condition1
                    PersonInformation personInformation1 = new PersonInformation();
                }

                if(domain.getSubmittedName() == SubNameClass.REPORTED_NAME) {
                    //Imported(給与)「個人情報」を取得する with condition2
                    PersonInformation personInformation2 = new PersonInformation();
                }

                //Imported（給与）「個人現住所」
                PersonCurrentAddress personCurrentAddress = new PersonCurrentAddress();

                //Imported（給与）「個人前住所」-> không biết object này lấy ra để làm gì và hiện tại k tìm thấy trong file excel và chưa tạo object
                //取得した「社会保険届作成設定.出力順」をチェックする--> chưa làm

                //if(empAddChangeInfor.getSpouseAddChangeDate() != null) {
                    //Imported（給与）「家族情報」
                    FamilyInformation familyInfor = new FamilyInformation();
                    if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_PER_NUMBER){
                        //ドメインモデル「家族マイナンバー」（仮）を取得する
                        //may be remove() cause related condition
                    }

                    if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER){
                        //ドメインモデル「家族マイナンバー」（仮）を取得する
                        //check if has not data -> ドメインモデル「社員家族社会保険履歴」
                        //same xu ly a
                        //may be remove() cause related condition
                    }

                    if (domain.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){
                        //process same first process xu ly a
                        // note : not copy yet cause of dupplicate code
                        empFamilySocialInsCtgInfoList.forEach(p->{
                            Optional<FamilyInformation> y =  familyInformationList.stream().filter(item->item.getFamilyId().equals(p.getFamilyId())).findFirst();
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

                   /* 1//Imported（給与）「家族現住所」
                    FamilyCurrentAddress familyCurrentAddress = new FamilyCurrentAddress();
                    //case : 同居別居区分
                    if (familyCurrentAddress.isLivingTogether()) {
                        if (empAddChangeInfor.getPersonAddChangeDate() != null){
                            //Imported（給与）「家族現同居住所」
                            //条件：
                            //社員ID = 対象者の社員ID
                            //基準日 = 被扶養配偶者住所変更日 -> chưa biêt đang set kiểu gì : base date có trong object familyresidence
                            FamilyResidence familyResidence = new FamilyResidence();
                            familyResidence.setBaseDate(empAddChangeInfor.getPersonAddChangeDate());
                        }
                    }
*/
                    familyCurrentAddressList.forEach(t->{
                        //1
                        Optional<FamilyResidence> fa = familyResidenceList.stream().filter(i->i.getFamilyId().equals(t.getFamilyId()) && t.isLivingTogether()).findFirst();
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(o->o.getFamilyId().equals(fa.get().getFamilyId())
                                && o.getSpouseAddChangeDate() != null && o.getPersonAddChangeDate()!= null).findFirst();
                        if(em.isPresent()) {
                            fa.get().setBaseDate(em.get().getPersonAddChangeDate());
                            //set in data export not yet
                        }
                    });



                   /*2 //Imported（給与）「家族前住所」
                    PreFamilyAddress preFamilyAddress = new PreFamilyAddress();
                    //case : 同居別居区分
                    if( preFamilyAddress.isLivingTogether()) {
                        if (empAddChangeInfor.getPersonAddChangeDate() != null){
                            //Imported（給与）「家族前同居住所」
                            //条件：
                            //社員ID = 対象者の社員ID
                            //基準日 = 被扶養配偶者住所変更日 -> chưa có object nên chưa set cho thuộc tính base date trong objetc đó được
                        }
                    }*/

                    preFamilyAddressList.forEach(i->{
                        //2
                        Optional<EmpAddChangeInfoExport> em = empAddChangeInfoExportList.stream().filter(o->o.getFamilyId().equals(i.getFamilyId())
                                && o.getSpouseAddChangeDate() != null && o.getPersonAddChangeDate()!= null && i.isLivingTogether()).findFirst();
                        //Imported（給与）「家族前同居住所」
                        //条件：
                        //社員ID = 対象者の社員ID
                        //基準日 = 被扶養配偶者住所変更日 -> chưa có object nên chưa set cho thuộc tính base date trong objetc đó được
                        //set data into data export
                    });

            List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
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
            //data hard code
            EmpAddChangeInfoExport empAddChangeInfoExport = new EmpAddChangeInfoExport(
                    "1233",
                    "1233",
                    "1233",
                    "1233",
                    "1234567",
                    "nameKana",
                    "fullNamePs",
                    start,
                    "1233121",
                    "add1KanaPs",
                    "add2KanaPs",
                    "add1Ps",
                    "add2Ps",
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
                    "spouseOtherReason",
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
                    start,
                    "1234567"
            );
            EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData();
            empAddChangeInforData.setBaseDate(baseDate);
            empAddChangeInforData.setEmpAddChangeInfoExportList(empAddChangeInfoExportList);
            empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);
        }
    }
}
