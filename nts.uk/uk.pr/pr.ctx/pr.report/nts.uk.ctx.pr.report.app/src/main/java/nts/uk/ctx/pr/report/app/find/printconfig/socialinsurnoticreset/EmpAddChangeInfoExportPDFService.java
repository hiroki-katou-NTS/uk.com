package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.List;

public class EmpAddChangeInfoExportPDFService extends ExportService<EmpAddChangeInfoExportQuery> {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private EmpAddChangeInfoRepository empAddChangeInfoRepository;

    @Override
    protected void handle(ExportServiceContext<EmpAddChangeInfoExportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
        int printPersonNumber = exportServiceContext.getQuery().getSocialInsurNotiCreateSet().getPrintPersonNumber();
        EmpAddChangeInfoExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
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
        }
    }
}
