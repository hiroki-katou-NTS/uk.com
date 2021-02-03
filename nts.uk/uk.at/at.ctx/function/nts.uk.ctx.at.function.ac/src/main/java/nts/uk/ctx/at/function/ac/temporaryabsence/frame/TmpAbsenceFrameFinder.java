package nts.uk.ctx.at.function.ac.temporaryabsence.frame;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.temporaryabsence.frame.TempAbsenceFrameApdater;
import nts.uk.ctx.at.function.dom.temporaryabsence.frame.TempAbsenceFrameApdaterDto;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.frame.TempAbsenceFramePub;

/**
 * The Class TempAbsenceFrameFinder.
 * 
 * @author LienPTK
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TmpAbsenceFrameFinder implements TempAbsenceFrameApdater {
	
	/** The temp absence frame pub. */
	@Inject
	private TempAbsenceFramePub tempAbsenceFramePub;

	/**
	 * Find with use state.
	 *
	 * @param cId the c id
	 * @param useAtr the use atr
	 * @return the list
	 */
	@Override
	public List<TempAbsenceFrameApdaterDto> findWithUseState(String cId, Integer useAtr) {
		return this.tempAbsenceFramePub.findWithUseState(cId, useAtr).stream()
				.map(t -> TempAbsenceFrameApdaterDto.builder()
						.companyId(t.getCompanyId())
						.tempAbsenceFrName(t.getTempAbsenceFrName())
						.tempAbsenceFrNo(BigDecimal.valueOf(t.getTempAbsenceFrNo()))
						.useClassification(t.getUseClassification())
						.build())
				.collect(Collectors.toList());
	}

}
