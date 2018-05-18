package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.text.MessageFormat;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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

	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

	/**
	 * KDM001 screen E
	 */
	public void insertSubOfHDMan(SubstitutionOfHDManagementData domain) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「inported雇用」を読み込む
		Optional<SEmpHistoryExport> syEmpHist = syEmploymentAdapter.findSEmpHistBySid(companyId, domain.getSID(),
				GeneralDate.today());
		if (!syEmpHist.isPresent()) {
			return;
		}
		ApplyPermission allowPrepaidLeave = null;
		// ドメインモデル「雇用振休管理設定」を」読み込む
		Optional<EmpSubstVacation> empSubstVacation = empSubstVacationRepository.findById(companyId,
				syEmpHist.get().getEmploymentCode());
		if (!empSubstVacation.isPresent()) {
			// ドメインモデル「振休管理設定」を」読み込む
			Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepository.findById(companyId);
			if (comSubstVacation.isPresent()) {
				allowPrepaidLeave = comSubstVacation.get().getSetting().getAllowPrepaidLeave();
			}
		}

		allowPrepaidLeave = empSubstVacation.get().getSetting().getAllowPrepaidLeave();
	}

	/**
	 * KDM001 screen H
	 */
	//Q&&A
	public boolean checkCompensatoryDate() {
		return false;
	}

	public boolean checkExpirationDate(GeneralDate expirationDate) {
		boolean checkExpirationDate = false;
		if(checkCompensatoryDate()){
			GeneralDate today = GeneralDate.today();
			if (today.compareTo(expirationDate) > 0) {
				throw new BusinessException("Mg_825");
			} else {
				checkExpirationDate = true;
			}
		}
		return checkExpirationDate;
	}

	public boolean deleteSubsitutionOfHDManaData(GeneralDate expirationDate, String sID, GeneralDate dayOff) {
		boolean checkError = false;
		boolean checkExDate = checkExpirationDate(expirationDate);
		if (checkExDate) {
			checkError = true;
			substitutionOfHDManaDataRepository.delete(sID, dayOff);
			// Đang Q&&A
			// 共通アルゴリズム「残数管理データ更新フラグ更新」を実行する
			// (Thực hiện thuật toán common 「Update cờ cập nhật quản lý data
			// còn lại」 )
		}
		return checkError;
	}

	public void delete(GeneralDate expirationDate, String sID, GeneralDate dayOff) {
		boolean checkData = deleteSubsitutionOfHDManaData(expirationDate, sID, dayOff);
		if (checkData) {
			throw new BusinessException("Msg_15");
		} else {
			throw new BusinessException("Message error!!");
		}
	}
}
