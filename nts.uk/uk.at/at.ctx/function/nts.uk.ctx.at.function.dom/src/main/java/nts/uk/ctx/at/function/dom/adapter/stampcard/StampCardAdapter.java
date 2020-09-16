package nts.uk.ctx.at.function.dom.adapter.stampcard;

import java.util.List;

public interface StampCardAdapter {

	List<StampCardImport> findByEmployees(String contractCode, List<String> empIds);
}
