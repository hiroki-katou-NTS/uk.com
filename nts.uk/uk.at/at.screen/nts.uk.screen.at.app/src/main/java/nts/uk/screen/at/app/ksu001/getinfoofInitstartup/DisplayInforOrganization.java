/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import lombok.Value;

/**
 * @author laitv
 * 組織の表示情報
 */

@Value
public class DisplayInforOrganization {
	
	public String designation ; // 呼称   Aa1_2_2
	public String code ;        // コード
	public String name ;       // 名称 
	public String targetOrganizationName;   //  表示名  A2_2
	public String genericTerm ;    // 総称

}
