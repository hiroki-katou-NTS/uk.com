package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TypeOffsetJudgment;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PayoutManagementDataService {

	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

	@Inject
	private AddSubHdManagementService addSubHdManagementService;

	@Inject
	private PayoutManagementDataRepository confirmRecMngRepo;

	@Inject
	private SysEmpAdapter syEmployeeAdapter;
	
	private static final Double ZERO = 0d;
	
	/**
	 * 所属会社履歴をチェック
	 * @param sid 社員ID
	 * @param occurrenceDate 発生日
	 * @param digestionDate 消化日
	 * @param dividedDigestionDate 分割消化日
	 * @param flag 振休・代休区分
	 */
	private void checkHistoryOfCompany(String sid, GeneralDate occurrenceDate, GeneralDate digestionDate, GeneralDate dividedDigestionDate, Integer flag) {
		SyEmployeeImport sysEmp = syEmployeeAdapter.getPersonInfor(sid);
		
		if (flag == TypeOffsetJudgment.ABSENCE.value) {
			if (occurrenceDate != null && occurrenceDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_SubstituteWork");
			} else if (digestionDate != null && digestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_SubstituteHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "分割消化");
			} else if (occurrenceDate != null && occurrenceDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_SubstituteWork");
			} else if (digestionDate != null && digestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_SubstituteHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "分割消化");
			}
		}
		
		if (flag == TypeOffsetJudgment.REAMAIN.value) {
			if (occurrenceDate != null && occurrenceDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "休出");
			} else if (digestionDate != null && digestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "Com_CompensationHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.before(sysEmp.getEntryDate())) {
				throw new BusinessException("Msg_2017", "分割消化");
			} else if (occurrenceDate != null && occurrenceDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "休出");
			} else if (digestionDate != null && digestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "Com_CompensationHoliday");
			} else if (dividedDigestionDate != null && dividedDigestionDate.after(sysEmp.getRetiredDate())) {
				throw new BusinessException("Msg_2018", "分割消化");
			}
		}
	}
	
	private void checkHolidate(Boolean pickUp, Boolean pause, Boolean checkedSplit, Double remainDays, Double occurredDays, Double linkingDate, Double subDay, Double requiredDays) {
		if (!pickUp) {
			occurredDays = 0.0;
		} else {
			linkingDate = 0.0;
		}
		if (!pause) {
			subDay = 0.0;
		}
		if (!checkedSplit) {
			requiredDays = 0.0;
		}
		// 振休残数をチェック
		if (remainDays < 0) {// 振休残数＜0の場合
			// エラーメッセージ「Msg_2029」を表示する
			throw new BusinessException("Msg_2029");
		}

		// 振出チェックボックスをチェックする
		if (pickUp) {// チェックするの場合
			// 振休残数　＝　振休日数（D6_3）+　紐付け日数（D16_4）-　振休日数（D11_3）-　振休日数（D12_4）
			remainDays = occurredDays + linkingDate - subDay - requiredDays;
			if (remainDays >= 0.5) {
				throw new BusinessException("Msg_2030");
			}
			return;
		}
		// 分割消化チェックボックスをチェック
		if (pause) {// チェックする
			// エラーメッセージ「Msg_1256」を表示する
			throw new BusinessException("Msg_1256");
		}
		// 振休残数　＝　紐付け日数（D16_4）-　振休日数（D11_3）
		remainDays = linkingDate - subDay;
		// 振休日数をチェック
		if (remainDays < 0) {// 振休残数　＜0
			// エラーメッセージ「Msg_2030」
			throw new BusinessException("Msg_2030");
		}
	}

	private boolean checkInfoPayMana(String cId, String sId, GeneralDate date) {
		Optional<PayoutManagementData> payout = payoutManagementDataRepository.find(cId, sId,date);
		if (payout.isPresent()) {
			return true;
		}
		return false;
	}

	private boolean checkInfoSubPayMana(String cId, String sId, GeneralDate date) {
		Optional<SubstitutionOfHDManagementData> subPayout = substitutionOfHDManaDataRepository.find(cId, sId,date);
		if (subPayout.isPresent()) {
			return true;
		}
		return false;
	}

	public List<String> addPayoutManagement(String sid, Boolean pickUp, Boolean pause,Boolean checkedSplit, PayoutManagementData payMana,SubstitutionOfHDManagementData subMana,
				SubstitutionOfHDManagementData splitMana,  Double requiredDays,  int closureId, List<String> linkingDates, Double remainDays, Double linkingDate) {
		List<String> errors = new ArrayList<String>();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		Optional<GeneralDate> closureDate = this.getClosureDate(closureId, processYearMonth);
		if (pickUp) {
			if (this.checkDateClosing(payMana.getPayoutDate().getDayoffDate().get(), closureDate, closureId)) {
				errors.add("Msg_1435");
			}
			if (this.checkInfoPayMana(payMana.getCID(), payMana.getSID(), payMana.getPayoutDate().getDayoffDate().orElse(null)) 
					|| this.checkInfoSubPayMana(payMana.getCID(), payMana.getSID(), payMana.getPayoutDate().getDayoffDate().orElse(null))) {
				errors.add("Msg_737_PayMana");
			}
		} else {
			if (!pause) {
				errors.add("Msg_725");
				return errors;
			}
		}
		if (pause) {
			if (this.checkInfoSubPayMana(subMana.getCid(), subMana.getSID(), subMana.getHolidayDate().getDayoffDate().orElse(null))
					|| this.checkInfoPayMana(subMana.getCid(), subMana.getSID(), subMana.getHolidayDate().getDayoffDate().orElse(null))) {
				errors.add("Msg_737_SubPay");
			}
			errors.addAll(this.checkOffHolidate(subMana.getHolidayDate().getDayoffDate().orElse(null), payMana.getPayoutDate().getDayoffDate().orElse(null),
						splitMana.getHolidayDate().getDayoffDate().orElse(null), closureDate, closureId, checkedSplit, pickUp));
			if (checkedSplit) {
				if (this.checkInfoSubPayMana(splitMana.getCid(), splitMana.getSID(), splitMana.getHolidayDate().getDayoffDate().orElse(null))
						|| this.checkInfoPayMana(splitMana.getCid(), splitMana.getSID(), splitMana.getHolidayDate().getDayoffDate().orElse(null))) {
					errors.add("Msg_737_splitMana");
				}
			}
		}
		this.checkHolidate(pickUp, pause, checkedSplit, remainDays, payMana.getOccurredDays().v(), linkingDate, subMana.getRequiredDays().v(), requiredDays);
		this.checkHistoryOfCompany(sid
				, payMana.getPayoutDate().getDayoffDate().orElse(null)
				, subMana.getHolidayDate().getDayoffDate().orElse(null)
				, splitMana.getHolidayDate().getDayoffDate().orElse(null)
				, TypeOffsetJudgment.ABSENCE.value);
		if (errors.isEmpty()) {
			if (pickUp) {
				payoutManagementDataRepository.create(payMana);
			}
			if (pause) {
				substitutionOfHDManaDataRepository.create(subMana);
			}

			if (checkedSplit) {
				substitutionOfHDManaDataRepository.create(splitMana);
			}
			//	作成したList<振休管理データ＞
			List<SubstitutionOfHDManagementData> substitutionOfHDManagementDatas = new ArrayList<SubstitutionOfHDManagementData>();
			substitutionOfHDManagementDatas.add(subMana);
			if (checkedSplit) {
				substitutionOfHDManagementDatas.add(splitMana);
			}
			
			//	アルゴリズム「振出振休紐付け管理」を実行する
			// (Thực hiện thuật toán 「振休残数管理データ更新フラグ処理」"xử lý flag cập nhật data quản ly ngày nghỉ bù lại")
			this.changeTheTieUpManagement(subMana.getSID(), substitutionOfHDManagementDatas, linkingDates);
		}
		
		return errors;
	}
	
	public Optional<GeneralDate> getClosureDate(int closureId, YearMonth processYearMonth) {
		Optional<Closure> optClosure = closureRepo.findById(AppContexts.user().companyId(), closureId);

		// Check exist and active
		if (!optClosure.isPresent() || optClosure.get().getUseClassification()
				.equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();

		// Get Processing Ym 処理年月
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);
		if (Objects.isNull(closurePeriod)) {
			return Optional.empty();
		}
		return Optional.of(closurePeriod.start());
	}
	
	private boolean checkDateClosing(GeneralDate date, Optional<GeneralDate> closureDate, int closureId) {
		if (closureDate.isPresent() && !closureDate.get().after(date)) {
			return true;
		}
		return false;
	}
	
	private List<String> checkOffHolidate(GeneralDate restDate,GeneralDate workDate,GeneralDate splitDate, Optional<GeneralDate> closureDate, int closureId,Boolean split, Boolean pickUp) {
		List<String> errors = new ArrayList<String>();
		if(checkDateClosing(restDate, closureDate, closureId)) {
			errors.add("Msg_1436");
		}
		if(pickUp && restDate.equals(workDate)) {
			errors.add("Msg_729_SubMana");
		}
		if(split) {
			if(restDate.equals(splitDate)) {
				errors.add("Msg_1437");
			}
			if(checkDateClosing(splitDate,closureDate,closureId)){
				errors.add("Msg_1438");
			}
			if(pickUp && splitDate.equals(workDate)) {
				errors.add("Msg_729_Split");
			}
		}
		return errors;
	}

	/**
	 * KDM001 screen G
	 */
	private List<String> checkClosureDate(int closureId, GeneralDate dayoffDate) {
		List<String> errorList = new ArrayList<>();
		YearMonth processYearMonth = GeneralDate.today().yearMonth();
		Optional<GeneralDate> closureDate = addSubHdManagementService.getClosureDate(closureId, processYearMonth);
		if (closureDate.isPresent()) {
			if (dayoffDate.compareTo(closureDate.get()) >= 0) {
				errorList.add("Msg_740");
			}
		}
		return errorList;
	}

	private List<String> checkBox(boolean checkBox, int stateAtr, Optional<GeneralDate> dayoffDate, boolean unknownDate, GeneralDate expiredDate,
			double unUsedDays) {
		List<String> errorList = new ArrayList<>();
		if (!checkBox) {
			if (stateAtr == DigestionAtr.EXPIRED.value) {
				errorList.add("Msg_1212");
				return errorList;
			} else {
				if (unknownDate || !dayoffDate.isPresent()){
					return errorList;
				}
				else if (dayoffDate.get().compareTo(expiredDate) >= 0) {
					errorList.add("Msg_825");
				}
			}
			return errorList;
		} else {
			if (ZERO.equals(unUsedDays)) {
				errorList.add("Msg_1213");
			}
			return errorList;
		}
	}

	public List<String> update(PayoutManagementData data, int closureId, boolean checkBox) {
		
		if (data.getPayoutDate().getDayoffDate().isPresent()){
			
			List<String> errorListClosureDate = checkClosureDate(closureId, data.getPayoutDate().getDayoffDate().get());
			
			if(!errorListClosureDate.isEmpty()){
				return errorListClosureDate;
			}
		}
		List<String> errorListCheckBox = checkBox(checkBox, data.getStateAtr().value,
				data.getPayoutDate().getDayoffDate(), data.getPayoutDate().isUnknownDate(), data.getExpiredDate(), data.getUnUsedDays().v());
		if (!errorListCheckBox.isEmpty()) {
			return errorListCheckBox;
		}
		// Update state 
		if (checkBox){
			if (!data.getPayoutDate().isUnknownDate()){
				data.setStateAtr(DigestionAtr.EXPIRED.value);
			}
		} else if (ZERO.equals(data.getUnUsedDays().v()) ){
			data.setStateAtr(DigestionAtr.USED.value);
		} else {
			data.setStateAtr(DigestionAtr.UNUSED.value);
		}
		
		payoutManagementDataRepository.update(data);
		return Collections.emptyList();
	}
	
	 //setToFree when delete subOfHDId
	public void setToFree(String subOfHDId) {
//		List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManaRepository.getBySubId(subOfHDId);
//		if (!listPayoutSub.isEmpty()) {
//			payoutSubofHDManaRepository.deleteBySubID(subOfHDId);
//		}
//		listPayoutSub.forEach(item -> {
//			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(item.getPayoutId());
//			if (payoutMan.isPresent()) {
//				payoutMan.get().setStateAtr(DigestionAtr.UNUSED.value);
//				payoutMan.get().setRemainNumber(Double.valueOf(item.getUsedDays().v().intValue()));
//				payoutManagementDataRepository.update(payoutMan.get());
//			}
//		});
	}

	/**
	 * Ｆ．振休管理データの紐付設定（振出選択）登録処理
	 */
	public void insertPayoutSubofHD(String sid, String subId, Double remainNumber, List<SubOfHDManagement> subOfHDId) {
//		List<PayoutSubofHDManagement>  listPayoutSub = payoutSubofHDManaRepository.getBySubId(subId);
//		if (!listPayoutSub.isEmpty()) {
//			payoutSubofHDManaRepository.deleteBySubID(subId);
//		}
//		
//		// Set all item to free
//		listPayoutSub.forEach(item->{
//			// Update remain days 振出管理データ
//			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(item.getPayoutId());
//			if (payoutMan.isPresent()) {
//				payoutMan.get().setRemainNumberToFree(item.getUsedDays().v());
//				payoutMan.get().setStateAtr(DigestionAtr.UNUSED.value);
//				payoutManagementDataRepository.update(payoutMan.get());
//			}
//		});
//		subOfHDId.forEach(i -> {
//			payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(i.getPayoutId(), subId,
//					i.getUnUsedDays(), TargetSelectionAtr.MANUAL.value));
//			// Update remain days 振出管理データ
//			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(i.getPayoutId());
//			if (payoutMan.isPresent()) {
//				payoutMan.get().setRemainNumber(0d);
//				payoutMan.get().setStateAtr(DigestionAtr.USED.value);
//				payoutManagementDataRepository.update(payoutMan.get());
//			}
//		});
//		// Update 振休管理データ
//		Optional<SubstitutionOfHDManagementData> subofHD = substitutionOfHDManaDataRepository.findByID(subId);
//		if (subofHD.isPresent()) {
//			subofHD.get().setRemainsDay(remainNumber);
//			substitutionOfHDManaDataRepository.update(subofHD.get());
//		}

	}

	/**
	 * UKDesign.UniversalK.就業.KDM_残数管理 (Quản lý số dư).KDM001_残数管理データの登録 (Đăng ký dữ liệu quản lý số dư).アルゴリズム（残数管理データ登録共通）
	 * Thuật toán (common đăng ký data quản lý số còn lại).振出振休紐付け管理を変更 (thay đổi quản lý liên kết đi làm/nghỉ thay thế).振出振休紐付け管理を変更
	 *
	 * @param employeeId the employee id
	 * @param subList the sub list
	 * @param linkingDateList the linking date list
	 */
	public void changeTheTieUpManagement(String employeeId
			, List<SubstitutionOfHDManagementData> substitutionOfHDManagementDatas
			, List<String> linkingDateList) {

		// Input．List＜紐付け日付＞をチェック Check Input．List＜紐付け日付＞
		if (linkingDateList.isEmpty()) { // ないの場合
			return;
		}

		// ドメインモデル「振出管理データ」を取得 Nhận domain model 「振出管理データ」 
		List<PayoutManagementData> lstRecconfirm  = this.confirmRecMngRepo.getAllByUnknownDate(employeeId, linkingDateList);

		// 未使用日数　＝　０ Số ngày chưa sử dụng =0
		double unUseDay = 0;

		// Input．List＜振休管理データ＞をループする
		for (SubstitutionOfHDManagementData substitutionOfHDManagementData : substitutionOfHDManagementDatas) {
			// 取得したList＜振出管理データ＞をループする
			for (PayoutManagementData x : lstRecconfirm) {
				// 未使用日数を計算 Tính toán số ngày chưa sử dụng
				if ((x.getOccurredDays().v() - substitutionOfHDManagementData.getRequiredDays().v()) > 0) {
					unUseDay = x.getOccurredDays().v() - substitutionOfHDManagementData.getRequiredDays().v();
				}
				if ((x.getOccurredDays().v() - substitutionOfHDManagementData.getRequiredDays().v()) <= 0 || unUseDay > 0) {
					unUseDay = 0;
				}

				// ・未使用日数　＝　計算した振休管理データ
				x.setRemainNumber(unUseDay);
				// ・振休消化区分　＝　消化済み
				x.setStateAtr(DigestionAtr.USED.value);

				// ループ中ドメインモデル「振出管理データ」を更新する Update domain model 「振出管理データ」 trong vòng lặp
				this.confirmRecMngRepo.update(x);
				
				// ドメインモデル「振出振休紐付け管理」を追加 Thêm domain model 「振出振休紐付け管理」
				PayoutSubofHDManagement payoutSubofHDManagement = new PayoutSubofHDManagement(employeeId //	社員ID　＝　Input．社員ID
						, x.getPayoutDate().getDayoffDate().orElse(null)								 //	 紐付け情報．発生日　＝　ループ中の振出管理データ．振出日
						, substitutionOfHDManagementData.getHolidayDate().getDayoffDate().orElse(null)	 //	紐付け情報．使用日　＝　ループ中の振休管理データ．振休日
						, substitutionOfHDManagementData.getRequiredDays().v()							 //	紐付け情報．使用日数　＝　ループ中の振休管理データ．必要日数
						, TargetSelectionAtr.MANUAL.value);												 //	紐付け情報．対象選択区分　＝　手動
				
				// 	ドメインモデル「振出振休紐付け管理」を追加 Thêm domain model 「振出振休紐付け管理」
				boolean insertState = this.addPayoutSub(payoutSubofHDManagement);
				if (insertState) {
					unUseDay = 0;
				}
			}
		}
		
	}
	
	public boolean addPayoutSub(PayoutSubofHDManagement domain) {
		try {
			payoutSubofHDManaRepository.add(domain);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
