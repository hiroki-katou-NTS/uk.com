package nts.uk.ctx.at.function.app.nrl.request;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.data.MarshalResult;
import nts.uk.ctx.at.function.app.nrl.data.RequestData;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFrameException;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.DefaultXDocument;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

/**
 * Request dispatcher.
 * 
 * @author manhnd
 */
public class RequestDispatcher {
	
	/**
	 * Request mapper
	 */
	private static final Map<String, Class<? extends NRLRequest<Frame>>> RequestMapper;
	static {
		RequestMapper = new HashMap<>();
		RequestMapper.put(Command.TEST.Request, TestRequest.class);
		RequestMapper.put(Command.POLLING.Request, StatusPollingRequest.class);
		RequestMapper.put(Command.SESSION.Request, SessionRequest.class);
		RequestMapper.put(Command.ALL_IO_TIME.Request, TimeIORequest.class);
		RequestMapper.put(Command.ALL_RESERVATION.Request, AllReservationRequest.class);
		RequestMapper.put(Command.ALL_PETITIONS.Request, AllPetitionsRequest.class);
		RequestMapper.put(Command.PERSONAL_INFO.Request, PersonalInfoRequest.class);
		RequestMapper.put(Command.OVERTIME_INFO.Request, OverTimeInfoRequest.class);
		RequestMapper.put(Command.RESERVATION_INFO.Request, ReservationMenuInfoRequest.class);
		RequestMapper.put(Command.TIMESET_INFO.Request, TimeSettingInfoRequest.class);
		RequestMapper.put(Command.WORKTIME_INFO.Request, WorkTimeInfoRequest.class);
		RequestMapper.put(Command.WORKTYPE_INFO.Request, WorkTypeInfoRequest.class);
	}
	
	/**
	 * Document
	 */
	@Inject
	private DefaultXDocument document;
	
	/**
	 * Ignite.
	 * @param is input stream
	 * @return response
	 */
	protected NRLResponse ignite(InputStream is) {
		
		MarshalResult result = null;
		try {
 			String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
			RequestData data = this.getClass().getDeclaredMethod(caller, InputStream.class)
					.getDeclaredAnnotation(RequestData.class);
			if (data == null) throw new RuntimeException("RequestData is required.");
			Command[] datas = data.value();
			JAXBContext context = JAXBContext.newInstance(Frame.class);
			result = new MarshalResult();
			Frame frame = document.unmarshal(context, is, result);
			
			String soh = frame.pickItem(Element.SOH);
			if (!DefaultValue.SOH.equals(soh)) throw new InvalidFrameException();
			String hdr = frame.pickItem(Element.HDR);

			Command command = Command.findName(hdr).orElseThrow(InvalidFrameException::new);
			if (command == Command.ACCEPT || command == Command.NOACCEPT) return NRLResponse.mute();
			if (Arrays.asList(datas).stream().noneMatch(d -> d == command)) {
				String nrlNo = frame.pickItem(Element.NRL_NO);
				String macAddr = frame.pickItem(Element.MAC_ADDR);
				return NRLResponse.noAccept(nrlNo, macAddr).build().addPayload(Frame.class, ErrorCode.PARAM.value);
			}
			
			NRLRequest<Frame> request = CDI.current().select(RequestMapper.get(hdr)).get();
			if (request.intrEntity(frame.isCracked())) {
				frame = document.unmarshal(result.toInputStream());
			}
			return request.responseTo(frame);
		} catch (JAXBException | NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (Objects.nonNull(result)) {
				result.dispose();
			}
		}
	}
}
