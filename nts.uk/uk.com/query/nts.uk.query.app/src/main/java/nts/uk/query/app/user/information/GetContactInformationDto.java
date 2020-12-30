package nts.uk.query.app.user.information;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.ContactInformationDTO;

/**
 * UKDesign.UniversalK.共通.CDL_共通ダイアログ.CDL010_連絡先の参照.メニュー別OCD.連絡先情報の参照
 * 
 * @author nws-ducnt
 *
 */
@Builder
@Data
public class GetContactInformationDto {
	/**
	 * 職位名
	 */
	private String positionName;
	/**
	 * 職場名
	 */
	private String workplaceName;
	/**
	 * 分類名
	 */
	private String categoryName;
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
	private ContactInformationDTO contactInformationDTO;

}
