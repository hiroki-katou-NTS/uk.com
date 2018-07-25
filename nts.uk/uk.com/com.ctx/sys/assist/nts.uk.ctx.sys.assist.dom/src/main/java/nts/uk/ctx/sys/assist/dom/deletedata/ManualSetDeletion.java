package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
/**
 * データ削除の手動設定
 */
public class ManualSetDeletion extends AggregateRoot {

	// データ削除処理ID
	/** The deletion Id */
	private String delId;

	// 会社ID
	/** The company Id. */
	private String companyId;

	// システム種類
	/** The system type. */
	private int systemType;

	// 削除名称
	/** The deletion name. */
	private DelName delName;

	// 削除前に保存
	/** The saving before deletion flag. */
	private boolean isSaveBeforeDeleteFlg;

	// パスワード有無
	/** The existing compress pass flag. */
	private boolean isExistCompressPassFlg;

	// 手動保存の圧縮パスワード
	/** The password encrypt for compress file. */
	private PasswordCompressFileEncrypt passwordCompressFileEncrypt;

	// 社員指定の有無
	/** The having employee specified flag. */
	private boolean haveEmployeeSpecifiedFlg;

	// 実行者
	/** The employee Id. */
	private String sId;

	// 補足説明
	/** The supplement explanation. */
	private SupplementExplanation supplementExplanation;

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
	private int startYearOfMonthly;

	// 年次終了年
	/** The end year of monthly. */
	private int endYearOfMonthly;
	

	public static ManualSetDeletion createFromJavatype(String delId, String companyId, int systemType, String delName,
			boolean isSaveBeforeDeleteFlg, boolean isExistCompressPassFlg, String passwordCompressFileEncrypt,
			boolean haveEmployeeSpecifiedFlg, String sId, String supplementExplanation, GeneralDate referenceDate,
			GeneralDateTime executionDateTime, GeneralDate startDateOfDaily, GeneralDate endDateOfDaily,
			GeneralDate startMonthOfMonthly, GeneralDate endMonthOfMonthly, int startYearOfMonthly, int endYearOfMonthly) {
		return new ManualSetDeletion(delId, companyId, systemType, new DelName(delName), isSaveBeforeDeleteFlg,
				isExistCompressPassFlg, new PasswordCompressFileEncrypt(passwordCompressFileEncrypt),
				haveEmployeeSpecifiedFlg, sId, new SupplementExplanation(supplementExplanation), referenceDate,
				executionDateTime, startDateOfDaily, endDateOfDaily, startMonthOfMonthly, endMonthOfMonthly,
				startYearOfMonthly, endYearOfMonthly);
	}
}
