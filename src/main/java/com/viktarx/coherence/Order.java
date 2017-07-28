package com.viktarx.coherence;

import com.tangosol.io.AbstractEvolvable;
import com.tangosol.io.pof.EvolvablePortableObject;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializable Order class with Coherence Write-Behind cache type support.
 */

@Entity
@Table(name = "ORDER")
public class Order extends AbstractEvolvable implements EvolvablePortableObject {

    private static final int VERSION = 1;

    private static final int ID = 0;
    private static final int ITEMS = 1;
    private static final int PROFILE = 2;
    private static final int STATUS = 3;
    private static final int CREATION_DATE = 4;
    private static final int LAST_MODIFIED_DATE = 5;

    @Column(name = "ID")
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ITEMS", joinColumns = @JoinColumn(name = "PROFILE"))
    @MapKeyColumn(name = "SKU_ID")
    @Column(name = "SKU_QUANTITY")
    private Map<String, Long> items;

    @Column(name = "STATUS")
    private String status;

    @Id
    @Column(name = "PROFILE")
    private String profile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

    public Order(String id, Map<String, Long> items, String status, String profile, Date creationDate, Date lastModifiedDate) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.profile = profile;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public void readExternal(PofReader pofReader) throws IOException {
        id = pofReader.readString(ID);
        items = pofReader.readMap(ITEMS, new HashMap<String, Long>());
        profile = pofReader.readString(PROFILE);
        status = pofReader.readString(STATUS);
        creationDate = pofReader.readDate(CREATION_DATE);
        lastModifiedDate = pofReader.readDate(LAST_MODIFIED_DATE);
    }

    public void writeExternal(PofWriter pofWriter) throws IOException {
        pofWriter.writeString(ID, id);
        pofWriter.writeMap(ITEMS, items);
        pofWriter.writeString(PROFILE, profile);
        pofWriter.writeString(STATUS, status);
        pofWriter.writeDateTime(CREATION_DATE, creationDate);
        pofWriter.writeDateTime(LAST_MODIFIED_DATE, lastModifiedDate);
    }

    @Override
    public int getImplVersion() {
        return VERSION;
    }

    public String id() {
        return id;
    }

    public Map<String, Long> items() {
        return items;
    }

    public String profile() {
        return profile;
    }

    public String status() {
        return status;
    }

    public Date creationDate() {
        return creationDate;
    }

    public Date lastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public String toString() {
        return String.format("Order[order=%s, items=%s, profile=%s, status=%s, creationDate=%s, lastModifiedDate=%s]",
                id, items, profile, status, creationDate, lastModifiedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (!items.equals(order.items)) return false;
        if (!status.equals(order.status)) return false;
        if (!profile.equals(order.profile)) return false;
        if (!creationDate.equals(order.creationDate)) return false;
        return lastModifiedDate.equals(order.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + items.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + profile.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + lastModifiedDate.hashCode();
        return result;
    }
}
