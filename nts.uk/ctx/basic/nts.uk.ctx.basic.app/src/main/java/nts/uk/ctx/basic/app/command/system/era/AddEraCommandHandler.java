package nts.uk.ctx.basic.app.command.system.era;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.system.era.Era;
import nts.uk.ctx.basic.dom.system.era.EraRepository;
import nts.uk.ctx.basic.dom.system.era.FixAttribute;

@RequestScoped
@Transactional
public class AddEraCommandHandler extends CommandHandler<AddEraCommand> {
	@Inject
	private EraRepository eraRepository;

	@Override
	public void handle(CommandHandlerContext<AddEraCommand> context) {
		Era era = context.getCommand().toDomain();
		era.setEraHist(IdentifierUtil.randomUniqueId());
//		if(era.getEraName()== null || era.getEraMark()== null || era.getStartDate()==null){
//			throw new BusinessException("The input data is not null");
//		}
		// check exist of that era
		Optional<Era> eraOpt = this.eraRepository.getEraDetail(era.getEraHist());
		if(eraOpt.isPresent()){
			throw new BusinessException("this era has already existed!");
		}
		//era.validate();
		era.setEndDate(GeneralDate.ymd(9999, 12, 31));
		era.setFixAttribute(FixAttribute.EDITABLE);
		Optional<Era> latestEra = this.eraRepository.getLatestEra();
		System.out.println(era.getEndDate());
		if (latestEra.isPresent()) {
			latestEra.get().setEndDate(era.getStartDate().addDays(-1));
			this.eraRepository.update(latestEra.get());
		}
		List<Era> latestStartDate = this.eraRepository.getStartDateEraMaster(era.getStartDate());
		if(latestStartDate != null && !latestStartDate.isEmpty()){
			throw new BusinessException("startDate must is latest");
		};
		
		this.eraRepository.add(era);
	}
	
	
	

}
