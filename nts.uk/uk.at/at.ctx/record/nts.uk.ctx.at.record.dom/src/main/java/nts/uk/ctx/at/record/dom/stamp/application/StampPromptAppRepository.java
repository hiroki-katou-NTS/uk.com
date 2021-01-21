package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

/**
 * 
 * 打刻の申請促す設定Repository
 */
public interface StampPromptAppRepository {

	void insert(StampPromptApplication application);

	void update(StampPromptApplication application);

	Optional<StampResultDisplay> getStampSet(String companyId);

	List<StampRecordDis> getAllStampSetPage(String companyId);

	Optional<StampPromptApplication> getStampPromptApp(String companyId);
}
