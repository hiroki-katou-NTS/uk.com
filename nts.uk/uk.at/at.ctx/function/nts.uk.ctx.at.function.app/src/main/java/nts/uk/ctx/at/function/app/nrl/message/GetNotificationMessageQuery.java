package nts.uk.ctx.at.function.app.nrl.message;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.request.NRLRequest;
import nts.uk.ctx.at.function.app.nrl.request.Named;
import nts.uk.ctx.at.function.app.nrl.request.ResourceContext;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.adapter.notice.MessageNoticeServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.FuncStampCardAdapter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message.GetNotificationMessage;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message.NoticeMessageImport;

/**
 * @author thanh_nx
 *
 *         タイムレコーダへ送信するお知らせメッセージを取得する
 */
@RequestScoped
@Named(value = Command.MESSAGE, decrypt = true)
public class GetNotificationMessageQuery extends NRLRequest<Frame> {
	
	@Inject
	private FuncStampCardAdapter funcStampCardAdapter;
	
	@Inject
	private MessageNoticeServiceAdapter messageNoticeServiceAdapter;
	
	@Inject
	private SyEmployeeFnAdapter syEmployeePub;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		String stampNumber = context.getEntity().pickItem(Element.PAYLOAD);
		RequireImpl impl = new RequireImpl();
		List<NoticeMessageImport> lstMessage = GetNotificationMessage.getNotified(impl,
				new ContractCode(context.getTerminal().getContractCode()), stampNumber);
		StringBuilder builder = new StringBuilder();
		lstMessage.forEach(data -> {
			builder.append(String.format("%02d", data.getDisplayOrder()));
			builder.append(Codryptofy.paddingWithByte(data.getContent(), 1200));
		});
		String payload = Codryptofy.paddingWithByte(builder.toString(), 36060);
	
		int length = 36060 + 42;//payload+soh+hdr+....
		List<MapItem> items = NRContentList.createFieldForPadding2(Command.MESSAGE,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		context.collect(items, payload);
	}

	@Override
	public String responseLength() {
		return "";
	}

	@AllArgsConstructor
	public class RequireImpl implements GetNotificationMessage.Require {

		@Override
		public Optional<String> getSidFromStampNumber(ContractCode contractCode, String stampNumber) {
			return funcStampCardAdapter.getSidByCardNoAndContractCode(contractCode.v(), stampNumber);
		}

		@Override
		public List<NoticeMessageImport> getMessage(String cid, String sid, DatePeriod period) {
			return messageNoticeServiceAdapter.getMessage(cid, sid, period);
		}

		@Override
		public Optional<String> getCompanyId(String employeeId) {
			return syEmployeePub.getCompanyId(employeeId);
		}

	}
}
