package nts.uk.ctx.pr.core.app.command.rule.employment.layout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMaster;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.LayoutMasterLineRepository;

@Stateless
@Transactional
public class DeleteLayoutHistoryCommandHandler extends CommandHandler<DeleteLayoutHistoryCommand> {

	
	@Inject
	private LayoutMasterRepository layoutRepository;
	@Inject
	private LayoutMasterCategoryRepository categoryRepository;
	@Inject
	private LayoutMasterLineRepository lineRepository;
	@Inject
	private LayoutMasterDetailRepository detailRepository;
	@Override
	protected void handle(CommandHandlerContext<DeleteLayoutHistoryCommand> context) {
		DeleteLayoutHistoryCommand command = context.getCommand();
		//データベース削除
		deleteObject(command);
		//データベース更新
		updateObject(command);
	}
	
	private void deleteObject(DeleteLayoutHistoryCommand command){
		//データベース削除[明細書マスタ.DEL-1]を実施する
		this.layoutRepository.remove(command.getCompanyCode(),
				command.getStmtCode(),
				command.getHistoryId());
		//1件成功した場合以外の場合
		//bo sung them
		
		//データベース削除[明細書マスタカテゴリ.DEL-2] を実施する
		List<LayoutMasterCategory> lstCategories = this.categoryRepository.getCategories(command.getCompanyCode(),
				command.getStmtCode(), 
				command.getStartYm());
		this.categoryRepository.removeAllCategory(lstCategories);
		//1件成功した場合以外の場合
		//bo sung them
		
		//データベース削除[明細書マスタ行.DEL-2] を実施する
		List<LayoutMasterLine> lstLineOfCategory = this.lineRepository.getLines(command.getCompanyCode(), 
				command.getStmtCode(), 
				command.getStartYm());
		this.lineRepository.remove(lstLineOfCategory);
		
		
		//データベース削除[明細書マスタ明細.DEｌ-1] を実施する
		List<LayoutMasterDetail> lstDetails = this.detailRepository.getDetails(command.getCompanyCode(),
				command.getStmtCode(),
				command.getStartYm());				
		this.detailRepository.remove(lstDetails);
	}
	
	private void updateObject(DeleteLayoutHistoryCommand command){
		//データベース更新[明細書マスタ.UPD-2] を実施する
		Optional<LayoutMaster> layoutBefore = layoutRepository.getHistoryBefore(command.getCompanyCode(),
				command.getStmtCode(),
				command.getStartYm() - 1);
		if (layoutBefore != null
			&& layoutBefore.isPresent()) {
			LayoutMaster layout = layoutBefore.get();
			layout.setEndYm(new YearMonth(command.getEndYm()));
			layoutRepository.update(layout);
		}
		
		//データベース更新[明細書マスタカテゴリ.UPD-2] を実施する
		List<LayoutMasterCategory> lstCategoryBefore = this.categoryRepository.getCategoriesBefore(command.getCompanyCode(),
				command.getStmtCode(),
				command.getStartYm() - 1);
		if(!lstCategoryBefore.isEmpty()){
			List<LayoutMasterCategory> lstCategoryUP = lstCategoryBefore.stream().map(
					category -> {
						return LayoutMasterCategory.createFromDomain(category.getCompanyCode(),
								category.getStartYM(),
								category.getStmtCode(),
								category.getCtAtr(),
								new YearMonth(command.getEndYm()),
								category.getCtgPos(),
								category.getHistoryId());
					}).collect(Collectors.toList());
			this.categoryRepository.update(lstCategoryUP);
		}
		
		
		//データベース更新[明細書マスタ行.UPD-2] を実施する
		List<LayoutMasterLine> lstLineBefore = this.lineRepository.getLinesBefore(command.getCompanyCode(),
				command.getStmtCode(),
				command.getStartYm() - 1);
		if(!lstLineBefore.isEmpty()){
			List<LayoutMasterLine> lstLineUp = lstLineBefore.stream().map(
					line ->{
						return LayoutMasterLine.createFromDomain(line.getCompanyCode(),
								line.getStartYM(), 
								line.getStmtCode(), 
								new YearMonth(command.getEndYm()), 
								line.getAutoLineId(), 
								line.getCategoryAtr(), 
								line.getLineDispayAttribute(), 
								line.getLinePosition(),
								line.getHistoryId());
					}).collect(Collectors.toList());
			this.lineRepository.update(lstLineUp);	
		}
		
		//データベース更新[明細書マスタ明細.UPD-2] を実施する
		List<LayoutMasterDetail> detailsBefore = detailRepository.getDetailsBefore(command.getCompanyCode(), 
				command.getStmtCode(), 
				command.getStartYm() - 1);
		if(!detailsBefore.isEmpty()){
			List<LayoutMasterDetail> detailsUpdate = detailsBefore.stream().map(
					org -> {
						return LayoutMasterDetail.createFromDomain(
								org.getCompanyCode(), 
								org.getLayoutCode(), 
								org.getStartYm(),
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
								org.getPersonalWageCode(),
								org.getHistoryId());
					}).collect(Collectors.toList());
			this.detailRepository.update(detailsUpdate);
		}
		
	}
	
}
