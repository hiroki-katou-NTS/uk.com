package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.pub.remainnumber.work.InterimRemainExport;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.work.DigestionHourlyTimeTypeExport;

/**
 * 暫定子の看護介護管理データ
 * @author masaaki_jinno
 *
 */
@Getter
public class TempChildCareNurseManagementExport extends InterimRemainExport {

	/** 使用数 */
	private ChildCareNurseUsedNumberExport usedNumber;
	/** 時間休暇種類 */
	private  Optional<DigestionHourlyTimeTypeExport> appTimeType;


	/**
	 * コンストラクタ
	 * @param remainManaID_in　残数管理データID
	 * @param sID_in　社員ID
	 * @param ymd_in　対象日
	 * @param creatorAtr_in　作成元区分
	 * @param remainType_in　残数種類
	 * @param workTypeCode_in　勤務種類
	 * @param usedNumber_in　使用数
	 * @param appTimeType_in　時間休暇種類
	 */
	public TempChildCareNurseManagementExport(
			String remainManaID_in,
			String sID_in,
			GeneralDate ymd_in,
			int creatorAtr_in,
			int remainType_in,
			String workTypeCode_in,
			ChildCareNurseUsedNumberExport usedNumber_in,
			Optional<DigestionHourlyTimeTypeExport> appTimeType_in){

			super(remainManaID_in, sID_in, ymd_in, creatorAtr_in, remainType_in, workTypeCode_in);

			usedNumber = usedNumber_in;
			appTimeType = appTimeType_in;
	}

}
