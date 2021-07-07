package nts.uk.screen.at.app.query.kdp.kdp003.q;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.dom.notice.DestinationClassification;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;
import nts.uk.ctx.sys.portal.dom.notice.adapter.WorkplaceInfoImport;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).Q:メッセージ登録.メニュー別OCD.打刻入力の作成するお知らせ登録の画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayNoticeRegisterScreen {

	@Inject
	private GetWorkplaceByStampNotice wkpByStampNotice;

	@Inject
	private MessageNoticeAdapter msgNoticeAdapter;

	/**
	 * 
	 * @param sId 作成者ID
	 * @param msg お知らせメッセージ(Optional)
	 * @return
	 */
	public DisplayNoticeRegisterScreenDto displayNoticeRegisterScreen(String sid, MessageNoticeDto msg) {

		DisplayNoticeRegisterScreenDto result = new DisplayNoticeRegisterScreenDto();
		GeneralDate baseDate = GeneralDate.today();

		// 1. call [RQ30]社員所属職場履歴を取得
		Optional<WorkplaceInfoImport> optWkpInfp = msgNoticeAdapter.getWorkplaceInfo(sid, baseDate);

		if (optWkpInfp.isPresent()) {
			result.setWorkplaceInfo(optWkpInfp.get());
		}

		// 2. [お知らせメッセージ Not Null AND お知らせメッセージ.対象情報.宛先区分＝職場選択]:get*(お知らせメッセージ.対象情報.対象職場ID):＜List＞対象職場（職場ID、職場コード、職場表示名）
		if (msg != null && msg.getTargetInformation().getDestination() == DestinationClassification.WORKPLACE.value) {

			List<WorkplaceInfoImport> targetWkps = wkpByStampNotice
					.getWkpNameByWkpId(msg.getTargetInformation().getTargetWpids()).stream()
					.map(m -> WorkplaceInfoImport.builder().workplaceId(m.getWorkplaceId())
							.workplaceCode(m.getWorkplaceCode()).workplaceName(m.getWorkplaceName()).build())
					.collect(Collectors.toList());

			result.setTargetWkps(targetWkps);
		}
		return result;
	}

}
