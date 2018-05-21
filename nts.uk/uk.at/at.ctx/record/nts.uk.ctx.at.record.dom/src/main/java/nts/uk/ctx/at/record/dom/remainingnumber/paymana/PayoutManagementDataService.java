package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

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
	
	public boolean checkInfoPayMana(PayoutManagementData domain){
		Optional<PayoutManagementData> payout = payoutManagementDataRepository.find(domain.getCID(), domain.getSID(), domain.getPayoutDate());
		if (payout.isPresent()){
			return true;
		}
		return false;
	}
	public GeneralDate getClosingDate(){
		return null;
	}
	
	public void addPayoutManagement( boolean pickUp, boolean pause,
			PayoutManagementData command, SubstitutionOfHDManagementData subMana, PayoutSubofHDManagement paySub){
		if (pickUp) {
			this.checkProcess();
			payoutManagementDataRepository.create(command);
		}
		if (this.checkInfoPayMana(command)) {
			throw new BusinessException("Msg_737");
		}
		if (pause) {
			if (this.checkInfoPayMana(command)) {
				throw new BusinessException("Msg_737");
			} else {
				substitutionOfHDManaDataRepository.create(subMana);
			}
		}
		if (pickUp && pause) {
			payoutSubofHDManaRepository.add(paySub);
		}
		
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
			throw new BusinessException("Mg_740");
		}
		return check;
	}

	// (Thực hiện thuật toán 「Ｇ．振休管理データの修正（振出設定）入力項目チェック処理」)
	public boolean checkboxData(boolean checkBox, int stateAtr, GeneralDate expiredDate, double unUsedDays) {
		boolean check = false;
		if (checkBox) {
			if (stateAtr == 2) {
				// message : エラーメッセージ(Msg_1212)をエラーリストにセットする
				check = false;
				throw new BusinessException("Msg_1212");
			} else {
				GeneralDate today = GeneralDate.today();
				if (today.before(expiredDate)) {
					check = false;
					throw new BusinessException("Mg_825");
				} else {
					check = true;
				}
			}
		} else {
			if (unUsedDays == 0) {
				check = false;
				throw new BusinessException("Mg_1213");
			} else {
				check = true;
			}
		}
		return check;
	}

	public boolean checkUpdate(PayoutManagementData data, boolean checkBox, int stateAtr, GeneralDate expiredDate,
			double unUsedDays) {
		boolean update = false;
		// 振出（年月日）チェック処理
		boolean check = checkProcess();
		if (check) {
			// 振休管理データの修正（振出設定）入力項目チェック処理
			boolean checkBoxData = checkboxData(checkBox, stateAtr, expiredDate, unUsedDays);
			if (checkBoxData) {
				payoutManagementDataRepository.update(data);

				// **chưa có :(Thực hiện thuật toán 「振休残数管理データ更新フラグ処理」):bât cờ flag

				update = true;
			} else {
				// error
				update = false;
			}
		} else {
			// error
			update = false;
		}
		return update;
	}

	public void update(PayoutManagementData data, boolean checkBox, int stateAtr, GeneralDate expiredDate,
			double unUsedDays) {
		boolean update = checkUpdate(data, checkBox, stateAtr, expiredDate, unUsedDays);
		if (update) {
			// hiển thị message
			throw new BusinessException("Mg_15");
		} else {
			throw new BusinessException("Error");
			// error
		}
	}
	
	
	
}
