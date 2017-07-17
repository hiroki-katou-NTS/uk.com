package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateSpecificDateItemCommandHandler extends CommandHandler<List<SpecificDateItemCommand>>{

	@Inject
	private SpecificDateItemRepository repo;
	@Override
	protected void handle(CommandHandlerContext<List<SpecificDateItemCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<SpecificDateItem> lstAdd = new ArrayList<>();
		List<SpecificDateItem> lstUpdate = new ArrayList<>();
		List<SpecificDateItem> listUpdate = context.getCommand().stream().map(c -> {
			return SpecificDateItem.createFromJavaType(companyId, c.getTimeItemId(), BigDecimal.valueOf(c.getUseAtr()),
					BigDecimal.valueOf(c.getSpecificDateItemNo()),c.getSpecificName());
		}).collect(Collectors.toList());
		
		if (listUpdate == null) {
			return;
		}
		for (SpecificDateItem specificDateItem : listUpdate) {
			if(specificDateItem.getTimeItemId().equals("")){
				specificDateItem.setTimeItemId(UUID.randomUUID().toString());
				lstAdd.add(specificDateItem);
			}else{
				lstUpdate.add(specificDateItem);
			}
		}
		if(lstUpdate.size()>0){
			repo.updateSpecificDateItem(lstUpdate);
		}
		if(lstAdd.size()>0){
			repo.addSpecificDateItem(lstAdd);
		}
	}


}
