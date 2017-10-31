package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category;

import java.util.Optional;

public interface EmInfoCtgDataRepository {
	public Optional<EmpInfoCtgData> getEmpInfoCtgDataBySIdAndCtgId(String sId, String ctgId);
}
