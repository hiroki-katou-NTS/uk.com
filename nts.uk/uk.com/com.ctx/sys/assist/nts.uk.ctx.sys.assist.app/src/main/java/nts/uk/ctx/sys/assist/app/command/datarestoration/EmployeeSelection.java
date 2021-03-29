package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.datarestoration.TargetItemDto;

/**
 * 社員選択
 */
@Data
public class EmployeeSelection {
	/**
	 * 年次To
	 */
	private String annualTo;
	
	/**
	 * 年次From
	 */
	private String annualFrom;
	
	/**
	 * 月次To
	 */
	private String monthlyTo;
	
	/**
	 * 月次From
	 */
	private String monthlyFrom;
	
	/**
	 * 日次To
	 */
	private String dailyTo;
	
	/**
	 * 日次From
	 */
	private String dailyFrom;
	
	/**
	 * 対象者
	 */
	private List<TargetItemDto> targets;
}