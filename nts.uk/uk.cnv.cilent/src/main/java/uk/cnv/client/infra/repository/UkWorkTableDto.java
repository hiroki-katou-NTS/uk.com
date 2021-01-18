package uk.cnv.client.infra.repository;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UkWorkTableDto {
	private String tableName;
	private String columnName;
	private boolean isNullable;
}
