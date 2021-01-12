package nts.uk.ctx.at.request.infra.repository.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// save info that are name, time , frame
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OverTimeObject {
	
	private String name;
	
	private Integer time;
	
	private Integer frame;
}
