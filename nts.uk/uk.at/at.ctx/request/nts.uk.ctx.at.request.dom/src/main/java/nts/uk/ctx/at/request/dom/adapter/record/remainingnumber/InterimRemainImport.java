package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 暫定残数管理データ
 * @author masaaki_jinno
 *
 */
@Getter
public abstract class InterimRemainImport {

	/** 残数管理データID */
	private String remainManaID;
	/** 社員ID */
	private String sID;
	/** 対象日 */
	private GeneralDate ymd;
	/** 作成元区分 CreateAtr*/
	private int creatorAtr;
	/** 残数種類 RemainType*/
	private int remainType;
	/** 勤務種類 */
	private String workTypeCode;

}
