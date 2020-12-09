package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.OrderReferWorkType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_MON_ITEM_SORTED")
public class KrcmtBusinessTypeSortedMon extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypeSortedMonPK krcmtBusinessTypeSortedMonPK;
	
	@Column(name = "DISP_ORDER")
	public int order;
	
	@Override
	protected Object getKey() {
		return krcmtBusinessTypeSortedMonPK;
	}

	public KrcmtBusinessTypeSortedMon(KrcmtBusinessTypeSortedMonPK krcmtBusinessTypeSortedMonPK, int order) {
		super();
		this.krcmtBusinessTypeSortedMonPK = krcmtBusinessTypeSortedMonPK;
		this.order = order;
	}
	
	public static KrcmtBusinessTypeSortedMon toEntity(String companyID,OrderReferWorkType domain ) {
		return new KrcmtBusinessTypeSortedMon(
				new KrcmtBusinessTypeSortedMonPK(
					companyID,
					domain.getAttendanceItemID()
						),
				domain.getOrder()
				);
	}
	
	public OrderReferWorkType toDomain() {
		return new OrderReferWorkType(
				this.krcmtBusinessTypeSortedMonPK.attendanceItemID,
				this.order
				);
	}

}
