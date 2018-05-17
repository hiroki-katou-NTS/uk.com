package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionOfHDManaDataService {
	
	@Inject
	private SysEmploymentHisAdapter syEmploymentAdapter;
	
	@Inject
	private EmpSubstVacationRepository empSubstVacationRepository;
	
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	
	/**
	 * KDM001 screen E
	 */
	public void insertSubOfHDMan(SubstitutionOfHDManagementData domain){
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「inported雇用」を読み込む
		Optional<SEmpHistoryExport> syEmpHist = syEmploymentAdapter.findSEmpHistBySid(companyId, domain.getSID(), GeneralDate.today());
		if (!syEmpHist.isPresent()){
			return;
		}
		ApplyPermission allowPrepaidLeave = null;
		// ドメインモデル「雇用振休管理設定」を」読み込む
		Optional<EmpSubstVacation> empSubstVacation =empSubstVacationRepository.findById(companyId, syEmpHist.get().getEmploymentCode());
		if (!empSubstVacation.isPresent()){
			// ドメインモデル「振休管理設定」を」読み込む
			Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepository.findById(companyId);
			if (comSubstVacation.isPresent()){
				allowPrepaidLeave = comSubstVacation.get().getSetting().getAllowPrepaidLeave();
			}
		}
		
		allowPrepaidLeave = empSubstVacation.get().getSetting().getAllowPrepaidLeave();
	}
}
