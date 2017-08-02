package nts.uk.shr.infra.i18n.loading;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.infra.i18n.dto.LanguageMasterDto;

public interface LanguageMasterLoading {

	public List<LanguageMasterDto> getSystemLanguages();
	
	public Optional<LanguageMasterDto> getSystemLanguage(String languageId);
}
