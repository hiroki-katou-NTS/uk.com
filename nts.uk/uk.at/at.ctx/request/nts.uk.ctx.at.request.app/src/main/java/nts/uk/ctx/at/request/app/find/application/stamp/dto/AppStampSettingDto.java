package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@AllArgsConstructor
@NoArgsConstructor
//打刻申請設定
public class AppStampSettingDto {
	/**
	 * 会社ID
	 */
	public String companyID;
	
	/**
	 * 取消の機能の使用する
	 */
	public Integer useCancelFunction;
	
	/**
	 * 各種類の設定
	 */
	public List<SettingForEachTypeDto> settingForEachTypeLst;
	
	/**
	 * 外出種類の表示制御
	 */
	public List<GoOutTypeDispControlDto> goOutTypeDispControl;
	
	/**
	 * 場所の選択を利用する
	 */
	public Integer useLocationSelection;

	/** 職場の選択を利用する */
	public Integer wkpDisAtr;
	
	public static AppStampSettingDto fromDomain(AppStampSetting appStampSetting) {
		return new AppStampSettingDto(
				appStampSetting.getCompanyID(),
				appStampSetting.getUseCancelFunction().value,
				!CollectionUtil.isEmpty(appStampSetting.getSettingForEachTypeLst())
						? appStampSetting.getSettingForEachTypeLst().stream()
								.map(x -> SettingForEachTypeDto.fromDomain(x)).collect(Collectors.toList())
						: Collections.emptyList(),
				!CollectionUtil.isEmpty(appStampSetting.getGoOutTypeDispControl())
						? appStampSetting.getGoOutTypeDispControl().stream()
								.map(x -> GoOutTypeDispControlDto.fromDomain(x)).collect(Collectors.toList())
						: Collections.emptyList(),
				appStampSetting.getUseLocationSelection() != null ? appStampSetting.getUseLocationSelection().value : null,
				appStampSetting.getWkpDisAtr() != null ? appStampSetting.getWkpDisAtr().value : null);
	}
	
	public AppStampSetting toDomain() {
		return new AppStampSetting(companyID,
				EnumAdaptor.valueOf(useCancelFunction, UseDivision.class),
				!CollectionUtil.isEmpty(settingForEachTypeLst)
						? settingForEachTypeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList(),
				!CollectionUtil.isEmpty(goOutTypeDispControl)
						? goOutTypeDispControl.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList(),
				EnumAdaptor.valueOf(useLocationSelection, NotUseAtr.class),
				EnumAdaptor.valueOf(wkpDisAtr, NotUseAtr.class));
	}
	
	
}
