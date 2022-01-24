package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OutConditionSetDto {
	private String condName;
	
	// 条件名出力
	private int conditionOutputName;
	
	// 項目名出力
	private int itemOutputName;
	
	// 区切り文字
	private int delimiter;
	
	// 文字列形式
	private int stringFormat;
	
	// 出力項目コード
	private List<String> itemCode;
}
