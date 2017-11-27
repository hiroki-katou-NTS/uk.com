package nts.uk.ctx.bs.employee.dom.jobtile.affiliate;

public interface AffJobTitleHistoryItemRepository_v1 {
		/**
		 * ドメインモデル「職務職位」を新規登録する
		 * @param domain
		 */
		void addJobTitleMain(AffJobTitleHistoryItem domain);
		
		/**
		 * 取得した「職務職位」を更新する
		 * @param domain
		 */
		void updateJobTitleMain(AffJobTitleHistoryItem domain);
		
		/**
		 * ドメインモデル「職務職位」を削除する
		 * @param jobTitleMainId
		 */
		void deleteJobTitleMain(String jobTitleMainId);
}
