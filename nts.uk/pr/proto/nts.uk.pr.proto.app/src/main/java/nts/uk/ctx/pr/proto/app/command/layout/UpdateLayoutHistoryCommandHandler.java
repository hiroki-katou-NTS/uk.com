package nts.uk.ctx.pr.proto.app.command.layout;

import java.util.List;
import java.util.Optional;
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
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getHistoryId(), command.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
		//Validate by EA: 12.履歴の編集-登録時チェック処理
		Optional<LayoutMaster> layoutBefore = layoutRepo.getHistoryBefore(companyCode, command.getStmtCode(), command.getStartYmOriginal());
		//validateHistorySpan(command, layoutOrigin, layoutBefore);

		//this.layoutRepo.remove(companyCode, command.getStmtCode(), command.getStartYmOriginal());
		layoutOrigin.setStartYM(new YearMonth(command.getStartYm()));
		this.layoutRepo.update(layoutOrigin);
		//layoutRepo.update(layoutOrigin);
		//trong truong hop ngay bat dau lon hon ngay ket thuc 
		updateCurrentObject(command, companyCode);
		if(layoutBefore != null){
			LayoutMaster layout = layoutBefore.get();
			//this.layoutRepo.remove(companyCode, command.getStmtCode(), layout.getStartYM().v());
			layout.setEndYm(new YearMonth(command.getStartYm()).previousMonth());
			layoutRepo.update(layout);
			//update thang dang truoc no
			updatePreviousObject(command, companyCode, layoutOrigin);
		}
	}

	private void validateHistorySpan(UpdateLayoutHistoryCommand command, LayoutMaster layoutOrigin,
			Optional<LayoutMaster> layoutBefore) {
		if (layoutBefore != null && layoutBefore.isPresent()) {
			//直前の[明細書マスタ]の開始年月　＜　入力した開始年月　≦　終了年月　の場合
			if (command.getStartYm() < layoutBefore.get().getStartYM().v()
					&& command.getStartYm() > layoutOrigin.getEndYm().v()) {
				throw new BusinessException(new RawErrorMessage("履歴の期間が重複しています。"));
			}
		} else {
			if (command.getStartYm() < 190001) {
				throw new BusinessException(new RawErrorMessage("履歴の期間が重複しています。"));
			}
		}
	}

	private void updatePreviousObject(UpdateLayoutHistoryCommand command, String companyCode,
			LayoutMaster layoutOrigin) {
		List<LayoutMasterCategory> categoriesBefore = categoryRepo.getCategoriesBefore(companyCode, command.getStmtCode(), command.getStartYmOriginal() - 1);
		List<LayoutMasterLine> linesBefore = lineRepo.getLinesBefore(companyCode, command.getStmtCode(), command.getStartYmOriginal() - 1);
		List<LayoutMasterDetail> detailsBefore = detailRepo.getDetailsBefore(companyCode, command.getStmtCode(), command.getStartYmOriginal() - 1);
		List<LayoutMasterCategory> categoriesUpdate = categoriesBefore.stream().map(
				org -> {
					return LayoutMasterCategory.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(), 
							org.getStmtCode(), 
							org.getCtAtr(), 
							new YearMonth(command.getStartYm()).previousMonth(), 
							org.getCtgPos(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		
		List<LayoutMasterLine> linesUpdate = linesBefore.stream().map(
				org ->{
					return LayoutMasterLine.createFromDomain(
							org.getCompanyCode(), 
							org.getStartYM(),
							org.getStmtCode(), 
							new YearMonth(command.getStartYm()).previousMonth(), 
							org.getAutoLineId(), 
							org.getCategoryAtr(), 
							org.getLineDispayAttribute(), 
							org.getLinePosition(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		
		List<LayoutMasterDetail> detailsUpdate = detailsBefore.stream().map(
				org -> {
					return LayoutMasterDetail.createFromDomain(
							org.getCompanyCode(), 
							org.getLayoutCode(), 
							org.getStartYm(),
							new YearMonth(command.getStartYm()).previousMonth(), 
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
		
		categoryRepo.update(categoriesUpdate);
		lineRepo.update(linesUpdate);
		detailRepo.update(detailsUpdate);
	}

	private void updateCurrentObject(UpdateLayoutHistoryCommand command, String companyCode) {
		
		List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategories(companyCode, command.getStmtCode(), command.getStartYmOriginal());
		List<LayoutMasterLine> linesOrigin = lineRepo.getLines(companyCode, command.getStmtCode(), command.getStartYmOriginal());
		List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetails(companyCode, command.getStmtCode(), command.getStartYmOriginal());
//		this.categoryRepo.removeAllCategory(categoriesOrigin);
//		this.lineRepo.remove(linesOrigin);
//		this.detailRepo.remove(detailsOrigin);
		
		List<LayoutMasterCategory> categoriesUpdate = categoriesOrigin.stream().map(
				org -> {
					return LayoutMasterCategory.createFromDomain(
							org.getCompanyCode(), 
							new YearMonth(command.getStartYm()), 
							org.getStmtCode(), 
							org.getCtAtr(), 
							org.getEndYm(), 
							org.getCtgPos(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		
		List<LayoutMasterLine> linesUpdate = linesOrigin.stream().map(
				org ->{
					return LayoutMasterLine.createFromDomain(
							org.getCompanyCode(), 
							new YearMonth(command.getStartYm()), 
							org.getStmtCode(), 
							org.getEndYM(),
							org.getAutoLineId(), 
							org.getCategoryAtr(), 
							org.getLineDispayAttribute(), 
							org.getLinePosition(),
							org.getHistoryId());
				}).collect(Collectors.toList());
		
		List<LayoutMasterDetail> detailsUpdate = detailsOrigin.stream().map(
				org -> {
					return LayoutMasterDetail.createFromDomain(
							org.getCompanyCode(), 
							org.getLayoutCode(), 
							new YearMonth(command.getStartYm()), 
							org.getEndYm(),
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
		
		categoryRepo.update(categoriesUpdate);
		lineRepo.update(linesUpdate);
		detailRepo.update(detailsUpdate);
	}
	
	
}

