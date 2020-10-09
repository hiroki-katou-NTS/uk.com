package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
 * 会社別の承認者（36協定）Repository
 *
 * Table: KRCMT_36AGR_APV_CMP
 *
 * @author khai.dh
 */

public interface Approver36AgrByCompanyRepo {

	/**
	 * [1] Insert(会社別の承認者（36協定）)
	 */
	public void insert(Approver36AgrByCompany domain);

	/**
	 * [2] Update(会社別の承認者（36協定）)
	 */
	public void update(Approver36AgrByCompany domain);

	/**
	 * [3] Delete(会社別の承認者（36協定）)
	 */
	public void delete(Approver36AgrByCompany domain);

	/**
	 * [4] get
	 * 指定会社の全ての会社別の承認者（36協定）を取得する
	 *
	 * AggregateRoot
	 * 		会社別の承認者（36協定） m
	 * Where
	 * 		m.会社ID = 会社ID
	 */
	public List<Approver36AgrByCompany> getByCompanyId(String companyId);

	/**
	 * [5] 指定日以降の履歴を取得する
	 * 指定日以降の会社別の承認者（36協定）を取得する
	 *
	 * AggregateRoot
	 * 		会社別の承認者（36協定） m
	 * Where
	 * 		m.会社ID = 会社ID
	 * 		m.期間.開始日 >= 年月日
	 */
	public List<Approver36AgrByCompany> getByCompanyIdFromDate(String companyId, GeneralDate date);

	/**
	 * [6] 指定終了日の履歴を取得する
	 * 指定日する終了日の会社別の承認者（36協定）の履歴を取得する
	 *
	 * AggregateRoot
	 * 		会社別の承認者（36協定） m
	 * Where
	 * 		m.会社ID = 会社ID
	 * 		m.期間.終了日 = 終了日
	 */
	public Optional<Approver36AgrByCompany> getByCompanyIdAndEndDate(String companyId, GeneralDate endDate);

	/**
	 * [7] get
	 * 基準日時点に指定の会社別の承認者（36協定）を取得する
	 *
	 * AggregateRoot
	 * 		会社別の承認者（36協定） m
	 * Where
	 * 		m.会社ID = 会社ID
	 * 		m.期間.開始日 <= 基準日 <= m.期間.終了日
	 */
	public Optional<Approver36AgrByCompany> getByCompanyIdAndDate(String companyId, GeneralDate refDate);

	/**
	 * [8] Update(会社別の承認者（36協定）)
	 */
	public void updateStartDate(Approver36AgrByCompany domain, GeneralDate startDateBeforeChange);
}
