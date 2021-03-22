package nts.uk.cnv.dom.td.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
@AllArgsConstructor
public class EventDetail implements Comparable<EventDetail> {
	private String name;
	private GeneralDateTime datetime;
	private String userName;
	private List<String> alterationIds;
	
	public EventDetail() {
	}
	
	@Override
	public int compareTo(EventDetail e) {
		return this.datetime.compareTo(e.datetime);
	}
}