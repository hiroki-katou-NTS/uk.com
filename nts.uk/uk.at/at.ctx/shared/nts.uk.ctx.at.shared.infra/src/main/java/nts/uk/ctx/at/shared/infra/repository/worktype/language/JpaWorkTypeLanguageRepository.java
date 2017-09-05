package nts.uk.ctx.at.shared.infra.repository.worktype.language;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguageRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguage;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkTypeLanguageRepository extends JpaRepository implements WorkTypeLanguageRepository {

	private static String SEL_BY_CID_LANGID = "SELECT a FROM KshmtWorkTypeLanguage a "
			+ "WHERE a.kmnmtWorkTypeLanguagePK.companyId = :companyId "
			+ "AND a.kmnmtWorkTypeLanguagePK.langId =:langId";

	private static WorkTypeLanguage toDomain(KshmtWorkTypeLanguage entity) {
		val domain = WorkTypeLanguage.createFromJavaType(entity.kmnmtWorkTypeLanguagePK.companyId,
				entity.kmnmtWorkTypeLanguagePK.workTypeCode, entity.kmnmtWorkTypeLanguagePK.langId, entity.name,
				entity.abname);
		return domain;
	}
	
//	private static KshmtWorkTypeLanguage toEntity(WorkTypeLanguage domain) {
//		val entity = new KshmtWorkTypeLanguage();
//
//		entity.kmnmtWorkTypeLanguagePK = new KmnmtWorkTypeLanguagePK();
//
//		return entity;
//	}

	@Override
	public List<WorkTypeLanguage> findByCIdAndLangId(String companyId, String langId) {
		return this.queryProxy().query(SEL_BY_CID_LANGID, KshmtWorkTypeLanguage.class)
				.setParameter("companyId", companyId).setParameter("langId", langId).getList(x -> toDomain(x));
	}

	@Override
	public void insert(WorkTypeLanguage workTypeLanguage) {
//		this.commandProxy().insert(entity);
	}
}
