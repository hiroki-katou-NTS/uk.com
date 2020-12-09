package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_FORM_SHEET")
public class KrcmtBusinessFormatSheet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessFormatSheetPK krcmtBusinessFormatSheetPK;

	@Column(name = "SHEET_NAME")
	public String sheetName;

	@Override
	protected Object getKey() {
		return this.krcmtBusinessFormatSheetPK;
	}
}
