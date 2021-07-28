package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;
/**
 * 夜勤上限時間Repository
 * @author lan_lt
 *
 */
public interface NightShiftUpperLimitTimeRepository {

	/**
	 * get
	 * @param contractCode 契約コード
	 * @return
	 */
	NightShiftUpperLimitTime getNightShiftUpperLimitTime(String contractCode);
}
