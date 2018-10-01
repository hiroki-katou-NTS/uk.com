package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ItemDays;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class PayoutManagementDataService {
	
	@Inject
	private ClosureService closureService;
	
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
	
	private static final Double ZERO = 0d;
	
	private List<String> checkHolidate(Boolean pickUp, Boolean pause,Boolean checkedSplit, Double requiredDays,Double subDays, Double occurredDays){
		List<String> errors = new ArrayList<String>();
		if (pause) {
			if (checkedSplit) {
				if (!ItemDays.HALF_DAY.value.equals(subDays)) {
					errors.add("Msg_1256_SubDays");
					return errors;
				} else if (!ItemDays.HALF_DAY.value.equals(requiredDays)){
					errors.add("Msg_1256_RequiredDays");
					return errors;
				} 
			}
		}
		if (pickUp) {
			if (checkedSplit) {
				if (!ItemDays.ONE_DAY.value.equals(occurredDays)) {
					errors.add("Msg_1256_OccurredDays");
					return errors;
				}
			} else if (pause && !occurredDays.equals(subDays)){
					errors.add("Msg_1257_OccurredDays");
					return errors;
			}
		}
		
		return errors;
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
	
	public List<String> addPayoutManagement(Boolean pickUp, Boolean pause,Boolean checkedSplit, PayoutManagementData payMana,SubstitutionOfHDManagementData subMana,
				SubstitutionOfHDManagementData splitMana,  Double requiredDays,  int closureId) {
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
		errors.addAll(checkHolidate(pickUp, pause, checkedSplit, splitMana.getRequiredDays().v(),subMana.getRequiredDays().v(), payMana.getOccurredDays().v() ));
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
			if (pause && pickUp) {
				PayoutSubofHDManagement paySub = new PayoutSubofHDManagement(payMana.getPayoutId(), subMana.getSubOfHDID(), subMana.getRequiredDays().v(), TargetSelectionAtr.MANUAL.value);
				payoutSubofHDManaRepository.add(paySub);
				if (checkedSplit) {
					PayoutSubofHDManagement paySplit = new PayoutSubofHDManagement(payMana.getPayoutId(), splitMana.getSubOfHDID(), splitMana.getRequiredDays().v(), TargetSelectionAtr.MANUAL.value);
					payoutSubofHDManaRepository.add(paySplit);
				}
			}
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

		DatePeriod closurePeriod = closureService.getClosurePeriod(closureId, processingYm);
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
		if(checkDateClosing(restDate,closureDate,closureId)) {
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
		List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManaRepository.getBySubId(subOfHDId);
		if (!listPayoutSub.isEmpty()) {
			payoutSubofHDManaRepository.deleteBySubID(subOfHDId);
		}
		listPayoutSub.forEach(item -> {
			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(item.getPayoutId());
			if (payoutMan.isPresent()) {
				payoutMan.get().setStateAtr(DigestionAtr.UNUSED.value);
				payoutMan.get().setRemainNumber(Double.valueOf(item.getUsedDays().v().intValue()));
				payoutManagementDataRepository.update(payoutMan.get());
			}
		});
	}

	/**
	 * Ｆ．振休管理データの紐付設定（振出選択）登録処理
	 */
	public void insertPayoutSubofHD(String sid, String subId, Double remainNumber, List<SubOfHDManagement> subOfHDId) {
		List<PayoutSubofHDManagement>  listPayoutSub = payoutSubofHDManaRepository.getBySubId(subId);
		if (!listPayoutSub.isEmpty()) {
			payoutSubofHDManaRepository.deleteBySubID(subId);
		}
		
		// Set all item to free
		listPayoutSub.forEach(item->{
			// Update remain days 振出管理データ
			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(item.getPayoutId());
			if (payoutMan.isPresent()) {
				payoutMan.get().setRemainNumberToFree(item.getUsedDays().v());
				payoutMan.get().setStateAtr(DigestionAtr.UNUSED.value);
				payoutManagementDataRepository.update(payoutMan.get());
			}
		});
		subOfHDId.forEach(i -> {
			payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(i.getPayoutId(), subId,
					i.getUnUsedDays(), TargetSelectionAtr.MANUAL.value));
			// Update remain days 振出管理データ
			Optional<PayoutManagementData> payoutMan = payoutManagementDataRepository.findByID(i.getPayoutId());
			if (payoutMan.isPresent()) {
				payoutMan.get().setRemainNumber(0d);
				payoutMan.get().setStateAtr(DigestionAtr.USED.value);
				payoutManagementDataRepository.update(payoutMan.get());
			}
		});
		// Update 振休管理データ
		Optional<SubstitutionOfHDManagementData> subofHD = substitutionOfHDManaDataRepository.findByID(subId);
		if (subofHD.isPresent()) {
			subofHD.get().setRemainsDay(remainNumber);
			substitutionOfHDManaDataRepository.update(subofHD.get());
		}

	}
}
