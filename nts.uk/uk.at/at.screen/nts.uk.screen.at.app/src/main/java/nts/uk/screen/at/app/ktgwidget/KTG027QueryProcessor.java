package nts.uk.screen.at.app.ktgwidget;

import java.util.List;
import java.util.Optional;
import nts.uk.ctx.at.auth.dom.adapter.AuthWorkPlaceAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
	
@Stateless
public class KTG027QueryProcessor {
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;
	
	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;
	
	@Inject
	private ClosureRepository closureRepository;

	public GeneralDate checkSysDateOrCloseEndDate() {
		// EA luôn trả về Systemdate
		return GeneralDate.today();
	}

	public int getLoginClosure(GeneralDate referenceDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// ログイン者の雇用を取得する
		// (Lấy employeement của login) request list 31
		Optional<BsEmploymentHistoryImport> employmentHistoryImport = shareEmploymentAdapter.findEmploymentHistory(companyID, employeeID, referenceDate);
		if (!employmentHistoryImport.isPresent()) {
			// If fail return closure ID = 1
			return 1;
		} else {
			Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD(companyID, employmentHistoryImport.get().getEmploymentCode());
			if (!closureEmploymentOpt.isPresent()) {
				throw new RuntimeException("Not found Employment history by employeeCD:" + employmentHistoryImport.get().getEmploymentCode());
			}
			return closureEmploymentOpt.get().getClosureId();
		}
	}

	public boolean buttonPressingProcess(GeneralDate targetMonth, int closureID) {
		String employeeID = AppContexts.user().employeeId();
		String companyID = AppContexts.user().companyId();
		// 締め期間を取得する
		// (Lấy closing period) tu domain Closure 
		/*
		 * ouput: Closing period start date End date
		 */
		Optional<Closure> optClosure = closureRepository.findByIdAndUseAtr(companyID, closureID, UseClassification.UseClass_Use);
		if(!optClosure.isPresent()){
			throw new BusinessException("Msg_1134");
		}
		else{
		// ログイン者の権限範囲内の職場を取得する
		// (Lấy workplace trong phạm vi quyền login) = Request List 334
		// referenceDate lấy theo baseDate ở xử lý 1
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		List<String> listWorkPlaceID = authWorkPlaceAdapter.getListWorkPlaceID(employeeID, referenceDate);
		if (listWorkPlaceID.isEmpty()) {
			throw new BusinessException("Msg_1135");
		} else {
			// 締めに紐づく雇用を取得する
			// (lấy employeement ứng với closing)
			List<ClosureEmployment> listClosureEmployment = closureEmploymentRepo.findByClosureId(companyID, closureID);
			if (listClosureEmployment.isEmpty()) {
				throw new BusinessException("Msg_1136");
			} else {
				// 対象者を取得する
				// (Lấy target) Lấy request list 335	
			}
		}
	}
		return false;
	}

	public int initialActivationArocess(GeneralDate targetMonth) {
		// 基準日を取得する
		// (Lấy base date)
		GeneralDate referenceDate = checkSysDateOrCloseEndDate();
		// ログイン者の締めを取得する
		// (Lấy closing của login)
		int closureID = getLoginClosure(referenceDate);
		// 締めの一覧を取得する
		// (Lấy list closing) Request 142
		List<ClosureResultModel> listClosureResultModel = workClosureQueryProcessor.findClosureByReferenceDate(referenceDate);
		// 時間外労働時間を取得する
		// (Lấy work time )
		
		return closureID;
	}
}