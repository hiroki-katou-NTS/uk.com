package nts.uk.screen.at.app.kmk004.n;

import java.util.List;

import lombok.Data;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;

/**
 * 雇用別法定労働時間の登録（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Data
public class DisplayInitialDeforScreenByEmploymentDto {

	// 雇用リスト
	private List<EmploymentCodeDto> employmentCds;

	// 雇用を選択する
	private SelectEmploymentDeforDto selectEmploymentDeforDto;
}
