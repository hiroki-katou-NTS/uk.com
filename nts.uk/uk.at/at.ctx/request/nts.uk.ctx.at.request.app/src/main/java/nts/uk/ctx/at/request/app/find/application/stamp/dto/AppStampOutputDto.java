package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;

@AllArgsConstructor
@NoArgsConstructor
//打刻申請起動時の表示情報
public class AppStampOutputDto {
//	打刻申請設定
	public AppStampSettingDto appStampSetting;
	
//	申請表示情報
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
//	レコーダイメージ申請
	public AppRecordImageDto appRecordImage;
	
//	打刻エラー情報
	public List<ErrorStampInfoDto> errorListOptional;
	
//	打刻申請
	public AppStampDto appStampOptional;
	
//	打刻申請の反映
	public AppStampReflectDto appStampReflectOptional;
	
//	臨時勤務利用
	public Boolean useTemporary;
	
	
	public static AppStampOutputDto fromDomain(AppStampOutput appStampOutput) {
		return new AppStampOutputDto(
				AppStampSettingDto.fromDomain(appStampOutput.getAppStampSetting()),
				AppDispInfoStartupDto.fromDomain(appStampOutput.getAppDispInfoStartupOutput()),
				appStampOutput.getAppRecordImage().isPresent()
						? AppRecordImageDto.fromDomain(appStampOutput.getAppRecordImage().get())
						: null,
				appStampOutput.getErrorListOptional().isPresent()
						? appStampOutput.getErrorListOptional().get().stream().map(x -> ErrorStampInfoDto.fromDomain(x))
								.collect(Collectors.toList())

						: Collections.emptyList(),
				appStampOutput.getAppStampOptional().isPresent()
						? AppStampDto.fromDomain(appStampOutput.getAppStampOptional().get())
						: null,
				appStampOutput.getAppStampReflectOptional().isPresent()
						? AppStampReflectDto.fromDomain(appStampOutput.getAppStampReflectOptional().get())
						: null,
				appStampOutput.getUseTemporary().isPresent() ? appStampOutput.getUseTemporary().get() : null);
	}
	
	
	public AppStampOutput toDomain() {
		return new AppStampOutput(
				appStampSetting.toDomain(),
				appDispInfoStartupOutput.toDomain(),
				appRecordImage != null ? Optional.of(appRecordImage.toDomain()) : Optional.empty(),
				!CollectionUtil.isEmpty(errorListOptional) ? 
						Optional.of(errorListOptional.stream().map(x -> x.toDomain()).collect(Collectors.toList()))
						: Optional.empty(),
				appStampOptional != null ? Optional.of(appStampOptional.toDomain()) : Optional.empty(),
				appStampReflectOptional != null ? Optional.of(appStampReflectOptional.toDomain()) : Optional.empty(),
				useTemporary != null ? Optional.of(useTemporary) : Optional.empty());
	}
}
