package nts.uk.file.at.app.export.ot.autocalsetting.wkpjob;

import java.util.List;

/**
 * The Interface WkpJobAutoCalSettingRepository.
 */
public interface WkpJobAutoCalSettingRepository {

	List<Object[]> getWkpJobSettingToExport(String cid, String baseDate);

}
