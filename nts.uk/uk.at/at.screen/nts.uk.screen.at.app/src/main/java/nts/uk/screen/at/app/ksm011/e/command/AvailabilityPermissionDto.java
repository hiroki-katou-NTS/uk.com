package nts.uk.screen.at.app.ksm011.e.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvailabilityPermissionDto {
    private int functionNo;
    // 1=> true ; 0 => false
    private int available;
}
