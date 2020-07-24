/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import lombok.Value;
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
	Integer licenseClassification; //免許区分 ---> class LicenseClassification
	/*
	 * NURSE(0, "看護師"),
	 * Associate nurse NURSE_ASSOCIATE(1, "准看護師"),
	 * Nursing assistant NURSE_ASSIST(2, "看護補助者");
	 */
	public PersonalConditionsDto(PersonalCondition domain) {
		this.sid = domain.getEmpId();
		this.teamName = domain.getTeamName().isPresent() ? domain.getTeamName().get() : null;
		this.rankName = domain.getOptRankSymbol().isPresent() ? domain.getOptRankSymbol().get() : null;
		this.licenseClassification = domain.getOptLicenseClassification().isPresent() ? domain.getOptLicenseClassification().get().value : null;
	}
	
	
}
