package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnSetting {
	private String columnKey;
	private Boolean allowSummaries;
	/* 時間 - thoi gian hh:mm 5,  回数: so lan 2,  金額 : so tien 3, 日数: so ngay -*/
	private int typeFormat;
	
	public ColumnSetting(String columnKey, Boolean allowSummaries){
		this.columnKey = columnKey;
		this.allowSummaries = allowSummaries;
	}
}
