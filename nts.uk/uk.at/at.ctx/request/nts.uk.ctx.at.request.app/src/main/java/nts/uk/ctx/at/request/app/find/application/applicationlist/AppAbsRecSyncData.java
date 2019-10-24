package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class AppAbsRecSyncData {

	//0 - abs
	//1 - rec
	private int typeApp;
	private String appMainID;
	private String appSubID;
	private String appDateSub;
}
