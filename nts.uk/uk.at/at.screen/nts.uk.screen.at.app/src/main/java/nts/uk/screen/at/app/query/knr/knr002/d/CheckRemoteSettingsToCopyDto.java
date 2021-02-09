package nts.uk.screen.at.app.query.knr.knr002.d;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckRemoteSettingsToCopyDto {
	//	エラーの有無を返す
	private List<String> listEmpCode;
}
