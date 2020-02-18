package nts.uk.ctx.bs.employee.app.command.groupcommonmaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.CommonMasterItemCode;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.CommonMasterItemName;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterDomainService;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterItem;
import nts.uk.shr.com.context.AppContexts;

public class SaveCommonMasterCmdHandler extends CommandHandler<SaveCommonMasterCommand> {

	@Inject
	private GroupCommonMasterDomainService services;

	@Override
	protected void handle(CommandHandlerContext<SaveCommonMasterCommand> context) {
		// 画面情報を取得する(Get thông tin màn hình)

		SaveCommonMasterCommand cmd = context.getCommand();
		UpdateMasterItemCommand updateItem = cmd.getSaveItem();

		String contractCd = AppContexts.user().contractCode();

		List<GroupCommonMasterItem> domains = new ArrayList<GroupCommonMasterItem>();

		String commonMasterId = cmd.getCommonMasterId();

		String CommonMasterItemId = updateItem.getCommonMasterItemId() != null ? updateItem.getCommonMasterItemId()
				: null;
		//・ List <Add common master item>. Display order = MAX (List <common master item>. displayNumber) ++ 1
		int displayNumber = updateItem.getDisplayNumber() != null ? updateItem.getDisplayNumber()
				: (Collections
						.max(cmd.getListMasterItem(), Comparator.comparing(UpdateMasterItemCommand::getDisplayNumber))
						.getDisplayNumber() + 1);

		// vì add và update đều tạo domain nên viết chung cho gọn
		domains.add(new GroupCommonMasterItem(CommonMasterItemId,
				new CommonMasterItemCode(updateItem.getCommonMasterItemCode()),
				new CommonMasterItemName(updateItem.getCommonMasterItemName()), displayNumber,
				updateItem.getUsageStartDate(), updateItem.getUsageEndDate()));

		if (cmd.isNewMode()) {
			// 画面モード = 新規モード (Screen mode = New mode)
			this.services.addCommonMasterItem(contractCd, commonMasterId, domains);
		} else {
			// bước kiểm tra data đã thay đổi chưa được làm ở dưới client
			// 画面モード = 更新モード (Screen mode = Update mode)
			this.services.updateCommonMasterItem(contractCd, commonMasterId, domains);
		}
		// phần get data để trả về làm dưới client
	}

}