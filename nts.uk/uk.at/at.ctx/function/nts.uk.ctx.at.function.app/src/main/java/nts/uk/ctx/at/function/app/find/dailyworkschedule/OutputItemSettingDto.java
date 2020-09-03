package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;

/**
 * 出力項目設定.
 *
 * @author LienPTK
 */
@Data
public class OutputItemSettingDto {
	
	/** 出力レイアウトID */
	private String layoutId;
	
	/** コード */
	private String code;
	
	/** 名称 */
	private String name;
}
