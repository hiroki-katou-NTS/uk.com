package nts.uk.ctx.sys.log.app.find.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.log.dom.reference.LogSetItemDetail;

/*
 * author: hiep.th
 */

@Data
@AllArgsConstructor
public class LogSetItemDetailDto {

	public LogSetItemDetailDto() {
		super();
	}

	// Id
	/** The Log Setting Id. */
	private String logSetId;

	// 項目NO
	/** The Item No */
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
	private Integer sybol;

	public static LogSetItemDetailDto fromDomain(LogSetItemDetail domain) {
		int isUseCondFlg = domain.isUseCondFlg() ? 1 : 0;
		String condition = domain.getCondition().isPresent() ? domain.getCondition().get().v() : null;
		Integer sybol = domain.getSymbol().isPresent() ? domain.getSymbol().get().code : null;
		return new LogSetItemDetailDto(domain.getLogSetId(), domain.getItemNo(), domain.getFrame(), isUseCondFlg,
				condition, sybol);
	}

}
