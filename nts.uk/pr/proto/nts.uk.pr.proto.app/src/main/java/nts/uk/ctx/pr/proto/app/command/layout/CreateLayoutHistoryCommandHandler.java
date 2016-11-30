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
	
	private CreateLayoutCommandHandler layoutCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutHistoryCommand> context) {
		CreateLayoutHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getStartYm(), command.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
		LayoutMaster layoutNew = command.toDomain(command.getStartPrevious(),
				999912,
				command.getLayoutAtr(),
				layoutOrigin.getStmtName().v());
		
		//Validate follow EAP
		if (layoutNew.getStartYM().compareTo(layoutOrigin.getStartYM()) < 0){
			//ER011
			throw new BusinessException(new RawErrorMessage("履歴の期間が正しくありません。"));
		}
		
		//データベース登録[明細書マスタ.INS-1]を実施する
		layoutNew.validate();
		layoutRepo.add(layoutNew);
		//「最新の履歴から引き継ぐ」を選択した場合
		if (command.isCheckContinue()) {
			copyFromPreviousHistory(command, companyCode);
		}else{
			layoutCommandHandler.createNewData(layoutNew, companyCode);
		}
		//「初めから作成する。」を選択した場合
		// and　「最新の履歴から引き継ぐ」を選択した場合 更新データ
		updateData(command, companyCode, layoutOrigin);
	}

	private void copyFromPreviousHistory(CreateLayoutHistoryCommand command, String companyCode) {
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
		
		categoryRepo.add(categoriesNew);
		lineRepo.add(linesNew);
		detailRepo.add(detailsNew);
	}
	
	private void updateData(CreateLayoutHistoryCommand command, String companyCode, LayoutMaster layoutOrigin){
		//データベース更新[明細書マスタ.UPD-2] を実施する
		layoutOrigin.setEndYm(new YearMonth(command.getEndYm()));
		this.layoutRepo.update(layoutOrigin);
		//データベース更新[明細書マスタカテゴリ.UPD-2] を実施する
		List<LayoutMasterCategory> lstCategory = this.categoryRepo.getCategoriesBefore(companyCode, command.getStmtCode(), 999912);
		List<LayoutMasterCategory> categoriesNew = lstCategory.stream().map(
				org -> {
					return LayoutMasterCategory.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(), 
							org.getStmtCode(), 
							org.getCtAtr(), 
							new YearMonth(command.getEndYm()), 
							org.getCtgPos());
				}).collect(Collectors.toList());
		this.categoryRepo.update(categoriesNew);
		//データベース更新[明細書マスタ行.UPD-2] を実施する
		List<LayoutMasterLine> lstLine = this.lineRepo.getLinesBefore(companyCode, command.getStmtCode(), 999912);
		List<LayoutMasterLine> linesNew = lstLine.stream().map(
				org ->{
					return LayoutMasterLine.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(), 
							org.getStmtCode(), 
							new YearMonth(command.getEndYm()), 
							org.getAutoLineId(), 
							org.getCategoryAtr(), 
							org.getLineDispayAttribute(), 
							org.getLinePosition());
				}).collect(Collectors.toList());
		this.lineRepo.update(linesNew);
		//データベース更新[明細書マスタ明細.UPD-2] を実施する
		List<LayoutMasterDetail> lstDetail = this.detailRepo.getDetailsBefore(companyCode, command.getStmtCode(), 999912);
		List<LayoutMasterDetail> detailsNew = lstDetail.stream().map(
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
							org.getPersonalWageCode());
				}).collect(Collectors.toList());
		this.detailRepo.update(detailsNew);
	}
}
