/**
 * 
 */
package nts.uk.ctx.sys.assist.app.find.deletedata;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;

/**
 * @author hiep.th
 *
 */

@Data
public class ManualSetDelDto {

	// データ削除処理ID
		/** The deletion Id */
		private String delId;

		// 会社ID
		/** The company Id. */
		private String companyId;

		// 削除名称
		/** The deletion name. */
		private String delName;

		// 削除前に保存
		/** The saving before deletion flag. */
		private boolean isSaveBeforeDeleteFlg;

		// パスワード有無
		/** The existing compress pass flag. */
		private boolean isExistCompressPassFlg;

		// 手動保存の圧縮パスワード
		/** The password encrypt for compress file. */
		private String passwordCompressFile;

		// 社員指定の有無
		/** The having employee specified flag. */
		private boolean haveEmployeeSpecifiedFlg;

		// 実行者
		/** The employee Id. */
		private String sId;

		// 補足説明
		/** The supplement explanation. */
		private String supplementExplanation;

		// 基準日
		/** The reference date. */
		private GeneralDate referenceDate;

		// 実行日時
		/** The execution date time. */
		private GeneralDateTime executionDateTime;

		// 日次削除開始日
		/** The start date of daily. */
		private GeneralDate startDateOfDaily;

		// 日次削除終了日
		/** The end date of daily. */
		private GeneralDate endDateOfDaily;

		// 月次削除開始月
		/** The start month of monthly. */
		private GeneralDate startMonthOfMonthly;

		// 月次削除終了月
		/** The end month of monthly. */
		private GeneralDate endMonthOfMonthly;

		// 年次開始年
		/** The start year of monthly. */
		private Integer startYearOfMonthly;

		// 年次終了年
		/** The end year of monthly. */
		private Integer endYearOfMonthly;

	public static ManualSetDelDto fromDomain(ManualSetDeletion domain) {

		return new ManualSetDelDto(domain.getDelId(), domain.getCompanyId(), domain.getDelName().v(),
				domain.isSaveBeforeDeleteFlg(), domain.isExistCompressPassFlg(), 
				domain.getPasswordCompressFileEncrypt().isPresent() ? CommonKeyCrypt.decrypt(domain.getPasswordCompressFileEncrypt().get().v()) : null, 
				domain.isHaveEmployeeSpecifiedFlg(), domain.getSId(),
				domain.getSupplementExplanation().isPresent() ? domain.getSupplementExplanation().get().v() : null, 
				domain.getReferenceDate().isPresent() ? domain.getReferenceDate().get() : null, 
				domain.getExecutionDateTime(), 
				domain.getStartDateOfDaily().isPresent() ? domain.getStartDateOfDaily().get() : null, 
				domain.getEndDateOfDaily().isPresent() ? domain.getEndDateOfDaily().get() : null, 
				domain.getStartMonthOfMonthly().isPresent() ? domain.getStartMonthOfMonthly().get() : null, 
				domain.getEndMonthOfMonthly().isPresent() ? domain.getEndMonthOfMonthly().get() : null,
				domain.getStartYearOfMonthly().isPresent() ? domain.getStartYearOfMonthly().get() : null, 
				domain.getEndYearOfMonthly().isPresent() ? domain.getEndYearOfMonthly().get() : null);
	}

	public ManualSetDelDto(String delId, String companyId, String delName,
			boolean isSaveBeforeDeleteFlg, boolean isExistCompressPassFlg,
			String passwordCompressFile, boolean haveEmployeeSpecifiedFlg, String sId,
			String supplementExplanation, GeneralDate referenceDate, GeneralDateTime executionDateTime,
			GeneralDate startDateOfDaily, GeneralDate endDateOfDaily, GeneralDate startMonthOfMonthly,
			GeneralDate endMonthOfMonthly, Integer startYearOfMonthly, Integer endYearOfMonthly) {
		super();
		this.delId = delId;
		this.companyId = companyId;
		this.delName = delName;
		this.isSaveBeforeDeleteFlg = isSaveBeforeDeleteFlg;
		this.isExistCompressPassFlg = isExistCompressPassFlg;
		this.passwordCompressFile = passwordCompressFile;
		this.haveEmployeeSpecifiedFlg = haveEmployeeSpecifiedFlg;
		this.sId = sId;
		this.supplementExplanation = supplementExplanation;
		this.referenceDate = referenceDate;
		this.executionDateTime = executionDateTime;
		this.startDateOfDaily = startDateOfDaily;
		this.endDateOfDaily = endDateOfDaily;
		this.startMonthOfMonthly = startMonthOfMonthly;
		this.endMonthOfMonthly = endMonthOfMonthly;
		this.startYearOfMonthly = startYearOfMonthly;
		this.endYearOfMonthly = endYearOfMonthly;
	}
}
