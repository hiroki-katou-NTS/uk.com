package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.Export.[No.50]ログイン者が担当者か判断する.パラメータ.ログイン者が担当者か判断する
 */
public class LoginPersonInCharge {
	/**
	 * オフィスヘルパー担当者か
	 */
	private boolean officeHelper = false;
	
	/**
	 * 人事担当者か
	 */
	private boolean personnel = false;
	
	/**
	 * 個人情報担当者か
	 */
	private boolean employeeInfo = false;
	
	/**
	 * 就業担当者か
	 */
	private boolean attendance = false;
	
	/**
	 * 給与担当者か
	 */
	private boolean payroll = false;
}
