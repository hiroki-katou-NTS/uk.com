package nts.uk.ctx.at.record.pub.remainnumber.work;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;

/**
 * 暫定残数管理データ
 * @author masaaki_jinno
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class InterimRemainExport {

	/** 残数管理データID */
	protected String remainManaID;
	/** 社員ID */
	protected String sID;
	/** 対象日 */
	protected GeneralDate ymd;
	/** 作成元区分 CreateAtr*/
	protected int creatorAtr;
	/** 残数種類 RemainType*/
	protected int remainType;
	/** 勤務種類 */
	protected String workTypeCode;

}
