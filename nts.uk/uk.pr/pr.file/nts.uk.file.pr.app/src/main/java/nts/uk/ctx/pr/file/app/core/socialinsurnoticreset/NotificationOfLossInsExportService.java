package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.PersonalNumClass;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NotificationOfLossInsExportService extends ExportService<NotificationOfLossInsExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private NotificationOfLossInsFileGenerator socialInsurNotiCreateSetFileGenerator;

	@Inject
	private NotificationOfLossInsExRepository socialInsurNotiCreateSetEx;


	@Override
	protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
		String userId = AppContexts.user().userId();
		String cid = AppContexts.user().companyId();
		NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();
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
		List<String> empIds = exportServiceContext.getQuery().getUserIds();
		//socialInsNotifiCreateSetRegis();
		if(!checkHealthInsQualificationInformation(userId) && checkWelfarePenInsQualiInformation(userId)) {
			throw new BusinessException("Msg_37");
		}

		if( socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.DO_NOT_OUTPUT.value) {
			List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetEx.getHealthInsLoss(empIds);
			List<InsLossDataExport> welfPenInsLoss = socialInsurNotiCreateSetEx.getWelfPenInsLoss(empIds);
			socialInsurNotiCreateSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), new LossNotificationInformation(healthInsLoss, welfPenInsLoss, socialInsurNotiCreateSet));
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

	//70歳以上被用者不該当届データ取得処理
	private void getSociaInsNoticeSet(){

	}

	//被保険者資格喪失届取得処理
	private void getInsPersonLossReport(){

	}
}
