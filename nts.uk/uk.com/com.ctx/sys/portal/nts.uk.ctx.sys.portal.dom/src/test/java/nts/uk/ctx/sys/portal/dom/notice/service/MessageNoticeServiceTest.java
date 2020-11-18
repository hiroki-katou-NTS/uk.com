package nts.uk.ctx.sys.portal.dom.notice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeHelper;

/**
 * Test domain service お知らせメッセージ
 * 
 * @author DungDV
 *
 */
@RunWith(JMockit.class)
public class MessageNoticeServiceTest {

	@Injectable
	private MessageNoticeService.MessageNoticeRequire require;

	@Mocked
	private static MessageNoticeDto mockDto = MessageNoticeHelper.mockDto;

	@Test
	public void testIsNewMsgTrueResult() {
		MessageNotice domain = MessageNotice.createFromMemento(mockDto);
		
		new Expectations() {
			{
				require.getWpId("mock-sid", GeneralDate.today());
				result = Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd");
			}
			{
				require.getNewMsgForDay(Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd"));
				result = Arrays.asList(domain);
			}
		};
		val service = new DefaultMessageNoticeService();
		val res = service.isNewMsg(require, "mock-sid");
		assertThat(res).isTrue();
	}
	
	@Test
	public void testIsNewMsgFalseResult() {
		new Expectations() {
			{
				require.getWpId("mock-sid", GeneralDate.today());
				result = Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd");
			}
			{
				require.getNewMsgForDay(Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd"));
				result = new ArrayList<String>();
			}
		};
		val service = new DefaultMessageNoticeService();
		val res = service.isNewMsg(require, "mock-sid");
		assertThat(res).isFalse();
	}

	@Test
	public void testGetAllMsgInPeriod() {
		MessageNotice domain = MessageNotice.createFromMemento(mockDto);
		new Expectations() {
			{
				require.getWpId("mock-sid", GeneralDate.today());
				result = Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd");
			}
			{
				require.getMsgRefByPeriod((DatePeriod) any, Optional.of("31559a03-1f9a-47ea-8ae5-85364ff7e3fd"),
						"mock-sid");
				result = Arrays.asList(domain);
			}
		};
		val service = new DefaultMessageNoticeService();
		List<MessageNotice> res = service.getAllMsgInPeriod(require, DatePeriod.oneDay(GeneralDate.today()), "mock-sid");
		assertThat(res).isNotEmpty();
		assertThat(res).hasSize(1);
		assertThat(res.get(0)).isEqualTo(domain);
	}
}
