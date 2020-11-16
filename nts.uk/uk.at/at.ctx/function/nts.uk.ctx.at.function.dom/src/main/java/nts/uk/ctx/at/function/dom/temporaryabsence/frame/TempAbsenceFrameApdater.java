package nts.uk.ctx.at.function.dom.temporaryabsence.frame;

import java.util.List;

/**
 * The Interface TempAbsenceFrameApdater.
 * 
 * @author LienPTK
 */
public interface TempAbsenceFrameApdater {
	
	/**
	 * Find with use state.
	 *
	 * @param cId the c id
	 * @param useAtr the use atr
	 * @return the list
	 */
	public List<TempAbsenceFrameApdaterDto> findWithUseState(String cId, Integer useAtr);
}
