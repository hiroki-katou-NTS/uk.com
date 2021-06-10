package nts.uk.ctx.exio.infra.entity.input.canonicalize.groups;

import static java.util.stream.Collectors.*;
import static nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.TaskCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

@Entity
@Table(name = "XIMCT_GROUP_CANONICALIZATION")
@AllArgsConstructor
@NoArgsConstructor
public class XimctGroupCanonicalization extends JpaEntity {
	
	public static final JpaEntityMapper<XimctGroupCanonicalization> MAPPER = new JpaEntityMapper<>(XimctGroupCanonicalization.class);
	
	@Embeddable
	public static class Key {

		@Column(name = "GROUP_ID")
		public int groupId;

		@Column(name = "ITEM_NAME")
		public String itemName;
	}

	@EmbeddedId
	@Getter
	public Key key;
	
	@Column(name = "ITEM_NO")
	public int itemNo;

	public static class Group {
		
		private final ImportingGroupId groupId;
		private final List<XimctGroupCanonicalization> entities;
		
		public Group(List<XimctGroupCanonicalization> entities) {
			
			if (entities.isEmpty()) {
				throw new RuntimeException("空リストは処理できない");
			}
			
			val containedGroupIds = entities.stream().map(e -> e.key.groupId).distinct().collect(toList());
			if (containedGroupIds.size() > 1) {
				throw new RuntimeException(
						"複数のグループが混ざっている: "
						+ containedGroupIds.stream().map(id -> Integer.toString(id)).collect(Collectors.joining(",")));
			}
			
			this.groupId = new ImportingGroupId(entities.get(0).key.groupId);
			this.entities = entities;
		}
		
		public GroupCanonicalization toDomain() {
			return FUNCS.get(groupId).apply(this);
		}
		
		/** toDomain用のMap */
		private static final Map<ImportingGroupId, Function<Group, GroupCanonicalization>> FUNCS;
		static {
			FUNCS = new HashMap<>();
			
			// 作業
			FUNCS.put(TASK, g -> new TaskCanonicalization(
					g.getItemNo("TaskFrameNo"),
					g.getItemNo("TaskCode")));
			
			// 雇用履歴
			FUNCS.put(EMPLOYMENT_HISTORY, g -> new EmploymentHistoryCanonicalization(
					g.getItemNo(Names.START_DATE),
					g.getItemNo(Names.END_DATE),
					g.getItemNo(Names.HISTORY_ID),
					g.toEmployeeCode()));
		}
		
		/**
		 * 社員コード
		 * @return
		 */
		private EmployeeCodeCanonicalization toEmployeeCode() {
			return new EmployeeCodeCanonicalization(
					getItemNo("EmployeeCode"),
					getItemNo("EmployeeId"));
		}

		private static class Names {
			static final String START_DATE = "StartDate";
			static final String END_DATE = "EndDate";
			static final String HISTORY_ID = "HistoryId";
		}
		
		private int getItemNo(String itemName) {
			return entities.stream()
					.filter(e -> e.key.itemName.equals(itemName))
					.findFirst()
					.get()
					.itemNo;
		}
	}
	
}
