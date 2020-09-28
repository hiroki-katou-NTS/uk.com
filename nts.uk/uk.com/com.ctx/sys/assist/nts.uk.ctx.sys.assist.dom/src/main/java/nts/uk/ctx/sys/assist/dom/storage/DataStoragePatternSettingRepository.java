package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;
import java.util.Optional;

public interface DataStoragePatternSettingRepository {
	List<DataStoragePatternSetting> findByContractCdAndPatternCdAndPatternAtr(String contractCd, String[] patternCd, int[] patternAtr);
	List<DataStoragePatternSetting> findByContractCd(String contractCd);
	Optional<DataStoragePatternSetting> findByContractCdAndPatternCd(String contractCd, String patternCd);
}
