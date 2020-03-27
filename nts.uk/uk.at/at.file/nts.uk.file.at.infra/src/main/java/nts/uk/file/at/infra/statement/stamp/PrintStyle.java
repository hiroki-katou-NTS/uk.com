package nts.uk.file.at.infra.statement.stamp;

import com.aspose.cells.Style;

import lombok.Data;

@Data
public class PrintStyle {
	
	// 年月日
	private Style DateStyle;
	// 時刻
	private Style StampStyle;
	// 出退勤区分
	private Style ClaStyle;
	// 打刻手段
	private Style MeanStyle;
	// 認証方法
	private Style MethodStyle;
	// 設置場所
	private Style InsStyle;
	// 位置情報
	private Style LocStyle;
	// 応援カード
	private Style CardStyle;
	// 就業時間帯
	private Style WHourStyle;
	// 残業時間
	private Style OverStyle;
	// 深夜時間
	private Style NightStyle;

}
