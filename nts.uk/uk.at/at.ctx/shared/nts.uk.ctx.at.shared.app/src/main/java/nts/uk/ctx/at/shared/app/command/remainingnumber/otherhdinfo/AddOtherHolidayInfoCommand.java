package nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddOtherHolidayInfoCommand {

	@PeregEmployeeId
	private String employeeId;
	
	// 代休残数
	@PeregItem("IS00366")
	private BigDecimal remainNumber;

	// 振休残数
	@PeregItem("IS00368")
	private BigDecimal remainsLeft;

	// 公休残数
	@PeregItem("IS00369")
	private BigDecimal pubHdremainNumber;

	// 60H超休管理
	@PeregItem("IS00370")
	private BigDecimal useAtr;

	// 発生単位
	@PeregItem("IS00371")
	private BigDecimal occurrenceUnit;

	// 精算方法
	@PeregItem("IS00372")
	private BigDecimal paymentMethod;

}
