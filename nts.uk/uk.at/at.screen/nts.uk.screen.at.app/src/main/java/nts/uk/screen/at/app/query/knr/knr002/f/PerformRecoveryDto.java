package nts.uk.screen.at.app.query.knr.knr002.f;

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
public class PerformRecoveryDto {
	//	復旧元就業情報端末コード、
	private String empInfoTerCode;
	//	復旧先就業情報端末コード<List>
	private List<String> terminalCodeList;
}
