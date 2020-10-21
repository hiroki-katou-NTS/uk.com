package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategory;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspmtDataDeletionSelectionCategory;

@Stateless
public class JpaDataDeletionSelectionCategoryRepository extends JpaRepository implements DataDeletionSelectionCategoryRepository {

	private static final String SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES = "SELECT t from SspmtDataDeletionSelectionCategory t "
			+ "WHERE t.pk.patternCode = :patternCd AND t.pk.patternClassification = :patternAtr "
			+ "AND t.pk.systemType IN :systemTypes";
	
	@Override
	public List<DataDeletionSelectionCategory> findByPatternCdAndPatternAtrAndSystemTypes(String patternCd,
			int patternAtr, List<Integer> systemTypes) {
		return this.queryProxy().query(SELECT_BY_PATTERN_CD_AND_PATTERN_ATR_AND_SYSTEM_TYPES, SspmtDataDeletionSelectionCategory.class)
				.setParameter("patternCd", patternCd)
				.setParameter("patternAtr", patternAtr)
				.setParameter("systemTypes", systemTypes)
				.getList(DataDeletionSelectionCategory::createFromMemento);
	}

}
