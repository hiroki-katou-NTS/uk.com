package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLangRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalTimesLang;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalTimesLangPK;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaTotalTimesLangRepository extends JpaRepository implements TotalTimesLangRepository {
	
	private static final String SEL_BY_CID_LANGID = "SELECT a FROM KshmtTotalTimesLang a "
			+ "WHERE a.kshmtTotalTimesLangPK.companyId = :companyId "
			+ "AND a.kshmtTotalTimesLangPK.languageId =:languageId";

	/**
	 * add Total Times Lang
	 */
	@Override
	public void add(TotalTimesLang totalTimesLang) {
		this.commandProxy().insert(toEntity(totalTimesLang));
	}

	/**
	 * update Total Times Lang
	 */
	@Override
	public void update(TotalTimesLang totalTimesLang) {
		KshmtTotalTimesLangPK pk = new KshmtTotalTimesLangPK(totalTimesLang.getCompanyId(),
				totalTimesLang.getTotalCountNo(), totalTimesLang.getLangId());
		KshmtTotalTimesLang entity = this.queryProxy().find(pk, KshmtTotalTimesLang.class).get();
		entity.kshmtTotalTimesLangPK = pk;
		entity.totalTimesName = totalTimesLang.getTotalTimesNameEng().v();

		this.commandProxy().update(entity);
		
	}
	
	/**
	 * to Entity KshmtTotalTimesLang
	 * @param domain
	 * @return
	 */
	private KshmtTotalTimesLang toEntity(TotalTimesLang domain) {
		val entity = new KshmtTotalTimesLang();

		entity.kshmtTotalTimesLangPK = new KshmtTotalTimesLangPK(domain.getCompanyId(),
				domain.getTotalCountNo(), domain.getLangId());
		entity.totalTimesName = domain.getTotalTimesNameEng().v();

		return entity;
	}

	/**
	 * find all Total Times Lang
	 */
	@Override
	public List<TotalTimesLang> findAll(String companyId, String langId) {
		return this.queryProxy().query(SEL_BY_CID_LANGID, KshmtTotalTimesLang.class)
				.setParameter("companyId", companyId).setParameter("languageId", langId).getList(x -> toDomain(x));
	}

	/**
	 * to Domain TotalTimesLang
	 * @param entity
	 * @return
	 */
	private TotalTimesLang toDomain(KshmtTotalTimesLang entity) {
		val domain = TotalTimesLang.createFromJavaType(entity.kshmtTotalTimesLangPK.companyId,
				entity.kshmtTotalTimesLangPK.totalTimesNo, entity.kshmtTotalTimesLangPK.languageId, entity.totalTimesName);
		return domain;
	}
	
	/**
	 * findById Total Times Lang
	 */
	@Override
	public Optional<TotalTimesLang> findById(String companyId, Integer totalTimesNo, String langId) {
		KshmtTotalTimesLangPK pk = new KshmtTotalTimesLangPK(companyId, totalTimesNo, langId);
		return this.queryProxy().find(pk, KshmtTotalTimesLang.class).map(x -> toDomain(x));
	}
}
