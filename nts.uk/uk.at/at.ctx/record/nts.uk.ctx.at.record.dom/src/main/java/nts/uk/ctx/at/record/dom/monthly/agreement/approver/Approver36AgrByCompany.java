package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 会社別の承認者（36協定）
 * 36協定特別条項の適用申請の会社別の承認者の履歴を管理する
 *
 * @author khai.dh
 */
@Getter
public class Approver36AgrByCompany extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private final String companyId;

	/**
	 * 期間
	 */
	@Setter
	private DatePeriod period;

	/**
	 * 承認者リスト
	 */
	private List<String> approverList;
	/**
	 * 確認者リスト
	 */
	private List<String> confirmerList;

	/**
	 * [C-1] 新規作成
	 *
	 * 新しい会社別の承認者（36協定）を作る。
	 */
	public static Approver36AgrByCompany create(
			String cid,
			DatePeriod period,
			List<String> approverList,
			List<String> confirmerList){

		//inv-1		@承認者リスト.size > 0
		if (approverList == null || approverList.size() <= 0) {
			throw new BusinessException("Msg_1790");
		}

		//inv-2		@承認者リスト.size < 6
		if (approverList.size() >= 6) {
			throw new BusinessException("Msg_1791");
		}

		//inv-3		@確認者リスト.size < 6
		if (confirmerList != null && confirmerList.size() >= 6) {
			throw new BusinessException("Msg_1792");
		}

		return new Approver36AgrByCompany(cid, period, approverList,confirmerList);
	}

	private Approver36AgrByCompany(
			String cid,
			DatePeriod period,
			List<String> approverList,
			List<String> confirmerList){
		this.companyId = cid;
		this.period = period;
		this.approverList = approverList;
		this.confirmerList = confirmerList;
	}
}
