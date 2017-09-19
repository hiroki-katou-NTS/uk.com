package nts.uk.ctx.bs.employee.dom.deleteEmpManagement;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteEmpManagement extends AggregateRoot {

	private boolean deleted;

	private String sid;

	private GeneralDate dateTime;

	private ReasonRemoveEmp reasonRemoveEmp;

	public static DeleteEmpManagement creatFromJavaType(String sid, boolean deleted, String dateTime,
			String reasonRemoveEmp) {
		return new DeleteEmpManagement(
				deleted, 
				sid, 
				GeneralDate.legacyDate(new Date(dateTime)),
				new ReasonRemoveEmp(reasonRemoveEmp));

	}

}
