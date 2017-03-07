package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * DeleteResidentalTaxCommandHandler
 * 
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class DeleteResidentialTaxCommandHandler extends CommandHandler<DeleteResidentialTaxCommand> {
	@Inject
	private ResidentialTaxRepository resiTaxRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteResidentialTaxCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		DeleteResidentialTaxCommand delete = context.getCommand();
		List<ResidentialTax> allResidential = this.resiTaxRepository.getAllResidentialTax(companyCode,
				delete.getResiTaxCode().toString(), delete.getResiTaxReportCode().toString());
		//17.住民税納付先の一括削除_削除時チェック処理 SEL_2 if result >=1 throw error  selected obj is used -> dont' delete this obj
		if (allResidential.isEmpty()) {
			this.resiTaxRepository.delele(companyCode, delete.getResiTaxCode());
		}else{
			
			throw new BusinessException(new RawErrorMessage("選択された  住民税納付先マスタ object  は使用されているため削除できません。"));
		}
	}

}
