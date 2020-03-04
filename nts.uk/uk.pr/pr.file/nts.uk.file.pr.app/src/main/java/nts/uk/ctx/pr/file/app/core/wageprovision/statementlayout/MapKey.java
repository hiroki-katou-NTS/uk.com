package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
@Getter
public class MapKey {
    private String code;
    private int category;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                        append(code).
                        append(category).
                        toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapKey))
            return false;
        if (obj == this)
            return true;

        MapKey rhs = (MapKey) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(code, rhs.code).
                        append(category, rhs.category).
                        isEquals();
    }
}
