package nts.uk.ctx.sys.auth.pub.user;

import java.util.List;

public interface GetUserByEmpPublisher {
		List<UserAuthDto> getByListEmp(List<String> listEmpID);
}
