package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

public interface SysEmployeeStorageAdapter {
	
	List<TargetEmployees> getListEmployeeByCompanyId(String cid);
	List<TargetEmployees> getByListSid(List<String> sIds);
}
