package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
 *　暫定子の看護管理データ
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class TempChildCareManagement  extends TempChildCareNurseManagement {

	/**
	 * コンストラクタ
	 */
	public TempChildCareManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainAtr remainAtr,
			ChildCareNurseUsedNumber usedNumber, Optional<DigestionHourlyTimeType> appTimeType){
		super(remainManaID, sID, ymd, creatorAtr, RemainType.CHILDCARE, remainAtr, usedNumber, appTimeType);
	}
	/**
	 * ファクトリー
	 * @param remainManaID 残数管理データID
	 * @param sID 社員ID
	 * @param ymd 対象日
	 * @param creatorAtr 作成元区分
	 * @param remainAtr 残数分類
	 * @param usedNumber 使用数
	 * @param appTimeType 時間休暇種類
	 * @return 暫定子の看護管理データ
	 */
	public static TempChildCareManagement of(
			String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainAtr remainAtr,
			ChildCareNurseUsedNumber usedNumber,
			Optional<DigestionHourlyTimeType>  appTimeType) {

		return new TempChildCareManagement(remainManaID, sID, ymd, creatorAtr, remainAtr, usedNumber, appTimeType);
	}
}
