package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertShiftPalletCombinaCommand {
	public int pairNo;
	public String workTypeCode;
	public String workTimeCode;

	public InsertShiftPalletCombinaCommand(int pairNo, String workTypeCode, String workTimeCode) {
		super();
		this.pairNo = pairNo;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}
}
