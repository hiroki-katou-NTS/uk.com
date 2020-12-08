package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_MON_FORM")
public class KrcmtMonthlyRecordWorkType extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtMonthlyRecordWorkTypePK krcmtMonthlyRecordWorkTypePK;

	@OneToMany(mappedBy="monthlyactualresult", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRCMT_BUS_MON_FORM_SHEET")
	public List<KrcmtMonthlyActualResultRC> listKrcmtMonthlyActualResultRC;
	
	
	@Override
	protected Object getKey() {
		return krcmtMonthlyRecordWorkTypePK;
	}


	public KrcmtMonthlyRecordWorkType(KrcmtMonthlyRecordWorkTypePK krcmtMonthlyRecordWorkTypePK, List<KrcmtMonthlyActualResultRC> listKrcmtMonthlyActualResultRC) {
		super();
		this.krcmtMonthlyRecordWorkTypePK = krcmtMonthlyRecordWorkTypePK;
		this.listKrcmtMonthlyActualResultRC = listKrcmtMonthlyActualResultRC;
	}
	
	public static KrcmtMonthlyRecordWorkType toEntity(MonthlyRecordWorkType domain) {
		return new KrcmtMonthlyRecordWorkType(
					new KrcmtMonthlyRecordWorkTypePK(
						domain.getCompanyID(),
						domain.getBusinessTypeCode().v()
							),
					domain.getDisplayItem().getListSheetCorrectedMonthly().stream()
					.map(c->KrcmtMonthlyActualResultRC.toEntity(domain.getCompanyID(),
						domain.getBusinessTypeCode().v(), c)).collect(Collectors.toList())
				);
	}
	
	public MonthlyRecordWorkType toDomain() {
		return new MonthlyRecordWorkType(
				this.krcmtMonthlyRecordWorkTypePK.companyID,
				new BusinessTypeCode(this.krcmtMonthlyRecordWorkTypePK.businessTypeCode),
				new MonthlyActualResults(this.listKrcmtMonthlyActualResultRC.stream().map(c->c.toDomain()).collect(Collectors.toList()))
				);
	}
	
}
