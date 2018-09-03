package nts.uk.ctx.at.request.ws.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class WorkchangeSetParam {
	private List<String> sIDs;
	private String appDate;
}
