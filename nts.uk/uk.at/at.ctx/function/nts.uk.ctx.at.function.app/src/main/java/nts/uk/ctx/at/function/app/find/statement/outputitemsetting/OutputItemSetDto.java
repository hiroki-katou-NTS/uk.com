package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import lombok.Data;

/**
 * The Class StampingOutputItemSetDto.
 */
@Data
public class OutputItemSetDto {
	
	// コード
	private String stampOutputSetCode;
	
	// 名称
	private String stampOutputSetName;
	
	// 打刻方法の出力
	private boolean outputEmbossMethod;
	
	// 就業時間帯の出力
	private boolean outputWorkHours; 
	
	// 設定場所の出力
	private boolean outputSetLocation;
	
	// 位置情報の出力
	private boolean outputPosInfor;
	
	// 残業時間の出力
	private boolean outputOT;
	
	// 深夜時間の出力
	private boolean outputNightTime;
	
	// 応援カードの出力
	private boolean outputSupportCard;
}
