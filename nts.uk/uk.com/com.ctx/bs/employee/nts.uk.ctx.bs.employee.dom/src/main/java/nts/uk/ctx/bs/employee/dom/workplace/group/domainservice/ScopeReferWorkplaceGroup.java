package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * Enum -職場グループ内の参照範囲 UKDesign.ドメインモデル."NittsuSystem.UniversalK".基幹.社員.職場.職場グループ
 * @author HieuLt
 */
@RequiredArgsConstructor
public enum ScopeReferWorkplaceGroup {
	// 0:全社員
	ALL_EMPLOYEE(0),

	// 1:自分のみ
	ONLY_ME(1);

	public final int value;

	public static ScopeReferWorkplaceGroup of(int value) {
		return EnumAdaptor.valueOf(value, ScopeReferWorkplaceGroup.class);
	}
}
