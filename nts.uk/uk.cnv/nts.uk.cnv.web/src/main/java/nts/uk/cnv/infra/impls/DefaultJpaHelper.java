package nts.uk.cnv.infra.impls;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.helper.JPAHelper;

@Stateless
public class DefaultJpaHelper implements JPAHelper {

	@Override
	public void makeDirty(Object entity) {
	}

}
