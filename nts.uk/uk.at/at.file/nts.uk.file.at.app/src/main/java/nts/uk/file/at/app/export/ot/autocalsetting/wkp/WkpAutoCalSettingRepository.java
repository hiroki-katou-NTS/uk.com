package nts.uk.file.at.app.export.ot.autocalsetting.wkp;

import java.util.List;

/**
 * The Interface WkpAutoCalSettingRepository.
 */
public interface WkpAutoCalSettingRepository {

	List<Object[]> getWorkPlaceSettingToExport(String cid, String baseDate);

}
