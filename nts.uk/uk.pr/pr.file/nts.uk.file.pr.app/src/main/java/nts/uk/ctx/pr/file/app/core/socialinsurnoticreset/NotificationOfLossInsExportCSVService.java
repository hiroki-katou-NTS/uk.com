package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NotificationOfLossInsExportCSVService extends ExportService<NotificationOfLossInsExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private NotificationOfLossInsCSVFileGenerator notificationOfLossInsCSVFileGenerator;

	@Inject
	private NotificationOfLossInsExRepository socialInsurNotiCreateSetEx;


	@Override
	protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
		String userId = AppContexts.user().userId();
		String cid = AppContexts.user().companyId();
		NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
		SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(cid, userId,
				socialInsurNotiCreateSet.getOfficeInformation(),
				socialInsurNotiCreateSet.getBusinessArrSymbol(),
				socialInsurNotiCreateSet.getOutputOrder(),
				socialInsurNotiCreateSet.getPrintPersonNumber(),
				socialInsurNotiCreateSet.getSubmittedName(),
				socialInsurNotiCreateSet.getInsuredNumber(),
				socialInsurNotiCreateSet.getFdNumber(),
				socialInsurNotiCreateSet.getTextPersonNumber(),
				socialInsurNotiCreateSet.getLineFeedCode(),
				socialInsurNotiCreateSet.getOutputFormat()
		);
		socialInsNotifiCreateSetRegis(domain);
		if(end.before(start)) {
			throw new BusinessException("Msg_812");
		}
		if(!checkHealthInsQualificationInformation(userId) && checkWelfarePenInsQualiInformation(userId)) {
			throw new BusinessException("Msg_37");
		}
		if(domain.getInsuredNumber() == InsurPersonNumDivision.DO_NOT_OUPUT) {
			throw new BusinessException("MsgQ_174", "QSI013_21");
		}
		if(domain.getOfficeInformation() == BusinessDivision.OUTPUT_COMPANY_NAME) {
			throw new BusinessException("MsgQ_174", "QSI013_23");
		}
		if(!domain.getFdNumber().isPresent()) {
			throw new BusinessException("MsgQ_5", "QSI013_32");
		}
        EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor = empWelfarePenInsQualiInforRepository.getEmpWelfarePenInsQualiInfor(end, empIds);
        EmplHealInsurQualifiInfor emplHealInsurQualifiInfor= emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(end, empIds);
		if(domain.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE && empWelfarePenInsQualiInfor == null && emplHealInsurQualifiInfor == null) {
		    throw new BusinessException("Msg_37");
		}
		if(domain.getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO && emplHealInsurQualifiInfor == null) {
		    throw new BusinessException("Msg_37");
        }
        if(domain.getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN && empWelfarePenInsQualiInfor == null) {
            throw new BusinessException("Msg_37");
        }
        if(domain.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE){
            List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds);
            List<InsLossDataExport> welfPenInsLoss = socialInsurNotiCreateSetEx.getWelfPenInsLoss(empIds);
			notificationOfLossInsCSVFileGenerator.generate(exportServiceContext.getGeneratorContext(), new LossNotificationInformation(healthInsLoss, welfPenInsLoss, socialInsurNotiCreateSet));
        }

    }

	//社会保険届作成設定登録処理
	private void socialInsNotifiCreateSetRegis(SocialInsurNotiCreateSet domain){
		socialInsurNotiCrSetRepository.update(domain);
	}

	//社員健康保険資格情報が存在するかチェックする
	private boolean checkHealthInsQualificationInformation(String userId){
		return emplHealInsurQualifiInforRepository.checkHealInsurQualifiInformation(userId);
	}

	//社員健康保険資格情報が存在するかチェックする
	private boolean checkWelfarePenInsQualiInformation(String userId){
		return empWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInfor(userId);
	}
}
