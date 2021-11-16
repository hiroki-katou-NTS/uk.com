package uk.cnv.client.infra.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UkWorkTableDto {
	@Getter
	@Setter
	private String tableName;

	private String columnName;
	private String typeName;

	public String columnExpression() {
		return needConversion()
				? "NULLIF("+ columnName + ",SPACE(0))"
				: columnName;
	}

	private boolean needConversion() {
		return (
					this.typeName.equals("char") ||
					this.typeName.equals("varchar") ||
					this.typeName.equals("nchar") ||
					this.typeName.equals("nvarchar")
				);
	}
}
