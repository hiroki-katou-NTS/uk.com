package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 勤務種別履歴
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkTypeHistoryImport {
	/** 会社ID */
	private String companyId;

	/** 社員ID */
	private String employeeId;

	List<ExWorkTypeHisItemImport> exWorkTypeHisItemImports;
}
