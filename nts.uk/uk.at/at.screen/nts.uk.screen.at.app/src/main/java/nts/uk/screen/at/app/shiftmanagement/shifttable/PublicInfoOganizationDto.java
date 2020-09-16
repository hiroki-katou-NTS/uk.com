package nts.uk.screen.at.app.shiftmanagement.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicInfoOganizationDto {
	/**
	 * 単位
	 */
	private int unit;

	/**
	 * 職場ID
	 */
	private String workplaceId;

	/**
	 * 職場グループID
	 */
	private String workplaceGroupId;
	/**
	 * 名称
	 */
	private String displayName;

	/**
	 * 登録した公開日
	 */
	private String publicDate;

	/**
	 * 登録した編集日
	 */
	private String editDate;
}
