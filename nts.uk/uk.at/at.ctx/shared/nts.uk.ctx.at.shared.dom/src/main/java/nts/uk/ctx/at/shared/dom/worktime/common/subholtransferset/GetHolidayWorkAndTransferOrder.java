package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.List;

/**
 * 休出・振替の処理順序を取得する
 * @author shuichu_ishida
 */
public interface GetHolidayWorkAndTransferOrder {

	/**
	 * 休出・振替の処理順序を取得する
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @param reverse 逆時系列にする
	 * @return 休出振替区分リスト　（処理順）
	 */
	List<HolidayWorkAndTransferAtr> get(String companyId, String workTimeCode, boolean reverse);
}
