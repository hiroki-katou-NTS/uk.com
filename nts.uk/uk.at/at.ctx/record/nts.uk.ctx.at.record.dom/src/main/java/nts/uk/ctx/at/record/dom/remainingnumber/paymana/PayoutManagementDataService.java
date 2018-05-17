package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.ws.json.serializer.GeneralDateDeserializer;
import nts.arc.time.GeneralDate;

@Stateless
public class PayoutManagementDataService {

	@Inject
	private PayoutManagementDataRepository payoutMNDTRepo;

	/**
	 * KDM001 screen G
	 */
	// (Thực hiện thuật toán 「振出（年月日）チェック処理」"xử lý check chuyển đổi (năm tháng
	// ngày)")
	public boolean checkProcess() {
		// todo somethings

		return false;
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
				payoutMNDTRepo.update(data);
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
