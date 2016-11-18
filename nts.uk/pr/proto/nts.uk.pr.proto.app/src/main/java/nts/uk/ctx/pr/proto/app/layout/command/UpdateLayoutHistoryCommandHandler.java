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
 * UpdateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class UpdateLayoutHistoryCommandHandler extends CommandHandler<UpdateLayoutHistoryCommand> {

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateLayoutHistoryCommand> handlerContext) {
		  //this.repository.update(handlerContext.getCommand().toDomain());
		UpdateLayoutHistoryCommand command = handlerContext.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getStartYM(), command.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
//		LayoutMaster layoutNew = command.toDomain(layoutOrigin.getEndYM().v(), layoutOrigin.getLayoutAtr().value, layoutOrigin.getStmtName().v());
		
		
		LayoutMaster layoutNew = layoutOrigin.cloneWithDifferentStartYM(command.getStartYM());
//		LayoutMaster layoutOld = layoutOrigin.cloneWithDifferentStartYM(command.());
	
		//Validate follow EAP
		//GET FIRTS PREVIOUS HISTORY TO COMPARE START WITH START OF PREVIOUS HISTORY
		
		if (layoutNew.getStartYM().compareTo(layoutOrigin.getStartYM()) <= 0 || layoutNew.getStartYM().compareTo(layoutOrigin.getEndYM()) > 0) {
				
				throw new BusinessException(new RawErrorMessage("履歴の期間が重複しています。"));
			
			
		
		}
		layoutNew.validate();
		layoutRepo.add(layoutNew);
		
		if (command.isContinue()) {
			List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategories(companyCode, command.getStmtCode(), command.getStartYM());
			List<LayoutMasterLine> linesOrigin = lineRepo.getLines(companyCode, command.getStmtCode(), command.getStartYM());
			List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetails(companyCode, command.getStmtCode(), command.getStartYM());
			
			
			
			List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(
					org -> {
						return LayoutMasterCategory.createFromDomain(
								org.getCompanyCode(), 
								new YearMonth(command.getStartYM()), 
								org.getStmtCode(), 
								org.getCtAtr(), 
								new YearMonth(command.getEndYm()), 
								org.getCtgPos());
					}).collect(Collectors.toList());
			List<LayoutMasterLine> linesNew = linesOrigin.stream().map(
					org ->{
						return LayoutMasterLine.createFromDomain(
								org.getCompanyCode(), 
								new YearMonth(command.getStartYM()), 
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
								new YearMonth(command.getStartYM()), 
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
			
			

			categoryRepo.add(categoriesNew);
			lineRepo.add(linesNew);
			detailRepo.add(detailsNew);

			
		}
		
		layoutOrigin.setEndYM(new YearMonth(command.getGetEndYmPreviousl()));
		layoutRepo.update(layoutOrigin);
	
	}
	
	
}

