package nts.uk.ctx.hr.shared.infra.repository.reflect;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyItem;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyItemRepository;
import nts.uk.ctx.hr.shared.infra.entity.report.registration.PpedtPreReflectAnyItem;
import nts.uk.ctx.hr.shared.infra.entity.report.registration.PpedtPreReflectAnyItemPk;

@Stateless
public class JpaPreReflectAnyItemRepository extends JpaRepository implements PreReflectAnyItemRepository{

	@Override
	public void insertAll(List<PreReflectAnyItem> anyItems) {
		this.commandProxy().insertAll(anyItems.stream().map(c -> {
			return toEntity(c);
		}).collect(Collectors.toList()));
		
	}
	
	private PpedtPreReflectAnyItem toEntity(PreReflectAnyItem  domain) {
		PpedtPreReflectAnyItem entity = new PpedtPreReflectAnyItem( 
				new PpedtPreReflectAnyItemPk(domain.getHistId(),
				domain.getParentHistId()),
				domain.getCid(),
				domain.getReportId(),
				domain.getDispOrder(),
				domain.getCategoryId(),
				domain.getCategoryCode(),
				domain.getItemId(),
				domain.getItemCode(),
				null,
				domain.getSaveDataAtr(),
				domain.getStringVal(),
				domain.getIntVal(),
				domain.getDateVal());
		return entity;
	}

}
