package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class DataExtractionService {
	public ServerPrepareMng extractData(ServerPrepareMng serverPrepareMng){
		//TODO
		//Waiting Kiban
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EXTRACTING);
		
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE);
		return serverPrepareMng;
	}
}
