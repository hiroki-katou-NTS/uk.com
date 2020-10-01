package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtDataStorageSelectionCategory;

@Stateless
public class JpaDataStorageSelectionCategoryRepository extends JpaRepository
		implements DataStorageSelectionCategoryRepository {

	private static final String SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES = "SELECT t FROM SspmtDataStorageSelectionCategory t "
			+ "WHERE t.pk.patternCode = :patternCd AND t.pk.patternClassification = :patternAtr AND t.systemType IN :systemTypes";
	
	private static final String DELETE_BY_PK = "DELETE FROM SspmtDataStorageSelectionCategory t "
			+ "WHERE t.pk.patternClassification = :patternAtr AND t.pk.patternCode = :patternCd "
			+ "AND t.pk.contractCode = :contractCd";

	@Override
	public List<DataStorageSelectionCategory> findByPatternCdAndPatternAtrAndSystemTypes(String patternCd,
			int patternAtr, List<Integer> systemTypes) {
		return this.queryProxy()
				.query(SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES, SspmtDataStorageSelectionCategory.class)
				.setParameter("patternCd", patternCd).setParameter("patternAtr", patternAtr)
				.setParameter("systemTypes", systemTypes).getList(DataStorageSelectionCategory::createFromMemento);
	}

	@Override
	public void delete(String contractCd, String patternCd, int patternAtr) {
		this.getEntityManager().createQuery(DELETE_BY_PK, SspmtDataStorageSelectionCategory.class)
							   .setParameter("contractCd", contractCd)
							   .setParameter("patternCd", patternCd)
							   .setParameter("patternAtr", patternAtr)
							   .executeUpdate();
	}
}
