package nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * プライマリキー：会社別の承認者（36協定）
 * @author khai.dh
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Krcmt36AgrApvCmpPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@NotNull
	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;

	@StaticMetamodel(Krcmt36AgrApvCmpPK.class)
	public static class Meta_ {
		public static volatile SingularAttribute<Krcmt36AgrApvCmpPK, String> companyId;
		public static volatile SingularAttribute<Krcmt36AgrApvCmpPK, GeneralDate> startDate;
	}
}
