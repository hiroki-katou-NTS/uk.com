package nts.uk.ctx.exio.app.find.exi.condset;

import lombok.Value;

/**
 * 
 * @author 
 * ログイン者が担当者か判断する
 */

@Value
public class LoginUserInCharge {

	//オフィスヘルパー担当者か
	//人事担当者か
	//個人情報担当者か
	//就業担当者か
	//給与担当者か
	// Official helper personnel?
	private boolean officeHelper;
	// Personnel representative
	private boolean humanResource;
	// Person in charge of personal information
	private boolean personalInfo;
	// Employment personnel
	private boolean employment;
	// Will it be a salary professional?
	private boolean salary;
}
