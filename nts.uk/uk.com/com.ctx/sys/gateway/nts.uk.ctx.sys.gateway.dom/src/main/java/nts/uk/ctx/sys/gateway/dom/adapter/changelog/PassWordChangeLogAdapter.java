package nts.uk.ctx.sys.gateway.dom.adapter.changelog;

import java.util.List;

public interface PassWordChangeLogAdapter {

	List<PassWordChangeLogImport> getListPwChangeLog(String userId);
}
