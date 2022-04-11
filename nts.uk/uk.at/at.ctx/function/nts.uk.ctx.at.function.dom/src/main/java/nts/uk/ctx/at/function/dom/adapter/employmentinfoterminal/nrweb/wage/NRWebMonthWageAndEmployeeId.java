package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @author sakuratani
*
*			月間賃金と社員ID
*         
*/
@Getter
@AllArgsConstructor
public class NRWebMonthWageAndEmployeeId {

	//社員ID
	private String employeedId;

	//月間賃金
	private NRWebMonthWage monthWage;

	//年度
	private Optional<Integer> year;

}
