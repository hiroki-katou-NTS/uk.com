package nts.uk.ctx.pr.core.app.command.rule.employment.layout;

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
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutHistRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMaster;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author lamvt
 *
 */
@Stateless
@Transactional
public class CreateLayoutHistoryCommandHandler extends CommandHandler<CreateLayoutHistoryCommand> {

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutHistRepository layoutHisRepo;
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
		LayoutHistory layoutHistOrigin = layoutHisRepo
				.getHistoryBefore(companyCode, command.getStmtCode(), command.getStartPrevious())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout history")));
		LayoutMaster layoutOrgin = layoutRepo.getHistoryBefore(companyCode, command.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout head")));
		List<LayoutMasterCategory> categoriesOrigin = categoryRepo.getCategoriesBefore(companyCode,
				command.getStmtCode(), layoutHistOrigin.getHistoryId());
		List<LayoutMasterLine> linesOrigin = lineRepo.getLines(companyCode, command.getStmtCode(),
				layoutHistOrigin.getHistoryId());
		List<LayoutMasterDetail> detailsOrigin = detailRepo.getDetailsBefore(companyCode, command.getStmtCode(),
				layoutHistOrigin.getHistoryId());
		LayoutMaster layoutNew = command.toDomain(layoutOrgin.getStmtName().v());
		LayoutHistory layoutHistNew = command.toDomain(command.getStartYm(), 999912, command.getLayoutAtr(),
				IdentifierUtil.randomUniqueId());

		// Validate follow EAP
		if (layoutHistNew.getStartYm().compareTo(layoutHistOrigin.getStartYm()) < 0) {
			// ER011
			throw new BusinessException(new RawErrorMessage("履歴の期間が正しくありません。"));
		}

		// データベース登録[明細書マスタ.INS-1]を実施する
		layoutNew.validate();
		layoutRepo.add(layoutNew);
		layoutHisRepo.add(layoutHistNew);
		// 「最新の履歴から引き継ぐ」を選択した場合
		if (command.isCheckContinue()) {
			// Lanlt 17.03.2017
			copyFromPreviousHistory(command, companyCode, categoriesOrigin, linesOrigin, detailsOrigin);
		} else {
			layoutCommandHandler.createNewData(layoutNew, layoutHistNew, companyCode);
		}
		// Lanlt 17.03.2017
		updateData(command, companyCode, layoutOrgin, layoutNew, layoutHistOrigin, layoutHistNew, categoriesOrigin,
				linesOrigin, detailsOrigin);
	}

	private void copyFromPreviousHistory(CreateLayoutHistoryCommand command, String companyCode,
			List<LayoutMasterCategory> categoriesOrigin, List<LayoutMasterLine> linesOrigin,
			List<LayoutMasterDetail> detailsOrigin) {
		List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(org -> {
			return LayoutMasterCategory.createFromDomain(org.getCompanyCode(), 
					org.getStmtCode(),
					org.getCtAtr(),
					org.getCtgPos(),
					IdentifierUtil.randomUniqueId());
		}).collect(Collectors.toList());
		categoryRepo.add(categoriesNew);

		List<LayoutMasterLine> linesNew = linesOrigin.stream().map(org -> {
			return LayoutMasterLine.createFromDomain(org.getCompanyCode(),
					org.getStmtCode(), org.getAutoLineId(),
					org.getCategoryAtr(),
					org.getLineDisplayAttribute(),
					org.getLinePosition(),
					IdentifierUtil.randomUniqueId());
		}).collect(Collectors.toList());
		lineRepo.add(linesNew);

		List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(org -> {
			return LayoutMasterDetail.createFromDomain(org.getCompanyCode(),
					org.getStmtCode(),
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
					org.getFormulaCode(),
					org.getPersonalWageCode(),
					org.getWageTableCode(),
					org.getCommonAmount(),
					IdentifierUtil.randomUniqueId());
		}).collect(Collectors.toList());
		detailRepo.add(detailsNew);
	}

	private void updateData(CreateLayoutHistoryCommand command, String companyCode, LayoutMaster layoutOrigin,
			LayoutMaster layoutNew, LayoutHistory layoutHistOrigin, LayoutHistory layoutHistNew,
			List<LayoutMasterCategory> categoriesOrigin, List<LayoutMasterLine> linesOrigin,
			List<LayoutMasterDetail> detailsOrigin) {
		YearMonth endYm = new YearMonth(command.getEndYm()).previousMonth();
		// データベース更新[明細書マスタ.UPD-2] を実施する
		// データベース更新[明細書マスタカテゴリ.UPD-2] を実施する

		layoutHistOrigin.setEndYm(endYm);
		this.layoutHisRepo.update(layoutHistOrigin);
		// データベース更新[明細書マスタカテゴリ.UPD-2] を実施する
		// データベース更新[明細書マスタ行.UPD-2] を実施する
		List<LayoutMasterCategory> categoriesNew = categoriesOrigin.stream().map(org -> {
			return LayoutMasterCategory.createFromDomain(org.getCompanyCode(), org.getStmtCode(), org.getCtAtr(),
					org.getCtgPos(), org.getHistoryId());
		}).collect(Collectors.toList());
		this.categoryRepo.update(categoriesNew);
		// データベース更新[明細書マスタ行.UPD-2] を実施する
		List<LayoutMasterLine> linesNew = linesOrigin.stream().map(org -> {
			return LayoutMasterLine.createFromDomain(org.getCompanyCode(), org.getStmtCode(), org.getAutoLineId(),
					org.getCategoryAtr(), org.getLineDisplayAttribute(), org.getLinePosition(), org.getHistoryId());
		}).collect(Collectors.toList());
		this.lineRepo.update(linesNew);
		// データベース更新[明細書マスタ明細.UPD-2] を実施する
		List<LayoutMasterDetail> detailsNew = detailsOrigin.stream().map(org -> {
			return LayoutMasterDetail.createFromDomain(org.getCompanyCode(), org.getStmtCode(), org.getCategoryAtr(),
					org.getItemCode(), org.getAutoLineId(), org.getItemPosColumn(), org.getError(),
					org.getCalculationMethod(), org.getDistribute(), org.getDisplayAtr(), org.getAlarm(),
					org.getSumScopeAtr(), org.getSetOffItemCode(), org.getCommuteAtr(), org.getFormulaCode(),
					org.getPersonalWageCode(), org.getWageTableCode(), org.getCommonAmount(), org.getHistoryId());
		}).collect(Collectors.toList());
		this.detailRepo.update(detailsNew);
	}
}
