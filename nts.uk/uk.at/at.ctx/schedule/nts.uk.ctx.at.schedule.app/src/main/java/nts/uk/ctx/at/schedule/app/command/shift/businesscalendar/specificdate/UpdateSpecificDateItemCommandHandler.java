package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItemRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateSpecificDateItemCommandHandler extends CommandHandler<List<SpecificDateItemCommand>>{

	@Inject
	private SpecificDateItemRepository repo;
	@Override
	protected void handle(CommandHandlerContext<List<SpecificDateItemCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<SpecificDateItemCommand> lstItem = context.getCommand();
		int check =0;
		for (SpecificDateItemCommand item : lstItem) {
			check = check + item.getUseAtr();
		}
		if(check==0){
			throw new BusinessException("Msg_135");
		}
		List<SpecificDateItem> lstAdd = new ArrayList<>();
		List<SpecificDateItem> lstUpdate = new ArrayList<>();
		List<SpecificDateItem> listUpdate = lstItem.stream().map(c -> {
			return SpecificDateItem.createFromJavaType(companyId, c.getUseAtr(),
					c.getSpecificDateItemNo(),c.getSpecificName());
		}).collect(Collectors.toList());
		
		if (listUpdate == null) {
			return;
		}
		List<SpecificDateItem> lstOld = repo.getAll(companyId);
		for (SpecificDateItem specificDateItem : listUpdate) {
			if(lstOld.stream()
		            .anyMatch(c -> c.getSpecificDateItemNo().equals(specificDateItem.getSpecificDateItemNo()))){
				lstUpdate.add(specificDateItem);
			}else{
				lstAdd.add(specificDateItem);
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
