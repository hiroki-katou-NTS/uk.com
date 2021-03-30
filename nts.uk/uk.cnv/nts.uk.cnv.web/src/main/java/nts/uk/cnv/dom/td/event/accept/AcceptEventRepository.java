package nts.uk.cnv.dom.td.event.accept;

import java.util.List;
import java.util.Optional;

public interface AcceptEventRepository {
	Optional<String> getNewestAcceptId();

	void regist(AcceptEvent orderEvent);

	List<AcceptEvent> getByAlter(List<String> alters);
}
