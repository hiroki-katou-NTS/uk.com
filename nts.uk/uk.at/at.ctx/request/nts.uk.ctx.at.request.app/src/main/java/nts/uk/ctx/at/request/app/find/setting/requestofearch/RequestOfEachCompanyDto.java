package nts.uk.ctx.at.request.app.find.setting.requestofearch;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.requestofearch.RequestAppDetailSettingDto;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOfEachCompanyDto {
	public int selectOfApproversFlg;
	public List<RequestAppDetailSettingDto> requestAppDetailSettingDtoLst;
}
