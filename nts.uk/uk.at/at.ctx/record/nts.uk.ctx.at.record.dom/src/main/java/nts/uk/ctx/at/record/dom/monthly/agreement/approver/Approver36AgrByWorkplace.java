package nts.uk.ctx.at.record.dom.monthly.agreement.approver; // TODO xem lại package

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 職場別の承認者（36協定）
 * @author khai.dh
 */
@Getter
public class Approver36AgrByWorkplace extends AggregateRoot {

	/**
	 * 職場ID
	 */
	private final String workplaceId;

	/**
	 * 期間
	 */
	@Setter
	private DatePeriod period;

	/**
	 * 承認者リスト
	 */
	private List<String> approverIds;
	/**
	 * 確認者リスト
	 */
	private List<String> confirmerIds;

	/**
	 * [C-1] 新規作成
	 *
	 * 新しい職場別の承認者（36協定）を作る。
	 */
	public static Approver36AgrByWorkplace create(
			String workplaceId,
			DatePeriod period,
			List<String> approverIds,
			List<String> confirmerIds){

		//inv-1		@承認者リスト.size > 0
		if (approverIds == null || approverIds.size() <= 0) {
			throw new BusinessException("Msg_1790");
		}

		//inv-2		@承認者リスト.size < 6
		if (approverIds.size() >= 6) {
			throw new BusinessException("Msg_1791");
		}

		//inv-3		@確認者リスト.size < 6
		if (confirmerIds != null && confirmerIds.size() >= 6) {
			throw new BusinessException("Msg_1792");
		}

		return new Approver36AgrByWorkplace(workplaceId, period, approverIds, confirmerIds);
	}

	private Approver36AgrByWorkplace(
			String workplaceId,
			DatePeriod period,
			List<String> approverIds,
			List<String> confirmerIds){

		this.workplaceId = workplaceId;
		this.period = period;
		this.approverIds = approverIds;
		this.confirmerIds = confirmerIds;
	}
}
