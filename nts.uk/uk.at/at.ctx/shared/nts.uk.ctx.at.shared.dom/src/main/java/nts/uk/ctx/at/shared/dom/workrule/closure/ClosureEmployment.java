package nts.uk.ctx.at.shared.dom.workrule.closure;
/**
 * 
 * 雇用に紐づく就業締め
 *
 */
/**
 * 
 * @author Doan Duy Hung
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureEmployment extends AggregateRoot {
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** Employemeny code */
	// 雇用コード
	private String employmentCD;
	
	/** The closure id. */
	// 締めＩＤ
	private Integer closureId;
}
