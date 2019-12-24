package test.mandatoryretirement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JsonObjectDto {

	private List<RetirePlanCourceDto> retirePlanCource;
}
