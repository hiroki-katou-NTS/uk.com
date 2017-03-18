package nts.uk.ctx.pr.core.app.find.rule.employment.averagepay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.app.find.rule.employment.averagepay.dto.AveragePayDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
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
@RequestScoped
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
	 * 
	 * @return
	 */
	
	public AveragePayDto findByCompanyCode() {
		String companyCode = AppContexts.user().companyCode();
		Optional<AveragePay> data = this.averagePayRepository.findByCompanyCode(companyCode);
		if (!data.isPresent()) {
			return null;
		}
		AveragePay x = data.get();
		return new AveragePayDto(
				x.getCompanyCode().v(),
				x.getAttendDayGettingSet().value, 
				x.getExceptionPayRate().v(), 
				x.getRoundTimingSet().value, 
				x.getRoundDigitSet().value);
	}
	
	public List<ItemMasterDto> findByItemSalary() {
		String companyCode = AppContexts.user().companyCode();
		List<ItemSalary> itemSalarys = this.itemSalaryRespository.findAll(companyCode);
		if (itemSalarys.isEmpty()){
			return null;
		}
		ArrayList<ItemMasterDto> itemMasterDtos = new ArrayList<ItemMasterDto>();
		itemSalarys.forEach(itemSalary -> {
			Optional<ItemMaster> itemMaster = this.itemMasterRepository.find(companyCode, 0, itemSalary.getItemCode().v());
			if(itemMaster.isPresent()){
				itemMasterDtos.add(ItemMasterDto.fromDomain(itemMaster.get()));
			}
		});
		
		return itemMasterDtos;
	}
	
	public List<ItemMasterDto> findByItemAttend() {
		String companyCode = AppContexts.user().companyCode();
		List<ItemAttend> itemAttends = this.itemAttendRespository.findAll(companyCode);
		if (itemAttends.isEmpty()){
			return null;
		}
		ArrayList<ItemMasterDto> itemMasterDtos = new ArrayList<ItemMasterDto>();
		itemAttends.forEach(itemSalary -> {
			Optional<ItemMaster> itemMaster = this.itemMasterRepository.find(companyCode, 2, itemSalary.getItemCode().v());
			itemMaster.ifPresent(x -> {
				itemMasterDtos.add(ItemMasterDto.fromDomain(x));
			});
		});
		return itemMasterDtos;
	}
}
