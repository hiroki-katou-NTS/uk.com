package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
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
public class InsuredNameChangedNotiService extends ExportService<InsuredNameChangedNotiQuery> {

    @Inject
    private InsuredNameChangedExportFileGenerator fileGenerator;

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

    @Inject
    private WelfarePenTypeInforRepository welfarePenTypeInforRepository;

    @Inject
    private EmPensionFundPartiPeriodInforRepository emPensionFundPartiPeriodInforRepository;

    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Inject
    private EmpNameChangeNotiInforRepository empNameChangeNotiInforRepository;

    @Inject
    private WelfPenNumInformationRepository welfPenNumInformationRepository;

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private HealInsurPortPerIntellRepository healInsurPortPerIntellRepository;

    @Inject
    private HealthCarePortInforRepository healthCarePortInforRepository;

    @Inject
    private InsuredNameChangedRepository insuredNameChangedRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    private InsuredNameChangedNotiExportData data;

    @Inject
    private ManagedParallelWithContext parallel;

    //get company infor
    @Inject
    private NotificationOfLossInsExRepository notificationOfLossInsExRepository;


    private List<SocialInsuranceOffice> listSocialInsuranceOffice = new ArrayList<SocialInsuranceOffice>();

    private CompanyInfor companyInfor;

    @Override
    protected void handle(ExportServiceContext<InsuredNameChangedNotiQuery> exportServiceContext) {
        List<InsuredNameChangedNotiExportData> listData = new ArrayList<>();

        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        InsuredNameChangedNotiQuery query = exportServiceContext.getQuery();
        SocialInsurNotiCreateSetDto socialInsurNotiCreateSetDto = query.getSocialInsurNotiCreateSetDto();



        SocialInsurNotiCreateSet socialInsurNotiCreateSet = this.toDomain(socialInsurNotiCreateSetDto,userId,cid);
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSetDomain = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId,cid);
        if(socialInsurNotiCreateSetDomain.isPresent()){
            socialInsurNotiCrSetRepository.update(socialInsurNotiCreateSet);
        }else{
            socialInsurNotiCrSetRepository.add(socialInsurNotiCreateSet);
        }

        //fill to A2_???
        if(socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value){

             companyInfor = notificationOfLossInsExRepository.getCompanyInfor(cid);

        }else if(socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_SIC_INSURES.value){
            listSocialInsuranceOffice = socialInsuranceOfficeRepository.findByCid(cid);
            //印刷対象社員リストの社員ごとに、以下の処理をループする

        }

        List<EmployeeInfoEx> employeeInfo = employeeInfoAdapter.findBySIds(query.getListEmpId());

        parallel.forEach(employeeInfo, x->{
            InsuredNameChangedNotiExportData insuredNameChangedNotiExportData =  this.get(x,companyInfor,listSocialInsuranceOffice,socialInsurNotiCreateSetDomain.get(),x.getEmployeeId(),query.getDate());

            if(insuredNameChangedNotiExportData.isProcessSate()){
                listData.add(insuredNameChangedNotiExportData);
            }
        });

