package nts.uk.file.at.app.export.holidayconfirmationtable;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateTraceConfirmationTableFileQuery {
    private List<String> listEmployeeId;
}
