package nts.uk.ctx.pereg.dom.person.additemdata.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpInfoCtgData extends AggregateRoot {

	private String recordId;

	private String personInfoCtgId;

	private String employeeId;

}
