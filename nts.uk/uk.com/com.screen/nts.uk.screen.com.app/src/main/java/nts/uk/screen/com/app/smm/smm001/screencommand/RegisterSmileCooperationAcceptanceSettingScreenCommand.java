package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import nts.arc.enums.EnumAdaptor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceClassification;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceItem;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;

@Getter
public class RegisterSmileCooperationAcceptanceSettingScreenCommand {
	private Integer paymentCode;

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

	private List<Integer> listCheckedCheckbox = Arrays.asList(checkedOrganizationInformation,
			checkedBasicPersonnelInformation, checkedJobStructureInformation, checkedAddressInformation,
			checkedLeaveInformation, checkedAffiliatedMaster, checkedEmployeeMaster);

	private List<String> listSelectedComboboxValue = Arrays.asList(selectedOrganizationInformation,
			selectedBasicPersonnelInformation, selectedJobStructureInformation, selectedAddressInformation,
			selectedLeaveInformation, selectedAffiliatedMaster, selectedEmployeeMaster);

	public List<SmileCooperationAcceptanceSetting> convertScreenCommandToListSetting() {
		List<SmileCooperationAcceptanceSetting> list = new ArrayList<>();
		IntStream.range(0, SmileCooperationAcceptanceItem.lookup.size())
				.forEach(index -> list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
						SmileCooperationAcceptanceItem.lookup.get(index), listCheckedCheckbox.get(index),
						listSelectedComboboxValue.get(index))));
		return list;
	}

	public SmileCooperationAcceptanceSetting createNewObjectSmileCooperationAcceptanceSetting(
			SmileCooperationAcceptanceItem item, Integer checkedItem, String selectedCode) {
		return new SmileCooperationAcceptanceSetting(item,
				EnumAdaptor.valueOf(checkedItem, SmileCooperationAcceptanceClassification.class),
				selectedCode == null ? Optional.empty()
						: Optional.of(new ExternalAcceptanceConditionCode(selectedOrganizationInformation)));
	}
}
