package nts.uk.screen.hr.app.databeforereflecting.command;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.ApprovalStatus;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingRepository;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.Status;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApprovedCommandHandler extends CommandHandler<ApprovedCommand> {
	
	@Inject
	private DataBeforeReflectingRepository drRepo;

	@Override
	protected void handle(CommandHandlerContext<ApprovedCommand> context) {

		ApprovedCommand cmd = context.getCommand();
		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();
		// アルゴリズム[業務承認の実施]を実行する(Thực hiện thuật toán [thực hiện apprpove nghiệp
		// vụ])
		approvalProcess(cId, sId, cmd);
		// アルゴリズム[事後表示処理]を実行する(Thực hiện thuật toán [xử lý hiển thị xin sau])
		// bước này làm dưới client
	}

	private void approvalProcess(String cId, String sId, ApprovedCommand cmd) {
		// 退職者情報リストを個人情報反映前データリストへ変換する(Chuyển đổi danh sách thông tin người nghỉ
		// hưu sang danh sách dữ liệu trước khi phản ánh thông tin cá nhân/data
		// before reflecting personal information)

		Optional<DataBeforeReflectingPerInfo> drOpt = this.drRepo.getByHistId(cmd.getHistoryId());

		if (!drOpt.isPresent()) {
			return;
		}

		DataBeforeReflectingPerInfo dr = drOpt.get();

		// 業務承認の実施(Thực hiện approve nghiệp vụ)
		setApprovalData(dr, cmd, sId);

		// 個人情報反映前データリストを退職者情報リストへ変換する(Chuyển đổi PPEDT_PRE_REFLECT_DATA List
		// thành ResignmentInfoList)

		this.drRepo.updateData(Arrays.asList(dr));
	}

	private void setApprovalData(DataBeforeReflectingPerInfo dr, ApprovedCommand cmd, String sId) {

		ApprovalStatus approveStatus = EnumAdaptor.valueOf(cmd.getApprovalStatus(), ApprovalStatus.class);

		if (dr.getApproveSid1().equals(sId)) {
			// <条件1>
			dr.setApproveStatus1(approveStatus);
			dr.setApproveComment1(cmd.getComment());
			dr.setApproveDateTime1(GeneralDateTime.now());
			dr.setApproveSendMailFlg1(cmd.isSendEmail());
			return;
		}

		if (dr.getApproveSid2().equals(sId)) {
			// <条件2>
			dr.setApproveStatus2(approveStatus);
			dr.setApproveComment2(cmd.getComment());
			dr.setApproveDateTime2(GeneralDateTime.now());
			dr.setApproveSendMailFlg2(cmd.isSendEmail());
			return;
		}

		if (dr.getApproveSid1() != null && dr.getApproveStatus1().equals(ApprovalStatus.Approved_WaitingForReflection)
				&& dr.getApproveSid2() != null
				&& dr.getApproveStatus2().equals(ApprovalStatus.Approved_WaitingForReflection)) {
			// <条件3>
			dr.setStattus(Status.WaitingReflection);
			return;
		}
		
		dr.setStattus(Status.Registered);

	}
}