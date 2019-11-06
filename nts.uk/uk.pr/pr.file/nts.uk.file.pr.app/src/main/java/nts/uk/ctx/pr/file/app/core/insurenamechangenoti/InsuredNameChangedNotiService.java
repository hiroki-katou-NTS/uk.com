package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        if(socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_SIC_INSURES.value){
            listSocialInsuranceOffice = socialInsuranceOfficeRepository.findByCid(cid);
            //印刷対象社員リストの社員ごとに、以下の処理をループする

        }
        List<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInfor(cid, query.getDate(),query.getListEmpId());
        empWelfarePenInsQualiInfors.forEach(x ->{
            InsuredNameChangedNotiExportData insuredNameChangedNotiExportData =  this.get(cid,listSocialInsuranceOffice,socialInsurNotiCreateSet,query.getDate(),x);
            insuredNameChangedNotiExportData.setSocialInsurOutOrder(query.getSocialInsurOutOrder());
            if(insuredNameChangedNotiExportData.isProcessSate()){
                listData.add(insuredNameChangedNotiExportData);
            }
        });

        if(listData.size() > 0){
            fileGenerator.generate(exportServiceContext.getGeneratorContext(),this.order(query.getSocialInsurOutOrder(),listData, query.getSocialInsurNotiCreateSetDto().getInsuredNumber()),socialInsurNotiCreateSet);
        }else{
            throw new BusinessException("Msg_37");
        }

    }

    private List<InsuredNameChangedNotiExportData> order(int order, List<InsuredNameChangedNotiExportData> listData, int insuredNumber) {

        if (order == SocialInsurOutOrder.HEAL_INSUR_NUMBER_UNION_ORDER.value) {
            listData.sort(((o1, o2) -> o1.getHealthCarePortInfor() != null && o2.getHealthCarePortInfor() != null ? o1.getHealthCarePortInfor().getHealInsurUnionNumber().v().compareTo(o2.getHealthCarePortInfor().getHealInsurUnionNumber().v()) : 0));
        } else if (order == SocialInsurOutOrder.ORDER_BY_FUND.value) {
            listData.sort(((o1, o2) -> o1.getFundMembership() != null && o2.getFundMembership() != null ? o1.getFundMembership().getMembersNumber().v().compareTo(o2.getFundMembership().getMembersNumber().v()) : 0));
        } else if (order == SocialInsurOutOrder.HEAL_INSUR_NUMBER_ORDER.value) {
            listData.sort(((o1, o2) -> o1.getHealInsurNumberInfor() != null && o2.getHealInsurNumberInfor() != null ? o1.getHealInsurNumberInfor().getHealInsNumber().get().v().compareTo(o2.getHealInsurNumberInfor().getHealInsNumber().get().v()) : 0));
        } else if (order == SocialInsurOutOrder.WELF_AREPEN_NUMBER_ORDER.value) {
            listData.sort(((o1, o2) -> o1.getWelfPenNumInformation() != null && o2.getWelfPenNumInformation() != null ? o1.getWelfPenNumInformation().getWelPenNumber().get().v().compareTo(o2.getWelfPenNumInformation().getWelPenNumber().get().v()) : 0));
        } else if (order == SocialInsurOutOrder.INSURED_PER_NUMBER_ORDER.value) {
            if (insuredNumber == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM.value) {
                listData.sort(((o1, o2) -> o1.getHealthCarePortInfor() != null && o2.getHealthCarePortInfor() != null ? o1.getHealthCarePortInfor().getHealInsurUnionNumber().v().compareTo(o2.getHealthCarePortInfor().getHealInsurUnionNumber().v()) : 0));
            }
            if (insuredNumber == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER.value) {
                listData.sort(((o1, o2) -> o1.getFundMembership() != null && o2.getFundMembership() != null ? o1.getFundMembership().getMembersNumber().v().compareTo(o2.getFundMembership().getMembersNumber().v()) : 1));
            }
            if (insuredNumber == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION.value) {
                listData.sort(((o1, o2) -> o1.getHealInsurNumberInfor() != null && o2.getHealInsurNumberInfor() != null ? o1.getHealInsurNumberInfor().getHealInsNumber().get().v().compareTo(o2.getHealInsurNumberInfor().getHealInsNumber().get().v()) : 1));
            }
            if (insuredNumber == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER.value) {
                listData.sort(((o1, o2) -> o1.getWelfPenNumInformation() != null && o2.getWelfPenNumInformation() != null ? o1.getWelfPenNumInformation().getWelPenNumber().get().v().compareTo(o2.getWelfPenNumInformation().getWelPenNumber().get().v()) : 1));
            }
        }
        return listData;
    }

    private InsuredNameChangedNotiExportData get(String cid, List<SocialInsuranceOffice> listSocialInsuranceOffice, SocialInsurNotiCreateSet socialInsurNotiCreateSetDomain, GeneralDate date, EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor){
        data = new InsuredNameChangedNotiExportData();
        data.setProcessSate(true);
        data.setEmpId(empWelfarePenInsQualiInfor.getEmployeeId());
        data.setSubmitDate(date);
        //ドメインモデル「社員厚生年金保険資格情報」を取得する
        DateHistoryItem dateHistoryItem = empWelfarePenInsQualiInfor.getMournPeriod().get(0).getDatePeriod();
        //チェック条件を満たすデータが取得できたか確認す
        //ドメインモデル「厚生年金種別情報」を取得する
        Optional<WelfarePenTypeInfor> welfarePenTypeInfor = welfarePenTypeInforRepository.getWelfarePenTypeInforById(cid, dateHistoryItem.identifier(), empWelfarePenInsQualiInfor.getEmployeeId());
        data.setWelfarePenTypeInfor(welfarePenTypeInfor.orElse(null));
        //ドメインモデル「厚生年金基金加入期間情報」を取得する
        Optional<FundMembership> emPensionFundPartiPeriodInfor = emPensionFundPartiPeriodInforRepository.getEmPensionFundPartiPeriodInfor(cid, empWelfarePenInsQualiInfor.getEmployeeId(), date);
        //取得した「厚生年金基金加入期間情報」をチェックする
        data.setFundMembership(emPensionFundPartiPeriodInfor.orElse(null));
        //ドメインモデル「社員健康保険資格情報」を取得する

        Optional<HealInsurNumberInfor> emplHealInsurQualifiInfor = emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInforByEmpId(cid, empWelfarePenInsQualiInfor.getEmployeeId(),date);
        data.setHealInsurNumberInfor(emplHealInsurQualifiInfor.orElse(null));
        this.checkInsuredNumber(cid,listSocialInsuranceOffice, date, empWelfarePenInsQualiInfor.getEmployeeId(),socialInsurNotiCreateSetDomain,empWelfarePenInsQualiInfor);
        return data;
    }



    //社会保険届作成設定・被保険者整理番号区分を確認する
    // fill to A1_2 item
    private void checkInsuredNumber(String cid, List<SocialInsuranceOffice> listSocialInsuranceOffice, GeneralDate date, String empId, SocialInsurNotiCreateSet socialInsurNotiCreateSet, EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor ){

        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            //ドメインモデル「厚生年金番号情報」を取得する
            if(empWelfarePenInsQualiInfor != null){
                Optional<WelfPenNumInformation> welfPenNumInformation = welfPenNumInformationRepository.getWelfPenNumInformationById(cid,empWelfarePenInsQualiInfor.getMournPeriod().get(0).getHistoryId(),empId);
                data.setWelfPenNumInformation(welfPenNumInformation.orElse(null));
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
