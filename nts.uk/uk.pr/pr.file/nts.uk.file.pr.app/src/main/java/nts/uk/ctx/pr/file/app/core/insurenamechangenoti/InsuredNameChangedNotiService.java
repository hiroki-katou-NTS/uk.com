package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.file.app.core.bank.BankExportFileGenerator;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class InsuredNameChangedNotiService extends ExportService<InsuredNameChangedNotiQuery> {


    @Inject
    private CompanyAdapter company;

    @Inject
    private BankExportFileGenerator generator;

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

    private boolean processSate = false;

    private String nameEmployee = null;

    @Override
    protected void handle(ExportServiceContext<InsuredNameChangedNotiQuery> exportServiceContext) {
        Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
        String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        InsuredNameChangedNotiQuery query = exportServiceContext.getQuery();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSetDomain = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId,cid);
        if(socialInsurNotiCreateSetDomain.isPresent()){
            socialInsurNotiCrSetRepository.update(socialInsurNotiCreateSetDomain.get());
        }else{
            socialInsurNotiCrSetRepository.add(socialInsurNotiCreateSetDomain.get());
        }

        List<SocialInsuranceOffice> listSocialInsuranceOffice = socialInsuranceOfficeRepository.findByCid(cid);

        //印刷対象社員リストの社員ごとに、以下の処理をループする
        query.listEmpId.forEach(x -> {
            this.get(socialInsurNotiCreateSetDomain.get(),x,query.date);
        });

    }

    private void get(SocialInsurNotiCreateSet socialInsurNotiCreateSetDomain, String empId, GeneralDate date){

        //ドメインモデル「社員厚生年金保険資格情報」を取得する
        Optional<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInforByEmpId(empId);
        //取得できた
        if(empWelfarePenInsQualiInfors.isPresent()){
            //ドメインモデル「厚生年金種別情報」を取得する
            Optional<EmployWelPenInsurAche> employWelPenInsurAche = empWelfarePenInsQualiInfors.get().getMournPeriod().stream().filter(x -> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
            if(employWelPenInsurAche.isPresent()){
                DateHistoryItem dateHistoryItem = employWelPenInsurAche.get().getDatePeriod();
                //チェック条件を満たすデータが取得できたか確認する
                if(dateHistoryItem != null){
                    //ドメインモデル「厚生年金種別情報」を取得する
                    Optional<WelfarePenTypeInfor> welfarePenTypeInfor = welfarePenTypeInforRepository.getWelfarePenTypeInforById(dateHistoryItem.identifier());
                    //ドメインモデル「厚生年金基金加入期間情報」を取得する
                    List<EmPensionFundPartiPeriodInfor> list = emPensionFundPartiPeriodInforRepository.getEmPensionFundPartiPeriodInforByEmpId(empId);
                    //取得した「厚生年金基金加入期間情報」をチェックする
                    Optional<EmPensionFundPartiPeriodInfor> emPensionFundPartiPeriodInfor = list.stream().filter( x ->{
                        return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
                    }).findFirst();

                    if(emPensionFundPartiPeriodInfor.isPresent()){

                    }else{
                        processSate = false;
                    }

                }else{
                    //ドメインモデル「社員健康保険資格情報」を取得する
                    Optional<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfor = emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInforByEmpId(empId);
                    if(emplHealInsurQualifiInfor.isPresent()){
                        //取得した「社員健康保険資格情報」をチェックする
                        Optional<EmpHealthInsurBenefits>  empHealthInsurBenefits = emplHealInsurQualifiInfor.get().getMourPeriod().stream().filter(x -> {
                            return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
                        }).findFirst();
                        //チェック条件を満たすデータが取得できたか確認する
                        if(empHealthInsurBenefits.isPresent()){

                        }else{
                            processSate = false;
                        }
                    }else{
                        processSate = false;
                    }

                }
            }

        }
    }
    //社会保険届作成設定・被保険者整理番号区分を確認する
    private void checkInsuredNumber(GeneralDate date, String empId, SocialInsurNotiCreateSet socialInsurNotiCreateSet, EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor , EmPensionFundPartiPeriodInfor emPensionFundPartiPeriodInfor,EmpHealthInsurBenefits empHealthInsurBenefits){
        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            Optional<WelfPenNumInformation> welfPenNumInformation = welfPenNumInformationRepository.getWelfPenNumInformationById(empWelfarePenInsQualiInfor.getEmployeeId());
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER){
            emPensionFundPartiPeriodInforRepository.getFundMembershipByEmpId(emPensionFundPartiPeriodInfor.getDatePeriod().identifier());
        }else  if(socialInsurNotiCreateSet.getInsuredNumber() ==  InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM || socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
            //社会保険届作成設定・被保険者整理番号区分を確認する
            //健康保険番号を出力
            if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM){
                //ドメインモデル「健保番号情報」を取得する
                Optional<HealInsurNumberInfor> healInsurNumberInfor = emplHealInsurQualifiInforRepository.getHealInsurNumberInforByHisId(empHealthInsurBenefits.getDatePeriod().identifier());
                //go to 1
                //健保組合番号を出力
            }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
                List<HealInsurPortPerIntell> listHealInsurPortPerIntell = healInsurPortPerIntellRepository.getHealInsurPortPerIntellById(empId);
                //取得した「健保組合加入期間情報」をチェックする
                if(listHealInsurPortPerIntell.size() > 0){
                    Optional<HealInsurPortPerIntell> dateHistoryItem = listHealInsurPortPerIntell.stream().filter( x-> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
                    //チェック条件を満たすデータが取得できたか確認する
                    if(dateHistoryItem.isPresent()){
                        //ドメインモデル「健保組合情報」を取得する
                        healthCarePortInforRepository.getHealthCarePortInforById(dateHistoryItem.get().getDatePeriod().identifier());
                    }else{
                        //go to 1
                    }
                }else{
                    // go to 1
                }

            }
        }
    }

    // 1
    private boolean checkPersonNumberClass(SocialInsurNotiCreateSet socialInsurNotiCreateSet, String empId,GeneralDate date, boolean processSate){


        if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){
            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId);
        }

        //社会保険届作成設定.事業所区分を確認する
        //社会保険事業所名・住所を出力
        if(socialInsurNotiCreateSet.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){
            //ドメインモデル「社員社保事業所所属履歴」を取得する
            Optional<EmpCorpHealthOffHis> empCorpHealthOffHis = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(empId,"");
            Optional<DateHistoryItem> historyItem = empCorpHealthOffHis.get().getPeriod().stream().filter(x ->{
                return date.afterOrEquals(x.start()) && date.beforeOrEquals(x.end());
            }).findFirst();
            //ドメインモデル「所属事業所情報」を取得する
            Optional<AffOfficeInformation> affOfficeInformation = affOfficeInformationRepository.getAffOfficeInformationById(empId,historyItem.get().identifier());
        }
        //ドメインモデル「社員氏名変更届情報」を取得する
        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = empNameChangeNotiInforRepository.getEmpNameChangeNotiInforById(empId,AppContexts.user().companyId());
        processSate = true;

        return processSate;
    }

}
