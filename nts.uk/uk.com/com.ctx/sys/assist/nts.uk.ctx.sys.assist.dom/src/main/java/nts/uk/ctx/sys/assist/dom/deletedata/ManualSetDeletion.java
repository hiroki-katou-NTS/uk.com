package nts.uk.ctx.sys.assist.dom.deletedata;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.PatternCode;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;

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
	private Optional<PasswordCompressFileEncrypt> passwordCompressFileEncrypt;

	// 社員指定の有無
	/** The having employee specified flag. */
	private boolean haveEmployeeSpecifiedFlg;

	// 実行者
	/** The employee Id. */
	private String sId;

	// 補足説明
	/** The supplement explanation. */
	private Optional<SupplementExplanation> supplementExplanation;

	// 基準日
	/** The reference date. */
	private Optional<GeneralDate> referenceDate;

	// 実行日時
	/** The execution date time. */
	private GeneralDateTime executionDateTime;

	// 日次削除開始日
	/** The start date of daily. */
	private Optional<GeneralDate> startDateOfDaily;

	// 日次削除終了日
	/** The end date of daily. */
	private Optional<GeneralDate> endDateOfDaily;

	// 月次削除開始月
	/** The start month of monthly. */
	private Optional<GeneralDate> startMonthOfMonthly;

	// 月次削除終了月
	/** The end month of monthly. */
	private Optional<GeneralDate> endMonthOfMonthly;

	// 年次開始年
	/** The start year of monthly. */
	private Optional<Integer> startYearOfMonthly;

	// 年次終了年
	/** The end year of monthly. */
	private Optional<Integer> endYearOfMonthly;
	
	/**
	 * 実行区分
	 */
	private StorageClassification executeClassification;
	
	/**
	 * 削除パターン
	 */
	private PatternCode delPattern;
	
	/**
	 * 対象カテゴリ
	 */
	private List<CategoryDeletion> categories;

	public static ManualSetDeletion createFromJavatype(String delId, String companyId, String delName,
			boolean isSaveBeforeDeleteFlg, boolean isExistCompressPassFlg, String passwordCompressFileEncrypt,
			boolean haveEmployeeSpecifiedFlg, String sId, String supplementExplanation, GeneralDate referenceDate,
			GeneralDateTime executionDateTime, GeneralDate startDateOfDaily, GeneralDate endDateOfDaily,
			Integer startMonthOfMonthly, Integer endMonthOfMonthly, Integer startYearOfMonthly, Integer endYearOfMonthly,
			int executeClassification, String delPattern, List<CategoryDeletion> categories) {
		return new ManualSetDeletion(delId, companyId, new DelName(delName), isSaveBeforeDeleteFlg,
				isExistCompressPassFlg, 
				Optional.ofNullable(new PasswordCompressFileEncrypt(passwordCompressFileEncrypt)),
				haveEmployeeSpecifiedFlg, sId, Optional.ofNullable(new  SupplementExplanation(supplementExplanation)), 
				Optional.ofNullable(referenceDate),
				executionDateTime, 
				Optional.ofNullable(startDateOfDaily), Optional.ofNullable(endDateOfDaily), 
				convertIntToYearStartMonth(Optional.ofNullable(startMonthOfMonthly)), 
				convertIntToYearStartMonth(Optional.ofNullable(endMonthOfMonthly)), 
				Optional.ofNullable(startYearOfMonthly), Optional.ofNullable(endYearOfMonthly),
				EnumAdaptor.valueOf(executeClassification, StorageClassification.class),
				new PatternCode(delPattern), categories);
	}
	public static Optional<Integer> convertYearMonthToInt(Optional<GeneralDate> yearMonth) {
		if (yearMonth.isPresent()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
			String formattedString = yearMonth.get().localDate().format(formatter);
			Integer formattedInt = Integer.valueOf(formattedString);
			return Optional.ofNullable(formattedInt);
		} else {
			return Optional.empty();
		}
	}
	
	public static Optional<GeneralDate> convertIntToYearStartMonth(Optional<Integer> yearMonth) {
		if (yearMonth.isPresent()) {
			String date = String.valueOf(yearMonth.get());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
			YearMonth yearMonthTime = YearMonth.parse(date, formatter);
			LocalDate localDate = yearMonthTime.atDay(1);
			return Optional.ofNullable(GeneralDate.localDate(localDate));
		} else {
			return Optional.empty();
		}
	}
	
	public static Optional<GeneralDate> convertIntToYearEndMonth(Optional<Integer> yearMonth) {
		if (yearMonth.isPresent()) {
			String date = String.valueOf(yearMonth.get());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
			YearMonth yearMonthTime = YearMonth.parse(date, formatter);
			LocalDate localDate = yearMonthTime.atEndOfMonth();
			return Optional.ofNullable(GeneralDate.localDate(localDate));
		} else {
			return Optional.empty();
		}
	}
}