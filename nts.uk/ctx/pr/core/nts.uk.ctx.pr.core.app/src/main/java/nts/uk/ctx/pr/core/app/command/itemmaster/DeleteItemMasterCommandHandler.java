package nts.uk.ctx.pr.core.app.command.itemmaster;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@Stateless
@Transactional
public class DeleteItemMasterCommandHandler extends CommandHandler<DeleteItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;
	@Inject
	private ItemSalaryRespository itemSalaryRespository;
	@Inject
	private ItemSalaryPeriodRepository itemSalaryPeriodRepository;
	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepository;
	@Inject
	private ItemDeductRespository itemDeductRespository;
	@Inject
	private ItemDeductPeriodRepository itemDeductPeriodRepository;
	@Inject
	private ItemDeductBDRepository itemDeductBDRepository;
	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemMasterCommand> context) {

		val companyCode = AppContexts.user().companyCode();
		val categoryAtr = context.getCommand().getCategoryAtr();
		val itemCode = context.getCommand().getItemCode();
		if (!this.itemMasterRepository.find(companyCode, categoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemMasterRepository.remove(companyCode, categoryAtr, itemCode);
		// remove all sub item linked with item master through the item code
		switch (categoryAtr) {
		case 0:
			// if item Category 支給
			deleteItemSalary(companyCode, context);
			break;
		case 1:
			// 控除
			deleteItemDeduct(companyCode, context);
			break;
		case 2:
			// 勤怠
			deleteItemAttend(companyCode, context);
			break;
		}
	}

	private void deleteItemSalary(String companyCode, CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		if (!this.itemSalaryRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemSalaryRespository.delete(companyCode, itemCode);
		if (this.itemSalaryPeriodRepository.find(companyCode, itemCode).isPresent())
		this.itemSalaryPeriodRepository.delete(companyCode, itemCode);
		// Then remove itemBD
		List<ItemSalaryBD> itemBDList = this.itemSalaryBDRepository.findAll(companyCode, itemCode);
		for (ItemSalaryBD itemBD : itemBDList) {
			if (!this.itemSalaryBDRepository.find(companyCode, itemCode, itemBD.getItemBreakdownCode().v()).isPresent())
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
			this.itemSalaryBDRepository.delete(companyCode, itemBD.getItemCode().v(),
					itemBD.getItemBreakdownCode().v());
		}
	}

	private void deleteItemDeduct(String companyCode, CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		if (!this.itemDeductRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemDeductRespository.delete(companyCode, itemCode);
		if (this.itemDeductPeriodRepository.find(companyCode, itemCode).isPresent())
		this.itemDeductPeriodRepository.delete(companyCode, itemCode);
		List<ItemDeductBD> itemBDList = this.itemDeductBDRepository.findAll(companyCode, itemCode);
		for (ItemDeductBD itemBD : itemBDList) {
			if (!this.itemDeductBDRepository.find(companyCode, itemCode, itemBD.getItemBreakdownCode().v()).isPresent())
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
			this.itemDeductBDRepository.delete(companyCode, itemBD.getItemCode().v(),
					itemBD.getItemBreakdownCode().v());
		}
	}

	private void deleteItemAttend(String companyCode, CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		if (!this.itemAttendRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemAttendRespository.delete(companyCode, itemCode);

	}

}
