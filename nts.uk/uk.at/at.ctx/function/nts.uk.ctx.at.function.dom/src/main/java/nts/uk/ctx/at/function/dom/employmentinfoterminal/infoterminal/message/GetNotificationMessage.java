package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;

/**
 * @author thanh_nx
 * 
 *         タイムレコーダへ送信するお知らせメッセージを取得する
 */
public class GetNotificationMessage {

	private GetNotificationMessage() {
	}

	// 取得する
	public static List<NoticeMessageImport> getNotified(Require require, ContractCode contractCode,
			String stampNumber) {
		Optional<String> sid = require.getSidFromStampNumber(contractCode, stampNumber);
		if (!sid.isPresent())
			return new ArrayList<>();

		Optional<String> cid = require.getCompanyId(sid.get());
		if (!cid.isPresent())
			return new ArrayList<>();

		return require.getMessage(cid.get(), sid.get(), new DatePeriod(GeneralDate.today(), GeneralDate.today()))
				.stream().limit(99).collect(Collectors.toList());

	}

	public static interface Require {

		// 打刻カード番号を指定して社員IDを取得する
		// FuncStampCardAdapter.getSidByCardNoAndContractCode
		public Optional<String> getSidFromStampNumber(ContractCode contractCode, String stampNumber);

		// 期間で参照できるメッセージを取得する
		//MessageNoticeServiceAdapter
		public List<NoticeMessageImport> getMessage(String cid, String sid, DatePeriod period);

		// 社員IDから会社IDを取得
		//SyEmployeePub.getCompanyId
		public Optional<String> getCompanyId(String employeeId);
	}
}
