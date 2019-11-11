package nts.uk.ctx.hr.develop.dom.personalinfo.stresscheck;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StressCheckManagement extends AggregateRoot {
	/**
	 *  ストレスチェックのリスト
	 */
	private List<StressCheck> stressChecks;
	/**
	 *  検索済み社員IDリスト
	 */
	private List<String> searchedEmployeeIDs;
}
