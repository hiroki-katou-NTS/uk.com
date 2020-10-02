/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import lombok.Value;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonalCondition;

/**
 * @author laitv
 * 個人条件
 */

@Value
public class PersonalConditionsDto {
	
	public String sid; // 社員ID
	String teamName; // チーム名
	String rankName; // ランク名
	String licenseClassification; //免許区分 ---> class LicenseClassification
	/*
	 * NURSE(0, "看護師"),
	 * Associate nurse NURSE_ASSOCIATE(1, "准看護師"),
	 * Nursing assistant NURSE_ASSIST(2, "看護補助者");
	 */
	public PersonalConditionsDto(PersonalCondition domain) {
		this.sid = domain.getEmpId();
		this.teamName = domain.getTeamName().isPresent() ? domain.getTeamName().get() : null;
		this.rankName = domain.getOptRankSymbol().isPresent() ? domain.getOptRankSymbol().get() : null;
		if (domain.getOptLicenseClassification().isPresent()) {
			switch (domain.getOptLicenseClassification().get().value) {
			case 0:
				this.licenseClassification = I18NText.getText("KSU001_4020");
				break;
			case 1:
				this.licenseClassification = I18NText.getText("KSU001_4021");
				break;
			case 2:
				this.licenseClassification = I18NText.getText("KSU001_4022");
				break;
			default:
				this.licenseClassification = "";
				break;
			}
		}else{
			this.licenseClassification = "";
		}
	}
	
	
}
