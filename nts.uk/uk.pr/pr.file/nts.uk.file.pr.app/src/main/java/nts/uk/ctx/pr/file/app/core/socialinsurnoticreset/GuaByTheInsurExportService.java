package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.InsurPersonNumDivision;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.query.pub.person.EmployeeInfoExport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class GuaByTheInsurExportService extends ExportService {

    @Inject
    private GuaByTheInsurExportGenerator generator;

    @Inject
    private GuaByTheInsurExportRepository repo;

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository mEmpWelfarePenInsQualiInforRepository;

    @Inject
    private SocialInsurNotiCrSetRepository mSocialInsurNotiCrSetRepository;

    @Inject
    private EmployeeInfoExport mEmployeeInfoExport;

    @Override
    protected void handle(ExportServiceContext exportServiceContext) {

              generator.generate(exportServiceContext.getGeneratorContext(),new ArrayList<>());

    }

    public void printInsuredQualifiNoti(List<String> employeeIds, GuaByTheInsurDto model, GeneralDate startDate, GeneralDate endDate) {
        settingRegisProcess(model);
        checkAcquiNotiInsurProcess(employeeIds,startDate,endDate);
        insurQualiNotiProcess(employeeIds);
    }

    private void settingRegisProcess(GuaByTheInsurDto model) {

        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        SocialInsurNotiCreateSet input = new SocialInsurNotiCreateSet(
                cid,
                uid,
                model.getSocialInsurNotiCreateSet().get().getOfficeInformation(),
                model.getSocialInsurNotiCreateSet().get().getBusinessArrSymbol(),
                model.getSocialInsurNotiCreateSet().get().getOutputOrder(),
                model.getSocialInsurNotiCreateSet().get().getPrintPersonNumber(),
                model.getSocialInsurNotiCreateSet().get().getSubmittedName(),
                InsurPersonNumDivision.DO_NOT_OUPUT,
                model.getSocialInsurNotiCreateSet().get().getFdNumber(),
                model.getSocialInsurNotiCreateSet().get().getTextPersonNumber(),
                model.getSocialInsurNotiCreateSet().get().getOutputFormat(),
                model.getSocialInsurNotiCreateSet().get().getLineFeedCode()

        );
        if (!socialInsurNotiCreateSet.isPresent()) {
            mSocialInsurNotiCrSetRepository.add(input);
            return;
        }
        input.setInsuredNumber(model.getSocialInsurNotiCreateSet().get().getInsuredNumber());
        mSocialInsurNotiCrSetRepository.update(input);
        return;
    }

    private void checkAcquiNotiInsurProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        List<EmplHealInsurQualifiInfor> empExistHeal = new ArrayList<>();
        /* startDate > endDate*/
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        /*アルゴリズム「社員健康保険資格情報」を取得する*/
        List<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfors = emplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor();
        EmplHealInsurQualifiInfor temp;
        for (int i = 0; i < employeeIds.size(); i++) {
            int finalI = i;
            temp = emplHealInsurQualifiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
            boolean checkHeal = temp.getMourPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
            if (checkHeal) {
                empExistHeal.add(temp);
            }
        }

        //アルゴリズム「社員厚生年金保険資格情報」を取得する
        List<EmpWelfarePenInsQualiInfor> empExistWelfare = new ArrayList<>();
        List<EmpWelfarePenInsQualiInfor>  empWelfarePenInsQualiInfors = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor();
        EmpWelfarePenInsQualiInfor tempEmpWelfare;
        for (int i = 0; i < employeeIds.size(); i++) {
            int finalI = i;
            tempEmpWelfare = empWelfarePenInsQualiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
            boolean checkHeal = tempEmpWelfare.getMournPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
            if (checkHeal) {
                empExistWelfare.add(tempEmpWelfare);
            }
        }



    }

    private void insurQualiNotiProcess(List<String> employeeIds){
        final int DO_NOT_OUTPUT = 3;
        final int OUTPUT_BASIC_PER_NUMBER = 1;
        final int OUTPUT_COMPANY_NAME_BusinessDivision = 0;
        final int OUTPUT_SIC_INSURES_BusinessDivision = 1;
        final int DO_NOT_OUTPUT_BusinessDivision = 2;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet =  mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid,cid);
        int valuePrintPersonNumber = socialInsurNotiCreateSet.get().getPrintPersonNumber().value ;
        int valueofficeInformation = socialInsurNotiCreateSet.get().getOfficeInformation().value ;
        if(valuePrintPersonNumber == DO_NOT_OUTPUT ||valuePrintPersonNumber == OUTPUT_BASIC_PER_NUMBER){
            switch (valueofficeInformation){
                case OUTPUT_COMPANY_NAME_BusinessDivision :{
                    break;
                }
                case OUTPUT_SIC_INSURES_BusinessDivision :
                {
                    break;
                }
                case DO_NOT_OUTPUT_BusinessDivision:{
                    break;
                }
            }
            //
            employeeIds.stream().forEach(e -> {

            });

        }
    }
}
