package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;

/**
 * AR: 応援カード編集設定 path:
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援カード編集設定
 * 
 * @author nws_namnv2
 */
@Getter
@AllArgsConstructor
public class SupportCardEdit extends AggregateRoot {

	// 編集方法
	private StampCardEditMethod editMethod;

	// カードを編集する
	public SupportCardNumber editTheCard(SupportCardNumber editNumber) {
		int numberOfDigits = 6;
		if (editNumber.v().length() > numberOfDigits) {
			throw new BusinessException("Msg_2130", String.valueOf(numberOfDigits));
		}
		String supportCardNumber = this.editMethod.editCardNumber(String.valueOf(numberOfDigits),
				String.valueOf(editNumber.v()));
		return new SupportCardNumber(supportCardNumber);
	}

}
