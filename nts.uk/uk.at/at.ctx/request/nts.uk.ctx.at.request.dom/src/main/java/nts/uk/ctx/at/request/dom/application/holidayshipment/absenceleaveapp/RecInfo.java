package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 振出情報
public class RecInfo {
	// 社員ID
	private String employeeID;
	// 振出データID
	private String drawingDataID;
	// 振出日
	private GeneralDate issueDate;
	// 状態
	private int state;
	// 法定内外区分
	private int statutoryType;
	// 使用期限日
	private GeneralDate expDate;
	// 発生日数
	private int occurredDays;
	// 未使用日数
	private int unusedDays;
	// 消滅済
	private int disappeared;

}
