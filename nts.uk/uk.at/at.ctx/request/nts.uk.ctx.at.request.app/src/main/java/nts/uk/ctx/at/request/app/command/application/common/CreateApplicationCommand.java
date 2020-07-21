package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationCommand {
	
	private Integer version;
	
	/**
	 * ID
	 */
	private String appID;
	
	/**
	 * 事前事後区分
	 */
	private Integer prePostAtr;
	
	/**
	 * 申請者
	 */
	private List<String> employeeIDLst;
	
	/**
	 * 
	 */
	private Integer appType;
	
	/**
	 * 
	 */
	private GeneralDate appDate;
	
	/**
	 * 
	 */
	private Integer opAppStandardReasonCD;
	
	/**
	 * 
	 */
	private String opAppReason;
	
}
