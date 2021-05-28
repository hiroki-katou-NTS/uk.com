package nts.uk.ctx.pereg.app.subscriber;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.domainevent.DomainEventSubscriber;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository;
import nts.uk.shr.com.tenant.event.CreatedTenantEvent;

/**
 * 個人情報のテナント初期値を作る
 * @author keisuke_hoshina
 */
@Stateless
public class CreatePeregInitialTenant implements DomainEventSubscriber<CreatedTenantEvent>{

	@Inject
	private CopyPerInfoRepository copyPerInfoRepository;
	
	@Override
	public Class<CreatedTenantEvent> subscribedToEventType() {
		return CreatedTenantEvent.class;
	}

	@Override
	public AtomTask handle(CreatedTenantEvent domainEvent) {
		return AtomTask.of(()->{
			copyPerInfoRepository.copyOnTenantCreated(domainEvent.getContractCode());
		});
	}

}
