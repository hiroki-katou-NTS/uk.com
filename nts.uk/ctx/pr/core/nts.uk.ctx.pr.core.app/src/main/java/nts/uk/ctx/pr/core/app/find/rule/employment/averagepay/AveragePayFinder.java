package nts.uk.ctx.pr.core.app.find.rule.employment.averagepay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.app.find.rule.employment.averagepay.dto.AveragePayDto;
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePay;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chinhbv
 *
 */
@Stateless
public class AveragePayFinder {

	@Inject
	private AveragePayRepository averagePayRepository;
	
	@Inject
	private ItemMasterRepository itemMasterRepository;
	
	@Inject
	private ItemSalaryRespository itemSalaryRespository;
	
	@Inject
	private ItemAttendRespository itemAttendRespository;
	
	/**
	 * find average pay by company code
	 * @return average pay dto
	 */
	public AveragePayDto findByCompanyCode() {
		String companyCode = AppContexts.user().companyCode();
		
		// QAPMT_AVE_PAY.SEL_1
		Optional<AveragePay> data = this.averagePayRepository.findByCompanyCode(companyCode);
		if (!data.isPresent()) {
			return null;
		}
		AveragePay x = data.get();
		return new AveragePayDto(
				x.getCompanyCode(),
				x.getAttendDayGettingSet().value, 
				x.getExceptionPayRate().v(), 
				x.getRoundTimingSet().value, 
				x.getRoundDigitSet().value);
	}
	
	/**
	 * find item master by salary item code and category = 0
	 * @return list item master
	 */
	public List<ItemMasterDto> findByItemSalary() {
		String companyCode = AppContexts.user().companyCode();
		
		// QCAMT_ITEM_SALARY.SEL_3
		List<ItemSalary> itemSalarys = this.itemSalaryRespository.findAll(companyCode, AvePayAtr.Object);
		if (itemSalarys.isEmpty()){
			return null;
		}
		
		// QCAMT_ITEM.SEL_5
		List<String> itemSalaryCodeList = itemSalarys.stream().map(x -> x.getItemCd().v()).collect(Collectors.toList());
		List<ItemMasterDto> itemMasterDtos = this.itemMasterRepository.findAll(companyCode, 0, itemSalaryCodeList)
				.stream().map(x -> ItemMasterDto.fromDomain(x)).collect(Collectors.toList());
		return itemMasterDtos;
	}
	
	/**
	 * find item master by attend item code and category = 2
	 * @return list item master
	 */
	public List<ItemMasterDto> findByItemAttend() {
		String companyCode = AppContexts.user().companyCode();
		
		// QCAMT_ITEM_ATTEND.SEL_4
		List<ItemAttend> itemAttends = this.itemAttendRespository.findAll(companyCode, AvePayAtr.Object);
		if (itemAttends.isEmpty()){
			return null;
		}
		
		// QCAMT_ITEM.SEL_5
		List<String> itemAttendCodeList = itemAttends.stream().map(y -> y.getItemCD().v()).collect(Collectors.toList());
		List<ItemMasterDto> itemMasterDtos = this.itemMasterRepository.findAll(companyCode, 2, itemAttendCodeList)
				.stream().map(x -> ItemMasterDto.fromDomain(x)).collect(Collectors.toList());
		return itemMasterDtos;
	}
}
