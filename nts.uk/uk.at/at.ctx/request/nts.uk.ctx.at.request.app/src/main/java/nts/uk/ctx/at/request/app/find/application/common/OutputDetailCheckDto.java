package nts.uk.ctx.at.request.app.find.application.common;

/*import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;*/
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
@Data
@AllArgsConstructor
public class OutputDetailCheckDto {
	//利用者
		private int user;
		//ステータス
		private int reflectPlanState;
		//承認できるフラグ
		private boolean authorizableFlags;
		//ログイン者の承認区分
		private int approvalATR;
		//代行期限切れフラグ
		private boolean alternateExpiration;
		
		private Integer initMode;
	
		public static OutputDetailCheckDto fromDomain(DetailedScreenPreBootModeOutput domain) {
			return new OutputDetailCheckDto(
					domain.getUser().value,
					domain.getReflectPlanState().value,
					domain.isAuthorizableFlags(),
					domain.getApprovalATR().value,
					domain.isAlternateExpiration(),
					null);
		}
}
