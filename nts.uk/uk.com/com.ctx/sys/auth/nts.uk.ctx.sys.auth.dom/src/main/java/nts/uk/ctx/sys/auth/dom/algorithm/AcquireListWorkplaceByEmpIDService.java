package nts.uk.ctx.sys.auth.dom.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AcquireListWorkplaceByEmpIDService {
  List<String> getListWorkPlaceID(String employeeID , int empRange , GeneralDate referenceDate);
}
