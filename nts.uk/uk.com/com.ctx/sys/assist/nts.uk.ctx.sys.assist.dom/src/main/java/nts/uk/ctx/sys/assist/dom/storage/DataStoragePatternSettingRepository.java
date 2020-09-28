package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

public interface DataStoragePatternSettingRepository {
	List<DataStoragePatternSetting> findByContractCdAndPatternCdAndPatternAtr(String contractCd, String[] patternCd, int[] patternAtr);
	List<DataStoragePatternSetting> findByContractCd(String contractCd);
}
