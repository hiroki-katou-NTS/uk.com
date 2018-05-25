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
	 * Ｅ．振休管理データの紐付設定（振休選択）登録処理
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
		
		if (subOfHDId.size() == 1){
			if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.ONE_DAY.value) != 0){

				// エラーメッセージ(Msg_731) エラーリストにセットする
				throw new BusinessException("Msg_731");
			}
		}
		if (subOfHDId.size() == 2){
			if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.ONE_DAY.value) == 0){
				
				if (allowPrepaidLeave == ApplyPermission.NOT_ALLOW){
					// エラーメッセージ(Msg_739)	エラーリストにセットする

					throw new BusinessException("Msg_739");
				}

			}
			if (subOfHDId.get(0).getRemainDays().compareTo(ItemDays.HALF_DAY.value) == 0 
					&& subOfHDId.get(1).getRemainDays().compareTo(ItemDays.HALF_DAY.value) != 0){
				// エラーメッセージ(Msg_731) エラーリストにセットする
				throw new BusinessException("Msg_731");
			}
		}

		if (!payoutSubofHDManaRepository.getByPayoutId(payoutId).isEmpty()) {
			payoutSubofHDManaRepository.delete(payoutId);
		}
		
		subOfHDId.forEach(i->{ 
			payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(payoutId, i.getSubOfHDID(),
					new BigDecimal(i.getRemainDays()), TargetSelectionAtr.MANUAL.value));
			// Update remain days 振休管理データ
			Optional<SubstitutionOfHDManagementData> subMana =  substitutionOfHDManaDataRepository.findByID(i.getSubOfHDID());
			if (subMana.isPresent()){
				subMana.get().setRemainsDay(0d);
				substitutionOfHDManaDataRepository.update(subMana.get());
			}
		});
		// Update 振出管理データ  残数
		Optional<PayoutManagementData> payoutData =  payoutManagementDataRepository.findByID(payoutId);
		if (payoutData.isPresent()){
			payoutData.get().setRemainNumber(remainNumber);
			payoutManagementDataRepository.update(payoutData.get());
		}
		
	}

	/**
	 * KDM001 screen H
	 */
	// Q&&A
	public boolean checkCompensatoryDate() {
		return false;
	}

	public boolean checkExpirationDate(GeneralDate expirationDate) {
		boolean checkExpirationDate = false;
		if (checkCompensatoryDate()) {
			GeneralDate today = GeneralDate.today();
			if (today.compareTo(expirationDate) > 0) {
				throw new BusinessException("Mg_825");
			} else {
				checkExpirationDate = true;
			}
		}
		return checkExpirationDate;
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
