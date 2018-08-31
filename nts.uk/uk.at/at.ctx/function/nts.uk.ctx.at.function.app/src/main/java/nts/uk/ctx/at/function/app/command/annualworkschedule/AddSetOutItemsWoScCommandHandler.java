package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.annualworkschedule.AnnualWorkScheduleService;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand> {

	@Inject
	private SetOutItemsWoScRepository repository;

	@Inject
	private AnnualWorkScheduleService domainService;

	/**
	 * 36協定時間
	 */
	private static final String CD_36_AGREEMENT_TIME = "01";

	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
		String companyId = AppContexts.user().companyId();
		SetOutItemsWoScCommand addCommand = context.getCommand();
		if(domainService.checkDuplicateCode(addCommand.getCd())) {
			throw new BusinessException("Msg_3");
		} 
		int[] itemOutCd = {1};
		List<ItemOutTblBook> listItemOutTblBook = addCommand.getListItemOutput().stream()
			.map(m -> {
				String itemOutCdStr = m.isItem36AgreementTime()? CD_36_AGREEMENT_TIME :
															StringUtil.padLeft(String.valueOf(++itemOutCd[0]), 2, '0');
				return ItemOutTblBook.createFromJavaType(companyId,
				addCommand.getCd(), //年間勤務表(36チェックリスト)の出力条件.コード
				itemOutCdStr,       //帳表に出力する項目.コード auto increment
				m.getSortBy(),
				m.getHeadingName(), m.isUseClass(), m.getValOutFormat(),
				//list 項目の算出式
				m.getListOperationSetting().stream()
				.map(os -> CalcFormulaItem.createFromJavaType(companyId,
						addCommand.getCd(), //年間勤務表(36チェックリスト)の出力条件.コード
						itemOutCdStr,       //帳表に出力する項目.コード
						os.getAttendanceItemId(), os.getOperation())).collect(Collectors.toList()));
			}).collect(Collectors.toList());

		repository.add(SetOutItemsWoSc.createFromJavaType(companyId, addCommand.getCd(),
														  addCommand.getName(),
														  addCommand.isOutNumExceedTime36Agr(),
														  addCommand.getDisplayFormat(),
														  addCommand.getPrintForm(),
														  listItemOutTblBook));
	}
}
