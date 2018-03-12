package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AppPrePostGroup {
	//事前
	private String preAppID;
	//事後
	private String postAppID;
	//実績
	@Setter
	private List<OverTimeFrame> time;
}
