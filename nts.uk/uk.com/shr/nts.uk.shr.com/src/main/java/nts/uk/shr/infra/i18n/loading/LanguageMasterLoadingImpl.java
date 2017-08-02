package nts.uk.shr.infra.i18n.loading;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.shr.infra.i18n.dto.LanguageMasterDto;
import nts.uk.shr.infra.i18n.entity.LanguageMaster;

@Stateless
public class LanguageMasterLoadingImpl extends JpaRepository implements LanguageMasterLoading {

	public LanguageMasterLoadingImpl() {
	}

	@Override
	public List<LanguageMasterDto> getSystemLanguages() {
		List<LanguageMasterDto> languages = new ArrayList<>();
		
		while (languages.size() < 25) {
			languages.add(new LanguageMasterDto("Language " + languages.size(), "Language " + languages.size(), "Language " + languages.size()));
		}
		
		return languages;
//		return this.queryProxy().query("SELECT l FROM LanguageMaster l", LanguageMaster.class).getList(l -> {
//			return new LanguageMasterDto(l.getLanguageId(), l.getLanguageCode(), l.getLanguageName());
//		});
	}

	@Override
	public Optional<LanguageMasterDto> getSystemLanguage(String languageId) {
		return this.queryProxy().find(languageId, LanguageMaster.class)
			.map(l -> new LanguageMasterDto(l.getLanguageId(), l.getLanguageCode(), l.getLanguageName()));
	}
}
