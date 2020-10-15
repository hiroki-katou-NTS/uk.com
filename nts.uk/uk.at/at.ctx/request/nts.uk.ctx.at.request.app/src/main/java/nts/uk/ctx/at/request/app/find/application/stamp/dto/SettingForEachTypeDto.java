package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppCommentSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.SettingForEachType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.StampAtr;
@AllArgsConstructor
@NoArgsConstructor
public class SettingForEachTypeDto {
	/**
	 * 下部コメント
	 */
	public AppCommentSetDto bottomComment;
	
	/**
	 * 上部コメント
	 */
	public AppCommentSetDto topComment;
	
	/**
	 * 打刻分類
	 */
	public Integer stampAtr;
	
	public static SettingForEachTypeDto fromDomain(SettingForEachType settingForEachType) {
		return new SettingForEachTypeDto(
				AppCommentSetDto.fromDomain(settingForEachType.getBottomComment()),
				AppCommentSetDto.fromDomain(settingForEachType.getTopComment()),
				settingForEachType.getStampAtr().value);
	}
	
	public SettingForEachType toDomain() {
		return new SettingForEachType(
				EnumAdaptor.valueOf(stampAtr, StampAtr.class),
				topComment.toDomain(),
				bottomComment.toDomain());
	}
}
