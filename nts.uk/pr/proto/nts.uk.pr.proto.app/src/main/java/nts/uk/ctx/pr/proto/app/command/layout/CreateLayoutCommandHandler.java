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
import nts.gul.util.Range;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.enums.UseOrNot;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.CategoryPosition;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.CalculationMethod;
import nts.uk.ctx.pr.proto.dom.layout.detail.ItemPosColumn;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.RangeChecker;
import nts.uk.ctx.pr.proto.dom.layout.detail.SumScopeAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.Distribute;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.DistributeSet;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.DistributeWay;
import nts.uk.ctx.pr.proto.dom.layout.line.AutoLineId;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LineDispAtr;
import nts.uk.ctx.pr.proto.dom.layout.line.LinePosition;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.wagename.PersonalWageCode;
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
			//作成方法で「既存のレイアウトをコピーして作成」を選択した場合
			if (command.isCheckCopy()) {
				copyNewData(command, companyCode);
			}
			//作成方法で「新規に作成」を選択した場合
			else{
				
				createNewData(layout, companyCode);
			}
		
		}
		catch(Exception ex){
			throw ex;
		}
	}
	
	public void createNewData(LayoutMaster layout, String companyCode){
		//データベース登録[明細書マスタカテゴリ.INS-1] を実施する
		LayoutMasterCategory category = new LayoutMasterCategory(new CompanyCode(companyCode),
				layout.getStartYM(), 
				layout.getStmtCode(),
				CategoryAtr.PAYMENT,
				layout.getEndYm(), 
				new CategoryPosition(1));
		this.categoryRepo.add(category);
		category = new LayoutMasterCategory(new CompanyCode(companyCode),
				layout.getStartYM(), 
				layout.getStmtCode(),
				CategoryAtr.DEDUCTION,
				layout.getEndYm(), 
				new CategoryPosition(2));
		this.categoryRepo.add(category);
		//データベース登録[明細書マスタ行.INS-1] を実施する
		createLineDefault(layout, companyCode, CategoryAtr.PAYMENT, "0", 1);
		createLineDefault(layout, companyCode, CategoryAtr.PAYMENT, "1", 2);
		createLineDefault(layout, companyCode, CategoryAtr.PAYMENT, "2", 3);
		createLineDefault(layout, companyCode, CategoryAtr.DEDUCTION, "3", 1);
		createLineDefault(layout, companyCode, CategoryAtr.DEDUCTION, "4", 2);
		//データベース登録[明細書マスタ明細.INS-1] を実施する
		//支給3項目
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.PAYMENT,
				"F001",
				"0",
				9,
				SumScopeAtr.EXCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.PAYMENT,
				"F002",
				"1",
				9,
				SumScopeAtr.EXCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.PAYMENT,
				"F003",
				"2",
				9,
				SumScopeAtr.EXCLUDED);
		
		//控除9項目
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F101",
				"3",
				1,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F102",
				"3",
				2,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F103",
				"3",
				3,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F104",
				"3",
				4,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F105",
				"3",
				5,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F106",
				"3",
				6,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F107",
				"3",
				7,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F108",
				"3",
				8,
				SumScopeAtr.INCLUDED);
		createDetailDefault(layout,
				companyCode,
				CategoryAtr.DEDUCTION,
				"F114",
				"4",
				9,
				SumScopeAtr.EXCLUDED);
	}
	private void createDetailDefault(LayoutMaster layout, 
			String companyCode,
			CategoryAtr cateAtr,
			String itemCode,
			String autoLineId,
			int itemPosColumn,
			SumScopeAtr sumAtr){
		RangeChecker alarm = new RangeChecker(UseOrNot.DO_NOT_USE, UseOrNot.DO_NOT_USE, Range.between(0,0));
		RangeChecker error = new RangeChecker(UseOrNot.DO_NOT_USE, UseOrNot.DO_NOT_USE, Range.between(0,0));
		Distribute distribute = new Distribute(DistributeWay.CALCULATED_PERCENTAGE, DistributeSet.NOT_PROPORTIONAL);
		LayoutMasterDetail detailData = new LayoutMasterDetail(new CompanyCode(companyCode),
				layout.getStmtCode(),
				layout.getStartYM(),
				layout.getEndYm(),
				cateAtr,
				new ItemCode(itemCode),
				new AutoLineId(autoLineId),
				new ItemPosColumn(itemPosColumn),
				alarm,
				CalculationMethod.MANUAL_ENTRY,
				distribute, 
				DisplayAtr.DISPLAY,
				error,
				sumAtr,
				new ItemCode("0000"),
				CommuteAtr.TRANSPORTATION_EQUIPMENT,
				new PersonalWageCode("00"));
		
		this.detailRepo.add(detailData);
	}
	
	private void createLineDefault(LayoutMaster layout, 
			String companyCode,
			CategoryAtr cateAtr,
			String autoLine,
			int linePos){
		LayoutMasterLine line = new LayoutMasterLine(new CompanyCode(companyCode),
				layout.getStartYM(),
				layout.getStmtCode(),
				layout.getEndYm(),
				new AutoLineId(autoLine),
				cateAtr,
				LineDispAtr.ENABLE,
				new LinePosition(linePos));
		this.lineRepo.add(line);
	}
	private void copyNewData(CreateLayoutCommand command, String companyCode) {
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
		List<LayoutMasterLine> linesOrigin = lineRepo.getLinesBefore(companyCode, command.getStmtCodeCopied(), 999912);
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
