package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

/**
 * 休暇加算設定を取得する
 * @author shuichu_ishida
 */
public interface GetVacationAddSet {

	/**
	 * 休暇加算設定を取得する
	 * @param companyId 会社ID
	 * @return 休暇加算設定
	 */
	VacationAddSet get(String companyId);
}
