package nts.uk.shr.infra.i18n.resource.data;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.shr.infra.i18n.resource.container.CustomizedI18NResourceContainers;
import nts.uk.shr.infra.i18n.resource.container.I18NResourceContainer;
import nts.uk.shr.infra.i18n.resource.container.I18NResourceItem;
import nts.uk.shr.infra.i18n.resource.container.I18NResourcesRepository;

@Stateless
public class JpaI18NResourcesRepository extends JpaRepository implements I18NResourcesRepository {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends I18NResourceItem> I18NResourceContainer<T> loadResourcesDefault(String languageId) {
		
		String query = "SELECT e FROM CisctI18NResource e"
				+ " WHERE e.pk.languageId = :languageId";
		
		val items = this.queryProxy().query(query, CisctI18NResource.class)
				.setParameter("languageId", languageId)
				.getList(e -> e.toDomain());
		
		val container = new I18NResourceContainer<T>();
		container.addAll((List<T>) items);
		
		return container;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends I18NResourceItem> CustomizedI18NResourceContainers<T> loadResourcesEachCompanies(
			String languageId) {

		String query = "SELECT e FROM CismtI18NResourceCus e"
				+ " WHERE e.pk.languageId = :languageId";
		
		val containers = new CustomizedI18NResourceContainers<T>();
		
		this.queryProxy().query(query, CismtI18NResourceCus.class)
				.setParameter("languageId", languageId)
				.getList().forEach(e -> {
					containers.add(e.pk.companyId, (T)e.toDomain());
				});
		
		return containers;
	}


}
