package nts.uk.ctx.exio.dom.exi.adapter.role;

import lombok.Value;

/**
 * 
 * @author 
 * ログイン者が担当者か判断する
 */

@Value
public class OperableSystemImport {
	
	//オフィスヘルパー
	private boolean officeHelper;
	//人事システム
	private boolean humanResource;
	//勤怠システム
	private boolean attendance;
	//給与システム
	private boolean salary;
	
}