        if(listData.size() > 0){
            fileGenerator.generate(exportServiceContext.getGeneratorContext(),listData,socialInsurNotiCreateSet);
        }else{
            throw new BusinessException("Msg_37");
        }

    }

    private InsuredNameChangedNotiExportData get(EmployeeInfoEx employeeInfo, CompanyInfor companyInfor, List<SocialInsuranceOffice> listSocialInsuranceOffice, SocialInsurNotiCreateSet socialInsurNotiCreateSetDomain, String empId, GeneralDate date){
        data = new InsuredNameChangedNotiExportData();
        data.setEmpId(empId);
        data.setSubmitDate(date);

        data.setEmployeeInfo(employeeInfo);

        if(socialInsurNotiCreateSetDomain.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value){
            data.setCompanyInfor(companyInfor);
        }
        //ドメインモデル「社員厚生年金保険資格情報」を取得する
        Optional<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInforByEmpId(empId);

        //取得できた
        if(empWelfarePenInsQualiInfors.isPresent()){
            //ドメインモデル「厚生年金種別情報」を取得する
            Optional<EmployWelPenInsurAche> employWelPenInsurAche = empWelfarePenInsQualiInfors.get().getMournPeriod().stream().filter(x -> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
            //チェック条件を満たすデータが取得できたか確認する
            if(employWelPenInsurAche.isPresent()){
                DateHistoryItem dateHistoryItem = employWelPenInsurAche.get().getDatePeriod();
                //チェック条件を満たすデータが取得できたか確認する
                if(dateHistoryItem != null){
                    //ドメインモデル「厚生年金種別情報」を取得する
                    Optional<WelfarePenTypeInfor> welfarePenTypeInfor = welfarePenTypeInforRepository.getWelfarePenTypeInforById(dateHistoryItem.identifier());
                    data.setWelfarePenTypeInfor(welfarePenTypeInfor.isPresent() ? welfarePenTypeInfor.get() : null);

                    //ドメインモデル「厚生年金基金加入期間情報」を取得する
                    List<EmPensionFundPartiPeriodInfor> listEmPensionFundPartiPeriodInfor = emPensionFundPartiPeriodInforRepository.getEmPensionFundPartiPeriodInforByEmpId(empId);
                    //取得した「厚生年金基金加入期間情報」をチェックする
                    Optional<EmPensionFundPartiPeriodInfor> emPensionFundPartiPeriodInfor = listEmPensionFundPartiPeriodInfor.stream().filter( x ->{
                        return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
                    }).findFirst();

                    if(emPensionFundPartiPeriodInfor.isPresent()){
                        data.setEmPensionFundPartiPeriodInfor(emPensionFundPartiPeriodInfor.get());
                    }

                }
            }

        }

        //ドメインモデル「社員健康保険資格情報」を取得する

        Optional<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfor = emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInforByEmpId(empId);
        if(emplHealInsurQualifiInfor.isPresent()){
            //取得した「社員健康保険資格情報」をチェックする
            Optional<EmpHealthInsurBenefits>  empHealthInsurBenefits = emplHealInsurQualifiInfor.get().getMourPeriod().stream().filter(x -> {
                return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
            }).findFirst();
            //チェック条件を満たすデータが取得できたか確認する
            if(empHealthInsurBenefits.isPresent()){
                data.setEmpHealthInsurBenefits(empHealthInsurBenefits.get());

            }
        }

        this.checkInsuredNumber(listSocialInsuranceOffice,date,empId,socialInsurNotiCreateSetDomain,empWelfarePenInsQualiInfors.isPresent() ? empWelfarePenInsQualiInfors.get() : null,data.getEmPensionFundPartiPeriodInfor(),data.getEmpHealthInsurBenefits());
        data.setProcessSate(true);
        return data;
    }



    //社会保険届作成設定・被保険者整理番号区分を確認する
    // fill to A1_2 item
    private void checkInsuredNumber(List<SocialInsuranceOffice> listSocialInsuranceOffice, GeneralDate date, String empId, SocialInsurNotiCreateSet socialInsurNotiCreateSet, EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor , EmPensionFundPartiPeriodInfor emPensionFundPartiPeriodInfor,EmpHealthInsurBenefits empHealthInsurBenefits){

        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            //ドメインモデル「厚生年金番号情報」を取得する
            if(empWelfarePenInsQualiInfor != null){
                Optional<WelfPenNumInformation> welfPenNumInformation = welfPenNumInformationRepository.getWelfPenNumInformationById(empWelfarePenInsQualiInfor.getEmployeeId());
                data.setWelfPenNumInformation(welfPenNumInformation.isPresent() ? welfPenNumInformation.get() : null);
            }else{
                data.setProcessSate(true);
            }

        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER){
            if(emPensionFundPartiPeriodInfor != null){
                //ドメインモデル「基金加入員情報」を取得する
                Optional<FundMembership> fundMembership =  emPensionFundPartiPeriodInforRepository.getFundMembershipByEmpId(empId,emPensionFundPartiPeriodInfor.getDatePeriod().identifier());
                data.setFundMembership(fundMembership.isPresent() ? fundMembership.get() : null);
            }else{
                data.setProcessSate(true);
            }

        }else  if(socialInsurNotiCreateSet.getInsuredNumber() ==  InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM || socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
            //社会保険届作成設定・被保険者整理番号区分を確認する
            //健康保険番号を出力
            if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM){
                if(empHealthInsurBenefits != null){
                    //ドメインモデル「健保番号情報」を取得する
                    Optional<HealInsurNumberInfor> healInsurNumberInfor = emplHealInsurQualifiInforRepository.getHealInsurNumberInforByHisId(empId,empHealthInsurBenefits.getDatePeriod().identifier());
                    data.setHealInsurNumberInfor(healInsurNumberInfor.isPresent() ? healInsurNumberInfor.get() : null);
                    //go to 1
                    //健保組合番号を出力
                }else{
                    data.setProcessSate(true);
                }

            }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
                List<HealInsurPortPerIntell> listHealInsurPortPerIntell = healInsurPortPerIntellRepository.getHealInsurPortPerIntellById(empId);
                //取得した「健保組合加入期間情報」をチェックする
                if(listHealInsurPortPerIntell.size() > 0){
                    Optional<HealInsurPortPerIntell> dateHistoryItem = listHealInsurPortPerIntell.stream().filter( x-> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
                    //チェック条件を満たすデータが取得できたか確認する
                    if(dateHistoryItem.isPresent()){
                        //ドメインモデル「健保組合情報」を取得する
                        Optional<HealthCarePortInfor>  healthCarePortInfor = healthCarePortInforRepository.getHealthCarePortInforById(dateHistoryItem.get().getDatePeriod().identifier());
                        data.setHealthCarePortInfor(healthCarePortInfor.isPresent() ? healthCarePortInfor.get() : null);
                    }
                }

            }
        }

        this.checkPersonNumberClass(listSocialInsuranceOffice,socialInsurNotiCreateSet,empId,date);
    }

    //個人番号区分
    // 1
    private void checkPersonNumberClass(List<SocialInsuranceOffice> listSocialInsuranceOffice, SocialInsurNotiCreateSet socialInsurNotiCreateSet, String empId,GeneralDate date){


        if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){

            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId);
            //fill to A1_3
            data.setEmpBasicPenNumInfor(empBasicPenNumInfor.isPresent() ? empBasicPenNumInfor.get() : null);

        }

        //社会保険届作成設定.事業所区分を確認する
        //社会保険事業所名・住所を出力
        //事業所情報
        if(socialInsurNotiCreateSet.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){
            //ドメインモデル「社員社保事業所所属履歴」を取得する
            Optional<EmpCorpHealthOffHis> empCorpHealthOffHis = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(empId);

            Optional<DateHistoryItem> historyItem = empCorpHealthOffHis.isPresent() ? empCorpHealthOffHis.get().getPeriod().stream().filter(x ->{
                return date.afterOrEquals(x.start()) && date.beforeOrEquals(x.end());
            }).findFirst() : Optional.empty();
            //ドメインモデル「所属事業所情報」を取得する
            if(historyItem.isPresent()){
                Optional<AffOfficeInformation> affOfficeInformation = affOfficeInformationRepository.getAffOfficeInformationById(empId,historyItem.get().identifier());
                if(affOfficeInformation.isPresent()){
                    Optional<SocialInsuranceOffice>  socialInsuranceOffice = listSocialInsuranceOffice.stream().filter(x -> x.getCode().v().equals(affOfficeInformation.get().getSocialInsurOfficeCode().v())).findFirst();
                    data.setSocialInsuranceOffice(socialInsuranceOffice.isPresent() ? socialInsuranceOffice.get() : null);
                }
            }
        }
        //ドメインモデル「社員氏名変更届情報」を取得する
        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = empNameChangeNotiInforRepository.getEmpNameChangeNotiInforById(empId,AppContexts.user().companyId());
        //fill to A1_21, A1_23
        data.setEmpNameChangeNotiInfor(empNameChangeNotiInfor.isPresent() ? empNameChangeNotiInfor.get() : null);

    }


    private SocialInsurNotiCreateSet toDomain(SocialInsurNotiCreateSetDto dto,String userId, String cid){
        return new SocialInsurNotiCreateSet(
                userId,
                cid,
                dto.getOfficeInformation(),
                dto.getBusinessArrSymbol(),
                dto.getOutputOrder(),
                dto.getPrintPersonNumber(),
                dto.getSubmittedName(),
                dto.getInsuredNumber(),
                dto.getFdNumber(),
                dto.getTextPersonNumber(),
                dto.getOutputFormat(),
                dto.getLineFeedCode()
        );
    }

}
