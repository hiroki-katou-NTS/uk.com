package nts.uk.ctx.pr.core.app.command.laborinsurance.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioService;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurPreRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurRateId;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
@Transactional
public class RegisterEmpInsurPreRateCommandHandler extends CommandHandler<RegisterEmpInsurBusBurRatioCommand> {

    @Inject
    private EmpInsurBusBurRatioService empInsurBusBurRatioService;

	@Override
	protected void handle(CommandHandlerContext<RegisterEmpInsurBusBurRatioCommand> context) {
	    
	    RegisterEmpInsurBusBurRatioCommand command = context.getCommand();
	    YearMonth startYearMonth = new YearMonth(command.getStartYearMonth());
	    YearMonth endYearMonth = new YearMonth(command.getEndYearMonth());
	    if (command.isNewMode()) {
	    	String newHistID = IdentifierUtil.randomUniqueId();
	    	Optional<EmpInsurBusBurRatio> general  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.GEN_BUS_BURDEN_RATIO.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(newHistID, EmpInsurRateId.GEN_BUS_BURDEN_RATIO.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	
	    	Optional<EmpInsurBusBurRatio> argiForestFish  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.BUS_RATIO_OF_AGRI_FOREST_FISH.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(newHistID, EmpInsurRateId.BUS_RATIO_OF_AGRI_FOREST_FISH.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	
	    	Optional<EmpInsurBusBurRatio> construction  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.BUS_BUR_RATIO_OF_CONSTRUCTION.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(newHistID, EmpInsurRateId.BUS_BUR_RATIO_OF_CONSTRUCTION.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	
	    	empInsurBusBurRatioService.addEmpInsurBusBurRatio(new EmpInsurPreRate(newHistID, argiForestFish.get(), construction.get(), general.get()), startYearMonth, endYearMonth);
	    } else {
	    	Optional<EmpInsurBusBurRatio> general  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.GEN_BUS_BURDEN_RATIO.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(command.getHisId(), EmpInsurRateId.GEN_BUS_BURDEN_RATIO.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	
	    	Optional<EmpInsurBusBurRatio> argiForestFish  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.BUS_RATIO_OF_AGRI_FOREST_FISH.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(command.getHisId(), EmpInsurRateId.BUS_RATIO_OF_AGRI_FOREST_FISH.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	
	    	Optional<EmpInsurBusBurRatio> construction  = command.getListEmpInsurPreRate().stream().filter(item -> item.getEmpPreRateId() == EmpInsurRateId.BUS_BUR_RATIO_OF_CONSTRUCTION.value).findFirst()
	    			.map(i -> {return new EmpInsurBusBurRatio(command.getHisId(), EmpInsurRateId.BUS_BUR_RATIO_OF_CONSTRUCTION.value, i.getIndBdRatio(), i.getEmpContrRatio(), i.getPerFracClass(), i.getBusiOwFracClass());
	    	});
	    	empInsurBusBurRatioService.updateEmpInsurBusBurRatio(new EmpInsurPreRate(command.getHisId(), argiForestFish.get(), construction.get(), general.get()), 
	    			new YearMonthHistoryItem(command.getHisId(),new YearMonthPeriod(startYearMonth, endYearMonth)));
	    }
	    
	}
}
