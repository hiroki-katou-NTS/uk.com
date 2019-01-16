package nts.uk.file.at.app.export.ot.autocalsetting.job;


import java.util.List;

/**
 * The Interface JobAutoCalSettingRepository.
 */
public interface JobAutoCalSettingRepository {

	List<Object[]> getPositionSettingToExport(String cid, String baseDate);

}
