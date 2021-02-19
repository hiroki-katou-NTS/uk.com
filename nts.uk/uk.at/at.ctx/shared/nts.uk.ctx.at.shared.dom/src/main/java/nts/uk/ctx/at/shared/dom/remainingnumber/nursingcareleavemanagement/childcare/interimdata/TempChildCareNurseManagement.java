package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
* 暫定子の看護介護管理データ
* @author yuri_tamakoshi
*/
@Getter
@Setter
public class TempChildCareNurseManagement extends InterimRemain{

	/** 使用数 */
	private ChildCareNurseUsedNumber usedNumber;
	/** 時間休暇種類 */
	private  Optional<DigestionHourlyTimeType> appTimeType;

	/**
	 * コンストラクタ
	 */
	public TempChildCareNurseManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType, RemainAtr remainAtr){
		super(remainManaID, sID, ymd, creatorAtr, remainType, remainAtr);
		this.usedNumber = new ChildCareNurseUsedNumber();
		this.appTimeType = Optional.empty();
	}
	/**
	 * コンストラクタ
	 */
	public TempChildCareNurseManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType, RemainAtr remainAtr,
			ChildCareNurseUsedNumber usedNumber, Optional<DigestionHourlyTimeType> appTimeType){
		super(remainManaID, sID, ymd, creatorAtr, remainType, remainAtr);
		this.usedNumber = usedNumber;
		this.appTimeType = appTimeType;
	}

	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param timezoneToUseHourlyHoliday 時間休暇種類
	 * @return 暫定子の看護管理データ
	 */
	public static TempChildCareNurseManagement of(
			String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType, RemainAtr remainAtr,
			ChildCareNurseUsedNumber usedNumber,
			Optional<DigestionHourlyTimeType>  appTimeType) {

		TempChildCareNurseManagement domain = new TempChildCareNurseManagement(remainManaID, sID ,ymd, creatorAtr, remainType, remainAtr);
		domain.usedNumber = usedNumber;
		domain.appTimeType = appTimeType;
		return domain;
	}
}
