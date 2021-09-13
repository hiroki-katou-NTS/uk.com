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
	private final Optional<OutputColumn> cellYear;

	/** 集計月 **/
	private final Optional<OutputColumn> cellMonth;
	
	/** 夜勤時間帯の開始時刻 **/
	private final Optional<OutputColumn> cellStartTime;

	/** 夜勤時間帯の終了時刻 **/
	private final Optional<OutputColumn> cellEndTime;
	
	/** 出力情報のタイトル **/
	private final Optional<OutputColumn> cellTitle;

	/** 出力情報の期間 **/
	private final Optional<OutputColumn> cellPrintPeriod;
	
	public static Form9Cover create(Optional<OutputColumn> cellYear
			,	Optional<OutputColumn> cellMonth
			,	Optional<OutputColumn> cellStartTime
			,	Optional<OutputColumn> cellEndTime
			,	Optional<OutputColumn> cellTitle
			,	Optional<OutputColumn> cellPrintPeriod) {
		
		val items = Arrays.asList(cellYear, cellMonth, cellStartTime
					, cellEndTime,cellTitle, cellPrintPeriod)
				.stream()
				.flatMap(OptionalUtil::stream).collect(Collectors.toList());
		val itemsDistinct = items.stream().distinct().collect(Collectors.toList());
		
		if(items.size() != itemsDistinct.size()) {
			throw new BusinessException("Msg_2244");
		}
		
		return new Form9Cover(cellYear, cellMonth, cellStartTime, cellEndTime, cellTitle, cellPrintPeriod);
		
	}
	
}
