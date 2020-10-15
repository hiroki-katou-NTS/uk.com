package nts.uk.ctx.at.request.app.find.setting.workplace.appuseset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApprovalFunctionSetDto {
	
	/**
	 * 申請利用設定
	 */
	private List<ApplicationUseSetDto> appUseSetLst;
	
	public static ApprovalFunctionSetDto fromDomain(ApprovalFunctionSet approvalFunctionSet) {
		return new ApprovalFunctionSetDto(approvalFunctionSet.getAppUseSetLst().stream().map(x -> ApplicationUseSetDto.fromDomain(x)).collect(Collectors.toList()));
	}
	
	public ApprovalFunctionSet toDomain() {
		return new ApprovalFunctionSet(appUseSetLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
