/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.Optional;

import lombok.Value;

/**
 * @author laitv
 * 個人条件
 */

@Value
public class PersonalConditionsDto {
	
	public String sid; // 社員ID
	Optional<String> teamName; // チーム名
	Optional<String> rankName; // ランク名
	Optional<Integer> licenseClassification; //免許区分 ---> class LicenseClassification
	/*
	 * NURSE(0, "看護師"),
	 * Associate nurse NURSE_ASSOCIATE(1, "准看護師"),
	 * Nursing assistant NURSE_ASSIST(2, "看護補助者");
	 */
}
