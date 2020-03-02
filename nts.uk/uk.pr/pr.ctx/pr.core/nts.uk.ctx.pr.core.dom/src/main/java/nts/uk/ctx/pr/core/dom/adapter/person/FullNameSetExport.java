package nts.uk.ctx.pr.core.dom.adapter.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FullNameSetExport {
    /**
     * 氏名 - FullName
     */
    private String fullName;

    /**
     * 氏名カナ - FullNameKana
     */
    private String fullNameKana;

    public FullNameSetExport(FullNameSetExport fullName) {
        this.fullName = fullName.fullName == null ? "" : fullName.fullName;
        this.fullNameKana = fullName.fullNameKana == null ? "" : fullName.fullNameKana;
    }
}
