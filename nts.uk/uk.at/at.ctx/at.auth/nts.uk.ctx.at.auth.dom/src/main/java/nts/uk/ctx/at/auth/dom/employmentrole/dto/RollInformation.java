/**
 * 
 */
package nts.uk.ctx.at.auth.dom.employmentrole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 * ロール情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RollInformation {
	Boolean roleCharge; // 担当ロールか
	String roleId;      // ロールID
}
