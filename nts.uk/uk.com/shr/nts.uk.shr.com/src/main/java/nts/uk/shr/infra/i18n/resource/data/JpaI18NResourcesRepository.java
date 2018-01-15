package nts.uk.shr.infra.i18n.resource.data;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
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
		
		val container = new I18NResourceContainer<T>(GeneralDateTime.now());
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends I18NResourceItem> I18NResourceContainer<T> loadResourcesOfCompany(
			String companyId,
			String languageId) {

		String query = "SELECT e FROM CismtI18NResourceCus e"
				+ " WHERE e.pk.companyId = :companyId AND e.pk.languageId = :languageId";
		
		val container = new I18NResourceContainer<T>(GeneralDateTime.now());
		
		this.queryProxy().query(query, CismtI18NResourceCus.class)
				.setParameter("companyId", companyId)
				.setParameter("languageId", languageId)
				.getList().forEach(e -> {
					container.add((T)e.toDomain());
				});
		
		return container;
	}

	@Override
	public Optional<GeneralDateTime> getLastUpdatedDateTime(String companyId, String languageId) {
		
		return this.queryProxy()
				.find(new CisdtI18NResourceVerPK(companyId, languageId), CisdtI18NResourceVer.class)
				.map(e -> e.lastUpdatedAt);
	}

	@Override
	public void refreshResource(String companyId, String languageId, GeneralDateTime datetime) {

		val entityOpt = this.queryProxy().find(new CisdtI18NResourceVerPK(companyId, languageId), CisdtI18NResourceVer.class);
		if (entityOpt.isPresent()) {
			entityOpt.get().lastUpdatedAt = datetime;
			this.commandProxy().update(entityOpt.get());
		} else {
			val entity = new CisdtI18NResourceVer();
			entity.pk = new CisdtI18NResourceVerPK(companyId, languageId);
			entity.lastUpdatedAt = datetime;
			this.commandProxy().insert(entity);
		}
	}

}
