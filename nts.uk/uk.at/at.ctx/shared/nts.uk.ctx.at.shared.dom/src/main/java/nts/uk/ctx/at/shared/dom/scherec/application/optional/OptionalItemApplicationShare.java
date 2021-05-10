package nts.uk.ctx.at.shared.dom.scherec.application.optional;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.任意項目申請.任意項目申請
 *
 * @author thanhnx
 */
@Getter
@Setter
public class OptionalItemApplicationShare extends ApplicationShare {
	
	/**
	 * 申請値
	 */
	private List<AnyItemValue> optionalItems;

	public OptionalItemApplicationShare(List<AnyItemValue> optionalItems,
			ApplicationShare appShare) {
         super(appShare);
         this.optionalItems = optionalItems;
	}
}
