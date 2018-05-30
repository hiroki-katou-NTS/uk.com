package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;

/**
 * 会社の日別実績の修正のフォーマット
 * @author nampt
 *
 */
@Getter
public class AuthorityDailyPerformanceFormat extends AggregateRoot {

	private String companyId;

	/**
	 * 日別実績フォーマットコード
	 */
	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	/**
	 * 日別実績フォーマット名称
	 */
	private DailyPerformanceFormatName dailyPerformanceFormatName;

	public AuthorityDailyPerformanceFormat(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			DailyPerformanceFormatName dailyPerformanceFormatName) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.dailyPerformanceFormatName = dailyPerformanceFormatName;
	}

	public static AuthorityDailyPerformanceFormat createFromJavaType(String companyId,
			String dailyPerformanceFormatCode, String dailyPerformanceFormatName) {
		return new AuthorityDailyPerformanceFormat(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				new DailyPerformanceFormatName(dailyPerformanceFormatName));
	}

}
