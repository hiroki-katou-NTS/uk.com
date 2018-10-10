package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.primitive.PostCode;

import java.util.Optional;

/**
 * とりまとめ局
 */
@Getter
public class CompileStation extends DomainObject {

    /**
     * 名称
     */
    private Optional<CompileStationName> name;

    /**
     * 郵便番号
     */
    private Optional<PostCode> zipCode;

    public CompileStation(String name, String zipCode)
    {
        this.name = name == null ? Optional.empty() : Optional.of(new CompileStationName(name));
        this.zipCode = zipCode == null ? Optional.empty() : Optional.of(new PostCode(zipCode));
    }

}
