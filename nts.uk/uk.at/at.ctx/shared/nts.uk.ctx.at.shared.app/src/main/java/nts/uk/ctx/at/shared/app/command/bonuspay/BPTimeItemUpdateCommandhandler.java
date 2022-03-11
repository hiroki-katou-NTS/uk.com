package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimeItemFinder;
import nts.uk.ctx.at.shared.app.find.bonuspay.item.BPItemArt;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author kansuke_shinoda
 *
 *02_決定ボタン押下時処理
 */
@Stateless
public class BPTimeItemUpdateCommandhandler extends CommandHandler<List<BPTimeItemUpdateCommand>> {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;
	@Inject
	private BPTimeItemFinder bpTimeItemFinder;
	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemUpdateCommand>> context) {

		//ドメインモデル「加給時間項目」を更新する

		//ドメインモデル「特定加給時間項目」を更新する

		List<BPTimeItemUpdateCommand> lstBpTimeItemUpdateCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		bpTimeItemRepository.updateListBonusPayTimeItem(lstBpTimeItemUpdateCommand.stream().map(c -> toBonusPayTimeItemDomain(c,companyId)).collect(Collectors.toList()));
	}
	private BonusPayTimeItem toBonusPayTimeItemDomain(BPTimeItemUpdateCommand bpTimeItemUpdateCommand,String companyId ) {
		return BonusPayTimeItem.createFromJavaType(companyId,
				bpTimeItemUpdateCommand.getUseAtr(),
				bpTimeItemUpdateCommand.getTimeItemName(), bpTimeItemUpdateCommand.getTimeItemNo(),
				bpTimeItemUpdateCommand.getTimeItemTypeAtr());
	}

	//加給時間帯と特定加給時間帯の使用区分をチェックする
	public void checkUseArt(BPItemArt art){
		boolean checkUseExist = false;

		for (Boolean useArt : art.lstUseArt) {
			if(useArt){
				checkUseExist=useArt;
			}
		}

		for (Boolean useArt : art.lstUseSpecArt) {
			if(useArt){
				checkUseExist=useArt;
			}
		}
		if(!checkUseExist){
			throw new BusinessException("Msg_131");
		}

	}

}
