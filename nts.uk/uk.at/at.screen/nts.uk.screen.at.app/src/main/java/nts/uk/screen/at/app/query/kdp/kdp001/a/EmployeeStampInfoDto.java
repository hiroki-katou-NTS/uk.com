package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;

@AllArgsConstructor
public class EmployeeStampInfoDto {

	/**
	 * 社員ID
	 */
	@Getter
	private final String employeeId;

	/**
	 * 年月日
	 */
	@Getter
	private final GeneralDate date;
	
	/**
	 * 打刻情報リスト
	 */
	@Getter
	private final List<StampInfoDispDto> listStampInfoDisp;
	
	public static EmployeeStampInfoDto fromDomain(EmployeeStampInfo domain) {
		
		List<StampInfoDispDto> listStampInfoDisp = domain.getListStampInfoDisp().stream()
				.map(x -> StampInfoDispDto.fromDomain(x)).collect(Collectors.toList());
		
		return new EmployeeStampInfoDto(domain.getEmployeeId(), domain.getDate(), listStampInfoDisp);
	}
}
