package nts.uk.ctx.pr.proto.app.command.layout;

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
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
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
 * CreateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class CreateLayoutCommandHandler extends CommandHandler<CreateLayoutCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutCommand> context) {
		try{
		CreateLayoutCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		if (layoutRepo.isExist(companyCode, command.getStmtCode())) {
			throw new BusinessException(new RawErrorMessage("入力した明細書コードは既に存在しています。\r\n明細書コードを確認してください。"));
		}
		
		LayoutMaster layout = command.toDomain();
		layout.validate();
		this.layoutRepo.add(layout);
		
		if (command.isCheckCopy()) {
			//List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategories(companyCode, command.getStmtCodeCopied(), command.getStartYmCopied());
			//[明細書マスタカテゴリ]の明細書コード = G_SEL_002で選択している項目の明細書コード AND　[明細書マスタカテゴリ]の終了年月 = 999912
			List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategoriesBefore(companyCode, command.getStmtCodeCopied(), 999912);
			if(!categoriesOrigin.isEmpty()){
				List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(
						org -> {
							return LayoutMasterCategory.createFromDomain(
									org.getCompanyCode(), 
									new YearMonth(command.getStartYm()), 
									//org.getStmtCode(),
									new LayoutCode(command.getStmtCode()),
									org.getCtAtr(), 
									new YearMonth(command.getEndYm()), 
									org.getCtgPos());
						}).collect(Collectors.toList());
				categoryRepo.add(categoriesNew);
			}
		
			//[明細書マスタ行]の明細書コード = G_SEL_002で選択している項目の明細書コード	　AND　[明細書マスタ行]の終了年月 = 999912
			List<LayoutMasterLine> linesOrigin = lineRepo.getLinesBefore(companyCode, command.getStmtCodeCopied(), command.getStartYmCopied());
			if(!linesOrigin.isEmpty())
			{
				List<LayoutMasterLine> linesNew = linesOrigin.stream().map(
						org ->{
							return LayoutMasterLine.createFromDomain(
									org.getCompanyCode(), 
									new YearMonth(command.getStartYm()), 
									//org.getStmtCode(),
									new LayoutCode(command.getStmtCode()),
									new YearMonth(command.getEndYm()), 
									org.getAutoLineId(), 
									org.getCategoryAtr(), 
									org.getLineDispayAttribute(), 
									org.getLinePosition());
						}).collect(Collectors.toList());
				lineRepo.add(linesNew);
			}
			
			//[明細書マスタ明細]の明細書コード = G_SEL_002で選択している項目の明細書コードAND　[明細書マスタ明細]の終了年月 = 999912
			List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetailsBefore(companyCode, command.getStmtCodeCopied(), 999912);
			if(!detailsOrigin.isEmpty()){
				List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(
						org -> {
							return LayoutMasterDetail.createFromDomain(
									org.getCompanyCode(), 
									//org.getLayoutCode(),
									new LayoutCode(command.getStmtCode()),
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
				
				detailRepo.add(detailsNew);
			}
		}
	}
		catch(Exception ex){
			throw ex;
		}
	}
	
}
