package nts.uk.screen.at.app.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BentoItemDto {
    private int code;
	private String locationCode;
    private String name;
}