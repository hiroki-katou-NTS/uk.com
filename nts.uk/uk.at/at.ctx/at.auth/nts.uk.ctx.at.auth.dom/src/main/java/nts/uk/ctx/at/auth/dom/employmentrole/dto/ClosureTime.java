/**
 * 
 */
package nts.uk.ctx.at.auth.dom.employmentrole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author laitv 
 * 締め期間 (closure time)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClosureTime {
	GeneralDate start; // 開始年月日
	GeneralDate end;   // 終了年月日
}
