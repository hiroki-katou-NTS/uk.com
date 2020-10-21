package nts.uk.ctx.sys.portal.app.query.notice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.DestinationClassification;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.AnniversaryNoticeImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.EmployeeInfoImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;
import nts.uk.ctx.sys.portal.dom.notice.adapter.RoleImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.WorkplaceInfoImport;
import nts.uk.ctx.sys.portal.dom.notice.service.MessageNoticeService;
import nts.uk.ctx.sys.portal.dom.notice.service.MessageNoticeService.MessageNoticeRequire;
import nts.uk.shr.com.context.AppContexts;

/**
 * ScreenQuery お知らせメッセージ
 * @author DungDV
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MessageNoticeScreenQuery {
	
	@Inject
	private MessageNoticeAdapter messageNoticeAdapter;
	
	@Inject
	private MessageNoticeRepository messageNoticeRepository;
	
	@Inject
	private MessageNoticeService messageNoticeService;
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.A：お知らせ表示.メニュー別OCD.社員が宛先のお知らせの内容を取得する
	 * @param period 期間
	 * @return DestinationNotificationDto
	 */
	public DestinationNotificationDto getContentOfDestinationNotification(DatePeriod period) {
		Map<MessageNotice, String> msgNotices = new HashMap<MessageNotice, String>();
		MessageNoticeRequireImpl require = new MessageNoticeRequireImpl(messageNoticeAdapter, messageNoticeRepository);
		String sid = AppContexts.user().employeeId();
		// 1. 期間で全て参照できるメッセージを取得する(require, 社員ID, 期間)
		List<MessageNotice> listMsg = messageNoticeService.getAllMsgInPeriod(require, period, sid);
		
		// 2. Not　List<お知らせメッセージ＞　IS Empty
		if (!listMsg.isEmpty()) { // List<お知らせメッセージ＞　IS Empty
			List<String> sIds = new ArrayList<String>();
			listMsg.stream().forEach(x -> {
				if (!sIds.contains(x.getCreatorID())) {
					sIds.add(x.getCreatorID());
				}
			});
			
			// 社員ID（List）から社員コードと表示名を取得
			List<EmployeeInfoImport> listEmp = messageNoticeAdapter.getByListSID(sIds);
			Map<String, String> listEmpMap = listEmp.isEmpty()
					? new HashMap<String, String>()
					: listEmp.stream().collect(Collectors.toMap(EmployeeInfoImport::getSid, EmployeeInfoImport::getBussinessName));
			
			// ※Map<お知らせメッセージ、作成者>の作成者にビジネスネームをセットする（Listの並び順はそのままとする)
			listMsg.stream().forEach(x -> {
				msgNotices.put(x, listEmpMap.get(x.getCreatorID()));
			});
			
		}
		// 3. 期間で記念日情報を取得する(int, 期間)
		Map<AnniversaryNoticeImport, Boolean> anniversaryNotices = messageNoticeAdapter.setFlag(period);
		
		return DestinationNotificationDto.builder().anniversaryNotices(anniversaryNotices).msgNotices(msgNotices).build();
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.A：お知らせ表示.メニュー別OCD.社員のお知らせの画面を表示する
	 * @param period 期間
	 * @return
	 */
	public EmployeeNotificationDto getEmployeeNotification(DatePeriod period) {
		String roleId = AppContexts.user().roles().forAttendance();
		// 1. ログイン者就業のロールID
		Optional<RoleImport> role = messageNoticeAdapter.findByRoleId(roleId);
		
		// 2. Get 社員が宛先のお知らせの内容を取得する
		DestinationNotificationDto destinationNotice = getContentOfDestinationNotification(period);
		
		return EmployeeNotificationDto.builder()
				.anniversaryNotices(destinationNotice.getAnniversaryNotices())
				.msgNotices(destinationNotice.getMsgNotices())
				.role(role.orElse(null))
				.build();
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.B：お知らせ作成一覧.メニュー別OCD.社員が作成したお知らせの内容を取得する
	 * @param period 期間
	 * @return <List>お知らせメッセージ
	 */
	public List<MessageNoticeDto> getContentOfNotification(DatePeriod period) {
		String sid = AppContexts.user().employeeId();
		List<MessageNotice> listMsg = messageNoticeRepository.getMsgByPeriodAndSid(period, sid);
		if (listMsg.isEmpty()) {
			return new ArrayList<MessageNoticeDto>();
		}
		return listMsg.stream().map(msg -> MessageNoticeDto.fromDomain(msg)).collect(Collectors.toList());
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.C：お知らせ登録.メニュー別OCD.お知らせ宛先の社員の名称を取得する
	 * @param listSID 社員一覧ID（リスト）
	 * @return ＜List＞対象社員（社員ID、社員コード、ビジネスネーム）
	 */
	public List<EmployeeInfoImport> acquireNameOfDestinationEmployee(List<String> listSID) {
		// 1. <call> 社員ID（List）から社員コードと表示名を取得
		List<EmployeeInfoImport> listEmp = messageNoticeAdapter.getByListSID(listSID);
		return listEmp.stream().sorted(Comparator.comparing(EmployeeInfoImport::getScd)).collect(Collectors.toList());
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.C：お知らせ登録.メニュー別OCD.お知らせ宛先の職場の名称を取得する
	 * @param wkIds 職場ID(List)
	 * @return ＜List＞対象職場（職場ID、職場コード、職場表示名）
	 */
	public List<WorkplaceInfoImport> getNameOfDestinationWkp(List<String> wkIds) {
		String cid = AppContexts.user().companyId();
		return messageNoticeAdapter.getWorkplaceMapCodeBaseDateName(cid, wkIds);
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG003_お知らせ機能.C：お知らせ登録.メニュー別OCD.社員が作成するお知らせ登録の画面を表示する
	 * @param creatorId 作成者ID
	 * @param refeRange 社員参照範囲
	 * @param msg お知らせメッセージ(Optional)
	 * @return
	 */
	public NotificationCreated notificationCreatedByEmp(String creatorId, Integer refeRange, Optional<MessageNotice> msg) {
		GeneralDate baseDate = GeneralDate.today();
		WorkplaceInfoImport wkpInfor = null;
		Optional<List<WorkplaceInfoImport>> targetWkps = Optional.empty();
		Optional<List<EmployeeInfoImport>> targetEmps = Optional.empty();
		// 1. call：[RQ30]社員所属職場履歴を取得
		Optional<WorkplaceInfoImport> sWkpHistExport = messageNoticeAdapter.getWorkplaceInfo(creatorId, baseDate);
		wkpInfor = sWkpHistExport.orElse(null);
		
		// 2. [お知らせメッセージ　Not　Null　AND　お知らせメッセージ.対象情報.宛先区分＝職場選択]:get*(ログイン会社ID、お知らせメッセージ.職場ID):職場ID、職場コード、職場名称
		if (msg.isPresent() && msg.get().getTargetInformation().getDestination() == DestinationClassification.WORKPLACE) {
			List<WorkplaceInfoImport> listWkp = messageNoticeAdapter.getWorkplaceMapCodeBaseDateName(AppContexts.user().companyId(),
					msg.get().getTargetInformation().getTargetWpids());
			targetWkps = Optional.of(listWkp);
		}
		// 3. [お知らせメッセージ　Not　Null　AND　お知らせメッセージ.対象情報.宛先区分＝社員選択]call()
		if (msg.isPresent() && msg.get().getTargetInformation().getDestination() == DestinationClassification.EMPLOYEE) {
			// 社員ID（List）から社員コードと表示名を取得
			List<EmployeeInfoImport> listEmp = messageNoticeAdapter.getByListSID(msg.get().getTargetInformation().getTargetSIDs());
			targetEmps = Optional.of(listEmp.stream().sorted(Comparator.comparing(EmployeeInfoImport::getScd)).collect(Collectors.toList()));
		}
		
		return NotificationCreated.builder().workplaceInfo(wkpInfor)
				.targetEmps(targetEmps)
				.targetWkps(targetWkps)
				.build();
	}
	
	@AllArgsConstructor
	public class MessageNoticeRequireImpl implements MessageNoticeRequire {
		
		@Inject
		private MessageNoticeAdapter messageNoticeAdapter;
		
		@Inject
		private MessageNoticeRepository messageNoticeRepository;
		
		@Override
		public Optional<String> getWpId(String sid, GeneralDate baseDate) {
			return messageNoticeAdapter.getWpId(sid, baseDate);
		}

		@Override
		public List<MessageNotice> getNewMsgForDay(Optional<String> wpId) {
			return messageNoticeRepository.getNewMsgForDay(wpId);
		}

		@Override
		public List<MessageNotice> getMsgRefByPeriod(DatePeriod period, Optional<String> wpId, String sid) {
			return messageNoticeRepository.getMsgRefByPeriod(period, wpId, sid);
		}
		
	}
}
