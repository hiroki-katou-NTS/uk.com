package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 会社のシフト表のルール
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.会社のシフト表のルール
 * @author hiroko_miura
 *
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ShiftTableRuleForCompany implements DomainAggregate {

	/** シフト表のルール */
	private ShiftTableRule shiftTableRule;
}
