package nts.uk.ctx.at.function.dom.adapter.stampcard;

import java.util.List;

public interface StampCardAdapterM {

	List<StampCardImport> findByEmployees(String contractCode, List<String> empIds);
}
