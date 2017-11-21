package nts.uk.screen.com.app.command.systemresource;

import java.util.List;

import lombok.Data;
import nts.uk.screen.com.app.systemresource.dto.SystemResoureceDto;

@Data
public class SystemResourceSaveCommand {
	
	private List<SystemResoureceDto> listData;
	
	/**
	 * Instantiates a new mail server save command.
	 */
	public SystemResourceSaveCommand(){
		super();
	}
}
