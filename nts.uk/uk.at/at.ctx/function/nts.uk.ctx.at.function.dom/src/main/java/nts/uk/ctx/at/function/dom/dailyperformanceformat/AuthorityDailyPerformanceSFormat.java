package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;

/**
 * 会社の日別実績の修正のフォーマット
 * @author anhdt
 */
@Getter
public class AuthorityDailyPerformanceSFormat extends AggregateRoot {

	private String companyId;

	/**
	 * 日別実績フォーマットコード
	 */
	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	/**
	 * 日別実績フォーマット名称
	 */
	private DailyPerformanceFormatName dailyPerformanceFormatName;

	/**
	 * 日次項目
	 */
	private List<AuthoritySFomatDaily> dailyDisplayItem;

	/**
	 * 月次項目
	 */
	private Optional<List<AuthoritySFomatMonthly>> monthlyDisplayItem;
	
	public AuthorityDailyPerformanceSFormat(String companyId,
			DailyPerformanceFormatCode dailyPerformanceFormatCode,
			DailyPerformanceFormatName dailyPerformanceFormatName) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.dailyPerformanceFormatName = dailyPerformanceFormatName;
	}

	public AuthorityDailyPerformanceSFormat(String companyId,
			DailyPerformanceFormatCode dailyPerformanceFormatCode,
			DailyPerformanceFormatName dailyPerformanceFormatName, List<AuthoritySFomatDaily> dailyDisplayItem,
			Optional<List<AuthoritySFomatMonthly>> monthlyDisplayItem) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.dailyPerformanceFormatName = dailyPerformanceFormatName;
		this.dailyDisplayItem = dailyDisplayItem;
		this.monthlyDisplayItem = monthlyDisplayItem;
	}

	public static AuthorityDailyPerformanceSFormat createFromJavaType(String companyId,
			String dailyPerformanceFormatCode, String dailyPerformanceFormatName,
			List<AuthoritySFomatDaily> dailyDisplayItem, Optional<List<AuthoritySFomatMonthly>> monthlyDisplayItem) {
		return new AuthorityDailyPerformanceSFormat(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				new DailyPerformanceFormatName(dailyPerformanceFormatName), 
				dailyDisplayItem, 
				monthlyDisplayItem);
	}
	
	public static AuthorityDailyPerformanceSFormat createFromJavaType(String companyId,
			String dailyPerformanceFormatCode, String dailyPerformanceFormatName) {
		return new AuthorityDailyPerformanceSFormat(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				new DailyPerformanceFormatName(dailyPerformanceFormatName));
	}

}
