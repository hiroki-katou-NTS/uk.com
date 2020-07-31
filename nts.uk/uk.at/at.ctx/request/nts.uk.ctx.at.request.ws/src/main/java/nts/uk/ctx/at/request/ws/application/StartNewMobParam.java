package nts.uk.ctx.at.request.ws.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class StartNewMobParam {
	
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 休暇申請の種類
	 */
	private Integer holidayAppType;
	
	/**
	 * 申請対象日リスト
	 */
	private List<String> dateLst;
	
	/**
	 * 残業区分
	 */
	private Integer overtimeAppAtr;
}
