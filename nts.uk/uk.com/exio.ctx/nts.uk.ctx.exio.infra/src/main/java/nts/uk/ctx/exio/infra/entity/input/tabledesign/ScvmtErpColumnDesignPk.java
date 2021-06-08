package nts.uk.ctx.exio.infra.entity.input.tabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ScvmtErpColumnDesignPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TABLE_NAME")
	public String tableName;

	@Column(name = "ID")
	public String id;

}
