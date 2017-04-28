package nts.uk.ctx.sys.portal.app.mypage.setting.find;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;

/**
 * The Class TopPagePartUseSettingDto.
 */
@Data
public class TopPagePartUseSettingDto {

	/** The company id. */
	public String companyId;

	/** The part item code. */
	public String partItemCode;

	/** The part item name. */
	public String partItemName;

	/** The use division. */
	public Integer useDivision;

	/** The part type. */
	public TopPagePartType partType;

	/**
	 * From domain.
	 *
	 * @param topPagePartSettingItem the top page part setting item
	 * @return the top page part use setting dto
	 */
	public static TopPagePartUseSettingDto fromDomain(TopPagePartUseSetting topPagePartSettingItem) {
		TopPagePartUseSettingDto topPagePartSettingItemDto = new TopPagePartUseSettingDto();
		topPagePartSettingItemDto.partItemCode = topPagePartSettingItem.getTopPagePartCode().toString();
		topPagePartSettingItemDto.partItemName = topPagePartSettingItem.getTopPagePartName().toString();
		topPagePartSettingItemDto.useDivision = topPagePartSettingItem.getUseDivision().value;
		topPagePartSettingItemDto.partType = topPagePartSettingItem.getTopPagePartType();
		return topPagePartSettingItemDto;
	}
}
