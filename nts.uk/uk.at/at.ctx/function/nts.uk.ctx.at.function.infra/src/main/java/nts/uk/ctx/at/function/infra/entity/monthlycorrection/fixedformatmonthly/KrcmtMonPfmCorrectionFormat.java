package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_AUT_MON_FORM")
public class KrcmtMonPfmCorrectionFormat extends ContractUkJpaEntity implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtMonPfmCorrectionFormatPK krcmtMonPfmCorrectionFormatPK;

	@Column(name = "MON_FORMAT_NAME")
	public String sheetName;
	
	@OneToMany(mappedBy="monthlyactualresultpfm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_AUT_MON_FORM_SHEET")
	public List<KfnmtMonthlyActualResultPrm> listKrcmtMonthlyActualResultPfm;
	
	@Override
	protected Object getKey() {
		return krcmtMonPfmCorrectionFormatPK;
	}
	
	public KrcmtMonPfmCorrectionFormat(KrcmtMonPfmCorrectionFormatPK krcmtMonPfmCorrectionFormatPK, String sheetName, List<KfnmtMonthlyActualResultPrm> listKrcmtMonthlyActualResultPfm) {
		super();
		this.krcmtMonPfmCorrectionFormatPK = krcmtMonPfmCorrectionFormatPK;
		this.sheetName = sheetName;
		this.listKrcmtMonthlyActualResultPfm = listKrcmtMonthlyActualResultPfm;
	}
	
	public static KrcmtMonPfmCorrectionFormat toEntity(MonPfmCorrectionFormat domain) {
		return new KrcmtMonPfmCorrectionFormat(
					new KrcmtMonPfmCorrectionFormatPK(
						domain.getCompanyID(),
						domain.getMonthlyPfmFormatCode().v()
							),
					domain.getMonPfmCorrectionFormatName().v(),
					domain.getDisplayItem().getListSheetCorrectedMonthly().stream()
					.map(c->KfnmtMonthlyActualResultPrm.toEntity(domain.getCompanyID(),
						domain.getMonthlyPfmFormatCode().v(), c)).collect(Collectors.toList())
				);
	}
	
	public MonPfmCorrectionFormat toDomain() {
		return new MonPfmCorrectionFormat(
				this.krcmtMonPfmCorrectionFormatPK.companyID,
				new MonthlyPerformanceFormatCode(this.krcmtMonPfmCorrectionFormatPK.monthlyPfmFormatCode),
				new MonPfmCorrectionFormatName(this.sheetName),
				new MonthlyActualResults(this.listKrcmtMonthlyActualResultPfm.stream().map(c->c.toDomain()).collect(Collectors.toList()))
				);
	}
}
