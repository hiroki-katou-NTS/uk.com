package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.RecInfo;

@Stateless
public class ChangeAbsDateToHolidayCommandHandler
		extends CommandHandlerWithResult<SaveHolidayShipmentCommand, Integer> {

	@Override
	protected Integer handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		// tạm thời chưa làm được do không có thông tin về domain
		SaveHolidayShipmentCommand command = context.getCommand();
		AbsenceLeaveAppCommand absCmd = command.getAbsCmd();
		// アルゴリズム「関連付けられた振出情報の取得」を実行する
		List<ChangeInfo> changeInfos = getChangeInfo(absCmd);
		Integer payoutType = null;
		for (ChangeInfo changeInfo : changeInfos) {
			RecInfo recInfo = changeInfo.getRecInfo();
			if (recInfo != null) {
				if (payoutType == null) {
					payoutType = recInfo.getStatutoryType();
				} else {
					if (payoutType != recInfo.getStatutoryType()) {
						throw new BusinessException("Msg_1218");
					}

				}
			}
		}
		return payoutType;
	}

	private List<ChangeInfo> getChangeInfo(AbsenceLeaveAppCommand absCmd) {
		List<ChangeInfo> changeInfos = new ArrayList<ChangeInfo>();
		// INPUT.消化対象振休管理の件数分ループ
//		absCmd.getSubDigestions().forEach(x -> {
//			RecInfo recInfo = null;
//			if (x.getPayoutMngDataID() != null) {
//				// Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa làm domain
//			} else {
//				// Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa làm domain
//			}
//
//			changeInfos.add(new ChangeInfo(x.getOccurrenceDate(), x.getDaysUsedNo(), recInfo));
//		});
		return changeInfos;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	class ChangeInfo {
		// 振出日
		private GeneralDate issueDate;
		// 使用日数
		private int dayUsed;
		// 振出情報
		private RecInfo recInfo;
	}

}
