package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLangRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimesLang;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalTimesLangPK;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaTotalTimesLangRepository extends JpaRepository implements TotalTimesLangRepository {
	
	private static final String SEL_BY_CID_LANGID = "SELECT a FROM KshstTotalTimesLang a "
			+ "WHERE a.kshstTotalTimesLangPK.companyId = :companyId "
			+ "AND a.kshstTotalTimesLangPK.languageId =:languageId";

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
		KshstTotalTimesLangPK pk = new KshstTotalTimesLangPK(totalTimesLang.getCompanyId(),
				totalTimesLang.getTotalCountNo(), totalTimesLang.getLangId());
		KshstTotalTimesLang entity = this.queryProxy().find(pk, KshstTotalTimesLang.class).get();
		entity.kshstTotalTimesLangPK = pk;
		entity.totalTimesName = totalTimesLang.getTotalTimesNameEng().v();

		this.commandProxy().update(entity);
		
	}
	
	/**
	 * to Entity KshstTotalTimesLang
	 * @param domain
	 * @return
	 */
	private KshstTotalTimesLang toEntity(TotalTimesLang domain) {
		val entity = new KshstTotalTimesLang();

		entity.kshstTotalTimesLangPK = new KshstTotalTimesLangPK(domain.getCompanyId(),
				domain.getTotalCountNo(), domain.getLangId());
		entity.totalTimesName = domain.getTotalTimesNameEng().v();

		return entity;
	}

	/**
	 * find all Total Times Lang
	 */
	@Override
	public List<TotalTimesLang> findAll(String companyId, String langId) {
		return this.queryProxy().query(SEL_BY_CID_LANGID, KshstTotalTimesLang.class)
				.setParameter("companyId", companyId).setParameter("languageId", langId).getList(x -> toDomain(x));
	}

	/**
	 * to Domain TotalTimesLang
	 * @param entity
	 * @return
	 */
	private TotalTimesLang toDomain(KshstTotalTimesLang entity) {
		val domain = TotalTimesLang.createFromJavaType(entity.kshstTotalTimesLangPK.companyId,
				entity.kshstTotalTimesLangPK.totalTimesNo, entity.kshstTotalTimesLangPK.languageId, entity.totalTimesName);
		return domain;
	}
	
	/**
	 * findById Total Times Lang
	 */
	@Override
	public Optional<TotalTimesLang> findById(String companyId, Integer totalTimesNo, String langId) {
		KshstTotalTimesLangPK pk = new KshstTotalTimesLangPK(companyId, totalTimesNo, langId);
		return this.queryProxy().find(pk, KshstTotalTimesLang.class).map(x -> toDomain(x));
	}
}
