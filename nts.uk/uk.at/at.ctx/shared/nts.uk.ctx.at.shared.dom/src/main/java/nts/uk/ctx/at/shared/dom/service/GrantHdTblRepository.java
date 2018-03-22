package nts.uk.ctx.at.shared.dom.service;
/**
 * validate grant holiday table and length service table
 * @author yennth
 *
 */
public interface GrantHdTblRepository {
	/**
	 * 「年休設定．年休管理設定．半日年休管理．管理区分＝管理する」 かつ「年休設定．年休管理設定．半日年休管理．参照先＝年休付与テーブルを参照」 の場合、半日年休上限回数を登録すること
	 * @param companyId
	 * @return
	 * @author yennth
	 */
	public Boolean checkLimitTime();
	/**
	 * 
	 * @param companyId
	 * @return
	 * @author yennth
	 */
	public Boolean checkLimitDay();
}
