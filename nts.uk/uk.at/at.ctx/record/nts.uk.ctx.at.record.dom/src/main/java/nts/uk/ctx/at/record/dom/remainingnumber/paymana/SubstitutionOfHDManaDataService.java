package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ItemDays;
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

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

	/**
	 * KDM001 screen E
	 */
	public void insertSubOfHDMan(String sid, String payoutId, List<DayOffManagement> subOfHDId) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「inported雇用」を読み込む
		Optional<SEmpHistoryImport> syEmpHist = syEmploymentAdapter.findSEmpHistBySid(companyId, sid,
				GeneralDate.today());
		if (!syEmpHist.isPresent()) {
			return;
		}
		ApplyPermission allowPrepaidLeave = ApplyPermission.NOT_ALLOW;
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

		// １件もない エラーメッセージ(Msg_738) エラーリストにセットする
		if (subOfHDId.isEmpty()) {
			throw new BusinessException("Msg_738");
		}

		// ３件以上あり エラーメッセージ(Msg_739) エラーリストにセットする
		if (subOfHDId.size() >= 3) {
			throw new BusinessException("Msg_739");
		}

		if (subOfHDId.size() == 1) {
			if (subOfHDId.get(0).getUseNumberDay() != ItemDays.ONE_DAY.value) {
				// エラーメッセージ(Msg_731) エラーリストにセットする
				throw new BusinessException("Msg_731");
			}
		}
		if (subOfHDId.size() == 2) {
			if (subOfHDId.get(0).getUseNumberDay() == ItemDays.ONE_DAY.value) {

				if (allowPrepaidLeave == ApplyPermission.NOT_ALLOW) {
					// エラーメッセージ(Msg_739) エラーリストにセットする
					throw new BusinessException("Msg_739");
				}

			}
			if (subOfHDId.get(0).getUseNumberDay() == ItemDays.HALF_DAY.value
					&& subOfHDId.get(1).getUseNumberDay() != ItemDays.HALF_DAY.value) {
				// エラーメッセージ(Msg_731) エラーリストにセットする
				throw new BusinessException("Msg_731");
			}
		}

		if (!payoutSubofHDManaRepository.getByPayoutId(payoutId).isEmpty()) {
			payoutSubofHDManaRepository.delete(payoutId);
		}

		subOfHDId.forEach(i -> {
			payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(payoutId, i.getCode(),
					new BigDecimal(i.getUseNumberDay()), TargetSelectionAtr.MANUAL.value));
		});

	}

	/**
	 * KDM001 update screen H
	 */
	// (Thực hiện thuật toán 「振休（年月日）チェック処理」"xử lý check ngày nghỉ bù (năm tháng
	// ngày)")
	public boolean checkDayOff() {
		// đang chờ Q&A
		return true;
	}

	public void updateSub(SubstitutionOfHDManagementData data) {
		boolean checkDayOff = checkDayOff();
		if (checkDayOff) {
			substitutionOfHDManaDataRepository.update(data);
		}
	}

}
