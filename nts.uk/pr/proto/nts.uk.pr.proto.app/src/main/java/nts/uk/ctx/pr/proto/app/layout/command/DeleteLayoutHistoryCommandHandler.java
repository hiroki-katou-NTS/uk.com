package nts.uk.ctx.pr.proto.app.layout.command;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;

@RequestScoped
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
		//データベース削除[明細書マスタ.DEL-1]を実施する
		this.layoutRepository.remove(context.getCommand().getCompanyCode(),
				context.getCommand().getStmtCode(),
				context.getCommand().getStartYM());
		//1件成功した場合以外の場合
		//bo sung them
		
		//データベース削除[明細書マスタカテゴリ.DEL-2] を実施する
		List<LayoutMasterCategory> lstCategories = this.categoryRepository.getCategories(context.getCommand().getCompanyCode(),
				context.getCommand().getStmtCode(), 
				context.getCommand().getStartYM());
		this.categoryRepository.removeAllCategory(lstCategories);
		//1件成功した場合以外の場合
		//bo sung them
		
		//データベース削除[明細書マスタ行.DEL-2] を実施する
		List<LayoutMasterLine> lstLineOfCategory = this.lineRepository.getLines(context.getCommand().getCompanyCode(), 
				context.getCommand().getStmtCode(), 
				context.getCommand().getStartYM());
		this.lineRepository.remove(lstLineOfCategory);
		
		
		//データベース削除[明細書マスタ明細.DEｌ-1] を実施する
		List<LayoutMasterDetail> lstDetails = this.detailRepository.getDetails(context.getCommand().getCompanyCode(),
				context.getCommand().getStmtCode(),
				context.getCommand().getStartYM());				
		this.detailRepository.remove(lstDetails);
		
		//データベース更新[明細書マスタ.UPD-2] を実施する
		
	}

}
