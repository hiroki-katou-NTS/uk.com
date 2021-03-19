package nts.uk.screen.at.app.kmt001;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TaskResultDto {

    private List<KmtDto> listTask;
    private Optional<KmtDto> optionalTask;
}
