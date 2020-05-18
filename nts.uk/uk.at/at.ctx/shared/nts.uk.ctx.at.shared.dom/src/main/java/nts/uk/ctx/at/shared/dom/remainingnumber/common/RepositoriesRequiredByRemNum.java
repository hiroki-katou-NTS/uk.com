package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * 残数処理 キャッシュクラス（１回の処理単位）
 * データベースから１度ロードしたデータを、残数処理の中で再利用する
 * （パフォーマンス対策）
 * @author masaaki_jinno
 *
 */
@Stateless
@Getter
public class RepositoriesRequiredByRemNum {

	/**
	 * 労働条件
	 */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	/**
	 * 年休設定
	 */
	@Inject
	private AnnualPaidLeaveSetting annualPaidLeaveSet;
}
