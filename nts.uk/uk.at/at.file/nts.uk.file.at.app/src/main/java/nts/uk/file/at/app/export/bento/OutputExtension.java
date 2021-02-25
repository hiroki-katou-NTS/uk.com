package nts.uk.file.at.app.export.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OutputExtension {

    PDF(0),

    EXCEL(1);

    public final int value;
}
