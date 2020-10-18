package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
 * 職場別の承認者（36協定）Repository
 *
 * Table: KRCMT_36AGR_APV_WKP
 *
 * @author khai.dh
 */

public interface Approver36AgrByWorkplaceRepo {

	/**
	 * [1] Insert(職場別の承認者（36協定）)
	 */
	public void insert(Approver36AgrByWorkplace domain);

	/**
	 * [2] Update(職場別の承認者（36協定）)
	 */
	public void update(Approver36AgrByWorkplace domain);


	/**
	 * [3] Delete(職場別の承認者（36協定）)
	 */
	public void delete(Approver36AgrByWorkplace domain);

	/**
	 * [4] get
	 * 指定職場の全ての職場別の承認者（36協定）を取得する
	 *
	 * AggregateRoot
	 * 		職場別の承認者（36協定） m
	 * Where
	 * 		m.職場ID = 職場ID
	 */
	public List<Approver36AgrByWorkplace> getByWorkplaceId(String wkpId);

	/**
	 * [5] 指定日以降の履歴を取得する
	 * 指定日以降の職場別の承認者（36協定）を取得する
	 *
	 * AggregateRoot
	 * 		職場別の承認者（36協定） m
	 * Where
	 * 		m.職場ID = 職場ID
	 * 		m.期間.開始日 >= 年月日
	 */
	public List<Approver36AgrByWorkplace> getByWorkplaceIdFromDate(String wkpId, GeneralDate date);

	/**
	 * [6] 指定終了日の履歴を取得する
	 * 指定日する終了日の職場別の承認者（36協定）の履歴を取得する
	 *
	 * AggregateRoot
	 * 		職場別の承認者（36協定） m
	 * Where
	 * 		m.職場ID = 職場ID
	 * 		m.期間.終了日 = 終了日
	 */
	public Optional<Approver36AgrByWorkplace> getByWorkplaceIdAndEndDate(String wkpId, GeneralDate endDate);

	/**
	 * [7] get
	 * 基準日時点に指定の職場の承認者を取得する
	 *
	 * AggregateRoot
	 * 		職場別の承認者（36協定） m
	 * Where
	 * 		m.職場ID = 職場ID
	 * 		m.期間.開始日 <= 基準日 <= m.期間.終了日
	 */
	public Optional<Approver36AgrByWorkplace> getByWorkplaceIdAndDate(String wkpId, GeneralDate refDate);

	/**
	 * [8] Update
	 * @param domain
	 * @param startDateBeforeChange
	 */
	public void updateStartDate(Approver36AgrByWorkplace domain,GeneralDate startDateBeforeChange);
}
