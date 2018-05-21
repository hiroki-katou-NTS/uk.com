package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteBusiFormatBySheetCmd {
	private String  businessTypeCode;
	
	private BigDecimal sheetNo;

	public DeleteBusiFormatBySheetCmd(String businessTypeCode, BigDecimal sheetNo) {
		super();
		this.businessTypeCode = businessTypeCode;
		this.sheetNo = sheetNo;
	}
	
	
}
