package nts.uk.ctx.pr.proto.app.layout.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class CreateLayoutHistoryCommandHandler extends CommandHandler<CreateLayoutHistoryCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutHistoryCommand> context) {
		CreateLayoutHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getStmtCode(), command.getStartYm())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
		LayoutMaster layoutNew = command.toDomain(layoutOrigin.getLayoutAtr().value, layoutOrigin.getStmtName().v());
		
		//Validate follow EAP
		if (layoutNew.getStartYM().compareTo(layoutOrigin.getStartYM()) < 0){
			//ER011
			throw new BusinessException(new RawErrorMessage("履歴の期間が正しくありません。"));
		}
		
		//Insert follow EAP
		layoutNew.validate();
		layoutRepo.add(layoutNew);
		
		if (command.isContinue()) {
			List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategories(companyCode, command.getStmtCode(), command.getStartYm());
			List<LayoutMasterLine> linesOrigin = lineRepo.getLines(companyCode, command.getStmtCode(), command.getStartYm());
			List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetails(companyCode, command.getStmtCode(), command.getStartYm());
			
			List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(
					org -> {
						return LayoutMasterCategory.createFromDomain(
								org.getCompanyCode(), 
								new YearMonth(command.getStartYm()), 
								org.getStmtCode(), 
								org.getCtAtr(), 
								new YearMonth(command.getEndYm()), 
								org.getCtgPos());
					}).collect(Collectors.toList());
			
			List<LayoutMasterLine> linesNew = linesOrigin.stream().map(
					org ->{
						return LayoutMasterLine.createFromDomain(
								org.getCompanyCode(), 
								new YearMonth(command.getStartYm()), 
								org.getStmtCode(), 
								new YearMonth(command.getEndYm()), 
								org.getAutoLineId(), 
								org.getCategoryAtr(), 
								org.getLineDispayAttribute(), 
								org.getLinePosition());
					}).collect(Collectors.toList());
			
			List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(
					org -> {
						return LayoutMasterDetail.createFromDomain(
								org.getCompanyCode(), 
								org.getLayoutCode(), 
								new YearMonth(command.getStartYm()), 
								new YearMonth(command.getEndYm()), 
								org.getCategoryAtr(), 
								org.getItemCode(), 
								org.getAutoLineId(), 
								org.getItemPosColumn(), 
								org.getError(), 
								org.getCalculationMethod(), 
								org.getDistribute(), 
								org.getDisplayAtr(), 
								org.getAlarm(), 
								org.getSumScopeAtr(), 
								org.getSetOffItemCode(), 
								org.getCommuteAtr(), 
								org.getPersonalWageCode());
					}).collect(Collectors.toList());
			
		}
	}

}
