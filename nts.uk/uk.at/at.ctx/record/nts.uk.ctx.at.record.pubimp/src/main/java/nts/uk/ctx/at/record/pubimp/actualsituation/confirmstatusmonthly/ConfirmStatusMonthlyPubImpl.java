package nts.uk.ctx.at.record.pubimp.actualsituation.confirmstatusmonthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.ConfirmStatusMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.ConfirmStatusResult;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.StatusConfirmMonthDto;
import nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly.ConfirmStatusMonthlyPub;
import nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly.ConfirmStatusResultEx;
import nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly.StatusConfirmMonthEx;
@Stateless
public class ConfirmStatusMonthlyPubImpl implements ConfirmStatusMonthlyPub {
	@Inject
	private ConfirmStatusMonthly confirmStatusMonthly;
	@Override
	public Optional<StatusConfirmMonthEx> getConfirmStatusMonthly(String companyId, List<String> listEmployeeId,
			YearMonth yearmonthInput, Integer clsId, boolean clearState) {
		Optional<StatusConfirmMonthDto> data = confirmStatusMonthly.getConfirmStatusMonthly(companyId, listEmployeeId, yearmonthInput, clsId, clearState);
		if(!data.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(convertToStatusConfirmMonthDto(data.get()));
	}
	
	private StatusConfirmMonthEx convertToStatusConfirmMonthDto(StatusConfirmMonthDto statusConfirmMonthDto) {
		return new StatusConfirmMonthEx(statusConfirmMonthDto.getListConfirmStatus().stream()
				.map(c -> convertToConfirmStatusResult(c)).collect(Collectors.toList()));
	}
	
	private ConfirmStatusResultEx convertToConfirmStatusResult(ConfirmStatusResult confirmStatusResult) {
		return new ConfirmStatusResultEx(
				confirmStatusResult.getEmployeeId(),
				confirmStatusResult.getYearMonth(), 
				confirmStatusResult.getClosureId(),
				confirmStatusResult.isConfirmStatus(),
				confirmStatusResult.getImplementaPropriety().value, 
				confirmStatusResult.getWhetherToRelease().value);
	}

}
