package nts.uk.shr.com.security.audittrail.correction.content;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CorrectionLog {

	private final List<DataCorrectionLog> targets;
}
