package nts.uk.shr.infra.i18n.loading;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nts.arc.i18n.custom.ICompanyResourceBundle;
import nts.arc.i18n.custom.ISessionLocale;
import nts.arc.layer.infra.data.EntityManagerLoader;
import nts.uk.shr.infra.i18n.entity.CompanyResource;

@SessionScoped
@Stateful
public class CompanyResourceLoading implements ICompanyResourceBundle {
	@Inject
	private ISessionLocale currentLanguage;
	@Inject
	private EntityManagerLoader managerLoader;
	// TODO: temporary fix session
	private static final String contractCode = "1";
	private static final String companyCode = "1";

	private static final String SELECT_ALL = "Select e from CompanyResource e where e.primaryKey.contractCode =:contractCode and e.primaryKey.companyCode =:companyCode and e.languageCode =:language";
	private Map<String, String> resource;

	public CompanyResourceLoading() {
	}

	/**
	 * reload resource
	 */
	public void refresh() {
		load();
	}

	@PostConstruct
	private void load() {
		resource = new HashMap<>();
		EntityManager em = managerLoader.getEntityManager();
		List<CompanyResource> compResource = em.createQuery(SELECT_ALL, CompanyResource.class)
				.setParameter("contractCode", contractCode).setParameter("companyCode", companyCode)
				.setParameter("language", currentLanguage.getSessionLocale().getLanguage()).getResultList();
		compResource.stream().forEach(p -> {
			resource.put(p.getPrimaryKey().getCode(), p.getContent());
		});
	}

	@Override
	public Map<String, String> getResource() {
		return resource;
	}

}
