package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	
	public TimePlaceOutput toDomain() {
		return new TimePlaceOutput(
				Strings.isBlank(opWorkLocationCD) ? Optional.empty() : Optional.of(new WorkLocationCD(opWorkLocationCD)), 
				opGoOutReasonAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opGoOutReasonAtr, GoingOutReason.class)), 
				new StampFrameNo(frameNo), 
				opEndTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(opEndTime)), 
				opStartTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(opStartTime)));
	}
}
