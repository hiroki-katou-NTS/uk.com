package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class TimePlaceDto {
	/**
	 * 場所コード
	 */
	private String opWorkLocationCD;
	
	/**
	 * 外出区分
	 */
	private Integer opGoOutReasonAtr;
	
	/**
	 * 打刻枠No
	 */
	private int frameNo;
	
	/**
	 * 時刻終了
	 */
	private Integer opEndTime;
	
	/**
	 * 時刻開始
	 */
	private Integer opStartTime;
	
	public static TimePlaceDto fromDomain(TimePlaceOutput timePlaceOutput) {
		return new TimePlaceDto(
				timePlaceOutput.getOpWorkLocationCD().map(x -> x.v()).orElse(null), 
				timePlaceOutput.getOpGoOutReasonAtr().map(x -> x.value).orElse(null), 
				timePlaceOutput.getFrameNo().v(), 
				timePlaceOutput.getOpEndTime().map(x -> x.v()).orElse(null), 
				timePlaceOutput.getOpStartTime().map(x -> x.v()).orElse(null));
	} 
}
