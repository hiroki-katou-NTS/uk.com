package nts.uk.ctx.pr.core.dom.adapter.person;

import java.util.List;

public interface PersonExportAdapter {
    List<PersonExport> findByPids(List<String> personIds);
}
