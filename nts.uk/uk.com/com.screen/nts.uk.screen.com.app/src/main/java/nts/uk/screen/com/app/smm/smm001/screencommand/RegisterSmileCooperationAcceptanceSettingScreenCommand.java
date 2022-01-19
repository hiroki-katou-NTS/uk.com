package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public List<SmileCooperationAcceptanceSetting> convertScreenCommandToListSetting() {
		List<SmileCooperationAcceptanceSetting> list = new ArrayList<>();
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.ORGANIZATION_INFORMATION, checkedOrganizationInformation,
				selectedOrganizationInformation));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.BASIC_PERSONNEL_INFORMATION, checkedBasicPersonnelInformation,
				selectedOrganizationInformation));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.JOB_STRUCTURE_INFORMATION, checkedJobStructureInformation,
				selectedJobStructureInformation));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.ADDRESS_INFORMATION, checkedAddressInformation,
				selectedAddressInformation));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.LEAVE_INFORMATION, checkedLeaveInformation,
				selectedLeaveInformation));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.AFFILIATED_MASTER, checkedAffiliatedMaster,
				selectedAffiliatedMaster));
		list.add(this.createNewObjectSmileCooperationAcceptanceSetting(
				SmileCooperationAcceptanceItem.EMPLOYEE_MASTER, checkedEmployeeMaster,
				selectedEmployeeMaster));
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
