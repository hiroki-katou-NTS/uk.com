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
 * 【Output】 ・List＜社員ID、締めID＞ ----------- 
 * 【Output】・List＜employeeID、closureID＞
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClosureInformation {
	String employeeID; // 社員ID
	Integer closureID; // 締めID
}
