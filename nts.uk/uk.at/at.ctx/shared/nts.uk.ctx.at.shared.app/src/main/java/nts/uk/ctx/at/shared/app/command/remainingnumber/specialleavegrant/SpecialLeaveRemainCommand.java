package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Data
public class SpecialLeaveRemainCommand {

	

	private String specialid;
	private String cid;
	private String sid;
	private int specialLeaCode;
	private String grantDate;
	private String deadlineDate;
	private int expStatus;
	private int registerType;
	private BigDecimal numberDayGrant;
	private Integer timeGrant;
	private BigDecimal numberDayUse;
	private Integer timeUse;
	private BigDecimal numberDaysOver;
	private Integer timeOver;
	private BigDecimal numberDayRemain;
	private Integer timeRemain;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

}
