package nts.uk.ctx.hr.develop.app.jmm018retire.dto;

import java.util.Optional;

import lombok.Data;

@Data
public class ReferItemDto {
	/** 評価項目  */
	private int evaluationItem;
	
	/** 参照する */
	private boolean usageFlg;
	
	/** 評価表示数 */
	private Integer displayNum;
	
	/** 判断基準値 */
	private Optional<String> passValue;

	public ReferItemDto(int evaluationItem, boolean usageFlg, Integer displayNum, Optional<String> passValue) {
		super();
		this.evaluationItem = evaluationItem;
		this.usageFlg = usageFlg;
		this.displayNum = displayNum;
		this.passValue = passValue;
	}
}
