package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL017_60H超休ダイアログ.A：60H超休参照.アルゴリズム.60超過時間表示情報パラメータ.残数情報
 * 
 * @author LienPTK
 */
@Data
public class RemainNumberDetailDto {

	/** 発生月 */
	private YearMonth occurrenceMonth;

	/** 使用日 */
	private GeneralDate usageDate;

	/** 発生時間 */
	private Integer occurrenceTime;

	/** 使用時間 */
	private Integer usageTime;

	/** 期限日 */
	private GeneralDate deadline;

	/** 作成区分 */
	private Integer creationCategory;

}
