package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
public interface DataDeletionPatternSettingRepository {
	List<DataDeletionPatternSetting> findByContractCd(String contractCd);
	Optional<DataDeletionPatternSetting> findByContractCdAndPatternCd(String contractCd, String patternCd);
	Optional<DataDeletionPatternSetting> findByPk(String contractCd, String patternCd, int patternAtr);
	void add(DataDeletionPatternSetting domain);
	void update(DataDeletionPatternSetting domain);
	void delete(String contractCd, String patternCd, int patternAtr);
}
