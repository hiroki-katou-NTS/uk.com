package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;

import java.util.List;

/**
 * 承認者項目
 *
 * @author khai.dh
 *
 */
@Getter
@AllArgsConstructor
public class ApproverItem implements DomainValue {

	/**
	 * 承認者リスト
	 */
	private List<String> approverList;

	/**
	 * 確認者リスト
	 */
	private List<String> confirmerList;
}
