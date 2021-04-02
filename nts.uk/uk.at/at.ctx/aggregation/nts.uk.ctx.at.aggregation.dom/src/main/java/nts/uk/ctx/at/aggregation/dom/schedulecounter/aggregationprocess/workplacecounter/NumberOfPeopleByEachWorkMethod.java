package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 勤務方法別の人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.職場計.職場計の勤務方法別人数カテゴリを集計する.勤務方法別の人数
 * @author dan_pv
 * @param <T>
 */
@Value
public class NumberOfPeopleByEachWorkMethod<T> implements DomainValue{
	
	/**
	 * 	勤務方法
	 */
	private final T workMethod;
	
	/**
	 * 計画人数
	 */
	private final BigDecimal planNumber;
	
	/**
	 * 予定人数
	 */
	private final BigDecimal scheduleNumber;
	
	/**
	 * 	実績人数
	 */
	private final BigDecimal actualNumber;
	
}
