package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
/**
 * Class ExtractionAverageMonthCommand
 * 複数月平均基準月
 * @author phongtq
 *
 */
@Data
public class ExtractionAverageMonthCommand {
	
	/** 抽出期間ID */
	private String extractionId;
	
	/** 抽出する範囲  */
	private int extractionRange;

	/** 基準月指定 */
	private int strMonth;
	
	public AverageMonth toDomain() {
		// check extractionId
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		return new AverageMonth(extractionId, extractionRange, strMonth);
	}
}

