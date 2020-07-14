package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDetailScreenInfoDto {
	
	/**
	 * 申請
	 */
	private ApplicationDto application;
	
	/**
	 * 承認ルートインスタンス
	 */
	private List<ApprovalPhaseStateForAppDto> approvalLst;
	
	/**
	 * 承認コメント
	 */
	private String authorComment;
	
	/**
	 * 利用者
	 */
	private int user; 
	
	/**
	 * ステータス
	 */
	private int reflectPlanState;
	
	/**
	 * 詳細画面モード
	 */
	private Integer outputMode;
	
	/**
	 * 承認できるフラグ
	 */
	private Boolean authorizableFlags;
	
	/**
	 * ログイン者の承認区分
	 */
	private Integer approvalATR;
	
	/**
	 * 代行期限フラグ
	 */
	private Boolean alternateExpiration;
	
	public static AppDetailScreenInfoDto fromDomain(AppDetailScreenInfo appDetailScreenInfo) {
		return new AppDetailScreenInfoDto(
				ApplicationDto.fromDomain(appDetailScreenInfo.getApplication()), 
				appDetailScreenInfo.getApprovalLst().stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), 
				appDetailScreenInfo.getAuthorComment(), 
				appDetailScreenInfo.getUser().value, 
				appDetailScreenInfo.getReflectPlanState().value, 
				appDetailScreenInfo.getOutputMode().value, 
				appDetailScreenInfo.getAuthorizableFlags().orElse(null), 
				appDetailScreenInfo.getApprovalATR().map(x -> x.value).orElse(null), 
				appDetailScreenInfo.getAlternateExpiration().orElse(null));
	}
	
	public AppDetailScreenInfo toDomain() {
		AppDetailScreenInfo appDetailScreenInfo = new AppDetailScreenInfo(
				application.toDomain(), 
				approvalLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				authorComment, 
				EnumAdaptor.valueOf(user, User.class), 
				EnumAdaptor.valueOf(reflectPlanState, ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(outputMode, OutputMode.class));
		if(authorizableFlags!=null) {
			appDetailScreenInfo.setAuthorizableFlags(Optional.of(authorizableFlags));
		}
		if(approvalATR!=null) {
			appDetailScreenInfo.setApprovalATR(Optional.of(EnumAdaptor.valueOf(approvalATR, ApprovalAtr.class)));
		}
		if(alternateExpiration!=null) {
			appDetailScreenInfo.setAlternateExpiration(Optional.of(alternateExpiration));
		}
		return appDetailScreenInfo;
	}
	
}
