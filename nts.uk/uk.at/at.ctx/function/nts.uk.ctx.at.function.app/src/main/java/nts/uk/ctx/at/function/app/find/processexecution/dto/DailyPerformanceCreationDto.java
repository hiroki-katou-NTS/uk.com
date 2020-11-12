package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;

/**
 * The class Daily performance creation dto.<br>
 * Dto 日別実績の作成・計算
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class DailyPerformanceCreationDto {

	/**
	 * 日別実績の作成・計算区分
	 */
	private boolean dailyPerfCls;

	/**
	 * 作成・計算項目
	 */
	private int dailyPerfItem;

	/**
	 * 対象者区分
	 */
	private TargetGroupClassification targetGroupClassification;

	/**
	 * No args constructor.
	 */
	private DailyPerformanceCreationDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Daily performance creation dto
	 */
	public static DailyPerformanceCreationDto createFromDomain(DailyPerformanceCreation domain) {
		if (domain == null) {
			return null;
		}
		DailyPerformanceCreationDto dto = new DailyPerformanceCreationDto();
		dto.dailyPerfCls = domain.getDailyPerfCls().value == 1;
		dto.dailyPerfItem = domain.getDailyPerfItem().value;
		dto.targetGroupClassification = domain.getTargetGroupClassification();
		return dto;
	}

}
