package nts.uk.ctx.pr.proto.app.command.layout.register;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RegisterLayoutCommandHandler extends CommandHandler<RegisterLayoutCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterLayoutCommand> context) {
		RegisterLayoutCommand command = context.getCommand();
		Layout layoutCommand = context.getCommand().getLayoutCommand();
		String companyCode = AppContexts.user().companyCode();
		String stmtCode = layoutCommand.getStmtCode();
		int startYm = layoutCommand.getStartYm();
		
		//Validate by EAP: 02.明細レイアウトの作成-登録時チェック処理
		LayoutMaster layoutOrg = layoutRepo.getLayout(companyCode, startYm, stmtCode)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。")));
		
		LayoutMaster layoutRegister = layoutCommand.toDomain(layoutOrg.getLayoutAtr().value);
		layoutRepo.update(layoutRegister);
		
		//Follow EAP: 03.明細レイアウトの作成-登録処理
		//Follow EAP: 03.1.明細書マスタカテゴリ登録処理
		categoryProcess(command, layoutCommand, companyCode, stmtCode, startYm);
		//Follow EAP: 03.2.明細書マスタ行登録処理
		lineProcess(command, layoutCommand, companyCode, stmtCode, startYm);
		//Follow EAP: 03.3.明細書マスタ明細登録処理
		detailProcess(command, layoutCommand, companyCode, stmtCode, startYm);
	}

	private void detailProcess(RegisterLayoutCommand command, Layout layoutCommand, String companyCode,
			String stmtCode, int startYm) {
		if (command.getListItemCodeDeleted().size() > 0) {
			for(LayoutDetail detailDeleteCommand : command.getListItemCodeDeleted()){
				detailRepo.remove(companyCode, stmtCode, startYm, detailDeleteCommand.getCategoryAtr(), detailDeleteCommand.getItemCode());
			}
		}

		List<LayoutMasterDetail> detailsFromDB = detailRepo.getDetails(companyCode, stmtCode, startYm);
		for(LayoutDetail detailCommand : command.getDetailCommand()){
			if (detailsFromDB.stream().filter(c ->
						c.getItemCode().v().equals(detailCommand.getItemCode())
						&& c.getCategoryAtr().value == detailCommand.getCategoryAtr()).findAny().isPresent()) {
				detailRepo.update(LayoutMasterDetail.createFromJavaType(companyCode, 
						stmtCode, startYm, layoutCommand.getEndYm(), detailCommand.getCategoryAtr(), 
						detailCommand.getItemCode(), detailCommand.getAutoLineId(), detailCommand.getDisplayAtr(), 
						detailCommand.getSumScopeAtr(), detailCommand.getCalculationMethod(), 
						detailCommand.getDistributeWay(), detailCommand.getDistributeSet(), 
						detailCommand.getPersonalWageCode(), detailCommand.getSetOffItemCode(), 
						detailCommand.getCommuteAtr(), detailCommand.getIsErrorUseHigh(), 
						detailCommand.getErrorRangeHigh(), detailCommand.getIsErrorUserLow(), 
						detailCommand.getErrorRangeLow(), detailCommand.getIsAlamUseHigh(), 
						detailCommand.getAlamRangeHigh(), detailCommand.getIsAlamUseLow(), 
						detailCommand.getAlamRangeLow(), detailCommand.getItemPosColumn()));
			} else {
				detailRepo.add(LayoutMasterDetail.createFromJavaType(companyCode, 
						stmtCode, startYm, layoutCommand.getEndYm(), detailCommand.getCategoryAtr(), 
						detailCommand.getItemCode(), detailCommand.getAutoLineId(), detailCommand.getDisplayAtr(), 
						detailCommand.getSumScopeAtr(), detailCommand.getCalculationMethod(), 
						detailCommand.getDistributeWay(), detailCommand.getDistributeSet(), 
						detailCommand.getPersonalWageCode(), detailCommand.getSetOffItemCode(), 
						detailCommand.getCommuteAtr(), detailCommand.getIsErrorUseHigh(), 
						detailCommand.getErrorRangeHigh(), detailCommand.getIsErrorUserLow(), 
						detailCommand.getErrorRangeLow(), detailCommand.getIsAlamUseHigh(), 
						detailCommand.getAlamRangeHigh(), detailCommand.getIsAlamUseLow(), 
						detailCommand.getAlamRangeLow(), detailCommand.getItemPosColumn()));
			}
		}
	}

	private void lineProcess(RegisterLayoutCommand command, Layout layoutCommand, String companyCode,
			String stmtCode, int startYm) {
		if (command.getListAutoLineIdDeleted().size() > 0) {
			for(LayoutLine lineDeleteCommand : command.getListAutoLineIdDeleted()){
				lineRepo.remove(companyCode, startYm, lineDeleteCommand.getAutoLineId(), 
						lineDeleteCommand.getCategoryAtr(), stmtCode);
								
				List<LayoutMasterDetail> detailsDelete = detailRepo.getDetailsByLine(lineDeleteCommand.getAutoLineId());
				detailRepo.remove(detailsDelete);
			}
		}

		List<LayoutMasterLine> linesFromDB = lineRepo.getLines(companyCode, stmtCode, startYm);
		for(LayoutLine lineCommand : command.getLineCommand()){
			if (linesFromDB.stream().filter(c ->
						c.getAutoLineId().v().equals(lineCommand.getAutoLineId())
						&& c.getCategoryAtr().value == lineCommand.getCategoryAtr()).findAny().isPresent()) {
				lineRepo.update(LayoutMasterLine.createFromJavaType(companyCode, 
						startYm, stmtCode, 
						layoutCommand.getEndYm(), 
						lineCommand.getAutoLineId(), 
						lineCommand.getLineDisplayAtr(), 
						lineCommand.getLinePosition(), 
						lineCommand.getCategoryAtr()));
			} else {
				lineRepo.add(LayoutMasterLine.createFromJavaType(companyCode, 
						startYm, stmtCode, 
						layoutCommand.getEndYm(), 
						lineCommand.getAutoLineId(), 
						lineCommand.getLineDisplayAtr(), 
						lineCommand.getLinePosition(), 
						lineCommand.getCategoryAtr()));
			}
		}
	}

	private void categoryProcess(RegisterLayoutCommand command, Layout layoutCommand, String companyCode,
			String stmtCode, int startYm) {
		if (command.getListCategoryAtrDeleted().size() > 0) {
			for(Integer categoryAtr : command.getListCategoryAtrDeleted()){
				categoryRepo.remove(companyCode, stmtCode, startYm, categoryAtr);
				
				List<LayoutMasterLine> linesDelete = lineRepo.getLines(companyCode, stmtCode, 
						startYm, categoryAtr);
				lineRepo.remove(linesDelete);
				
				List<LayoutMasterDetail> detailsDelete = detailRepo.getDetailsByCategory(
						companyCode, stmtCode, startYm, categoryAtr);
				detailRepo.remove(detailsDelete);
			}
		}
		
		List<LayoutMasterCategory> categoriesFromDB = categoryRepo.getCategories(companyCode, stmtCode, startYm);
		for(LayoutCategory categoryCommand : command.getCategoryCommand()){
			if (categoriesFromDB.stream().filter(c -> 
						c.getCtAtr().value == categoryCommand.getCategoryAtr()).findAny().isPresent()) {
				categoryRepo.update(LayoutMasterCategory.createFromJavaType(
						companyCode, startYm, stmtCode, 
						categoryCommand.getCategoryAtr(), 
						layoutCommand.getEndYm(), 
						categoryCommand.getCategoryPosition()));
			} else {
				categoryRepo.add(LayoutMasterCategory.createFromJavaType(
						companyCode, startYm, stmtCode, 
						categoryCommand.getCategoryAtr(), 
						layoutCommand.getEndYm(), 
						categoryCommand.getCategoryPosition()));
			}
		}
	}

}
