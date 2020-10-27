package nts.uk.ctx.at.schedule.infra.repository.budget.premium.language;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguage;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguageRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.language.KscmtPremiumItemLanguage;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.language.KscmtPremiumItemLanguagePK;

@Stateless
public class JpaPremiumItemLanguageRepository extends JpaRepository implements PremiumItemLanguageRepository {

	private static final String SEL_BY_CID_LANGID = "SELECT a FROM KscmtPremiumItemLanguage a "
			+ "WHERE a.kscmtPremiumItemLanguagePK.companyID = :companyId "
			+ "AND a.kscmtPremiumItemLanguagePK.langID = :langId";

	@Override
	public List<PremiumItemLanguage> findByCIdAndLangId(String companyId, String langId) {
		List<PremiumItemLanguage> data = this.queryProxy().query(SEL_BY_CID_LANGID, KscmtPremiumItemLanguage.class)
				.setParameter("companyId", companyId).setParameter("langId", langId).getList(x -> x.toDomain());
		return data;
	}

	@Override
	public Optional<PremiumItemLanguage> findById(String companyId, Integer displayNumber, String langId) {
		Optional<PremiumItemLanguage> data = this.queryProxy()
				.find(new KscmtPremiumItemLanguagePK(companyId, displayNumber, langId), KscmtPremiumItemLanguage.class)
				.map(x -> x.toDomain());
		return data;
	}

	@Override
	public void add(PremiumItemLanguage premiumItemLanguage) {
		this.commandProxy().insert(KscmtPremiumItemLanguage.toEntity(premiumItemLanguage));

	}

	@Override
	public void update(PremiumItemLanguage premiumItemLanguage) {
		KscmtPremiumItemLanguage entity = this.queryProxy()
				.find(new KscmtPremiumItemLanguagePK(premiumItemLanguage.getCompanyID(),
						premiumItemLanguage.getDisplayNumber(), premiumItemLanguage.getLangID()),
						KscmtPremiumItemLanguage.class)
				.get();
		entity.name = !premiumItemLanguage.getName().isPresent() ? null : premiumItemLanguage.getName().get().v();
		this.commandProxy().update(entity);

	}

}
