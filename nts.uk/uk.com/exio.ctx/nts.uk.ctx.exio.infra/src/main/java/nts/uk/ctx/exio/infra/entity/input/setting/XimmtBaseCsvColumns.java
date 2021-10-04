package nts.uk.ctx.exio.infra.entity.input.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  ベースCSV列
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_BASE_CSV_COLUMNS")
public class XimmtBaseCsvColumns extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtBaseCsvColumnsPK pk; 

	/* CSV列名 */
	@Column(name = "COLUMN_NAME")
	private String columnName;

	@ManyToOne
	@JoinColumns( {
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false)
	})
	public XimmtImportSetting importSetting;
	
	@Override
	protected Object getKey() {
		return pk;
	}

}
