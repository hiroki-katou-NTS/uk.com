package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmpAddChangeInfoExportPDFService extends ExportService<NotificationOfLossInsExportQuery> {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private EmpAddChangeInfoRepository empAddChangeInfoRepository;

    @Inject
    private EmpAddChangeInfoFileGenerator empAddChangeInfoFileGenerator;

    @Override
    protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
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

        if ( printPersonNumber == 1 || printPersonNumber == 3){
            List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
            for (int i = 0; i < empIds.size(); i++) {
                //ワーククラス「住所変更届情報」をチェックする
                EmpAddChangeInformation empAddChangeInformation = new EmpAddChangeInformation();
                empAddChangeInformation.setEmpId(empIds.get(i));
            }
        }

        List<EmpAddChangeInfoExport> empAddChangeInfoExportList = new ArrayList<>();
        EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData();
        empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);

    }
}
