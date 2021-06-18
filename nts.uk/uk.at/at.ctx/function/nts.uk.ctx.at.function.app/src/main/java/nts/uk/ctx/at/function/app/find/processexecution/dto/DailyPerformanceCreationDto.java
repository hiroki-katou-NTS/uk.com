package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Daily performance creation dto.<br>
 * Dto 日別実績の作成・計算
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyPerformanceCreationDto {

	/**
	 * The Daily performance item.<br>
	 * 作成・計算項目
	 */
	private int dailyPerfItem;

	/**
	 * The Create new employee daily performance.<br>
	 * 新入社員は入社日から作成
	 */
	private int createNewEmpDailyPerf;

	/**
	 * The Daily performance classification.<br>
	 * 日別実績の作成・計算区分
	 */
	private boolean dailyPerfCls;

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
		dto.dailyPerfCls = domain.getDailyPerfCls() == NotUseAtr.USE;
		dto.dailyPerfItem = domain.getDailyPerfItem().value;
		dto.createNewEmpDailyPerf = domain.getCreateNewEmpDailyPerf().value;
		return dto;
	}

	/**
	 * Converts <code>DailyPerformanceCreation</code> to domain.
	 *
	 * @return the domain Daily performance creation
	 */
	public DailyPerformanceCreation toDomain() {
		return new DailyPerformanceCreation(this.dailyPerfItem, this.createNewEmpDailyPerf, this.dailyPerfCls);
	}

}
