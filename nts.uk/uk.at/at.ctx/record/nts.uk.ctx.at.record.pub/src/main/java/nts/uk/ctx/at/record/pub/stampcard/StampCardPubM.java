package nts.uk.ctx.at.record.pub.stampcard;

import java.util.List;

public interface StampCardPubM {

	List<StampCardExport> findByEmployees(String contractCode, List<String> empIds);
}
