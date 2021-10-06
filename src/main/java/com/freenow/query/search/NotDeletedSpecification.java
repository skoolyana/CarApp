package com.freenow.query.search;
import org.springframework.data.jpa.domain.Specification;

import com.freenow.domainobject.DriverDO;

public class NotDeletedSpecification {

    private NotDeletedSpecification()
    {}

    
    public static Specification<DriverDO> isNotDeleted()
    {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);

    }
    
}
