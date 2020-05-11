package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;

/**
 * @author phongtq
 * 職場グループの職場入替処理結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループの職場入替処理結果
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceReplaceResult {

	/** 処理結果 */
	private WorkplaceReplacement workplaceReplacement;
	
	/** 所属済み職場グループID */
	private Optional<String> wKPGRPID;

	/** 永続化処理 */
	private Optional<AtomTask> persistenceProcess;

	// [C-1] 追加する
	public WorkplaceReplaceResult add(Optional<AtomTask> persistenceProcess) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.ADD.value, WorkplaceReplacement.class), Optional.empty(), persistenceProcess);
	}

	// [C-2] 削除する
	public WorkplaceReplaceResult delete(Optional<AtomTask> persistenceProcess) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.DELETE.value, WorkplaceReplacement.class), Optional.empty(), persistenceProcess);
	}

	// [C-3] 所属済み
	public WorkplaceReplaceResult alreadyBelong(String wKPGRPID) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.ALREADY_BELONGED.value, WorkplaceReplacement.class), Optional.of(wKPGRPID) ,
				Optional.empty());
	}

	// [C-4] 別職場グループに所属
	public WorkplaceReplaceResult belongAnother(String wKPGRPID) {
		return new WorkplaceReplaceResult(
				EnumAdaptor.valueOf(WorkplaceReplacement.BELONGED_ANOTHER.value, WorkplaceReplacement.class), Optional.of(wKPGRPID) ,
				Optional.empty());
	}
}
