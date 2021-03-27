package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

/**
 * おるたの状態ビュー
 * @author ai_muto
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALTERATION_VIEW")
public class NemTdAlterationView extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	private static final Map<DevelopmentStatus, String> EventColumns;
	static {
		EventColumns = new HashMap<>();
		EventColumns.put(DevelopmentStatus.ORDERED, "orderedEventId");
		EventColumns.put(DevelopmentStatus.DELIVERED, "deliveredEventId");
		EventColumns.put(DevelopmentStatus.ACCEPTED, "acceptedEventId");
	}
	
	public static String getField(DevelopmentStatus status) {
		return EventColumns.get(status);
	}
	
	/**
	 * 指定された開発進捗状況を条件とするWhere句
	 * @param progress
	 * @return
	 */
	public static String jpqlWhere(DevelopmentProgress progress) {

		if(progress.getBaseline().isFirst()) {
			return getField(progress.getBaseline().next())
					+ " is" + (progress.isAchieved() ? " not " : " ") + "null";
		}
		return getField(progress.getBaseline())
				+ " is" + (progress.isAchieved() ? " not " : " ") + "null";
	}
	
	/**
	 * 指定された開発状況を条件とするWhere句
	 * @param status
	 * @param alias
	 * @return
	 */
	public static String jpqlWhere(DevelopmentStatus status, String alias) {
		// 開発状況が先頭のケース
		// ※先頭は特殊、「先頭のイベントが存在しない」という状況のため「is null」を使う
		if(status.isFirst()) {
			return 	alias + "." + getField(status.next()) + " is null";
		}
		// 開発状況が末尾のケース
		else if(status.isLast()) {
			return 	alias + "." + getField(status) + " is not null";
		}
		// 上記以外
		else {
			return 	alias + "." + getField(status) + " is not null"
					+ " and " + 
					alias + "." + getField(status.next()) + " is null";
		}
	}
	
	@Id
	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "FEATURE_ID")
	public String featureId;

	@Column(name = "DATETIME")
	public GeneralDateTime time;

	@Column(name = "USER_NAME")
	public String userName;

	@Column(name = "COMMENT")
	public String comment;

	@Column(name = "ORDERED_EVENT_ID")
	public String orderedEventId;

	@Column(name = "DELIVERED_EVENT_ID")
	public String deliveredEventId;

	@Column(name = "ACCEPTED_EVENT_ID")
	public String acceptedEventId;

	@Override
	protected Object getKey() {
		return alterationId;
	}

	public AlterationSummary toDomain() {
		return new AlterationSummary(
				this.alterationId,
				this.time,
				this.tableId,
				convertState(),
				new AlterationMetaData(this.userName, this.comment),
				this.featureId
			);
	}
	
	public boolean isOrdered() {
		return this.orderedEventId != null;
	}
	
	public boolean isDelivered() {
		return this.deliveredEventId != null;
	}
	
	public boolean isAccepted() {
		return this.acceptedEventId != null;
	}
	
	

	private DevelopmentState convertState() {
		if(isAccepted()) return DevelopmentState.ACCEPTED;

		if(isDelivered()) return DevelopmentState.DELIVERED;

		if(isOrdered()) return DevelopmentState.ORDERED;

		return DevelopmentState.NOT_ORDERING;
	}
}
