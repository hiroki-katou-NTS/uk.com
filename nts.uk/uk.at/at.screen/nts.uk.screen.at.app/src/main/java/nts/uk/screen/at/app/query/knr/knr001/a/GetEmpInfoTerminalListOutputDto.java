package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuannt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetEmpInfoTerminalListOutputDto {
	//	　応援の運用設定.利用するか
	private boolean isUsedSupportOperationSetting;
	//	　List<就業情報端末>
	private List<EmpInfoTerminalListDto> empInfoTerminalListDto;
}
