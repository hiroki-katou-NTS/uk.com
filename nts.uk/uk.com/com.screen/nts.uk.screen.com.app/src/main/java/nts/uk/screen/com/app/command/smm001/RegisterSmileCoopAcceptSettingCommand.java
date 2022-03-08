package nts.uk.screen.com.app.command.smm001;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceClassification;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceItem;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;

@Getter
public class RegisterSmileCoopAcceptSettingCommand {
	private Integer checkedOrganizationInformation;
	
	private Integer checkedBasicPersonnelInformation;
	
	private Integer checkedJobStructureInformation;
	
	private Integer checkedAddressInformation;
	
	private Integer checkedLeaveInformation;
	
	private Integer checkedAffiliatedMaster;
	
	private Integer checkedEmployeeMaster;
	
	private String selectedOrganizationInformation;
	
	private String selectedBasicPersonnelInformation;
	
	private String selectedJobStructureInformation;
	
	private String selectedAddressInformation;
	
	private String selectedLeaveInformation;
	
	private String selectedAffiliatedMaster;
	
	private String selectedEmployeeMaster;
	
	private List<Integer> listCheckedCheckbox;
	
	private List<String> listSelectedComboboxValue;

	public List<SmileCooperationAcceptanceSetting> convertScreenCommandToListSetting() {
		listCheckedCheckbox = Arrays.asList(checkedOrganizationInformation, checkedBasicPersonnelInformation,
				checkedJobStructureInformation, checkedAddressInformation, checkedLeaveInformation,
				checkedAffiliatedMaster, checkedEmployeeMaster);

		listSelectedComboboxValue = Arrays.asList(selectedOrganizationInformation, selectedBasicPersonnelInformation,
				selectedJobStructureInformation, selectedAddressInformation, selectedLeaveInformation,
				selectedAffiliatedMaster, selectedEmployeeMaster);

		List<SmileCooperationAcceptanceSetting> smileCooperationAcceptanceSettings = new ArrayList<>();
		IntStream.range(0, SmileCooperationAcceptanceItem.lookup.size()).forEach(
				index -> smileCooperationAcceptanceSettings.add(this.createNewObjectSmileCooperationAcceptanceSetting(
						SmileCooperationAcceptanceItem.lookup.get(index), listCheckedCheckbox.get(index),
						listSelectedComboboxValue.get(index))));
		return smileCooperationAcceptanceSettings;
	}

	public SmileCooperationAcceptanceSetting createNewObjectSmileCooperationAcceptanceSetting(
			SmileCooperationAcceptanceItem item, Integer checkedItem, String selectedCode) {
		return new SmileCooperationAcceptanceSetting(item,
				EnumAdaptor.valueOf(checkedItem, SmileCooperationAcceptanceClassification.class),
				selectedCode == null ? Optional.empty()
						: Optional.of(new ExternalAcceptanceConditionCode(selectedCode)));
	}
}
