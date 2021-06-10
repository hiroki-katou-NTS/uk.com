package nts.uk.cnv.core.infra.entity.conversiontable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ScvmtConversionTablePk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "TARGET_TBL_NAME")
	private String targetTableName;

	@Column(name = "RECORD_NO")
	private int recordNo;

	@Column(name = "TARGET_COLUMN_NAME")
	private String targetColumnName;
}