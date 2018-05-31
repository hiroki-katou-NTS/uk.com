package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.Data;

@Data
public class MPCellDataDto {
	
	private String columnKey;
	
	private String value;
	
	private String dataType;
	
	private String type;
	
	public MPCellDataDto(String columnKey, String value, String dataType, String type) {
		this.value = value;
		this.dataType = dataType;
		this.columnKey = columnKey;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MPCellDataDto other = (MPCellDataDto) obj;
		if (columnKey == null) {
			if (other.columnKey != null)
				return false;
		} else if (!columnKey.equals(other.columnKey))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnKey == null) ? 0 : columnKey.hashCode());
		return result;
	}
	
}