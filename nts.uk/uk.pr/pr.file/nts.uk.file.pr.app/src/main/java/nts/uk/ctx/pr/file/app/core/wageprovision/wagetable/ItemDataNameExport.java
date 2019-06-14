package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDataNameExport {
    private String code;
    private String name;
    private String type;

    public ItemDataNameExport(String code, String name){
        this.code = code;
        this.name = name;
    }
}
