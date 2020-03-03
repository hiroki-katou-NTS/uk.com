package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertShiftPalletCombinaCommand {
	public int pairNo;
	public String shiftCode;

	public InsertShiftPalletCombinaCommand(int pairNo, String shiftCode) {
		super();
		this.pairNo = pairNo;
		this.shiftCode = shiftCode;
	}
}
