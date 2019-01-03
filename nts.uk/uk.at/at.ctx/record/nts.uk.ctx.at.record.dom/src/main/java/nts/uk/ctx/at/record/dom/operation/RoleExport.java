package nts.uk.ctx.at.record.dom.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RoleExport {
	private String codeRole;
	private String nameRole;
	private String displayName;
	private String description;
	private int availability;
}
