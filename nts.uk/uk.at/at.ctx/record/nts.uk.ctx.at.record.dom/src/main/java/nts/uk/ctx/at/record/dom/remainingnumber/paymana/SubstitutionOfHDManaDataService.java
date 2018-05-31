package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.AddSubHdManagementService;

@Stateless
public class SubstitutionOfHDManaDataService {

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

		List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManaRepository.getByPayoutId(payoutId);
		if (!listPayoutSub.isEmpty()) {
			payoutSubofHDManaRepository.delete(payoutId);
		}
		
		// Set all item to free
		listPayoutSub.forEach(item->{
			// Update remain days 振休管理データ
			Optional<SubstitutionOfHDManagementData> subMana = substitutionOfHDManaDataRepository
					.findByID(item.getSubOfHDID());
			if (subMana.isPresent()) {
				subMana.get().setRemainsDay(Double.valueOf(item.getUsedDays().v().intValue()));
				substitutionOfHDManaDataRepository.update(subMana.get());
			}
		});
		
		subOfHDId.forEach(i -> {
			payoutSubofHDManaRepository.add(new PayoutSubofHDManagement(payoutId, i.getSubOfHDID(),
					new BigDecimal(i.getRequiredDays()), TargetSelectionAtr.MANUAL.value));
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
			if (remainNumber == 0){
				payoutData.get().setStateAtr(DigestionAtr.USED.value);
			}
			payoutManagementDataRepository.update(payoutData.get());
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
	//setToFree when delete payoutId
		public void setToFree(String payoutId) {
			List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManaRepository.getByPayoutId(payoutId);
			if (!listPayoutSub.isEmpty()) {
				payoutSubofHDManaRepository.delete(payoutId);
			}
			listPayoutSub.forEach(item -> {
				Optional<SubstitutionOfHDManagementData> subMana = substitutionOfHDManaDataRepository
						.findByID(item.getSubOfHDID());
				if (subMana.isPresent()) {
					subMana.get().setRemainsDay(Double.valueOf(item.getUsedDays().v().intValue()));
					substitutionOfHDManaDataRepository.update(subMana.get());
				}
			});
		}
}
