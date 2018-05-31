package nts.uk.shr.com.security.audittrail.jms;

import java.io.Serializable;

import javax.jms.MapMessage;
import javax.jms.Session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import nts.gul.serialize.ObjectSerializer;
import nts.uk.shr.com.security.audittrail.correction.CorrectionProcessorId;

@RequiredArgsConstructor
public class JmsAuditTrailMessage {

	private static final String MAPKEY_OPERATION_ID = "operationId";
	private static final String MAPKEY_PROCESSOR_ID = "processorId";
	private static final String MAPKEY_PARAMETER = "parameter";
	
	@Getter
	private final String operationId;

	@Getter
	private final CorrectionProcessorId processorId;
	
	@Getter
	private final Serializable parameter;
	
	@SneakyThrows
	public static JmsAuditTrailMessage restore(MapMessage message) {
		
		String operationId = message.getString(MAPKEY_OPERATION_ID);
		val processorId = CorrectionProcessorId.of(message.getInt(MAPKEY_PROCESSOR_ID));
		Serializable parameter = ObjectSerializer.restore(message.getString(MAPKEY_PARAMETER));
		
		return new JmsAuditTrailMessage(operationId, processorId, parameter);
	}
	
	@SneakyThrows
	public MapMessage toMapMessage(Session session) {
		val message = session.createMapMessage();
		
		message.setString(MAPKEY_OPERATION_ID, this.operationId);
    	message.setInt(MAPKEY_PROCESSOR_ID, processorId.value);
    	message.setString(MAPKEY_PARAMETER, ObjectSerializer.toBase64(this.parameter));
    	
    	return message;
	}
}
