package nts.uk.ctx.pr.file.app.core.temp;

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
import java.util.Optional;

@Stateless
public class SocialInsurNotiCreateSetExportService extends ExportService<SocialInsurNotiCreateSetExportQuery> {

	@Inject
	private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

	@Inject
	private EmpWelfarePenInsQualiInforRepository empWelfarePenInsQualiInforRepository;

	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private SocialInsurNotiCreateSetFileGenerator socialInsurNotiCreateSetFileGenerator;

	@Inject
	private SocialInsurNotiCreateSetRepository socialInsurNotiCreateSetRepository;


	@Override
	protected void handle(ExportServiceContext<SocialInsurNotiCreateSetExportQuery> exportServiceContext) {
		String userId = AppContexts.user().userId();
		List<String> empIds = exportServiceContext.getQuery().getUserIds();
		List<InsLossDataExport> healthInsLoss = socialInsurNotiCreateSetRepository.getHealthInsLoss(empIds);
		List<InsLossDataExport> welfPenInsLoss = socialInsurNotiCreateSetRepository.getWelfPenInsLoss(empIds);
		SocialInsurNotiCreateSetExport socialInsurNotiCreateSet = socialInsurNotiCreateSetRepository.getSocialInsurNotiCreateSet(userId);
		socialInsurNotiCreateSetFileGenerator.generate(exportServiceContext.getGeneratorContext(), new SocialInsurNotiCreateSetExportData(healthInsLoss, welfPenInsLoss, socialInsurNotiCreateSet));
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

	//被保険者資格喪失届印刷処理
	private void getSocialInsurNotiCreateSet(){
		String cid = AppContexts.user().userId();
		String userId = AppContexts.user().companyId();
		Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId, cid);
		if(socialInsurNotiCreateSet.isPresent() && socialInsurNotiCreateSet.get().getPrintPersonNumber() == PersonalNumClass.DO_NOT_OUTPUT) {

		}
	}

	//被保険者資格喪失届取得処理
	private void getInsPersonLossReport(){

	}
}
