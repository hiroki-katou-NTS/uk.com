package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import nts.arc.time.GeneralDateTime;

public interface RegulationInfoEmployeeAdapter {
	List<RegulationInfoEmployeeAdapterDto> find(RegulationInfoEmployeeAdapterImport r);

	List<String> sortEmployee(String comId, List<String> sIds, Integer systemType, Integer orderNo,
				Integer nameType, GeneralDateTime referenceDate);
	
	public List<String> sortEmployee(String comId, List<String> sIds, List<SortingConditionOrderImport> orders,
			GeneralDateTime referenceDate);
}
