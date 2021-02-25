package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL017_60H超休ダイアログ.A：60H超休参照.アルゴリズム.60超過時間表示情報パラメータ.紐付け管理
 * 
 * @author LienPTK
 */
@Data
public class PegManagementDto {
	
	/** 発生月 */
	private YearMonth occurrenceMonth;
	
	/** 使用日 */
	private GeneralDate usageDate;
	
	/** 使用数 */
	private Integer usageNumber;

}
