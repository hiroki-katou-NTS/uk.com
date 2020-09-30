package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtDataStorageSelectionCategory;

@Stateless
public class JpaDataStorageSelectionCategoryRepository extends JpaRepository implements DataStorageSelectionCategoryRepository {

	private static final String SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES = "SELECT t FROM SspmtDataStorageSelectionCategory "
			+ "WHERE t.pk.patternCd = :patternCd AND t.pk.patternAtr = :patternAtr AND t.systemTypes IN :systemTypes";
	
	@Override
	public List<DataStorageSelectionCategory> findByPatternCdAndPatternAtrAndSystemTypes(String patternCd,
			int patternAtr, List<Integer> systemTypes) {
		return this.queryProxy().query(SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES, SspmtDataStorageSelectionCategory.class)
				.getList(DataStorageSelectionCategory::createFromMemento);
	}
	
	@Override
	public void add(DataStorageSelectionCategory domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	private SspmtDataStorageSelectionCategory toEntity(DataStorageSelectionCategory domain) {
		SspmtDataStorageSelectionCategory entity = new SspmtDataStorageSelectionCategory();
		domain.setMemento(entity);
		return entity;
	}
}
