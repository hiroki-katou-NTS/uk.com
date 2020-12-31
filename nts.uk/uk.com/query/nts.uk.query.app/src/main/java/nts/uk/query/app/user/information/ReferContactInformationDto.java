package nts.uk.query.app.user.information;

import lombok.Builder;
import lombok.Data;

/**
 * UKDesign.UniversalK.共通.CDL_共通ダイアログ.CDL010_連絡先の参照.メニュー別OCD.連絡先情報の参照
 * 
 * @author nws-ducnt
 *
 */
@Builder
@Data
public class ReferContactInformationDto {
	/**
	 * 職位名
	 */
	private String jobTitleName;
	/**
	 * 職場名
	 */
	private String workplaceName;
	/**
	 * 分類名
	 */
	private String classificationName;
	/**
	 * 雇用名
	 */
	private String employmentName;
	/**
	 * ビジネスネーム
	 */
	private String businessName;
	/**
	 * 連絡先情報DTO
	 */
	private ContactInformationDTO contactInformation;

}
