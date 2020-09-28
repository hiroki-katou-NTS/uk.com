package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 社員選択
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	private List<String> targetPerson = new ArrayList<String>();
}
