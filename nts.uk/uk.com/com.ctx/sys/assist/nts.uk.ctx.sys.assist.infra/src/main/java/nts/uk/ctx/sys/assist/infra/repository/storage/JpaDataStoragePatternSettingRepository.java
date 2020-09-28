package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtDataStoragePatternSetting;

@Stateless
public class JpaDataStoragePatternSettingRepository extends JpaRepository implements DataStoragePatternSettingRepository {
	private static final String SELECT_DS_PATTERN_SETTING_BY_PK = "SELECT t from SspmtDataStoragePatternSetting t "
			+ "WHERE t.contractCode = :contractCd AND t.patternCode IN :patternCd AND t.patternClassification IN :patternAtr";
	
	private static final String SELECT_PATTERN_BY_CONTRACT_CD = "SELECT t FROM SspmtDataStoragePatternSetting t "
			+ "WHERE t.contractCode = :contractCd";

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public List<DataStoragePatternSetting> findByContractCdAndPatternCdAndPatternAtr(String contractCd,
			String[] patternCd, int[] patternAtr) {
		return this.queryProxy().query(SELECT_DS_PATTERN_SETTING_BY_PK, SspmtDataStoragePatternSetting.class)
				.setParameter("contractCode", contractCd)
				.setParameter("patternCode", patternCd)
				.setParameter("patternAtr", patternAtr)
				.getList(DataStoragePatternSetting::createFromMemento);
	}

	@Override
	public List<DataStoragePatternSetting> findByContractCd(String contractCd) {
		return this.queryProxy().query(SELECT_PATTERN_BY_CONTRACT_CD, SspmtDataStoragePatternSetting.class)
				.setParameter("contractCd", contractCd)
				.getList(DataStoragePatternSetting::createFromMemento);
	}

	@Override
	public Optional<DataStoragePatternSetting> findByContractCdAndPatternCd(String contractCd, String patternCd) {
		// TODO Auto-generated method stub
		return null;
	}
}
