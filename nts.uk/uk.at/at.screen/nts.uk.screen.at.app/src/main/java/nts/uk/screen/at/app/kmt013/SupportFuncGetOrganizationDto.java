package nts.uk.screen.at.app.kmt013;

import lombok.Value;

import java.util.Objects;

@Value
public class SupportFuncGetOrganizationDto {
    /**
     * 単位
     */
    private int unit;

    private String orgId;
    @Override
    public int hashCode() {
        return Objects.hash(orgId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SupportFuncGetOrganizationDto other = (SupportFuncGetOrganizationDto) obj;
        return Objects.equals(orgId, other.orgId);
    }
}
