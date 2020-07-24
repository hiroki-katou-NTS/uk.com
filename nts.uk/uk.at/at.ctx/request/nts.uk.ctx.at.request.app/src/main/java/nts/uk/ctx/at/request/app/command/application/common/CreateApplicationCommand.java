package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	 * 申請種類
	 */
	private Integer appType;
	
	/**
	 * 申請日
	 */
	private String appDate;
	
	/**
	 * 定型理由
	 */
	private Integer opAppStandardReasonCD;
	
	/**
	 * 申請理由
	 */
	private String opAppReason;
	
}
