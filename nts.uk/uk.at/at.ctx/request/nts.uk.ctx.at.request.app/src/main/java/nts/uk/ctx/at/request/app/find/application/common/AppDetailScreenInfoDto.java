package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDetailScreenInfoDto {
	
	/**
	 * 申請
	 */
	public ApplicationDto_New application;
	
	/**
	 * 承認ルートインスタンス
	 */
	public List<ApprovalPhaseStateForAppDto> approvalLst;
	
	/**
	 * 承認コメント
	 */
	public String authorComment;
	
	/**
	 * 利用者
	 */
	public int user; 
	
	/**
	 * ステータス
	 */
	public int reflectPlanState;
	
	/**
	 * 詳細画面モード
	 */
	public Integer outputMode;
	
	/**
	 * 承認できるフラグ
	 */
	public boolean authorizableFlags;
	
	/**
	 * ログイン者の承認区分
	 */
	public int approvalATR;
	
	/**
	 * 代行期限フラグ
	 */
	public boolean alternateExpiration;
	
	public static AppDetailScreenInfoDto fromDomain(AppDetailScreenInfo appDetailScreenInfo) {
		AppDetailScreenInfoDto result = new AppDetailScreenInfoDto();
		result.application = ApplicationDto_New.fromDomain(appDetailScreenInfo.getApplication());
		result.approvalLst = appDetailScreenInfo.getApprovalLst().stream()
				.map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList());
		result.authorComment = appDetailScreenInfo.getAuthorComment();
		result.user = appDetailScreenInfo.getUser().value;
		result.reflectPlanState = appDetailScreenInfo.getReflectPlanState().value;
	 	result.outputMode = appDetailScreenInfo.getOutputMode().value;
		result.authorizableFlags = appDetailScreenInfo.getAuthorizableFlags().orElse(false);
		result.approvalATR = appDetailScreenInfo.getApprovalATR().map(x -> x.value).orElse(ApprovalAtr.UNAPPROVED.value);
		result.alternateExpiration = appDetailScreenInfo.getAlternateExpiration().orElse(false);
		return result;
	}
	
	public AppDetailScreenInfo toDomain() {
		AppDetailScreenInfo appDetailScreenInfo = new AppDetailScreenInfo();
		appDetailScreenInfo.setApplication(ApplicationDto_New.toEntity(application));
		appDetailScreenInfo.setApprovalLst(approvalLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		appDetailScreenInfo.setAuthorComment(authorComment);
		appDetailScreenInfo.setUser(EnumAdaptor.valueOf(user, User.class));
		appDetailScreenInfo.setReflectPlanState(EnumAdaptor.valueOf(reflectPlanState, ReflectPlanPerState.class));
		appDetailScreenInfo.setOutputMode(EnumAdaptor.valueOf(outputMode, OutputMode.class));
		appDetailScreenInfo.setAuthorizableFlags(Optional.of(authorizableFlags));
		appDetailScreenInfo.setApprovalATR(Optional.of(EnumAdaptor.valueOf(approvalATR, ApprovalAtr.class)));
		appDetailScreenInfo.setAlternateExpiration(Optional.of(alternateExpiration));
		return appDetailScreenInfo;
	}
	
}
