package nts.uk.ctx.at.schedule.pubimp.budget.premium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItem;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.ctx.at.schedule.pub.budget.premium.PersonCostSettingExport;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemDto;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class PremiumItemPubImpl implements PremiumItemPub {

	@Inject
	private PremiumItemRepository premiumItemRepository;
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;

	@Override
	public List<PremiumItemDto> findByCompanyID(String companyID) {
		return premiumItemRepository.findByCompanyID(companyID).stream()
				.map(x -> new PremiumItemDto(
						x.getCompanyID(),
						x.getDisplayNumber(),
						x.getName().v(),
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}

	@Override
	public List<PremiumItemDto> findByCompanyIDAndDisplayNumber(String companyID, List<Integer> displayNumbers) {
		return premiumItemRepository.findByCompanyIDAndDisplayNumber(companyID, displayNumbers).stream()
				.map(x -> new PremiumItemDto(
						x.getCompanyID(),
						x.getDisplayNumber(),
						x.getName().v(),
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}

	@Override
	public List<PersonCostSettingExport> getPersonCostSetting(String companyID, GeneralDate date) {
		List<PersonCostSettingExport> result = new ArrayList<>();
		// ドメインモデル「割増時間項目」を取得する
		List<PremiumItem> preiumItems = this.premiumItemRepository.findAllIsUse(companyID);
		if(CollectionUtil.isEmpty(preiumItems)){
			return result;
		}
		// 取得したドメインモデル「割増時間項目」のデータ分を処理をループする
		for(PremiumItem preium : preiumItems){
			List<PersonCostCalculation> personCosts = this.personCostCalculationRepository.findByCompanyIDAndDisplayNumber(companyID, date);
			if(personCosts != null){
				for(PersonCostCalculation person : personCosts){
					for(PremiumSetting premiumSetting : person.getPremiumSettings()){
						if(premiumSetting.getDisplayNumber().equals(preium.getDisplayNumber())){
							PersonCostSettingExport export = new PersonCostSettingExport(premiumSetting.getRate().v(), premiumSetting.getDisplayNumber(), premiumSetting.getAttendanceItems());
							result.add(export);
						}
					}
				}
			}
		}
		return result;
	}
}
