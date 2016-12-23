package nts.uk.ctx.pr.proto.app.command.layout;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
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
@Stateless
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
	@Inject
	private CreateLayoutCommandHandler layoutCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutHistoryCommand> context) {
		CreateLayoutHistoryCommand command = context.getCommand();
		
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getHistoryBefore(companyCode, command.getStmtCode(), command.getStartYm())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategories(companyCode, command.getStmtCode(), command.getStartPrevious());
		List<LayoutMasterLine> linesOrigin = lineRepo.getLines(companyCode, command.getStmtCode(), command.getStartPrevious());
		List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetails(companyCode, command.getStmtCode(), command.getStartPrevious());
		LayoutMaster layoutNew = command.toDomain(command.getStartYm(),
				999912,
				command.getLayoutAtr(),
				layoutOrigin.getStmtName().v(),
				IdentifierUtil.randomUniqueId());
		
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
			copyFromPreviousHistory(command, 
					companyCode,
					categoriesOrigin,
					linesOrigin,
					detailsOrigin);
		}else{
			layoutCommandHandler.createNewData(layoutNew, companyCode);
		}
		//「初めから作成する。」を選択した場合
		// and　「最新の履歴から引き継ぐ」を選択した場合 更新データ
		updateData(command, 
				companyCode, 
				layoutOrigin, 
				layoutNew,
				categoriesOrigin,
				linesOrigin,
				detailsOrigin);
	}

	private void copyFromPreviousHistory(CreateLayoutHistoryCommand command, 
			String companyCode, 
			List<LayoutMasterCategory> categoriesOrigin,
			List<LayoutMasterLine> linesOrigin,
			List<LayoutMasterDetail> detailsOrigin) {
		List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(
				org -> {
					return LayoutMasterCategory.createFromDomain(
							org.getCompanyCode(), 
							new YearMonth(command.getStartYm()), 
							org.getStmtCode(), 
							org.getCtAtr(), 
							new YearMonth(999912), 
							org.getCtgPos(),
							IdentifierUtil.randomUniqueId());
				}).collect(Collectors.toList());
		categoryRepo.add(categoriesNew);
		
		List<LayoutMasterLine> linesNew = linesOrigin.stream().map(
				org ->{
					return LayoutMasterLine.createFromDomain(
							org.getCompanyCode(), 
							new YearMonth(command.getStartYm()), 
							org.getStmtCode(), 
							new YearMonth(999912), 
							org.getAutoLineId(), 
							org.getCategoryAtr(), 
							org.getLineDispayAttribute(), 
							org.getLinePosition(),
							IdentifierUtil.randomUniqueId());
				}).collect(Collectors.toList());
		lineRepo.add(linesNew);
		
		List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(
				org -> {
					return LayoutMasterDetail.createFromDomain(
							org.getCompanyCode(), 
							org.getLayoutCode(), 
							new YearMonth(command.getStartYm()), 
							new YearMonth(999912), 
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
							IdentifierUtil.randomUniqueId());
				}).collect(Collectors.toList());
		detailRepo.add(detailsNew);
	}
	
	private void updateData(CreateLayoutHistoryCommand command, 
			String companyCode, 
			LayoutMaster layoutOrigin, 
			LayoutMaster layoutNew,
			List<LayoutMasterCategory> categoriesOrigin,
			List<LayoutMasterLine> linesOrigin,
			List<LayoutMasterDetail> detailsOrigin){
		YearMonth endYm = new YearMonth(command.getEndYm()).previousMonth();
		//データベース更新[明細書マスタ.UPD-2] を実施する
		layoutOrigin.setEndYm(endYm);
		this.layoutRepo.update(layoutOrigin);
		//データベース更新[明細書マスタカテゴリ.UPD-2] を実施する
		//List<LayoutMasterCategory> lstCategory = this.categoryRepo.getCategories(companyCode, command.getStmtCode(), command.getStartPrevious());
		List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(
				org -> {
					return LayoutMasterCategory.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(), 
							org.getStmtCode(), 
							org.getCtAtr(), 
							endYm, 
							org.getCtgPos(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		this.categoryRepo.update(categoriesNew);
		//データベース更新[明細書マスタ行.UPD-2] を実施する
		//List<LayoutMasterLine> lstLine = this.lineRepo.getLines(companyCode, command.getStmtCode(), command.getStartPrevious());
		List<LayoutMasterLine> linesNew = linesOrigin.stream().map(
				org ->{
					return LayoutMasterLine.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(), 
							org.getStmtCode(), 
							endYm, 
							org.getAutoLineId(), 
							org.getCategoryAtr(), 
							org.getLineDispayAttribute(), 
							org.getLinePosition(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		this.lineRepo.update(linesNew);
		//データベース更新[明細書マスタ明細.UPD-2] を実施する
//		List<LayoutMasterDetail> lstDetail = this.detailRepo.getDetailsBefore(companyCode, command.getStmtCode(), 999912);
		//List<LayoutMasterDetail> lstDetail = this.detailRepo.getDetails(companyCode, command.getStmtCode(), command.getStartPrevious());
		List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(
				org -> {
					return LayoutMasterDetail.createFromDomain(
							org.getCompanyCode(), 
							org.getLayoutCode(), 
							org.getStartYm(), 
							endYm, 
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
		this.detailRepo.update(detailsNew);
	}
}
