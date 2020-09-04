package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppListApproveResult {
	
	private Map<String, String> successMap;
	
	private Map<String, String> failMap;
}
