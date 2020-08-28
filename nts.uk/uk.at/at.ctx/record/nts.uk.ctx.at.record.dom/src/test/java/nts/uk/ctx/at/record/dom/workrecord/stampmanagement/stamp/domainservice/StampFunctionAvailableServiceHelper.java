package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

public class StampFunctionAvailableServiceHelper {

	public static List<StampCard> getStampCards(){
		List<StampCard> list = new ArrayList<>();
		
		list.add(new StampCard("DUMMY", "DUMMY", "DUMMY"));
		list.add(new StampCard("DUMMY", "DUMMY", "DUMMY"));
		list.add(new StampCard("DUMMY", "DUMMY", "DUMMY"));
		list.add(new StampCard("DUMMY", "DUMMY", "DUMMY"));
		
		return list;
	}
	
}
