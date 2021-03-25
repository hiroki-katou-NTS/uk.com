package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;

/**
 * おるた
 * @author ai_muto
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALTERATION")
public class NemTdAlteration extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@Override
	protected Object getKey() {
		return alterationId;
	}

	public Alteration toDomain(List<AlterationContent> contents) {
		return new Alteration(
				alterationId,
				featureId,
				time,
				tableId,
				new AlterationMetaData(
					userName,
					comment),
				contents
			);
	}

	public static NemTdAlteration toEntity(Alteration domain) {

		val e = new NemTdAlteration();

		e.alterationId = domain.getAlterId();
		e.tableId = domain.getTableId();
		e.featureId = domain.getFeatureId();
		e.time = domain.getCreatedAt();
		e.userName = domain.getMetaData().getUserName();
		e.comment = domain.getMetaData().getComment();

		return e;
	}

	@RequiredArgsConstructor
	private static class Match {
		private final AlterationContent content;
		private final List<Process<?, ?>> processes = new ArrayList<>();

		<D, E> Match when(Class<D> entityClass, List<E> container, Function<D, E> toEntity) {
			processes.add(new Process<>(entityClass, container, toEntity));
			return this;
		}

		void go(Runnable elseAction) {
			for (val p : processes) {
				if (p.go(content)) {
					return;
				}
			}

			elseAction.run();
		}

		@Value
		private static class Process<D, E> {
			Class<D> entityClass;
			List<E> container;
			Function<D, E> toEntity;

			@SuppressWarnings("unchecked")
			boolean go(AlterationContent content) {
				if (entityClass.isInstance(content)) {
					container.add(toEntity.apply((D) content));
					return true;
				}

				return false;
			}
		}
	}
}
