package nts.uk.ctx.bs.employee.dom.jobtile.affiliate;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;


public interface AffJobTitleHistoryRepository_ver1 {
		// get listbypid
		Optional<AffJobTitleHistory_ver1> getListBySid(String sid);
		
		/**
		 * ドメインモデル「職務職位」を新規登録する
		 * @param domain
		 */
		void addJobTitleMain(AffJobTitleHistory_ver1 domain);
		
		/**
		 * 取得した「職務職位」を更新する
		 * @param domain
		 */
		void updateJobTitleMain(AffJobTitleHistory_ver1 domain, DateHistoryItem item);
		
		/**
		 * ドメインモデル「職務職位」を削除する
		 * @param jobTitleMainId
		 */
		void deleteJobTitleMain(AffJobTitleHistory_ver1 domain, DateHistoryItem item);
}
