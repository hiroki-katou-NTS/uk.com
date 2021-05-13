package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *　介護対象期間を確認　テンポラリークラス
  * @author yuri_tamakoshi
 */
public class CareTargetPeriodWork {

	/** 看護介護対象人数変更日（List） */
	private List<ChildCareTargetChanged> childCareTargetChanged;
	/** 介護対象期間 */
	private Optional<CareTargetPeriod> careTargetPeriod;

	/**
	 * コンストラクタ
	 */
	public CareTargetPeriodWork(){
		this.childCareTargetChanged = new ArrayList<>();
		this.careTargetPeriod = Optional.empty();
	}
	/**
	 * ファクトリー
	 * @param childCareTargetChanged 看護介護対象人数変更日（List）
	 * @param careTargetPeriod 介護対象期間
	 * @return 介護対象期間
	 */
	public static CareTargetPeriodWork of(
			List<ChildCareTargetChanged> childCareTargetChanged,
			Optional<CareTargetPeriod> careTargetPeriod) {

		CareTargetPeriodWork domain = new CareTargetPeriodWork();
		domain.childCareTargetChanged = childCareTargetChanged;
		domain.careTargetPeriod = careTargetPeriod;
		return domain;
	}

}
