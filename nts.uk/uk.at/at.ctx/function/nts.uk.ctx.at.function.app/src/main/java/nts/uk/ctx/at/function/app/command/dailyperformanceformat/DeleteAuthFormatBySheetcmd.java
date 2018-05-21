package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteAuthFormatBySheetcmd {

	private String dailyPerformanceFormatCode;
	
	private BigDecimal sheetNo;
	
	public DeleteAuthFormatBySheetcmd( String dailyPerformanceFormatCode, BigDecimal sheetNo) {
		super();
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.sheetNo = sheetNo;
	}
}
