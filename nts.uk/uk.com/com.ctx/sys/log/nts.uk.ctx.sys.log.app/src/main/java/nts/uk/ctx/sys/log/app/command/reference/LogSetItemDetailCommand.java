package nts.uk.ctx.sys.log.app.command.reference;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.log.dom.reference.LogCondition;
import nts.uk.ctx.sys.log.dom.reference.LogSetItemDetail;
import nts.uk.ctx.sys.log.dom.reference.SymbolEnum;

@Setter
@Getter
public class LogSetItemDetailCommand {
	/** The Item No*/
	private int itemNo;
	
	// 枠
	/** The Frame */
	private int frame;
	
	// 使用区分
	/** The Using Condition Flag */
	private int isUseCondFlg;
	
	// 条件
	/** The Log Condition */
	private String condition;
	
	// 記号
	/** The Symbol Condition */
	private int sybol;
	
	public LogSetItemDetail toDomain(String logSetId) {
		boolean isUseCondFlg = this.isUseCondFlg == 1 ? true : false;
		return new LogSetItemDetail(logSetId, itemNo, frame, isUseCondFlg, 
				Optional.of(new LogCondition(condition)), Optional.of(SymbolEnum.valueOf(sybol)));			
	}
}
