/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.service;

import lombok.Getter;

/**
 * The Class WorkTimeAggregateRoot.
 */
public class WorkTimeAggregateRoot extends WorkTimeDomainObject {
    
    /** The version. */
    @Getter
    private long version;
    
    /**
     * Instantiates a new work time aggregate root.
     */
    public WorkTimeAggregateRoot() {
        this(0);
    }
    
    /**
     * Instantiates a new work time aggregate root.
     *
     * @param version the version
     */
    public WorkTimeAggregateRoot(int version) {
        this.version = version;
    }
    
    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(long version) {
        this.version = version;
    }
}
