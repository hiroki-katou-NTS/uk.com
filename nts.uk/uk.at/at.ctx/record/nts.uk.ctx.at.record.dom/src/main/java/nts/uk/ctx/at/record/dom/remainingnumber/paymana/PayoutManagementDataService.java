package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;

@Stateless
public class PayoutManagementDataService {

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

	public boolean checkInfoPayMana(PayoutManagementData domain) {
		Optional<PayoutManagementData> payout = payoutManagementDataRepository.find(domain.getCID(), domain.getSID(),
				domain.getPayoutDate());
		if (payout.isPresent()) {
			return true;
		}
		return false;
	}
	
	public boolean checkInfoSubPayMana(SubstitutionOfHDManagementData subDomain) {
		Optional<SubstitutionOfHDManagementData> subPayout = substitutionOfHDManaDataRepository.find(subDomain.getCid(), subDomain.getSID(),
				subDomain.getHolidayDate());
		if (subPayout.isPresent()) {
			return true;
		}
		return false;
	}

	public GeneralDate getClosingDate() {
		return null;
	}

	public List<String> addPayoutManagement(boolean pickUp, boolean pause, PayoutManagementData payMana,
			SubstitutionOfHDManagementData subMana, PayoutSubofHDManagement paySub) {
		List<String> errors = new ArrayList<String>();
		if (pickUp) {
			//this.checkProcess(); Wait QA
			if (this.checkInfoPayMana(payMana)) {
				errors.add("Msg_737_PayMana");
			}
			if (errors.isEmpty()) {
				payoutManagementDataRepository.create(payMana);
			}
		}
		if (pause) {
			//this.checkProcess(); Wait QA
			if (this.checkInfoSubPayMana(subMana)) {
				errors.add("Msg_737_SubPay");
			}
			if (errors.isEmpty()) {
				substitutionOfHDManaDataRepository.create(subMana);
			}	
		}
		if (pickUp && pause) {
			if (errors.isEmpty())
			payoutSubofHDManaRepository.add(paySub);
		}
		return errors;

	}

	/**
	 * KDM001 screen G
	 */
	// (Thực hiện thuật toán 「振出（年月日）チェック処理」"xử lý check chuyển đổi (năm tháng
	// ngày)")
	public boolean checkProcess() {
		// data fake : đang chờ trả lời Q&A
		GeneralDate closingDate = GeneralDate.ymd(2018, 03, 12);
		GeneralDate changeDate = GeneralDate.ymd(2018, 5, 12);
		boolean check = false;
		if (changeDate.compareTo(closingDate) < 0) {
			check = true;
		} else {
			check = false;
//			throw new BusinessException("Msg_740");
		}
		return true;
	}
	
	// (Thực hiện thuật toán 「Ｇ．振休管理データの修正（振出設定）入力項目チェック処理」)
	public boolean checkboxData(boolean checkBox, int lawAtr, GeneralDate dayoffDate, GeneralDate expiredDate, double unUsedDays) {
		boolean check = false;
		if (checkBox) {
			if (lawAtr == 2) {
				// message : エラーメッセージ(Msg_1212)をエラーリストにセットする
				check = false;
				throw new BusinessException("Msg_1212");
			} else {
				
				if (dayoffDate.compareTo(expiredDate) >0) {
					check = false;
					throw new BusinessException("Msg_825");
				} else {
					check = true;
				}
			}
		} else {
			if (unUsedDays == 0) {
				check = false;
				throw new BusinessException("Msg_1213");
			} else {
				check = true;
			}
		}
		return check;
	}

	public void update(PayoutManagementData data, boolean checkBox, int lawAtr,GeneralDate dayoffDate, GeneralDate expiredDate,
			double unUsedDays) {
		// 振出（年月日）チェック処理
		boolean check = checkProcess();
		if (check) {
			// 振休管理データの修正（振出設定）入力項目チェック処理
			boolean checkBoxData = checkboxData(checkBox, lawAtr,dayoffDate, expiredDate, unUsedDays);
			if (checkBoxData) {
				payoutManagementDataRepository.update(data);
				// **chưa có :(Thực hiện thuật toán 「振休残数管理データ更新フラグ処理」):bât cờ
				// flag
			}
		}
	}
	
}
