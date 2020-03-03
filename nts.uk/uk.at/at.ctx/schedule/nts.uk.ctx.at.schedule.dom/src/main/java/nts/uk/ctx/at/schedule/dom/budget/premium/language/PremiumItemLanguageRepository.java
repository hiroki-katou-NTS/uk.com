package nts.uk.ctx.at.schedule.dom.budget.premium.language;

import java.util.List;
import java.util.Optional;

public interface PremiumItemLanguageRepository {

	public List<PremiumItemLanguage> findByCIdAndLangId(String companyId, String langId);

	public Optional<PremiumItemLanguage> findById(String companyId, Integer displayNumber, String langId);

	void add(PremiumItemLanguage premiumItemLanguage);

	void update(PremiumItemLanguage premiumItemLanguage);
}
