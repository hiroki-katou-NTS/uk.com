package nts.uk.cnv.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class ScvmtIndexDesignPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TABLE_ID")
	public String tableId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "COLUMN_NAME")
	private String columnName;

}
