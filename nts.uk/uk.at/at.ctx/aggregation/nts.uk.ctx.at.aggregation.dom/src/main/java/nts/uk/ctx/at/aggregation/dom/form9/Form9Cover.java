package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.util.OptionalUtil;

/**
 * 様式９の表紙
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の表紙
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class Form9Cover implements DomainValue{
	/** 集計年 **/
	private final Optional<OutputCell> cellYear;

	/** 集計月 **/
	private final Optional<OutputCell> cellMonth;
	
	/** 夜勤時間帯の開始時刻 **/
	private final Optional<OutputCell> cellStartTime;

	/** 夜勤時間帯の終了時刻 **/
	private final Optional<OutputCell> cellEndTime;
	
	/** 出力情報のタイトル **/
	private final Optional<OutputCell> cellTitle;

	/** 出力情報の期間 **/
	private final Optional<OutputCell> cellPrintPeriod;
	
	/**
	 * 作る
	 * @param cellYear 集計年 
	 * @param cellMonth 集計月
	 * @param cellStartTime 夜勤時間帯の開始時刻
	 * @param cellEndTime 夜勤時間帯の終了時刻
	 * @param cellTitle 出力情報のタイトル
	 * @param cellPrintPeriod 出力情報の期間
	 * @return
	 */
	public static Form9Cover create( Optional<OutputCell> cellYear
			,	Optional<OutputCell> cellMonth
			,	Optional<OutputCell> cellStartTime
			,	Optional<OutputCell> cellEndTime
			,	Optional<OutputCell> cellTitle
			,	Optional<OutputCell> cellPrintPeriod) {
		
		val cells = Arrays.asList(cellYear, cellMonth, cellStartTime
					, cellEndTime,cellTitle, cellPrintPeriod)
				.stream()
				.flatMap(OptionalUtil::stream).collect(Collectors.toList());
		val cellsDistinct = cells.stream().distinct().collect(Collectors.toList());
		
		if(cells.size() != cellsDistinct.size()) {
			throw new BusinessException("Msg_2288");
		}
		
		return new Form9Cover(cellYear, cellMonth, cellStartTime, cellEndTime, cellTitle, cellPrintPeriod);
		
	}
	
}
