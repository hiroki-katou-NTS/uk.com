package nts.uk.cnv.infra.impls;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nts.arc.layer.infra.data.EntityManagerLoader;
import nts.arc.layer.infra.data.EntityManagerLoaderSwitch;

public class EntityManagerLoaderSwitchImpl implements EntityManagerLoaderSwitch {

	@Inject
    private DefaultEntityManagerLoader defaultLoader;

	@PostConstruct
	public void useTenantLocator() {
	}

	@Override
	public EntityManagerLoader get() {
		return defaultLoader;
	}

}
