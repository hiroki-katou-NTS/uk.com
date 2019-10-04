package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
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

    private InsuredNameChangedNotiExportData data;

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


        query.getListEmpId().forEach(x ->{
            InsuredNameChangedNotiExportData insuredNameChangedNotiExportData =  this.get(cid,listSocialInsuranceOffice,socialInsurNotiCreateSet,x,query.getDate());

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

    private InsuredNameChangedNotiExportData get(String cid, List<SocialInsuranceOffice> listSocialInsuranceOffice, SocialInsurNotiCreateSet socialInsurNotiCreateSetDomain, String empId, GeneralDate date){
        data = new InsuredNameChangedNotiExportData();
        data.setProcessSate(true);
        data.setEmpId(empId);
        data.setSubmitDate(date);

        if(socialInsurNotiCreateSetDomain.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value){
            data.setCompanyInfor(companyInfor);
        }
        //ドメインモデル「社員厚生年金保険資格情報」を取得する
        Optional<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInforByEmpId(cid,empId);

        //取得できた
        if(empWelfarePenInsQualiInfors.isPresent()){
            //ドメインモデル「厚生年金  種別情報」を取得する
            Optional<EmployWelPenInsurAche> employWelPenInsurAche = empWelfarePenInsQualiInfors.get().getMournPeriod().stream().filter(x -> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
            //チェック条件を満たすデータが取得できたか確認する
            if(employWelPenInsurAche.isPresent()){
                DateHistoryItem dateHistoryItem = employWelPenInsurAche.get().getDatePeriod();
                //チェック条件を満たすデータが取得できたか確認する
                if(dateHistoryItem != null){
                    //ドメインモデル「厚生年金種別情報」を取得する
                    Optional<WelfarePenTypeInfor> welfarePenTypeInfor = welfarePenTypeInforRepository.getWelfarePenTypeInforById(cid,dateHistoryItem.identifier(),empId);
                    data.setWelfarePenTypeInfor(welfarePenTypeInfor.isPresent() ? welfarePenTypeInfor.get() : null);
                    //data.setWelfPenNumInformation(welfPenNumInformation.isPresent() ? welfPenNumInformation.get() : null);
                    //ドメインモデル「厚生年金基金加入期間情報」を取得する
                    Optional<FundMembership> emPensionFundPartiPeriodInfor = emPensionFundPartiPeriodInforRepository.getEmPensionFundPartiPeriodInfor(cid,empId,date);
                    //取得した「厚生年金基金加入期間情報」をチェックする
                    /*Optional<EmPensionFundPartiPeriodInfor> emPensionFundPartiPeriodInfor = listEmPensionFundPartiPeriodInfor.stream().filter( x ->{
                        return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
                    }).findFirst();*/

                    if(emPensionFundPartiPeriodInfor.isPresent()){
                        data.setFundMembership(emPensionFundPartiPeriodInfor.get());
                    }

                }
            }

        }

        //ドメインモデル「社員健康保険資格情報」を取得する

        Optional<HealInsurNumberInfor> emplHealInsurQualifiInfor = emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInforByEmpId(cid,empId,date);
        if(emplHealInsurQualifiInfor.isPresent()){
            //取得した「社員健康保険資格情報」をチェックする
            /*Optional<EmpHealthInsurBenefits>  empHealthInsurBenefits = emplHealInsurQualifiInfor.get().getMourPeriod().stream().filter(x -> {
                return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
            }).findFirst();*/
            //チェック条件を満たすデータが取得できたか確認する
            /*if(empHealthInsurBenefits.isPresent()){

            }*/
            data.setHealInsurNumberInfor(emplHealInsurQualifiInfor.get());
            //data.setEmpHealthInsurBenefits(emplHealInsurQualifiInfor.get().getMourPeriod().isEmpty() ? null : emplHealInsurQualifiInfor.get().getMourPeriod().get(0));
        }

        this.checkInsuredNumber(cid,listSocialInsuranceOffice,date,empId,socialInsurNotiCreateSetDomain,empWelfarePenInsQualiInfors.isPresent() ? empWelfarePenInsQualiInfors.get() : null);

        return data;
    }



    //社会保険届作成設定・被保険者整理番号区分を確認する
    // fill to A1_2 item
    private void checkInsuredNumber(String cid, List<SocialInsuranceOffice> listSocialInsuranceOffice, GeneralDate date, String empId, SocialInsurNotiCreateSet socialInsurNotiCreateSet, EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor ){

        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            //ドメインモデル「厚生年金番号情報」を取得する
            if(empWelfarePenInsQualiInfor != null){
                Optional<WelfPenNumInformation> welfPenNumInformation = welfPenNumInformationRepository.getWelfPenNumInformationById(cid,empWelfarePenInsQualiInfor.getMournPeriod().get(0).getHistoryId(),empId);
                data.setWelfPenNumInformation(welfPenNumInformation.isPresent() ? welfPenNumInformation.get() : null);
            }else{
                data.setProcessSate(false);
            }

        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER){
            if(data.getFundMembership() == null){
                data.setProcessSate(false);
            }

        }else  if(socialInsurNotiCreateSet.getInsuredNumber() ==  InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM || socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
            //社会保険届作成設定・被保険者整理番号区分を確認する
            //健康保険番号を出力
            if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM){
                if(data.getHealInsurNumberInfor() == null){
                    data.setProcessSate(false);
                }
            }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
                Optional<HealthCarePortInfor> healthCarePortInfor = healInsurPortPerIntellRepository.getHealInsurPortPerIntellById(cid,empId,date);

                if(healthCarePortInfor.isPresent()){
                    data.setHealthCarePortInfor(healthCarePortInfor.get());
                }else{
                    data.setProcessSate(false);
                }
            }
        }

        this.checkPersonNumberClass(cid,listSocialInsuranceOffice,socialInsurNotiCreateSet,empId,date);
    }

    //個人番号区分
    // 1
    private void checkPersonNumberClass(String cid, List<SocialInsuranceOffice> listSocialInsuranceOffice, SocialInsurNotiCreateSet socialInsurNotiCreateSet, String empId,GeneralDate date){


        /*if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){

            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(cid,empId);
            //fill to A1_3
            data.setEmpBasicPenNumInfor(empBasicPenNumInfor.isPresent() ? empBasicPenNumInfor.get() : null);

        }*/

        //社会保険届作成設定.事業所区分を確認する
        //社会保険事業所名・住所を出力
        //事業所情報
        if(socialInsurNotiCreateSet.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){

            Optional<String> socialInsuranceOfficeCd = empCorpHealthOffHisRepository.getSocialInsuranceOfficeCd(cid,empId,date);
            if(socialInsuranceOfficeCd.isPresent()){
                Optional<SocialInsuranceOffice>  socialInsuranceOffice = listSocialInsuranceOffice.stream().filter(x -> x.getCode().v().equals(socialInsuranceOfficeCd.get())).findFirst();
                data.setSocialInsuranceOffice(socialInsuranceOffice.isPresent() ? socialInsuranceOffice.get() : null);
            }
        }
        //ドメインモデル「社員氏名変更届情報」を取得する
        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = empNameChangeNotiInforRepository.getEmpNameChangeNotiInforById(empId,cid);
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
