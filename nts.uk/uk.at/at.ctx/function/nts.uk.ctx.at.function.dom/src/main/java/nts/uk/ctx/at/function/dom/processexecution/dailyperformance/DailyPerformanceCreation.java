package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Daily performance creation.<br>
 * Domain 日別実績の作成・計算
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DailyPerformanceCreation extends DomainObject {

	/**
	 * The Daily performance item.<br>
	 * 作成・計算項目
	 */
	private DailyPerformanceItem dailyPerfItem;

	/**
	 * The Create new employee daily performance.<br>
	 * 新入社員は入社日から作成
	 */
	private NotUseAtr createNewEmpDailyPerf;

	/**
	 * The Daily performance classification.<br>
	 * 日別実績の作成・計算区分
	 */
	private NotUseAtr dailyPerfCls;

	/**
	 * Instantiates a new <code>DailyPerformanceCreation</code>.
	 *
	 * @param dailyPerfItem         the daily performance item
	 * @param createNewEmpDailyPerf the create new employee daily performance
	 * @param dailyPerfCls          the daily performance classification
	 */
	public DailyPerformanceCreation(int dailyPerfItem, int createNewEmpDailyPerf, int dailyPerfCls) {
		this.dailyPerfItem = EnumAdaptor.valueOf(dailyPerfItem, DailyPerformanceItem.class);
		this.createNewEmpDailyPerf = EnumAdaptor.valueOf(createNewEmpDailyPerf, NotUseAtr.class);
		this.dailyPerfCls = EnumAdaptor.valueOf(dailyPerfCls, NotUseAtr.class);
	}

	/**
	 * Instantiates a new <code>DailyPerformanceCreation</code>.
	 *
	 * @param dailyPerfItem         the daily performance item
	 * @param createNewEmpDailyPerf the create new employee daily performance
	 * @param dailyPerfCls          the daily performance classification
	 */
	public DailyPerformanceCreation(int dailyPerfItem, int createNewEmpDailyPerf, boolean dailyPerfCls) {
		this.dailyPerfItem = EnumAdaptor.valueOf(dailyPerfItem, DailyPerformanceItem.class);
		this.createNewEmpDailyPerf = EnumAdaptor.valueOf(createNewEmpDailyPerf, NotUseAtr.class);
		this.dailyPerfCls = dailyPerfCls ? NotUseAtr.USE : NotUseAtr.NOT_USE;
	}

}
