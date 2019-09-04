package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.file.app.core.bank.BankExportFileGenerator;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfomationAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfoExportAdapter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
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
    private PersonInfomationAdapter personAdapter;

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

        query.listEmpId.forEach(x -> {
            this.get(x,query.date);
        });

    }

    private void get(String empId, GeneralDate date){
        PersonInfoExportAdapter personInfo = personAdapter.getPersonInfo(empId);
        Optional<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInforByEmpId(empId);

        if(empWelfarePenInsQualiInfors.isPresent()){
            DateHistoryItem dateHistoryItem= this.check(empWelfarePenInsQualiInfors.get(),date);
            if(dateHistoryItem != null){
                Optional<WelfarePenTypeInfor> welfarePenTypeInfor = welfarePenTypeInforRepository.getWelfarePenTypeInforById(dateHistoryItem.identifier());
                List<EmPensionFundPartiPeriodInfor> list = emPensionFundPartiPeriodInforRepository.getEmPensionFundPartiPeriodInforByEmpId(empId);
                Optional<EmPensionFundPartiPeriodInfor> emPensionFundPartiPeriodInfor = this.check(list,date);
                if(emPensionFundPartiPeriodInfor.isPresent()){

                }
            }
        }


    }

    private DateHistoryItem check(EmpWelfarePenInsQualiInfor domain, GeneralDate date){
        Optional<EmployWelPenInsurAche> employWelPenInsurAche = domain.getMournPeriod().stream().filter(x -> date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end())).findFirst();
        if(employWelPenInsurAche.isPresent()){
            return employWelPenInsurAche.get().getDatePeriod();
        }

        return null;
    }

    private Optional<EmPensionFundPartiPeriodInfor> check(List<EmPensionFundPartiPeriodInfor> list, GeneralDate date){
        return list.stream().filter(x -> {
             return date.afterOrEquals(x.getDatePeriod().start()) && date.beforeOrEquals(x.getDatePeriod().end());
        }).findFirst();
    }
    // 1
    private void checkSocialInsurNotiCrSet(SocialInsurNotiCreateSet socialInsurNotiCreateSet, String empId,GeneralDate date){

        //出力しない
        if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER){
            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId);
        }

        if(socialInsurNotiCreateSet.getOfficeInformation() == BusinessDivision.OUTPUT_SIC_INSURES){
            Optional<EmpCorpHealthOffHis> empCorpHealthOffHis = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(empId,"");
            Optional<DateHistoryItem> historyItem = this.getHistoryID(empCorpHealthOffHis.get(),date);
            Optional<AffOfficeInformation> affOfficeInformation = affOfficeInformationRepository.getAffOfficeInformationById(empId,historyItem.get().identifier());
        }

        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = empNameChangeNotiInforRepository.getEmpNameChangeNotiInforById(empId,AppContexts.user().companyId());

    }

    //
    private Optional<DateHistoryItem> getHistoryID(EmpCorpHealthOffHis domain, GeneralDate date){
        Optional<DateHistoryItem> historyItem = domain.getPeriod().stream().filter(x ->{
            return date.afterOrEquals(x.start()) && date.beforeOrEquals(x.end());
        }).findFirst();

        return historyItem;
    }
}
