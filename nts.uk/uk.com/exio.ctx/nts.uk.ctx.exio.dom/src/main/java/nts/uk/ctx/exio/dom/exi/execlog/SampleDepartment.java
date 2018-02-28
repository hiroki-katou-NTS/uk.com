package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SampleDepartment {
	
	private String name;
	private List<SampleEmployee> employees;
}
