package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
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
	private boolean createNewEmpDailyPerf;

	/**
	 * The Daily performance classification.<br>
	 * 日別実績の作成・計算区分
	 */
	private boolean dailyPerfCls;

	public static DailyPerformanceCreationDto createFromDomain(DailyPerformanceCreation domain) {
		if (domain == null) {
			return null;
		}
		DailyPerformanceCreationDto dto = new DailyPerformanceCreationDto();
		dto.dailyPerfCls = domain.getDailyPerfCls().equals(NotUseAtr.USE);
		dto.dailyPerfItem = domain.getDailyPerfItem().value;
		dto.createNewEmpDailyPerf = domain.getCreateNewEmpDailyPerf().equals(NotUseAtr.USE);
		return dto;
	}

	public DailyPerformanceCreation toDomain() {
		return DailyPerformanceCreation.builder()
				.createNewEmpDailyPerf(this.createNewEmpDailyPerf ? NotUseAtr.USE : NotUseAtr.NOT_USE)
				.dailyPerfCls(this.dailyPerfCls ? NotUseAtr.USE : NotUseAtr.NOT_USE)
				.dailyPerfItem(EnumAdaptor.valueOf(dailyPerfItem, DailyPerformanceItem.class))
				.build();
	}
}
