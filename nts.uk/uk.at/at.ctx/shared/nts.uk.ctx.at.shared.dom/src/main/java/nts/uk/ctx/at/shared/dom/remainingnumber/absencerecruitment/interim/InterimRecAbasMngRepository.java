package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimRecAbasMngRepository {
	/**
	 * 暫定振出管理データ
	 * @param recId
	 * @return
	 */
	Optional<InterimRecMng> getReruitmentById(String recId);
	
	/**
	 * 暫定振休管理データ
	 * @param absId
	 * @return
	 */
	Optional<InterimAbsMng> getAbsById(String absId);
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param interimId
	 * @param isRec: True: 振出管理データ, false: 振休管理データ
	 * @return
	 */
	Optional<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr);
	/**
	 * ドメインモデル「暫定振出管理データ」を取得する
	 * @param recId
	 * @param unUseDays 未使用日数＞unUseDays
	 * @param dateData ・使用期限日が指定期間内
	 * ・使用期限日≧INPUT.期間.開始年月日
	 * ・使用期限日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<InterimRecMng> getRecByIdPeriod(List<String> recId, Double unUseDays, DatePeriod dateData);
	
}
