package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;

/**
 * 職場グループの職場入替処理結果
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceReplaceResult {

	/** 処理結果 */
	private WorkplaceReplacement workplaceReplacement;

	/** 永続化処理 */
	private Optional<AtomTask> persistenceProcess;

	// [C-1] 追加する
	public static WorkplaceReplaceResult add(Optional<AtomTask> persistenceProcess) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.ADD.value, WorkplaceReplacement.class), persistenceProcess);
	}

	// [C-2] 削除する
	public static WorkplaceReplaceResult delete(Optional<AtomTask> persistenceProcess) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.DELETE.value, WorkplaceReplacement.class), persistenceProcess);
	}

	// [C-3] 所属済み
	public static WorkplaceReplaceResult alreadyBelong() {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.ALREADY_BELONGED.value, WorkplaceReplacement.class),
				Optional.empty());
	}

	// [C-4] 別職場グループに所属
	public static WorkplaceReplaceResult belongAnother() {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.BELONGED_ANOTHER.value, WorkplaceReplacement.class),
				Optional.empty());
	}
}
