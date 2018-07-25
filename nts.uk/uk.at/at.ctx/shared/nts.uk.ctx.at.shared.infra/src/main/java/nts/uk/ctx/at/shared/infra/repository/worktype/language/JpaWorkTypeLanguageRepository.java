package nts.uk.ctx.at.shared.infra.repository.worktype.language;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguageRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguage;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguagePK;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkTypeLanguageRepository extends JpaRepository implements WorkTypeLanguageRepository {

	private static final String SEL_BY_CID_LANGID = "SELECT a FROM KshmtWorkTypeLanguage a "
			+ "WHERE a.kshmtWorkTypeLanguagePK.companyId = :companyId "
			+ "AND a.kshmtWorkTypeLanguagePK.langId =:langId";

	private WorkTypeLanguage toDomain(KshmtWorkTypeLanguage entity) {
		val domain = WorkTypeLanguage.createFromJavaType(entity.kshmtWorkTypeLanguagePK.companyId,
				entity.kshmtWorkTypeLanguagePK.workTypeCode, entity.kshmtWorkTypeLanguagePK.langId, entity.name,
				entity.abname);
		return domain;
	}

	private KshmtWorkTypeLanguage toEntity(WorkTypeLanguage domain) {
		val entity = new KshmtWorkTypeLanguage();

		entity.kshmtWorkTypeLanguagePK = new KshmtWorkTypeLanguagePK(domain.getCompanyId(),
				domain.getWorkTypeCode().v(), domain.getLangId());
		entity.name = domain.getName().v();
		entity.abname = domain.getAbbreviationName().v();

		return entity;
	}

	@Override
	public List<WorkTypeLanguage> findByCIdAndLangId(String companyId, String langId) {
		return this.queryProxy().query(SEL_BY_CID_LANGID, KshmtWorkTypeLanguage.class)
				.setParameter("companyId", companyId).setParameter("langId", langId).getList(x -> toDomain(x));
	}

	@Override
	public void add(WorkTypeLanguage workTypeLanguage) {
		this.commandProxy().insert(toEntity(workTypeLanguage));
	}

	@Override
	public void update(WorkTypeLanguage workTypeLanguage) {
		KshmtWorkTypeLanguagePK pk = new KshmtWorkTypeLanguagePK(workTypeLanguage.getCompanyId(),
				workTypeLanguage.getWorkTypeCode().v(), workTypeLanguage.getLangId());
		KshmtWorkTypeLanguage entity = this.queryProxy().find(pk, KshmtWorkTypeLanguage.class).get();
		entity.kshmtWorkTypeLanguagePK = pk;
		entity.abname = workTypeLanguage.getAbbreviationName().v();
		entity.name = workTypeLanguage.getName().v();

		this.commandProxy().update(entity);
	}

	@Override
	public Optional<WorkTypeLanguage> findById(String companyId, String workTypeCode, String langId) {
		KshmtWorkTypeLanguagePK pk = new KshmtWorkTypeLanguagePK(companyId, workTypeCode, langId);
		return this.queryProxy().find(pk, KshmtWorkTypeLanguage.class).map(x -> toDomain(x));
	}
}
