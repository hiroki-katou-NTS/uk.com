package nts.uk.ctx.sys.log.dom.reference;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * author: hiep.th
 */

@Getter
@Setter
@AllArgsConstructor
/**
 * LogOutputItem.java
 */
public class LogSetItemDetail {
	
	// Id
	/** The Log Setting Id. */
	private String logSetId;

	// 項目NO
	/** The Item No*/
	private int itemNo;
	
	// 枠
	/** The Frame */
	private int frame;
	
	// 使用区分
	/** The Using Condition Flag */
	private boolean isUseCondFlg;
	
	// 条件
	/** The Log Condition */
	private Optional<LogCondition> condition;
	
	// 記号
	/** The Symbol Condition */
	private Optional<SymbolEnum> symbol;
	
	
	public static LogSetItemDetail createFromJavatype(String logSetId, int itemNo, 
			int frame, boolean isUseCondFlg, String condition, int symbol) {
		return new LogSetItemDetail(logSetId, itemNo, frame, isUseCondFlg, 
				Optional.of(new LogCondition(condition)), Optional.of(SymbolEnum.valueOf(symbol)));
	}
}
