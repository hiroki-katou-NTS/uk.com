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
	public boolean checkboxData(boolean checkBox, int stateAtr, GeneralDate expiredDate, double unUsedDays ) {
		if (checkBox) {
			if (stateAtr == 2) {
				// message : エラーメッセージ(Msg_1212)をエラーリストにセットする
				throw new BusinessException("Msg_1212");
			} else {
				GeneralDate today = GeneralDate.today();
			}
		} else {

		}
		return false;
	}

	public boolean checkUpdate(PayoutManagementData data, boolean checkBox, int stateAtr,  GeneralDate expiredDate, double unUsedDays) {
		boolean update = false;
		// (Thực hiện thuật toán 「振出（年月日）チェック処理」"xử lý check chuyển đổi (năm
		// tháng ngày)")
		boolean check = checkProcess();
		if (check) {
			// (Thực hiện thuật toán 「Ｇ．振休管理データの修正（振出設定）入力項目チェック処理」)

			boolean checkBoxData = checkboxData(checkBox, stateAtr, expiredDate,unUsedDays);
			if (checkBoxData) {
				// update

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

	public void update(PayoutManagementData data, boolean checkBox, int stateAtr, GeneralDate expiredDate, double unUsedDays) {
		boolean up = checkUpdate(data, checkBox, stateAtr, expiredDate, unUsedDays);
		if (up) {
			// hiển thị message
		} else {
			// error
		}

	}

}
