package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ScvmtConversionCategoryTablesPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY_NAME")
	public String categoryName;

	@Column(name = "TARGET_TBL_NAME")
	public String targetTableName;

}