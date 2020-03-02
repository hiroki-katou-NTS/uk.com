package nts.uk.ctx.at.shared.dom.person;

import java.util.List;

public interface PersonAdaptor {
	public List<PersonImport> findByPids(List<String> personIds);
}
