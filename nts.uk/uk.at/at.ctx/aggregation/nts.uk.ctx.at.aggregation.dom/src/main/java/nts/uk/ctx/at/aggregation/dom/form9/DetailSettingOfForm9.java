package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 様式９の明細設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の明細設定
 * @author lan_lt
 *
 */
@Value
public class DetailSettingOfForm9 implements DomainValue {
	
	/** 明細開始行 **/
	private final OutputRow bodyStartRow;
	
	/** 表示人数 **/
	private final OnePageDisplayNumerOfPeople maxNumerOfPeople;
	
	/** 日にち行 **/
	private final OutputRow rowDate;
	
	/** 曜日行 **/
	private final OutputRow rowDayOfWeek;
	
	public static DetailSettingOfForm9 create(OutputRow bodyStartRow
			,	OnePageDisplayNumerOfPeople maxNumerOfPeople
			,	OutputRow rowDate
			,	OutputRow rowDayOfWeek) {
		
		val rows = Arrays.asList( bodyStartRow, rowDate, rowDayOfWeek );
		
		val rowsDistinct = rows.stream()
				.distinct()
				.collect(Collectors.toList());
		
		if(rows.size() != rowsDistinct.size()) {
			throw new BusinessException("Msg_2289");
		}
		
		return new DetailSettingOfForm9( bodyStartRow, maxNumerOfPeople, rowDate, rowDayOfWeek);
	}
	
}
