package nts.uk.ctx.at.record.pub.application.divergence;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;

/*
 * Refactor5 
 * hoangnd
 */
public interface DivergenceTimeRepositoryPub {
	List<DivergenceTimeRoot> getAllDivTime(String companyId);
}
