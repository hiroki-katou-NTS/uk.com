package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ItemDays;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlyPolicy;
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

	@Inject
	private AddSubHdManagementService addSubHdManagementService;

	/**
	 * Ｅ．振休管理データの紐付設定（振休選択）登録処理
	 * 
	 * @param sid
	 * @param payoutId
	 * @param remainNumber
	 * @param subOfHDId
	 */
	public void insertSubOfHDMan(String sid, String payoutId, Double remainNumber, List<DayOffManagement> subOfHDId) {
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
		// if (subOfHDId.isEmpty()){
		// throw new BusinessException("Msg_738");
		// }

		// ３件以上あり エラーメッセージ(Msg_739) エラーリストにセットする
		// if (subOfHDId.size() >= 3){
		// throw new BusinessException("Msg_739");
		// }

		if (subOfHDId.size() == 1) {
			if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.ONE_DAY.value) != 0) {

				if (subOfHDId.size() == 1) {
					if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.ONE_DAY.value) != 0) {

						// エラーメッセージ(Msg_731) エラーリストにセットする
						throw new BusinessException("Msg_731");
					}
				}
				if (subOfHDId.size() == 2) {
					if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.ONE_DAY.value) == 0) {

						if (allowPrepaidLeave == ApplyPermission.NOT_ALLOW) {
							// エラーメッセージ(Msg_739) エラーリストにセットする

							throw new BusinessException("Msg_739");
						}

					}
					if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.HALF_DAY.value) == 0
							&& subOfHDId.get(1).getRemainDays().compareTo(ItemDays.HALF_DAY.value) != 0) {
						// エラーメッセージ(Msg_731) エラーリストにセットする
						throw new BusinessException("Msg_731");
					}
				}

				if (!payoutSubofHDManaRepository.getByPayoutId(payoutId).isEmpty()) {
					payoutSubofHDManaRepository.delete(payoutId);
				}

				subOfHDId.forEach(i -> {
					payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(payoutId, i.getSubOfHDID(),
							new BigDecimal(i.getRemainDays()), TargetSelectionAtr.MANUAL.value));
					// Update remain days 振休管理データ
					Optional<SubstitutionOfHDManagementData> subMana = substitutionOfHDManaDataRepository
							.findByID(i.getSubOfHDID());
					if (subMana.isPresent()) {
						subMana.get().setRemainsDay(0d);
						substitutionOfHDManaDataRepository.update(subMana.get());
					}
				});
				// Update 振出管理データ 残数
				Optional<PayoutManagementData> payoutData = payoutManagementDataRepository.findByID(payoutId);
				if (payoutData.isPresent()) {
					payoutData.get().setRemainNumber(remainNumber);
					payoutManagementDataRepository.update(payoutData.get());
				}

			}
		}
	}

	/**
	 * KDM001 update screen H
	 */
	private List<String> checkClosureDate(int closureId, GeneralDate dayoffDate, String subOfHDID) {
		List<String> errorList = new ArrayList<>();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		Optional<GeneralDate> closureDate = addSubHdManagementService.getClosureDate(closureId, processYearMonth);
		if (dayoffDate.compareTo(closureDate.get()) >= 0) {
			errorList.add("Msg_744");
		}
		List<PayoutManagementData> listPayout = payoutManagementDataRepository.getDayoffDateBysubOfHDID(subOfHDID);
		if (!listPayout.isEmpty()) {
			for (PayoutManagementData payout : listPayout) {
				Optional<GeneralDate> dayoffDateOfPayout = payout.getPayoutDate().getDayoffDate();
				if (dayoffDateOfPayout.isPresent()) {
					if (dayoffDate.equals(dayoffDateOfPayout.get())) {
						errorList.add("Msg_729");
					}
				}
			}

		}
		return errorList;
	}

	public List<String> updateSubOfHD(SubstitutionOfHDManagementData data, int closureId) {
		List<String> errorListClosureDate = checkClosureDate(closureId, data.getHolidayDate().getDayoffDate().get(),
				data.getSubOfHDID());
		if (!errorListClosureDate.isEmpty()) {
			substitutionOfHDManaDataRepository.update(data);
		}
		return errorListClosureDate;
	}

}
