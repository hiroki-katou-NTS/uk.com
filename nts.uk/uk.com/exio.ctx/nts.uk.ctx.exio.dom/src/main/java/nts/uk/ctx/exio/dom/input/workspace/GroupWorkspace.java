package nts.uk.ctx.exio.dom.input.workspace;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

/**
 * 受入グループのワークスペース
 */
@Value
public class GroupWorkspace {
	
	/** 受入グループID */
	private final ImportingGroupId groupId;
	
	/** 主キーの項目一覧 */
	private final List<WorkspaceItem> itemsPk;
	
	/** 非主キーの項目一覧 */
	private final List<WorkspaceItem> itemsNotPk;
	
	public List<WorkspaceItem> getAllItemsSortedByItemNo() {
		return Stream.concat(itemsPk.stream(), itemsNotPk.stream())
				.sorted(Comparator.comparing(i -> i.getItemNo()))
				.collect(toList());
	}
	
	public Optional<WorkspaceItem> getItem(int itemNo) {
		
		return Stream.concat(itemsPk.stream(), itemsNotPk.stream())
				.filter(item -> item.getItemNo() == itemNo)
				.findFirst();
	}
	
	public WorkspaceItem getItemByName(String itemName) {

		return Stream.concat(itemsPk.stream(), itemsNotPk.stream())
				.filter(item -> item.getName() == itemName)
				.findFirst()
				.get();
	}
}
