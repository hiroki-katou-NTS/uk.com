package nts.uk.shr.infra.i18n.resource.container;

public interface I18NResourcesRepository {
	
	<T extends I18NResourceItem> I18NResourceContainer<T> loadResourcesDefault(String languageId);
	
	<T extends I18NResourceItem> CustomizedI18NResourceContainers<T> loadResourcesEachCompanies(String languageId);
}
